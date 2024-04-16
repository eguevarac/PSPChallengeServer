package j_panels;

import j_dialogs.UserCreationDialog;
import p_s_p_challenge.PSPChallenge;
import tools_classes.SpellBook;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelAdmin extends JPanel{

    public PanelAdmin() {

        SpellBook.creatingStandardPanelForFrame(this);

        addingButtons();

        PSPChallenge.frame.setTitle("Panel de control de administrador");
    }


    private void addingButtons() {

        addingProfileButton();

        addingLogoutButton();

        addingInstalledPrgButton();

        addingExecuteProgButton();

        addingNewRegularUserButton();

        addingNewAdminButton();

        addingDeleteOrModifyButton();
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

    private void addingDeleteOrModifyButton() {
        JButton deleteModifyButton = new JButton();
        deleteModifyButton.setText("Borrar/Modificar usuario");
        deleteModifyButton.setSize(200, 50);
        deleteModifyButton.setLocation(
                400,
                250);
        this.add(deleteModifyButton);
        deleteModifyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                PSPChallenge.frame.setContentPane(new PanelDeleteModifyUsers());

            }
        });
    }

    private void addingNewAdminButton() {
        JButton newAdminButton = new JButton();
        newAdminButton.setText("Añadir Administrador");
        newAdminButton.setSize(200, 50);
        newAdminButton.setLocation(
                400,
                150);
        this.add(newAdminButton);
        newAdminButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                UserCreationDialog dialog = new UserCreationDialog(1);
                dialog.setVisible(true);
            }
        });
    }


    private void addingNewRegularUserButton() {
        JButton newUserButton = new JButton();
        newUserButton.setText("Añadir usuario");
        newUserButton.setSize(200, 50);
        newUserButton.setLocation(
                400,
                50);
        this.add(newUserButton);
        newUserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                UserCreationDialog dialog = new UserCreationDialog(2);
                dialog.setVisible(true);
            }
        });
    }
}
