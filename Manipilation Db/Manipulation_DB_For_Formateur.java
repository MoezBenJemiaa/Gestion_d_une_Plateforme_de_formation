import java.sql.*;

public class Manipulation_DB_For_Formateur extends Manipulation_DB {
    
    public static int nbr_formations_proposer(String email,String motDePasse){
        Statement state = null;
        ResultSet result;
        int rowcount=0;
        try{
            state = con.createStatement();

            //get id formateur from email and password
            String id = Manipulation_DB_For_user.get_user_data(email, motDePasse, "id");
                               
            result = state.executeQuery("select count(formations.id_for) from  formations where  formations.id_formateur='"+id+"'");
            result.next();
            rowcount = result.getInt(1);
            
            }
        catch(SQLException e){
            System.err.println("Query error.");
            }
         return rowcount;
    }

    public static Formation[] formations_proposer(String email,String motDePasse){
        Statement state = null;
        ResultSet result;
        Formation[] f= new Formation[100];
        String titre ,description;  
        Formateur formateur;
        double prix;
        int i=0;
        try{
            state = con.createStatement();

            String id = Manipulation_DB_For_user.get_user_data(email, motDePasse, "id");         
            result = state.executeQuery("select titre,description,prix from  formations where  formations.id_formateur='"+id+"'");
            
        
                while(result.next()){
                    
                    titre = result.getString("titre");
                    description = result.getString("description");
                    prix=result.getDouble("prix");
                    formateur=new Formateur(Manipulation_DB_For_user.get_user_data(email, motDePasse, "nom"), email, motDePasse);
                    f[i]=new Formation(titre, description, formateur, prix);
                    i++;
                    
    

                }
            
            
            
            }
        catch(SQLException e){
            System.err.println("Query error.");
            }
            
         return f;
        }

    public static void insert_formation(Formation formation){
        PreparedStatement pstate;
        try{
            pstate = con.prepareStatement("insert into formations(titre, description, prix, id_formateur)"+
                                            "values(?,?,?,?)");
            pstate.setString(1, formation.getTitre());
            pstate.setString(2, formation.getDescription());
            pstate.setString(3, Double.toString(formation.getPrix()));
            pstate.setString(4, Manipulation_DB_For_user.get_user_data(formation.getFormateur().getEmail(),formation.getFormateur().getMotDePasse() , "id"));
            pstate.executeUpdate();
    
                
            }
        catch(SQLException e){
            System.err.println("Query error.");
            }
        }
    
    public static Formation get_formation(String titre,String email){
        Statement state = null;
        ResultSet result;
        Formateur formateur=new Formateur("", "", "");
        Formation f=new Formation("", "", formateur, 0);
        String description;
        double prix=0;
        try{
            state = con.createStatement();
    
            String id = Manipulation_DB_For_user.get_user_id(email);
            result = state.executeQuery("select prix,description from utilisateurs ,formations where  utilisateurs.id='"+id+"' and formations.id_formateur=utilisateurs.id and titre='"+titre+"'");
            if(result.next()){
                description = result.getString("description");
                prix=result.getDouble("prix");
                formateur=Manipulation_DB_For_user.get_user_information_from_id(id);
                f=new Formation(titre, description, formateur, prix);
                    
            }
            return f;
                
            }
        catch(SQLException e){
            System.err.println("Query error.");
            }
        return f;
        }

    public static void delet_fromation(Formation formation,String email,String pass){
        PreparedStatement pstate;
        try{
            pstate = con.prepareStatement("delete from formations where id_for = ?");
            pstate.setString(1,Manipulation_DB_For_Etudiant.get_formation_id(formation));
            pstate.executeUpdate();

            
            }
        catch(SQLException e){
            System.err.println("Query error.");
            }
        }
    }

    
