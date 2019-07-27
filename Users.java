public class Users {
  private String name;
  private int score;

  public Users(String name, String score) {
    this.name = name;
    this.score = Integer.parseInt(score);
  }
  public Users(Users user) {
    this.name = user.name;
    this.score = user.score;
  }

  public int getScore() {
      return this.score;
  }
  public String getName() {
    return this.name;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String toString() {
    String str = "";
    str += "Name : " + this.name + "\n";
    str += "Score : " + this.score + "\n";
    return str;
  }
}
