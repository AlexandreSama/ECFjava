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
import java.util.stream.Collectors;

public class GestionProspect {

    private static final List<Prospect> prospects = new ArrayList<>();
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
     * Sauvegarde les prospects dans un fichier JSON.
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
     * Charge les prospects depuis un fichier JSON.
     */
    public static void loadProspectsFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            System.out.println("Aucun fichier de prospects trouvé ou fichier vide. Liste initialisée vide.");
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<Prospect>>() {}.getType();
            List<Prospect> loadedProspects = JsonUtils.readFromJsonFile(FILE_PATH, listType);
            prospects.clear();
            prospects.addAll(loadedProspects);
            updateCompteurIdentifiant(); // Mise à jour du compteur
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des prospects : " + e.getMessage());
        }
    }

    private static void updateCompteurIdentifiant() {
        if (!prospects.isEmpty()) {
            Prospect.setCompteurIdentifiant(
                    prospects.stream().mapToInt(Prospect::getIdentifiant).max().orElse(0) + 1
            );
        }
    }

    /**
     * Trie les prospects par raison sociale.
     *
     * @return Une liste triée.
     */
    public static List<Prospect> trierParRaisonSociale() {
        return prospects.stream()
                .sorted(Comparator.comparing(Prospect::getRaisonSociale, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }
}