package com.flaviolisboa.siaa.negocio.autenticacoes;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.GroupSequence;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.util.excecoes.ErroNegocio;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;
import com.flaviolisboa.siaa.util.marcadores.orm.Interno;

@Entity
@DiscriminatorValue(TipoAutenticacao.Valores.SENHA)
@GroupSequence({ Identidade.class, Integridade.class })
public class AutenticacaoSenha extends Autenticacao {
	private static final long serialVersionUID = 1L;

	private static final int TAMANHO_SAL = 256;
	private static final int TAMANHO_SENHA_CRIPTOGRAFADA = 256;
	
    @NotNull(message = "Autenticação por senha deve possuir o valor de sal.", groups = Interno.class)
    @Size(message = "O valor de sal deve ter tamanho entre {min} e {max} bytes.", min = 1, max = TAMANHO_SAL, groups = Interno.class)
    @Lob
    @Column(name = "sal")
    private byte[] sal;

    @Size(message = "A senha criptografada deve ter tamanho entre {min} e {max} bytes.", min = 0, max = TAMANHO_SENHA_CRIPTOGRAFADA, groups = Interno.class)
    @Lob
    @Column(name = "senha")
    private byte[] senhaCriptografada;

    @Future(message = "Data da última alteração não pode estar no passado.", groups = Interno.class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_ultima_alteracao")
    private Date dataUltimaAlteracao;

    public AutenticacaoSenha() {
    }

    public AutenticacaoSenha(Long id) {
        super(id);
    }

    public AutenticacaoSenha(Pessoa pessoa, byte[] sal) {
        super(pessoa, TipoAutenticacao.SENHA);
        this.sal = sal;
    }
    
    public Date getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public boolean isAutenticacaoCadastrada() {
        return this.senhaCriptografada != null;
    }

    public boolean isCredenciaisValidas(char[] senha) {
        byte[] senhaFinal = criptografarSenha(senha);
        boolean valido = Arrays.equals(this.senhaCriptografada, senhaFinal);
        return valido;
    }

    public void autenticar(char[] senha) {
    	if (!isCredenciaisValidas(senha)) {
    		// TODO Melhorar o erro lançado
    		throw new ErroNegocio();
    	}
    }
    
    public void setSal(byte[] sal) {
        if (!Arrays.equals(this.sal, sal)) {
            // Se o sal for alterado, a senha deve ser recadastrada
            this.senhaCriptografada = null;
            this.sal = Arrays.copyOf(sal, sal.length);
        }
    }
    
    public void cadastrar(char[] senha) throws ErroNegocio {
        byte[] novaSenhaCriptografada = criptografarSenha(senha);
        if (novaSenhaCriptografada == null) {
            // TODO melhorar a mensagem e exceção neste caso
            throw new ErroNegocio();
        }
        this.senhaCriptografada = novaSenhaCriptografada;
        // TODO melhorar a geração do Calendar a partir do locale do usuário (como injetar o locale aqui?)
        this.dataUltimaAlteracao = new Date();
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
