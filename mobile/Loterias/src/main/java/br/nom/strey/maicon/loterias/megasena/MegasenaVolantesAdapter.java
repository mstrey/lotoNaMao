package br.nom.strey.maicon.loterias.megasena;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by maicon on 06/09/13.
 */
public class MegasenaVolantesAdapter extends BaseAdapter {

    private List<MegasenaVolantesVO> lista;
    private Activity ctx;

    public MegasenaVolantesAdapter(Activity ctx, List<MegasenaVolantesVO> lista){
        this.ctx = ctx;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // TODO ajustar dados na View
        return null;
    }
}
