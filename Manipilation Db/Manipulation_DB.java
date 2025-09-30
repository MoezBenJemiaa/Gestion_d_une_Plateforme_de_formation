import java.sql.*;

public class Manipulation_DB {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String connection = "jdbc:mysql://localhost:3306/Gestion_Formation"; 
    private static String user = "";                  
    private static String password = "";  //Mysql password    


    public static Connection con = null;


    public static void mysqlConnect(){
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(connection, user, password);
            }
        catch(ClassNotFoundException e){
            System.err.println("Couldn't load driver.");
            }
        catch(SQLException e){
            System.err.println("Couldn't connect to database.");
            }
        }
    
    
    public static void closeConnection(){
        try{
            if(!con.isClosed()){
                con.close();
                }
            }
        catch(NullPointerException e){
            System.err.println("Couldn't load driver.");
            }
        catch(SQLException e){
            System.err.println("Couldn't close database.");
            }
        }

    }
    



    
    
    
    


    

    
