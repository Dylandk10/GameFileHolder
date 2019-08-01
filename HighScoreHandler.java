import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class HighScoreHandler {
  private static ArrayList<String> names = new ArrayList<String>();
  private static ArrayList<String> findScoresHolder = new ArrayList<String>();

  public HighScoreHandler() {
    System.out.println("Initializing highScoreHandler");
  }
  //inits the userhandler to setup the highscores
  public void init() throws IOException {
    readAllUserNames();
    findUserAndScore();
    findHighScore();
    writeHighScores();
  }
  //pushes all the userNames to the arrayList names
  private void readAllUserNames() throws IOException {
    File file = new File("./../Data/UserNames.txt");
    Scanner scan = new Scanner(file);

    while(scan.hasNextLine()) {
      names.add(scan.nextLine());
    }
  }
  //this reads many files and many names in linear order may have slow run time of O(n)
  private void findUserAndScore() throws IOException {
    for(int i = 0; i < names.size(); i++) {
      File file = new File("./../Characters/" + names.get(i) +".txt");
      Scanner scan = new Scanner(file);
      String str = "";

      while(scan.hasNextLine()) {
        str += tokenizeLine(scan.nextLine()) + ",";
        scan.nextLine();
        str += tokenizeLine(scan.nextLine());
      }
      findScoresHolder.add(str);
    }
  }

  private void findHighScore() {
    for (int i = 0; i < findScoresHolder.size() - 1; i++) {
      for(int k = 0; k < findScoresHolder.size() - i-1; k++) {
        String[] number = findScoresHolder.get(k).split(",");
        String[] number2 = findScoresHolder.get(k+1).split(",");
        int n1 = Integer.parseInt(number[1]);
        int n2 = Integer.parseInt(number2[1]);

        if(n1 < n2) {
          String temp = findScoresHolder.get(k);
          findScoresHolder.set(k, findScoresHolder.get(k+1));
          findScoresHolder.set(k+1, temp);
        }
      }
    }
  }

  private String tokenizeLine(String line) {
    String[] returnResult = line.split("=");
    return returnResult[1].trim();
  }

  private void writeHighScores() throws IOException {
    File file = new File("./../Data/highScores.txt");
    FileWriter writer = new FileWriter(file);
    BufferedWriter output = new BufferedWriter(writer);

    try {
      for(int i = 0; i < findScoresHolder.size(); i++) {
        output.write(findScoresHolder.get(i));
        output.newLine();
      }
    } catch(Exception e) {
        System.out.println("Error writing highscores file");
      }
      output.close();
      writer.close();
  }
}
