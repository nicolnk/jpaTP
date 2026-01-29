package pharmacie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pharmacie.entity.Dispensaire;
import java.util.List;

public interface DispensaireRepository extends JpaRepository<Dispensaire, String> {

    /**
     * Trouve les dispensaires par région.
     */
    List<Dispensaire> findByAdresse_Region(String region);
}
