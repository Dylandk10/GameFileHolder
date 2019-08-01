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

  //this is the listener for sockets accepted allows for only 20 fixed threads
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
          String result = inputStream.nextLine();
          String[] res = result.split(":");
          int caseNum = Integer.parseInt(res[0]);
          user = caseReader(caseNum, res[1]);
          outputSender(user, outputStream, caseNum);
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
    //outputSender is used for sending data to client
    public void outputSender(Users user, PrintWriter output, int caseReader) {
      switch(caseReader) {
        //case 1 = login
        case 1:
          output.println("UserName: " + user.getName() + " Score: " + user.getScore());
          break;
        //case 2 = chnage user name
        case 2:
          output.println("UserName changed to: " + user.getName());
          break;
      }
    }
    //used to determin where send the thread
    private Users caseReader(int result, String message) throws IOException {
      Users xuser = new Users();
      switch(result) {
        //case 1 = login
        case 1:
          xuser = MainApp.initUserHandler(message);
          break;
        //case 2 = change user name
          case 2:
          xuser = MainApp.userhandlerAdjustName(message);
          break;
      }
      return xuser;
    }
  }
}
