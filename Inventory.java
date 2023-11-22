import java.util.ArrayList;
/*Inventory class that creates items and add/removes items as they are being collected or used*/
public class Inventory{
  public static ArrayList<Item> items = new ArrayList<Item>();
  

/*initialize items by priority, name, and amount in an item object*/
  public static void initializeItems(){
    items.add(new Item("Fuel", 0)); //0
    items.add(new Item("Ore", 30)); //1
    items.add(new Item("Coal", 0)); //2
    items.add(new Item("Wood", 80)); //3
    items.add(new Item("Copper", 0)); //4
    items.add(new Item("Tin", 50)); //5
    items.add(new Item("Iron", 10)); //6
    items.add(new Item("Steel", 0)); //7
    items.add(new Item("Gold", 0)); //8
    items.add(new Item("Silver", 0)); //9
    items.add(new Item("Tungsten", 0)); //10
    items.add(new Item("Iridium", 0)); //11
    items.add(new Item("Diamond", 0)); //12
    items.add(new Item("Iron Plating", 50)); //13
    items.add(new Item("Basic Wiring Kit", 0)); //14
    items.add(new Item("Basic Heat Regulator", 0)); //15
    items.add(new Item("Steel Reinforced Plating", 0)); //16 
    items.add(new Item("Advanced Wiring Kit", 0)); //17
    items.add(new Item("Advanced Heat Regulator", 0)); //18
    items.add(new Item("Heat Shielding", 0)); //19
    items.add(new Item("Navigation Computer", 0)); //20
    items.add(new Item("Primary Engine", 0)); //21
    items.add(new Item("Thruster", 0)); //22
    items.add(new Item("Rocket Fuel", 0)); //23
  }
 
  /*collecting item count and adds to the amount in inventory*/
  public static void addItem (int itemID, int amount){ 
    items.get(itemID).setAmount(items.get(itemID).getAmount()+amount);
  }
  
  /*losing or using items which diminished the amount in inventory*/
  public static void subtractItem (int itemID, int amount){ 
    items.get(itemID).setAmount(items.get(itemID).getAmount()-amount);
  }
  
  /*printing out an inventory summary*/
  public static void allItems(){
    System.out.println("Your Inventory:");
    System.out.format("%-10s %5s","Item","Count\n");
      
    for(int i=0; i < items.size();i++){
      if (items.get(i).getAmount() > 0){
        System.out.format("%-12s %-6s %8s",items.get(i).name,items.get(i).getAmount(),"\n");
      }
    }
  }

  /*a method that gets an amount for a specific resource*/
  public static int getAmountFromName(String s) {
    for (int i = 0; i < items.size(); i++){
      if (s.equals(items.get(i).name)){
        return items.get(i).getAmount();
      }
    }
    return -1;
  }

  /*a method that gets the ID of a resource*/
  public static int getID(String s) {
    for (int i = 0; i < items.size(); i++){
      if (s.equals(items.get(i).name)){
        return i;
      }
    }
    return -1;
  }

}