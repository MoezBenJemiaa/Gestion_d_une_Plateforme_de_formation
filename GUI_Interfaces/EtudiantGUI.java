import javax.swing.*;
import java.awt.*;

public class EtudiantGUI {
    static Etudiant etu;

    //Main interface fo etudiant
    public static void showEtudiantInterface(JFrame parentFrame, Etudiant etudiant) {
    etu = etudiant;
    parentFrame.getContentPane().removeAll();
    parentFrame.setTitle("Etudiant Dashboard");

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(new Color(240, 240, 240)); // Light background color
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10); 

    // Title Label
    JLabel titleLabel = new JLabel("Etudiant: " + etu.getNom(), JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    titleLabel.setForeground(new Color(50, 50, 50)); 
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    panel.add(titleLabel, gbc);

    String[] actions = {"Account settings", "Show inscriptions", "Show available formations"};
    JButton[] buttons = new JButton[actions.length];

    for (int i = 0; i < actions.length; i++) {
        buttons[i] = createStyledButton(actions[i]);
        gbc.gridx = 0;
        gbc.gridy = i + 1;
        gbc.gridwidth = 2;
        panel.add(buttons[i], gbc);
    }

    buttons[0].addActionListener(e -> 
        UserGUI.modifyAccountSettings_interface(parentFrame, "e",new Utilisateur(etu.getNom(), etu.getEmail(), etu.getMotDePasse())));
    buttons[1].addActionListener(e -> showInscriptions(parentFrame));
    buttons[2].addActionListener(e -> showAvailableFormations(parentFrame));

    JButton backButton = createStyledButton("Back", new Color(255, 100, 100)); // Red for back
    JButton quitButton = createStyledButton("Quit", new Color(180, 0, 0)); // Dark red for quit

    backButton.addActionListener(e -> MainGUI.showStartScreen(parentFrame));
    quitButton.addActionListener(e -> {
        Manipulation_DB.closeConnection();
        System.exit(0);}
        );

    // Add Back button
    gbc.gridx = 0;
    gbc.gridy = actions.length + 1;
    gbc.gridwidth = 1;
    panel.add(backButton, gbc);

    // Add Quit button
    gbc.gridx = 1;
    panel.add(quitButton, gbc);

    // Add panel to frame
    JScrollPane scrollPane = new JScrollPane(panel);
    parentFrame.add(scrollPane);
    parentFrame.revalidate();
    parentFrame.repaint();
}

    private static JButton createStyledButton(String text) {
    return createStyledButton(text, new Color(60, 140, 255)); // Default blue color
}

    private static JButton createStyledButton(String text, Color backgroundColor) {
    JButton button = new JButton(text);
    button.setBackground(backgroundColor);
    button.setForeground(Color.WHITE); // White text
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setFocusPainted(false); // Remove focus border
    button.setPreferredSize(new Dimension(200, 40)); // Uniform button size
    return button;
}

