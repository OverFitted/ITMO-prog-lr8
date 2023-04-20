package exmp;

import exmp.client.Client;

public class ClientApp {
    public static void main(String[] args) {
        Client client = new Client("localhost", 5555);
        client.start();
    }
}
