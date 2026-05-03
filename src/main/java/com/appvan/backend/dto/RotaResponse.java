package com.appvan.backend.dto;

import lombok.Data;

@Data
public class RotaResponse {

    private Integer ordem;
    private String aluno;
    private String endereco;
    private String turno;
}
