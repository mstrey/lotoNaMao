package br.nom.strey.maicon.loterias.megasena;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.nom.strey.maicon.loterias.R;
import br.nom.strey.maicon.loterias.main.LoteriaDetailActivity;

public class MegaListFragment extends Fragment {

    private static final String TAG = "MegaListFragment";
    private MegaEditFragment megaEditFragment = new MegaEditFragment();
    private View rootView = null;
    private ListView listView_volantes = null;
    private Context ctx;
    private List<MegasenaVolantesVO> listMegaVolantes;
    private MegasenaVolantesDAO dao_volantes;
    private MegasenaVolantesAdapter adapter_volantes;


    public MegaListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getActionBar().setTitle(getString(R.string.megasena_action_header_list));

        rootView = inflater.inflate(R.layout.fragment_megasena_list, container, false);

        ctx = getActivity().getBaseContext();

        listView_volantes = (ListView) rootView.findViewById(R.id.lv_volantes);
        dao_volantes = new MegasenaVolantesDAO(ctx);
        listMegaVolantes = dao_volantes.getAll();

        adapter_volantes = new MegasenaVolantesAdapter(getActivity(), listMegaVolantes);

        listView_volantes.setAdapter(adapter_volantes);
        listView_volantes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView_volantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                MegasenaVolantesVO vo_mega_volante_item = listMegaVolantes.get(position);

                ((LoteriaDetailActivity) getActivity()).editMegaFragment(vo_mega_volante_item);

            }
        });

        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
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
                Toast.makeText(getActivity().getBaseContext(), "refresh", Toast.LENGTH_LONG).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refreshVolantesList() {
        listMegaVolantes = dao_volantes.getAll();
        adapter_volantes = new MegasenaVolantesAdapter(this, listMegaVolantes);
        listView_volantes.setAdapter(adapter_volantes);
        adapter_volantes.notifyDataSetChanged();
    }

}
