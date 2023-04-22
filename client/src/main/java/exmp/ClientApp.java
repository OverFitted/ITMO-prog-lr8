package exmp;

public class ClientApp {
    public static void main(String[] args) {
        exmp.Client client = new exmp.Client("localhost", 38761);
        client.start();
    }
}
