package j_panels;

import p_s_p_challenge.PSPChallenge;
import tools_classes.SpellBook;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelUser extends JPanel {


    public PanelUser() {

        SpellBook.creatingStandardPanelForFrame(this);

        addingButtons();

        PSPChallenge.frame.setTitle("Panel de control de usuario");
    }


    protected void addingButtons() {

        addingProfileButton();

        addingLogoutButton();

        addingInstalledPrgButton();

        addingExecuteProgButton();

    }

    private void addingExecuteProgButton() {
        JButton executingProgButton = new JButton();
        executingProgButton.setText("Procesos en ejecución");
        executingProgButton.setSize(200, 50);
        executingProgButton.setLocation(
                20,
                250);
        this.add(executingProgButton);
        executingProgButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                PSPChallenge.frame.setContentPane(new PanelExecutingPrograms());
            }
        });

    }

    private void addingInstalledPrgButton() {

        JButton consultButton = new JButton();
        consultButton.setText("Programas instalados");
        consultButton.setSize(200, 50);
        consultButton.setLocation(
                20,
                150);
        this.add(consultButton);
        consultButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                PSPChallenge.frame.setContentPane(new PanelInstalledPrograms());
            }
        });
    }


    private void addingProfileButton() {
        JButton profileButton = new JButton();
        profileButton.setText("Editar perfil");
        profileButton.setSize(200, 50);
        profileButton.setLocation(
                20,
                50);
        this.add(profileButton);
        profileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                PSPChallenge.frame.setContentPane(new PanelEditProfile());
            }
        });
    }

    private void addingLogoutButton() {

        JButton logoutButton = new JButton();
        logoutButton.setText("Cerrar sesión");
        logoutButton.setSize(150, 20);
        logoutButton.setLocation(
                445,
                420);
        this.add(logoutButton);
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                PSPChallenge.frame.setContentPane(new PanelMain());
                PSPChallenge.actualUser = null;
            }
        });
    }
}
