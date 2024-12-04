package fr.djinn.main;

import fr.djinn.main.utils.ECFLogger;
import static fr.djinn.main.utils.ECFLogger.LOGGER;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {

        try {
            initialiserLogger();
        } catch (Exception e){
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