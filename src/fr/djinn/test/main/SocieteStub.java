package fr.djinn.test.main;

import fr.djinn.main.entities.Adresse;
import fr.djinn.main.entities.ECFException;
import fr.djinn.main.entities.Societe;

public class SocieteStub extends Societe {

    public SocieteStub(Adresse adresse, String adresseMail, String commentaire, Integer identifiant, String raisonSociale, String telephone) throws ECFException {
        super(adresse, adresseMail, commentaire, identifiant, raisonSociale, telephone);
    }
}