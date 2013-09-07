package br.nom.strey.maicon.loterias.megasena;

/**
 * Created by maicon on 06/09/13.
 */
public class MegasenaResultadosDAO {

    private static final String TABLE_NAME = "megasena_resultados";
    private static final String[] COLUNAS = { "concurso",
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

    public MegasenaResultadosDAO(Context ctx) {
        this.ctx = ctx;
    }

    public boolean insert(MegasenaResultadosVO vo){

        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        ContentValues ctv = new ContentValues();

        ctv.put("concurso", vo.getConcurso());
        ctv.put("data_sorteio", vo.getData_sorteio());
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
        ctv.put("data_inclusao" vo.getData_inclusao());


        result = db.insert(TABLE_NAME, null, ctv) > 0;
        db.close();

        return (result);
    }

    public boolean delete(MegasenaResultadosVO vo){
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        result = db.delete(TABLE_NAME, "concurso=?", new String[]{vo.getConcurso()+""}) > 0;
        db.close();

        return (result);
    }

    public boolean update(MegasenaResultadosVO vo){
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        ContentValues ctv = new ContentValues();

        ctv.put("concurso", vo.getConcurso());
        ctv.put("data_sorteio", vo.getData_sorteio());
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
        ctv.put("data_inclusao" vo.getData_inclusao());

        result = db.update(TABLE_NAME, ctv, "concurso=?", new String[]{vo.getConcurso() + ""}) > 0;
        db.close();

        return (result);

    }

    public MegasenaResultadosVO get(Integer concurso){
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        Cursor c = db.query(TABLE_NAME,
                COLUNAS,
                "concurso = "+concurso,
                null,
                null,
                null,
                null);

        c.moveToFirst();
        MegasenaResultadosVO vo = new MegasenaResultadosVO();

        if (c.getCount() > 0){
            vo.setConcurso(c.getInt(c.getColumnIndex("concurso")));
            vo.setBola1(c.getInt(c.getColumnIndex("bola1")));
            vo.setBola2(c.getInt(c.getColumnIndex("bola2")));
            vo.setBola3(c.getInt(c.getColumnIndex("bola3")));
            vo.setBola4(c.getInt(c.getColumnIndex("bola4")));
            vo.setBola5(c.getInt(c.getColumnIndex("bola5")));
            vo.setBola6(c.getInt(c.getColumnIndex("bola6")));
            vo.getGanhadores_6(c.getInt(c.getColumnIndex("ganhadores_6")));
            vo.getGanhadores_5(c.getInt(c.getColumnIndex("ganhadores_5")));
            vo.getGanhadores_6(c.getInt(c.getColumnIndex("ganhadores_4")));

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

    public Boolean existe(Integer concurso){
        Boolean result = false;
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();

        Cursor c = db.query(TABLE_NAME,
                COLUNAS,
                "concurso = "+concurso,
                null,
                null,
                null,
                null);

        if (c.moveToNext()){
            result = true;
        }

        c.close();
        db.close();

        return result;
    }

}
