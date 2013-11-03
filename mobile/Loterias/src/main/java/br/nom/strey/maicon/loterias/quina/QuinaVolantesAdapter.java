package br.nom.strey.maicon.loterias.quina;

import android.app.Activity;
import android.app.AlertDialog;
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
import br.nom.strey.maicon.loterias.main.LoteriaDetailActivity;

/**
 * Created by maicon on 06/09/13.
 */
public class QuinaVolantesAdapter extends BaseAdapter {
    private List<QuinaVolantesVO> lista;
    private Activity ctx;
    private Boolean exibe_acertos;
    private Fragment fragment;

    public QuinaVolantesAdapter(Activity ctx, List<QuinaVolantesVO> lista, Boolean exibe_acertos) {
        this.ctx = ctx;
        this.lista = lista;
        this.exibe_acertos = exibe_acertos;
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
        final QuinaVolantesVO vo_quina_volante = lista.get(position);

        LayoutInflater layout = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layout.inflate(R.layout.fragment_quina_list_row, null);

        TextView txt_concurso = (TextView) v.findViewById(R.id.quina_row_concurso);
        TextView txt_aposta = (TextView) v.findViewById(R.id.quina_row_aposta);
        TextView txt_acertos = (TextView) v.findViewById(R.id.quina_row_acertos);
        ImageView img_discard = (ImageView) v.findViewById(R.id.quina_row_discard);

        Integer acertos = vo_quina_volante.getQtdAcertos();

        txt_concurso.setText(vo_quina_volante.getConcurso().toString());
        txt_aposta.setText(vo_quina_volante.getApostaView());

        QuinaResultadosDAO resultadosDAO = new QuinaResultadosDAO(ctx);

        if (exibe_acertos) {
            txt_acertos.setText(resultadosDAO.existeResultado(vo_quina_volante.getConcurso()) ? acertos.toString() : "");
        }

        View.OnClickListener editClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoteriaDetailActivity) ctx).editQuinaFragment(vo_quina_volante);
            }
        };

        txt_concurso.setOnClickListener(editClickListener);
        txt_aposta.setOnClickListener(editClickListener);
        txt_acertos.setOnClickListener(editClickListener);

        img_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener discardClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                QuinaVolantesDAO dao_volantes = new QuinaVolantesDAO(ctx);
                                dao_volantes.delete(vo_quina_volante);
                                ((LoteriaDetailActivity) ctx).refreshFragmentList(true);
                        }

                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage(R.string.msg_excluir).setPositiveButton(R.string.ok, discardClickListener)
                        .setNegativeButton(R.string.cancel, discardClickListener).show();
            }
        });

        return v;
    }
}
