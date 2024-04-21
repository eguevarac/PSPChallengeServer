package utils;

import data_classes.User;
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

            receiveOrderFromClient();

        }while (!exit);
    }


    /**
     * Recibe la petición de modificación de usuario del cliente
     * y lo actualiza
     */
    private void receiveOrderFromClient() {
        String clientOrder = SocketsManager.getString();
        int indexToDelete;
        if(clientOrder.equals("changeUser")){

            User userToChange = SocketsManager.getUserFromClient();

            indexToDelete = lookingForUser();

            updateUser(userToChange, indexToDelete);

            showAndSendInfo();

        }
    }

    /**
     * Busca al usuario en la lista de usuarios y obtiene su index
     * @return int con el index del usuario
     */
    private int lookingForUser() {
        int indexToDelete = -1;
        for (User user :
                PSPChallenge.usersList) {
            if(user.getName().equals(PSPChallenge.userConnected.getName())){
                indexToDelete = PSPChallenge.usersList.indexOf(user);
            }
        }
        return indexToDelete;
    }

    /**
     * Actualiza la lista de usuarios y sobreescribe el fichero
     * @param userToChange User que será modificado en la lista
     * @param indexToDelete int con el índice del usuario que hay que modificar
     */
    private void updateUser(User userToChange, int indexToDelete) {
        PSPChallenge.usersList.remove(indexToDelete);
        PSPChallenge.usersList.add(userToChange);
        FilesRW.overwritingFile();
        PSPChallenge.userConnected.setName(userToChange.getName());
    }

    /**
     * Muestra el diálogo de información de actualización de usuario y le envía la respuesta al cliente
     */
    private void showAndSendInfo() {
        JOptionPane.showMessageDialog(null, "El cliente ha cambiado de nombre o contraseña", "Información", JOptionPane.INFORMATION_MESSAGE);
        SocketsManager.sendString("Usuario actualizado con éxito");
        lblConnectionTxt.setText(PSPChallenge.userConnected.showData());
    }



    /**
     * Envía la orden (o nada) al cliente
     */
    private void sendOrderToClient() {

        SocketsManager.sendString(PSPChallenge.userConnected.getOrderToClient());

        if(PSPChallenge.userConnected.getOrderToClient().equals("stopProcess")) {

            String response;
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
