/**
  This is for handling all players as a world objects and all the data while UserHandler
  is ment for handling individual objects of the user
*/
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class MainApp {
  //this is a User file of User objects
  public static ArrayList<Users> userList = new ArrayList<Users>();
  //constant for number of useres allowed change to whatever the program is needed can also be dynamic
  public static final int listLength = 12;
  public static Server server;
  //main method
  public static void main(String args[]) throws IOException {
    //list is the data the user enters
    ArrayList<Integer> list = new ArrayList<Integer>();
    //writeFile(list);
  //  readFile(userList);
    //findHighScore(userList);
    //updateSingleUserScore(userList, list);
    //initUserHandler(userList);
    server = new Server();
    server.init();
  }
  //used for client connections from the input stream
  public static Users initUserHandler(String name) throws IOException {
    UserHandler user = new UserHandler(name.trim());
    userList.add(user.getUser());
    return user.getUser();
  }
  //gets users and pushes to userList not for client use
  public static void initUserHandler(ArrayList<Users> userList) throws IOException {
    userList.add(new UserHandler().getUser());
    printCharacterList(userList);
  }
  //the writer function to control the writing flow
  //Note: only use if you want to manually write all data!
  //also changes all user names to user-i where i = 0-listLength
  public static void writeFile(ArrayList list) throws IOException {
    createFile();
    getData(list);
    writeToFile(list);
  }

  //actually writes the data to the file
  public static void writeToFile(ArrayList list) throws IOException {
    File file = new File("data.txt");
    FileWriter writer = new FileWriter(file);
    BufferedWriter outStream = new BufferedWriter(writer);
    try {
      for(int i = 0; i < listLength; i++) {
        outStream.write("User-" + i +" = " + list.get(i));
        outStream.newLine();
      }
    } catch(Exception e) {
      System.out.println("Error writing file : " + e);
    }
    outStream.close();
    writer.close();
  }

  //ask the user to enter the needed data for the list
  public static void getData(ArrayList<Integer> list) {
    Scanner userInput = new Scanner(System.in);

    for(int i = 0; i < listLength; i++) {
      System.out.println("Enter data for memeber " + i);
      //if user enters a error place -1
      String input = userInput.nextLine();
      if(checkInt(input))
        list.add(Integer.parseInt(input));
      else
        list.add(-1);
    }
  }

  //return true if its a int else return false
  public static boolean checkInt(String check) {
    try {
      Integer.parseInt(check);
      return true;
    } catch(Exception e) {
      return false;
    }
  }

  //creates a file if needed
  public static void createFile() {
    File file = new File("data.txt");

    if(!(file.exists())) {
      try {
        file.createNewFile();
        System.out.println("Writing new file");
      } catch(IOException e) {
        System.out.println("Error creating file :" + e);
      }
    } else {
      System.out.println("File exist continue");
    }
  }
  //updates the data file for a single user
  public static void updateDataFile(ArrayList<Integer> list, String userName, int score) throws IOException {
    File file = new File("data.txt");
    Scanner reader = new Scanner(file);

    if(!file.exists()) {
      System.out.println("File not found..closing");
      System.exit(0);
    }

    int i = 0;
    while(reader.hasNextLine()) {
      String line = reader.nextLine().trim();
      int spot = line.indexOf("=");
      String line2 = line.substring(0, spot).trim();

      if(userName.equalsIgnoreCase(line2)) {
        list.set(i, score);
      }
      i++;
    }
    writeToFile(list);
  }
//reads the file with all data including user names
//creates new User object
  public static void readFile(ArrayList<Users> userList) throws IOException {
    File file = new File("data.txt");
    Scanner reader = new Scanner(file);

    if(!file.exists()) {
      System.out.println("File not found..closing");
      System.exit(0);
    }

    while(reader.hasNextLine()) {
      String[] line = reader.nextLine().split("=");
      Users user = new Users(line[0].trim(), line[1].trim());
      userList.add(user);
    }
  }

  //print the Usersslist just for testing
  public static void printCharacterList(ArrayList<Users> userList) {
    for(int i = 0; i < userList.size(); i++) {
      System.out.println(userList.get(i));
    }
  }

  //uses bubble sort to sort the data and then make a high score list
  public static void findHighScore(ArrayList<Users> userList) throws IOException {
    for(int i = 0; i < userList.size()-1; i++) {
      for(int k = 0; k < userList.size()-i-1; k++) {
        if(userList.get(k).getScore() < userList.get(k+1).getScore()) {
          Users temp = new Users(userList.get(k));
          userList.set(k, userList.get(k+1));
          userList.set(k+1, temp);
        }
      }
    }
    writeHighScores(userList);
  }

  //write a highscores file to show the high scores listLength
  public static void writeHighScores(ArrayList<Users> userList) throws IOException {
    File file = new File("highScores.txt");
    FileWriter writer = new FileWriter(file);
    BufferedWriter outStream = new BufferedWriter(writer);
    try {
      if(!file.exists())
        file.createNewFile();
    } catch(IOException e) {
      System.out.println("Error writing file");
    }
    try {
      for(int i = 0; i < listLength; i++) {
        outStream.write(userList.get(i).getName() + "= "+ userList.get(i).getScore());
        outStream.newLine();
      }
    } catch(Exception e) {
      System.out.println("Error writing highscores file : " + e);
    }
    outStream.close();
    writer.close();
  }

  //method for updating a single userScore also updates data file and highscores file
  public static void updateSingleUserScore(ArrayList<Users> userList, ArrayList<Integer> list) throws IOException {
    Scanner keyboard = new Scanner(System.in);
    System.out.println("Enter the user name to change score");
    String input = keyboard.nextLine();

    for(int i = 0; i < userList.size(); i++) {
      if(input.equalsIgnoreCase(userList.get(i).getName())) {
        System.out.println("Enter new score for user " + input);
        String inputInt = keyboard.nextLine();

        if(checkInt(inputInt)) {
          userList.get(i).setScore(Integer.parseInt(inputInt));
          updateDataFile(list, input, Integer.parseInt(inputInt));
        }
        else
          System.out.println("Not a valid input");

      }
    }
    findHighScore(userList);
  }
}
