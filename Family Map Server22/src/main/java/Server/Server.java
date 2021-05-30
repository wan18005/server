package Server;

import Handler.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

  private static final int MAX_WAITING_CONNECTIONS = 12;

  public static void main(String[] args) {
    String portNumber = "8081";
    new Server().run(portNumber);
  }

  private void run(String portNumber) {
    HttpServer server;
    System.out.println("Initializing HTTP Server");
    try {
      server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    server.setExecutor(null);
    System.out.println("Creating contexts");

    server.createContext("/", new DefaultHandler());
    server.createContext("/user/register", new RegisterHandler());
    server.createContext("/user/login", new LoginHandler());
    server.createContext("/clear", new ClearHandler());
    server.createContext("/fill", new FillHandler());
    server.createContext("/load", new LoadHandler());
    server.createContext("/person", new PersonHandler());
    server.createContext("/event", new EventHandler());

    System.out.println("Starting server");
    server.start();
    System.out.println("Server started");
  }
}
