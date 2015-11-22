package com.flaviolisboa.siaa.negocio.turmas.participacoes;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.util.entidades.impl.ValidadorBv;

@Stateless(name = "ValidadorParticipacaoAluno")
@Local(ValidadorParticipacaoAluno.class)
public class ValidadorParticipacaoAlunoBv
extends ValidadorBv<ParticipacaoAluno>
implements ValidadorParticipacaoAluno {

}
