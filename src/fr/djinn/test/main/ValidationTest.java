package fr.djinn.test.main;

import fr.djinn.main.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {

    /**
     * Teste si une raison sociale unique est imposée lors de la création d'un client.
     * Vérifie que deux clients ne peuvent pas avoir la même raison sociale.
     *
     * Condition de succès : une exception ECFException est levée si la raison sociale est déjà utilisée.
     */
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

    /**
     * Teste si tous les champs obligatoires doivent être remplis lors de la création d'un client.
     * Vérifie que l'absence de champs obligatoires déclenche une exception.
     *
     * Condition de succès : une exception ECFException est levée si un champ obligatoire est manquant.
     */
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

    /**
     * Teste si le chiffre d'affaires minimum requis est respecté lors de la création d'un client.
     * Vérifie que le chiffre d'affaires doit être supérieur à 200.
     *
     * Condition de succès : une exception ECFException est levée si le chiffre d'affaires est inférieur à 200.
     */
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

    /**
     * Teste si le nombre d'employés est strictement positif lors de la création d'un client.
     * Vérifie que la valeur 0 ou négative pour le nombre d'employés n'est pas acceptée.
     *
     * Condition de succès : une exception ECFException est levée si le nombre d'employés est <= 0.
     */
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


    /**
     * Teste si le code postal respecte le format attendu (exactement 5 chiffres).
     * Vérifie qu'un code postal invalide déclenche une exception.
     *
     * Condition de succès : une exception ECFException est levée si le code postal est invalide.
     */
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

    /**
     * Teste si le numéro de téléphone respecte le format attendu.
     * Vérifie qu'un numéro de téléphone invalide déclenche une exception.
     *
     * Condition de succès : une exception ECFException est levée si le numéro de téléphone est invalide.
     */
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

    /**
     * Teste si l'adresse e-mail respecte le format attendu.
     * Vérifie qu'une adresse e-mail invalide déclenche une exception.
     *
     * Condition de succès : une exception ECFException est levée si l'adresse e-mail est invalide.
     */
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

    /**
     * Teste si la date de prospection respecte le format 'jj/MM/aaaa'.
     * Vérifie qu'une date au format incorrect déclenche une exception.
     *
     * Condition de succès : une exception ECFException est levée si la date est invalide.
     */
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

    /**
     * Teste si le statut d'intérêt d'un prospect est valide (doit être 'OUI' ou 'NON').
     * Vérifie que des valeurs autres que 'OUI' ou 'NON' déclenchent une exception.
     *
     * Condition de succès : une exception ECFException est levée si le statut est invalide.
     */
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