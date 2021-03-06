package br.nom.strey.maicon.loterias.quina;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by maicon on 06/09/13.
 */
public class QuinaResultadosAdapter extends BaseAdapter {

    private List<QuinaResultadosVO> lista;
    private Activity ctx;

    public QuinaResultadosAdapter(Activity ctx, List<QuinaResultadosVO> lista) {
        this.ctx = ctx;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).getConcurso();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // TODO ajustar dados na View
        return null;
    }
}
