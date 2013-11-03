package br.nom.strey.maicon.loterias.quina;

import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maicon on 06/09/13.
 */
public class QuinaResultadosVO {

    private static final String LOGTAG = "MegasenaResultadoVO";

    private Integer concurso;
    private Date data_sorteio;
    private Integer bola1;
    private Integer bola2;
    private Integer bola3;
    private Integer bola4;
    private Integer bola5;
    private Double arrecadacao_total;
    private Integer ganhadores_5;
    private Double rateio_5;
    private Integer ganhadores_4;
    private Double rateio_4;
    private Integer ganhadores_3;
    private Double rateio_3;
    private Double acumulado_5;
    private Double estimativa_premio;
    private Double acumulado_sao_joao;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;

        return data_sorteio;
    }

    public void setData_sorteio(Date data_sorteio) {
        this.data_sorteio = data_sorteio;
    }

    public List<String> getNumerosList() {

        List<String> list_numeros = new ArrayList<String>();

        list_numeros.add(bola1 < 10 ? ("0" + bola1) : bola1.toString());
        list_numeros.add(bola2 < 10 ? ("0" + bola2) : bola2.toString());
        list_numeros.add(bola3 < 10 ? ("0" + bola3) : bola3.toString());
        list_numeros.add(bola4 < 10 ? ("0" + bola4) : bola4.toString());
        list_numeros.add(bola5 < 10 ? ("0" + bola5) : bola5.toString());

        return list_numeros;
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

    public Double getArrecadacao_total() {
        return arrecadacao_total;
    }

    public void setArrecadacao_total(Double arrecadacao_total) {
        this.arrecadacao_total = arrecadacao_total;
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

    public Integer getGanhadores_3() {
        return ganhadores_3;
    }

    public void setGanhadores_3(Integer ganhadores_3) {
        this.ganhadores_3 = ganhadores_3;
    }

    public Double getRateio_3() {
        return rateio_3;
    }

    public void setRateio_3(Double rateio_3) {
        this.rateio_3 = rateio_3;
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

    public Double getAcumulado_sao_joao() {
        return acumulado_sao_joao;
    }

    public void setAcumulado_sao_joao(Double acumulado_sao_joao) {
        this.acumulado_sao_joao = acumulado_sao_joao;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;

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

            setGanhadores_5(obj_json.getInt("ganhadores_5"));
            setGanhadores_4(obj_json.getInt("ganhadores_4"));
            setGanhadores_3(obj_json.getInt("ganhadores_3"));

            setArrecadacao_total(obj_json.getDouble("arrecadacao_total"));

            setRateio_5(obj_json.getDouble("rateio_5"));
            setRateio_4(obj_json.getDouble("rateio_4"));
            setRateio_3(obj_json.getDouble("rateio_3"));

            setAcumulado_5(obj_json.getDouble("acumulado_5"));
            setEstimativa_premio(obj_json.getDouble("estimativa_premio"));
            setAcumulado_sao_joao(obj_json.getDouble("acumulado_sao_joao"));

            Date dataSorteio = new Date();
            Date dataInclusao = new Date();

            dataSorteio = dateFormatSorteio.parse(obj_json.getString("data_sorteio"));
            setData_sorteio(dataSorteio);

            dataInclusao = dateFormatInclusao.parse(obj_json.getString("data_inclusao"));
            setData_inclusao(dataInclusao);

            Log.d(LOGTAG, "data_sorteio:" + obj_json.getString("data_sorteio") + " - data_inclusao:" + obj_json.getString("data_inclusao"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }

}
