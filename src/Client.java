import java.net.DatagramSocket;
import java.util.Scanner;

public class Client extends Thread {

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();

            while (true) {
                String message = scanner.nextLine();
                clientThread.sendMessage("message" + name + ": " + message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
