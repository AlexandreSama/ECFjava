package fr.djinn.main.entities;

import com.google.gson.reflect.TypeToken;
import fr.djinn.main.utils.JsonUtils;
import static fr.djinn.main.utils.ECFLogger.LOGGER;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class GestionClient {

    private static final List<Client> clients = new ArrayList<>();
    private static final String FILE_PATH = "save/clients.json";

    /**
     * Retourne directement la liste interne des clients (modifiable).
     *
     * @return Liste des clients.
     */
    public static List<Client> getClients() {
        return clients;
    }

    /**
     * Sauvegarde les clients dans un fichier JSON.
     */
    public static void saveClientsToFile() throws IOException {
        JsonUtils.ensureDirectoryExists(FILE_PATH); // Assure l'existence du dossier
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile(); // Crée un fichier vide si absent
        }
        JsonUtils.writeToJsonFile(clients, FILE_PATH);
    }

    /**
     * Charge les clients depuis un fichier JSON.
     */
    public static void loadClientsFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            LOGGER.log(Level.INFO, "Aucun fichier de clients trouvé ou fichier vide. Liste initialisée vide.");
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<Client>>() {}.getType();
            List<Client> loadedClients = JsonUtils.readFromJsonFile(FILE_PATH, listType);
            clients.clear();
            clients.addAll(loadedClients);
            updateCompteurIdentifiant(); // Mise à jour du compteur
        } catch (IOException e) {
            LOGGER.log(Level.INFO,"Erreur lors du chargement des clients : " + e.getMessage());
        }
    }

    private static void updateCompteurIdentifiant() {
        if (!clients.isEmpty()) {
            Client.setCompteurIdentifiant(
                    clients.stream().mapToInt(Client::getIdentifiant).max().orElse(0) + 1
            );
        }
    }

    /**
     * Trie les clients par raison sociale.
     *
     * @return Une liste triée.
     */
    public static List<Client> trierParRaisonSociale() {
        return clients.stream()
                .sorted(Comparator.comparing(Client::getRaisonSociale, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }
}
