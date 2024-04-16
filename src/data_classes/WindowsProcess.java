package data_classes;

public class WindowsProcess {

    String name;

    String PID;
    String type;
    String memory;

    public WindowsProcess(String name, String PID, String type, String memory) {

        this.name = name;
        this.PID = PID;
        this.type = type;
        this.memory = memory;
    }


    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
}
