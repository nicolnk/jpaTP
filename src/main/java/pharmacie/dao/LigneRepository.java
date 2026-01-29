package pharmacie.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import pharmacie.entity.Ligne;

public interface LigneRepository extends JpaRepository<Ligne, Integer> {
}
