import java.sql.*;

    public class Manipulation_DB_For_user extends Manipulation_DB {
        public static boolean user_exist(String email,String password){
            Statement state = null;
            ResultSet result;
            try{
                state = con.createStatement();
                
                result = state.executeQuery("select * from utilisateurs where email='"+email+"' and mot_de_pass='"+password+"' ");
                return result.next();
            }
            catch(SQLException e){
                System.err.println("Query error.");
                return false;
            }
            catch(NullPointerException e){
                return false;
            }
            
                

            }

        public static String get_user_data(String email,String password,String data_name){
        Statement state = null;
        ResultSet res;
        try{
        
            state = con.createStatement();
            res = state.executeQuery("select "+data_name+" from utilisateurs where email='"+email+"' and mot_de_pass='"+password+"' ");
        
            if (res.next()) {
                return res.getString(data_name);
            }
            return "";
    
        }
        catch(SQLException e){
            System.err.println("Query error.");
            return "";

        }
        catch(NullPointerException e){
            return "";
        }
        

    }
        
        public static Formateur get_user_information_from_id(String id){
            Statement state = null;
            String email ,password,nom;
            Formateur f=new Formateur("", "", "");
            ResultSet res;
            try{
                state = con.createStatement();
                res = state.executeQuery("select nom,email,mot_de_pass from utilisateurs where id='"+id+"'");
                if (res.next()) {
                    nom=res.getString("nom");
                    email=res.getString("email");
                    password=res.getString("mot_de_pass");
                    f=new Formateur(nom, email, password);
                }
                return f;
                
                }
            catch(SQLException e){
                System.err.println("Query error.");
                return f;
                }
            
            }

        public static void add_user(String name,String email,String password,String user_type) {
            PreparedStatement pstate;
            try{
                pstate = con.prepareStatement("insert into utilisateurs(nom, email, mot_de_pass, user_type)"+
                                                    "values(?,?,?,?)");
                pstate.setString(1, name);
                pstate.setString(2, email);
                pstate.setString(3, password);
                pstate.setString(4, user_type);
                pstate.executeUpdate();
        
                }
            catch(SQLException e){
                System.err.println("Query error.");
                }
            }
             
        public static String get_user_id(String email){
            Statement state = null;
            ResultSet result;
            String id="";
            try {
                state = con.createStatement();
                result = state.executeQuery("select id from utilisateurs where email='"+email+"'");
                
                if (result.next()) {
                        id=result.getString("id");
                }
                return id;
            } catch (SQLException e) {
                System.err.println("Query error.");
            }
            return id;
            }
        
        public static void set_field_value(String email,String pass,String field,String fieldValue){
            PreparedStatement pstate;
            try{
                pstate = con.prepareStatement("update utilisateurs set "+field+" = ? where id = ? ");
                
                pstate.setString(1, fieldValue);
                pstate.setString(2, get_user_data(email, pass, "id"));
                pstate.executeUpdate();
    
                
                }
            catch(SQLException e){
                System.err.println("Query error."+e.getMessage());
                }
        }
        
        }
