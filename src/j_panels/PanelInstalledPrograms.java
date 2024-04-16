package j_panels;

import listeners.BackButtonListener;
import p_s_p_challenge.PSPChallenge;
import tools_classes.SpellBook;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PanelInstalledPrograms extends JPanel {


    ArrayList<String> installedPrograms;

    public PanelInstalledPrograms() {


        SpellBook.creatingStandardPanelForFrame(this);

        extractingListOfPrograms();

        addingButtons();

        addingLabels();

        PSPChallenge.frame.setTitle("Lista de programas instalados");
    }

    private void extractingListOfPrograms() {
        installedPrograms = new ArrayList<>();

        JOptionPane.showMessageDialog(null, "Ten paciencia. Esto puede tardar un tiempo.");

        // Inicia el hilo para ejecutar el comando
        Thread thread = new Thread(this::runningCommand);

        thread.start();

        // Espera a que el hilo termine antes de cerrar el cuadro de diálogo de carga
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cierra el cuadro de diálogo de carga
        JOptionPane.showMessageDialog(null, "Lista Cargada.");

    }

    private void runningCommand() {
        try {

            Process process = Runtime.getRuntime().exec("wmic product get name");

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String resultOfExecution;
            while ((resultOfExecution = br.readLine()) != null) {
                installedPrograms.add(resultOfExecution);
            }

        } catch (IOException e) {

            System.out.println(e);
        }
    }

    private void addingLabels() {

        addingJList();

    }


    private void addingJList() {
        DefaultListModel defaultListModel = new DefaultListModel();
        JList jListInstalledPrograms = new JList();
        jListInstalledPrograms.setModel(defaultListModel);


        //pongo la variable i a 1 para que no me incluya el título name del texto extraído
        for (int i = 1; i < installedPrograms.size(); i++) {
            defaultListModel.addElement(installedPrograms.get(i));
        }

        JScrollPane listScroller = new JScrollPane();
        listScroller.setViewportView(jListInstalledPrograms);
        jListInstalledPrograms.setLayoutOrientation(JList.VERTICAL);
        listScroller.setSize(500, 300);
        listScroller.setLocation(this.getWidth() / 2 - listScroller.getWidth() / 2, 20);

        this.add(listScroller);
    }

    private void addingButtons() {

        SpellBook.addingBackButton(this);
    }
}
