package fr.djinn.main.entities;

import static fr.djinn.main.utils.RegEx.PATTERN_TELEPHONE;
import static fr.djinn.main.utils.RegEx.PATTERN_EMAIL;

public abstract class Societe {

    private final Integer identifiant;
    private Adresse adresse;
    private String adresseMail;
    private String commentaire;
    private String telephone;
    private String raisonSociale;

    public Societe(Adresse adresse, String adresseMail, String commentaire, Integer identifiant, String raisonSociale, String telephone) throws ECFException{
        setAdresse(adresse);
        setAdresseMail(adresseMail);
        setCommentaire(commentaire);
        setTelephone(telephone);
        setRaisonSociale(raisonSociale);
        this.identifiant = identifiant;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) throws ECFException{
        if(adresseMail == null || !PATTERN_EMAIL.matcher(adresseMail).matches()) {
            throw new ECFException("Adresse mail invalide");
        }else {
            this.adresseMail = adresseMail;
        }
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Integer getIdentifiant() {
        return identifiant;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) throws ECFException {

        if (raisonSociale == null || raisonSociale.trim().isEmpty()) {
            throw new ECFException("La raison sociale ne peut pas être vide !");
        }

        // Boucle pour vérifier les doublons dans la liste des clients
        for (Client client : GestionClient.getClients()) {
            if (client.getRaisonSociale().equalsIgnoreCase(raisonSociale) && client != this) {
                throw new ECFException("La raison sociale existe déjà parmi les clients !");
            }
        }

        // Vérifier les doublons dans la liste des prospects
        for (Prospect prospect : GestionProspect.getProspects()) {
            if (prospect.getRaisonSociale().equalsIgnoreCase(raisonSociale) && prospect != this) {
                throw new IllegalArgumentException("La raison sociale existe déjà parmi les prospects !");
            }
        }

        this.raisonSociale = raisonSociale;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) throws ECFException {
        if(telephone == null || !PATTERN_TELEPHONE.matcher(telephone).matches()) {
            throw new ECFException("Numéro de téléphone incorrecte");
        }else{
            this.telephone = telephone;
        }
    }
}
