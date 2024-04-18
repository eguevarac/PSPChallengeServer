package utils;

import p_s_p_challenge.PSPChallenge;

import javax.swing.*;
import java.net.ServerSocket;

import static utils.SocketsManager.PORT;

public class ConnectionThread extends Thread {

    private final JLabel lblConnectionTxt;

    public ConnectionThread(JLabel lblConnectionTxt){
        super();
        this.lblConnectionTxt = lblConnectionTxt;
    }

    @Override
    public void run() {
        super.run();
        boolean isLoggedIn;
        try {
            SocketsManager.server = new ServerSocket(PORT);
            lblConnectionTxt.setText("Socket servidor abierto esperando conexiones...");

            SocketsManager.socketClient = SocketsManager.server.accept();
            lblConnectionTxt.setText(
                    "<html>Conexión establecida!<br><br>" +
                            " IP del cliente: " + SocketsManager.socketClient.getInetAddress().getHostAddress() + "<html>");
//            System.out.println("IP del cliente:");
//            System.out.println(SocketsManager.socketClient.getInetAddress().getHostAddress());

        } catch (Exception e) {
            System.out.println(e);
        }

        do{
            isLoggedIn = SocketsManager.getRegisterOrLoginPetition(SocketsManager.socketClient.getInetAddress().getHostAddress());
        }while (!isLoggedIn);

        lblConnectionTxt.setText(PSPChallenge.userConnected.showUserData("Cargando...", "Cargando..."));
    }

}
