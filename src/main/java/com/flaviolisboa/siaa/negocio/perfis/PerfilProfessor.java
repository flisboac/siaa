
package com.flaviolisboa.siaa.negocio.perfis;

import java.util.Collections;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.GroupSequence;

import com.flaviolisboa.siaa.negocio.turmas.Turma;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;

@Entity
@DiscriminatorValue(TipoPerfil.Valores.PROFESSOR)
@GroupSequence({ Identidade.class, Integridade.class, PerfilProfessor.class })
public class PerfilProfessor extends Perfil {
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "professorResponsavel")
	private List<Turma> turmas;
	
	public List<Turma> getTurmas() {
		return Collections.unmodifiableList(turmas);
	}
}
