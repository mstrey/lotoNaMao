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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import br.nom.strey.maicon.loterias.R;
import br.nom.strey.maicon.loterias.main.Categories;
import br.nom.strey.maicon.loterias.main.LoteriaDetailActivity;
import br.nom.strey.maicon.loterias.utils.DBHelper;
import br.nom.strey.maicon.loterias.utils.WebService;

public class MegaListFragment extends Fragment {

    private static final String LOGTAG = "MegaListFragment";
    private View rootView = null;
    private ListView listView_volantes = null;
    private Context ctx;
    private List<MegasenaVolantesVO> listMegaVolantes;
    private MegasenaVolantesDAO dao_volantes;
    private MegasenaVolantesAdapter adapter_volantes;
    private MenuItem refresh;
    private Menu menu;
    private static Integer concurso_max_remote_resultados;
    private static final String MAX_CONCURSO = "max_conc";


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
        getActivity().getActionBar().setTitle(getString(R.string.action_megasena_header_list));

        ctx = getActivity().getBaseContext();
        rootView = inflater.inflate(R.layout.fragment_megasena_list, container, false);

        refresh = menu.findItem(R.id.action_update);

        listView_volantes = (ListView) rootView.findViewById(R.id.lv_volantes);
        dao_volantes = new MegasenaVolantesDAO(ctx);
        listMegaVolantes = dao_volantes.getAll();

        adapter_volantes = new MegasenaVolantesAdapter(getActivity(), listMegaVolantes, false);

        listView_volantes.setAdapter(adapter_volantes);
        listView_volantes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

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

    public void refreshVolantesList(Boolean exibe_acertos) {
        listMegaVolantes = dao_volantes.getAll();
        adapter_volantes = new MegasenaVolantesAdapter(getActivity(), listMegaVolantes, exibe_acertos);
        listView_volantes.setAdapter(adapter_volantes);
        adapter_volantes.notifyDataSetChanged();
        if (listMegaVolantes.size() > 0) {
            refresh.setVisible(true);
        } else {
            refresh.setVisible(false);
        }
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
            if (refresh != null) {
                refresh.setActionView(R.layout.actionbar_indeterminate_progress);
            }
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (refresh != null) {
                refresh.setActionView(null);
            }
            refreshVolantesList(true);

            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            MegasenaVolantesDAO dao_volantes = new MegasenaVolantesDAO(ctx);
            List<MegasenaVolantesVO> volantes_para_conferir = dao_volantes.getAll();
            ArrayList<Integer> concursos_para_conferir = dao_volantes.getConcursosParaConferir();
            MegasenaResultadosDAO dao_resultado = new MegasenaResultadosDAO(ctx);

            if (!concursos_para_conferir.isEmpty()) {

//                dao_resultado.getConcRemote(concursos_para_conferir);

                for (Integer concurso : concursos_para_conferir) {
                    if (!dao_resultado.existeResultado(concurso)) {
                        if (WebService.isConnected(ctx) != WebService.DISCONNECTED) {
                            StringBuffer strUrl = new StringBuffer("http://maicon.strey.nom.br/");
                            strUrl.append("loto/");
                            strUrl.append("getResults.php");
                            strUrl.append("?loto=");
                            strUrl.append(URLEncoder.encode(Categories.MEGASENA));
                            strUrl.append("&concurso=");
                            strUrl.append(concurso);
                            try {

                                URL url = new URL(strUrl.toString());
                                URLConnection con = url.openConnection();
                                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                                String str_json = in.readLine();
                                Log.d(LOGTAG, "str_json: " + str_json);

                                JSONObject obj_json = new JSONObject(str_json);

                                if (concurso > 0) {
                                    MegasenaResultadosVO vo_resultado = new MegasenaResultadosVO();
                                    vo_resultado.setJson(obj_json);

                                    if (dao_resultado.existe(vo_resultado.getConcurso())) {
                                        dao_resultado.update(vo_resultado);
                                    } else {
                                        dao_resultado.insert(vo_resultado);
                                    }

                                } else {
                                    concurso_max_remote_resultados = obj_json.getInt(MAX_CONCURSO);
                                    Log.d(LOGTAG, MAX_CONCURSO + "_remote: " + concurso_max_remote_resultados);
                                }

                            } catch (Exception e) {
                                concurso_max_remote_resultados = 1;
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(ctx, getString(R.string.conexao_nao_identificada), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                for (MegasenaVolantesVO vo_volante : volantes_para_conferir) {
                    vo_volante.confereResultado(ctx);
                    dao_volantes = new MegasenaVolantesDAO(ctx);
                    dao_volantes.update(vo_volante);
                }
            }

//            backUpDB();

            return null;
        }
    }

}
