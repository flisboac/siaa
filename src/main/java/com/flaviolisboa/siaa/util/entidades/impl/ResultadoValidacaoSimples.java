
package com.flaviolisboa.siaa.util.entidades.impl;

import com.flaviolisboa.siaa.util.entidades.MensagemValidacao;
import com.flaviolisboa.siaa.util.entidades.ResultadoValidacao;
import java.util.ArrayList;
import java.util.List;

public class ResultadoValidacaoSimples implements ResultadoValidacao {

    private boolean valido;
    private String resumo;
    private List<MensagemValidacao> mensagens = new ArrayList<>();

    @Override
    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    @Override
    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    @Override
    public List<MensagemValidacao> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<MensagemValidacao> mensagens) {
        this.mensagens = mensagens;
    }
    
    public ResultadoValidacaoSimples addMensagem(MensagemValidacao mensagemValidacao) {
        if (mensagemValidacao != null) {
            this.mensagens.add(mensagemValidacao);
        }
        return this;
    }
    
    public static ResultadoValidacaoSimples instanciar(String mensagem) {
        ResultadoValidacaoSimples resultado = new ResultadoValidacaoSimples();
        resultado.setResumo(mensagem);
        resultado.setValido(mensagem == null || mensagem.isEmpty());
        return resultado;
    }
    
    public static ResultadoValidacaoSimples instanciar(boolean valido, String mensagem) {
        ResultadoValidacaoSimples resultado = new ResultadoValidacaoSimples();
        resultado.setResumo(mensagem);
        resultado.setValido(valido);
        return resultado;
    }
}
