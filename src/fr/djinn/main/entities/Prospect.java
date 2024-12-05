package fr.djinn.main.entities;

import static fr.djinn.main.utils.ECFLogger.LOGGER;
import fr.djinn.main.utils.InterestedType;
import fr.djinn.main.utils.RegEx;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Représente un prospect avec des informations spécifiques telles que la date de prospection
 * et son statut d'intérêt.
 */
public class Prospect extends Societe {

    private static int compteurIdentifiant = 1;
    private LocalDate dateProspection;
    private InterestedType estInteresse;

    /**
     * Constructeur pour créer un prospect avec les informations spécifiées.
     *
     * @param adresse        Adresse du prospect.
     * @param adresseMail    Adresse e-mail du prospect.
     * @param commentaire    Commentaire sur le prospect.
     * @param raisonSociale  Raison sociale du prospect.
     * @param telephone      Numéro de téléphone du prospect.
     * @param dateProspection Date de prospection au format "dd/MM/yyyy".
     * @param estInteresse   Statut d'intérêt (OUI/NON).
     * @throws ECFException Si une validation échoue.
     */
    public Prospect(Adresse adresse, String adresseMail, String commentaire, String raisonSociale, String telephone, String dateProspection, String estInteresse) throws ECFException {
        super(adresse, adresseMail, commentaire, generateIdentifiant(), raisonSociale, telephone);
        setDateProspection(dateProspection);
        setEstInteresse(estInteresse);
    }

    /**
     * Retourne la date de prospection.
     *
     * @return Date de prospection.
     */
    public LocalDate getDateProspection() {
        return dateProspection;
    }

    /**
     * Définit la date de prospection après validation.
     *
     * @param dateProspection Date de prospection au format "dd/MM/yyyy".
     * @throws ECFException Si la date est invalide ou dans le futur.
     */
    public void setDateProspection(String dateProspection) throws ECFException {
        try {
            LocalDate parsedDate = LocalDate.parse(dateProspection, RegEx.FORMATTER);
            if (parsedDate.isAfter(LocalDate.now())) {
                logValidationError("Date de prospection dans le futur : " + dateProspection);
                throw new ECFException("La date de prospection ne peut pas être dans le futur.");
            }
            this.dateProspection = parsedDate;
        } catch (DateTimeParseException e) {
            logValidationError("Date de prospection invalide (format incorrect) : " + dateProspection);
            throw new ECFException("La date de prospection doit être au format 'dd/MM/yyyy'.");
        }
    }

    /**
     * Retourne le statut d'intérêt du prospect.
     *
     * @return Statut d'intérêt (OUI/NON).
     */
    public InterestedType getEstInteresse() {
        return estInteresse;
    }

    /**
     * Définit le statut d'intérêt du prospect après validation.
     *
     * @param estInteresse Statut d'intérêt (OUI/NON).
     * @throws ECFException Si le statut est invalide.
     */
    public void setEstInteresse(String estInteresse) throws ECFException {
        try {
            this.estInteresse = InterestedType.valueOf(estInteresse.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            logValidationError("Statut d'intérêt invalide : " + estInteresse);
            throw new ECFException("Le statut d'intérêt doit être 'OUI' ou 'NON'.");
        }
    }

    /**
     * Génère un identifiant unique pour un prospect.
     *
     * @return Identifiant unique.
     */
    public static int generateIdentifiant() {
        return compteurIdentifiant++;
    }

    public static void setCompteurIdentifiant(int value) {
        compteurIdentifiant = value;
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
