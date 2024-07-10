package br.edu.ifsul.cstsi.aulatads.api.assume;

import br.edu.ifsul.cstsi.aulatads.api.advogado.Advogado;
import br.edu.ifsul.cstsi.aulatads.api.processo.Processo;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity(name="Assume")
@Table(name = "assume")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assume {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codassume;
    private Date dataInicio;
    private Date dataFinal;

    @ManyToOne
    @JoinColumn(name = "codprocesso", referencedColumnName = "codprocesso", nullable = false)
    private Processo processo;

    @ManyToOne
    @JoinColumn(name = "codadvogado", referencedColumnName = "codadvogado", nullable = false)
    private Advogado advogado;
}
