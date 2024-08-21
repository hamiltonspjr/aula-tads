package br.edu.ifsul.cstsi.aulatads.api.advogado;


import jakarta.validation.constraints.*;

public record AdvogadoDTOPut(
        String oab,
        @Size(min = 2, max = 50, message = "Tamanho mínimo do nome de 2 caracteres e máximo de 50")
        String nome,

        String endereco,

        String bairro,

        String cep,

        @Email
        String email
) {
    public AdvogadoDTOPut(Advogado advogado) {
        this(advogado.getOab(), advogado.getNome(), advogado.getEndereco(), advogado.getBairro(), advogado.getCep(), advogado.getEmail());
    }
}