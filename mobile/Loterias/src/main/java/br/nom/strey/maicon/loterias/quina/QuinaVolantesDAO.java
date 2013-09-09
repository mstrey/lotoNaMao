package br.nom.strey.maicon.loterias.quina;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.nom.strey.maicon.loterias.megasena.MegasenaVolantesVO;
import br.nom.strey.maicon.loterias.utils.DBHelper;

/**
 * Created by maicon on 06/09/13.
 */
public class QuinaVolantesDAO {

    private Context ctx;

    private static final String TABLE_NAME = "megasena_volantes";
    private static final String[] COLUNAS = { "volante_id",
            "concurso",
            "aposta",
            "faixa_1",
            "faixa_2",
            "faixa_3",
            "data_inclusao"
    };

    public QuinaVolantesDAO(Context ctx) {
        this.ctx = ctx;
    }

    public boolean insert(MegasenaVolantesVO vo){

        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        ContentValues ctv = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String data_inclusao = dateFormat.format(new Date());

        ctv.put("volante_id", vo.getVolante_id());
        ctv.put("concurso", vo.getConcurso());
        ctv.put("aposta", vo.getAposta());
        ctv.put("faixa_1", vo.getFaixa_1());
        ctv.put("faixa_2", vo.getFaixa_2());
        ctv.put("faixa_3", vo.getFaixa_3());
        ctv.put("data_inclusao", data_inclusao);

        boolean result = db.insert(TABLE_NAME, null, ctv) > 0;
        db.close();

        return (result);
    }

    public boolean delete(MegasenaVolantesVO vo){
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        boolean result = db.delete(TABLE_NAME, "volante_id=?", new String[]{vo.getVolante_id().toString()}) > 0;
        db.close();

        return (result);
    }

    public boolean update(MegasenaVolantesVO vo){
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        ContentValues ctv = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String data_inclusao = dateFormat.format(vo.getData_inclusao());

        ctv.put("volante_id", vo.getVolante_id());
        ctv.put("concurso", vo.getConcurso());
        ctv.put("aposta", vo.getAposta());
        ctv.put("faixa_1", vo.getFaixa_1());
        ctv.put("faixa_2", vo.getFaixa_2());
        ctv.put("faixa_3", vo.getFaixa_3());
        ctv.put("data_inclusao", data_inclusao);

        boolean result = db.update(TABLE_NAME, ctv, "volante_id=?", new String[]{vo.getVolante_id().toString()}) > 0;
        db.close();

        return (result);

    }

    public MegasenaVolantesVO get(Integer volante_id){
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

        if (c.getCount() > 0){

            vo.setVolante_id(c.getInt(c.getColumnIndex("volante_id")));
            vo.setConcurso(c.getInt(c.getColumnIndex("concurso")));
            vo.setAposta(c.getString(c.getColumnIndex("aposta")));
            vo.setFaixa_1(c.getDouble(c.getColumnIndex("faixa_1")));
            vo.setFaixa_2(c.getDouble(c.getColumnIndex("faixa_2")));
            vo.setFaixa_3(c.getDouble(c.getColumnIndex("faixa_3")));

            Date data_inclusao = new Date();

            try {
                data_inclusao = dateFormat.parse(c.getString(c.getColumnIndex("data_inclusao")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            vo.setData_inclusao(data_inclusao);

        }

        c.close();
        db.close();

        return vo;
    }

    public List<MegasenaVolantesVO> getAll(Integer concurso){
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        List<MegasenaVolantesVO> lista_volantes = new ArrayList<MegasenaVolantesVO>();

        Cursor c = db.query(TABLE_NAME,
                COLUNAS,
                "concurso = ?",
                new String[]{concurso.toString()},
                null,
                null,
                "data_inclusao asc",
                null);

        while(c.moveToNext()){
            MegasenaVolantesVO volante_vo = new MegasenaVolantesVO();

            volante_vo = get(c.getInt(c.getColumnIndex("volante_id")));
            lista_volantes.add(volante_vo);
        }

        c.close();
        db.close();

        return lista_volantes;
    }

    public Boolean existAny(Integer concurso){
        Boolean result = false;
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();

        Cursor c = db.query(TABLE_NAME,
                COLUNAS,
                "concurso = ?",
                new String[]{concurso.toString()},
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
