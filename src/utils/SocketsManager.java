package utils;

import data_classes.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class SocketsManager {

    public static String ipServer;
    public static final int PORT = 5000;
    public static boolean isOpen = true;
    public static Socket socketClient;
    public static ServerSocket server;
    public static User userTryingLogin;


    /**
     * Recoge la petición de registro o login del usuario
     */
    public static boolean getRegisterOrLoginPetition() {
        String petition;
        boolean isLoggedIn = false;
        try {
            InputStream is = socketClient.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            petition = (String) ois.readObject();
            switch (petition){
                case "register":
                    tryToRegister();
                    System.out.println("Ha escogido registro");
                    break;
                case "login":
                    System.out.println("Ha escogido login");
                    isLoggedIn = true;
                    //tryToLogin();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return isLoggedIn;
    }

    private static void tryToRegister() {
        User userToRegister = getUser();
        boolean alreadyExist;
        if (userToRegister != null){
            alreadyExist = SpellBook.checkingIfUserExist(userToRegister.getName());
            if(!alreadyExist){
                SpellBook.creatingNewUser(userToRegister.getName(), userToRegister.getPasswd(), userToRegister.getUserType());
                sendResponse("Usuario registrado con éxito");
            }else{
                sendResponse("Ya existe un usuario registrado con ese nombre");
            }
        }else {
            sendResponse("Ha habido un error en el registro de usuario");
        }
    }


    /**
     * Envía una respuesta al cliente
     * @param response String con la respuesta
     */
    public static void sendResponse(String response) {
        try {
            new ObjectOutputStream(socketClient.getOutputStream()).writeObject(response);
        } catch (IOException ex) {
            System.out.println("excepción IOE");
        }
    }

    public static User getUser() {
        User user = null;
        try {
            InputStream is = socketClient.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            user = (User) ois.readObject();
            System.out.println(user.getName());
            System.out.println(user.getPasswd());
        } catch (Exception e) {

            System.out.println(e);
        }
        return user;
    }
















    public static void closeServer() {
        try{
            socketClient.close();
            server.close();
            System.out.println("Conexión con cliente cerrada");
            System.out.println("Servidor cerrado");

        }catch (Exception e){

            System.out.println(e);
        }
    }

}
