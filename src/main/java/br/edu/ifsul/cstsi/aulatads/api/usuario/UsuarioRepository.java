package br.edu.ifsul.cstsi.aulatads.api.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByEmail(String email);
    Usuario findByEmail(String email);
}
