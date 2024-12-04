package fr.djinn.main.entities;

import java.util.ArrayList;
import java.util.List;

public class GestionClient {

    private static final List<Client> clients = new ArrayList<>();

    public static List<Client> getClients() {
        return clients;
    }
}
