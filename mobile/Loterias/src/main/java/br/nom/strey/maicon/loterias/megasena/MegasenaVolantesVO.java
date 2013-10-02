package br.nom.strey.maicon.loterias.megasena;

import java.util.Date;

/**
 * Created by maicon on 06/09/13.
 */
public class MegasenaVolantesVO {

    private Integer volante_id;
    private Integer concurso;
    private String aposta;
    private Double faixa_1;
    private Double faixa_2;
    private Double faixa_3;
    private Integer qtd_acertos;
    private Boolean conferido;
    private Boolean arquivado;
    private Date data_inclusao;

    public Integer getQtdAcertos() {
        return qtd_acertos;
    }

    public void setQtdAcertos(Integer qtd_acertos) {
        this.qtd_acertos = qtd_acertos;
    }

    public Boolean getConferido() {
        return conferido;
    }

    public void setConferido(Integer conferido) {
        if (conferido == 0) {
            this.conferido = false;
        } else {
            this.conferido = true;
        }
    }

    public void setConferido(Boolean conferido) {
        this.conferido = conferido;
    }

    public Boolean getArquivado() {
        return arquivado;
    }

    public void setArquivado(Integer arquivado) {
        if (arquivado == 0) {
            this.arquivado = false;
        } else {
            this.arquivado = true;
        }
    }

    public void setArquivado(Boolean arquivado) {
        this.arquivado = arquivado;
    }

    public Date getDataInclusao() {
        return data_inclusao;
    }

    public void setDataInclusao(Date data_inclusao) {
        this.data_inclusao = data_inclusao;
    }

    public Double getFaixa3() {
        return faixa_3;
    }

    public void setFaixa3(Double faixa_3) {
        this.faixa_3 = faixa_3;
    }

    public Double getFaixa2() {
        return faixa_2;
    }

    public void setFaixa2(Double faixa_2) {
        this.faixa_2 = faixa_2;
    }

    public Double getFaixa1() {
        return faixa_1;
    }

    public void setFaixa1(Double faixa_1) {
        this.faixa_1 = faixa_1;
    }

    public String getAposta() {

        return aposta;
    }

    public String getApostaView() {

        String aposta_view = "";
        Boolean quebra = false;

        for (int i = 0; i < aposta.length(); i = i + 2) {
            aposta_view += aposta.substring(i, i + 2) + " ";
            if (i >= 14) {
                if (!quebra) {
                    aposta_view += "\n";
                }
                quebra = true;
            }
        }

        return aposta_view;
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

    public Integer getVolanteId() {
        return volante_id;
    }

    public void setVolanteId(Integer volante_id) {
        this.volante_id = volante_id;
    }

}
