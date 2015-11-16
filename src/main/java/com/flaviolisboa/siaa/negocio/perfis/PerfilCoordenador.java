
package com.flaviolisboa.siaa.negocio.perfis;

import java.util.Collections;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.GroupSequence;

import com.flaviolisboa.siaa.negocio.coordenacoes.Coordenacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;

@Entity
@DiscriminatorValue(TipoPerfil.Valores.COORDENADOR)
@GroupSequence({ Identidade.class, Integridade.class })
public class PerfilCoordenador extends Perfil {
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "coordenador")
	private List<Coordenacao> coordenacoes;
	
	public List<Coordenacao> getCoordenacoes() {
		return Collections.unmodifiableList(coordenacoes);
	}
}
