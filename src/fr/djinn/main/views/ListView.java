package fr.djinn.main.views;

import fr.djinn.main.entities.Client;
import fr.djinn.main.entities.GestionClient;
import fr.djinn.main.entities.GestionProspect;
import fr.djinn.main.entities.Prospect;
import fr.djinn.main.utils.EntityType;
import static fr.djinn.main.utils.RegEx.FORMATTER;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class ListView extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel listTitleLabel;
    private JTable listTable;

    public ListView(EntityType p_entityType) {
        initComponents();
        listeners();
        populateTable(p_entityType);
    }

    private void initComponents() {
        setContentPane(contentPane);
        setSize(1380, 600);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    private void listeners(){
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void populateTable(EntityType p_entityType) {
        String[] headers;
        DefaultTableModel tableModel;

        if(p_entityType == EntityType.CLIENT){
            listTitleLabel.setText("Liste des clients");
            headers = new String[]{"Identifiant", "Raison Sociale", "Numéro de rue",
                    "Nom de rue", "Code Postal", "Ville",
                    "Téléphone", "Adresse Mail", "C.A",
                    "Nombre d'Employé"
            };
            tableModel = new DefaultTableModel(headers, 0);
            tableModel.addRow(headers);

            for (Client client : GestionClient.trierParRaisonSociale()){
                tableModel.addRow(new Object[]{
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
                });
            }
        } else {
            listTitleLabel.setText("Liste des prospects");
            headers = new String[]{"Identifiant", "Raison Sociale", "Numéro de rue",
                    "Nom de rue", "Code Postal", "Ville",
                    "Téléphone", "Adresse Mail", "Date de prospection",
                    "Est intéréssé"
            };
            tableModel = new DefaultTableModel(headers, 0);
            tableModel.addRow(headers);

            for (Prospect prospect : GestionProspect.trierParRaisonSociale()){
                tableModel.addRow(new Object[]{
                        prospect.getIdentifiant(),
                        prospect.getRaisonSociale(),
                        prospect.getAdresse().getNumeroDeRue(),
                        prospect.getAdresse().getNomDeRue(),
                        prospect.getAdresse().getCodePostal(),
                        prospect.getAdresse().getVille(),
                        prospect.getTelephone(),
                        prospect.getAdresseMail(),
                        prospect.getDateProspection().format(FORMATTER),
                        prospect.getEstInteresse()
                });
            }
        }
        listTable.setModel(tableModel);
    }

    private void onOK() {
        // add your code here
        dispose();
        new Accueil().setVisible(true);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
        new Accueil().setVisible(true);
    }
}
