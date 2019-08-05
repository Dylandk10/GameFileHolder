/**
  This class is for single User object handling and editing of the Users
*/
import java.io.*;
import java.util.Scanner;

public class UserHandler {
  private Users user;

  public UserHandler(String name, String password) throws IOException {
    login(name, password);
  }
  //default for testing purposes
  public UserHandler() throws IOException {
    String name = promptLogin();
    String password = promptPass();
    login(name, password);
  }

  private String promptLogin() {
    Scanner key = new Scanner(System.in);
    System.out.println("Enter loginName");
    String logName = key.nextLine();
    return logName;
  }
  private String promptPass() {
    Scanner key = new Scanner(System.in);
    System.out.println("Enter Password");
    String pass = key.nextLine();
    return pass;
  }

  private void login(String name, String password) throws IOException {
    File file = new File("./../Characters/" + name +".txt");
    //create new file for the player
    if(!file.exists()) {
      try {
        firstLog(name, password);
      } catch(IOException e) {
        System.out.println("Could not create character file");
      }
    }
    //if character exist then match the userName and push player into world user array
    Scanner scan = new Scanner(file);
    while(scan.hasNextLine()) {
      String[] line = scan.nextLine().split("=");
      String[] line2 = scan.nextLine().split("=");
      String[] line3 = scan.nextLine().split("=");
      user = new Users(line[1].trim(), line2[1].trim(), line3[1].trim());
    }
  }
  //returns the user
  public Users getUser() {
    return user;
  }

  private void firstLog(String name, String password) throws IOException {
    playerSaving(name, 0, password);
    writeUserNameFile(name);
    login(name, password);
    HighScoreHandler userH = new HighScoreHandler();
    userH.addNewUser(name, 0);
  }

  public void changeScore(int score) throws IOException {
    user.setScore(score);
    playerSaving(user.getName(), user.getScore(), user.getPassword());
  }

  public void changeName(String name) throws IOException {
    user.setName(name);
    playerSaving(user.getName(), user.getScore(), user.getPassword());
  }
  //for saving players information to a personal file
  public void playerSaving(String name, int score, String password) throws IOException {
    File file = new File("./../Characters/" + name + ".txt");
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
      output.write("Password = " + password);
      output.newLine();
      output.write("Score = " + score);
      output.newLine();
    } catch(Exception e) {
      System.out.println("Could not write character file");
    }
    output.close();
    writer.close();
  }
  //for saving players information to a personal file
  public void playerSaving() throws IOException {
    File file = new File("./../Characters/" + user.getName() + ".txt");
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
      output.write("UserName = " + user.getName());
      output.newLine();
      output.write("Password = " + user.getPassword());
      output.newLine();
      output.write("Score = " + user.getScore());
      output.newLine();
    } catch(Exception e) {
      System.out.println("Could not write character file");
    }
    output.close();
    writer.close();
  }

  private void writeUserNameFile(String name) throws IOException {
    File file = new File("./../Data/UserNames.txt");
    FileWriter writer = new FileWriter(file, true);
    BufferedWriter output = new BufferedWriter(writer);
    if(!file.exists())
      System.out.println("Can not find Username.txt");
    else {
      try {
        output.write(name);
        output.newLine();
      } catch(Exception e) {
        System.out.println("Could not write File");
      }
    }
    output.close();
    writer.close();
  }
}
