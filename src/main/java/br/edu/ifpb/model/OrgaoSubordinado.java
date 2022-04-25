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
public class OrgaoSubordinado implements Serializable {

    private Long cod_org_sub;
    private String nom_org_sub;
}
