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
public class UnidadeGestora implements Serializable {

    private Long cod_unid_gestora;
    private String nom_unid_gestora;
}