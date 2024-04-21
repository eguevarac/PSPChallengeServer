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
    boolean isClientConnected;
    boolean isLoggedIn;

    public ConnectionThread(JLabel lblConnectionTxt){
        super();
        this.lblConnectionTxt = lblConnectionTxt;
    }

    @Override
    public void run() {
        super.run();
        exit = false;
        isClientConnected = false;

        do{
            System.out.println("EMPIEZA EL BUCLE");
            System.out.println("VALOR DEL BOOLEAN -> " + isLoggedIn);
            if(!isClientConnected){

                System.out.println("NO ESTÁ CONECTADO, ASÍ QUE SE CONECTA");
                establishConnection();

                isClientConnected = true;
            }
            System.out.println("CHEQUEA EL LOGIN");
            awaitForClientLogin();

            System.out.println("COGE PROGRAMAS");
            SocketsManager.getPrograms();
            lblConnectionTxt.setText(PSPChallenge.userConnected.showData());

            System.out.println("COGE PROCESOS");
            SocketsManager.getProcesses();
            lblConnectionTxt.setText(PSPChallenge.userConnected.showData());

            System.out.println("ENVÍA PETICIÓN A CLIENTE");
            sendOrderToClient();

            System.out.println("RECIBE PETICIÓN DE CLIENTE");
            receiveOrderFromClient();

            System.out.println("CHEQUEA SI ESTÁ LOGEADO");
            checkIfClientStillConnected();


        }while (!exit);
    }

    /**
     * Chequea si el usuario ha cerrado sesión y actualiza la interfaz
     */
    private void checkIfClientStillConnected() {
        isLoggedIn = SocketsManager.checkUserConnection();
        if(!isLoggedIn){
            JOptionPane.showMessageDialog(null, "El usuario ha cerrado sesión", "Información", JOptionPane.INFORMATION_MESSAGE);
            PSPChallenge.userConnected.clearDataAfterOrder();
            lblConnectionTxt.setText(
                    "<html>Conexión establecida!<br><br>" +
                            " IP del cliente: " + SocketsManager.socketClient.getInetAddress().getHostAddress() + "<html>");
        }
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
        while(!isLoggedIn){
            System.out.println("NO ESTÁ LOGEADO");
            isLoggedIn = SocketsManager.getRegisterOrLoginPetition(SocketsManager.socketClient.getInetAddress().getHostAddress());
        }

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
