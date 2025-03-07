public class Etudiant extends Utilisateur {


    private Formation[] inscriptions = new Formation[100];
    private int n=0;


    public Etudiant(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse); 
        n=Manipulation_DB_For_Etudiant.nbr_formations_inscris(email,motDePasse);
        this.inscriptions  = Manipulation_DB_For_Etudiant.get_formations_inscris(email,motDePasse); 
    }

    public Formation[] getInscriptions() {
        return inscriptions;
    }

    public void sinscrireFormation(Formation formation) {
            Manipulation_DB_For_Etudiant.insert_inscrption(formation,super.getEmail(),super.getMotDePasse());
            inscriptions[n]=formation;
            n+=1;
     
    }

    public void dropFormation(Formation formation){
        Manipulation_DB_For_Etudiant.delet_formation(formation,super.getEmail(),super.getMotDePasse());
        n=Manipulation_DB_For_Etudiant.nbr_formations_inscris(super.getEmail(),super.getMotDePasse());
        this.inscriptions  = Manipulation_DB_For_Etudiant.get_formations_inscris(super.getEmail(),super.getMotDePasse()); 

    }

}
    