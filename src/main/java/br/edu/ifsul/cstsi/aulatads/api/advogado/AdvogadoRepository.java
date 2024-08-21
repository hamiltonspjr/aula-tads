package br.edu.ifsul.cstsi.aulatads.api.advogado;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvogadoRepository extends JpaRepository<Advogado, Integer> {
    List<Advogado> findByNomeStartsWithOrderByNome(String nome);
    Optional<Advogado> findByCodadvogado(Integer codadvogado);
}
