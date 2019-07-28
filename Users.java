public class Users {
  private String name;
  //password is not encrypted yet
  private String password;
  private int score;

  public Users(String name, String password, String score) {
    this.name = name;
    this.password = password;
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

  public String getPassword() {
    return this.password;
  }

  public void setScore(int score) {
    this.score = score;
  }
  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String toString() {
    String str = "";
    str += "Name : " + this.name + "\n";
    str += "Score : " + this.score + "\n";
    return str;
  }
}
