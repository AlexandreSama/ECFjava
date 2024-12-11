package fr.djinn.test.main;

import fr.djinn.main.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SocieteTest {

    private Adresse adresseValide;
    private Societe societe;

    @BeforeEach
    void setup() throws ECFException {
        adresseValide = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        societe = new SocieteStub(adresseValide, "contact@example.com", "Un commentaire", 1, "Entreprise XYZ", "0123456789");
    }

    @Test
    void testConstructeurValide() {
        assertDoesNotThrow(() -> new SocieteStub(adresseValide, "contact@example.com", "Un commentaire", 1, "Entreprise ABC", "0987654321"));
    }

    @Test
    void testSetAdresseValide() {
        Adresse nouvelleAdresse = new Adresse("13001", "Rue Saint-Ferréol", "15", "Marseille");
        assertDoesNotThrow(() -> societe.setAdresse(nouvelleAdresse));
        assertEquals(nouvelleAdresse, societe.getAdresse());
    }

    @Test
    void testSetAdresseInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> societe.setAdresse(null));
        assertTrue(exception.getMessage().contains("L'adresse ne peut pas être nulle."));
    }

    @Test
    void testSetAdresseMailValide() throws ECFException {
        societe.setAdresseMail("nouveau@example.com");
        assertEquals("nouveau@example.com", societe.getAdresseMail());
    }

    @Test
    void testSetAdresseMailInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> societe.setAdresseMail("invalide-email"));
        assertTrue(exception.getMessage().contains("Adresse e-mail invalide"));
    }

    @Test
    void testSetCommentaire() {
        societe.setCommentaire("Un commentaire mis à jour");
        assertEquals("Un commentaire mis à jour", societe.getCommentaire());
    }

    @Test
    void testSetTelephoneValide() throws ECFException {
        societe.setTelephone("0987654321");
        assertEquals("0987654321", societe.getTelephone());
    }

    @Test
    void testSetTelephoneInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> societe.setTelephone("123"));
        assertTrue(exception.getMessage().contains("Numéro de téléphone invalide"));
    }

    @Test
    void testSetRaisonSocialeValide() throws ECFException {
        societe.setRaisonSociale("Nouvelle Raison Sociale");
        assertEquals("Nouvelle Raison Sociale", societe.getRaisonSociale());
    }

    @Test
    void testSetRaisonSocialeVide() {
        Exception exception = assertThrows(ECFException.class, () -> societe.setRaisonSociale(""));
        assertTrue(exception.getMessage().contains("La raison sociale ne peut pas être vide"));
    }

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
