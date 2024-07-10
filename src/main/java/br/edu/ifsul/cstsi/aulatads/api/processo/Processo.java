package br.edu.ifsul.cstsi.aulatads.api.processo;

import br.edu.ifsul.cstsi.aulatads.api.assume.Assume;
import br.edu.ifsul.cstsi.aulatads.api.audiencia.Audiencia;
import br.edu.ifsul.cstsi.aulatads.api.custa.Custa;
import br.edu.ifsul.cstsi.aulatads.api.pessoa.Pessoa;
import br.edu.ifsul.cstsi.aulatads.api.tribunal.Tribunal;
import br.edu.ifsul.cstsi.aulatads.api.vara.Vara;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Collection;

@Entity(name="Processo")
@Table(name = "processo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Processo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codprocesso;
    private String numero;
    private Date abertura;
    private Date conclusao;
    private String descricao;
    private String situacao;

    @OneToMany(mappedBy = "processo", fetch = FetchType.EAGER)
    private Collection<Assume> assumesByCodprocesso;

    @OneToMany(mappedBy = "processo", fetch = FetchType.EAGER)
    private Collection<Audiencia> audienciasByCodprocesso;

    @OneToMany(mappedBy = "processo", fetch = FetchType.EAGER)
    private Collection<Custa> custasByCodprocesso;

    @ManyToOne
    @JoinColumn(name = "codvara", referencedColumnName = "codvara", nullable = false)
    private Vara vara;

    @ManyToOne
    @JoinColumn(name = "codpessoa", referencedColumnName = "codpessoa", nullable = false)
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "codreu", referencedColumnName = "codpessoa", nullable = false)
    private Pessoa reu;
}
