package listeners;

import j_panels.PanelAdmin;
import p_s_p_challenge.PSPChallenge;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Listener genérico para el botón "volver"
 */
public class BackButtonListener extends MouseAdapter {

    JPanel previousPanel;

    public BackButtonListener() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        switch (PSPChallenge.actualUser.getUserType()) {

            case 0, 1:
                PSPChallenge.frame.setContentPane(new PanelAdmin());
                PSPChallenge.frame.setTitle("Panel de control de administrador");
                break;
            case 2:
                PSPChallenge.frame.setContentPane(new PanelAdmin());
                PSPChallenge.frame.setTitle("Panel de control de usuario");
                break;
        }


    }
}
