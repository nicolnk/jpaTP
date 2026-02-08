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

    @Test
    public void testMedicamentSansCategorie(){
        Medicament m = new Medicament();
        m.setNom("Medicament sans catégorie");
        assertThrows(jakarta.validation.ConstraintViolationException.class, () ->  {
            medicamentRepository.saveAndFlush(m);
        }, "Le médicament doit avoir une catégorie");
    }

    @Test
    public void testSuppressionCategorieAvecMedicaments() {
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            categorieRepository.deleteById(1);
            categorieRepository.flush();
        }, "On ne doit pas pouvoir supprimer une catégorie qui a des médicaments");
    }

    @Test
    public void testCommandesEnCoursDispensaire() {
        List<Commande> enCours = commandeRepository.findByDispensaireCodeAndEnvoyeeLeIsNull("D002");
        assertEquals(1, enCours.size());
        assertNull(enCours.get(0).getEnvoyeeLe());
    }

    @Test
    public void testNombreArticlesCommandes() {
        Integer total = commandeRepository.countOrderedArticlesByDispensaire("D001");
        assertEquals(60, total);
    }

    @Test
    public void testMedicamentsDisponiblesPourCategorie() {
        List<Medicament> dispos = medicamentRepository.findAvailableByCategory(1);
        assertTrue(dispos.stream().anyMatch(m -> m.getNom().equals("Morphine 10mg")));

        List<Medicament> disposCat3 = medicamentRepository.findAvailableByCategory(3);
        assertTrue(disposCat3.isEmpty(), "Les médicaments de cat 3 sont marqués indisponibles");
    }
}
