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

/**
 * Utilitaire pour la gestion de la sérialisation et désérialisation JSON.
 * Fournit des méthodes pour écrire et lire des fichiers JSON
 * avec prise en charge de types personnalisés comme {@link LocalDate}.
 */
public class JsonUtils {

    /**
     * Instance de {@link Gson} configurée pour inclure le formatage JSON
     * et l'adaptateur pour {@link LocalDate}.
     */
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Enregistrement du TypeAdapter
            .create();

    /**
     * Sérialise un objet en JSON et l'écrit dans un fichier.
     *
     * @param object   L'objet à sérialiser.
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
     * @param type     Le type de l'objet à désérialiser (ex. via {@code TypeToken}).
     * @param <T>      Le type générique de l'objet attendu.
     * @return L'objet désérialisé.
     * @throws IOException En cas d'erreur de lecture.
     */
    public static <T> T readFromJsonFile(String filePath, Type type) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return GSON.fromJson(reader, type);
        }
    }

    /**
     * Vérifie si le dossier contenant le fichier spécifié existe et le crée si nécessaire.
     *
     * @param filePath Le chemin complet du fichier (incluant le dossier parent).
     * @throws RuntimeException Si la création du dossier échoue.
     */
    public static void ensureDirectoryExists(String filePath) {
        File file = new File(filePath);
        File directory = file.getParentFile(); // Récupère le dossier parent

        if (directory != null && !directory.exists()) {
            if (directory.mkdirs()) {
                LOGGER.log(Level.INFO, "Dossier créé : " + directory.getAbsolutePath());
            } else {
                throw new RuntimeException("Échec de la création du dossier : " + directory.getAbsolutePath());
            }
        }
    }
}
