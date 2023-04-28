package exmp;

import exmp.commands.CommandResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientServerIntegrationTest {
    private static final int SERVER_PORT = 12321;
    private static final String HOST = "localhost";
    private static exmp.Server server;
    private static final exmp.App app = new exmp.App("src/main/resources/input.xml");

    private static final Logger logger = LogManager.getLogger(ClientServerIntegrationTest.class);

    @BeforeAll
    public static void setUp() {
        server = new exmp.Server(SERVER_PORT, app);
        exmp.Client client = new exmp.Client(HOST, SERVER_PORT);

        new Thread(() -> server.start()).start();
    }

    @Test
    public void testSuccessfulCommunication() throws IOException, ClassNotFoundException {
        String commandName = "help";
        String commandInput = "";

        CommandResult expectedResult = app.executeCommand(commandName, commandInput);
        CommandResult result = sendCommand(commandName, commandInput);

        assertEquals(expectedResult.getStatusCode(), result.getStatusCode());
        assertEquals(expectedResult.getOutput(), result.getOutput());
    }

    @Test
    public void testPacketLoss() throws IOException, InterruptedException {
        DatagramSocket mockedSocket = mock(DatagramSocket.class);
        doNothing().when(mockedSocket).send(Mockito.any(DatagramPacket.class));

    }

    @Test
    public void testPacketDuplication() throws IOException, InterruptedException {
    }

    @Test
    public void testPacketReordering() throws IOException, InterruptedException {
    }

    private CommandResult sendCommand(String commandName, String commandInput) throws IOException, ClassNotFoundException {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            exmp.commands.CommandData outCommand = new exmp.commands.CommandData(commandName, commandInput);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(outCommand);
            byte[] data = byteArrayOutputStream.toByteArray();

            DatagramPacket sendPacket = new DatagramPacket(data, data.length, new InetSocketAddress(HOST, SERVER_PORT));
            datagramSocket.send(sendPacket);

            ByteBuffer buffer = ByteBuffer.allocate(65536);
            DatagramPacket receivePacket = new DatagramPacket(buffer.array(), buffer.capacity());
            datagramSocket.receive(receivePacket);
            buffer.position(receivePacket.getLength());

            buffer.flip();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            return (CommandResult) objectInputStream.readObject();
        }
    }
}
