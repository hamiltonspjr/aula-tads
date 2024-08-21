package br.edu.ifsul.cstsi.aulatads.api.usuario;

import br.edu.ifsul.cstsi.aulatads.api.usuario.validacoes.ValidacaoCadastroDeUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository rep;


//    @Autowired
//    private List<ValidacaoCadastroDeUsuario> validacoes;

    public Usuario insert(Usuario usuario) {
        Assert.isNull(usuario.getId(),"Não foi possível inserir o registro");

        //validações
//        validacoes.forEach(v -> v.validar(usuario));

        var usuarioSalvo = rep.save(usuario);
        //var tokenConfirmacaoEmail = new TokenConfirmacaoEmail(usuarioSalvo);
        //tokenConfirmacaoEmailRepository.save(tokenConfirmacaoEmail);

        return usuarioSalvo;
    }

//    public boolean confirmarEmail(String TokenDeConfirmacao) {
//        var token = tokenConfirmacaoEmailRepository.findByToken(TokenDeConfirmacao);
//        if(token != null)
//        {
//            var usuario = rep.findByEmail(token.getUsuario().getEmail());
//            usuario.setConfirmado(true);
//            rep.save(usuario);
//            return true;
//        }
//        return false;
//    }

}
