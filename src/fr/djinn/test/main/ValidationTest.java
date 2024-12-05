package fr.djinn.test.main;

import fr.djinn.main.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {

    @Test
    void testRaisonSocialeUnique() {
        GestionClient.getClients().clear();
        GestionProspect.getProspects().clear();

        // Ajout d'un premier client
        Client client1 = new Client(
                new Adresse("75001", "rue des Lilas", "10", "Paris"),
                "test@mail.com",
                "Test commentaire",
                "UniqueCorp",
                "0102030405",
                500000,
                10
        );
        GestionClient.getClients().add(client1);

        // Tentative d'ajout d'un client avec la même raison sociale
        Executable executable = () -> new Client(
                new Adresse("75001", "avenue des Champs", "20", "Lyon"),
                "test2@mail.com",
                "Test commentaire 2",
                "UniqueCorp", // Raison sociale identique
                "0102030406",
                300000,
                20
        );

        assertThrows(ECFException.class, executable, "La raison sociale doit être unique");
    }

    @Test
    void testChampsObligatoires() {
        Executable executable = () -> new Client(
                new Adresse(null, "rue des Lilas", "10", "Paris"), // Code postal manquant
                "test@mail.com",
                null, // Commentaire facultatif
                "SomeCorp",
                "0102030405",
                500000,
                10
        );

        assertThrows(ECFException.class, executable, "Tous les champs obligatoires doivent être remplis");
    }

    @Test
    void testChiffreAffaireMinimum() {
        Executable executable = () -> new Client(
                new Adresse("75001", "rue des Lilas", "10", "Paris"),
                "test@mail.com",
                "Test commentaire",
                "LowRevenueCorp",
                "0102030405",
                199, // CA inférieur à 200
                10
        );

        assertThrows(ECFException.class, executable, "Le chiffre d'affaires doit dépasser 200");
    }

    @Test
    void testNombreEmployePositif() {
        Executable executable = () -> new Client(
                new Adresse("75001", "rue des Lilas", "10", "Paris"),
                "test@mail.com",
                "Test commentaire",
                "NegativeEmployeesCorp",
                "0102030405",
                500000,
                0 // Nombre d'employés non positif
        );

        assertThrows(ECFException.class, executable, "Le nombre d'employés doit être supérieur à 0");
    }

    @Test
    void testCodePostalValide() {
        Executable executable = () -> new Adresse(
                "7500A", // Code postal invalide
                "rue des Lilas",
                "10",
                "Paris"
        );

        assertThrows(ECFException.class, executable, "Le code postal doit contenir exactement 5 chiffres");
    }

    @Test
    void testTelephoneValide() {
        Executable executable = () -> new Client(
                new Adresse("75001", "rue des Lilas", "10", "Paris"),
                "test@mail.com",
                "Test commentaire",
                "ValidCorp",
                "01234", // Téléphone invalide
                500000,
                10
        );

        assertThrows(ECFException.class, executable, "Le numéro de téléphone doit respecter le format attendu");
    }

    @Test
    void testEmailValide() {
        Executable executable = () -> new Client(
                new Adresse("75001", "rue des Lilas", "10", "Paris"),
                "not-an-email", // Email invalide
                "Test commentaire",
                "InvalidEmailCorp",
                "0102030405",
                500000,
                10
        );

        assertThrows(ECFException.class, executable, "L'adresse e-mail doit respecter le format attendu");
    }

    @Test
    void testDateProspectionFormatValide() {
        Executable executable = () -> new Prospect(
                new Adresse("75001", "rue des Lilas", "10", "Paris"),
                "test@mail.com",
                "Test commentaire",
                "ValidProspect",
                "0102030405",
                "35/12/2023", // Date invalide
                "OUI"
        );

        assertThrows(ECFException.class, executable, "La date doit respecter le format 'jj/MM/aaaa'");
    }

    @Test
    void testInterestedTypeValide() {
        Executable executable = () -> new Prospect(
                new Adresse("75001", "rue des Lilas", "10", "Paris"),
                "test@mail.com",
                "Test commentaire",
                "InterestedCorp",
                "0102030405",
                "23/11/2023",
                "MAYBE" // Valeur invalide
        );

        assertThrows(ECFException.class, executable, "Le statut d'intérêt doit être 'OUI' ou 'NON'");
    }
}