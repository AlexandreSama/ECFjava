package fr.djinn.main.entities;

import static fr.djinn.main.utils.ECFLogger.LOGGER;
import fr.djinn.main.utils.RegEx;

/**
 * Classe abstraite représentant une société.
 * Cette classe est étendue par des entités concrètes comme Client et Prospect.
 */
public abstract class Societe {

    private final Integer identifiant;
    private Adresse adresse;
    private String adresseMail;
    private String commentaire;
    private String telephone;
    private String raisonSociale;

    /**
     * Constructeur pour initialiser une société avec les informations de base.
     *
     * @param adresse        Adresse de la société.
     * @param adresseMail    Adresse e-mail de la société.
     * @param commentaire    Commentaire sur la société.
     * @param identifiant    Identifiant unique de la société.
     * @param raisonSociale  Raison sociale de la société.
     * @param telephone      Numéro de téléphone de la société.
     * @throws ECFException Si une validation échoue.
     */
    public Societe(Adresse adresse, String adresseMail, String commentaire, Integer identifiant, String raisonSociale, String telephone) throws ECFException {
        setAdresse(adresse);
        setAdresseMail(adresseMail);
        setCommentaire(commentaire);
        setTelephone(telephone);
        setRaisonSociale(raisonSociale);
        this.identifiant = identifiant;
    }

    /**
     * Retourne l'adresse de la société.
     *
     * @return Adresse de la société.
     */
    public Adresse getAdresse() {
        return adresse;
    }

    /**
     * Définit l'adresse de la société.
     *
     * @param adresse Adresse à définir.
     * @throws ECFException Si l'adresse est nulle.
     */
    public void setAdresse(Adresse adresse) {
        if (adresse == null) {
            logValidationError("Adresse invalide : null");
            throw new ECFException("L'adresse ne peut pas être nulle.");
        }
        this.adresse = adresse;
    }

    /**
     * Retourne l'adresse e-mail de la société.
     *
     * @return Adresse e-mail de la société.
     */
    public String getAdresseMail() {
        return adresseMail;
    }

    /**
     * Définit l'adresse e-mail de la société.
     *
     * @param adresseMail Adresse e-mail à définir.
     * @throws ECFException Si l'adresse e-mail est invalide.
     */
    public void setAdresseMail(String adresseMail) throws ECFException {
        if (adresseMail == null || !RegEx.PATTERN_EMAIL.matcher(adresseMail).matches()) {
            logValidationError("Adresse e-mail invalide : " + adresseMail);
            throw new ECFException("Adresse e-mail invalide. Format attendu : exemple@domaine.com.");
        }
        this.adresseMail = adresseMail;
    }

    /**
     * Retourne le commentaire sur la société.
     *
     * @return Commentaire sur la société.
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * Définit un commentaire pour la société.
     *
     * @param commentaire Commentaire à définir.
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire != null ? commentaire.trim() : null;
    }

    /**
     * Retourne l'identifiant unique de la société.
     *
     * @return Identifiant de la société.
     */
    public Integer getIdentifiant() {
        return identifiant;
    }

    /**
     * Retourne la raison sociale de la société.
     *
     * @return Raison sociale de la société.
     */
    public String getRaisonSociale() {
        return raisonSociale;
    }

    /**
     * Définit la raison sociale de la société.
     *
     * @param raisonSociale Raison sociale à définir.
     * @throws ECFException Si la raison sociale est invalide ou existe déjà.
     */
    public void setRaisonSociale(String raisonSociale) throws ECFException {
        if (raisonSociale == null || raisonSociale.trim().isEmpty()) {
            logValidationError("Raison sociale invalide : " + raisonSociale);
            throw new ECFException("La raison sociale ne peut pas être vide.");
        }

        if (isRaisonSocialeDuplicate(raisonSociale)) {
            logValidationError("Doublon pour la raison sociale : " + raisonSociale);
            throw new ECFException("La raison sociale existe déjà dans les bases de données.");
        }

        this.raisonSociale = raisonSociale;
    }

    /**
     * Retourne le numéro de téléphone de la société.
     *
     * @return Numéro de téléphone de la société.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Définit le numéro de téléphone de la société.
     *
     * @param telephone Numéro de téléphone à définir.
     * @throws ECFException Si le numéro de téléphone est invalide.
     */
    public void setTelephone(String telephone) throws ECFException {
        if (telephone == null || !RegEx.PATTERN_TELEPHONE.matcher(telephone).matches()) {
            logValidationError("Numéro de téléphone invalide : " + telephone);
            throw new ECFException("Numéro de téléphone invalide. Format attendu : 10 chiffres consécutifs.");
        }
        this.telephone = telephone;
    }

    /**
     * Vérifie si une raison sociale existe déjà dans les bases des clients ou prospects.
     *
     * @param raisonSociale Raison sociale à vérifier.
     * @return true si la raison sociale existe, false sinon.
     */
    private boolean isRaisonSocialeDuplicate(String raisonSociale) {
        return GestionClient.getClients().stream()
                .anyMatch(client -> client.getRaisonSociale().equalsIgnoreCase(raisonSociale)) ||
                GestionProspect.getProspects().stream()
                        .anyMatch(prospect -> prospect.getRaisonSociale().equalsIgnoreCase(raisonSociale));
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
