package fr.djinn.main.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GestionProspect {

    private static final List<Prospect> prospects = new ArrayList<>();

    public static List<Prospect> getProspects() {
        return prospects;
    }

    public static List<Prospect> trierParRaisonSociale() {
        return prospects.stream()
                .sorted(Comparator.comparing(Prospect::getRaisonSociale, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

}
