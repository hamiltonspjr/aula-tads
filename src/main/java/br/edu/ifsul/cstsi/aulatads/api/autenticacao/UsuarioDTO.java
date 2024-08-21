package br.edu.ifsul.cstsi.aulatads.api.autenticacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(@Email @NotBlank
                         String email,
                         @NotBlank
                         String senha) {
}
