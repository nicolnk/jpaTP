package pharmacie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
public class Ligne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Min(1)
    private Integer quantite;

    @ManyToOne(optional = false)
    @NonNull
    @ToString.Exclude
    private Commande commande;

    @ManyToOne(optional = false)
    @NonNull
    @ToString.Exclude
    private Medicament medicament;
}
