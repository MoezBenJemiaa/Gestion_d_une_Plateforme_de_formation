public class Formateur extends Utilisateur {

    private Formation[] formations =new Formation[100];
    private int n=0;

  
    public Formateur(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse); 
        
        
    }
    
    public void load_formation(){
        n=Manipulation_DB_For_Formateur.nbr_formations_proposer(super.getEmail(),super.getMotDePasse());
        this.formations = Manipulation_DB_For_Formateur.formations_proposer(super.getEmail(),super.getMotDePasse()); 
    }

    public Formation[] getFormations() {
        load_formation();
        return formations;
    }

    public void ajouterFormation(Formation formation) {
        Manipulation_DB_For_Formateur.insert_formation(formation);
        formations[n]=formation;
        n+=1;
    }

    public void delete_formation(Formation formation){
        Manipulation_DB_For_Formateur.delet_fromation(formation,super.getEmail(),super.getMotDePasse());

    }


}
