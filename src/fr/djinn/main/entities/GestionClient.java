package fr.djinn.main.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GestionClient {

    private static final List<Client> clients = new ArrayList<>();

    /**
     * Retourne directement la liste interne des clients (modifiable).
     *
     * @return Liste des clients.
     */
    public static List<Client> getClients() {
        return clients;
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
