import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

class ClientThread extends Thread {
    private final DatagramSocket socket;
    private List<ClientModel> receivedModelList;

    public ClientThread(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            sendJoinPacket();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    sendExitMessage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));

            while (true) {
                byte[] receivedData = new byte[1024];
                DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
                socket.receive(receivedPacket);

                String receivedString = new String(receivedData, 0, receivedPacket.getLength());
                if (receivedString.startsWith("message")) {
                    System.out.println(receivedString.replaceFirst("message", ""));
                } else {
                    receivedModelList = ModelSerializationUtil.deserializeModelList(receivedPacket.getData());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendJoinPacket() throws IOException {
        DatagramPacket joinPacket = new DatagramPacket("".getBytes(), 0, InetAddress.getLocalHost(), ChatServer.SERVER_PORT);
        socket.send(joinPacket);
    }

    public void sendMessage(String message) throws IOException {
        for (ClientModel client : receivedModelList) {
            if (client.getPort() != socket.getLocalPort()) {
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, client.getAddress(), client.getPort());
                socket.send(sendPacket);
            }
        }
    }

    public void sendExitMessage() throws IOException {
        if (socket != null && !socket.isClosed()) {
            String exitMessage = "exit";
            byte[] sendData = exitMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(), ChatServer.SERVER_PORT);
            socket.send(sendPacket);
            socket.close();
        }
    }
}