package br.nom.strey.maicon.loterias.megasena;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.nom.strey.maicon.loterias.utils.DBHelper;

/**
 * Created by maicon on 06/09/13.
 */
public class MegasenaVolantesDAO {

    private static final String TABLE_NAME = "megasena_volantes";
    private static final String[] COLUNAS = {"volante_id",
            "concurso",
            "aposta",
            "faixa_1",
            "faixa_2",
            "faixa_3",
            "qtd_acertos",
            "conferido",
            "data_inclusao"
    };
    private Context ctx;

    public MegasenaVolantesDAO(Context ctx) {
        this.ctx = ctx;
    }

    public static Integer getMaxConc(Context ctx) {
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();

        String query = " SELECT MAX(" + COLUNAS[1] + ") concurso " +
                " from " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        Integer concurso_max = c.getInt(c.getColumnIndex(COLUNAS[1]));
        c.close();
        db.close();

        return concurso_max;
    }

    public boolean insert(MegasenaVolantesVO vo) {

        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        ContentValues ctv = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String data_inclusao = dateFormat.format(new Date());

        ctv.put("volante_id", vo.getVolanteId());
        ctv.put("concurso", vo.getConcurso());
        ctv.put("aposta", vo.getAposta());
        ctv.put("data_inclusao", data_inclusao);

        boolean result = db.insert(TABLE_NAME, null, ctv) > 0;
        db.close();

        return (result);
    }

    public boolean delete(MegasenaVolantesVO vo) {
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        boolean result = db.delete(TABLE_NAME, "volante_id=?", new String[]{vo.getVolanteId().toString()}) > 0;
        db.close();

        return (result);
    }

    public boolean update(MegasenaVolantesVO vo) {
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        ContentValues ctv = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String data_inclusao = dateFormat.format(vo.getDataInclusao());

        ctv.put("volante_id", vo.getVolanteId());
        ctv.put("concurso", vo.getConcurso());
        ctv.put("aposta", vo.getAposta());
        ctv.put("faixa_1", vo.getFaixa1());
        ctv.put("faixa_2", vo.getFaixa2());
        ctv.put("faixa_3", vo.getFaixa3());
        ctv.put("qtd_acertos", vo.getQtdAcertos());
        ctv.put("data_inclusao", data_inclusao);

        boolean result = db.update(TABLE_NAME, ctv, "volante_id=?", new String[]{vo.getVolanteId().toString()}) > 0;
        db.close();

        return (result);

    }

    public MegasenaVolantesVO get(Integer volante_id) {
        return get(volante_id, ctx);
    }

    public static MegasenaVolantesVO get(Integer volante_id, Context ctx) {
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, // table
                COLUNAS,                // columns
                "volante_id = ?",       // selection
                new String[]{volante_id.toString()},// selectionArgs
                null,                   // groupBy
                null,                   // having
                null,                   // orderBy
                null);                  // limit

        c.moveToFirst();
        MegasenaVolantesVO vo = new MegasenaVolantesVO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (c.getCount() > 0) {

            vo.setVolanteId(c.getInt(c.getColumnIndex("volante_id")));
            vo.setConcurso(c.getInt(c.getColumnIndex("concurso")));
            vo.setAposta(c.getString(c.getColumnIndex("aposta")));
            vo.setFaixa1(c.getDouble(c.getColumnIndex("faixa_1")));
            vo.setFaixa2(c.getDouble(c.getColumnIndex("faixa_2")));
            vo.setFaixa3(c.getDouble(c.getColumnIndex("faixa_3")));
            vo.setQtdAcertos(c.getInt(c.getColumnIndex("qtd_acertos")));

            Date data_inclusao = new Date();

            try {
                data_inclusao = dateFormat.parse(c.getString(c.getColumnIndex("data_inclusao")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            vo.setDataInclusao(data_inclusao);

        }

        c.close();
        db.close();

        return vo;
    }

    public List<MegasenaVolantesVO> getAll() {
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        List<MegasenaVolantesVO> lista_volantes = new ArrayList<MegasenaVolantesVO>();

        Cursor c = db.query(TABLE_NAME,
                COLUNAS,
                null,
                null,
                null,
                null,
                COLUNAS[1] + " asc",
                null);

        while (c.moveToNext()) {
            MegasenaVolantesVO volante_vo = get(c.getInt(c.getColumnIndex("volante_id")));
            lista_volantes.add(volante_vo);
        }

        c.close();
        db.close();

        return lista_volantes;
    }

    public ArrayList<Integer> getConcursosParaConferir() {
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();

        ArrayList<Integer> list_concursos = new ArrayList<Integer>();

        Cursor c = db.query(TABLE_NAME,
                new String[]{COLUNAS[1]},
                null,
                null,
                COLUNAS[1],
                null,
                COLUNAS[1] + " asc",
                null);


        while (c.moveToNext()) {
            list_concursos.add(c.getInt(c.getColumnIndex(COLUNAS[1])));
        }

        c.close();
        db.close();

        return list_concursos;
    }

}
