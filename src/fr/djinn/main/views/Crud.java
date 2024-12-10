package fr.djinn.main.views;

import fr.djinn.main.entities.*;
import fr.djinn.main.utils.ActionType;
import fr.djinn.main.utils.EntityType;
import static fr.djinn.main.utils.ECFLogger.LOGGER;
import fr.djinn.main.utils.RegEx;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
     *
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
     *
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
     *
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
        retourButton.addActionListener(e -> onOK());
        quitterButton.addActionListener(e -> onCancel());

        validerButton.addActionListener(e -> {
            switch (actionType) {
                case DELETE -> handleDelete();
                case UPDATE -> handleUpdate();
                case CREATE -> handleCreate();
            }
        });
    }

    /**
     * Configure l'interface en fonction de l'entité et de l'action sélectionnée.
     *
     * @param entity L'entité à gérer (Client ou Prospect).
     * @param p_actionType L'action à effectuer (UPDATE ou DELETE).
     * @param p_entityType Le type d'entité (CLIENT ou PROSPECT).
     */
    private void setupCrudPanel(Object entity, ActionType p_actionType, EntityType p_entityType) {
        actionType = p_actionType;
        entityType = p_entityType;

        boolean isDelete = (p_actionType == ActionType.DELETE);

        if (p_entityType == EntityType.CLIENT) {
            crudPanelTitleLabel.setText(isDelete ? "Supprimer ce client ?" : "Modifier ce client ?");
            validerButton.setText(isDelete ? "Supprimer" : "Enregistrer les modifications");
            client = (Client) entity;
            populateFieldsForClient(client);
            dateProspectionField.setVisible(false);
            dateProspectionLabel.setVisible(false);
            isInterestedComboBox.setVisible(false);
            isInterestedLabel.setVisible(false);
        } else {
            crudPanelTitleLabel.setText(isDelete ? "Supprimer ce prospect ?" : "Modifier ce prospect ?");
            validerButton.setText(isDelete ? "Supprimer" : "Enregistrer les modifications");
            prospect = (Prospect) entity;
            populateFieldsForProspect(prospect);
            caField.setVisible(false);
            caLabel.setVisible(false);
            nbrEmployField.setVisible(false);
            nbrEmployeLabel.setVisible(false);
            isInterestedComboBox.setVisible(true);
            isInterestedComboBox.setSelectedItem(prospect.getEstInteresse());
        }

        toggleFieldsEditable(!isDelete);
    }

    /**
     * Configure l'interface pour la création d'une nouvelle entité.
     *
     * @param p_entityType Le type d'entité à créer (CLIENT ou PROSPECT).
     */
    private void setupCreatePanel(EntityType p_entityType) {
        entityType = p_entityType;

        if (p_entityType == EntityType.CLIENT) {
            int identifiant = Client.generateIdentifiant();
            identifiantField.setText(String.valueOf(identifiant));
            crudPanelTitleLabel.setText("Créer un nouveau client ?");
            validerButton.setText("Créer");
            caField.setVisible(true);
            caLabel.setVisible(true);
            nbrEmployField.setVisible(true);
            nbrEmployeLabel.setVisible(true);
            dateProspectionField.setVisible(false);
            dateProspectionLabel.setVisible(false);
            isInterestedComboBox.setVisible(false);
            isInterestedLabel.setVisible(false);
        } else {
            int identifiant = Prospect.generateIdentifiant();
            identifiantField.setText(String.valueOf(identifiant));
            crudPanelTitleLabel.setText("Créer un nouveau prospect ?");
            validerButton.setText("Créer");
            caField.setVisible(false);
            caLabel.setVisible(false);
            nbrEmployField.setVisible(false);
            nbrEmployeLabel.setVisible(false);
            dateProspectionField.setVisible(true);
            dateProspectionLabel.setVisible(true);
            isInterestedComboBox.setVisible(true);
        }
    }

    /**
     * Remplit les champs pour afficher ou modifier un Client.
     *
     * @param client Le client dont les données sont affichées.
     */
    private void populateFieldsForClient(Client client) {
        identifiantField.setText(client.getIdentifiant().toString());
        raisonSocialeField.setText(client.getRaisonSociale());
        telephoneField.setText(client.getTelephone());
        emailField.setText(client.getAdresseMail());
        numeroDeRueField.setText(client.getAdresse().getNumeroDeRue());
        nomDeRueField.setText(client.getAdresse().getNomDeRue());
        codePostalField.setText(client.getAdresse().getCodePostal());
        villeField.setText(client.getAdresse().getVille());
        caField.setText(String.valueOf(client.getChiffreAffaire()));
        nbrEmployField.setText(String.valueOf(client.getNbrEmploye()));
        commentaireArea.setText(client.getCommentaire());
    }

    /**
     * Remplit les champs pour afficher ou modifier un Prospect.
     *
     * @param prospect Le prospect dont les données sont affichées.
     */
    private void populateFieldsForProspect(Prospect prospect) {
        identifiantField.setText(prospect.getIdentifiant().toString());
        raisonSocialeField.setText(prospect.getRaisonSociale());
        telephoneField.setText(prospect.getTelephone());
        emailField.setText(prospect.getAdresseMail());
        numeroDeRueField.setText(prospect.getAdresse().getNumeroDeRue());
        nomDeRueField.setText(prospect.getAdresse().getNomDeRue());
        codePostalField.setText(prospect.getAdresse().getCodePostal());
        villeField.setText(prospect.getAdresse().getVille());
        dateProspectionField.setText(prospect.getDateProspection().format(RegEx.FORMATTER));
        isInterestedComboBox.setSelectedItem(prospect.getEstInteresse());
        commentaireArea.setText(prospect.getCommentaire());
    }

    /**
     * Active ou désactive les champs en fonction de l'action sélectionnée.
     *
     * @param editable Si vrai, les champs sont éditables ; sinon, ils sont verrouillés.
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
                dispose();
                new Accueil().setVisible(true);
            } else {
                // Si l'utilisateur annule la suppression
                JOptionPane.showMessageDialog(this, "Suppression annulée.");
                dispose();
                new Accueil().setVisible(true);
            }
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
                dispose();
                new Accueil().setVisible(true);
            } else {
                updateProspectFields(prospect);
                JOptionPane.showMessageDialog(this, "Prospect mis à jour avec succès !");
                dispose();
                new Accueil().setVisible(true);
            }
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
                dispose();
                new Accueil().setVisible(true);
            } else {
                GestionProspect.getProspects().add(new Prospect(
                        new Adresse(codePostalField.getText(), nomDeRueField.getText(), numeroDeRueField.getText(), villeField.getText()),
                        emailField.getText(),
                        commentaireArea.getText(),
                        raisonSocialeField.getText(),
                        telephoneField.getText(),
                        dateProspectionField.getText(),
                        isInterestedComboBox.getSelectedItem().toString()
                ));
                JOptionPane.showMessageDialog(this, "Prospect ajouté avec succès !");
                dispose();
                new Accueil().setVisible(true);
            }
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
     * Affiche un message d'erreur à l'utilisateur et enregistre un log de l'exception.
     *
     * @param e L'exception à gérer.
     */
    private void handleException(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        LOGGER.severe("Erreur : " + e.getMessage());
    }

    /**
     * Action effectuée lors du clic sur le bouton "Retour".
     * Ferme la fenêtre actuelle et retourne à l'accueil.
     */
    private void onOK() {
        dispose();
        new Accueil().setVisible(true);
    }

    /**
     * Action effectuée lors de la fermeture de la fenêtre.
     * Ferme la fenêtre actuelle et retourne à l'accueil.
     */
    private void onCancel() {
        dispose();
    }
}
