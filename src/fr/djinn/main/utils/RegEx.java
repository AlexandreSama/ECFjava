package fr.djinn.main.utils;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Classe utilitaire contenant des expressions régulières et des formatteurs pour valider
 * et manipuler des données courantes dans l'application.
 */
public class RegEx {

    /**
     * Expression régulière pour valider les codes postaux français (5 chiffres).
     */
    public static final Pattern PATTERN_CODE_POSTAL = Pattern.compile("^[0-9]{5}$");

    /**
     * Expression régulière pour valider les numéros de téléphone français (10 chiffres).
     */
    public static final Pattern PATTERN_TELEPHONE = Pattern.compile("^[0-9]{10}$");

    /**
     * Expression régulière pour valider les adresses e-mail.
     * Le format attendu est : exemple@domaine.com.
     */
    public static final Pattern PATTERN_EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    /**
     * Formatteur de date pour les champs de type LocalDate.
     * Le format attendu est : "dd/MM/yyyy".
     */
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

}
