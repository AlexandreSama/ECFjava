package fr.djinn.main.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Classe personnalisée pour formater les enregistrements de log dans un style lisible et organisé.
 * Le format inclut la date, l'heure, le niveau de log, le message, la classe source et la méthode source.
 */
public class LoggerFormat extends Formatter {

    /**
     * Formate un enregistrement de log dans un style lisible et organisé.
     *
     * @param record {@link LogRecord} : L'enregistrement de log contenant les détails de l'événement.
     * @return {@code String} : La chaîne formatée prête à être écrite dans le fichier de log.
     */
    @Override
    public String format(LogRecord record) {
        // Format de la date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Construction de la chaîne formatée
        StringBuilder result = new StringBuilder();
        result.append("[").append(dateFormat.format(new Date(record.getMillis()))).append("] "); // Date et heure
        result.append("[").append(record.getLevel()).append("] "); // Niveau de log
        result.append("Message: ").append(record.getMessage()).append(" | "); // Message
        result.append("Classe: ").append(record.getSourceClassName()).append(" | "); // Classe source
        result.append("Méthode: ").append(record.getSourceMethodName()).append("\n"); // Méthode source

        return result.toString();
    }
}
