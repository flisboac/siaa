/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flaviolisboa.siaa.negocio.turmas;

import com.flaviolisboa.siaa.negocio.perfis.PerfilAluno;
import java.math.BigDecimal;

/**
 *
 * @author flavio.costa
 */
public class Avaliacao {
    
    private Long id;
    
    private Turma turma;
    
    private PerfilAluno aluno;
    
    private NotaAvaliacao avaliacao01;
    
    private NotaAvaliacao avaliacao02;
    
    private NotaAvaliacao avaliacao03;
    
    private NotaAvaliacao avaliacao04;
    
    private BigDecimal mediaPreliminar;
    
    private BigDecimal mediaFinal;
}
