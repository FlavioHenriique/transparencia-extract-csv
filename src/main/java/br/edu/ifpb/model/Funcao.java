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
public class Funcao implements Serializable {

    private Long cod_func;
    private String nom_func;
}
