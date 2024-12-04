package fr.djinn.main.views;

import fr.djinn.main.entities.*;
import fr.djinn.main.utils.ActionType;
import fr.djinn.main.utils.EntityType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Accueil extends JFrame {
    private JPanel contentPane;
    private JButton buttonQuitter;
    private JPanel firstPanel;
    private JPanel crudPanel;
    private JPanel choosePanel;
    private JLabel firstPanelTitleLabel;
    private JButton clientButton;
    private JButton prospectButton;
    private JLabel crudPanelTitleLabel;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton readButton;
    private JLabel choosePanelTitleLabel;
    private JComboBox selectSocieteComboBox;
    private JButton validerButton;

    private EntityType entityType;
    private ActionType actionType;
    private Window thisWindow; // Référence à la fenêtre actuelle

    public Accueil() {
        initComponents();
        listeners();

    }

    private void initComponents() {
        setContentPane(contentPane);
        setSize(900, 600);
        thisWindow = this;

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        crudPanel.setVisible(false);
        choosePanel.setVisible(false);
    }

    private void listeners(){
        buttonQuitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        clientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                entityType = EntityType.CLIENT;
                updateCrudPanelTexts("client", "clients");
                crudPanel.setVisible(true);
            }
        });

        prospectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                entityType = EntityType.PROSPECT;
                updateCrudPanelTexts("prospect", "prospects");
                crudPanel.setVisible(true);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionType = ActionType.UPDATE;
                prepareUpdateOrDelete(entityType, "Quel société voulez-vous mettre a jour ?");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionType = ActionType.DELETE;
                prepareUpdateOrDelete(entityType, "Quel société voulez-vous supprimer ?");
            }
        });
    }

    private void updateCrudPanelTexts(String singular, String plural) {
        readButton.setText("voir la liste des " + plural);
        createButton.setText("Ajouter un " + singular);
        updateButton.setText("Modifier un " + singular);
        deleteButton.setText("Supprimer un " + singular);
    }

    private void prepareUpdateOrDelete(EntityType type, String labelText){
        populateComboBox(type);
        choosePanelTitleLabel.setText(labelText);
    }

    private void populateComboBox(EntityType type) {
        // Supprimer tous les éléments existants dans la ComboBox
        selectSocieteComboBox.removeAllItems();

        if(type == EntityType.CLIENT){
            List<Client> clients = GestionClient.getClients();
            if(clients.isEmpty()){
                JOptionPane.showMessageDialog(this, "Aucun " + type.name().toLowerCase() +
                        " enregistré pour l'instant.", "Liste vide", JOptionPane.INFORMATION_MESSAGE);
                choosePanel.setVisible(false);
                return;
            }
            for(Client client : clients){
                selectSocieteComboBox.addItem(client.getRaisonSociale());
            }
        } else {
            List<Prospect> prospects = GestionProspect.getProspects();
            if(prospects.isEmpty()){
                JOptionPane.showMessageDialog(this, "Aucun " + type.name().toLowerCase() +
                        " enregistré pour l'instant.", "Liste vide", JOptionPane.INFORMATION_MESSAGE);
                choosePanel.setVisible(false);
                return;
            }

            for(Prospect prospect : prospects){
                selectSocieteComboBox.addItem(prospect.getRaisonSociale());
            }
        }
        choosePanel.setVisible(true);

    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
