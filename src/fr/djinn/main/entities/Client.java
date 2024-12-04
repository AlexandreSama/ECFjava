package fr.djinn.main.entities;

public class Client extends Societe{

    private static int compteurIdentifiant = 1;
    private long chiffreAffaire;
    private int nbrEmploye;

    public Client(Adresse adresse, String adresseMail, String commentaire, String raisonSociale, String telephone, long chiffreAffaire, int nbrEmploye) {
        super(adresse, adresseMail, commentaire, compteurIdentifiant++, raisonSociale, telephone); // Génération de l'identifiant
        setChiffreAffaire(chiffreAffaire);
        setNbrEmploye(nbrEmploye);
    }

    public long getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(long chiffreAffaire) {
        if(chiffreAffaire > 200){
            this.chiffreAffaire = chiffreAffaire;
        }else{
            throw new ECFException("Le chiffre d'affaire doit être strictement supérieur a 200 ");
        }
    }

    public int getNbrEmploye() {
        return nbrEmploye;
    }

    public void setNbrEmploye(int nbrEmploye) {
        if(nbrEmploye > 0){
            this.nbrEmploye = nbrEmploye;
        }else{
            throw new ECFException("Le nombre d'employé doit être strictement supérieur a 0");
        }
    }
}
