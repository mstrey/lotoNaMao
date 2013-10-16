package br.nom.strey.maicon.loterias.megasena;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import br.nom.strey.maicon.loterias.R;
import br.nom.strey.maicon.loterias.main.LoteriaDetailActivity;
import br.nom.strey.maicon.loterias.utils.DBHelper;

public class MegaListFragment extends Fragment {

    private static final String LOGTAG = "MegaListFragment";
    private MegaEditFragment megaEditFragment = new MegaEditFragment();
    private View rootView = null;
    private ListView listView_volantes = null;
    private Context ctx;
    private List<MegasenaVolantesVO> listMegaVolantes;
    private MegasenaVolantesDAO dao_volantes;
    private MegasenaVolantesAdapter adapter_volantes;
    private MenuItem refresh;
    private Menu menu;
    private Boolean mTwoPane;


    public MegaListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOGTAG, "onCreate");
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new CheckVolantesResultado().execute();
        getActivity().getActionBar().setTitle(getString(R.string.megasena_action_header_list));

        ctx = getActivity().getBaseContext();
        rootView = inflater.inflate(R.layout.fragment_megasena_list, container, false);

        listView_volantes = (ListView) rootView.findViewById(R.id.lv_volantes);
        dao_volantes = new MegasenaVolantesDAO(ctx);
        listMegaVolantes = dao_volantes.getAll();

        adapter_volantes = new MegasenaVolantesAdapter(getActivity(), listMegaVolantes);

        listView_volantes.setAdapter(adapter_volantes);
        listView_volantes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

//        listView_volantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                MegasenaVolantesVO vo_mega_volante_item = listMegaVolantes.get(position);
//
//                ((LoteriaDetailActivity) getActivity()).editMegaFragment(vo_mega_volante_item);
//
//            }
//        });

        return rootView;

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        ctx = getActivity().getBaseContext();

        refresh = menu.findItem(R.id.action_update);

        MegasenaVolantesDAO dao_volantes = new MegasenaVolantesDAO(ctx);

        if (dao_volantes.getAll().isEmpty()) {
            refresh.setVisible(false);
        }

//        backUpDB();

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.menu = menu;

        menu.clear();
        menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.action_lista, menu);

        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_incluir:

                ((LoteriaDetailActivity) getActivity()).editMegaFragment();

                return true;
            case R.id.action_update:
                new CheckVolantesResultado().execute();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refreshVolantesList() {
        listMegaVolantes = dao_volantes.getAll();
        adapter_volantes = new MegasenaVolantesAdapter(getActivity(), listMegaVolantes);
        listView_volantes.setAdapter(adapter_volantes);
        adapter_volantes.notifyDataSetChanged();
    }

    private void backUpDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//br.nom.strey.maicon.loterias//databases//" + DBHelper.DBNAME;
                String backupDBPath = "//data//" + DBHelper.DBNAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(ctx, "Backup Efetuado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ctx, "Base de origem não localizada: " + currentDBPath, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ctx, "Sem permissão de escrita no SDCARD", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
        }
    }

    private class CheckVolantesResultado extends AsyncTask<Void, Void, Void> {

        public CheckVolantesResultado() {

        }

        @Override
        protected void onPreExecute() {
            refresh.setActionView(R.layout.actionbar_indeterminate_progress);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            refresh.setActionView(null);
            refreshVolantesList();

            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<MegasenaVolantesVO> volantes_nao_conferidos = MegasenaVolantesDAO.getNaoConferidos(ctx);
            List<Integer> concursos_nao_conferidos = MegasenaVolantesDAO.getConcursosNaoConferidos(ctx);
            MegasenaResultadosDAO dao_resultado = new MegasenaResultadosDAO(ctx);

            ArrayList<Integer> concursos = new ArrayList<Integer>();

            if (!concursos_nao_conferidos.isEmpty()) {

                for (Integer conc : concursos_nao_conferidos) {
                    concursos.add(conc);
                }

                dao_resultado.getConcRemote(concursos);

                for (MegasenaVolantesVO vo_volante : volantes_nao_conferidos) {
                    vo_volante.confereResultado(ctx);
                    MegasenaVolantesDAO dao_volantes = new MegasenaVolantesDAO(ctx);
                    dao_volantes.update(vo_volante);
                }
            }

//            backUpDB();

            return null;
        }
    }

}
