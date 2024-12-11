package fr.djinn.test.main;

import fr.djinn.main.entities.Adresse;
import fr.djinn.main.entities.Client;
import fr.djinn.main.entities.ECFException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private Adresse adresseValide;
    private Client client;

    @BeforeEach
    void setup() throws ECFException {
        adresseValide = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        client = new Client(adresseValide, "client@example.com", "Client important", "Entreprise ABC", "0123456789", 500_000, 50);
    }

    @Test
    void testConstructeurValide() {
        assertDoesNotThrow(() -> new Client(adresseValide, "client@example.com", "Nouveau client", "Entreprise XYZ", "0987654321", 1_000_000, 100));
    }

    @Test
    void testSetChiffreAffaireValide() throws ECFException {
        client.setChiffreAffaire(1_000_000);
        assertEquals(1_000_000, client.getChiffreAffaire());
    }

    @Test
    void testSetChiffreAffaireInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> client.setChiffreAffaire(200));
        assertTrue(exception.getMessage().contains("Le chiffre d'affaires doit être strictement supérieur à 200."));
    }

    @Test
    void testSetNbrEmployeValide() throws ECFException {
        client.setNbrEmploye(150);
        assertEquals(150, client.getNbrEmploye());
    }

    @Test
    void testSetNbrEmployeInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> client.setNbrEmploye(0));
        assertTrue(exception.getMessage().contains("Le nombre d'employés doit être strictement supérieur à 0."));
    }

    @Test
    void testGenerateIdentifiant() {
        int currentId = Client.generateNextIdentifiant();
        int newId = Client.generateIdentifiant();
        assertEquals(currentId, newId);
        assertEquals(currentId + 1, Client.generateNextIdentifiant());
    }

    @Test
    void testSetCompteurIdentifiant() {
        Client.setCompteurIdentifiant(500);
        assertEquals(500, Client.generateIdentifiant());
        assertEquals(501, Client.generateNextIdentifiant());
    }

    @Test
    void testHeritageDeSociete() throws ECFException {
        assertEquals("Entreprise ABC", client.getRaisonSociale());
        client.setRaisonSociale("Nouvelle Entreprise");
        assertEquals("Nouvelle Entreprise", client.getRaisonSociale());
    }
}
