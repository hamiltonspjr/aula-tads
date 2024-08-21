package br.edu.ifsul.cstsi.aulatads.api.advogado;

import jakarta.validation.constraints.*;

public record AdvogadoDTOPost(
        @NotBlank(message = "O código da OAB não pode ser nulo ou vazio")
        String oab,
        @NotBlank(message = "O nome não pode ser nulo ou vazio")
        @Size(min = 2, max = 50, message = "Tamanho mínimo do nome de 2 caracteres e máximo de 50")
        String nome,
        @NotBlank
        String endereco,
        @NotBlank
        String bairro,
        @NotBlank
        String cep,
        @NotBlank
        @Email
        String email
) {
    public AdvogadoDTOPost(Advogado advogado) {
        this(advogado.getOab(), advogado.getNome(), advogado.getEndereco(), advogado.getBairro(), advogado.getCep(), advogado.getEmail());
    }
}


