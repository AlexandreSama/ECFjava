package fr.djinn.main.entities;

import java.time.LocalDate;

public class Prospect extends Societe{

    private static int compteurIdentifiant = 1;
    private LocalDate dateProspection;
    private String estInteresse;

    public Prospect(Adresse adresse, String adresseMail, String commentaire, String raisonSociale, String telephone, LocalDate dateProspection, String estInteresse) {
        super(adresse, adresseMail, commentaire, compteurIdentifiant++, raisonSociale, telephone); // Génération de l'identifiant
        setDateProspection(dateProspection);
        setEstInteresse(estInteresse);
    }

    public LocalDate getDateProspection() {
        return dateProspection;
    }

    public void setDateProspection(LocalDate dateProspection) {
        this.dateProspection = dateProspection;
    }

    public String getEstInteresse() {
        return estInteresse;
    }

    public void setEstInteresse(String estInteresse) {
        this.estInteresse = estInteresse;
    }

    public static int getProchainIdentifiant() {
        return compteurIdentifiant;
    }
}
