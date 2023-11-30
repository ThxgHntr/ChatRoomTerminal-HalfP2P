# Java Chat Application

This is a simple chat application implemented in Java using DatagramSocket for communication. The application allows multiple clients to connect to a server and exchange messages.

## Features

- **Multi-Client Communication:** Multiple clients can connect to the server simultaneously and exchange messages.
- **Dynamic Client List:** The server maintains a dynamic list of connected clients and updates it when a new client joins or an existing client exits.
- **Clean Code:** The code is organized and follows Java best practices for readability and maintainability.

## Prerequisites

- Java Development Kit (JDK) installed on your machine.

## Code Structure

- **ChatServer.java:** The server implementation.
- **Client.java:** The client implementation.
- **ClientModel.java:** Represents a client with its address and port.
- **ClientThread.java:** Manages communication for each client on the server.
- **ModelSerializationUtil.java:** Utility class for serializing and deserializing client models.

## Important Notes

- The server runs on port 12345 by default. Make sure this port is available and not blocked by a firewall.
- The communication is done using UDP DatagramSocket.

## Contribution

Feel free to contribute to the project by submitting issues or pull requests.
