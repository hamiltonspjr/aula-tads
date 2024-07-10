package br.edu.ifsul.cstsi.aulatads.api.custa;

import br.edu.ifsul.cstsi.aulatads.api.processo.Processo;
import br.edu.ifsul.cstsi.aulatads.api.tribunal.Tribunal;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;


@Entity(name="Custa")
@Table(name = "custa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Custa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codcusta;
    private Date data;
    private String descricao;
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "codprocesso", referencedColumnName = "codprocesso", nullable = false)
    private Processo processo;
}
