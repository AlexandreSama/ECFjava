package fr.djinn.main.views;

import fr.djinn.main.entities.Client;
import fr.djinn.main.entities.Prospect;
import fr.djinn.main.utils.ActionType;
import fr.djinn.main.utils.EntityType;

import javax.swing.*;
import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.awt.event.*;

public class Crud extends JFrame {
    private JPanel contentPane;
    private JButton retourButton;
    private JButton quitterButton;
    private JLabel crudPanelTitleLabel;
    private JTextField identifiantField;
    private JTextField raisonSocialeField;
    private JTextField telephoneField;
    private JTextField emailField;
    private JTextField numeroDeRueField;
    private JTextField nomDeRueField;
    private JTextField codePostalField;
    private JTextField villeField;
    private JTextField caField;
    private JTextField nbrEmployField;
    private JButton validerButton;
    private JTextArea commentaireArea;
    private JTextField dateProspectionField;
    private JTextField isInterestedField;
    private JLabel identifiantLabel;
    private JLabel raisonSocialeLabel;
    private JLabel telephoneLabel;
    private JLabel emailLabel;
    private JLabel commentaireLabel;
    private JLabel numeroDeRueLabel;
    private JLabel nomDeRueLabel;
    private JLabel codePostalLabel;
    private JLabel villeLabel;
    private JLabel caLabel;
    private JLabel nbrEmployeLabel;
    private JLabel dateProspectionLabel;
    private JLabel isInterestedLabel;
    private Window thisWindow;

    private EntityType entityType;
    private ActionType actionType;
    private Client client;
    private Prospect prospect;
    private int identifiant;

    public Crud(Client p_client, ActionType p_actionType) {
        initComponents();
        listeners();
        this.client = p_client;
        actionUpdateOrClient(p_client, p_actionType);
    }

    public Crud(Prospect p_prospect, ActionType p_actionType) {
        initComponents();
        listeners();
        this.prospect = p_prospect;
        actionUpdateOrDeleteProspect(p_prospect, p_actionType);
    }

    public Crud(EntityType p_entityType) {
        initComponents();
        listeners();

        if(p_entityType == EntityType.CLIENT) {
            actionCreateClient();
        }else{
            actionCreateProspect();
        }
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
    }

    private void listeners(){
        retourButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        quitterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    private void actionUpdateOrClient(Client p_client, ActionType p_actionType) {

        crudPanelTitleLabel.setText("Modifier ce client ?");
        validerButton.setText("Enregistrer les modifications");
        identifiantField.setText(p_client.getIdentifiant().toString());
        raisonSocialeField.setText(p_client.getRaisonSociale());
        telephoneField.setText(p_client.getTelephone());
        emailField.setText(p_client.getAdresseMail());
        numeroDeRueField.setText(p_client.getAdresse().getNumeroDeRue());
        nomDeRueField.setText(p_client.getAdresse().getNomDeRue());
        codePostalField.setText(p_client.getAdresse().getCodePostal());
        villeField.setText(p_client.getAdresse().getVille());
        caField.setText(String.valueOf(p_client.getChiffreAffaire()));
        nbrEmployField.setText(String.valueOf(p_client.getNbrEmploye()));
        dateProspectionField.setVisible(false);
        isInterestedField.setVisible(false);
        dateProspectionLabel.setVisible(false);
        isInterestedLabel.setVisible(false);
        if(p_client.getCommentaire() != null){
            commentaireArea.setText(p_client.getCommentaire());
        }

        if(p_actionType == ActionType.DELETE){
            crudPanelTitleLabel.setText("Supprimer ce client ?");
            validerButton.setText("Supprimer ce client");
            raisonSocialeField.setEditable(false);
            telephoneField.setEditable(false);
            emailField.setEditable(false);
            numeroDeRueField.setEditable(false);
            nomDeRueField.setEditable(false);
            codePostalField.setEditable(false);
            villeField.setEditable(false);
            caField.setEditable(false);
            nbrEmployField.setEditable(false);
            commentaireArea.setEditable(false);
        }
    }

    private void actionUpdateOrDeleteProspect(Prospect p_prospect, ActionType p_actionType) {

        crudPanelTitleLabel.setText("Modifier ce prospect ?");
        validerButton.setText("Enregistrer les modifications");
        identifiantField.setText(p_prospect.getIdentifiant().toString());
        raisonSocialeField.setText(p_prospect.getRaisonSociale());
        telephoneField.setText(p_prospect.getTelephone());
        emailField.setText(p_prospect.getAdresseMail());
        numeroDeRueField.setText(p_prospect.getAdresse().getNumeroDeRue());
        nomDeRueField.setText(p_prospect.getAdresse().getNomDeRue());
        codePostalField.setText(p_prospect.getAdresse().getCodePostal());
        villeField.setText(p_prospect.getAdresse().getVille());
        dateProspectionField.setText(p_prospect.getDateProspection().toString());
        isInterestedField.setText(p_prospect.getEstInteresse());
        caField.setVisible(false);
        caLabel.setVisible(false);
        nbrEmployField.setVisible(false);
        nbrEmployeLabel.setVisible(false);

        if(p_prospect.getCommentaire() != null){
            commentaireArea.setText(p_prospect.getCommentaire());
        }

        if(p_actionType == ActionType.DELETE){
            crudPanelTitleLabel.setText("Supprimer ce prospect ?");
            validerButton.setText("Supprimer ce prospect");
            raisonSocialeField.setEditable(false);
            telephoneField.setEditable(false);
            emailField.setEditable(false);
            numeroDeRueField.setEditable(false);
            nomDeRueField.setEditable(false);
            codePostalField.setEditable(false);
            villeField.setEditable(false);
            caField.setEditable(false);
            nbrEmployField.setEditable(false);
            commentaireArea.setEditable(false);
        }
    }

    private void actionCreateClient(){
        crudPanelTitleLabel.setText("Ajouter un client ?");
        validerButton.setText("Ajouter un client");
        identifiant = Client.getProchainIdentifiant();
        identifiantField.setText(String.valueOf(identifiant));
    }

    private void actionCreateProspect(){
        crudPanelTitleLabel.setText("Ajouter un prospect ?");
        validerButton.setText("Ajouter un prospect");
        identifiant = Prospect.getProchainIdentifiant();
        identifiantField.setText(String.valueOf(identifiant));
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
