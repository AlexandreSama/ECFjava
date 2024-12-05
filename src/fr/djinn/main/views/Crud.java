package fr.djinn.main.views;

import fr.djinn.main.entities.*;
import fr.djinn.main.utils.ActionType;
import fr.djinn.main.utils.EntityType;
import static fr.djinn.main.utils.RegEx.FORMATTER;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;

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

    private EntityType entityType;
    private ActionType actionType;
    private Client client;
    private Prospect prospect;
    private int identifiant;

    public Crud(Client p_client, ActionType p_actionType) {
        initComponents();
        listeners();
        actionUpdateOrClient(p_client, p_actionType);
        actionType = p_actionType;
        client = p_client;
    }

    public Crud(Prospect p_prospect, ActionType p_actionType) {
        initComponents();
        listeners();
        actionUpdateOrDeleteProspect(p_prospect, p_actionType);
        actionType = p_actionType;
        prospect = p_prospect;
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

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    private void listeners(){
        retourButton.addActionListener(e -> onOK());
        quitterButton.addActionListener(e -> onCancel());
        validerButton.addActionListener(e -> {
            switch (actionType) {
                case DELETE:
                    if(client != null) {
                        deleteClient(client);
                    } else {
                        deleteProspect(prospect);
                    }
                    break;
                    case UPDATE:
                        if(client != null) {
                            updateClient(client);
                        } else {
                            updateProspect(prospect);
                        }
                        break;
                case CREATE:
                    if(client != null) {
                        createClient();
                    }else{
                        createProspect();
                    }
                    break;
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

    private void createClient() throws ECFException {
        try {
            GestionClient.getClients().add(new Client(
                    new Adresse(codePostalField.getText(), nomDeRueField.getText(), numeroDeRueField.getText(), villeField.getText()),
                    emailField.getText(),
                    commentaireArea.getText(),
                    raisonSocialeField.getText(),
                    telephoneField.getText(),
                    Long.parseLong(caField.getText()),
                    Integer.parseInt(nbrEmployField.getText())
            ));
            JOptionPane.showMessageDialog(null, "Client ajouté !");
            dispose();
            new Accueil().setVisible(true);
        } catch (ECFException ec) {
            JOptionPane.showMessageDialog(null, ec.getMessage());
        }
    }

    private void createProspect() throws ECFException {
        try {
            GestionProspect.getProspects().add(new Prospect(
                    new Adresse(codePostalField.getText(), nomDeRueField.getText(), numeroDeRueField.getText(), villeField.getText()),
                    emailField.getText(),
                    commentaireArea.getText(),
                    raisonSocialeField.getText(),
                    telephoneField.getText(),
                    LocalDate.parse(dateProspectionField.getText(), FORMATTER),
                    isInterestedField.getText()
            ));
            JOptionPane.showMessageDialog(null, "Prospect ajouté !");
            dispose();
            new Accueil().setVisible(true);
        } catch (ECFException ec) {
            JOptionPane.showMessageDialog(null, ec.getMessage());
        }
    }

    private void deleteClient(Client client) throws ECFException {
        try{
            GestionClient.getClients().remove(client);
            JOptionPane.showMessageDialog(null, "Ce client a bien été supprimé");
            dispose();
            new Accueil().setVisible(true);
        } catch (ECFException ec) {
            JOptionPane.showMessageDialog(null, ec.getMessage());
        }
    }

    private void deleteProspect(Prospect prospect) throws ECFException {
        try{
            GestionProspect.getProspects().remove(prospect);
            JOptionPane.showMessageDialog(null, "Ce prospect a bien été supprimé");
            dispose();
            new Accueil().setVisible(true);
        } catch (ECFException ec) {
            JOptionPane.showMessageDialog(null, ec.getMessage());
        }
    }

    private void updateClient(Client client) throws ECFException {
        try{
            client.setRaisonSociale(raisonSocialeField.getText());
            client.setTelephone(telephoneField.getText());
            client.setAdresseMail(emailField.getText());
            client.setChiffreAffaire(Long.parseLong(caField.getText()));
            client.setNbrEmploye(Integer.parseInt(nbrEmployField.getText()));
            client.setAdresse(new Adresse(codePostalField.getText(), nomDeRueField.getText(), numeroDeRueField.getText(), villeField.getText()));
            JOptionPane.showMessageDialog(null, "Ce client a bien été mis a jour");
            dispose();
            new Accueil().setVisible(true);
        } catch (ECFException ec) {
            JOptionPane.showMessageDialog(null, ec.getMessage());
        }
    }

    private void updateProspect(Prospect prospect) throws ECFException {
        try{
            prospect.setRaisonSociale(raisonSocialeField.getText());
            prospect.setTelephone(telephoneField.getText());
            prospect.setAdresseMail(emailField.getText());
            prospect.setDateProspection(LocalDate.parse(dateProspectionField.getText(), FORMATTER));
            prospect.setEstInteresse(isInterestedField.getText());
            prospect.setAdresse(new Adresse(codePostalField.getText(), nomDeRueField.getText(), numeroDeRueField.getText(), villeField.getText()));
            JOptionPane.showMessageDialog(null, "Ce prospect a bien été mis a jour");
            dispose();
            new Accueil().setVisible(true);
        } catch (ECFException ec) {
            JOptionPane.showMessageDialog(null, ec.getMessage());
        }
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
