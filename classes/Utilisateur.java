public class Utilisateur {

    private String nom;
    private String email;
    private String mdp;

    public Utilisateur(String nom, String email, String mdp) {
        this.nom = nom;
        this.email = email;
        this.mdp = mdp;
    }

    //Get and set for all attributs
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
        Manipulation_DB_For_user.set_field_value(getEmail(),getMotDePasse(),"nom",nom);

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        Manipulation_DB_For_user.set_field_value(getEmail(),getMotDePasse(),"email",email);

    }

    public String getMotDePasse() {
        return mdp;
    }

    public void setMotDePasse(String mdp) {
        this.mdp = mdp;
        Manipulation_DB_For_user.set_field_value(getEmail(),getMotDePasse(),"mot_de_pass",mdp);

    }

}
