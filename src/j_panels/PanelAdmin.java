package j_panels;

import j_dialogs.UserCreationDialog;
import p_s_p_challenge.PSPChallenge;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelAdmin extends PanelUser {

    public PanelAdmin() {
        super();

        PSPChallenge.frame.setTitle("Panel de control de administrador");
    }

    @Override
    protected void addingButtons() {
        super.addingButtons();

        addingNewRegularUserButton();

        addingNewAdminButton();

        addingDeleteOrModifyButton();
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
