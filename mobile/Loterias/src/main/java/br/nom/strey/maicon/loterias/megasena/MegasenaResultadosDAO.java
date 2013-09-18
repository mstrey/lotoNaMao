package br.nom.strey.maicon.loterias.megasena;


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
import java.util.Date;

import br.nom.strey.maicon.loterias.main.Categories;
import br.nom.strey.maicon.loterias.utils.DBHelper;
import br.nom.strey.maicon.loterias.utils.WebService;

/**
 * Created by maicon on 06/09/13.
 */
public class MegasenaResultadosDAO {

    private static final String LOGTAG = "MegasenaResultadoDAO";
    private static final String MAX_CONCURSO = "max_conc";
    private static final String TABLE_NAME = "megasena_resultados";
    private static final String[] COLUNAS = {"concurso",
            "data_sorteio",
            "bola1",
            "bola2",
            "bola3",
            "bola4",
            "bola5",
            "bola6",
            "arrecadacao_total",
            "ganhadores_6",
            "rateio_6",
            "ganhadores_5",
            "rateio_5",
            "ganhadores_4",
            "rateio_4",
            "acumulado_5",
            "estimativa_premio",
            "acumulado_virada",
            "local",
            "local_gps",
            "data_inclusao"
    };
    private static Integer concurso_max_local_volantes;
    private static Integer concurso_max_local_resultados;
    private static Integer concurso_max_remote;
    private static Integer concurso_max;
    private Context ctx;

    public MegasenaResultadosDAO(Context ctx) {

        this.ctx = ctx;
        new GetResultado().execute();

    }

    public boolean insert(MegasenaResultadosVO vo) {

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
        ctv.put("bola6", vo.getBola6());
        ctv.put("arrecadacao_total", vo.getArrecadacao_total());
        ctv.put("ganhadores_6", vo.getGanhadores_6());
        ctv.put("rateio_6", vo.getRateio_6());
        ctv.put("ganhadores_5", vo.getGanhadores_5());
        ctv.put("rateio_5", vo.getRateio_5());
        ctv.put("ganhadores_4", vo.getGanhadores_4());
        ctv.put("rateio_4", vo.getRateio_4());
        ctv.put("acumulado_5", vo.getAcumulado_5());
        ctv.put("estimativa_premio", vo.getEstimativa_premio());
        ctv.put("acumulado_virada", vo.getAcumulado_virada());
        ctv.put("local", vo.getLocal());
        ctv.put("local_gps", vo.getLocal_gps());
        ctv.put("data_inclusao", data_inclusao);

        boolean result = db.insert(TABLE_NAME, null, ctv) > 0;
        db.close();

        return (result);
    }

    public boolean delete(MegasenaResultadosVO vo) {
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        boolean result = db.delete(TABLE_NAME, "concurso=?", new String[]{vo.getConcurso().toString()}) > 0;
        db.close();

        return (result);
    }

    public boolean update(MegasenaResultadosVO vo) {
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
        ctv.put("bola6", vo.getBola6());
        ctv.put("arrecadacao_total", vo.getArrecadacao_total());
        ctv.put("ganhadores_6", vo.getGanhadores_6());
        ctv.put("rateio_6", vo.getRateio_6());
        ctv.put("ganhadores_5", vo.getGanhadores_5());
        ctv.put("rateio_5", vo.getRateio_5());
        ctv.put("ganhadores_4", vo.getGanhadores_4());
        ctv.put("rateio_4", vo.getRateio_4());
        ctv.put("acumulado_5", vo.getAcumulado_5());
        ctv.put("estimativa_premio", vo.getEstimativa_premio());
        ctv.put("acumulado_virada", vo.getAcumulado_virada());
        ctv.put("local", vo.getLocal());
        ctv.put("local_gps", vo.getLocal_gps());
        ctv.put("data_inclusao", data_inclusao);

        boolean result = db.update(TABLE_NAME, ctv, "concurso=?", new String[]{vo.getConcurso() + ""}) > 0;
        db.close();

        return (result);

    }

