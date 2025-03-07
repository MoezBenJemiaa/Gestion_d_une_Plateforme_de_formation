import javax.swing.JFrame;

public class Main {
    
    public static void main(String[] args){
        //connect to data basse
        Manipulation_DB.mysqlConnect();

        //Frame(window) Declaration
        JFrame frame = new JFrame("Gestion de Formation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);// Center the frame on the screen
        frame.setVisible(true);

        //run programe
        MainGUI.showStartScreen(frame);

    }
}
