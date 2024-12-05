package fr.djinn.main.entities;

import fr.djinn.main.utils.RegEx;
import static fr.djinn.main.utils.ECFLogger.LOGGER;

/**
 * Représente une adresse avec des champs tels que le numéro de rue, le nom de rue,
 * le code postal et la ville. Les valeurs sont validées avant d'être enregistrées.
 */
public class Adresse {

    protected String numeroDeRue;
    protected String nomDeRue;
    protected String codePostal;
    protected String ville;

    /**
     * Constructeur pour initialiser une adresse avec les informations fournies.
     *
     * @param codePostal   Code postal de l'adresse.
     * @param nomDeRue     Nom de la rue.
     * @param numeroDeRue  Numéro de la rue.
     * @param ville        Ville de l'adresse.
     * @throws ECFException Si une des validations échoue.
     */
    public Adresse(String codePostal, String nomDeRue, String numeroDeRue, String ville) throws ECFException {
        setCodePostal(codePostal);
        setNomDeRue(nomDeRue);
        setNumeroDeRue(numeroDeRue);
        setVille(ville);
    }

    /**
     * Retourne le code postal de l'adresse.
     *
     * @return Code postal.
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Définit le code postal après validation.
     *
     * @param codePostal Code postal à définir.
     * @throws ECFException Si le code postal est invalide (format attendu : 5 chiffres).
     */
    public void setCodePostal(String codePostal) throws ECFException {
        if (codePostal == null || !RegEx.PATTERN_CODE_POSTAL.matcher(codePostal).matches()) {
            logValidationError("Code postal invalide : " + codePostal);
            throw new ECFException("Code postal invalide. Assurez-vous qu'il contient 5 chiffres.");
        }
        this.codePostal = codePostal;
    }

    /**
     * Retourne le nom de la rue de l'adresse.
     *
     * @return Nom de la rue.
     */
    public String getNomDeRue() {
        return nomDeRue;
    }

    /**
     * Définit le nom de la rue après validation.
     *
     * @param nomDeRue Nom de la rue à définir.
     * @throws ECFException Si le nom de la rue est vide ou null.
     */
    public void setNomDeRue(String nomDeRue) throws ECFException {
        validateNonEmptyField(nomDeRue, "Nom de rue invalide.");
        this.nomDeRue = nomDeRue;
    }

    /**
     * Retourne le numéro de la rue de l'adresse.
     *
     * @return Numéro de la rue.
     */
    public String getNumeroDeRue() {
        return numeroDeRue;
    }

    /**
     * Définit le numéro de la rue après validation.
     *
     * @param numeroDeRue Numéro de la rue à définir.
     * @throws ECFException Si le numéro de la rue est vide ou null.
     */
    public void setNumeroDeRue(String numeroDeRue) throws ECFException {
        validateNonEmptyField(numeroDeRue, "Numéro de rue invalide.");
        this.numeroDeRue = numeroDeRue;
    }

    /**
     * Retourne la ville de l'adresse.
     *
     * @return Ville.
     */
    public String getVille() {
        return ville;
    }

    /**
     * Définit la ville après validation.
     *
     * @param ville Ville à définir.
     * @throws ECFException Si la ville est vide ou null.
     */
    public void setVille(String ville) throws ECFException {
        validateNonEmptyField(ville, "Ville invalide.");
        this.ville = ville;
    }

    /**
     * Valide qu'un champ n'est ni null ni vide.
     *
     * @param value       La valeur à valider.
     * @param errorMessage Le message d'erreur en cas de validation échouée.
     * @throws ECFException Si la validation échoue.
     */
    private void validateNonEmptyField(String value, String errorMessage) throws ECFException {
        if (value == null || value.trim().isEmpty()) {
            logValidationError(errorMessage + " Valeur reçue : " + value);
            throw new ECFException(errorMessage);
        }
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
