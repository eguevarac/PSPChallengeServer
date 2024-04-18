package data_classes;

import utils.SocketsManager;

import java.util.ArrayList;

public class UserConnected {

    private String name;
    private String ip;
    private ArrayList<String> installedPrograms;
    private ArrayList<WindowsProcess> executingProcesses;


    public UserConnected(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public ArrayList<String> getInstalledPrograms() {
        return installedPrograms;
    }

    public void setInstalledPrograms(ArrayList<String> installedPrograms) {
        this.installedPrograms = installedPrograms;
    }

    public ArrayList<WindowsProcess> getExecutingProcesses() {
        return executingProcesses;
    }

    public void setExecutingProcesses(ArrayList<WindowsProcess> executingProcesses) {
        this.executingProcesses = executingProcesses;
    }

    @Override
    public String toString() {
        return "<html>Conexión establecida!<br><br>" +
                " IP del cliente: " + ip + "<br><br>" +
                "Nombre del usuario: " + name + "<html>";
    }
}
