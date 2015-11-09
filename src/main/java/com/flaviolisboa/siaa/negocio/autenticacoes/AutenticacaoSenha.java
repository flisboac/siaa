
package com.flaviolisboa.siaa.negocio.autenticacoes;

import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;
import com.flaviolisboa.siaa.util.marcadores.orm.Interno;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue(TipoAutenticacao.Valores.SENHA)
@GroupSequence({ Identificacao.class, Identidade.class, Integridade.class})
public class AutenticacaoSenha extends Autenticacao {

    @NotNull(message = "Forneça uma senha.", groups = Integridade.class)
    @Size(min = 1, max = 64, message = "A senha deve ter entre {min} e {max} catacrer(es).", groups = Integridade.class)
    @Transient
    private char[] senha;
    
    @NotNull(groups = Interno.class)
    @Column(name = "sal", length = 256)
    private byte[] sal;
    
    @NotNull(groups = Interno.class)
    @Column(name = "senha", length = 256)
    private byte[] senhaCriptografada;
    
}
