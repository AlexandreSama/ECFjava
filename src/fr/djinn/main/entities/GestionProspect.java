package fr.djinn.main.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GestionProspect {

    private static final List<Prospect> prospects = new ArrayList<>();

    /**
     * Retourne directement la liste interne des prospects (modifiable).
     *
     * @return Liste des prospects.
     */
    public static List<Prospect> getProspects() {
        return prospects;
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