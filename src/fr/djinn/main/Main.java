package fr.djinn.main;

import fr.djinn.main.entities.*;
import fr.djinn.main.utils.ECFLogger;
import fr.djinn.main.views.Accueil;
import static fr.djinn.main.utils.ECFLogger.LOGGER;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Classe principale de l'application qui initialise les données et lance l'interface graphique.
 */
public class Main {

    /**
     * Point d'entrée principal de l'application.
     *
     * @param args Arguments de ligne de commande (non utilisés).
     */
    public static void main(String[] args) {

        try {
            initialiserLogger();
            initialiserSocietesTest();
            new Accueil().setVisible(true);
        } catch (Exception e) {
            gererErreurCritique(e);
        }
    }

    /**
     * Initialise le logger pour l'application.
     * En cas d'échec, affiche un message à l'utilisateur et continue sans le logger.
     */
    private static void initialiserLogger() {
        try {
            new ECFLogger().initFileLogger();
            LOGGER.log(Level.INFO, "Initialisation du logger réussie");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Impossible de lancer le gestionnaire de logs. Les journaux ne seront pas enregistrés.",
                    "Avertissement",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Initialise une liste de clients et de prospects à des fins de test.
     * Les données sont ajoutées directement aux listes gérées par {@link GestionClient} et {@link GestionProspect}.
     */
    public static void initialiserSocietesTest() {
        GestionClient.getClients().add(new Client(
                new Adresse("75001", "rue des Lilas", "10", "Paris"),
                "client1@mail.com",
                "Commentaire client 1",
                "Woods Associates",
                "0102030405",
                500000,
                50
        ));

        GestionClient.getClients().add(new Client(
                new Adresse("69002", "avenue des Champs", "20", "Lyon"),
                "client2@mail.com",
                "Commentaire client 2",
                "IBM",
                "0102030406",
                300000,
                30
        ));

        GestionClient.getClients().add(new Client(
                new Adresse("75009", "boulevard Haussmann", "5", "Paris"),
                "client3@mail.com",
                "Commentaire client 3",
                "Swifty Corp",
                "0102030407",
                700000,
                70
        ));

        // Ajouter des prospects
        GestionProspect.getProspects().add(new Prospect(
                new Adresse("75002", "rue de la Paix", "15", "Paris"),
                "prospect1@mail.com",
                "Commentaire prospect 1",
                "Prospect X",
                "0102030408",
                "23/11/2015",
                "OUI"
        ));

        GestionProspect.getProspects().add(new Prospect(
                new Adresse("75001", "rue Saint-Honoré", "25", "Paris"),
                "prospect2@mail.com",
                "Commentaire prospect 2",
                "Prospect Y",
                "0102030409",
                "14/01/2023",
                "NON"
        ));

        GestionProspect.getProspects().add(new Prospect(
                new Adresse("75008", "avenue de l'Opéra", "30", "Paris"),
                "prospect3@mail.com",
                "Commentaire prospect 3",
                "Prospect Z",
                "0102030410",
                "20/06/2014",
                "OUI"
        ));

        LOGGER.log(Level.INFO, "Sociétés de test ajoutées : 3 clients, 3 prospects");
    }

    /**
     * Gère les erreurs critiques en affichant un message d'erreur à l'utilisateur
     * et en arrêtant l'application proprement.
     *
     * @param e L'exception à gérer.
     */
    private static void gererErreurCritique(Exception e) {
        if (LOGGER != null) {
            LOGGER.log(Level.SEVERE, "Erreur critique : " + e.getMessage(), e);
            LOGGER.log(Level.WARNING, "Fermeture de l'application en raison d'une erreur critique");
        }

        JOptionPane.showMessageDialog(null,
                "Une erreur critique est survenue. L'application va se fermer.",
                "Erreur critique",
                JOptionPane.ERROR_MESSAGE);

        System.exit(1); // Arrêt de l'application
    }
}
