package br.nom.strey.maicon.loterias.megasena;

import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by maicon on 06/09/13.
 */
public class MegasenaResultadosVO {

    private static final String LOGTAG = "MegasenaResultadoVO";

    private Integer concurso;
    private Date data_sorteio;
    private Integer bola1;
    private Integer bola2;
    private Integer bola3;
    private Integer bola4;
    private Integer bola5;
    private Integer bola6;
    private Double arrecadacao_total;
    private Integer ganhadores_6;
    private Double rateio_6;
    private Integer ganhadores_5;
    private Double rateio_5;
    private Integer ganhadores_4;
    private Double rateio_4;
    private Double acumulado_5;
    private Double estimativa_premio;
    private Double acumulado_virada;
    private String local;
    private String local_gps;
    private Date data_inclusao;

    public Integer getConcurso() {
        return concurso;
    }

    public void setConcurso(Integer concurso) {
        this.concurso = concurso;
    }

    public Date getData_sorteio() {
        try {
            if (data_sorteio == null) {
                SimpleDateFormat dateFormatSorteio = new SimpleDateFormat("yyyy-MM-dd");
                data_sorteio = dateFormatSorteio.parse("1996-03-11");
            }
        } catch (Exception e){
            e.printStackTrace();
        };

        return data_sorteio;
    }

    public void setData_sorteio(Date data_sorteio) {
        this.data_sorteio = data_sorteio;
    }

    public Integer getBola1() {
        return bola1;
    }

    public void setBola1(Integer bola1) {
        this.bola1 = bola1;
    }

    public Integer getBola2() {
        return bola2;
    }

    public void setBola2(Integer bola2) {
        this.bola2 = bola2;
    }

    public Integer getBola3() {
        return bola3;
    }

    public void setBola3(Integer bola3) {
        this.bola3 = bola3;
    }

    public Integer getBola4() {
        return bola4;
    }

    public void setBola4(Integer bola4) {
        this.bola4 = bola4;
    }

    public Integer getBola5() {
        return bola5;
    }

    public void setBola5(Integer bola5) {
        this.bola5 = bola5;
    }

    public Integer getBola6() {
        return bola6;
    }

    public void setBola6(Integer bola6) {
        this.bola6 = bola6;
    }

    public Double getArrecadacao_total() {
        return arrecadacao_total;
    }

    public void setArrecadacao_total(Double arrecadacao_total) {
        this.arrecadacao_total = arrecadacao_total;
    }

    public Integer getGanhadores_6() {
        return ganhadores_6;
    }

    public void setGanhadores_6(Integer ganhadores_6) {
        this.ganhadores_6 = ganhadores_6;
    }

    public Double getRateio_6() {
        return rateio_6;
    }

    public void setRateio_6(Double rateio_6) {
        this.rateio_6 = rateio_6;
    }

    public Integer getGanhadores_5() {
        return ganhadores_5;
    }

    public void setGanhadores_5(Integer ganhadores_5) {
        this.ganhadores_5 = ganhadores_5;
    }

    public Double getRateio_5() {
        return rateio_5;
    }

    public void setRateio_5(Double rateio_5) {
        this.rateio_5 = rateio_5;
    }

    public Integer getGanhadores_4() {
        return ganhadores_4;
    }

    public void setGanhadores_4(Integer ganhadores_4) {
        this.ganhadores_4 = ganhadores_4;
    }

    public Double getRateio_4() {
        return rateio_4;
    }

    public void setRateio_4(Double rateio_4) {
        this.rateio_4 = rateio_4;
    }

    public Double getAcumulado_5() {
        return acumulado_5;
    }

    public void setAcumulado_5(Double acumulado_5) {
        this.acumulado_5 = acumulado_5;
    }

    public Double getEstimativa_premio() {
        return estimativa_premio;
    }

    public void setEstimativa_premio(Double estimativa_premio) {
        this.estimativa_premio = estimativa_premio;
    }

    public Double getAcumulado_virada() {
        return acumulado_virada;
    }

    public void setAcumulado_virada(Double acumulado_virada) {
        this.acumulado_virada = acumulado_virada;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLocal_gps() {
        return local_gps;
    }

    public void setLocal_gps(String local_gps) {
        this.local_gps = local_gps;
    }

    public Date getData_inclusao() {
        try {
            if (data_inclusao == null) {
                SimpleDateFormat dateFormatInclusao = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                data_inclusao = dateFormatInclusao.parse("2013-01-01 00:00:00");
            }
        } catch (Exception e){
            e.printStackTrace();
        };

        return data_inclusao;
    }

    public void setData_inclusao(Date data_inclusao) {
        this.data_inclusao = data_inclusao;
    }

    public void setJson(JSONObject obj_json) {

        SimpleDateFormat dateFormatSorteio = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatInclusao = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            setConcurso(obj_json.getInt("concurso"));

            setBola1(obj_json.getInt("bola1"));
            setBola2(obj_json.getInt("bola2"));
            setBola3(obj_json.getInt("bola3"));
            setBola4(obj_json.getInt("bola4"));
            setBola5(obj_json.getInt("bola5"));
            setBola6(obj_json.getInt("bola6"));

            setGanhadores_6(obj_json.getInt("ganhadores_6"));
            setGanhadores_5(obj_json.getInt("ganhadores_5"));
            setGanhadores_4(obj_json.getInt("ganhadores_4"));

            setArrecadacao_total(obj_json.getDouble("arrecadacao_total"));

            setRateio_6(obj_json.getDouble("rateio_6"));
            setRateio_5(obj_json.getDouble("rateio_5"));
            setRateio_4(obj_json.getDouble("rateio_4"));

            setAcumulado_5(obj_json.getDouble("acumulado_5"));
            setEstimativa_premio(obj_json.getDouble("estimativa_premio"));
            setAcumulado_virada(obj_json.getDouble("acumulado_virada"));

            Date dataSorteio = new Date();
            Date dataInclusao = new Date();

            dataSorteio = dateFormatSorteio.parse(obj_json.getString("data_sorteio"));
            setData_sorteio(dataSorteio);

            dataInclusao = dateFormatInclusao.parse(obj_json.getString("data_inclusao"));
            setData_inclusao(dataInclusao);

            Log.d(LOGTAG,"data_sorteio:"+obj_json.getString("data_sorteio")+" - data_inclusao:"+obj_json.getString("data_inclusao"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }

}
