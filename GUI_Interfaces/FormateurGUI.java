import javax.swing.*;
import java.awt.*;

public class FormateurGUI {
    static Formateur formateur;

    //Add formation interface
    private static void showAddFormationScreen(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setTitle("Add Formation");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel descLabel = new JLabel("Description:");
        JTextField descField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();
        JButton addButton = createStyledButton("Add");
        JButton backButton = createStyledButton("Back", new Color(255, 100, 100));

        // Adding components
        addComponentToPanel(panel, gbc, titleLabel, 0, 0);
        addComponentToPanel(panel, gbc, titleField, 1, 0);
        addComponentToPanel(panel, gbc, descLabel, 0, 1);
        addComponentToPanel(panel, gbc, descField, 1, 1);
        addComponentToPanel(panel, gbc, priceLabel, 0, 2);
        addComponentToPanel(panel, gbc, priceField, 1, 2);
        addComponentToPanel(panel, gbc, backButton, 0, 3);
        addComponentToPanel(panel, gbc, addButton, 1, 3);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        addButton.addActionListener(e -> {
            try {
                String title = titleField.getText();
                String desc = descField.getText();
                double price= Double.parseDouble(priceField.getText());

                if (title.equals("")) {
                    throw new All_exeptions("titre est null");
                }
                if (price<0) {
                    throw new All_exeptions("Invalid price value");
                }

                Formation newFormation = new Formation(title, desc, formateur, price);
                formateur.ajouterFormation(newFormation);
                JOptionPane.showMessageDialog(frame, "Formation added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                showFormateurInterface(formateur, frame);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid price value.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (All_exeptions ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> showFormateurInterface(formateur, frame));
    }

    //Formation interface
    private static void showFormations(JFrame frame) {
        frame.getContentPane().removeAll();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));

        for (int i = 0; i < Manipulation_DB_For_Formateur.nbr_formations_proposer(formateur.getEmail(), formateur.getMotDePasse()); i++) {
            JPanel formationPanel = new JPanel();
            formationPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            formationPanel.setBackground(new Color(255, 255, 255));
            formationPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));
            formationPanel.setMaximumSize(new Dimension(450, 120));

            JLabel formationLabel = new JLabel("<html><b>" + formateur.getFormations()[i].getTitre() + "</b><br>" +
                    "descriptions: " + formateur.getFormations()[i].getDescription() +
                    "<br>Price: $" + formateur.getFormations()[i].getPrix() + "</html>");
                    
            formationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            formationLabel.setPreferredSize(new Dimension(300, 80));
            formationLabel.setForeground(new Color(50, 50, 50));

            formationPanel.add(formationLabel);

            JButton deleteButton = createStyledButton("DELETE", new Color(255, 100, 100));
            deleteButton.setPreferredSize(new Dimension(120, 40));

            final int index = i;
            deleteButton.addActionListener(e -> {
                formateur.delete_formation(formateur.getFormations()[index]);
                JOptionPane.showMessageDialog(frame, "Formation deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                showFormateurInterface(formateur, frame);
            });

            formationPanel.add(deleteButton);
            panel.add(formationPanel);
            panel.add(Box.createVerticalStrut(10));
        }

        JButton backButton = createStyledButton("Back", new Color(255, 100, 100));
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.addActionListener(e -> showFormateurInterface(formateur, frame));
        panel.add(backButton);

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    //Method to creat a button with a blue color
    private static JButton createStyledButton(String text) {
        return createStyledButton(text, new Color(60, 140, 255));
    }

    //Method to creat a button with a specified color
    private static JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 30));
        return button;
    }

    //Methode to add a Component to panell with specified place
    private static void addComponentToPanel(JPanel panel, GridBagConstraints gbc, Component component, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(component, gbc);
    }

    //Main interface for Formateur
    public static void showFormateurInterface(Formateur f, JFrame parentFrame){
        formateur = f;
        parentFrame.getContentPane().removeAll();
        parentFrame.setTitle("Formateur Dashboard");
    
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240)); // Light background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); 
    
        // Title Label
        JLabel titleLabel = new JLabel("Formateur: " + formateur.getNom(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50)); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
    
        String[] actions = {"Account settings", "Show Formations", "Add Formation"};
        JButton[] buttons = new JButton[actions.length];
    
        for (int i = 0; i < actions.length; i++) {
            buttons[i] = createStyledButton(actions[i]);
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 2;
            panel.add(buttons[i], gbc);
        }
    
        buttons[0].addActionListener(e -> 
            UserGUI.modifyAccountSettings_interface(parentFrame, "f",new Utilisateur(formateur.getNom(), formateur.getEmail(), formateur.getMotDePasse())));
        buttons[1].addActionListener(e -> showFormations(parentFrame));
        buttons[2].addActionListener(e -> showAddFormationScreen(parentFrame));
    
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

}
