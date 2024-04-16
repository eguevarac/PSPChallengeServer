package j_panels;

import data_classes.WindowsProcess;
import listeners.BackButtonListener;
import p_s_p_challenge.PSPChallenge;
import tools_classes.FilesRW;
import tools_classes.SpellBook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PanelExecutingPrograms extends JPanel {

    WindowsProcess winProcSelected;
    ArrayList<String> executingProgramsList;
    ArrayList<WindowsProcess> processesList;


    public PanelExecutingPrograms() {

        SpellBook.creatingStandardPanelForFrame(this);


        extractingListOfProcesses();

        manipulatingData();

        addingButtons();

        addingJTable();

        PSPChallenge.frame.setTitle("Tabla de procesos en ejecución");
    }

    private void manipulatingData() {
        processesList = new ArrayList<>();

        Path path = Paths.get("src/resources/files/processes.txt");

        FilesRW.creatingFile(path);

        FilesRW.writtingProcessesInFile(path, executingProgramsList);

        FilesRW.takingProcessesFromFile(processesList);

        FilesRW.deleteFile(path);
    }

    private void addingJTable() {

        // Convertir la lista de objetos a un arreglo bidimensional para la JTable
        Object[][] tableData = new Object[processesList.size()][4];

        for (int i = 0; i < processesList.size(); i++) {
            WindowsProcess windowsProcess = processesList.get(i);
            tableData[i][0] = windowsProcess.getName();
            tableData[i][1] = windowsProcess.getPID();
            tableData[i][2] = windowsProcess.getType();
            tableData[i][3] = windowsProcess.getMemory();
        }

        // Nombres de las columnas
        String[] columns = {"Nombre", "PID", "Tipo de proceso", "Memoria consumida"};

        // Crear el modelo de la tabla
        DefaultTableModel defaultTableModel = new DefaultTableModel(tableData, columns);

        // Crear la tabla con el modelo
        JTable table = new JTable(defaultTableModel);

        // Crear un JScrollPane y agregar la tabla a él
        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setSize(500, 300);
        scrollPane.setLocation(this.getWidth() / 2 - scrollPane.getWidth() / 2, 20);
        this.add(scrollPane);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                winProcSelected = processesList.get(table.getSelectedRow());
            }
        });
    }


    private void extractingListOfProcesses() {
        executingProgramsList = new ArrayList<>();
        //pongo este índice para que no me coja las líneas que no necesito
        int index = 0;
        try {

            Process process = Runtime.getRuntime().exec("taskList");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String resultOfExecution;

            while ((resultOfExecution = br.readLine()) != null) {

                resultOfExecution = resultOfExecution.replaceAll("\s+", ";");

                if (index > 2) {
                    executingProgramsList.add(resultOfExecution);
                }

                index++;
            }

        } catch (IOException e) {

            System.out.println(e);
        }


    }

    private void addingButtons() {

        addingBackButton();

        addingDeleteButton();

    }

    private void addingDeleteButton() {
        JButton deleteButton = new JButton();

        deleteButton.setSize(220, 50);
        deleteButton.setText("Detener proceso seleccionado");
        deleteButton.setLocation(80, 360);
        this.add(deleteButton);
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                checkingAndStoppingProcess();
            }
        });
    }

    private void checkingAndStoppingProcess() {

        if (winProcSelected != null &&
                winProcSelected.getType().equals("Console")) {

            stoppingProcess();

        } else if (winProcSelected == null) {

            JOptionPane.showMessageDialog(null, "No has seleccionado ningún proceso.", "Error", JOptionPane.ERROR_MESSAGE);

        } else if (!winProcSelected.getType().equals("Console")) {

            JOptionPane.showMessageDialog(null, "Sólo puedes detener los procesos de tipo 'Console'.", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void stoppingProcess() {

        try {
            Runtime.getRuntime().exec("taskkill /F /PID ".concat(winProcSelected.getPID()));

            JOptionPane.showMessageDialog(null, "Proceso detenido exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            PSPChallenge.frame.setContentPane(new PanelExecutingPrograms());

        } catch (IOException ex) {

            System.out.println("Problema ejecutando el comando");
        }
    }

    private void addingBackButton() {
        JButton backButton = new JButton();

        backButton.setSize(200, 50);
        backButton.setText("Volver");
        backButton.setLocation(350, 360);
        backButton.addMouseListener(new BackButtonListener());
        this.add(backButton);
    }


}
