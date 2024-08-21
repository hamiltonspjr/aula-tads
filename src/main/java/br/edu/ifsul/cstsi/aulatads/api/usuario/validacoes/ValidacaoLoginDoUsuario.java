package br.edu.ifsul.cstsi.aulatads.api.usuario.validacoes;

import br.edu.ifsul.cstsi.aulatads.api.autenticacao.UsuarioDTO;

public interface ValidacaoLoginDoUsuario {
    void validar(UsuarioDTO usuarioDTO);
}
