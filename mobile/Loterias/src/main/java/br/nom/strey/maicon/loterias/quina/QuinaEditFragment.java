package br.nom.strey.maicon.loterias.quina;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import java.util.List;

import br.nom.strey.maicon.loterias.R;
import br.nom.strey.maicon.loterias.main.LoteriaDetailActivity;

public class QuinaEditFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    private static final String TAG = "QuinaEditFragment";
    private ArrayList<String> lista_numeros_marcados = new ArrayList<String>();
    private ArrayList<String> lista_numeros_sorteados = new ArrayList<String>();
    private QuinaVolantesVO vo_volante_quina;
    private NumberPicker numberPicker;
    private View rootView;
    private TextView txt_conc_ini = null;
    private TextView txt_qtd_repetir = null;
    private Toast mToast;
    private Integer conc_ini;
    private Integer qtd_repetir;
    private Integer concurso_max;
    private Context ctx;
    private AlertDialog.Builder np_dialog_np;
    private Boolean editing = false;
    private MenuItem discard;

    public QuinaEditFragment() {

    }

    public QuinaEditFragment(QuinaVolantesVO vo_volante) {
        vo_volante_quina = vo_volante;
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

        getActivity().getActionBar().setTitle(getString(R.string.action_quina_header_edit));
        rootView = inflater.inflate(R.layout.fragment_quina_edit, container, false);

        ctx = getActivity().getBaseContext();

        txt_conc_ini = (TextView) rootView.findViewById(R.id.quina_conc_ini_ed);
        txt_qtd_repetir = (TextView) rootView.findViewById(R.id.quina_qtd_repetir_ed);

        QuinaResultadosDAO dao_resultado = new QuinaResultadosDAO(ctx);

        concurso_max = dao_resultado.getMaxConcResultado();

        if (editing) {
            txt_conc_ini.setText(vo_volante_quina.getConcurso().toString());
            setAposta(vo_volante_quina);
        } else {
            conc_ini = concurso_max + 1;
            txt_conc_ini.setText(conc_ini.toString());
        }
        conc_ini = Integer.parseInt(txt_conc_ini.getText().toString());

        if (null == vo_volante_quina) {
            vo_volante_quina = new QuinaVolantesVO();
            vo_volante_quina.setConcurso(conc_ini);
        }

        if (dao_resultado.existe(conc_ini)) {
            setSorteados(conc_ini);
        }

        txt_conc_ini.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick(ini)");

                np_dialog_np = new AlertDialog.Builder(getActivity());

                conc_ini = Integer.parseInt(txt_conc_ini.getText().toString());

                numberPicker = new NumberPicker(getActivity().getBaseContext());
                numberPicker.setFocusable(true);
                numberPicker.setFocusableInTouchMode(true);
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(concurso_max + 20);

                numberPicker.setValue(conc_ini);

                np_dialog_np.setView(numberPicker);

                np_dialog_np.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        QuinaResultadosDAO dao_resultado = new QuinaResultadosDAO(ctx);

                        if (dao_resultado.existe(conc_ini)) {
                            setSorteados(conc_ini);
                        }
                        conc_ini = ((Integer) numberPicker.getValue());
                        txt_conc_ini.setText(conc_ini.toString());

                        if (dao_resultado.existe(conc_ini)) {
                            vo_volante_quina.setConcurso(conc_ini);
                            setSorteados(conc_ini);
                        }

                    }
                });
                np_dialog_np.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                np_dialog_np.setTitle(R.string.concurso_inicial);
                np_dialog_np.show();
            }

        });

        txt_qtd_repetir.setText("1");

        txt_qtd_repetir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick(qtd_repetir)");

                np_dialog_np = new AlertDialog.Builder(getActivity());

                qtd_repetir = Integer.parseInt(txt_qtd_repetir.getText().toString());

                numberPicker = new NumberPicker(getActivity().getBaseContext());
                numberPicker.setFocusable(true);
                numberPicker.setFocusableInTouchMode(true);
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(20);

                numberPicker.setValue(qtd_repetir);

                np_dialog_np.setView(numberPicker);

                np_dialog_np.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        qtd_repetir = ((Integer) numberPicker.getValue());
                        txt_qtd_repetir.setText(qtd_repetir.toString());

                    }
                });
                np_dialog_np.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                np_dialog_np.setTitle(R.string.qtd_repetir);
                np_dialog_np.show();
            }

        });

        return rootView;

    }

    public void setNumber(View v) {
        TextView txt_num = (TextView) v;
        String num = txt_num.getText().toString();

        Log.d(TAG, "setNumber(" + txt_num.getText().toString() + ")");

        if (!lista_numeros_marcados.contains(num)) {

            if (lista_numeros_marcados.size() == 7) {
                ((LoteriaDetailActivity) getActivity()).exibeToast(R.string.marcar_maximo_sete);
            } else {
                if (lista_numeros_sorteados.contains(num)) {
                    txt_num.setBackgroundResource(R.drawable.quina_acerto);
                } else {
                    txt_num.setBackgroundResource(R.drawable.quina_marcado);
                }

                txt_num.setTextColor(getResources().getColor(R.color.quina_blue_light));

                lista_numeros_marcados.add(num);
                // TODO: incluir campo na tela para informar quantos numeros já foram marcados
            }
        } else {
            if (lista_numeros_sorteados.contains(num)) {
                txt_num.setBackgroundResource(R.drawable.sorteado);
            } else {
                txt_num.setBackgroundColor(Color.TRANSPARENT);
            }
            txt_num.setTextColor(Color.BLACK);

            lista_numeros_marcados.remove(num);
        }

    }

    public void setSorteado(View v) {
        TextView txt_num = (TextView) v;
        String num = txt_num.getText().toString();

        Log.d(TAG, "setSorteado(" + txt_num.getText().toString() + ")");

        if (!lista_numeros_sorteados.contains(num)) {

            if (!lista_numeros_marcados.contains(num)) {
                txt_num.setBackgroundResource(R.drawable.sorteado);
            } else {
                txt_num.setBackgroundResource(R.drawable.quina_acerto);
            }

            lista_numeros_sorteados.add(num);
        } else {
            if (!lista_numeros_marcados.contains(num)) {
                txt_num.setBackgroundResource(Color.TRANSPARENT);
            } else {
                txt_num.setBackgroundResource(R.drawable.quina_marcado);
            }

            lista_numeros_sorteados.remove(num);
        }

    }

    private void setSorteados(Integer concurso) {
        QuinaResultadosDAO quina_resultados_DAO = new QuinaResultadosDAO(ctx);
        QuinaResultadosVO quina_resultados_VO = quina_resultados_DAO.get(concurso);
        ArrayList<View> txt_aposta = new ArrayList<View>();

        List<String> numeros_sorteados = quina_resultados_VO.getNumerosList();

        for (String numero_sorteado : numeros_sorteados) {

            txt_aposta = new ArrayList<View>();

            LinearLayout ll_numbers = (LinearLayout) rootView.findViewById(R.id.quina_volante_numbers);
            ll_numbers.findViewsWithText(txt_aposta, numero_sorteado, View.FIND_VIEWS_WITH_TEXT);

            setSorteado(txt_aposta.get(0));

        }

    }

    private void setAposta(QuinaVolantesVO vo_volante_quina) {

        String aposta = vo_volante_quina.getAposta();
        String numero = "";
        ArrayList<View> txt_aposta = new ArrayList<View>();

        LinearLayout ll_numbers = (LinearLayout) rootView.findViewById(R.id.quina_volante_numbers);

        for (int i = 0; i < aposta.length(); i = i + 2) {
            numero = aposta.substring(i, i + 2);
            txt_aposta = new ArrayList<View>();

            ll_numbers.findViewsWithText(txt_aposta, numero, View.FIND_VIEWS_WITH_TEXT);

            setNumber(txt_aposta.get(0));
        }

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        discard = menu.findItem(R.id.action_discard);
        if (!editing) {
            discard.setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
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
        QuinaVolantesDAO dao_volante_quina = new QuinaVolantesDAO(getActivity().getBaseContext());
        QuinaListFragment quinaListFragment = new QuinaListFragment();
        if (!editing) {
            vo_volante_quina = new QuinaVolantesVO();
        }

        switch (item.getItemId()) {
            case R.id.action_save:
                Log.d(TAG, "save");

                if (lista_numeros_marcados.size() >= 5) {
                    if (lista_numeros_marcados.size() > 7) {
                        ((LoteriaDetailActivity) getActivity()).exibeToast(R.string.marcar_maximo_sete);
                    } else {
                        conc_ini = Integer.parseInt(txt_conc_ini.getText().toString());
                        qtd_repetir = Integer.parseInt(txt_qtd_repetir.getText().toString());

                        String aposta = "";

                        Collections.sort(lista_numeros_marcados);

                        for (int i = 0; i < lista_numeros_marcados.size(); i++) {
                            aposta += lista_numeros_marcados.get(i);
                        }

                        for (int i = conc_ini; i < (conc_ini + qtd_repetir); i++) {
                            if (i != conc_ini) {
                                vo_volante_quina = new QuinaVolantesVO();
                                editing = false;
                            }
                            vo_volante_quina.setConcurso(i);
                            vo_volante_quina.setAposta(aposta);
                            if (!editing) {
                                dao_volante_quina.insert(vo_volante_quina);
                            } else {
                                dao_volante_quina.update(vo_volante_quina);
                            }
                        }
                        LoteriaDetailActivity activity = (LoteriaDetailActivity) getActivity();
                        activity.listQuinaFragment();

                    }
                } else {
                    ((LoteriaDetailActivity) getActivity()).exibeToast(R.string.marcar_minimo_cinco);
                }

                return true;
            case R.id.action_discard:
                Log.d(TAG, "discard");
                if (editing) {
                    dao_volante_quina.delete(vo_volante_quina);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.loteria_detail_container, quinaListFragment)
                            .commit();

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
