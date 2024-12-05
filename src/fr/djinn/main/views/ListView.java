package fr.djinn.main.views;

import fr.djinn.main.entities.Client;
import fr.djinn.main.entities.GestionClient;
import fr.djinn.main.entities.GestionProspect;
import fr.djinn.main.entities.Prospect;
import static fr.djinn.main.utils.ECFLogger.LOGGER;
import fr.djinn.main.utils.EntityType;
import fr.djinn.main.utils.RegEx;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Classe graphique permettant d'afficher une liste de clients ou de prospects
 * sous forme de tableau.
 */
public class ListView extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel listTitleLabel;
    private JTable listTable;

    /**
     * Constructeur de la classe ListView.
     * Initialise l'interface graphique et peuple le tableau avec les données
     * en fonction du type d'entité spécifié.
     *
     * @param entityType Le type d'entité à afficher (CLIENT ou PROSPECT).
     */
    public ListView(EntityType entityType) {
        initComponents();
        listeners();
        populateTable(entityType);
    }

    /**
     * Initialise les composants graphiques de la vue.
     */
    private void initComponents() {
        setContentPane(contentPane);
        setSize(1380, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    /**
     * Configure les écouteurs d'événements pour les composants graphiques.
     */
    private void listeners() {
        buttonOK.addActionListener(e -> onOK());
    }

    /**
     * Remplit le tableau avec les données des clients ou prospects en fonction
     * du type d'entité spécifié.
     *
     * @param entityType Le type d'entité à afficher (CLIENT ou PROSPECT).
     */
    private void populateTable(EntityType entityType) {
        String[] headers;
        List<?> entities;

        if (entityType == EntityType.CLIENT) {
            listTitleLabel.setText("Liste des clients");
            headers = new String[]{
                    "Identifiant", "Raison Sociale", "Numéro de rue",
                    "Nom de rue", "Code Postal", "Ville",
                    "Téléphone", "Adresse Mail", "C.A", "Nombre d'employés"
            };
            entities = GestionClient.trierParRaisonSociale();
        } else {
            listTitleLabel.setText("Liste des prospects");
            headers = new String[]{
                    "Identifiant", "Raison Sociale", "Numéro de rue",
                    "Nom de rue", "Code Postal", "Ville",
                    "Téléphone", "Adresse Mail", "Date de prospection", "Est intéressé"
            };
            entities = GestionProspect.trierParRaisonSociale();
        }

        DefaultTableModel tableModel = new DefaultTableModel(headers, 0);
        tableModel.addRow(headers);
        entities.forEach(entity -> tableModel.addRow(entityToRow(entity, entityType)));
        listTable.setModel(tableModel);

        LOGGER.info("Table peuplée avec les données des " + entityType.name().toLowerCase() + "s.");
    }

    /**
     * Convertit une entité (Client ou Prospect) en tableau d'objets pour
     * l'ajouter à la ligne du tableau.
     *
     * @param entity    L'entité à convertir (Client ou Prospect).
     * @param entityType Le type de l'entité (CLIENT ou PROSPECT).
     * @return Un tableau d'objets représentant une ligne du tableau.
     */
    private Object[] entityToRow(Object entity, EntityType entityType) {
        if (entityType == EntityType.CLIENT) {
            Client client = (Client) entity;
            return new Object[]{
                    client.getIdentifiant(),
                    client.getRaisonSociale(),
                    client.getAdresse().getNumeroDeRue(),
                    client.getAdresse().getNomDeRue(),
                    client.getAdresse().getCodePostal(),
                    client.getAdresse().getVille(),
                    client.getTelephone(),
                    client.getAdresseMail(),
                    client.getChiffreAffaire(),
                    client.getNbrEmploye()
            };
        } else {
            Prospect prospect = (Prospect) entity;
            return new Object[]{
                    prospect.getIdentifiant(),
                    prospect.getRaisonSociale(),
                    prospect.getAdresse().getNumeroDeRue(),
                    prospect.getAdresse().getNomDeRue(),
                    prospect.getAdresse().getCodePostal(),
                    prospect.getAdresse().getVille(),
                    prospect.getTelephone(),
                    prospect.getAdresseMail(),
                    prospect.getDateProspection().format(RegEx.FORMATTER),
                    prospect.getEstInteresse().name()
            };
        }
    }

    /**
     * Action effectuée lors du clic sur le bouton "OK".
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
        new Accueil().setVisible(true);
    }
}
