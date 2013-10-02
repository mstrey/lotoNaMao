package br.nom.strey.maicon.loterias.megasena;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import br.nom.strey.maicon.loterias.R;
import br.nom.strey.maicon.loterias.main.LoteriaDetailActivity;

public class MegaEditFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    private static final String TAG = "MegaEditFragment";
    private ArrayList<String> lista_numeros_marcados = new ArrayList<String>();
    private MegasenaVolantesVO vo_volante_mega;
    private NumberPicker np_conc_ini;
    private View rootView;
    private TextView txt_conc_ini = null;
    private TextView txt_conc_fim = null;
    private TextView txt_teimosinha = null;
    private Toast mToast;
    private Integer conc_ini;
    private Integer conc_fim;
    private Integer teimosinha;
    private Integer concurso_max;
    private String[] teimosinha_options;
    private Context ctx;
    private AlertDialog.Builder np_dialog_conc_ini;
    private AlertDialog.Builder np_dialog_teimosinha;
    private Boolean editing = false;

    public MegaEditFragment() {

    }

    public MegaEditFragment(MegasenaVolantesVO vo_volante) {
        vo_volante_mega = vo_volante;
        editing = true;
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

        getActivity().getActionBar().setTitle(getString(R.string.megasena_action_header_edit));
        rootView = inflater.inflate(R.layout.fragment_megasena_edit, container, false);

        ctx = getActivity().getBaseContext();

        txt_conc_ini = (TextView) rootView.findViewById(R.id.mega_conc_ini_ed);
        txt_conc_fim = (TextView) rootView.findViewById(R.id.mega_conc_final_ed);
        txt_teimosinha = (TextView) rootView.findViewById(R.id.mega_teimosinha_ed);

        MegasenaResultadosDAO dao_resultado = new MegasenaResultadosDAO(ctx);

        concurso_max = dao_resultado.getMaxConcResultado();

        if (editing) {
            txt_conc_ini.setText(vo_volante_mega.getConcurso().toString());
            setAposta(vo_volante_mega.getAposta());
        } else {
            txt_conc_ini.setText(concurso_max.toString());
        }
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
                np_conc_ini.setMaxValue(concurso_max + 8);

                np_conc_ini.setValue(conc_ini);

                np_dialog_conc_ini.setView(np_conc_ini);

                np_dialog_conc_ini.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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

                        TextView label_conc_fim = (TextView) rootView.findViewById(R.id.mega_conc_final_l);

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

        return rootView;

    }

    public void setNumber(View v) {
        TextView txt_num = (TextView) v;
        String num = txt_num.getText().toString();

        Log.d(TAG, "setNumber(" + txt_num.getText().toString() + ")");

        if (!lista_numeros_marcados.contains(num)) {

            if (lista_numeros_marcados.size() == 15) {
                ((LoteriaDetailActivity) getActivity()).exibeToast(R.string.marcar_maximo_quinze);
            } else {
                txt_num.setBackgroundColor(getResources().getColor(R.color.mega_green_dark));
                txt_num.setTextColor(getResources().getColor(R.color.mega_green_light));

                lista_numeros_marcados.add(num);
            }
        } else {
            txt_num.setBackgroundColor(Color.TRANSPARENT);
            txt_num.setTextColor(Color.BLACK);

            lista_numeros_marcados.remove(num);
        }

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setAposta(String aposta) {

        TextView txt_numero = null;
        String numero = "";
        ArrayList<View> txt_aposta = new ArrayList<View>();

        for (int i = 0; i < aposta.length(); i = i + 2) {
            numero = aposta.substring(i, i + 2);
            txt_aposta = new ArrayList<View>();

            LinearLayout ll_numbers = (LinearLayout) rootView.findViewById(R.id.mega_volante_numbers);
            ll_numbers.findViewsWithText(txt_aposta, numero, View.FIND_VIEWS_WITH_TEXT);

            setNumber(txt_aposta.get(0));
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.action_editar, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        MegasenaVolantesDAO dao_volante_mega = new MegasenaVolantesDAO(getActivity().getBaseContext());
        MegaListFragment megaListFragment = new MegaListFragment();
        if (!editing) {
            vo_volante_mega = new MegasenaVolantesVO();
        }

        switch (item.getItemId()) {
            case R.id.action_save:
                Log.d(TAG, "save");

                if (lista_numeros_marcados.size() >= 6) {
                    if (lista_numeros_marcados.size() > 15) {
                        ((LoteriaDetailActivity) getActivity()).exibeToast(R.string.marcar_maximo_quinze);
                    } else {
                        conc_ini = Integer.parseInt(txt_conc_ini.getText().toString());
                        conc_fim = Integer.parseInt(txt_conc_fim.getText().toString());

                        String aposta = "";

                        Collections.sort(lista_numeros_marcados);

                        for (int i = 0; i < lista_numeros_marcados.size(); i++) {
                            aposta += lista_numeros_marcados.get(i).toString();
                        }

                        for (int i = conc_ini; i <= conc_fim; i++) {
                            vo_volante_mega.setConcurso(i);
                            vo_volante_mega.setAposta(aposta);
                            if (!editing) {
                                dao_volante_mega.insert(vo_volante_mega);
                            } else {
                                dao_volante_mega.update(vo_volante_mega);
                            }
                        }
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.loteria_detail_container, megaListFragment)
                                .commit();

                    }
                } else {
                    ((LoteriaDetailActivity) getActivity()).exibeToast(R.string.marcar_minimo_seis);
                }

                return true;
            case R.id.action_discard:
                Log.d(TAG, "discard");
                if (editing) {
                    dao_volante_mega.delete(vo_volante_mega);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.loteria_detail_container, megaListFragment)
                            .commit();

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
