/*This class creates bases with resources depending on which base is called on for the specific game*/
public class Bases {
  //declaring arrays and 2 dimensional arrays
  public static String[] tiles = {"?", "\u001B[30m-", "\u001B[32mϡ", "\u001B[0;33m-", "\033[0;90m-", "\033[0;91m_", "\033[0;91m_", "\033[0;91m_", "\033[0;91m_", "\033[0;91m_", "\033[0;91m_", "\033[0;91m^", "\033[0;34m~"};
  static int[][] requiredAmount = {{},{10},{20, 10},{30, 30, 5},{50, 5},{100, 30, 50}, {40, 100, 50}, {50, 100, 80}, {200, 100, 200, 100}, {100, 100, 20, 15}, {10, 5, 150, 200}, {50, 100, 10, 20}, {100}};
  static String[][] requiredItem = {{},{"Tungsten"},{"Tin", "Iron Plating"},{"Ore", "Wood", "Iron"},{"Wood", "Iron Plating"},{"Ore", "Wood", "Coal"}, {"Iron Plating", "Basic Heat Regulator", "Basic Wiring Kit"}, {"Steel Reinforced Plating", "Advanced Heat Regulator", "Advanced Wiring Kit"}, {"Iron", "Copper", "Wood", "Coal"}, {"Basic Wiring Kit", "Basic Heat Regulator", "Steel", "Silver"}, {"Diamond", "Iridium", "Advanced Wiring Kit", "Advanced Heat Regulator"}, {"Steel", "Wood", "Tungsten", "Iridium"}, {"Wood"}};
  /*Adds resources to inventory for the corresponding base*/
  public static void doFunction(int baseID) {
    switch(baseID){
      //Fuel pump
      case 1:
        Inventory.addItem(0,5);
        break;
      //Lumber Yard
      case 2:
        Inventory.addItem(3,5);
        break;
      //Coal Mine
      case 3:
        Inventory.addItem(2, 5);
        break;
      //Ore Mine
      case 4: 
        Inventory.addItem(1, 5);
        break;
      //Refiner
      case 5:
        if (ExtraMenus.activeBases[1] == false){
          ExtraMenus.activeBases[1] = true;
        }
        break;
      //Refiner II
      case 6: 
        if (ExtraMenus.activeBases[2] == false){
          ExtraMenus.activeBases[2] = true;
        }
        break;
      //Refiner III
      case 7:
        if (ExtraMenus.activeBases[3] == false){
          ExtraMenus.activeBases[3] = true;
        }
        break;
      //Fabricator
      case 8:
        if (ExtraMenus.activeBases[4] == false) {
          ExtraMenus.activeBases[4] = true;
        }
        break;
      //Fabricator II
      case 9:
        if (ExtraMenus.activeBases[5] == false){
          ExtraMenus.activeBases[5] = true;
        }
        break;
      //Fabricator III
      case 10: 
        if (ExtraMenus.activeBases[6] == false){
          ExtraMenus.activeBases[6] = true;
        }
        break;
      //Rocket
      case 11: 
        // The Rocket has a name! It's called "The Accretion" according to the lore of this game.
        if (ExtraMenus.activeBases[0] == false) {
          ExtraMenus.activeBases[0] = true;
        }
        break;
      case 12:
        Crew.food+=20;
    }
  }
  /*this method adds resources to inventory if the space you land on has resources*/
  public static boolean hasResources(int selection) {
    //checking if the space has resources
    for (int i = 0; i<requiredAmount[selection].length; i++){
      if ((requiredAmount[selection][i]) > Inventory.getAmountFromName(requiredItem[selection][i])){
        return false;
      }
    }
    return true;
  }
  /*this method takes away resources from the base*/
  public static void subtractResources(int selection) {
    for (int i = 0; i<requiredAmount[selection].length; i++){
      Inventory.subtractItem(Inventory.getID(requiredItem[selection][i]), requiredAmount[selection][i]);
    }
  }
}