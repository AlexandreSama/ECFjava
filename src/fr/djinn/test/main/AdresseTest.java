package fr.djinn.test.main;

import fr.djinn.main.entities.Adresse;
import fr.djinn.main.entities.ECFException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Adresse.
 * Contient des tests unitaires pour valider le comportement de Adresse.
 */
class AdresseTest {

    /**
     * Teste le constructeur avec des paramètres valides.
     * Vérifie qu'aucune exception n'est levée.
     */
    @Test
    void testAdresseConstructeurValide() {
        assertDoesNotThrow(() -> new Adresse("75001", "Rue de Rivoli", "12", "Paris"));
    }

    /**
     * Teste le setter du code postal avec une valeur valide.
     * Vérifie que le code postal est correctement mis à jour.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour du code postal.
     */
    @Test
    void testSetCodePostalValide() throws ECFException {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        assertDoesNotThrow(() -> adresse.setCodePostal("13001"));
        assertEquals("13001", adresse.getCodePostal());
    }

    /**
     * Teste le setter du code postal avec une valeur invalide.
     * Vérifie qu'une exception est levée avec le message approprié.
     */
    @Test
    void testSetCodePostalInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> new Adresse("7500A", "Rue de Rivoli", "12", "Paris"));
        assertTrue(exception.getMessage().contains("Code postal invalide"));
    }

    /**
     * Teste le setter du nom de rue avec une valeur valide.
     * Vérifie que le nom de rue est correctement mis à jour.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour du nom de rue.
     */
    @Test
    void testSetNomDeRueValide() throws ECFException {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        assertDoesNotThrow(() -> adresse.setNomDeRue("Avenue des Champs-Élysées"));
        assertEquals("Avenue des Champs-Élysées", adresse.getNomDeRue());
    }

    /**
     * Teste le setter du nom de rue avec une valeur invalide.
     * Vérifie qu'une exception est levée avec le message approprié.
     */
    @Test
    void testSetNomDeRueInvalide() {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        Exception exception = assertThrows(ECFException.class, () -> adresse.setNomDeRue(""));
        assertTrue(exception.getMessage().contains("Nom de rue invalide"));
    }

    /**
     * Teste le setter du numéro de rue avec une valeur valide.
     * Vérifie que le numéro de rue est correctement mis à jour.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour du numéro de rue.
     */
    @Test
    void testSetNumeroDeRueValide() throws ECFException {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        assertDoesNotThrow(() -> adresse.setNumeroDeRue("45B"));
        assertEquals("45B", adresse.getNumeroDeRue());
    }

    /**
     * Teste le setter du numéro de rue avec une valeur invalide.
     * Vérifie qu'aucune exception n'est levée pour le format spécifique.
     */
    @Test
    void testSetNumeroDeRueInvalide() {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        assertDoesNotThrow(() -> adresse.setNumeroDeRue("B45"));
    }

    /**
     * Teste le setter de la ville avec une valeur valide.
     * Vérifie que la ville est correctement mise à jour.
     *
     * @throws ECFException si une erreur survient lors de la mise à jour de la ville.
     */
    @Test
    void testSetVilleValide() throws ECFException {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        assertDoesNotThrow(() -> adresse.setVille("Marseille"));
        assertEquals("Marseille", adresse.getVille());
    }

    /**
     * Teste le setter de la ville avec une valeur invalide.
     * Vérifie qu'une exception est levée avec le message approprié.
     */
    @Test
    void testSetVilleInvalide() {
        Adresse adresse = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        Exception exception = assertThrows(ECFException.class, () -> adresse.setVille(""));
        assertTrue(exception.getMessage().contains("Ville invalide"));
    }
}
