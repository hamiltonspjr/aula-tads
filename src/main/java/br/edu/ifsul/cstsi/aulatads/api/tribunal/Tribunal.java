package br.edu.ifsul.cstsi.aulatads.api.tribunal;

import br.edu.ifsul.cstsi.aulatads.api.vara.Vara;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity(name="Tribunal")
@Table(name = "tribunal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tribunal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codtribunal;
    private String denominacao;
    private String endereco;

    @OneToMany(mappedBy = "tribunal", fetch = FetchType.EAGER)
    private Collection<Vara> varasByCodtribunal;
}