    //all formation interface
    private static void showAvailableFormations(JFrame frame) {
        frame.getContentPane().removeAll();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);  
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240)); 
    
        Formation[] formations = Manipulation_DB_For_Etudiant.get_Formatins_not_inscrit(etu.getEmail(), etu.getMotDePasse());
    
        for (int i = 0; i < Manipulation_DB_For_Etudiant.get_nbr_Formation_not_inscrit(etu.getEmail(), etu.getMotDePasse()); i++) {
            // Create a panel for each formation with spacing and styling
            JPanel formationPanel = new JPanel();
            formationPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));  // Left-aligned with padding
            formationPanel.setBackground(new Color(255, 255, 255)); // White background for each formation
            formationPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));  // Light border for separation
            formationPanel.setMaximumSize(new Dimension(450, 120));  // Max height for better control
    
            // Create and style formation details label
            JLabel formationLabel = new JLabel("<html><b>" + formations[i].getTitre() + "</b><br>" + 
                                              "Formateur Email: " + formations[i].getFormateur().getEmail() + "<br>"+
                                              "descriptions: " + formations[i].getDescription() +
                                              "<br>Price: $" + formations[i].getPrix() + "</html>");
            formationLabel.setFont(new Font("Arial", Font.PLAIN, 14)); 
            formationLabel.setPreferredSize(new Dimension(300, 80)); 
            formationLabel.setForeground(new Color(50, 50, 50)); 
    
            formationPanel.add(formationLabel); 
    
            JButton inscriptionButton = new JButton("Inscription");
            inscriptionButton.setBackground(new Color(60, 140, 255)); 
            inscriptionButton.setForeground(Color.WHITE);  
            inscriptionButton.setFont(new Font("Arial", Font.BOLD, 14)); 
            inscriptionButton.setPreferredSize(new Dimension(120, 40));  
            inscriptionButton.setFocusPainted(false);  
    
            final int index = i;  
            inscriptionButton.addActionListener(e -> {
                etu.sinscrireFormation(formations[index]);
                JOptionPane.showMessageDialog(frame, "Inscription added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                showEtudiantInterface(frame, etu);
            });
    
            formationPanel.add(inscriptionButton);  // Add the button to the panel
    
            // Add the formation panel to the main panel
            panel.add(formationPanel);
            panel.add(Box.createVerticalStrut(10));  // Add vertical spacing between each formation panel
        }
    
        // Add a back button at the bottom of the panel
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 40));  
        backButton.setBackground(new Color(255, 100, 100));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));  
        backButton.setFocusPainted(false); 
    
        backButton.addActionListener(e -> showEtudiantInterface(frame, etu));
        panel.add(backButton);
        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);
    
        frame.setVisible(true);  
    }

    //inscription Interfaces
    private static void showInscriptions(JFrame frame) {
        frame.getContentPane().removeAll();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);  
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        
        for (int i = 0; i < Manipulation_DB_For_Etudiant.nbr_formations_inscris(etu.getEmail(),etu.getMotDePasse()); i++) {
           
            JPanel formationPanel = new JPanel();
            formationPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));  // Left-aligned with padding
            formationPanel.setBackground(new Color(255, 255, 255)); 
            formationPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));  // Light border for separation
            formationPanel.setMaximumSize(new Dimension(450, 120));  // Max height for better control
    
            // Create and style formation details label
            JLabel formationLabel = new JLabel("<html><b>" + etu.getInscriptions()[i].getTitre() + "</b><br>" + 
                                              "Formateur Email: " + etu.getInscriptions()[i].getFormateur().getEmail() + "<br>"+
                                              "descriptions: " + etu.getInscriptions()[i].getDescription() +
                                              "<br>Price: $" + etu.getInscriptions()[i].getPrix() + "</html>");
            formationLabel.setFont(new Font("Arial", Font.PLAIN, 14));  // Set font for better readability
            formationLabel.setPreferredSize(new Dimension(300, 80)); // Adjust size to fit label text
            formationLabel.setForeground(new Color(50, 50, 50)); // Dark text for good contrast
    
            formationPanel.add(formationLabel);  // Add the label to the formation panel
    
            // Create and style the "Inscription" button
            JButton inscriptionButton = new JButton("DROP");
            inscriptionButton.setBackground(new Color(60, 140, 255));  
            inscriptionButton.setForeground(Color.WHITE); 
            inscriptionButton.setFont(new Font("Arial", Font.BOLD, 14)); 
            inscriptionButton.setPreferredSize(new Dimension(120, 40));  
            inscriptionButton.setFocusPainted(false);  
    
            final int index = i;  // Final index to use in the ActionListener
            inscriptionButton.addActionListener(e -> {
                etu.dropFormation(etu.getInscriptions()[index]);
                JOptionPane.showMessageDialog(frame, "successfully! Drop", "Success", JOptionPane.INFORMATION_MESSAGE);
                showEtudiantInterface(frame, etu);
            });
    
            formationPanel.add(inscriptionButton);  // Add the button to the panel
    
            // Add the formation panel to the main panel
            panel.add(formationPanel);
            panel.add(Box.createVerticalStrut(10));  
        }
    
        // Add a back button at the bottom of the panel
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 40));  
        backButton.setBackground(new Color(255, 100, 100));
        backButton.setForeground(Color.WHITE); 
        backButton.setFont(new Font("Arial", Font.BOLD, 14)); 
        backButton.setFocusPainted(false); 

        backButton.addActionListener(e -> showEtudiantInterface(frame, etu));
        panel.add(backButton);  
        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);
    
        frame.setVisible(true); 
    }
}    
