package fr.djinn.main.entities;

import com.google.gson.reflect.TypeToken;
import fr.djinn.main.utils.JsonUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static fr.djinn.main.utils.ECFLogger.LOGGER;

/**
 * Classe de gestion des prospects.
 * Permet la manipulation, la sauvegarde, le chargement et le tri des prospects.
 * Les données des prospects sont stockées dans un fichier JSON.
 */
public class GestionProspect {

    /**
     * Liste interne des prospects. Modifiable.
     */
    private static final List<Prospect> prospects = new ArrayList<>();

    /**
     * Chemin vers le fichier JSON de sauvegarde des prospects.
     */
    private static final String FILE_PATH = "save/prospects.json";

    /**
     * Retourne directement la liste interne des prospects (modifiable).
     *
     * @return Liste des prospects.
     */
    public static List<Prospect> getProspects() {
        return prospects;
    }

    /**
     * Sauvegarde la liste des prospects dans un fichier JSON.
     * Crée le fichier et le dossier parent si nécessaire.
     *
     * @throws IOException En cas d'erreur d'écriture.
     */
    public static void saveProspectsToFile() throws IOException {
        JsonUtils.ensureDirectoryExists(FILE_PATH); // Assure l'existence du dossier
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile(); // Crée un fichier vide si absent
        }
        JsonUtils.writeToJsonFile(prospects, FILE_PATH);
    }

    /**
     * Charge la liste des prospects depuis un fichier JSON.
     * Si le fichier est absent ou vide, initialise une liste vide.
     */
    public static void loadProspectsFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            LOGGER.log(Level.INFO, "Aucun fichier de prospects trouvé ou fichier vide. Liste initialisée vide.");
            return;
        }

        try (FileReader ignored = new FileReader(file)) {
            Type listType = new TypeToken<List<Prospect>>() {}.getType();
            List<Prospect> loadedProspects = JsonUtils.readFromJsonFile(FILE_PATH, listType);
            prospects.clear();
            prospects.addAll(loadedProspects);
            updateCompteurIdentifiant(); // Mise à jour du compteur
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "Erreur lors du chargement des prospects : " + e.getMessage());
        }
    }

    /**
     * Met à jour le compteur d'identifiants des prospects.
     * Définit le compteur au maximum des identifiants existants + 1.
     */
    private static void updateCompteurIdentifiant() {
        if (!prospects.isEmpty()) {
            Prospect.setCompteurIdentifiant(
                    prospects.stream().mapToInt(Prospect::getIdentifiant).max().orElse(0) + 1
            );
        }
    }

    /**
     * Trie les prospects par raison sociale de manière insensible à la casse.
     *
     * @return Une nouvelle liste des prospects triés par raison sociale.
     */
    public static List<Prospect> trierParRaisonSociale() {
        return prospects.stream()
                .sorted(Comparator.comparing(Prospect::getRaisonSociale, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }
}