    public MegasenaResultadosVO get(Integer concurso) {
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
        MegasenaResultadosVO vo = new MegasenaResultadosVO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (c.getCount() > 0) {
            vo.setConcurso(c.getInt(c.getColumnIndex("concurso")));
            vo.setBola1(c.getInt(c.getColumnIndex("bola1")));
            vo.setBola2(c.getInt(c.getColumnIndex("bola2")));
            vo.setBola3(c.getInt(c.getColumnIndex("bola3")));
            vo.setBola4(c.getInt(c.getColumnIndex("bola4")));
            vo.setBola5(c.getInt(c.getColumnIndex("bola5")));
            vo.setBola6(c.getInt(c.getColumnIndex("bola6")));
            vo.setGanhadores_6(c.getInt(c.getColumnIndex("ganhadores_6")));
            vo.setGanhadores_5(c.getInt(c.getColumnIndex("ganhadores_5")));
            vo.setGanhadores_4(c.getInt(c.getColumnIndex("ganhadores_4")));

            vo.setArrecadacao_total(c.getDouble(c.getColumnIndex("arrecadacao_total")));
            vo.setRateio_6(c.getDouble(c.getColumnIndex("rateio_6")));
            vo.setRateio_5(c.getDouble(c.getColumnIndex("rateio_5")));
            vo.setRateio_4(c.getDouble(c.getColumnIndex("rateio_4")));
            vo.setAcumulado_5(c.getDouble(c.getColumnIndex("acumulado_5")));
            vo.setEstimativa_premio(c.getDouble(c.getColumnIndex("estimativa_premio")));
            vo.setAcumulado_virada(c.getDouble(c.getColumnIndex("acumulado_virada")));

            vo.setLocal(c.getString(c.getColumnIndex("local")));
            vo.setLocal_gps(c.getString(c.getColumnIndex("local_gps")));

            Date data_sorteio = new Date();
            Date data_inclusao = new Date();

            try {
                data_sorteio = dateFormat.parse(c.getString(c.getColumnIndex("data_sorteio")));
                data_inclusao = dateFormat.parse(c.getString(c.getColumnIndex("data_inclusao")));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            vo.setData_sorteio(data_sorteio);
            vo.setData_inclusao(data_inclusao);

        }

        c.close();
        db.close();

        return vo;
    }

    public Boolean exist(Integer concurso) {
        Boolean result = false;
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();

        Cursor c = db.query(TABLE_NAME,
                COLUNAS,
                "concurso = ?",
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

    public Integer getMaxConcResultado() {
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();

        String query =  " SELECT MAX(" + COLUNAS[0] + ") concurso " +
                " from " + TABLE_NAME + ";";

        Log.d(LOGTAG,query);
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        concurso_max_local_resultados = c.getInt(c.getColumnIndex("concurso"));

        c.close();

        db.close();

        concurso_max_remote = 0;
        Log.d(LOGTAG, "max_remote: " + concurso_max_remote);
        Log.d(LOGTAG, "max_local_resultados: "+concurso_max_local_resultados);
        Log.d(LOGTAG, "max_local_volantes: "+concurso_max_local_volantes);
        concurso_max = 1;

        concurso_max_local_volantes = MegasenaVolantesDAO.getMaxConc(ctx);

        if (concurso_max_local_resultados > concurso_max_remote) {
            concurso_max = concurso_max_local_resultados;
        } else {
            MegasenaResultadosVO vo_resultado = new MegasenaResultadosVO();

            vo_resultado.setConcurso(concurso_max);
            insert(vo_resultado);

            concurso_max = concurso_max_remote;
        }

        if (concurso_max_local_volantes > concurso_max) {
            concurso_max = concurso_max_local_volantes;
        }
        Log.d(LOGTAG, "max: "+concurso_max);
        return concurso_max;
    }

    private class GetResultado extends AsyncTask<Void, Void, Void> {

        public GetResultado() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (WebService.connected(ctx) != WebService.DISCONNECTED) {
                StringBuffer strUrl = new StringBuffer("http://maicon.strey.nom.br/");
                strUrl.append("loto/");
                strUrl.append("getResults.php");
                strUrl.append("?loto=");
                strUrl.append(URLEncoder.encode(Categories.MEGASENA));
                strUrl.append("&concurso=");
                strUrl.append(concurso_max_remote);

                try {

                    URL url = new URL(strUrl.toString());
                    URLConnection con = url.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String str_json = in.readLine();
                    Log.e(LOGTAG, "str_json: " + str_json);

                    JSONObject obj_json = new JSONObject(str_json);

                    if (concurso_max_remote > 0) {
                        MegasenaResultadosVO vo_resultado = new MegasenaResultadosVO();
                        vo_resultado.setJson(obj_json);
                        insert(vo_resultado);

                    } else {
                        concurso_max_remote = obj_json.getInt(MAX_CONCURSO);
                        Log.e(LOGTAG, MAX_CONCURSO + "_remote: " + concurso_max_remote);
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    concurso_max_remote = 1;
                }
            }
            return null;
        }
    }

}
