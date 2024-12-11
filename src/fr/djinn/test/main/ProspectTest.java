package fr.djinn.test.main;

import fr.djinn.main.entities.Adresse;
import fr.djinn.main.entities.ECFException;
import fr.djinn.main.entities.Prospect;
import fr.djinn.main.utils.InterestedType;
import fr.djinn.main.utils.RegEx;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Prospect.
 * Contient des tests unitaires pour valider le comportement de Prospect.
 */
class ProspectTest {

    /**
     * Adresse valide utilisée pour les tests.
     */
    private Adresse adresseValide;

    /**
     * Instance de la classe Prospect utilisée pour les tests.
     */
    private Prospect prospect;

    /**
     * Initialisation des objets avant chaque test.
     *
     * @throws ECFException si une erreur survient lors de l'initialisation de l'objet Prospect.
     */
    @BeforeEach
    void setup() throws ECFException {
        adresseValide = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        prospect = new Prospect(adresseValide, "prospect@example.com", "Premier contact", "Entreprise XYZ", "0123456789", "01/01/2022", "OUI");
    }

    /**
     * Teste le constructeur avec des paramètres valides.
     * Vérifie qu'aucune exception n'est levée.
     */
    @Test
    void testConstructeurValide() {
        assertDoesNotThrow(() -> new Prospect(adresseValide, "prospect@example.com", "Nouveau contact", "Entreprise ABC", "0987654321", "15/05/2021", "NON"));
    }

    /**
     * Teste le setter de la date de prospection avec une valeur valide.
     * Vérifie que la date est correctement mise à jour.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour de la date de prospection.
     */
    @Test
    void testSetDateProspectionValide() throws ECFException {
        prospect.setDateProspection("15/06/2023");
        assertEquals(LocalDate.parse("15/06/2023", RegEx.FORMATTER), prospect.getDateProspection());
    }

    /**
     * Teste le setter de la date de prospection avec une date dans le futur.
     * Vérifie qu'une exception est levée avec le message approprié.
     */
    @Test
    void testSetDateProspectionInvalideFutur() {
        String futureDate = LocalDate.now().plusDays(1).format(RegEx.FORMATTER);
        Exception exception = assertThrows(ECFException.class, () -> prospect.setDateProspection(futureDate));
        assertTrue(exception.getMessage().contains("La date de prospection ne peut pas être dans le futur."));
    }

    /**
     * Teste le setter de la date de prospection avec un format invalide.
     * Vérifie qu'une exception est levée avec le message approprié.
     */
    @Test
    void testSetDateProspectionInvalideFormat() {
        Exception exception = assertThrows(ECFException.class, () -> prospect.setDateProspection("2022-01-01"));
        assertTrue(exception.getMessage().contains("La date de prospection doit être au format 'dd/MM/yyyy'."));
    }

    /**
     * Teste le setter du statut d'intérêt avec une valeur valide.
     * Vérifie que le statut est correctement mis à jour.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour du statut d'intérêt.
     */
    @Test
    void testSetEstInteresseValide() throws ECFException {
        prospect.setEstInteresse("NON");
        assertEquals(InterestedType.NON, prospect.getEstInteresse());
    }

    /**
     * Teste le setter du statut d'intérêt avec une valeur invalide.
     * Vérifie qu'une exception est levée avec le message approprié.
     */
    @Test
    void testSetEstInteresseInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> prospect.setEstInteresse("MAYBE"));
        assertTrue(exception.getMessage().contains("Le statut d'intérêt doit être 'OUI' ou 'NON'."));
    }

    /**
     * Teste la génération d'identifiants uniques pour les prospects.
     * Vérifie que les identifiants générés sont consécutifs.
     */
    @Test
    void testGenerateIdentifiant() {
        int currentId = Prospect.generateNextIdentifiant();
        int newId = Prospect.generateIdentifiant();
        assertEquals(currentId, newId);
        assertEquals(currentId + 1, Prospect.generateNextIdentifiant());
    }

    /**
     * Teste la mise à jour du compteur d'identifiants.
     * Vérifie que le compteur est correctement mis à jour.
     */
    @Test
    void testSetCompteurIdentifiant() {
        Prospect.setCompteurIdentifiant(100);
        assertEquals(100, Prospect.generateIdentifiant());
        assertEquals(101, Prospect.generateNextIdentifiant());
    }

    /**
     * Teste l'héritage de la classe Societe par la classe Prospect.
     * Vérifie que les méthodes héritées fonctionnent correctement.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour de la raison sociale.
     */
    @Test
    void testHeritageDeSociete() throws ECFException {
        assertEquals("Entreprise XYZ", prospect.getRaisonSociale());
        prospect.setRaisonSociale("Nouvelle Entreprise");
        assertEquals("Nouvelle Entreprise", prospect.getRaisonSociale());
    }
}
