/*This is a class to create base objects fo the bases class*/
public class Base{

  public int id;
  public int[] amount;
  public int storedAmount = 0;
  public int baseType;
  public int x;
  public int y;

/*Constructor for Base class to organize variables*/
  public Base (int ID, int X, int Y) {
    this.id = ID;
    this.x = X;
    this.y = Y;
  }
/*This method increases the amount in stored amount*/
  public void increaseStored(int amount) {
    this.storedAmount+=amount;
  }
/*This setter method sets the value of x*/
  public void setX (int X){
    this.x = X;
  }
  /*This setter method sets the value of y*/
  public void setY (int Y){
    this.y = Y;
  }
}