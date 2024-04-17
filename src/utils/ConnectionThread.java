package utils;

import java.net.ServerSocket;

import static utils.SocketsManager.PORT;

public class ConnectionThread extends Thread {

    @Override
    public void run() {
        super.run();
        try {
             SocketsManager.server = new ServerSocket(PORT);
            System.out.println("Socket servidor abierto esperando conexiones...");

            SocketsManager.socketClient = SocketsManager.server.accept();
            System.out.println("IP del cliente:");
            System.out.println(SocketsManager.socketClient.getInetAddress().getHostAddress());

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
