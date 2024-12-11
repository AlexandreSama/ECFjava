package fr.djinn.test.main;

import fr.djinn.main.entities.Adresse;
import fr.djinn.main.entities.ECFException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdresseTest {

    @BeforeEach
    void setup() {
        // Si des initialisations de contexte sont nécessaires, les placer ici.
    }

    @Test
    void testAdresseConstructeurValide() {
        assertDoesNotThrow(() -> new Adresse("75001", "Rue de Rivoli", "12", "Paris"));
    }

    @Test
    void testSetCodePostalValide() throws ECFException {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        assertDoesNotThrow(() -> adresse.setCodePostal("13001"));
        assertEquals("13001", adresse.getCodePostal());
    }

    @Test
    void testSetCodePostalInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> new Adresse("7500A", "Rue de Rivoli", "12", "Paris"));
        assertTrue(exception.getMessage().contains("Code postal invalide"));
    }

    @Test
    void testSetNomDeRueValide() throws ECFException {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        assertDoesNotThrow(() -> adresse.setNomDeRue("Avenue des Champs-Élysées"));
        assertEquals("Avenue des Champs-Élysées", adresse.getNomDeRue());
    }

    @Test
    void testSetNomDeRueInvalide() {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        Exception exception = assertThrows(ECFException.class, () -> adresse.setNomDeRue(""));
        assertTrue(exception.getMessage().contains("Nom de rue invalide"));
    }

    @Test
    void testSetNumeroDeRueValide() throws ECFException {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        assertDoesNotThrow(() -> adresse.setNumeroDeRue("45B"));
        assertEquals("45B", adresse.getNumeroDeRue());
    }

    @Test
    void testSetNumeroDeRueInvalide() {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        assertDoesNotThrow(() -> adresse.setNumeroDeRue("B45"));
    }

    @Test
    void testSetVilleValide() throws ECFException {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        assertDoesNotThrow(() -> adresse.setVille("Marseille"));
        assertEquals("Marseille", adresse.getVille());
    }

    @Test
    void testSetVilleInvalide() {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        Exception exception = assertThrows(ECFException.class, () -> adresse.setVille(""));
        assertTrue(exception.getMessage().contains("Ville invalide"));
    }
}

