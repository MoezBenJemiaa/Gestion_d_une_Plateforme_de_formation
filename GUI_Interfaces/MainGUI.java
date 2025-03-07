import javax.swing.*;
import java.awt.*;

public class MainGUI {
    static Etudiant etu;
    static Formateur f;

    //the start interface
    public static void showStartScreen(JFrame frame) {
        frame.getContentPane().removeAll(); // Clear existing components
        frame.setTitle("Gestion de Formation");

        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

       
        JLabel titleLabel = new JLabel("Gestion de Formation", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Action buttons
        String[] actions = {"Login", "Register", "Help", "Quit"};
        Runnable[] listeners = {
            () -> showLoginScreen(frame),
            () -> showRegisterScreen(frame),
            () ->showHelpDialog(),
            () -> {
                Manipulation_DB.closeConnection();
                System.exit(0);}
        };

        for (int i = 0; i < actions.length; i++) {
            JButton button;
            if ("Quit".equals(actions[i])) {
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

    //Login interface
    private static void showLoginScreen(JFrame frame)  {
        frame.getContentPane().removeAll(); 
        frame.setTitle("Login");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

       

        // Components for login
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = createStyledButton("Login");
        JButton backButton = createStyledButton("Back", new Color(255, 100, 100));

        // Add components to panel
        addComponentToPanel(panel, gbc, emailLabel, 0, 0);
        addComponentToPanel(panel, gbc, emailField, 1, 0);
        addComponentToPanel(panel, gbc, passwordLabel, 0, 1);
        addComponentToPanel(panel, gbc, passwordField, 1, 1);
        addComponentToPanel(panel, gbc, backButton, 0, 2);
        addComponentToPanel(panel, gbc, loginButton, 1, 2);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        loginButton.addActionListener(e -> {
            try {
                
            
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if ( ! All_exeptions.isValidEmail(email)) { throw new All_exeptions("email is not valid");}
                if ( ! Manipulation_DB_For_user.user_exist(email, password)) {throw new All_exeptions("user dosn't exist");}

                String name = Manipulation_DB_For_user.get_user_data(email, password, "nom");
                if (Manipulation_DB_For_user.get_user_data(email, password, "user_type").equalsIgnoreCase("Formateur")) {
                    f = new Formateur(name, email, password);
                    f.load_formation();
                    FormateurGUI.showFormateurInterface(f, frame);
                } else {
                    etu = new Etudiant(name, email, password);
                    EtudiantGUI.showEtudiantInterface(frame, etu);
                }
                
                
            } catch (All_exeptions exp) {
                JOptionPane.showMessageDialog(frame, exp.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> showStartScreen(frame));
    }

    //Registration interface
    private static void showRegisterScreen(JFrame frame) {
        frame.getContentPane().removeAll(); // Clear existing components
        frame.setTitle("Register");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components for registration
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JLabel userTypeLabel = new JLabel("User Type:");
        JComboBox<String> userTypeComboBox = new JComboBox<>(new String[]{"Formateur", "Etudiant"});
        JButton registerButton = createStyledButton("Register");
        JButton backButton = createStyledButton("Back", new Color(255, 100, 100));

        // Add components to panel
        addComponentToPanel(panel, gbc, nameLabel, 0, 0);
        addComponentToPanel(panel, gbc, nameField, 1, 0);
        addComponentToPanel(panel, gbc, emailLabel, 0, 1);
        addComponentToPanel(panel, gbc, emailField, 1, 1);
        addComponentToPanel(panel, gbc, passwordLabel, 0, 2);
        addComponentToPanel(panel, gbc, passwordField, 1, 2);
        addComponentToPanel(panel, gbc, userTypeLabel, 0, 3);
        addComponentToPanel(panel, gbc, userTypeComboBox, 1, 3);
        addComponentToPanel(panel, gbc, backButton, 0, 4);
        addComponentToPanel(panel, gbc, registerButton, 1, 4);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            try {
                if ( email.equals("") || name.equals("") || password.equals("")) { throw new All_exeptions("email or password or name not valid");}

                if ( ! All_exeptions.isValidEmail(email)) { throw new All_exeptions("email is not valid");}
                if ( ! Manipulation_DB_For_user.get_user_id(email).equals("")) {throw new All_exeptions("user exist with that email");}

                String userType = (String) userTypeComboBox.getSelectedItem();

                if (userType.equalsIgnoreCase("Formateur")) {
                    Manipulation_DB_For_user.add_user(name, email, password, "Formateur");
                    f = new Formateur(name, email, password);
                    FormateurGUI.showFormateurInterface(f, frame);
                } else {
                    Manipulation_DB_For_user.add_user(name, email, password, "Etudiant");
                    etu = new Etudiant(name, email, password);
                    EtudiantGUI.showEtudiantInterface(frame, etu);
                }
            } catch (All_exeptions exp) {
                JOptionPane.showMessageDialog(frame, exp.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> showStartScreen(frame));
    }

    private static void showHelpDialog() {
        JOptionPane.showMessageDialog(null, "Help: Select an option from the menu.", "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    //Method to creat a button with a blue color
    private static JButton createStyledButton(String text) {
        return createStyledButton(text, new Color(60, 140, 255)); // Default blue color
    }

    //Method to creat a button with a specified color
    private static JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 30));
        return button;
    }

    //Methode to add a Component to panell with specified place
    private static void addComponentToPanel(JPanel panel, GridBagConstraints gbc, Component component, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(component, gbc);
    }
}
