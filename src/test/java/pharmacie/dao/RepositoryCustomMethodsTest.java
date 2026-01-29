package pharmacie.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles; // Import ajouté
import pharmacie.entity.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class RepositoryCustomMethodsTest {

    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private MedicamentRepository medicamentRepository;
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private DispensaireRepository dispensaireRepository;

    @Test
    public void testMedicamentCustomMethods() {
        Medicament indisponible = medicamentRepository.findByNom("Lévofloxacine 500mg").orElseThrow();
        Medicament disponible   = medicamentRepository.findByNom("Doliprane Effervescent 1g").orElseThrow();
        List<Medicament> disponibles = medicamentRepository.findByIndisponibleFalse();

        assertTrue(disponibles.contains(disponible));
        assertFalse(disponibles.contains(indisponible));
    }

    @Test
    public void testCategorieCustomMethods() {
        Categorie c1 = new Categorie();
        c1.setLibelle("AnalgesiquesTest");
        categorieRepository.save(c1);
        assertNotNull(categorieRepository.findByLibelle("AnalgesiquesTest"));
    }

    @Test
    public void testCommandeCustomMethods() {
        LocalDate datePivot = LocalDate.of(2025, 12, 31);
        List<Commande> recentes = commandeRepository.findBySaisieLeAfter(datePivot);

        assertFalse(recentes.isEmpty());
        assertEquals(1, recentes.size());
        assertEquals("D002", recentes.get(0).getDispensaire().getCode());
    }

    @Test
    public void testDispensaireCustomMethods() {
        List<Dispensaire> occitanie = dispensaireRepository.findByAdresse_Region("Occitanie");
        assertEquals(1, occitanie.size());
        assertEquals("Dispensaire du Sud", occitanie.get(0).getNom());
        assertEquals("Toulouse", occitanie.get(0).getAdresse().getVille());

        List<Dispensaire> idf = dispensaireRepository.findByAdresse_Region("Île-de-France");
        assertEquals(1, idf.size());
    }
}
