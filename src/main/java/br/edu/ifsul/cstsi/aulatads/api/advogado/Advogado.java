package br.edu.ifsul.cstsi.aulatads.api.advogado;

import br.edu.ifsul.cstsi.aulatads.api.assume.Assume;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity(name="Advogado")
@Table(name = "advogado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advogado {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codadvogado;
    private String oab;
    private String nome;
    private String endereco;
    private String bairro;
    private String cep;
    private String email;

    @OneToMany(mappedBy = "advogado", fetch = FetchType.EAGER)
    private Collection<Assume> assumesByCodadvogado;
}
