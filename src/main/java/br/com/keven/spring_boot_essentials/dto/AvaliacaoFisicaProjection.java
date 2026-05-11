package br.com.keven.spring_boot_essentials.dto;

import java.math.BigDecimal;

public interface AvaliacaoFisicaProjection {

    Integer getIdAluno();
    String getNomeAluno();
    Integer getIdAvaliacao();
    BigDecimal getPeso();
    BigDecimal getAltura();
    BigDecimal getPercentualGorduraCorporal();
}
