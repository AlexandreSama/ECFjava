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

class ProspectTest {

    private Adresse adresseValide;
    private Prospect prospect;

    @BeforeEach
    void setup() throws ECFException {
        adresseValide = new Adresse("75001", "Rue de Rivoli", "12", "Paris");
        prospect = new Prospect(adresseValide, "prospect@example.com", "Premier contact", "Entreprise XYZ", "0123456789", "01/01/2022", "OUI");
    }

    @Test
    void testConstructeurValide() {
        assertDoesNotThrow(() -> new Prospect(adresseValide, "prospect@example.com", "Nouveau contact", "Entreprise ABC", "0987654321", "15/05/2021", "NON"));
    }

    @Test
    void testSetDateProspectionValide() throws ECFException {
        prospect.setDateProspection("15/06/2023");
        assertEquals(LocalDate.parse("15/06/2023", RegEx.FORMATTER), prospect.getDateProspection());
    }

    @Test
    void testSetDateProspectionInvalideFutur() {
        String futureDate = LocalDate.now().plusDays(1).format(RegEx.FORMATTER);
        Exception exception = assertThrows(ECFException.class, () -> prospect.setDateProspection(futureDate));
        assertTrue(exception.getMessage().contains("La date de prospection ne peut pas être dans le futur."));
    }

    @Test
    void testSetDateProspectionInvalideFormat() {
        Exception exception = assertThrows(ECFException.class, () -> prospect.setDateProspection("2022-01-01"));
        assertTrue(exception.getMessage().contains("La date de prospection doit être au format 'dd/MM/yyyy'."));
    }

    @Test
    void testSetEstInteresseValide() throws ECFException {
        prospect.setEstInteresse("NON");
        assertEquals(InterestedType.NON, prospect.getEstInteresse());
    }

    @Test
    void testSetEstInteresseInvalide() {
        Exception exception = assertThrows(ECFException.class, () -> prospect.setEstInteresse("MAYBE"));
        assertTrue(exception.getMessage().contains("Le statut d'intérêt doit être 'OUI' ou 'NON'."));
    }

    @Test
    void testGenerateIdentifiant() {
        int currentId = Prospect.generateNextIdentifiant();
        int newId = Prospect.generateIdentifiant();
        assertEquals(currentId, newId);
        assertEquals(currentId + 1, Prospect.generateNextIdentifiant());
    }

    @Test
    void testSetCompteurIdentifiant() {
        Prospect.setCompteurIdentifiant(100);
        assertEquals(100, Prospect.generateIdentifiant());
        assertEquals(101, Prospect.generateNextIdentifiant());
    }

    @Test
    void testHeritageDeSociete() throws ECFException {
        assertEquals("Entreprise XYZ", prospect.getRaisonSociale());
        prospect.setRaisonSociale("Nouvelle Entreprise");
        assertEquals("Nouvelle Entreprise", prospect.getRaisonSociale());
    }
}
