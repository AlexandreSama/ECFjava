package fr.djinn.main.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.logging.Level;

import static fr.djinn.main.utils.ECFLogger.LOGGER;

public class JsonUtils {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Enregistrement du TypeAdapter
            .create();
    /**
     * Sérialise un objet en JSON et l'écrit dans un fichier.
     *
     * @param object L'objet à sérialiser.
     * @param filePath Le chemin du fichier où écrire le JSON.
     * @throws IOException En cas d'erreur d'écriture.
     */
    public static void writeToJsonFile(Object object, String filePath) throws IOException {
        ensureDirectoryExists(filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            GSON.toJson(object, writer);
        }
    }

    /**
     * Désérialise un fichier JSON en un objet Java.
     *
     * @param filePath Le chemin du fichier JSON.
     * @param type Le type de l'objet à désérialiser.
     * @param <T> Le type générique.
     * @return L'objet désérialisé.
     * @throws IOException En cas d'erreur de lecture.
     */
    public static <T> T readFromJsonFile(String filePath, Type type) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return GSON.fromJson(reader, type);
        }
    }

    public static void ensureDirectoryExists(String filePath) {
        File file = new File(filePath);
        File directory = file.getParentFile(); // Récupère le dossier parent

        if (directory != null && !directory.exists()) {
            if (directory.mkdirs()) {
                LOGGER.log(Level.INFO,"Dossier créé : " + directory.getAbsolutePath());
            } else {
                throw new RuntimeException("Échec de la création du dossier : " + directory.getAbsolutePath());
            }
        }
    }
}