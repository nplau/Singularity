import java.util.Random;
/*Crew class that creates characters and adds characters*/
public class Crew{
  static Random rand = new Random();
  public static int amountOfPeople = rand.nextInt(10)+10;
  public static int sanity = rand.nextInt(50)+20;
  public static int intelligence = rand.nextInt(50)+20;
  public static int food = rand.nextInt(amountOfPeople*50)+(amountOfPeople*20);

  //Declaring arrays and 2 dimensional arrays
  public static String[] government = {"\033[0;31mDEMO\033[0;34mCRATIC", "\u001B[0;31mCOMMUN\033[0;93mIST", "\033[0;31mIMPER\033[0;30mIAL", "\033[0;33mUTILI\033[0;37mTARIAN", "\u001B[0;35mDICTATOR\033[0;31mSHIP", "\u001B[0;35mMONARCHY", "\033[0;32mCOLON\033[0;92mIALISM", "\033[0;30mANAR\033[0;37mCHY"};
  public static boolean[][] governmentID = {{true, true, true},{false, true, true},{true, true, false},{true, false, true},{true, false, false},{false, true, false},{false, false, true}, {false, false, false}};

  /*getGovernment is a method that determines the type of government based on sanity, intellect, and food levels*/
  public static String getGovernment (){
    boolean STY = false;
    boolean INT = false;
    boolean HNG = false;
    if (sanity >= 50){
      STY = true;
    }
    if (intelligence >= 50){
      INT = true;
    }
    if (food >= (amountOfPeople*12)){
      HNG = true;
    }
    for (int i = 0; i < government.length; i++){
      if (governmentID[i][0] == STY && governmentID[i][1] == INT && governmentID[i][2] == HNG){
        return government[i];
      }
    }
    return "BUGGED";
  }

  /*this method decreases the statistics for food, sanity, and intelligence. it shows an appropriate statement and if everyone in crew is dead, then the game ends and the user loses*/
  public static void handleCrew (){
    //if food runs out and everyone is hungry
    if (sanity<0){
      sanity = 0;
    }
    if (food == 0){
      if (rand.nextInt(20) == 1){
        System.out.println("\033[0;92mEveryone starved to death.\033[0m");
        Start.active = false;
      }
    }
    //if sanity is too low and people start killing themselves
    if (sanity == 0) {
      if (rand.nextInt(10) == 1){
        amountOfPeople--;
        System.out.println("\033[0;92mSomeone from your crew killed themselves.\033[0m");
        if (amountOfPeople==0){
          System.out.println("\033[0;92mEveryone from your crew killed themselves.\033[0m");
          Start.active = false;
        }
      }
    }
    //sanity, food, and intelligence decreasing each turn
    if (sanity>0){
      sanity-=1;
    }
    if (food>0){
      food-=amountOfPeople;
      if (food<0){
        food = 0;
      }
    }
    if (intelligence>0){
      intelligence-=1;
    }
    //random birth phenomenon
    if (rand.nextInt(50) == 1){
      amountOfPeople++;
      System.out.println("\033[0;92mA new baby was born!\033[0m");
    }
    //random death phenomenon
    if (rand.nextInt(100) == 1){
      amountOfPeople--;
      System.out.println("\033[0;31mA crewmate has died from natural causes.\033[0m");
    }
    // condition if the crew size if everyone in the crew died 
    if (amountOfPeople <= 0) {
      System.out.println("\033[0;92mEveryone died.\033[0m");
      Start.active = false;
    }
  }
}
