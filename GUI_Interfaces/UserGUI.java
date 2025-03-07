import javax.swing.*;
import java.awt.*;

public class UserGUI {
    static Utilisateur u;

    //Main interface for affcout settings
    static void modifyAccountSettings_interface(JFrame frame ,String page,Utilisateur ut){
        u=ut;

        frame.getContentPane().removeAll(); // Clear existing components
        frame.setTitle("Account Settings");

        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

       
        JLabel titleLabel = new JLabel("Account Settings", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Action buttons
        String[] actions = {"Change name", "Change email", "Change password", "Back"};
        Runnable[] listeners = {
            () -> set_user(frame,"nom",page),
            () -> set_user(frame,"email",page),
            () -> set_user(frame,"mot_de_pass",page),
            () ->{if (page.equals("e")) {
                    Etudiant etu=new Etudiant(u.getNom(), u.getEmail(), u.getMotDePasse());
                    EtudiantGUI.showEtudiantInterface(frame,etu);
                } else {
                    Formateur f=new Formateur(u.getNom(), u.getEmail(), u.getMotDePasse());
                    f.load_formation();
                    FormateurGUI.showFormateurInterface(f, frame);
                }} 
        };

        for (int i = 0; i < actions.length; i++) {
            JButton button;
            if ("Back".equals(actions[i])) {
                button = createStyledButton(actions[i], new Color(255, 100, 100)); 
            } else {
                button = createStyledButton(actions[i]);
            }
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 2;
            panel.add(button, gbc);
            int finalI = i; 
            button.addActionListener(e -> listeners[finalI].run());
        }

        frame.add(panel);
        frame.revalidate(); // Refresh frame
        frame.repaint();
        }
    
    //Method to creat a button with a blue color
    private static JButton createStyledButton(String text) {
        return createStyledButton(text, new Color(60, 140, 255)); // Default blue color
    }
    
    //Method to creat a button with a specified color
    private static JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE); // White text
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false); // Remove focus border
        button.setPreferredSize(new Dimension(200, 40)); // Uniform button size
        return button;
    }
        
    static void set_user(JFrame frame,String field,String page){
        frame.getContentPane().removeAll();
        
        if (field.equals("mot_de_pass")) {
            frame.setTitle("Set Password");
        }else{
            frame.setTitle("Set "+field);
        } 
        

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel fieldLabel ;

        if (field.equals("mot_de_pass")) {
            fieldLabel = new JLabel("new Password:");
        }else{
            fieldLabel = new JLabel(field+" :");
        }
        JTextField fieldField = new JTextField();
        JLabel passwordLabel;
        if (field.equals("mot_de_pass")) {
             passwordLabel = new JLabel("Old Password:");
        }else{
             passwordLabel = new JLabel("Password:");
        }
        
        JPasswordField passwordField = new JPasswordField();
        JButton setButton = createStyledButton("Set");
        JButton backButton = createStyledButton("Back", new Color(255, 100, 100));

        // Add components to panel
        addComponentToPanel(panel, gbc, fieldLabel, 0, 0);
        addComponentToPanel(panel, gbc, fieldField, 1, 0);
        addComponentToPanel(panel, gbc, passwordLabel, 0, 1);
        addComponentToPanel(panel, gbc, passwordField, 1, 1);
        addComponentToPanel(panel, gbc, backButton, 0, 2);
        addComponentToPanel(panel, gbc, setButton, 1, 2);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        setButton.addActionListener(e -> {
            try {
                String fieldValue = fieldField.getText();
                String password = new String(passwordField.getPassword());
                if ( ! u.getMotDePasse().equals(password)) {throw new All_exeptions("Password is not correct !!");}
                if ( fieldValue.equals("")) {throw new All_exeptions(field+" is Null !!");}

                if (field.equals("email") && ! All_exeptions.isValidEmail(fieldValue)) {throw new All_exeptions("Email is not valid !!");}
                if (field.equals("email") && ! Manipulation_DB_For_user.get_user_id(fieldValue).equals("")) {throw new All_exeptions("Email alreadyused  !!");}
                
                
                switch (field) {
                    case "nom":
                        u.setNom(fieldValue);
                        break;
                    case "email":
                        u.setEmail(fieldValue);
                        break;
                    case "mot_de_pass":
                        u.setMotDePasse(fieldValue);
                    break;
                        
                }
                JOptionPane.showMessageDialog(frame, "Set "+field+ " added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);    
                modifyAccountSettings_interface(frame,page,u);
                    
           
            } catch (Exception exp) {
                JOptionPane.showMessageDialog(frame, exp.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            

            
        });

        backButton.addActionListener(e -> modifyAccountSettings_interface(frame,page,u));

    }

    //Methode to add a Component to panell with specified place
    private static void addComponentToPanel(JPanel panel, GridBagConstraints gbc, Component component, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(component, gbc);
    }
}
        
    
    


