package fr.djinn.main.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GestionClient {

    private static final List<Client> clients = new ArrayList<>();

    public static List<Client> getClients() {
        return clients;
    }

    public static List<Client> trierParRaisonSociale() {
        return clients.stream()
                .sorted(Comparator.comparing(Client::getRaisonSociale, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }
}
