package fr.djinn.main.views;

import fr.djinn.main.entities.Client;
import fr.djinn.main.entities.GestionClient;
import fr.djinn.main.entities.GestionProspect;
import fr.djinn.main.entities.Prospect;
import static fr.djinn.main.utils.ECFLogger.LOGGER;
import fr.djinn.main.utils.EntityType;
import fr.djinn.main.utils.RegEx;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;
import java.util.logging.Level;

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

        DefaultTableModel tableModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Rendre la colonne "Identifiant" (première colonne) non éditable
                return column != 0;
            }
        };
        tableModel.addRow(headers);
        entities.forEach(entity -> tableModel.addRow(entityToRow(entity, entityType)));
        listTable.setModel(tableModel);

        LOGGER.info("Table peuplée avec les données des " + entityType.name().toLowerCase() + "s.");

        listTable.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                // Récupérer les valeurs nécessaires
                Object newValue = listTable.getValueAt(row, column);
                Object id = listTable.getValueAt(row, 0); // Identifiant (non éditable mais nécessaire pour associer l'objet)

                // Trouver l'objet correspondant (Client ou Prospect)
                if (entityType == EntityType.CLIENT) {
                    Client client = GestionClient.getClients().stream()
                            .filter(c -> c.getIdentifiant() == (int) id)
                            .findFirst()
                            .orElse(null);

                    if (client != null) {
                        switch (column) {
                            case 1: // Raison sociale
                                client.setRaisonSociale(newValue.toString());
                                break;
                            case 2: // Numéro de rue
                                client.getAdresse().setNumeroDeRue(newValue.toString());
                                break;
                            case 3: // Nom de rue
                                client.getAdresse().setNomDeRue(newValue.toString());
                                break;
                            case 4: // Code postal
                                client.getAdresse().setCodePostal(newValue.toString());
                                break;
                            case 5: // Ville
                                client.getAdresse().setVille(newValue.toString());
                                break;
                            case 6: // Téléphone
                                client.setTelephone(newValue.toString());
                                break;
                            case 7: // Adresse mail
                                client.setAdresseMail(newValue.toString());
                                break;
                            case 8: // Chiffre d'affaires
                                client.setChiffreAffaire(Long.parseLong(newValue.toString()));
                                break;
                            case 9: // Nombre d'employés
                                client.setNbrEmploye(Integer.parseInt(newValue.toString()));
                                break;
                        }
                        LOGGER.log(Level.INFO,"Client mis à jour : " + client);
                    }
                } else if (entityType == EntityType.PROSPECT) {
                    Prospect prospect = GestionProspect.getProspects().stream()
                            .filter(p -> p.getIdentifiant() == (int) id)
                            .findFirst()
                            .orElse(null);

                    if (prospect != null) {
                        switch (column) {
                            case 1: // Raison sociale
                                prospect.setRaisonSociale(newValue.toString());
                                break;
                            case 2: // Numéro de rue
                                prospect.getAdresse().setNumeroDeRue(newValue.toString());
                                break;
                            case 3: // Nom de rue
                                prospect.getAdresse().setNomDeRue(newValue.toString());
                                break;
                            case 4: // Code postal
                                prospect.getAdresse().setCodePostal(newValue.toString());
                                break;
                            case 5: // Ville
                                prospect.getAdresse().setVille(newValue.toString());
                                break;
                            case 6: // Téléphone
                                prospect.setTelephone(newValue.toString());
                                break;
                            case 7: // Adresse mail
                                prospect.setAdresseMail(newValue.toString());
                                break;
                            case 8: // Date de prospection
                                prospect.setDateProspection(newValue.toString());
                                break;
                            case 9: // Intéressé (OUI/NON)
                                prospect.setEstInteresse(newValue.toString());
                                break;
                        }
                        LOGGER.log(Level.INFO,"Prospect mis à jour : " + prospect);
                    }
                }
            }
        });

        listTable.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
                    int selectedRow = listTable.getSelectedRow();

                    if (selectedRow != -1) {
                        // Récupérer l'identifiant de la ligne sélectionnée
                        int id = (int) listTable.getValueAt(selectedRow, 0);

                        // Demander confirmation
                        int confirmation = JOptionPane.showConfirmDialog(
                                listTable,
                                "Êtes-vous sûr de vouloir supprimer cette entrée ?",
                                "Confirmation de suppression",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        );

                        if (confirmation == JOptionPane.YES_OPTION) {
                            // Supprimer l'entrée du modèle de table
                            ((DefaultTableModel) listTable.getModel()).removeRow(selectedRow);

                            // Supprimer l'entrée des données en mémoire
                            if (entityType == EntityType.CLIENT) {
                                GestionClient.getClients().removeIf(client -> client.getIdentifiant() == id);
                            } else if (entityType == EntityType.PROSPECT) {
                                GestionProspect.getProspects().removeIf(prospect -> prospect.getIdentifiant() == id);
                            }

                            JOptionPane.showMessageDialog(
                                    listTable,
                                    "Entrée supprimée avec succès.",
                                    "Suppression réussie",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                listTable,
                                "Veuillez sélectionner une ligne à supprimer.",
                                "Aucune sélection",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
            }
        });
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
