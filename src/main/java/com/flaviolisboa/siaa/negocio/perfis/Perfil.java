
package com.flaviolisboa.siaa.negocio.perfis;

import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.util.entidades.Entidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "tipo")
@GroupSequence({ Identificacao.class, Identidade.class, Integridade.class })
@SequenceGenerator(name = "sq_perfil", sequenceName = "sq_perfil", initialValue = 1, allocationSize = 1)
public class Perfil implements Entidade, Serializable {
    
    @NotNull(message = "ID não pode ser nulo.", groups = Identificacao.class)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_perfil")
    private Long id;
    
    @NotNull(message = "Perfil deve possuir um tipo.", groups = Identidade.class)
    @Convert(converter = ConversorTipoPerfilJpa.class)
    private TipoPerfil tipo;

    @NotNull(message = "Perfil deve pertencer a uma pessoa.", groups = Identidade.class)
    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private Pessoa pessoa;
    
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public boolean isIdentificado() {
        return id != null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.tipo);
        hash = 71 * hash + Objects.hashCode(this.pessoa);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Perfil other = (Perfil) obj;
        if (this.tipo != other.tipo) {
            return false;
        }
        if (!Objects.equals(this.pessoa, other.pessoa)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Perfil{" + "id=" + id + ", tipo=" + tipo + ", pessoa=" + pessoa + '}';
    }
    
}
