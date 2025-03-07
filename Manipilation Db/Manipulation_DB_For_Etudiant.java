import java.sql.*;

public class Manipulation_DB_For_Etudiant extends Manipulation_DB{
    
    public static int nbr_formations_inscris(String email,String pass){
        Statement state = null;
        ResultSet result;
        int rowcount=0;
        try{
            state = con.createStatement();

            String id = Manipulation_DB_For_user.get_user_data(email, pass, "id");
                               
            result = state.executeQuery("select count(inscriptions.id_For) from utilisateurs ,inscriptions where utilisateurs.id= inscriptions.id_Etu and utilisateurs.id='"+id+"'");
            result.next();
            rowcount = result.getInt(1);
            
            }
        catch(SQLException e){
            System.err.println("Query error.");
            }
         return rowcount;
        }
        
    public static Formation[] get_formations_inscris(String email,String pass){
        Statement state = null;
        ResultSet result;
        Formation[] f= new Formation[100];
        String titre ,description;
        Formateur formateur;
        double prix;
        int i=0;
        try{
            state = con.createStatement();
    
            String id = Manipulation_DB_For_user.get_user_data(email, pass, "id");
                                   
            result = state.executeQuery("select titre,description,prix,id_formateur from utilisateurs ,inscriptions,formations where utilisateurs.id= inscriptions.id_Etu and utilisateurs.id='"+id+"' and formations.id_for=inscriptions.id_For");
            while(result.next()){
                titre = result.getString("titre");
                description = result.getString("description");
                prix=result.getDouble("prix");
                formateur=Manipulation_DB_For_user.get_user_information_from_id(result.getString("id_formateur"));
                f[i]=new Formation(titre, description, formateur, prix);
                i++;
            }
            return f;
                
            }
        catch(SQLException e){
            System.err.println("Query error.");
            }
        return f;
        }

    public static void insert_inscrption(Formation formation,String email,String pass){
        PreparedStatement pstate;
        try{
            //using PreparedStatement
            pstate = con.prepareStatement("insert into inscriptions(id_Etu, id_For)"+
                                                "values(?,?)");
            pstate.setString(1, Manipulation_DB_For_user.get_user_data(email, pass, "id"));
            pstate.setString(2, get_formation_id(formation));
            pstate.executeUpdate();
            
                
            }
        catch(SQLException e){
            System.err.println("Query error.");
            }
        }
    
    public static String get_formation_id(Formation formation){
        Statement state = null;
        ResultSet result;
        String idf="";
        try{
            state = con.createStatement();
    
            String id = Manipulation_DB_For_user.get_user_data(formation.getFormateur().getEmail(), formation.getFormateur().getMotDePasse(), "id");       
            result = state.executeQuery("select id_for from formations where  titre='"+formation.getTitre()+"' and id_formateur='"+id+"'");
            if(result.next()){
                idf = result.getString("id_for");
                   
            }
            return idf;
                
            }
        catch(SQLException e){
            System.err.println("Query error.");
            }
        return idf;
        }
    
    public static int get_nbr_Formation_not_inscrit(String email,String pass){
        Statement state = null;
        ResultSet result;
            
        try{
            state = con.createStatement();
        
            String id = Manipulation_DB_For_user.get_user_data(email, pass, "id");
                                       
            result = state.executeQuery("SELECT count(id_For) FROM formations WHERE formations.id_for NOT IN (SELECT id_For FROM Gestion_Formation.inscriptions WHERE id_Etu = '"+id+"')");
                    
            if(result.next()){
                return result.getInt(1);
            }
            return 0;
                    
            }
        catch(SQLException e){
            System.err.println("Query error.");
            }
        return 0;
        }

    public static Formation[]  get_Formatins_not_inscrit(String email,String pass){
        Statement state = null;
        ResultSet result;
        Formation[] f= new Formation[100];
        String titre ,description,ch="";
        Formateur formateur;
        double prix;
        int i=0;
        try{
            state = con.createStatement();
        
            String id = Manipulation_DB_For_user.get_user_data(email, pass, "id");
                                       
            result = state.executeQuery("SELECT titre, description, prix, id_formateur FROM formations WHERE formations.id_for NOT IN (SELECT id_For FROM Gestion_Formation.inscriptions WHERE id_Etu = '"+id+"')");
                    
            while(result.next()){
                titre = result.getString("titre");
                description = result.getString("description");
                prix=result.getDouble("prix");
                formateur=Manipulation_DB_For_user.get_user_information_from_id(result.getString("id_formateur"));
                f[i]=new Formation(titre, description, formateur, prix);
                ch=ch+"Formation "+(i+1)+" :\n"+f[i].toString()+"\n";
                ch=ch+"\n";
                i++;
            }
            return f;
                    
                }
        catch(SQLException e){
            System.err.println("Query error.");
            }
        return f;
            }
    
    public static void delet_formation(Formation formation,String email,String pass){
        PreparedStatement pstate;
        try{
            pstate = con.prepareStatement("delete from inscriptions where id_Etu = ? and id_For = ?");
            pstate.setString(1,Manipulation_DB_For_user.get_user_data(email, pass, "id"));
            pstate.setString(2,get_formation_id(formation));
            pstate.executeUpdate();

           
            }
        catch(SQLException e){
            System.err.println("Query error.");
            }
        }
    }
    
