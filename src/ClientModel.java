import java.io.Serializable;
import java.net.InetAddress;

public class ClientModel implements Serializable {

    private final InetAddress address;
    private final int port;

    ClientModel(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientModel client = (ClientModel) o;

        if (port != client.port) return false;
        return address.equals(client.address);
    }
}
