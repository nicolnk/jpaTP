package pharmacie.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
public class Dispensaire {
    @Id
    @NonNull
    private String code;

    @NonNull
    @NotBlank
    private String nom;

    private String contact;
    private String fonction;
    private String telephone;
    private String fax;

    @Embedded
    private AdressePostale adresse;

    @OneToMany(mappedBy = "dispensaire", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Commande> commandes = new LinkedList<>();

}
