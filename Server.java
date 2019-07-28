import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

/**
  This is the server for listening to connections comming from the client only handles connections and return vales
*/
public class Server {
  ServerSocket listener;
  ExecutorService pool;
  Users user;

  //this is the listener for sockets accepted
  public void init() throws IOException {
    try {
      listener = new ServerSocket(9898);
      System.out.println("Server Running on port 9898");
      pool = Executors.newFixedThreadPool(20);
    } catch(IOException e) {
      System.out.println("Error starting server");
    }
      while(true) {
        pool.execute(new ServerThread(listener.accept()));
      }
    }

  private class ServerThread implements Runnable {
    private Socket socket;

    ServerThread(Socket socket) {
      this.socket = socket;
    }

    //this is the main thread reader for the server
    @Override
    public void run() {
      try {
        Scanner inputStream = new Scanner(socket.getInputStream());
        PrintWriter outputStream = new PrintWriter(socket.getOutputStream(), true);

        while(inputStream.hasNextLine()) {
          //sends to get the user from back end then returns the user for use
          String name = inputStream.nextLine();
          System.out.println(name);
          user = MainApp.initUserHandler(name);
          outputStream.println("UserName: " + user.getName() + ", Score: " + user.getScore());
        }
      } catch(Exception e) {
        System.out.println("Socket Error: " + socket);
      } finally {
        try {
          socket.close();
        } catch(IOException e) {
          System.out.println("Could not close socket");
        }
      }
    }
  }
}
