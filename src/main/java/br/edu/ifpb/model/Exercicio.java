package br.edu.ifpb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Exercicio implements Serializable {

    private String UF;
    private String ano_mes;
    private Long cod_org_sup;
    private Long cod_unid_gestora;
    private Long cod_org_sub;
    private Long cod_func;
    private double val_emp;
    private double val_liquid;
    private double val_pago;
}
