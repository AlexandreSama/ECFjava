package fr.djinn.main.entities;

import static fr.djinn.main.utils.RegEx.PATTERN_CODE_POSTAL;

public class Adresse {

    protected String numeroDeRue;
    protected String nomDeRue;
    protected String codePostal;
    protected String ville;

    public Adresse(String codePostal, String nomDeRue, String numeroDeRue, String ville) throws ECFException{
        setCodePostal(codePostal);
        setNomDeRue(nomDeRue);
        setNumeroDeRue(numeroDeRue);
        setVille(ville);
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) throws ECFException{
        if(codePostal == null || !PATTERN_CODE_POSTAL.matcher(codePostal).matches()){
            throw new ECFException("Code postal invalide");
        }else{
            this.codePostal = codePostal;
        }
    }

    public String getNomDeRue() {
        return nomDeRue;
    }

    public void setNomDeRue(String nomDeRue) {
        if(nomDeRue == null || nomDeRue.isEmpty()){
            throw new ECFException("Nom de rue invalide");
        }else{
            this.nomDeRue = nomDeRue;
        }
    }

    public String getNumeroDeRue() {
        return numeroDeRue;
    }

    public void setNumeroDeRue(String numeroDeRue) {
        if(numeroDeRue == null || numeroDeRue.isEmpty()){
            throw new ECFException("Numero de rue invalide");
        }else {
            this.numeroDeRue = numeroDeRue;
        }
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        if(ville == null || ville.isEmpty()){
            throw new ECFException("Ville invalide");
        }else {
            this.ville = ville;
        }
    }
}
