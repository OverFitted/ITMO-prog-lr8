package exmp;

public class GatewayApp {
    public static void main(String[] args) {
        exmp.GatewayLBService gatewayLBService = new exmp.GatewayLBService(38761);
        gatewayLBService.start();
    }
}
