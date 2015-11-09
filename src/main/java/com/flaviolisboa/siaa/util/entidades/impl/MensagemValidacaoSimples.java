
package com.flaviolisboa.siaa.util.entidades.impl;

import com.flaviolisboa.siaa.util.entidades.MensagemValidacao;
import com.flaviolisboa.siaa.util.UtilPropriedades;
import java.util.ArrayList;
import java.util.List;

public class MensagemValidacaoSimples implements MensagemValidacao {

    private Object codigo;
    private Object propriedade;
    private String mensagem;
    private List<String> detalhes = new ArrayList<>();

    @Override
    public Object getCodigo() {
        return codigo;
    }

    public void setCodigo(Object codigo) {
        this.codigo = codigo;
    }

    public MensagemValidacaoSimples withCodigo(Object codigo) {
        this.codigo = codigo;
        return this;
    }
    
    @Override
    public Object getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Object propriedade) {
        this.propriedade = propriedade;
    }

    @Override
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public List<String> getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(List<String> detalhes) {
        this.detalhes = detalhes;
    }
    
    public MensagemValidacaoSimples addMensagem(String mensagem) {
        if (mensagem != null && !mensagem.isEmpty()) {
            this.detalhes.add(mensagem);
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(propriedade != null ? UtilPropriedades.getNomePropriedadeBean(propriedade.toString()) + ": " : "");
        for (String detalhe : detalhes) {
            detalhe = detalhe.trim();
            sb.append(detalhe);
            if (!detalhe.matches(".*\\p{Punct}$")) {
                sb.append(".");
            }
        }
        return sb.toString();
    }
            
    public static MensagemValidacaoSimples instanciar(String mensagem) {
        MensagemValidacaoSimples msg = new MensagemValidacaoSimples();
        msg.setMensagem(mensagem);
        return msg;
    }
    
    public static MensagemValidacaoSimples instanciar(Object propriedade, String mensagem) {
        MensagemValidacaoSimples msg = new MensagemValidacaoSimples();
        msg.setMensagem(mensagem);
        return msg;
    }
    
    public static MensagemValidacaoSimples instanciar(Object propriedade, Object codigo, String mensagem) {
        MensagemValidacaoSimples msg = new MensagemValidacaoSimples();
        msg.setCodigo(codigo);
        msg.setPropriedade(propriedade);
        msg.setMensagem(mensagem);
        return msg;
    }
    
}
