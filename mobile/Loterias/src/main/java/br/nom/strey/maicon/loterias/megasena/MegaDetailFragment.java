package br.nom.strey.maicon.loterias.megasena;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.nom.strey.maicon.loterias.R;

public class MegaDetailFragment extends Fragment {

    private static final String TAG = "MegaDetailFragment";

    private static final Integer CONCURSO_INICAL = 111;
    private static final Integer TEIMOZINHA = 222;

    public static final String ARG_ITEM_ID = "item_id";

    private NumberPicker np_conc_ini;
    private View rootView = null;
    private TextView txt_conc_ini = null;
    private TextView txt_conc_fim = null;
    private TextView txt_teimosinha = null;
    private Button btn_save = null;
    private Toast mToast;
    private Integer conc_ini;
    private Integer conc_fim;
    private Integer teimosinha;
    private String[] teimosinha_options;

    private AlertDialog.Builder np_dialog_conc_ini;
    private AlertDialog.Builder np_dialog_teimosinha;

    public ArrayList<Integer> lista_numeros_marcados = new ArrayList<Integer>();

    public MegaDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        getActivity().getActionBar().setTitle(getString(R.string.megasena));
        rootView = inflater.inflate(R.layout.fragment_megasena, container, false);

        txt_conc_ini = (TextView) rootView.findViewById(R.id.ed_mega_conc_ini);
        txt_conc_fim = (TextView) rootView.findViewById(R.id.ed_mega_conc_final);
        txt_teimosinha = (TextView) rootView.findViewById(R.id.ed_mega_teimosinha);
        btn_save = (Button) rootView.findViewById(R.id.btn_mega_save);

        MegasenaVolantesDAO volante_dao = new MegasenaVolantesDAO(getActivity().getBaseContext());
        Integer concurso_max = volante_dao.getMaxConc();

        txt_conc_ini.setText(concurso_max.toString());
        txt_teimosinha.setText("1");
        txt_conc_fim.setText(concurso_max.toString());

        txt_conc_ini.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick(ini)");

                np_dialog_conc_ini = new AlertDialog.Builder(getActivity());

                conc_ini = Integer.parseInt(txt_conc_ini.getText().toString());

                np_conc_ini = new NumberPicker(getActivity().getBaseContext());
                np_conc_ini.setFocusable(true);
                np_conc_ini.setFocusableInTouchMode(true);
                np_conc_ini.setMinValue(1);
                np_conc_ini.setMaxValue(1550);
                np_conc_ini.setValue(conc_ini);

                np_dialog_conc_ini.setView(np_conc_ini);

                np_dialog_conc_ini.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        conc_ini = ((Integer) np_conc_ini.getValue());
                        teimosinha = Integer.parseInt(txt_teimosinha.getText().toString());
                        conc_fim = conc_ini + teimosinha - 1;

                        txt_conc_ini.setText(conc_ini.toString());
                        txt_conc_fim.setText(conc_fim.toString());

                    }
                });
                np_dialog_conc_ini.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                np_dialog_conc_ini.setTitle(R.string.concurso_inicial);
                np_dialog_conc_ini.show();
            }

        });

        txt_teimosinha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                np_dialog_teimosinha = new AlertDialog.Builder(getActivity());
                conc_ini = Integer.parseInt(txt_conc_ini.getText().toString());
                teimosinha = Integer.parseInt(txt_teimosinha.getText().toString());
                np_dialog_teimosinha.setTitle(R.string.teimosinha);

                teimosinha_options = getResources().getStringArray(R.array.mega_teimosinha);

                np_dialog_teimosinha.setItems(teimosinha_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        conc_ini = Integer.parseInt(txt_conc_ini.getText().toString());
                        teimosinha = Integer.parseInt(teimosinha_options[which]);
                        conc_fim = conc_ini + teimosinha - 1;

                        txt_teimosinha.setText(teimosinha.toString());

                        TextView label_conc_fim = (TextView) rootView.findViewById(R.id.l_mega_conc_final);

                        if (which > 0) {
                            Log.d(TAG, "setVisible(teimosa1)");
                            txt_conc_fim.setText(conc_fim.toString());
                            txt_conc_fim.setVisibility(View.VISIBLE);
                            label_conc_fim.setVisibility(View.VISIBLE);
                        } else {
                            Log.d(TAG, "setVisible(teimosa2)");
                            txt_conc_fim.setText(conc_fim.toString());
                            txt_conc_fim.setVisibility(View.GONE);
                            label_conc_fim.setVisibility(View.GONE);
                        }

                    }
                });

                np_dialog_teimosinha.create();
                np_dialog_teimosinha.show();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MegasenaVolantesVO volante_vo = new MegasenaVolantesVO();
                MegasenaVolantesDAO volante_dao = new MegasenaVolantesDAO(getActivity().getBaseContext());

                conc_ini = Integer.parseInt(txt_conc_ini.getText().toString());
                teimosinha = Integer.parseInt(txt_teimosinha.getText().toString());
                conc_fim = Integer.parseInt(txt_conc_fim.getText().toString());

                String aposta = "";

                for(int i = 0; i < lista_numeros_marcados.size(); i++){
                    aposta += lista_numeros_marcados.get(i).toString();
                }

                for (int i=conc_ini; i<=conc_fim; i++) {
                    volante_vo.setConcurso(i);
                    volante_vo.setAposta(aposta);
                    volante_dao.insert(volante_vo);
                }

            }
        });

        return rootView;

    }

    public void setNumber(View v) {
        TextView txt_num = (TextView) v;
        Integer num = Integer.parseInt(txt_num.getText().toString());

        Log.d(TAG, "setNumber("+txt_num.getText().toString()+")");

        if (!lista_numeros_marcados.contains(num)) {

            if (lista_numeros_marcados.size() == 15) {
                Log.d(TAG, "numeros marcados(" + lista_numeros_marcados.size() + ")");
            } else {
                txt_num.setBackgroundColor(R.color.mega_green_dark);
                txt_num.setTextColor(R.color.mega_green_light);

                lista_numeros_marcados.add(num);
            }
        } else {
            txt_num.setBackgroundColor(Color.TRANSPARENT);
            txt_num.setTextColor(Color.BLACK);

            lista_numeros_marcados.remove(num);
        }

        if (lista_numeros_marcados.size() >= 6) {
            btn_save.setEnabled(true);
        } else {
            btn_save.setEnabled(false);
        }

    }


}
