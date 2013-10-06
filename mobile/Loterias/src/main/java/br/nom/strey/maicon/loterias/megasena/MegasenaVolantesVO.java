package br.nom.strey.maicon.loterias.megasena;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public void setAposta(String aposta) {
        this.aposta = aposta;
    }

    public String getApostaView() {

        String aposta_view = "";
        Boolean quebra = false;
        List<Integer> list_aposta = new ArrayList<Integer>();
        list_aposta = getApostaList();

//        for (Integer numero: list_aposta) {
//            aposta_view += numero+" ";
//            if (aposta_view.length() >= 21) {
//                if (!quebra) {
//                    aposta_view += "\n";
//                }
//                quebra = true;
//            }
//        }

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

    public List<Integer> getApostaList() {

        List<Integer> list_aposta = new ArrayList<Integer>();

        for (int i = 0; i < aposta.length(); i = i + 2) {
            list_aposta.add(Integer.parseInt(aposta.substring(i, i + 2)));
        }
        return list_aposta;
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

    public void confereResultado(Context ctx) {
        MegasenaResultadosDAO dao_resultado = new MegasenaResultadosDAO(ctx);
        qtd_acertos = 0;

        if (dao_resultado.exist(concurso)) {
            MegasenaResultadosVO vo_resultado = dao_resultado.get(concurso);

            for (Integer numero : vo_resultado.getNumerosList()) {
                if (apostaContem(numero)) {
                    qtd_acertos++;
                }
            }
        }
    }

    private boolean apostaContem(Integer numero) {
        return getApostaList().contains(numero);
    }

}
