package exmp;

import exmp.commands.CommandResult;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientServerIntegrationTest {
    private final Random random = new Random();
    private static final String TEST_HOST = "localhost";
    private static exmp.App app;

    int port;
    Thread serverThread;


    private static final Logger logger = LogManager.getLogger(ClientServerIntegrationTest.class);

    @BeforeAll
    public static void setup() {
        app = Mockito.spy(new exmp.App("src/main/resources/input.xml"));

        logger.info("Staring tests");
    }

    @AfterAll
    public static void tearDown() {
        app.switchOff();

        logger.info("Testing finished");
    }

    @BeforeEach
    public void serverSetup() {
        port = random.nextInt(65535);
        exmp.Server server = new exmp.Server(port, app);

        when(app.getState()).thenReturn(true);

        serverThread = new Thread(server::start);
        serverThread.start();
    }

    @Test
    public void testPacketLoss() throws Exception {
        logger.debug("Packet loss test started");

        String commandName = "add";
        String commandInput = "Sample Product";
        CommandResult result = sendCommandToServer(port + 1, commandName, commandInput);

        assertNull(result);

        serverThread.join();
    }

    @Test
    public void testPacketDelay() throws Exception {
        logger.debug("Packet delay test started");

        String commandName = "add";
        String commandInput = "Sample Product";
        CommandResult expectedResult = new CommandResult(0, "Product added successfully.", null);
        doReturn(expectedResult).when(app).executeCommand(commandName, commandInput);

        CommandResult actualResult = sendCommandToServerWithDelay(port, commandName, commandInput, delay);

        assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
        assertEquals(expectedResult.getOutput(), actualResult.getOutput());

        serverThread.join();
    }

    @Test
    public void testIncorrectPacketOrder() throws Exception {
        logger.debug("Packet incorrect order test started");

        String commandName1 = "add";
        String commandInput1 = "Sample Product 1";
        CommandResult expectedResult1 = new CommandResult(0, "Product 1 added successfully.", null);
        doReturn(expectedResult1).when(app).executeCommand(commandName1, commandInput1);

        String commandName2 = "add";
        String commandInput2 = "Sample Product 2";
        CommandResult expectedResult2 = new CommandResult(0, "Product 2 added successfully.", null);
        doReturn(expectedResult2).when(app).executeCommand(commandName2, commandInput2);

        CommandResult actualResult2 = sendCommandToServer(port, commandName2, commandInput2);
        CommandResult actualResult1 = sendCommandToServer(port, commandName1, commandInput1);

        assertEquals(expectedResult1.getStatusCode(), actualResult1.getStatusCode());
        assertNotEquals(expectedResult1.getOutput(), actualResult1.getOutput());

        assertEquals(expectedResult2.getStatusCode(), actualResult2.getStatusCode());
        assertNotEquals(expectedResult2.getOutput(), actualResult2.getOutput());

        serverThread.join();
    }

    @Test
    public void testClientServerCommunication() throws Exception {
        logger.debug("Communication test started");

        String commandName = "info";
        String commandInput = "";

        CommandResult expectedResult = new CommandResult(0, null, null);

        doReturn(expectedResult).when(app).executeCommand(commandName, commandInput);

        CommandResult actualResult = sendCommandToServer(port, commandName, commandInput);

        assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
        assertEquals(expectedResult.getOutput(), actualResult.getOutput());
    }

    private CommandResult sendCommandToServerWithDelay(int port, String commandName, String commandInput, int delayMillis) throws Exception {
        try (DatagramSocket socket = new DatagramSocket()) {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(new exmp.commands.CommandData(commandName, commandInput));
            byte[] dataToSend = byteOutput.toByteArray();

            DatagramPacket packetToSend = new DatagramPacket(dataToSend, dataToSend.length, new InetSocketAddress(ClientServerIntegrationTest.TEST_HOST, port));
            socket.send(packetToSend);

            if (delayMillis > 0) {
                Thread.sleep(delayMillis);
            }

            byte[] dataToReceive = new byte[4096];
            DatagramPacket packetToReceive = new DatagramPacket(dataToReceive, dataToReceive.length);
            socket.receive(packetToReceive);

            ByteArrayInputStream byteInput = new ByteArrayInputStream(dataToReceive);
            ObjectInputStream objectInput = new ObjectInputStream(byteInput);
            return (CommandResult) objectInput.readObject();
        }
    }

    private CommandResult sendCommandToServer(int port, String commandName, String commandInput) throws Exception {
        try (DatagramSocket socket = new DatagramSocket()) {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(new exmp.commands.CommandData(commandName, commandInput));
            byte[] dataToSend = byteOutput.toByteArray();

            DatagramPacket packetToSend = new DatagramPacket(dataToSend, dataToSend.length, new InetSocketAddress(ClientServerIntegrationTest.TEST_HOST, port));
            socket.send(packetToSend);

            byte[] dataToReceive = new byte[4096];
            DatagramPacket packetToReceive = new DatagramPacket(dataToReceive, dataToReceive.length);
            socket.receive(packetToReceive);

            ByteArrayInputStream byteInput = new ByteArrayInputStream(dataToReceive);
            ObjectInputStream objectInput = new ObjectInputStream(byteInput);
            return (CommandResult) objectInput.readObject();
        }
    }
}
