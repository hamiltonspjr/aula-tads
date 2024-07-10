package br.edu.ifsul.cstsi.aulatads.api.vara;

import br.edu.ifsul.cstsi.aulatads.api.processo.Processo;
import br.edu.ifsul.cstsi.aulatads.api.tribunal.Tribunal;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity(name="Vara")
@Table(name = "vara")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vara {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codvara;
    private String descricao;
    private String nome;

    @OneToMany(mappedBy = "vara", fetch = FetchType.EAGER)
    private Collection<Processo> processosByCodvara;

    @ManyToOne
    @JoinColumn(name = "codtribunal", referencedColumnName = "codtribunal", nullable = false)
    private Tribunal tribunal;


}
