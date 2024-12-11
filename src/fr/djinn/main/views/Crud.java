package fr.djinn.main.views;

import fr.djinn.main.entities.*;
import fr.djinn.main.utils.ActionType;
import fr.djinn.main.utils.EntityType;
import static fr.djinn.main.utils.ECFLogger.LOGGER;
import fr.djinn.main.utils.RegEx;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Crud extends JFrame {
    private JPanel contentPane;
    private JButton retourButton;
    private JButton quitterButton;
    private JLabel crudPanelTitleLabel;
    private JLabel identifiantLabel;
    private JLabel raisonSocialeLabel;
    private JLabel telephoneLabel;
    private JLabel emailLabel;
    private JLabel numeroDeRueLabel;
    private JLabel nomDeRueLabel;
    private JLabel codePostalLabel;
    private JLabel villeLabel;
    private JLabel caLabel;
    private JLabel nbrEmployeLabel;
    private JLabel dateProspectionLabel;
    private JLabel isInterestedLabel;
    private JLabel commentaireLabel;
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
    private JComboBox isInterestedComboBox;

    private EntityType entityType;
    private ActionType actionType;
    private Client client;
    private Prospect prospect;

    /**
     * Constructeur pour gérer les actions (UPDATE ou DELETE) sur un Client.
     * @param p_client Le client à modifier ou supprimer.
     * @param p_actionType L'action à effectuer (UPDATE ou DELETE).
     */
    public Crud(Client p_client, ActionType p_actionType) {
        initComponents();
        listeners();
        setupCrudPanel(p_client, p_actionType, EntityType.CLIENT);
    }

    /**
     * Constructeur pour gérer les actions (UPDATE ou DELETE) sur un Prospect.
     * @param p_prospect Le prospect à modifier ou supprimer.
     * @param p_actionType L'action à effectuer (UPDATE ou DELETE).
     */
    public Crud(Prospect p_prospect, ActionType p_actionType) {
        initComponents();
        listeners();
        setupCrudPanel(p_prospect, p_actionType, EntityType.PROSPECT);
    }

    /**
     * Constructeur pour créer une nouvelle entité (Client ou Prospect).
     * @param p_entityType Le type d'entité à créer (CLIENT ou PROSPECT).
     */
    public Crud(EntityType p_entityType) {
        initComponents();
        listeners();
        actionType = ActionType.CREATE;
        setupCreatePanel(p_entityType);
    }

    /**
     * Initialise les composants graphiques de la vue.
     */
    private void initComponents() {
        setContentPane(contentPane);
        setSize(900, 600);
        setTitle("ECF Exo : CRUD");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    /**
     * Configure les écouteurs d'événements pour les boutons de l'interface.
     */
    private void listeners() {
        retourButton.addActionListener(e -> onCancel());
        quitterButton.addActionListener(e -> onCancel());
        validerButton.addActionListener(e -> handleAction());
    }

    /**
     * Gère l'action principale (CREATE, UPDATE ou DELETE) en fonction du contexte.
     */
    private void handleAction() {
        try {
            switch (actionType) {
                case DELETE -> handleDelete();
                case UPDATE -> handleUpdate();
                case CREATE -> handleCreate();
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Configure l'interface pour modifier ou supprimer une entité (Client ou Prospect).
     * @param entity       L'entité à gérer.
     * @param p_actionType L'action à effectuer (UPDATE ou DELETE).
     * @param p_entityType Le type d'entité (CLIENT ou PROSPECT).
     */
    private void setupCrudPanel(Object entity, ActionType p_actionType, EntityType p_entityType) {
        actionType = p_actionType;
        entityType = p_entityType;

        boolean isDelete = (p_actionType == ActionType.DELETE);

        crudPanelTitleLabel.setText(isDelete ? getDeleteTitle() : getUpdateTitle());
        validerButton.setText(isDelete ? "Supprimer" : "Enregistrer");

        if (entity instanceof Client) {
            client = (Client) entity;
            populateFields(client);
            toggleFieldsVisibility(false, true, true);
        } else if (entity instanceof Prospect) {
            prospect = (Prospect) entity;
            populateFields(prospect);
            toggleFieldsVisibility(true, false, false);
        }

        toggleFieldsEditable(!isDelete);
    }

    /**
     * Configure l'interface pour créer une nouvelle entité.
     * @param p_entityType Le type d'entité à créer (CLIENT ou PROSPECT).
     */
    private void setupCreatePanel(EntityType p_entityType) {
        entityType = p_entityType;
        actionType = ActionType.CREATE;

        int identifiant = (p_entityType == EntityType.CLIENT)
                ? Client.generateNextIdentifiant()
                : Prospect.generateNextIdentifiant();
        identifiantField.setText(String.valueOf(identifiant));

        crudPanelTitleLabel.setText("Créer un nouveau " + (p_entityType == EntityType.CLIENT ? "client" : "prospect"));
        validerButton.setText("Créer");

        toggleFieldsVisibility(p_entityType == EntityType.PROSPECT, p_entityType == EntityType.CLIENT, true);
    }

    /**
     * Active ou désactive les champs en fonction de leur pertinence pour l'entité.
     * @param showProspectFields Si vrai, affiche les champs spécifiques aux prospects.
     * @param showClientFields   Si vrai, affiche les champs spécifiques aux clients.
     * @param showCommonFields   Si vrai, affiche les champs communs.
     */
    private void toggleFieldsVisibility(boolean showProspectFields, boolean showClientFields, boolean showCommonFields) {
        caField.setVisible(showClientFields);
        caLabel.setVisible(showClientFields);
        nbrEmployField.setVisible(showClientFields);
        nbrEmployeLabel.setVisible(showClientFields);

        dateProspectionField.setVisible(showProspectFields);
        dateProspectionLabel.setVisible(showProspectFields);
        isInterestedComboBox.setVisible(showProspectFields);
        isInterestedLabel.setVisible(showProspectFields);
    }

    /**
     * Remplit les champs d'un formulaire en fonction de l'entité donnée.
     * @param entity L'entité à afficher.
     */
    private void populateFields(Object entity) {
        if (entity instanceof Client client) {
            identifiantField.setText(client.getIdentifiant().toString());
            raisonSocialeField.setText(client.getRaisonSociale());
            telephoneField.setText(client.getTelephone());
            emailField.setText(client.getAdresseMail());
            setAddressFields(client.getAdresse());
            caField.setText(String.valueOf(client.getChiffreAffaire()));
            nbrEmployField.setText(String.valueOf(client.getNbrEmploye()));
        } else if (entity instanceof Prospect prospect) {
            identifiantField.setText(prospect.getIdentifiant().toString());
            raisonSocialeField.setText(prospect.getRaisonSociale());
            telephoneField.setText(prospect.getTelephone());
            emailField.setText(prospect.getAdresseMail());
            setAddressFields(prospect.getAdresse());
            dateProspectionField.setText(prospect.getDateProspection().format(RegEx.FORMATTER));
            isInterestedComboBox.setSelectedItem(prospect.getEstInteresse());
        }
    }

    /**
     * Définit les champs d'adresse à partir d'un objet Adresse.
     * @param adresse L'adresse à utiliser.
     */
    private void setAddressFields(Adresse adresse) {
        numeroDeRueField.setText(adresse.getNumeroDeRue());
        nomDeRueField.setText(adresse.getNomDeRue());
        codePostalField.setText(adresse.getCodePostal());
        villeField.setText(adresse.getVille());
    }


    /**
     * Rend les champs éditables ou non en fonction du paramètre donné.
     * @param editable Si vrai, les champs deviennent éditables ; sinon, ils sont verrouillés.
     */
    private void toggleFieldsEditable(boolean editable) {
        raisonSocialeField.setEditable(editable);
        telephoneField.setEditable(editable);
        emailField.setEditable(editable);
        numeroDeRueField.setEditable(editable);
        nomDeRueField.setEditable(editable);
        codePostalField.setEditable(editable);
        villeField.setEditable(editable);
        caField.setEditable(editable);
        nbrEmployField.setEditable(editable);
        dateProspectionField.setEditable(editable);
        isInterestedComboBox.setEnabled(editable);
        commentaireArea.setEditable(editable);
    }

    /**
     * Supprime une entité (Client ou Prospect) en fonction du type spécifié.
     * Affiche un message de confirmation et enregistre un log de l'opération.
     */
    private void handleDelete() {
        try {
            // Demande de confirmation à l'utilisateur
            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Êtes-vous sûr de vouloir supprimer cette entité ?",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            // Si l'utilisateur confirme la suppression
            if (confirmation == JOptionPane.YES_OPTION) {
                if (entityType == EntityType.CLIENT) {
                    GestionClient.getClients().remove(client);
                    JOptionPane.showMessageDialog(this, "Client supprimé avec succès !");
                } else {
                    GestionProspect.getProspects().remove(prospect);
                    JOptionPane.showMessageDialog(this, "Prospect supprimé avec succès !");
                }

                // Retour à l'accueil après la suppression
            } else {
                // Si l'utilisateur annule la suppression
                JOptionPane.showMessageDialog(this, "Suppression annulée.");
            }
            dispose();
            new Accueil().setVisible(true);
        } catch (Exception e) {
            handleException(e);
        }
    }


    /**
     * Met à jour une entité (Client ou Prospect) avec les données saisies dans le formulaire.
     * Enregistre les modifications et affiche un message de confirmation.
     */
    private void handleUpdate() {
        try {
            if (entityType == EntityType.CLIENT) {
                updateClientFields(client);
                JOptionPane.showMessageDialog(this, "Client mis à jour avec succès !");
            } else {
                updateProspectFields(prospect);
                JOptionPane.showMessageDialog(this, "Prospect mis à jour avec succès !");
            }
            dispose();
            new Accueil().setVisible(true);
        } catch (ECFException e) {
            handleException(e);
        }
    }

    /**
     * Crée une nouvelle entité (Client ou Prospect) avec les données saisies dans le formulaire.
     * Ajoute l'entité à la liste correspondante et affiche un message de confirmation.
     */
    private void handleCreate() {
        try {
            if (entityType == EntityType.CLIENT) {
                GestionClient.getClients().add(new Client(
                        new Adresse(codePostalField.getText(), nomDeRueField.getText(), numeroDeRueField.getText(), villeField.getText()),
                        emailField.getText(),
                        commentaireArea.getText(),
                        raisonSocialeField.getText(),
                        telephoneField.getText(),
                        Long.parseLong(caField.getText()),
                        Integer.parseInt(nbrEmployField.getText())
                ));
                JOptionPane.showMessageDialog(this, "Client ajouté avec succès !");
            } else {
                GestionProspect.getProspects().add(new Prospect(
                        new Adresse(codePostalField.getText(), nomDeRueField.getText(), numeroDeRueField.getText(), villeField.getText()),
                        emailField.getText(),
                        commentaireArea.getText(),
                        raisonSocialeField.getText(),
                        telephoneField.getText(),
                        dateProspectionField.getText(),
                        Objects.requireNonNull(isInterestedComboBox.getSelectedItem()).toString()
                ));
                JOptionPane.showMessageDialog(this, "Prospect ajouté avec succès !");
            }
            dispose();
            new Accueil().setVisible(true);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Met à jour les champs d'un Client en fonction des données saisies dans le formulaire.
     *
     * @param client Le client à mettre à jour.
     * @throws ECFException Si une erreur de validation se produit.
     */
    private void updateClientFields(Client client) throws ECFException {
        client.setRaisonSociale(raisonSocialeField.getText());
        client.setTelephone(telephoneField.getText());
        client.setAdresseMail(emailField.getText());
        client.setChiffreAffaire(Long.parseLong(caField.getText()));
        client.setNbrEmploye(Integer.parseInt(nbrEmployField.getText()));
        client.setAdresse(new Adresse(codePostalField.getText(), nomDeRueField.getText(), numeroDeRueField.getText(), villeField.getText()));
    }

    /**
     * Met à jour les champs d'un Prospect en fonction des données saisies dans le formulaire.
     *
     * @param prospect Le prospect à mettre à jour.
     * @throws ECFException Si une erreur de validation se produit.
     */
    private void updateProspectFields(Prospect prospect) throws ECFException {
        prospect.setRaisonSociale(raisonSocialeField.getText());
        prospect.setTelephone(telephoneField.getText());
        prospect.setAdresseMail(emailField.getText());
        prospect.setDateProspection(dateProspectionField.getText());
        prospect.setEstInteresse(isInterestedComboBox.getSelectedItem().toString());
        prospect.setAdresse(new Adresse(codePostalField.getText(), nomDeRueField.getText(), numeroDeRueField.getText(), villeField.getText()));
    }

    /**
     * Gère les exceptions survenues lors des actions CRUD.
     * @param e L'exception à gérer.
     */
    private void handleException(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        LOGGER.severe("Erreur : " + e.getMessage());
    }

    /**
     * Méthode pour envoyer le titre en cas de suppression
     * @return le titre en fonction de si c'est un client ou un prospect
     */
    private String getDeleteTitle() {
        return entityType == EntityType.CLIENT ? "Supprimer ce client ?" : "Supprimer ce prospect ?";
    }

    /**
     * Méthode pour envoyer le titre en cas de modification
     * @return le titre en fonction de si c'est un client ou un prospect
     */
    private String getUpdateTitle() {
        return entityType == EntityType.CLIENT ? "Modifier ce client ?" : "Modifier ce prospect ?";
    }

    /**
     * Action effectuée lors de la fermeture de la fenêtre.
     * Ferme la fenêtre actuelle et retourne à l'accueil.
     */
    private void onCancel() {
        dispose();
        new Accueil().setVisible(true);
    }
}