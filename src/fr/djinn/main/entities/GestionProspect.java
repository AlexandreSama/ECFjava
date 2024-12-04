package fr.djinn.main.entities;

import java.util.ArrayList;
import java.util.List;

public class GestionProspect {

    private static final List<Prospect> prospects = new ArrayList<>();

    public static List<Prospect> getProspects() {
        return prospects;
    }

}
