package listeners;

import data_classes.User;
import j_panels.PanelAdmin;
import j_panels.PanelInstalledPrograms;
import j_panels.PanelMain;
import p_s_p_challenge.PSPChallenge;
import j_panels.PanelUser;
import tools_classes.BlowFishManager;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Listener para el botón de login de usuario
 */
public class LoginDialogListener extends MouseAdapter {

    private final JTextField NAME_FIELD;
    private final JPasswordField PASSWD_FIELD;
    private final JDialog DIALOG;
    private User foundUser;


    public LoginDialogListener(JTextField nameField, JPasswordField paswdField, JDialog dialog) {
        this.NAME_FIELD = nameField;
        this.PASSWD_FIELD = paswdField;
        this.DIALOG = dialog;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        String name = NAME_FIELD.getText().trim();
        String passwd = String.valueOf(PASSWD_FIELD.getPassword()).trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(null, "No has introducido nombre de usuario.", "Error", JOptionPane.ERROR_MESSAGE);

        } else if (passwd.isEmpty()) {

            JOptionPane.showMessageDialog(null, "No has introducido contraseña.", "Error", JOptionPane.ERROR_MESSAGE);

        } else {

            lookingForUser(name);

            if (foundUser != null) {

                loginUser(passwd);

            } else {

                JOptionPane.showMessageDialog(null, "El nombre de usuario no está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loginUser(String passwd) {

        if (BlowFishManager.checkingPasswd(passwd, foundUser)) {

            PSPChallenge.actualUser = foundUser;

            DIALOG.dispose();

            goingPanelDependingOnUserType();


        } else {

            JOptionPane.showMessageDialog(null, "La contraseña no coincide con la del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goingPanelDependingOnUserType() {

        switch (PSPChallenge.actualUser.getUserType()) {

            case 0, 1:
                PSPChallenge.frame.setContentPane(new PanelAdmin());
                break;
            case 2:
                PSPChallenge.frame.setContentPane(new PanelUser());
                break;
        }

    }

    private void lookingForUser(String name) {

        for (User user :
                PSPChallenge.usersList) {

            if (user.getName().toUpperCase().trim().equals(name.toUpperCase())) {

                foundUser = user;
            }
        }
    }
}
