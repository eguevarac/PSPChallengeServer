package utils;

import listeners.BackButtonListener;

import javax.swing.*;
import java.awt.*;

/**
 * Almacena métodos diversos para usar a lo largo de la aplicación
 */
public abstract class SpellBook {

    public static JPasswordField passwdFieldCreation(JPanel contentPane, int xPosition, int yPosition) {
        JPasswordField passwdField = new JPasswordField();

        passwdField.setSize(150, 20);
        passwdField.setLocation(
                xPosition,
                yPosition);
        contentPane.add(passwdField);

        return passwdField;
    }

    public static JLabel textLabelCreation(JPanel contentPane, String text, int xPosition, int yPosition) {
        JLabel labelTxt = new JLabel();
        labelTxt.setText(text);
        labelTxt.setForeground(Color.WHITE);
        labelTxt.setSize(160, 20);
        labelTxt.setLocation(
                xPosition,
                yPosition);
        contentPane.add(labelTxt);

        return labelTxt;
    }

    public static JTextField txtFieldCreation(JPanel contentPane, int xPosition, int yPosition) {
        JTextField txtField = new JTextField();
        txtField.setSize(150, 20);
        txtField.setLocation(
                xPosition,
                yPosition);
        contentPane.add(txtField);

        return txtField;
    }


    public static void creatingStandardPanelForFrame(JPanel contentPane) {

        contentPane.setPreferredSize(new Dimension(640, 480));
        contentPane.setSize(new Dimension(640, 480));
        contentPane.setBackground(new Color(60, 61, 59));

        contentPane.setLayout(null);
    }


    public static void creatingStandardJDialog(String tittle, JDialog jDialog) {

        jDialog.setTitle(tittle);
        jDialog.setSize(380, 240);
        jDialog.setPreferredSize(jDialog.getSize());
        jDialog.setLocationRelativeTo(null);

        Toolkit pantalla = Toolkit.getDefaultToolkit();
        Image icon = pantalla.getImage("src/resources/img/server.png");
        jDialog.setIconImage(icon);

        jDialog.setModal(true);

    }

    public static JPanel creatingStandardPanelForDialog() {
        JPanel contentPane = new JPanel();

        contentPane.setPreferredSize(new Dimension(380, 240));
        contentPane.setSize(new Dimension(380, 240));
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(60, 61, 59));

        return contentPane;
    }

    public static void addingBackButton(JPanel panel) {
        JButton backButton = new JButton();

        backButton.setSize(200, 50);
        backButton.setText("Volver");
        backButton.setLocation(panel.getWidth() / 2 - backButton.getWidth() / 2, 360);
        backButton.addMouseListener(new BackButtonListener());
        panel.add(backButton);
    }

}
