package fr.djinn.main.entities;

import static fr.djinn.main.utils.ECFLogger.LOGGER;

/**
 * Représente un client avec des informations spécifiques telles que le chiffre d'affaires
 * et le nombre d'employés.
 */
public class Client extends Societe {

    private static int compteurIdentifiant = 1;
    private long chiffreAffaire;
    private int nbrEmploye;

    /**
     * Constructeur pour créer un client avec les informations spécifiées.
     *
     * @param adresse        Adresse du client.
     * @param adresseMail    Adresse e-mail du client.
     * @param commentaire    Commentaire sur le client.
     * @param raisonSociale  Raison sociale du client.
     * @param telephone      Numéro de téléphone du client.
     * @param chiffreAffaire Chiffre d'affaires du client.
     * @param nbrEmploye     Nombre d'employés du client.
     * @throws ECFException Si une validation échoue.
     */
    public Client(Adresse adresse, String adresseMail, String commentaire, String raisonSociale, String telephone, long chiffreAffaire, int nbrEmploye) throws ECFException {
        super(adresse, adresseMail, commentaire, generateIdentifiant(), raisonSociale, telephone);
        setChiffreAffaire(chiffreAffaire);
        setNbrEmploye(nbrEmploye);
    }

    /**
     * Retourne le chiffre d'affaires du client.
     *
     * @return Chiffre d'affaires.
     */
    public long getChiffreAffaire() {
        return chiffreAffaire;
    }

    /**
     * Définit le chiffre d'affaires du client après validation.
     *
     * @param chiffreAffaire Chiffre d'affaires à définir.
     * @throws ECFException Si le chiffre d'affaires est inférieur ou égal à 200.
     */
    public void setChiffreAffaire(long chiffreAffaire) throws ECFException {
        if (chiffreAffaire <= 200) {
            logValidationError("Chiffre d'affaires invalide : " + chiffreAffaire);
            throw new ECFException("Le chiffre d'affaires doit être strictement supérieur à 200.");
        }
        this.chiffreAffaire = chiffreAffaire;
    }

    /**
     * Retourne le nombre d'employés du client.
     *
     * @return Nombre d'employés.
     */
    public int getNbrEmploye() {
        return nbrEmploye;
    }

    /**
     * Définit le nombre d'employés du client après validation.
     *
     * @param nbrEmploye Nombre d'employés à définir.
     * @throws ECFException Si le nombre d'employés est inférieur ou égal à 0.
     */
    public void setNbrEmploye(int nbrEmploye) throws ECFException {
        if (nbrEmploye <= 0) {
            logValidationError("Nombre d'employés invalide : " + nbrEmploye);
            throw new ECFException("Le nombre d'employés doit être strictement supérieur à 0.");
        }
        this.nbrEmploye = nbrEmploye;
    }

    /**
     * Génère un identifiant unique pour un client.
     *
     * @return Identifiant unique.
     */
    public static int generateIdentifiant() {
        return compteurIdentifiant++;
    }

    /**
     * Enregistre une erreur de validation dans le logger.
     *
     * @param message Message d'erreur.
     */
    private void logValidationError(String message) {
        LOGGER.warning(message);
    }
}
