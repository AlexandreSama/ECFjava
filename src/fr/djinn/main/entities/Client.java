package fr.djinn.main.entities;

public class Client extends Societe{

    private static int compteurIdentifiant = 1;
    private long chiffreAffaire;
    private int nbrEmploye;

    public Client(Adresse adresse, String adresseMail, String commentaire, String raisonSociale, String telephone, long chiffreAffaire, int nbrEmploye) {
        super(adresse, adresseMail, commentaire, compteurIdentifiant++, raisonSociale, telephone); // Génération de l'identifiant
        setChiffreAffaire(chiffreAffaire);
        setNbrEmploye(nbrEmploye);
        GestionClient.getClients().add(this);
    }

    public long getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(long chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }

    public int getNbrEmploye() {
        return nbrEmploye;
    }

    public void setNbrEmploye(int nbrEmploye) {
        this.nbrEmploye = nbrEmploye;
    }
}
