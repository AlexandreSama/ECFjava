package fr.djinn.main.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Classe utilitaire pour gérer les logs de l'application.
 * Configure un logger global qui écrit les logs dans un fichier situé à l'emplacement `log/debug.log`.
 */
public class ECFLogger {

    /**
     * Logger global utilisé pour enregistrer les événements.
     */
    public static final Logger LOGGER = Logger.getLogger(ECFLogger.class.getName());

    /**
     * Chemin vers le fichier de log.
     */
    private static final String FILE_PATH = "log/debug.log";

    /**
     * Initialise le logger avec un gestionnaire de fichier.
     * Les logs seront écrits dans un fichier nommé `debug.log` situé dans un dossier `log`,
     * qui sera créé s'il n'existe pas déjà.
     *
     * @throws IOException si le fichier ou le dossier ne peuvent pas être créés ou accédés.
     */
    public void initFileLogger() throws IOException {
        File file = new File(FILE_PATH);
        File directory = file.getParentFile();

        // Vérifie si le dossier parent existe, sinon le crée.
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Création du gestionnaire de fichier pour les logs.
        FileHandler fileHandler = new FileHandler(FILE_PATH, true);

        // Désactiver les gestionnaires par défaut du logger.
        LOGGER.setUseParentHandlers(false);

        // Ajouter le gestionnaire de fichier au logger.
        LOGGER.addHandler(fileHandler);

        // Configurer le format des logs avec une classe personnalisée (LoggerFormat).
        fileHandler.setFormatter(new LoggerFormat());
    }
}