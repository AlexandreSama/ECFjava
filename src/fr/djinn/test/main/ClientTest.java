package fr.djinn.test.main;

import fr.djinn.main.entities.Adresse;
import fr.djinn.main.entities.Client;
import fr.djinn.main.entities.ECFException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Client.
 * Contient des tests unitaires pour valider le comportement de Client.
 */
class ClientTest {

    /**
     * Adresse valide utilisée pour les tests.
     */
    private Adresse adresseValide;

    /**
     * Instance de la classe Client utilisée pour les tests.
     */
    private Client client;

    /**
     * Initialisation des objets avant chaque test.
     *
     * @throws ECFException si une erreur survient lors de l'initialisation de l'objet Client.
     */
    @BeforeEach
    void setup() throws ECFException {
        adresseValide = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        client = new Client(adresseValide, "client@example.com", "Client important", "Entreprise ABC", "0123456789", 500_000, 50);
    }

    /**
     * Teste le constructeur avec des paramètres valides.
     * Vérifie qu'aucune exception n'est levée.
     */
    @Test
    void testConstructeurValide() {
        assertDoesNotThrow(() -> new Client(adresseValide, "client@example.com", "Nouveau client", "Entreprise XYZ", "0987654321", 1_000_000, 100));
    }

    /**
     * Teste le setter du chiffre d'affaires avec une valeur valide.
     * Vérifie que le chiffre d'affaires est correctement mis à jour.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour du chiffre d'affaires.
     */
    @Test
    void testSetChiffreAffaireValide() throws ECFException {
        client.setChiffreAffaire(1_000_000);
        assertEquals(1_000_000, client.getChiffreAffaire());
    }

    /**
     * Teste le setter du chiffre d'affaires avec une valeur invalide.
     * Vérifie qu'une exception est levée avec le message approprié.
     */
    @Test
    void testSetChiffreAffaireInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> client.setChiffreAffaire(200));
        assertTrue(exception.getMessage().contains("Le chiffre d'affaires doit être strictement supérieur à 200."));
    }

    /**
     * Teste le setter du nombre d'employés avec une valeur valide.
     * Vérifie que le nombre d'employés est correctement mis à jour.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour du nombre d'employés.
     */
    @Test
    void testSetNbrEmployeValide() throws ECFException {
        client.setNbrEmploye(150);
        assertEquals(150, client.getNbrEmploye());
    }

    /**
     * Teste le setter du nombre d'employés avec une valeur invalide.
     * Vérifie qu'une exception est levée avec le message approprié.
     */
    @Test
    void testSetNbrEmployeInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> client.setNbrEmploye(0));
        assertTrue(exception.getMessage().contains("Le nombre d'employés doit être strictement supérieur à 0."));
    }

    /**
     * Teste la génération d'identifiants uniques pour les clients.
     * Vérifie que les identifiants générés sont consécutifs.
     */
    @Test
    void testGenerateIdentifiant() {
        int currentId = Client.generateNextIdentifiant();
        int newId = Client.generateIdentifiant();
        assertEquals(currentId, newId);
        assertEquals(currentId + 1, Client.generateNextIdentifiant());
    }

    /**
     * Teste la mise à jour du compteur d'identifiants.
     * Vérifie que le compteur est correctement mis à jour.
     */
    @Test
    void testSetCompteurIdentifiant() {
        Client.setCompteurIdentifiant(500);
        assertEquals(500, Client.generateIdentifiant());
        assertEquals(501, Client.generateNextIdentifiant());
    }

    /**
     * Teste l'héritage de la classe Societe par la classe Client.
     * Vérifie que les méthodes héritées fonctionnent correctement.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour de la raison sociale.
     */
    @Test
    void testHeritageDeSociete() throws ECFException {
        assertEquals("Entreprise ABC", client.getRaisonSociale());
        client.setRaisonSociale("Nouvelle Entreprise");
        assertEquals("Nouvelle Entreprise", client.getRaisonSociale());
    }
}
