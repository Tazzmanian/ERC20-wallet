package wallet.erc20.tazzmanian.erc20wallet;

public class ServerItems {
    String name;
    boolean active;
    String host;
    String port;
    long id;

    public ServerItems(String name, boolean active, String host, String port, long id) {
        this.name = name;
        this.active = active;
        this.host = host;
        this.port = port;
        this.id = id;
    }
}
