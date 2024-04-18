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

        establishConnection();

        awaitForClientLogin();

        do{
            SocketsManager.getPrograms();
            lblConnectionTxt.setText(PSPChallenge.userConnected.showData());

            SocketsManager.getProcesses();
            lblConnectionTxt.setText(PSPChallenge.userConnected.showData());

            sendOrderToClient();

        }while (!exit);
    }

    /**
     * Envía la orden (o nada) al cliente
     */
    private void sendOrderToClient() {
        String response;

        SocketsManager.sendString(PSPChallenge.userConnected.getOrderToClient());

        if(PSPChallenge.userConnected.getOrderToClient().equals("stopProcess")) {

            SocketsManager.sendString(PSPChallenge.userConnected.getProcessPID());
            response = SocketsManager.getString();
            JOptionPane.showMessageDialog(null, response, "Información", JOptionPane.INFORMATION_MESSAGE);
            PSPChallenge.userConnected.clearDataAfterOrder();
            lblConnectionTxt.setText(PSPChallenge.userConnected.showData());
        }
    }

    /**
     * Espera a que se conecte un cliente
     */
    private void awaitForClientLogin() {
        boolean isLoggedIn;
        do{
            isLoggedIn = SocketsManager.getRegisterOrLoginPetition(SocketsManager.socketClient.getInetAddress().getHostAddress());
        }while (!isLoggedIn);

        lblConnectionTxt.setText(PSPChallenge.userConnected.showData());
    }


    /**
     * establece la conexión con el cliente
     */
    private void establishConnection() {
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
    }

}
