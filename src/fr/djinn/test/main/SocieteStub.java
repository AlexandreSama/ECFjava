package fr.djinn.test.main;

import fr.djinn.main.entities.Adresse;
import fr.djinn.main.entities.ECFException;
import fr.djinn.main.entities.Societe;

/**
 * Classe SocieteStub qui étend la classe Societe.
 * Cette classe est utilisée pour fournir une version mockée de Societe.
 */
public class SocieteStub extends Societe {

    /**
     * Constructeur de la classe SocieteStub.
     *
     * @param adresse        L'adresse de la société.
     * @param adresseMail    L'adresse email de la société.
     * @param commentaire    Un commentaire concernant la société.
     * @param identifiant    L'identifiant unique de la société.
     * @param raisonSociale  La raison sociale de la société.
     * @param telephone      Le numéro de téléphone de la société.
     * @throws ECFException Si un des paramètres est invalide ou si une erreur se produit lors de la création.
     */
    public SocieteStub(Adresse adresse, String adresseMail, String commentaire, Integer identifiant, String raisonSociale, String telephone) throws ECFException {
        super(adresse, adresseMail, commentaire, identifiant, raisonSociale, telephone);
    }
}
