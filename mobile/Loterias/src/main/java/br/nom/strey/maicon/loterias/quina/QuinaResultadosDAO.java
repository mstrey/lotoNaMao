package br.nom.strey.maicon.loterias.quina;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.nom.strey.maicon.loterias.main.Categories;
import br.nom.strey.maicon.loterias.utils.DBHelper;
import br.nom.strey.maicon.loterias.utils.WebService;


/**
 * Created by maicon on 06/09/13.
 */
public class QuinaResultadosDAO {

    private static final String LOGTAG = "QuinaResultadoDAO";
    private static final String MAX_CONCURSO = "max_conc";
    private static final String TABLE_NAME = "quina_resultados";
    private static final String[] COLUNAS = {"concurso",
            "data_sorteio",
            "bola1",
            "bola2",
            "bola3",
            "bola4",
            "bola5",
            "arrecadacao_total",
            "ganhadores_5",
            "rateio_5",
            "ganhadores_4",
            "rateio_4",
            "ganhadores_3",
            "rateio_3",
            "acumulado_5",
            "estimativa_premio",
            "acumulado_sao_joao",
            "local",
            "local_gps",
            "data_inclusao"
    };

    private static Integer concurso_max_local_volantes;
    private static Integer concurso_max_local_resultados;
    private static Integer concurso_max_remote_resultados;
    private static Integer concurso_max;
    private Context ctx;

    public QuinaResultadosDAO(Context ctx) {

        this.ctx = ctx;
        new GetResultadosRemote().execute();

    }

    public boolean insert(QuinaResultadosVO vo) {

        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        ContentValues ctv = new ContentValues();
        SimpleDateFormat dateFormatSorteio = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatInclusao = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String data_sorteio = dateFormatSorteio.format(vo.getData_sorteio());
        String data_inclusao = dateFormatInclusao.format(vo.getData_inclusao());

        ctv.put("concurso", vo.getConcurso());
        ctv.put("data_sorteio", data_sorteio);
        ctv.put("bola1", vo.getBola1());
        ctv.put("bola2", vo.getBola2());
        ctv.put("bola3", vo.getBola3());
        ctv.put("bola4", vo.getBola4());
        ctv.put("bola5", vo.getBola5());
        ctv.put("arrecadacao_total", vo.getArrecadacao_total());
        ctv.put("ganhadores_5", vo.getGanhadores_5());
        ctv.put("rateio_5", vo.getRateio_5());
        ctv.put("ganhadores_4", vo.getGanhadores_4());
        ctv.put("rateio_4", vo.getRateio_4());
        ctv.put("ganhadores_3", vo.getGanhadores_3());
        ctv.put("rateio_3", vo.getRateio_3());
        ctv.put("acumulado_5", vo.getAcumulado_5());
        ctv.put("estimativa_premio", vo.getEstimativa_premio());
        ctv.put("acumulado_sao_joao", vo.getAcumulado_sao_joao());
        ctv.put("local", vo.getLocal());
        ctv.put("local_gps", vo.getLocal_gps());
        ctv.put("data_inclusao", data_inclusao);

        boolean result = db.insert(TABLE_NAME, null, ctv) > 0;
        db.close();

        return (result);
    }

