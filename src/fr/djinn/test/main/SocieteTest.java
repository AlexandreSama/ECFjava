package fr.djinn.test.main;

import fr.djinn.main.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Societe.
 * Contient des tests unitaires pour valider le comportement de Societe.
 */
class SocieteTest {

    /**
     * Adresse valide utilisée pour les tests.
     */
    private Adresse adresseValide;

    /**
     * Instance de la classe Societe utilisée pour les tests.
     */
    private Societe societe;

    /**
     * Initialisation des objets avant chaque test.
     *
     * @throws ECFException si une erreur survient lors de l'initialisation de l'objet Societe.
     */
    @BeforeEach
    void setup() throws ECFException {
        adresseValide = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        societe = new SocieteStub(adresseValide, "contact@example.com", "Un commentaire", 1, "Entreprise XYZ", "0123456789");
    }

    /**
     * Teste le constructeur avec des paramètres valides.
     * Vérifie qu'aucune exception n'est levée.
     */
    @Test
    void testConstructeurValide() {
        assertDoesNotThrow(() -> new SocieteStub(adresseValide, "contact@example.com", "Un commentaire", 1, "Entreprise ABC", "0987654321"));
    }

    /**
     * Teste le setter de l'adresse avec une adresse valide.
     * Vérifie que l'adresse est correctement mise à jour.
     */
    @Test
    void testSetAdresseValide() {
        Adresse nouvelleAdresse = new Adresse("13001", "Rue Saint-Ferréol", "15", "Marseille");
        assertDoesNotThrow(() -> societe.setAdresse(nouvelleAdresse));
        assertEquals(nouvelleAdresse, societe.getAdresse());
    }

    /**
     * Teste le setter de l'adresse avec une adresse invalide (null).
     * Vérifie qu'une exception est levée avec le message approprié.
     */
    @Test
    void testSetAdresseInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> societe.setAdresse(null));
        assertTrue(exception.getMessage().contains("L'adresse ne peut pas être nulle."));
    }

    /**
     * Teste le setter de l'adresse email avec une adresse valide.
     * Vérifie que l'adresse email est correctement mise à jour.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour de l'adresse email.
     */
    @Test
    void testSetAdresseMailValide() throws ECFException {
        societe.setAdresseMail("nouveau@example.com");
        assertEquals("nouveau@example.com", societe.getAdresseMail());
    }

    /**
     * Teste le setter de l'adresse email avec une adresse invalide.
     * Vérifie qu'une exception est levée avec le message approprié.
     */
    @Test
    void testSetAdresseMailInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> societe.setAdresseMail("invalide-email"));
        assertTrue(exception.getMessage().contains("Adresse e-mail invalide"));
    }

    /**
     * Teste le setter du commentaire.
     * Vérifie que le commentaire est correctement mis à jour.
     */
    @Test
    void testSetCommentaire() {
        societe.setCommentaire("Un commentaire mis à jour");
        assertEquals("Un commentaire mis à jour", societe.getCommentaire());
    }

    /**
     * Teste le setter du numéro de téléphone avec une valeur valide.
     * Vérifie que le numéro de téléphone est correctement mis à jour.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour du numéro de téléphone.
     */
    @Test
    void testSetTelephoneValide() throws ECFException {
        societe.setTelephone("0987654321");
        assertEquals("0987654321", societe.getTelephone());
    }

    /**
     * Teste le setter du numéro de téléphone avec une valeur invalide.
     * Vérifie qu'une exception est levée avec le message approprié.
     */
    @Test
    void testSetTelephoneInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> societe.setTelephone("123"));
        assertTrue(exception.getMessage().contains("Numéro de téléphone invalide"));
    }

    /**
     * Teste le setter de la raison sociale avec une valeur valide.
     * Vérifie que la raison sociale est correctement mise à jour.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour de la raison sociale.
     */
    @Test
    void testSetRaisonSocialeValide() throws ECFException {
        societe.setRaisonSociale("Nouvelle Raison Sociale");
        assertEquals("Nouvelle Raison Sociale", societe.getRaisonSociale());
    }

    /**
     * Teste le setter de la raison sociale avec une valeur vide.
     * Vérifie qu'une exception est levée avec le message approprié.
     */
    @Test
    void testSetRaisonSocialeVide() {
        Exception exception = assertThrows(ECFException.class, () -> societe.setRaisonSociale(""));
        assertTrue(exception.getMessage().contains("La raison sociale ne peut pas être vide"));
    }

    /**
     * Teste le setter de la raison sociale avec une valeur déjà existante (doublon).
     * Vérifie qu'une exception est levée avec le message approprié.
     *
     * @throws ECFException si une erreur survient lors de la gestion des clients.
     */
    @Test
    void testSetRaisonSocialeDoublon() throws ECFException {
        // Ajouter un client avec une raison sociale existante
        GestionClient.getClients().add(new Client(
                new Adresse("75001", "Rue de Rivoli", "12", "Paris"),
                "client1@example.com",
                "Commentaire",
                "Entreprise Dupont",
                "0123456789",
                500_000,
                50
        ));

        // Tester si une exception est levée pour un doublon
        Exception exception = assertThrows(ECFException.class, () -> {
            societe.setRaisonSociale("Entreprise Dupont"); // Doublon attendu
        });

        // Vérifier le message de l'exception
        assertTrue(exception.getMessage().contains("La raison sociale existe déjà"));
    }
}
