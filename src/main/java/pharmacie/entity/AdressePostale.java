package pharmacie.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class AdressePostale {

    @NotBlank
    @Size(max = 255)
    private String adresse;

    @NotBlank
    @Size(max = 10)
    private String codePostal;

    @NotBlank
    @Size(max = 255)
    private String ville;

    @Size(max = 255)
    private String region;

    @NotBlank
    @Size(max = 255)
    private String pays;
}
