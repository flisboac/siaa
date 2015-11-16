
package com.flaviolisboa.siaa.util.entidades.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import javax.validation.Validator;

import com.flaviolisboa.siaa.util.entidades.Entidade;
import com.flaviolisboa.siaa.util.entidades.ResultadoValidacao;
import com.flaviolisboa.siaa.util.entidades.Validador;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.NaoNulo;

public class ValidadorBv<T extends Entidade> implements Validador<T> {

    @Resource
    private Validator validator;

    public Validator getValidator() {
        return this.validator;
    }

    public void setValidator(Validator validator, boolean gerenciadoPeloContainer) {
        this.validator = validator;
    }
    
    @Override
    public <TT extends T> ResultadoValidacao validar(TT entidade, Class<?>... aspectos) {
        return doValidar(entidade, null, aspectos);
    }

    @Override
    public <TT extends T> ResultadoValidacao validar(TT entidade, Object propriedade, Class<?>... aspectos) {
        ResultadoValidacao resultado;
        
        if (propriedade != null) {
            resultado = doValidar(entidade, propriedade, aspectos);
            
        } else {
            resultado = ResultadoValidacaoSimples.instanciar(getResumoPropriedadeNula());
        }
        
        return resultado;
    }

    @Override
    public <TT extends T> void validarOuFalhar(TT entidade, Class<?>... aspectos) throws ErroValidacao {
        ResultadoValidacao resultado = validar(entidade, aspectos);
        
        if (!resultado.isValido()) {
            throw new ErroValidacao(resultado);
        }
    }

    @Override
    public <TT extends T> void validarOuFalhar(TT entidade, Object propriedade, Class<?>... aspectos) throws ErroValidacao {
        ResultadoValidacao resultado = validar(entidade, propriedade, aspectos);
        
        if (!resultado.isValido()) {
            throw new ErroValidacao(resultado);
        }
    }
    
    protected String getResumoPropriedadeNula() {
        return "Objeto Propriedade não pode ser nulo.";
    }
    
    protected String getResumoObjetoNula() {
        return "Entidade não pode ser nula.";
    }
    
    protected String getResumoMultiplasViolacoes() {
        return "Múltiplas violações de regras encontradas.";
    }
    
    protected <TT extends T> Object getPayloadValidacao(ConstraintViolation<TT> violacao) {
        Object retorno = null;
        
        if (violacao != null) {
            Set<Class<? extends Payload>> payload = violacao.getConstraintDescriptor().getPayload();
            
            for (Class<? extends Payload> classe : payload) {
                retorno = classe;
                break;
            }
        }
        
        return retorno;
    }
    
    private <TT extends T> ResultadoValidacao doValidar(TT entidade, Object propriedade, Class<?>... aspectos) throws ErroValidacao {
        ResultadoValidacaoSimples resultado = ResultadoValidacaoSimples.instanciar(null);
        List<Class<?>> marcadores = Arrays.asList(aspectos);
        
        if (entidade == null) {
            resultado = ResultadoValidacaoSimples.instanciar(getResumoPropriedadeNula());
            
        } else if (marcadores.isEmpty() || !marcadores.contains(NaoNulo.class)) {
            Validator validador = getValidator();
            Set<ConstraintViolation<TT>> violacoes;
            
            if (propriedade == null) {
                violacoes = validador.validate(entidade, aspectos);
                
            } else {
                violacoes = validador.validateProperty(entidade, null, aspectos);
            }
            
			if (!violacoes.isEmpty()) {
				if (violacoes.size() == 1) {
					resultado = ResultadoValidacaoSimples.instanciar(getResumoMultiplasViolacoes());
                    
				} else {
                    resultado = ResultadoValidacaoSimples.instanciar(null);
                }
                
                for (ConstraintViolation<TT> cv : violacoes) {
                    Object payload = getPayloadValidacao(cv);
                    
                    if (violacoes.size() == 1) {
                        resultado.setResumo(cv.getMessage());
                    }
                    
                    resultado.addMensagem(MensagemValidacaoSimples.instanciar(propriedade, payload, cv.getMessage()));
                }
			}
        }
        
        return resultado;
    }

    @Override
    public <TT extends T> ResultadoValidacao validar(TT entidade, List<Class<?>> aspectos) {
        Class<?>[] array = new Class<?>[aspectos.size()];
        return validar(entidade, aspectos.toArray(array));
    }

    @Override
    public <TT extends T> ResultadoValidacao validar(TT entidade, Object propriedade, List<Class<?>> aspectos) {
        Class<?>[] array = new Class<?>[aspectos.size()];
        return validar(entidade, propriedade, aspectos.toArray(array));
    }

    @Override
    public <TT extends T> void validarOuFalhar(TT entidade, List<Class<?>> aspectos) throws ErroValidacao {
        Class<?>[] array = new Class<?>[aspectos.size()];
        validarOuFalhar(entidade, aspectos.toArray(array));
    }

    @Override
    public <TT extends T> void validarOuFalhar(TT entidade, Object propriedade, List<Class<?>> aspectos) throws ErroValidacao {
        Class<?>[] array = new Class<?>[aspectos.size()];
        validarOuFalhar(entidade, propriedade, aspectos.toArray(array));
    }
}
