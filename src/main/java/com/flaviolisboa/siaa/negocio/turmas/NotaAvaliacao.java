package com.flaviolisboa.siaa.negocio.turmas;

import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Column;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

public class NotaAvaliacao {
    
    @DecimalMin(value = "0", message = "Nota da avaliação não deve ser menor que {value}.", groups = Integridade.class)
    @DecimalMax(value = "10", message = "Nota da avaliação não deve ultrapassar {value}.", groups = Integridade.class)
    @Column(name = "nota")
    private BigDecimal nota;
    
    private Calendar dataLancamento;
}
