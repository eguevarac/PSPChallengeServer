package j_panels;

import j_dialogs.LoginDialog;
import j_dialogs.UserCreationDialog;
import p_s_p_challenge.PSPChallenge;
import tools_classes.SpellBook;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelMain extends JPanel {

    public PanelMain() {


        chooseServerOrClient();

        SpellBook.creatingStandardPanelForFrame(this);

        addingLabels();

        PSPChallenge.frame.setTitle("Programa de gestión de usuarios y procesos");
    }

    private void chooseServerOrClient() {
        int adminChoice = JOptionPane.showConfirmDialog(null, "Quieres iniciar sesión como administrador", "Tipo de usuario", JOptionPane.YES_NO_OPTION);

        if (adminChoice == JOptionPane.NO_OPTION) {
            showConnectionDialog();
        }
    }

    private void showConnectionDialog() {
        String ip;
        do {
            ip = JOptionPane.showInputDialog(null, "Introduce IP del servidor", "Conectarse", JOptionPane.OK_CANCEL_OPTION);
        }while (ip == null || ip.equals(""));
    }

    private void addingLabels() {

        addingRegisterButton();

        addingLoginButton();

        addingExitButton();
    }

    private void addingExitButton() {
        JButton exitButton = new JButton();
        exitButton.setText("Salir");
        exitButton.setSize(200, 50);
        exitButton.setLocation(
                this.getWidth() / 2 - exitButton.getWidth() / 2,
                300);
        this.add(exitButton);

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                JOptionPane.showMessageDialog(null, "Hasta la próxima!");

                System.exit(0);
            }
        });
    }

    private void addingLoginButton() {
        JButton loginButton = new JButton();
        loginButton.setText("Acceder");
        loginButton.setSize(200, 50);
        loginButton.setLocation(
                this.getWidth() / 2 - loginButton.getWidth() / 2,
                200);
        this.add(loginButton);

        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                LoginDialog dialog = new LoginDialog();
                dialog.setVisible(true);
            }
        });
    }

    private void addingRegisterButton() {
        JButton registerButton = new JButton();
        registerButton.setText("Darse de alta");
        registerButton.setSize(200, 50);
        registerButton.setLocation(
                this.getWidth() / 2 - registerButton.getWidth() / 2,
                100);
        this.add(registerButton);
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int userType = PSPChallenge.usersList.isEmpty() ? 0 : 2;

                UserCreationDialog dialog = new UserCreationDialog(userType);
                dialog.setVisible(true);
            }
        });
    }
}
