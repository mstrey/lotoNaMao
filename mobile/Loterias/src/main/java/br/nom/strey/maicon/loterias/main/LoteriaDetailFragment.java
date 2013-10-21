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
        getActivity().getActionBar().setTitle(getString(R.string.em_aberto));
        rootView = inflater.inflate(R.layout.fragment_principal, container, false);

        return rootView;

    }
}
