/**
  This class is for single User object handling and editing of the Users
*/
import java.io.*;
import java.util.Scanner;

public class UserHandler {
  private Users user;
  public UserHandler() throws IOException {
    String name = promptLogin();
    login(name);
  }

  public String promptLogin() {
    Scanner key = new Scanner(System.in);
    System.out.println("Enter loginName");
    String logName = key.nextLine();
    return logName;
  }

  public void login(String name) throws IOException {
    File file = new File("./Characters/" + name +".txt");
    //create new file for the player
    if(!file.exists()) {
      try {
        firstLog(name);
      } catch(IOException e) {
        System.out.println("Could not create character file");
      }
    }
    //if character exist then match the userName and push player into world user array
    Scanner scan = new Scanner(file);
    while(scan.hasNextLine()) {
      String[] line = scan.nextLine().split("=");
      String[] line2 = scan.nextLine().split("=");
      user = new Users(line[1].trim(), line2[1].trim());
    }
  }
  public Users getUser() {
    return user;
  }

  public void firstLog(String name) throws IOException {
    playerSaving(name, 0);
    login(name);
  }

  //for saving players information to a personal file
  public void playerSaving(String name, int score) throws IOException {
    File file = new File("./Characters/" + name + ".txt");
    FileWriter writer = new FileWriter(file);
    BufferedWriter output = new BufferedWriter(writer);

    if(!file.exists()) {
      try {
        file.createNewFile();
      } catch(IOException e) {
        System.out.println("player File not found and can not create one");
      }
    }

    try {
      output.write("UserName = " + name);
      output.newLine();
      output.write("Score = " + score);
      output.newLine();
    } catch(Exception e) {
      System.out.println("Could not write character file");
    }
    output.close();
    writer.close();
  }
}
