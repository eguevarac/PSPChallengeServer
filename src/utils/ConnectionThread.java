package utils;

import p_s_p_challenge.PSPChallenge;

import javax.swing.*;
import java.net.ServerSocket;
import java.util.ArrayList;

import static utils.SocketsManager.PORT;

public class ConnectionThread extends Thread {

    private final JLabel lblConnectionTxt;
    boolean exit;

    public ConnectionThread(JLabel lblConnectionTxt){
        super();
        this.lblConnectionTxt = lblConnectionTxt;
        exit = false;
    }

    @Override
    public void run() {
        super.run();
        boolean isLoggedIn;
        String response;
        try {
            SocketsManager.server = new ServerSocket(PORT);
            lblConnectionTxt.setText("Socket servidor abierto esperando conexiones...");

            SocketsManager.socketClient = SocketsManager.server.accept();
            lblConnectionTxt.setText(
                    "<html>Conexión establecida!<br><br>" +
                            " IP del cliente: " + SocketsManager.socketClient.getInetAddress().getHostAddress() + "<html>");

        } catch (Exception e) {
            System.out.println(e);
        }

        do{
            isLoggedIn = SocketsManager.getRegisterOrLoginPetition(SocketsManager.socketClient.getInetAddress().getHostAddress());
        }while (!isLoggedIn);

        do{
            lblConnectionTxt.setText(PSPChallenge.userConnected.showData());

            SocketsManager.getPrograms();
            lblConnectionTxt.setText(PSPChallenge.userConnected.showData());


            SocketsManager.getProcesses();
            lblConnectionTxt.setText(PSPChallenge.userConnected.showData());


            SocketsManager.sendString(PSPChallenge.userConnected.getOrderToClient());

            switch (PSPChallenge.userConnected.getOrderToClient()){
                case "stopProcess":
                    SocketsManager.sendString(PSPChallenge.userConnected.getProcessPID());
                    response = SocketsManager.getString();
                    PSPChallenge.userConnected.setProcessPID("");
                    PSPChallenge.userConnected.setOrderToClient("");
                    JOptionPane.showMessageDialog(null, response, "Información", JOptionPane.INFORMATION_MESSAGE);
                    PSPChallenge.userConnected.setExecutingProcesses(new ArrayList<>());
                    PSPChallenge.userConnected.setLoadingProcess("cargando...");
                    lblConnectionTxt.setText(PSPChallenge.userConnected.showData());
                    break;
                default:
                    break;
            }

        }while (!exit);
    }

}
