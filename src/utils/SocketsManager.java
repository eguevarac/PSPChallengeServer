package utils;

import data_classes.User;

import java.net.ServerSocket;
import java.net.Socket;

public abstract class SocketsManager {

    public static String ipServer;
    public static final int PORT = 5000;
    public static boolean isOpen = true;
    public static Socket socketClient;
    public static ServerSocket server;
    public static User userTryingLogin;



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
