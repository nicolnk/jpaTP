package pharmacie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numero;

    @NonNull
    @NotNull
    private LocalDate saisieLe;

    private LocalDate envoyeeLe;

    @PositiveOrZero
    private BigDecimal port = BigDecimal.ZERO;

    @PositiveOrZero
    private BigDecimal remise = BigDecimal.ZERO;

    private String destinataire;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "adresse", column = @Column(name = "LIVRAISON_ADRESSE")),
        @AttributeOverride(name = "codePostal", column = @Column(name = "LIVRAISON_CODE_POSTAL")),
        @AttributeOverride(name = "ville", column = @Column(name = "LIVRAISON_VILLE")),
        @AttributeOverride(name = "region", column = @Column(name = "LIVRAISON_REGION")),
        @AttributeOverride(name = "pays", column = @Column(name = "LIVRAISON_PAYS"))
    })
    private AdressePostale adresseLivraison;

    @ManyToOne(optional = false)
    @NonNull
    private Dispensaire dispensaire;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Ligne> lignes = new LinkedList<>();


}
