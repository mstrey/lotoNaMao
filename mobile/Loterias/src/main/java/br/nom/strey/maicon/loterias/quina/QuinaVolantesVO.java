package br.nom.strey.maicon.loterias.quina;

import java.util.Date;

/**
 * Created by maicon on 06/09/13.
 */
public class QuinaVolantesVO {

    private Integer volante_id;
    private Integer concurso;
    private String aposta;
    private Double faixa_1;
    private Double faixa_2;
    private Double faixa_3;
    private Date data_inclusao;

    public Date getData_inclusao() {
        return data_inclusao;
    }

    public void setData_inclusao(Date data_inclusao) {
        this.data_inclusao = data_inclusao;
    }

    public Double getFaixa_3() {
        return faixa_3;
    }

    public void setFaixa_3(Double faixa_3) {
        this.faixa_3 = faixa_3;
    }

    public Double getFaixa_2() {
        return faixa_2;
    }

    public void setFaixa_2(Double faixa_2) {
        this.faixa_2 = faixa_2;
    }

    public Double getFaixa_1() {
        return faixa_1;
    }

    public void setFaixa_1(Double faixa_1) {
        this.faixa_1 = faixa_1;
    }

    public String getAposta() {
        return aposta;
    }

    public void setAposta(String aposta) {
        this.aposta = aposta;
    }

    public Integer getConcurso() {
        return concurso;
    }

    public void setConcurso(Integer concurso) {
        this.concurso = concurso;
    }

    public Integer getVolante_id() {
        return volante_id;
    }

    public void setVolante_id(Integer volante_id) {
        this.volante_id = volante_id;
    }

}
