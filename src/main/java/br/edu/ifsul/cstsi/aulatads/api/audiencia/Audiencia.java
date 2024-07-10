package br.edu.ifsul.cstsi.aulatads.api.audiencia;

import br.edu.ifsul.cstsi.aulatads.api.pessoa.Pessoa;
import br.edu.ifsul.cstsi.aulatads.api.processo.Processo;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity(name="Audiencia")
@Table(name = "audiencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audiencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codaudiencia;
    private Date data;
    private String parecer;

    @ManyToOne
    @JoinColumn(name = "codprocesso", referencedColumnName = "codprocesso", nullable = false)
    private Processo processo;
}
