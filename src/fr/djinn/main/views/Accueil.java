package fr.djinn.main.views;

import fr.djinn.main.Main;
import fr.djinn.main.entities.Client;
import fr.djinn.main.entities.GestionClient;
import fr.djinn.main.entities.GestionProspect;
import fr.djinn.main.entities.Prospect;
import fr.djinn.main.utils.ActionType;
import fr.djinn.main.utils.EntityType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Vue principale de l'application permettant de gérer les entités (Clients ou Prospects).
 * Permet de naviguer entre les différentes actions (CRUD) sur les entités.
 */
public class Accueil extends JFrame {
    private JPanel contentPane;
    private JButton buttonQuitter;
    private JPanel firstPanel;
    private JPanel crudPanel;
    private JPanel choosePanel;
    private JLabel crudPanelTitleLabel;
    private JLabel firstPanelTitleLabel;
    private JLabel choosePanelTitleLabel;

    private JButton clientButton;
    private JButton prospectButton;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton readButton;
    private JComboBox<String> selectSocieteComboBox;
    private JButton validerButton;
    private JButton retourButton;

    private EntityType entityType;
    private ActionType actionType;

    /**
     * Constructeur de la classe `Accueil`.
     * Initialise les composants graphiques et configure les écouteurs d'événements.
     */
    public Accueil() {
        initComponents();
        listeners();
    }

    /**
     * Initialise les composants graphiques de la fenêtre.
     */
    private void initComponents() {
        setContentPane(contentPane);
        setSize(900, 600);
        setTitle("ECF Exo : Accueil");
        ImageIcon img = new ImageIcon(Main.class.getResource("/images/logo.jpg"));
        setIconImage(img.getImage());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        retourButton.setVisible(false);
        crudPanel.setVisible(false);
        choosePanel.setVisible(false);
    }

    /**
     * Configure les écouteurs d'événements pour les composants graphiques.
     */
    private void listeners() {
        buttonQuitter.addActionListener(e -> onCancel());

        clientButton.addActionListener(e -> handleEntitySelection(EntityType.CLIENT, "clients"));

        prospectButton.addActionListener(e -> handleEntitySelection(EntityType.PROSPECT, "prospects"));

        updateButton.addActionListener(e -> handleActionSelection(ActionType.UPDATE, "Quelle société voulez-vous mettre à jour ?"));

        deleteButton.addActionListener(e -> handleActionSelection(ActionType.DELETE, "Quelle société voulez-vous supprimer ?"));

        validerButton.addActionListener(e -> handleValidation());

        createButton.addActionListener(e -> {
            new Crud(entityType).setVisible(true);
            dispose();
        });

        readButton.addActionListener(e -> {
            new ListView(entityType).setVisible(true);
            dispose();
        });

        retourButton.addActionListener(e -> retour());
    }

    /**
     * Gère la sélection d'une entité (Client ou Prospect).
     * Configure l'interface pour afficher les actions disponibles pour l'entité choisie.
     *
     * @param type Le type d'entité sélectionnée (CLIENT ou PROSPECT).
     * @param pluralLabel Libellé au pluriel pour l'entité (ex. "clients").
     */
    private void handleEntitySelection(EntityType type, String pluralLabel) {
        entityType = type;
        crudPanelTitleLabel.setText("Que souhaitez-vous faire avec les " + pluralLabel + " ?");
        updateCrudPanelTexts(type == EntityType.CLIENT ? "client" : "prospect", pluralLabel);
        crudPanel.setVisible(true);
        retourButton.setVisible(true);
    }

    /**
     * Gère la sélection d'une action (CREATE, UPDATE ou DELETE) pour l'entité en cours.
     * Configure l'interface pour afficher la liste des entités à modifier ou supprimer.
     *
     * @param action L'action sélectionnée (CREATE, UPDATE ou DELETE).
     * @param labelText Texte affiché pour guider l'utilisateur.
     */
    private void handleActionSelection(ActionType action, String labelText) {
        actionType = action;
        populateComboBox(entityType);
        choosePanel.setVisible(true);
        retourButton.setVisible(true);
    }

    /**
     * Remplit la `ComboBox` avec les noms des entités disponibles (Clients ou Prospects).
     *
     * @param type Le type d'entité à afficher (CLIENT ou PROSPECT).
     */
    private void populateComboBox(EntityType type) {
        selectSocieteComboBox.removeAllItems();
        List<?> entities = type == EntityType.CLIENT ? GestionClient.getClients() : GestionProspect.getProspects();

        if (entities.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Aucun " + type.name().toLowerCase() + " enregistré pour l'instant.",
                    "Liste vide",
                    JOptionPane.INFORMATION_MESSAGE);
            choosePanel.setVisible(false);
            return;
        }

        entities.forEach(entity -> {
            if (type == EntityType.CLIENT) {
                selectSocieteComboBox.addItem(((Client) entity).getRaisonSociale());
            } else {
                selectSocieteComboBox.addItem(((Prospect) entity).getRaisonSociale());
            }
        });
    }

    /**
     * Valide l'entité sélectionnée et ouvre la vue correspondante à l'action choisie.
     */
    private void handleValidation() {
        String selectedSociete = (String) selectSocieteComboBox.getSelectedItem();

        if (selectedSociete == null || selectedSociete.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une société.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (entityType == EntityType.CLIENT) {
            Client selectedClient = GestionClient.getClients().stream()
                    .filter(client -> client.getRaisonSociale().equals(selectedSociete))
                    .findFirst()
                    .orElse(null);

            if (selectedClient == null) {
                JOptionPane.showMessageDialog(this,
                        "Client introuvable.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            new Crud(selectedClient, actionType).setVisible(true);
        } else {
            Prospect selectedProspect = GestionProspect.getProspects().stream()
                    .filter(prospect -> prospect.getRaisonSociale().equals(selectedSociete))
                    .findFirst()
                    .orElse(null);

            if (selectedProspect == null) {
                JOptionPane.showMessageDialog(this,
                        "Prospect introuvable.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            new Crud(selectedProspect, actionType).setVisible(true);
        }

        dispose();
    }

    /**
     * Met à jour les libellés des boutons du panneau CRUD en fonction de l'entité sélectionnée.
     *
     * @param singular Libellé au singulier pour l'entité (ex. "client").
     * @param plural Libellé au pluriel pour l'entité (ex. "clients").
     */
    private void updateCrudPanelTexts(String singular, String plural) {
        readButton.setText("Voir la liste des " + plural);
        createButton.setText("Ajouter un " + singular);
        updateButton.setText("Modifier un " + singular);
        deleteButton.setText("Supprimer un " + singular);
    }

    /**
     * Gère le retour à l'écran précédent (CRUD ou choix d'entité).
     */
    private void retour() {
        if (choosePanel.isVisible()) {
            choosePanel.setVisible(false);
            crudPanel.setVisible(true);
        } else if (crudPanel.isVisible()) {
            crudPanel.setVisible(false);
            retourButton.setVisible(false);
        }
    }

    /**
     * Ferme la fenêtre actuelle.
     */
    private void onCancel() {
        dispose();
    }
}
