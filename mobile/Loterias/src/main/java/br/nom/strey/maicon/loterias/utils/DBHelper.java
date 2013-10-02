package br.nom.strey.maicon.loterias.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static String dbName = "loterias.db";
    private final static int DB_VERSION = 1;

    // scripts para criação das tabelas da mega-sena
    private static String dbSQL_megasena_resultados = "CREATE TABLE megasena_resultados ("
            + "  concurso INTEGER PRIMARY KEY ,"
            + "  data_sorteio DATE ,"
            + "  bola1 INTEGER ,"
            + "  bola2 INTEGER ,"
            + "  bola3 INTEGER ,"
            + "  bola4 INTEGER ,"
            + "  bola5 INTEGER ,"
            + "  bola6 INTEGER ,"
            + "  arrecadacao_total DECIMAL(11,2) ,"
            + "  ganhadores_6 INTEGER ,"
            + "  rateio_6 DECIMAL(11,2) ,"
            + "  ganhadores_5 INTEGER ,"
            + "  rateio_5 DECIMAL(11,2) ,"
            + "  ganhadores_4 INTEGER ,"
            + "  rateio_4 DECIMAL(11,2) ,"
            + "  acumulado_5 DECIMAL(11,2) ,"
            + "  estimativa_premio DECIMAL(11,2) ,"
            + "  acumulado_virada DECIMAL(11,2) ,"
            + "  local VARCHAR(255),"
            + "  local_gps VARCHAR(45),"
            + "  data_inclusao DATETIME "
            + "  );";
    private static String dbSQL_megasena_resultados_index = "CREATE INDEX mega_result_concurso ON megasena_resultados(concurso); ";

    private static String dbSQL_megasena_volantes = "CREATE TABLE megasena_volantes ("
            + "  volante_id INTEGER PRIMARY KEY,"
            + "  aposta VARCHAR(30) ,"
            + "  faixa_1 INTEGER ,"
            + "  faixa_2 INTEGER ,"
            + "  faixa_3 INTEGER ,"
            + "  qtd_acertos INTEGER ,"
            + "  conferido BOOLEAN ,"
            + "  data_inclusao DATETIME ,"
            + "  concurso INTEGER ,"
            + "  FOREIGN KEY(concurso) REFERENCES megasena_resultados(concurso)"
            + "  );";

    private static String dbSQL_megasena_volantes_index_id = "CREATE INDEX mega_volantes_id ON megasena_volantes(volante_id); ";
    private static String dbSQL_megasena_volantes_index_concurso = "CREATE INDEX mega_volantes_conc ON megasena_volantes(concurso); ";


    // scripts para criação das tabelas da quina
    private static String dbSQL_quina_resultados = "CREATE TABLE quina_resultados ("
            + "  concurso INTEGER PRIMARY KEY ,"
            + "  data_sorteio DATE ,"
            + "  bola1 INTEGER ,"
            + "  bola2 INTEGER ,"
            + "  bola3 INTEGER ,"
            + "  bola4 INTEGER ,"
            + "  bola5 INTEGER ,"
            + "  arrecadacao_total DECIMAL(11,2) ,"
            + "  ganhadores_5 INTEGER ,"
            + "  rateio_5 DECIMAL(11,2) ,"
            + "  ganhadores_4 INTEGER ,"
            + "  rateio_4 DECIMAL(11,2) ,"
            + "  ganhadores_3 INTEGER ,"
            + "  rateio_3 DECIMAL(11,2) ,"
            + "  acumulado_5 DECIMAL(11,2) ,"
            + "  estimativa_premio DECIMAL(11,2) ,"
            + "  acumulado_sao_joao DECIMAL(11,2) ,"
            + "  local VARCHAR(255) ,"
            + "  local_gps VARCHAR(45) ,"
            + "  data_inclusao DATETIME "
            + "  );";

    private static String dbSQL_quina_resultados_index = "CREATE INDEX quina_result_conc ON quina_resultados(concurso); ";

    private static String dbSQL_quina_volantes = "CREATE TABLE quina_volantes ("
            + "  volante_id INTEGER PRIMARY KEY,"
            + "  aposta VARCHAR(14) ,"
            + "  faixa_1 DECIMAL(11,2) ,"
            + "  faixa_2 DECIMAL(11,2) ,"
            + "  faixa_3 DECIMAL(11,2) ,"
            + "  qtd_acertos INTEGER ,"
            + "  conferido BOOLEAN ,"
            + "  data_inclusao DATETIME ,"
            + "  concurso INTEGER ,"
            + "  FOREIGN KEY(concurso) REFERENCES quina_resultados(concurso)"
            + "  );";

    private static String dbSQL_quina_volantes_index_id = "CREATE INDEX quina_volantes_id ON quina_volantes(volante_id); ";
    private static String dbSQL_quina_volantes_index_concurso = "CREATE INDEX quina_volantes_conc ON quina_volantes(concurso); ";

    public DBHelper(Context context) {
        super(context, dbName, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //mega-sena
        db.execSQL(dbSQL_megasena_resultados);
        db.execSQL(dbSQL_megasena_resultados_index);
        db.execSQL(dbSQL_megasena_volantes);
        db.execSQL(dbSQL_megasena_volantes_index_id);
        db.execSQL(dbSQL_megasena_volantes_index_concurso);

        //quina
        db.execSQL(dbSQL_quina_resultados);
        db.execSQL(dbSQL_quina_resultados_index);
        db.execSQL(dbSQL_quina_volantes);
        db.execSQL(dbSQL_quina_volantes_index_id);
        db.execSQL(dbSQL_quina_volantes_index_concurso);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            //TODO create tables for LotoFacil, LotoMania, DuplaSena and TimeMania
        }

    }

}
