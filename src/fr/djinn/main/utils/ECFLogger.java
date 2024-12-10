package fr.djinn.main.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ECFLogger {
    /**
     * Logger global utilisé pour enregistrer les événements.
     */
    public static final Logger LOGGER = Logger.getLogger(ECFLogger.class.getName());
    private static final String FILE_PATH = "log/debug.log";


    /**
     * Initialise le logger avec un gestionnaire de fichier.
     * Les logs seront écrits dans un fichier nommé `debug.log` situé dans un dossier `log`.
     * @throws IOException si le fichier ou le dossier ne peuvent pas être créés ou accédés.
     */
    public void initFileLogger() throws IOException {

        File file = new File(FILE_PATH);
        File directory =  file.getParentFile();

        if(!directory.exists()){
            directory.mkdirs();
        }
        // Création du gestionnaire de fichier
        FileHandler fileHandler = new FileHandler("log/debug.log", true);

        // Désactiver les gestionnaires par défaut
        LOGGER.setUseParentHandlers(false);

        // Ajouter le gestionnaire de fichier au logger
        LOGGER.addHandler(fileHandler);

        // Configurer le format des logs avec la classe LoggerFormat
        fileHandler.setFormatter(new LoggerFormat());
    }
}
