package br.nom.strey.maicon.loterias.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.nom.strey.maicon.loterias.R;

public class LoteriaDetailFragment extends Fragment {

    private final String TAG = "DetailFragment";

    public static final String ARG_ITEM_ID = "item_id";

    public LoteriaDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = null;
        getActivity().getActionBar().setTitle(getString(R.string.emaberto));
        rootView = inflater.inflate(R.layout.fragment_principal, container, false);

//        TextView txt_conc_ini = null;
//        TextView txt_conc_fim = null;
//        TextView txt_teimosinha = null;
//
//        switch (mItem.id){
//            case 1:
//                getActivity().getActionBar().setTitle(getString(R.string.emaberto));
//                rootView = inflater.inflate(R.layout.fragment_principal, container, false);
//                break;
//
//            case 2:
//                getActivity().getActionBar().setTitle(getString(R.string.megasena));
//                rootView = inflater.inflate(R.layout.fragment_megasena_edit, container, false);
//
//                txt_conc_ini = (TextView) rootView.findViewById(R.id.ed_mega_conc_ini);
//                txt_conc_fim = (TextView) rootView.findViewById(R.id.ed_mega_conc_final);
//                txt_teimosinha = (TextView) rootView.findViewById(R.id.ed_mega_teimosinha);
//
//                txt_conc_ini.setText("1521");
//                txt_teimosinha.setText("2");
//                txt_conc_fim.setText("1522");
//
//                txt_conc_ini.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        Log.d(TAG, "onClick(ini)");
//                    }
//
//                });
//
//                txt_conc_fim.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        Log.d(TAG, "onClick(fim)");
//                    }
//                });
//
//                txt_teimosinha.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        Log.d(TAG, "onClick(teimosa)");
//                    }
//                });
//
//                break;
//
//            case 3:
//                getActivity().getActionBar().setTitle(getString(R.string.quina));
//                rootView = inflater.inflate(R.layout.fragment_quina, container, false);
//                break;
//
//        }

        return rootView;

    }
}
