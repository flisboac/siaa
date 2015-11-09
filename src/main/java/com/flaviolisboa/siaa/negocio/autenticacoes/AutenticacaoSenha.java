package com.flaviolisboa.siaa.negocio.autenticacoes;

import com.flaviolisboa.siaa.util.excecoes.ErroNegocio;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;
import com.flaviolisboa.siaa.util.marcadores.orm.Interno;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue(TipoAutenticacao.Valores.SENHA)
@GroupSequence({Identificacao.class, Identidade.class, Integridade.class})
public class AutenticacaoSenha extends Autenticacao {

    @NotNull(message = "Forneça uma senha.", groups = Integridade.class)
    @Size(min = 1, max = 64, message = "A senha deve ter entre {min} e {max} catacrer(es).", groups = Integridade.class)
    @Transient
    private char[] senha;

    @Lob
    @NotNull(groups = Interno.class)
    @Size(min = 1, max = 256, groups = Interno.class)
    @Column(name = "sal", length = 256)
    private byte[] sal;

    @Lob
    @Size(min = 0, max = 256, groups = Interno.class)
    @Column(name = "senha", length = 256)
    private byte[] senhaCriptografada;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_ultima_alteracao")
    private Calendar dataUltimaAlteracao;
    
    public char[] getSenha() {
        return senha;
    }

    public void setSenha(char[] senha) {
        this.senha = senha;
    }

    public Calendar getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public boolean isAutenticacaoCadastrada() {
        return this.senhaCriptografada != null;
    }

    public void setSal(byte[] sal) {
        if (!Arrays.equals(this.sal, sal)) {
            // Se o sal for alterado, a senha deve ser recadastrada
            this.senhaCriptografada = null;
            this.sal = Arrays.copyOf(sal, sal.length);
        }
    }

    public void cadastrar() throws ErroNegocio {
        cadastrar(this.senha);
        this.senha = null;
    }
    
    public void cadastrar(char[] senha) throws ErroNegocio {
        byte[] novaSenhaCriptografada = criptografarSenha(senha);
        if (novaSenhaCriptografada == null) {
            // TODO melhorar a mensagem e exceção neste caso
            throw new ErroNegocio();
        }
        this.senhaCriptografada = novaSenhaCriptografada;
        // TODO melhorar a geração do Calendar a partir do locale do usuário (como injetar o locale aqui?)
        this.dataUltimaAlteracao = Calendar.getInstance();
    }
    
    public boolean isCredenciaisValidas() {
        return isCredenciaisValidas(this.senha);
    }

    public boolean isCredenciaisValidas(char[] senha) {
        byte[] senhaFinal = criptografarSenha(senha);
        boolean valido = Arrays.equals(this.senhaCriptografada, senhaFinal);
        return valido;
    }

    public boolean isValidaParaAutenticacao() {
        return this.senha != null && isAutenticacaoCadastrada();
    }
    
    private byte[] criptografarSenha(char[] senha) throws ErroNegocio {
        byte[] retorno;

        if (senha == null) {
            retorno = null;

        } else if (senha.length == 0) {
            retorno = new byte[0];

        } else {
            try {
                byte[] senhaFinal = Arrays.copyOf(this.sal, senha.length + this.sal.length);
                byte[] bytesSenhaFornecida = toBytes(senha);
                copiarPara(senhaFinal, this.sal.length, bytesSenhaFornecida);
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                retorno = digest.digest(senhaFinal);
                Arrays.fill(senhaFinal, (byte) 0);
                Arrays.fill(bytesSenhaFornecida, (byte) 0);
                
            } catch (NoSuchAlgorithmException ex) {
                throw new ErroNegocio(ex);
            }
        }
        
        return retorno;
    }

    private byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000');
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }
    
    private void copiarPara(byte[] array, int indiceInicial, byte[] outra) throws ErroNegocio {
        try {
            int limite = outra.length;
            for (int i = 0; i < limite; ++i) {
                array[indiceInicial + i] = outra[i];
            }
            
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ErroNegocio(ex);
        }
    }
}