    public boolean update(QuinaResultadosVO vo) {
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        ContentValues ctv = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String data_sorteio = dateFormat.format(vo.getData_sorteio());
        String data_inclusao = dateFormat.format(vo.getData_inclusao());

        ctv.put("concurso", vo.getConcurso());
        ctv.put("data_sorteio", data_sorteio);
        ctv.put("bola1", vo.getBola1());
        ctv.put("bola2", vo.getBola2());
        ctv.put("bola3", vo.getBola3());
        ctv.put("bola4", vo.getBola4());
        ctv.put("bola5", vo.getBola5());
        ctv.put("arrecadacao_total", vo.getArrecadacao_total());
        ctv.put("ganhadores_5", vo.getGanhadores_5());
        ctv.put("rateio_5", vo.getRateio_5());
        ctv.put("ganhadores_4", vo.getGanhadores_4());
        ctv.put("rateio_4", vo.getRateio_4());
        ctv.put("ganhadores_3", vo.getGanhadores_3());
        ctv.put("rateio_3", vo.getRateio_3());
        ctv.put("acumulado_5", vo.getAcumulado_5());
        ctv.put("estimativa_premio", vo.getEstimativa_premio());
        ctv.put("acumulado_sao_joao", vo.getAcumulado_sao_joao());
        ctv.put("local", vo.getLocal());
        ctv.put("local_gps", vo.getLocal_gps());
        ctv.put("data_inclusao", data_inclusao);

        boolean result = db.update(TABLE_NAME, ctv, "concurso=?", new String[]{vo.getConcurso() + ""}) > 0;
        db.close();

        return (result);

    }

    public QuinaResultadosVO get(Integer concurso) {
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, // table
                COLUNAS,                // columns
                "concurso = ?",         // selection
                new String[]{concurso.toString()},  // selectionArgs
                null,                   // groupBy
                null,                   // having
                null,                   // orderBy
                null);                  // limit

