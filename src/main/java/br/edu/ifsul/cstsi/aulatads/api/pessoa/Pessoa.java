package br.edu.ifsul.cstsi.aulatads.api.pessoa;

import br.edu.ifsul.cstsi.aulatads.api.processo.Processo;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity(name="Pessoa")
@Table(name = "pessoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codpessoa;
    private String nome;
    private String endereco;
    private String bairro;
    private String cidade;
    private String cep;
    private String uf;
    private String telefone;
    private String email;
    private String cpf;
    private String rg;
    private String cpnj;

    @OneToMany(mappedBy = "pessoa", fetch = FetchType.EAGER)
    private Collection<Processo> processosByCodpessoa;
}
