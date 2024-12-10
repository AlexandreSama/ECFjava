package fr.djinn.main.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Adaptateur pour {@link LocalDate} permettant la sérialisation et la désérialisation JSON
 * en utilisant un format de date personnalisé ("dd/MM/yyyy") avec la bibliothèque Gson.
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    /**
     * Formateur pour analyser et formater les dates au format "dd/MM/yyyy".
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Sérialise un objet {@link LocalDate} en sa représentation JSON en utilisant le format spécifié.
     *
     * @param out   le {@link JsonWriter} utilisé pour écrire la sortie JSON
     * @param value la valeur {@link LocalDate} à sérialiser
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'écriture
     */
    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.format(FORMATTER));
        }
    }

    /**
     * Désérialise une chaîne JSON en un objet {@link LocalDate} en utilisant le format spécifié.
     *
     * @param in le {@link JsonReader} utilisé pour lire l'entrée JSON
     * @return l'objet {@link LocalDate} analysé
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la lecture
     * ou si l'analyse échoue
     */
    @Override
    public LocalDate read(JsonReader in) throws IOException {
        String date = in.nextString();
        return LocalDate.parse(date, FORMATTER);
    }
}
