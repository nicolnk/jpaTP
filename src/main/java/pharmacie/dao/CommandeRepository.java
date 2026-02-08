package pharmacie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pharmacie.entity.Commande;
import java.time.LocalDate;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    List<Commande> findBySaisieLeAfter(LocalDate date);

    // Trouver les commandes non envoyées pour un dispensaire
    List<Commande> findByDispensaireCodeAndEnvoyeeLeIsNull(String code);

    // Calculer le nombre total d'articles (somme des quantités) pour les commandes envoyées
    @Query("SELECT SUM(l.quantite) FROM Ligne l WHERE l.commande.dispensaire.code = :code " +
        "AND l.commande.envoyeeLe IS NOT NULL")
    Integer countOrderedArticlesByDispensaire(String code);
}
