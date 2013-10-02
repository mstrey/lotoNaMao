package br.nom.strey.maicon.loterias.megasena;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.nom.strey.maicon.loterias.R;

/**
 * Created by maicon on 06/09/13.
 */
public class MegasenaVolantesAdapter extends BaseAdapter {
    private List<MegasenaVolantesVO> lista;
    private Activity ctx;
    private Fragment fragment;

    public MegasenaVolantesAdapter(Activity ctx, List<MegasenaVolantesVO> lista) {
        this.ctx = ctx;
        this.lista = lista;
    }

    public MegasenaVolantesAdapter(Fragment fragment, List<MegasenaVolantesVO> lista) {
        this.fragment = fragment;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lista.get(i).getVolanteId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // TODO ajustar dados na View
        final MegasenaVolantesVO vo_mega_volante = lista.get(position);

        LayoutInflater layout = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layout.inflate(R.layout.fragment_megasena_list_row, null);

        TextView txt_concurso = (TextView) v.findViewById(R.id.mega_row_concurso);
        TextView txt_aposta = (TextView) v.findViewById(R.id.mega_row_aposta);
        TextView txt_acertos = (TextView) v.findViewById(R.id.mega_row_acertos);
        ImageView img_discard = (ImageView) v.findViewById(R.id.mega_row_discard);

        txt_concurso.setText(vo_mega_volante.getConcurso().toString());
        txt_aposta.setText(vo_mega_volante.getApostaView());
        txt_acertos.setText(vo_mega_volante.getQtdAcertos().toString());

        img_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener discarClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                MegasenaVolantesDAO dao_volantes = new MegasenaVolantesDAO(ctx);
                                dao_volantes.delete(vo_mega_volante);
                                ((MegaListFragment) fragment).refreshVolantesList();
                        }

                    }
                };
            }
        });

        return v;
    }
}