        c.moveToFirst();
        QuinaResultadosVO vo = new QuinaResultadosVO();
        SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatDayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (c.getCount() > 0) {
            vo.setConcurso(c.getInt(c.getColumnIndex("concurso")));
            vo.setBola1(c.getInt(c.getColumnIndex("bola1")));
            vo.setBola2(c.getInt(c.getColumnIndex("bola2")));
            vo.setBola3(c.getInt(c.getColumnIndex("bola3")));
            vo.setBola4(c.getInt(c.getColumnIndex("bola4")));
            vo.setBola5(c.getInt(c.getColumnIndex("bola5")));
            vo.setGanhadores_5(c.getInt(c.getColumnIndex("ganhadores_5")));
            vo.setGanhadores_4(c.getInt(c.getColumnIndex("ganhadores_4")));
            vo.setGanhadores_3(c.getInt(c.getColumnIndex("ganhadores_3")));

            vo.setArrecadacao_total(c.getDouble(c.getColumnIndex("arrecadacao_total")));
            vo.setRateio_5(c.getDouble(c.getColumnIndex("rateio_5")));
            vo.setRateio_4(c.getDouble(c.getColumnIndex("rateio_4")));
            vo.setRateio_3(c.getDouble(c.getColumnIndex("rateio_3")));
            vo.setAcumulado_5(c.getDouble(c.getColumnIndex("acumulado_5")));
            vo.setEstimativa_premio(c.getDouble(c.getColumnIndex("estimativa_premio")));
            vo.setAcumulado_sao_joao(c.getDouble(c.getColumnIndex("acumulado_sao_joao")));

            vo.setLocal(c.getString(c.getColumnIndex("local")));
            vo.setLocal_gps(c.getString(c.getColumnIndex("local_gps")));

            Date data_sorteio = new Date();
            Date data_inclusao = new Date();

            try {
                data_sorteio = dateFormatDay.parse(c.getString(c.getColumnIndex("data_sorteio")));
                data_inclusao = dateFormatDayTime.parse(c.getString(c.getColumnIndex("data_inclusao")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            vo.setData_sorteio(data_sorteio);
            vo.setData_inclusao(data_inclusao);

        }

        c.close();
        db.close();

        return vo;
    }

    public Boolean existeResultado(Integer concurso) {
        Boolean result = false;
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();

        Cursor c = db.query(TABLE_NAME,
                COLUNAS,
                COLUNAS[0] + " = ? AND " + COLUNAS[7] + " > 0",
                new String[]{concurso.toString()},
                null,
                null,
                null);

        if (c.moveToNext()) {
            result = true;
        }

        c.close();
        db.close();

        return result;
    }

    public Boolean existe(Integer concurso) {
        Boolean result = false;
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();

        Cursor c = db.query(TABLE_NAME,
                COLUNAS,
                COLUNAS[0] + " = ? ",
                new String[]{concurso.toString()},
                null,
                null,
                null);

        if (c.moveToNext()) {
            result = true;
        }

        c.close();
        db.close();

        return result;
    }

    public void getMaxConcRemote() {
        ArrayList<Integer> concursos = new ArrayList<Integer>();
        concursos.add(0);
        GetResultadosRemote get_remote = new GetResultadosRemote();
        get_remote.execute(concursos);
    }

    public Integer getMaxConcResultado() {

        getMaxConcRemote();

        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();

        String query = " SELECT MAX(" + COLUNAS[0] + ") " + COLUNAS[0] +
                " FROM " + TABLE_NAME +
                " WHERE " + COLUNAS[7] + " > 0;";

        Log.d(LOGTAG, query);
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        concurso_max_local_resultados = c.getInt(c.getColumnIndex(COLUNAS[0]));

        c.close();

        db.close();

        concurso_max_local_volantes = QuinaVolantesDAO.getMaxConc(ctx);

        Log.d(LOGTAG, "max_remote_resultado: " + concurso_max_remote_resultados);
        Log.d(LOGTAG, "max_local_resultado: " + concurso_max_local_resultados);
        Log.d(LOGTAG, "max_local_volante: " + concurso_max_local_volantes);
        concurso_max = 1;

        if (concurso_max_local_resultados > concurso_max_remote_resultados) {
            concurso_max = concurso_max_local_resultados;
        } else {
            QuinaResultadosVO vo_resultado = new QuinaResultadosVO();

            concurso_max = concurso_max_remote_resultados;
            vo_resultado.setConcurso(concurso_max);
            if (!existe(concurso_max)) {
                insert(vo_resultado);
            } else {
                update(vo_resultado);
            }

        }

        if (concurso_max_local_volantes > concurso_max) {
            concurso_max = concurso_max_local_volantes;
        }
        Log.d(LOGTAG, "max: " + concurso_max);
        return concurso_max;
    }

    private class GetResultadosRemote extends AsyncTask<ArrayList<Integer>, Void, Boolean> {

        public GetResultadosRemote() {

        }

        @Override
        protected void onPreExecute() {
            concurso_max_remote_resultados = 0;
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }

        @Override
        protected Boolean doInBackground(ArrayList<Integer>... concursos) {

            if (concursos.length > 0) {
                ArrayList<Integer> lista_concursos = concursos[0];

                for (Integer concurso : lista_concursos) {
                    if (!existeResultado(concurso)) {
                        if (WebService.connected(ctx).equals(WebService.DISCONNECTED)) {
                            StringBuffer strUrl = new StringBuffer("http://maicon.strey.nom.br/");
                            strUrl.append("loto/");
                            strUrl.append("getResults.php");
                            strUrl.append("?loto=");
                            strUrl.append(URLEncoder.encode(Categories.QUINA));
                            strUrl.append("&concurso=");
                            strUrl.append(concurso);
                            try {

                                URL url = new URL(strUrl.toString());
                                URLConnection con = url.openConnection();
                                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                                String str_json = in.readLine();
                                Log.d(LOGTAG, "str_json: " + str_json);

                                JSONObject obj_json = new JSONObject(str_json);

                                if (concurso > 0) {
                                    QuinaResultadosVO vo_resultado = new QuinaResultadosVO();
                                    vo_resultado.setJson(obj_json);

                                    if (existe(vo_resultado.getConcurso())) {
                                        update(vo_resultado);
                                    } else {
                                        insert(vo_resultado);
                                    }

                                } else {
                                    concurso_max_remote_resultados = obj_json.getInt(MAX_CONCURSO);
                                    Log.d(LOGTAG, MAX_CONCURSO + "_remote: " + concurso_max_remote_resultados);
                                }

                            } catch (Exception e) {
                                concurso_max_remote_resultados = 1;
                                e.printStackTrace();
                            }
                        } else {
                            // TODO: exibir toast iformando que nenhuma conexão foi identificada
                        }
                    }
                }

            }

            return null;
        }
    }

}
