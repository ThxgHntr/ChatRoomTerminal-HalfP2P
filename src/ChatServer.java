import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends Thread {
    public static final int SERVER_PORT = 12345;
    private final List<ClientModel> clients = new ArrayList<>();

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start();
    }

    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(SERVER_PORT);
            System.out.println("Server is running on port " + SERVER_PORT);

            while (!socket.isClosed()) {
                byte[] receivedData = new byte[1024];
                DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
                socket.receive(receivedPacket);

                boolean isDuplicate = false;
                ClientModel client = new ClientModel(receivedPacket.getAddress(), receivedPacket.getPort());
                for (ClientModel cl : clients) {
                    if (cl.equals(client)) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    clients.add(client);
                    sendClientList(socket);
                }

                String message = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
                if (message.equals("exit")) {
                    System.out.println(client);
                    removeClient(client);
                    System.out.println(clients);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeClient(ClientModel client) {
        clients.remove(client);
    }

    public void sendClientList(DatagramSocket socket) {
        try {
            for (ClientModel client : clients) {
                byte[] sendData = ModelSerializationUtil.serializeModelList(clients);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, client.getAddress(), client.getPort());
                socket.send(sendPacket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
