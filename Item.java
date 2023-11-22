
/*public class to be used to initialize item objects*/ 
public class Item{
  
  //declaring variables
  public String name;
  public int amount;

  /*Constructor for Item class to organize variables*/
  public Item (String Name, int Amount) {
      this.name = Name;
      this.amount = Amount;
  }
  /*Setter method to set an integer for the amount of that resource*/
  public void setAmount (int n) {
    amount = n;
  }
  /*returns amount value to be printed*/
  public int getAmount () {
    return amount;
  }
}