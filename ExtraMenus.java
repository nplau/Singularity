import java.util.Scanner;
import java.lang.Math;
import java.util.Arrays;
import java.io.*;
/*This class creates menus and allows for you to choose a certain menu when you are playing the game*/
class ExtraMenus {
  //variables
  public static boolean[] rocketCompletion = {false, false, false, false, false, false, false};
  public static boolean[] activeBases = {false, false, false, false, false, false, false};
  static String[] baseTypes = {"ROCKET", "REFINER I", "REFINER II", "REFINER III", "FABRICATOR I", "FABRICATOR II", "FABRICATOR III"};

  /*Method that displays menus*/
  public static void printMenu () {
    //variables
    Scanner in = new Scanner(System.in);
    int select = 0;
    //Checks if the user can open the specific menus or not
    for (int i = 0; i < baseTypes.length; i++){
      if (activeBases[i] == true){
        System.out.println("["+(i+1)+"] \033[0;92m" + baseTypes[i] + " [ONLINE]");
      }
      else {
        System.out.println("["+(i+1)+"] \033[0;31m"+baseTypes[i] + " [OFFLINE]");
      }
      System.out.print("\033[0m");
    }
    //Opens menus
    do {
      System.out.print("Select a menu to open: ");
      String option = in.nextLine();
      //convert option to int string
      try {
        //Subtract 1 from select so it can be properly used for array reference
        select = Integer.parseInt(option);
        select--;
        //message if an invalid number is entered
        if (select < 0 || select >= 7) {
          System.out.println("Please enter a valid number from 1 to 7");
        }
      }
      catch (NumberFormatException e) {
        System.out.println("Please enter a valid number.");
        select = -1;
      }
    }
    while (select < 0 || select >= 7);
    if (activeBases[select] == true) {
      menuSelect(select);
    }
    else {
      System.out.println("This menu can not yet be accessed. Please build "+baseTypes[select]+" to access this menu");
    }
  }

  /*This method takes your menu selection and access that menu.*/
  private static void menuSelect (int select) {
    //variables
    Scanner in = new Scanner(System.in);
    String [] rocketParts = {"Heat Shielding", "Navigation Computer", "Primary Engine", "Thruster 1", "Thruster 2", "Rocket Fuel", "LAUNCH"};
    int [] refinedOre = {0,0,0};
    int option = 0;
    String input = "";
    int oreGenerated = 0;

    switch (select) {

      //Rocket
      case 0: 
        if (rocketCompletion[0] == true && rocketCompletion[1] == true && rocketCompletion[2] == true && rocketCompletion[3] == true && rocketCompletion[4] == true && rocketCompletion[5] == true){
          rocketCompletion[6] = true;
        }
        System.out.println("[Rocket]\n{Module Overview}");
        for (int i = 0; i < rocketParts.length; i++) {
          if (rocketCompletion[i] == true) {
            System.out.println("["+(i+1)+"] \033[0;92m"+rocketParts[i]+": STATUS ONLINE");
          }
          else {
            System.out.println("["+(i+1)+"] \033[0;31m"+rocketParts[i]+": STATUS OFFLINE");
          }
          System.out.print("\033[0m");
        }

        //Checks User input
        do {
          System.out.print("Select a menu to open: ");
          input = in.nextLine();
          //convert option to int string
          try {
            //Subtract 1 from select so it can be properly used for array reference
            option = Integer.parseInt(input);
            option--;
            //message if an invalid number is entered
            if (option < 0 || option > 7) {
              System.out.println("Please enter a valid number from 1 to 7");
            }
          }
          catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            option = -1;
          }
        }
        while (option < 0 || option > 7);

        //If the user enters Launch, plays takeoff animation and ends the game
        if (option == 6 && rocketCompletion[0] == true && rocketCompletion[1] == true && rocketCompletion[2] == true && rocketCompletion[3] == true && rocketCompletion[4] == true && rocketCompletion[5] == true) {
           ending();
           Start.active = false;
           break;
        }

        //Used since thruster 1 and 2 use the same item to repair
        int thrust2 = option;
        if (option == 4) {
          option = 3;
        }
        else if (option == 5) {
          option = 4;
        }

        //If the user can repair the module it will be repaired otherwise an appropriate message will be displayed
        if (Inventory.items.get(option+19).getAmount() > 0 && rocketCompletion[thrust2] == false) {
          Inventory.subtractItem(option+19, 1);
          rocketCompletion[thrust2] = true;
          System.out.println("Module Repaired");
        }
        else {
          System.out.println("You do not have the required item to repair this module or you have already repaired this module.");
        }
        break;
      //refiner 1
      case 1: 
        //Breaks out of the menu if the user is unable to refine any ore
        if (Inventory.items.get(1).getAmount() == 0) {
          System.out.println("You do not have enough ore to refine. You must have at least 1 ore.");
          break;
        }
        System.out.println("[Refinery I]\n1 Ore ---> 1 Random Metal");
        System.out.println("{Ore Chances}\nCopper: 33.3%\nTin: 33.3%\nIron: 33.3%");
        System.out.print("How much ore do you want to refine: ");
        //Check input
        do {
          input = in.nextLine();
          try {
            option = Integer.parseInt(input);
          }
          catch (NumberFormatException e ) {
            System.out.println("Please enter a valid number.");
            option = -1;
          }
        }
        while (option > Inventory.items.get(1).getAmount());
        //Performs refinement
        Inventory.subtractItem(1, option);
        for (int i = 0; i < option; i++) {
          oreGenerated = (int)(Math.random()*(3)+1);
          refinedOre[(oreGenerated-1)]++;
        }
        System.out.println("Ore Refined\nCopper: "+refinedOre[0]+"\nTin: "+refinedOre[1]+"\nIron: "+refinedOre[2]);
        //Add items to inventory and reset refinedOre array for next use
        Inventory.addItem(4, refinedOre[0]);
        Inventory.addItem(5, refinedOre[1]);
        Inventory.addItem(6, refinedOre[2]);
        Arrays.fill(refinedOre, 0);
        break;
      //refiner 2
      case 2: 
        //Breaks out of the menu if the user is unable to refine any ore
        if (Inventory.items.get(1).getAmount() < 10) {
          System.out.println("You do not have enough ore to refine. You must have at least 10 ore.");
          break;
        }
        System.out.println("[Refinery II]\n10 Ore ---> 1 Random Uncommon Metal");
        System.out.println("{Ore Chances}\nSteel: 33.3%\nGold: 33.3%\nSilver: 33.3%");
        System.out.print("How much ore do you want to refine: ");
        //Check input
        do {
          input = in.nextLine();
          try {
            option = Integer.parseInt(input);
          }
          catch (NumberFormatException e ) {
            System.out.println("Please enter a valid number.");
            option = -1;
          }
        }
        while (option*10 > Inventory.items.get(1).getAmount());
        //Performs refinement
        Inventory.subtractItem(1, option*10);
        for (int i = 0; i < option; i++) {
          oreGenerated = (int)(Math.random()*(3)+1);
          refinedOre[(oreGenerated-1)]++;
          }
        System.out.println("Ore Refined\nSteel: "+refinedOre[0]+"\nGold: "+refinedOre[1]+"\nSilver: "+refinedOre[2]);
        //Add items to inventory and reset refinedOre array for next use
        Inventory.addItem(7, refinedOre[0]);
        Inventory.addItem(8, refinedOre[1]);
        Inventory.addItem(9, refinedOre[2]);
        Arrays.fill(refinedOre, 0);
        break;
      //refiner 3
      case 3: 
        //Breaks out of the menu if the user is unable to refine any ore
        if (Inventory.items.get(1).getAmount() < 100) {
          System.out.println("You do not have enough ore to refine. You must have at least 100 ore.");
          break;
        }
        System.out.println("[Refinery III]\n100 Ore ---> 1 Random Rare Metal");
        System.out.println("{Ore Chances}\nTungsten: 33.3%\nIridium: 33.3%\nDiamond: 33.3%");
        System.out.print("How much ore do you want to refine: ");
        //Check input
        do {
          input = in.nextLine();
          try {
            option = Integer.parseInt(input);
          }
          catch (NumberFormatException e ) {
            System.out.println("Please enter a valid number.");
            option = -1;
          }
        }
        while (option*100 > Inventory.items.get(1).getAmount());
        //Performs refinement
        Inventory.subtractItem(1, option*100);
        for (int i = 0; i < option; i++) {
          oreGenerated = (int)(Math.random()*(3)+1);
          refinedOre[(oreGenerated-1)]++;
          }
        System.out.println("Ore Refined\nTungsten: "+refinedOre[0]+"\nIridium: "+refinedOre[1]+"\nDiamond: "+refinedOre[2]);
        //Add items to inventory and reset refinedOre array for next use
        Inventory.addItem(10, refinedOre[0]);
        Inventory.addItem(11, refinedOre[1]);
        Inventory.addItem(12, refinedOre[2]);
        Arrays.fill(refinedOre, 0);
        break;

      //fabricator 1
      case 4: System.out.println("[Fabricator I]\n{Recipies}\n[1] 10 Iron ---> Iron   Plating\n[2] 5 Copper ---> Basic Wiring Kit\n[3] 10 Tin ---> Basic Heat Regulator\n[4] Back");
        //Check input
        do {
          input = in.nextLine();
          try {
            option = Integer.parseInt(input);
            if (option > 4 || option < 1) {
              System.out.println("Please enter a valid option.");
            }
          }
          catch (NumberFormatException e ) {
            System.out.println("Please enter a valid number.");
            option = -1;
          }
        }
        while (option > 4 || option < 1);
        //Crafting different Items
        switch (option) {
          //Iron Plating
          case 1: 
            //breaks if user does not have enough materials to craft
            if (Inventory.items.get(6).getAmount() < 10) {
              System.out.println("You do not have enough materials to craft this item.");
              break;
            }
            System.out.print("How many do you want to craft: ");
            //Check input
            do {
              input = in.nextLine();
              try {
                option = Integer.parseInt(input);
                if (option*10 > Inventory.items.get(6).getAmount() || option*10 < 0) {
                  System.out.println("You cannot craft this many.");
                }
              }
              catch (NumberFormatException e ) {
                System.out.println("Please enter a valid number.");
                option = -1;
              }
            }
            while (option*10 > Inventory.items.get(6).getAmount() || option*10 < 0);
            //Craft the item and add them to inventory
            System.out.println("You crafted "+option+" Iron Plating");
            Inventory.subtractItem(6, option*10);
            Inventory.addItem(13, option);
            break;
          //Basic Wiring Kit
          case 2:
            //breaks if user does not have enough materials to craft
            if (Inventory.items.get(4).getAmount() < 5) {
              System.out.println("You do not have enough materials to craft this item.");
              break;
            }
            System.out.print("How many do you want to craft: ");
            //Check input
            do {
              input = in.nextLine();
              try {
                option = Integer.parseInt(input);
                if (option*5 > Inventory.items.get(4).getAmount() || option*5 < 0) {
                  System.out.println("You cannot craft this many.");
                }
              }
              catch (NumberFormatException e ) {
                System.out.println("Please enter a valid number.");
                option = -1;
              }
            }
            while (option*5 > Inventory.items.get(4).getAmount() || option*5 < 0);
            //Craft the item and add them to inventory
            System.out.println("You crafted "+option+" Basic Wiring Kit");
            Inventory.subtractItem(4, option*5);
            Inventory.addItem(14, option);
            break;
          //Basic Heat Regulator
          case 3:
            //breaks if user does not have enough materials to craft
            if (Inventory.items.get(5).getAmount() < 10) {
              System.out.println("You do not have enough materials to craft this item.");
              break;
            }
            //Check input
            do {
              input = in.nextLine();
              try {
                option = Integer.parseInt(input);
                if (option*10 > Inventory.items.get(5).getAmount() || option*10 < 0) {
                  System.out.println("You cannot craft this many.");
                }
              }
              catch (NumberFormatException e ) {
                System.out.println("Please enter a valid number.");
                option = -1;
              }
            }
            while (option*10 > Inventory.items.get(5).getAmount() || option*10 < 0);
            //Craft the item and add them to inventory
            System.out.println("You crafted "+option+" Basic Heat Regulator");
            Inventory.subtractItem(5, option*10);
            Inventory.addItem(15, option);
            break;
          //Exits the menu
          default: 
            break;
        }
        break;
      
      //fabricator 2
      case 5: System.out.println("[Fabricator II]\n{Recipies}\n[1] 10 Steel + 5 Iron Plating ---> Steel Reinforced Plating\n[2] 10 Gold + 10 Basic Wiring Kit ---> Advanced Wiring Kit\n[3] 10 Silver + 5 Basic Heat Regulator ---> Advanced Heat Regulator\n[4] Back");
        //Check input
        do {
          input = in.nextLine();
          try {
            option = Integer.parseInt(input);
            if (option > 4 || option < 1) {
              System.out.println("Please enter a valid option.");
            }
          }
          catch (NumberFormatException e ) {
            System.out.println("Please enter a valid number.");
            option = -1;
          }
        }
        while (option > 4 || option < 1);
        //Crafting different Items
        switch (option) {
          //Steel Reinforced Plating
          case 1:
            //breaks if user does not have enough materials to craft
            if (Inventory.items.get(7).getAmount() < 10 && Inventory.items.get(13).getAmount() < 5) {
              System.out.println("You do not have enough materials to craft this item.");
              break;
            }
            //Check input
            do {
              input = in.nextLine();
              try {
                option = Integer.parseInt(input);
                if (option*10 > Inventory.items.get(7).getAmount() || option*10 < 0 || option*5 > Inventory.items.get(13).getAmount()) {
                  System.out.println("You cannot craft this many.");
                }
              }
              catch (NumberFormatException e ) {
                System.out.println("Please enter a valid number.");
                option = -1;
              }
            }
            while (option*10 > Inventory.items.get(7).getAmount() || option*10 < 0 || option*5 > Inventory.items.get(13).getAmount());
            //Craft the item and add them to inventory
            System.out.println("You crafted "+option+" Steel Reinforced Plating");
            Inventory.subtractItem(7, option*10);
            Inventory.subtractItem(13, option*5);
            Inventory.addItem(16, option);
            break;
          //Advanced Wiring Kit
          case 2:
            //breaks if user does not have enough materials to craft
            if (Inventory.items.get(8).getAmount() < 10 && Inventory.items.get(14).getAmount() < 10) {
              System.out.println("You do not have enough materials to craft this item.");
              break;
            }
            //Check input
            do {
              input = in.nextLine();
              try {
                option = Integer.parseInt(input);
                if (option*10 > Inventory.items.get(8).getAmount() || option*10 < 0 || option*10 > Inventory.items.get(14).getAmount()) {
                  System.out.println("You cannot craft this many.");
                }
              }
              catch (NumberFormatException e ) {
                System.out.println("Please enter a valid number.");
                option = -1;
              }
            }
            while (option*10 > Inventory.items.get(8).getAmount() || option*10 < 0 || option*10 > Inventory.items.get(14).getAmount());
            //Craft the item and add them to inventory
            System.out.println("You crafted "+option+" Advanced Wiring Kit");
            Inventory.subtractItem(8, option*10);
            Inventory.subtractItem(14, option*10);
            Inventory.addItem(17, option);
            break;
          //Advanced Heat Regulator
          case 3:
            //breaks if user does not have enough materials to craft
              if (Inventory.items.get(9).getAmount() < 10 && Inventory.items.get(15).getAmount() < 5) {
                System.out.println("You do not have enough materials to craft this item.");
                break;
              }
            //Check input
            do {
              input = in.nextLine();
              try {
                option = Integer.parseInt(input);
                if (option*10 > Inventory.items.get(9).getAmount() || option*10 < 0 || option*5 > Inventory.items.get(15).getAmount()) {
                  System.out.println("You cannot craft this many.");
                }
              }
              catch (NumberFormatException e ) {
                System.out.println("Please enter a valid number.");
                option = -1;
              }
          }
            while (option*10 > Inventory.items.get(9).getAmount() || option*10 < 0 || option*5 > Inventory.items.get(15).getAmount());
            //Craft the item and add them to inventory
            System.out.println("You crafted "+option+" Steel Reinforced Plating");
            Inventory.subtractItem(9, option*10);
            Inventory.subtractItem(15, option*5);
            Inventory.addItem(18, option);
            break;
          default: break;
        }
        break;
      //fabricator 3
      case 6: System.out.println("[Fabricator III]\n{Recipies}\n[1] 200 Steel Reinforced Plating + 100 Tungsten + 500 Steel ---> Heat Shielding\n[2] 150 Advanced Wiring Kit + 200 Iridium + 100 Diamond ---> Navigation Computer\n[3] 100 Steel Reinforced Plating + 300 Tungsten + 200 Advanced Heat Regulator ---> Primary Engine [4] 100 Iridium + 200 Diamond + 100 Advanced Heat Regulator ---> Thruster\n[5] 1000 Fuel + 10000 Wood + 10000 Coal ---> Rocket Fuel\n[6]Back");
        //Check input
        do {
          input = in.nextLine();
          try {
            option = Integer.parseInt(input);
            if (option > 4 || option < 1) {
              System.out.println("Please enter a valid option.");
            }
          }
          catch (NumberFormatException e ) {
            System.out.println("Please enter a valid number.");
            option = -1;
          }
        }
        while (option > 4 || option < 1);
        //Crafting Different Items
        switch (option) {
          case 1:
            //breaks if user does not have enough materials to craft
            if (Inventory.items.get(16).getAmount() < 200 && Inventory.items.get(10).getAmount() < 100 && Inventory.items.get(7).getAmount() < 500) {
              System.out.println("You do not have enough materials to craft this item.");
              break;
            }
            //Check input
            do {
              input = in.nextLine();
              try {
                option = Integer.parseInt(input);
                if (option*200 > Inventory.items.get(16).getAmount() || option*10 < 0 || option*100 > Inventory.items.get(10).getAmount() || option*500 > Inventory.items.get(7).getAmount()) {
                  System.out.println("You cannot craft this many.");
                }
              }
              catch (NumberFormatException e ) {
                System.out.println("Please enter a valid number.");
                option = -1;
              }
            }
            while (option*200 > Inventory.items.get(16).getAmount() || option*10 < 0 || option*100 > Inventory.items.get(10).getAmount() || option*500 > Inventory.items.get(7).getAmount());
            //Craft the item and add them to inventory
            System.out.println("You crafted "+option+" Heat Shielding");
            Inventory.subtractItem(16, option*200);
            Inventory.subtractItem(10, option*100);
            Inventory.subtractItem(7, option*500);
            Inventory.addItem(19, option);
            break;
          case 2:
            //breaks if user does not have enough materials to craft
            if (Inventory.items.get(17).getAmount() < 150 && Inventory.items.get(11).getAmount() < 200 && Inventory.items.get(12).getAmount() < 100) {
              System.out.println("You do not have enough materials to craft this item.");
              break;
            }
            //Check input to see if user can craft the amount 
            do {
              input = in.nextLine();
              try {
                option = Integer.parseInt(input);
                if (option*150 > Inventory.items.get(17).getAmount() || option*10 < 0 || option*200 > Inventory.items.get(11).getAmount() || option*100 > Inventory.items.get(12).getAmount()) {
                  System.out.println("You cannot craft this many.");
                }
              }
              //catches any invalid inputs
              catch (NumberFormatException e ) {
                System.out.println("Please enter a valid number.");
                option = -1;
              }
            }
            while (option*150 > Inventory.items.get(17).getAmount() || option*10 < 0 || option*200 > Inventory.items.get(11).getAmount() || option*100 > Inventory.items.get(12).getAmount());
            //Craft the item and add them to inventory
            System.out.println("You crafted "+option+" Heat Shielding");
            Inventory.subtractItem(17, option*150);
            Inventory.subtractItem(11, option*200);
            Inventory.subtractItem(12, option*100);
            Inventory.addItem(20, option);
            break;
          case 3: 
            //breaks if user does not have enough materials to craft
            if (Inventory.items.get(16).getAmount() < 100 && Inventory.items.get(10).getAmount() < 300 && Inventory.items.get(18).getAmount() < 200) {
              System.out.println("You do not have enough materials to craft this item.");
              break;
            }
            //Check input to see if user can craft the amount 
            do {
              input = in.nextLine();
              try {
                option = Integer.parseInt(input);
                if (option*100 > Inventory.items.get(16).getAmount() || option*10 < 0 || option*300 > Inventory.items.get(10).getAmount() || option*200 > Inventory.items.get(18).getAmount()) {
                  System.out.println("You cannot craft this many.");
                }
              }
              //catches any invalid inputs
              catch (NumberFormatException e ) {
                System.out.println("Please enter a valid number.");
                option = -1;
              }
            }
            while (option*100 > Inventory.items.get(16).getAmount() || option*10 < 0 || option*300 > Inventory.items.get(10).getAmount() || option*200 > Inventory.items.get(18).getAmount());
            //Craft the item and add them to inventory
            System.out.println("You crafted "+option+" Heat Shielding");
            Inventory.subtractItem(16, option*100);
            Inventory.subtractItem(10, option*300);
            Inventory.subtractItem(18, option*200);
            Inventory.addItem(21, option);
            break;
          case 4: 
            //breaks if user does not have enough materials to craft
            if (Inventory.items.get(11).getAmount() < 100 && Inventory.items.get(12).getAmount() < 200 && Inventory.items.get(18).getAmount() < 100) {
              System.out.println("You do not have enough materials to craft this item.");
              break;
            }
            //Check input to see if user can craft the amount 
            do {
              input = in.nextLine();
              try {
                option = Integer.parseInt(input);
                if (option*100 > Inventory.items.get(11).getAmount() || option*10 < 0 || option*200 > Inventory.items.get(12).getAmount() || option*100 > Inventory.items.get(18).getAmount()) {
                  System.out.println("You cannot craft this many.");
                }
              }
              //catches any invalid inputs
              catch (NumberFormatException e ) {
                System.out.println("Please enter a valid number.");
                option = -1;
              }
            }
            while (option*100 > Inventory.items.get(11).getAmount() || option*10 < 0 || option*200 > Inventory.items.get(12).getAmount() || option*100 > Inventory.items.get(18).getAmount());
            //Craft the item and add them to inventory
            System.out.println("You crafted "+option+" Heat Shielding");
            Inventory.subtractItem(11, option*100);
            Inventory.subtractItem(12, option*200);
            Inventory.subtractItem(18, option*100);
            Inventory.addItem(22, option);
            break;
          case 5: 
            //breaks if user does not have enough materials to craft
            if (Inventory.items.get(1).getAmount() < 1000 && Inventory.items.get(2).getAmount() < 10000 && Inventory.items.get(3).getAmount() < 10000) {
              System.out.println("You do not have enough materials to craft this item.");
              break;
            }
            //Check input
            do {
              input = in.nextLine();
              try {
                option = Integer.parseInt(input);
                if (option*1000 > Inventory.items.get(1).getAmount() || option*10 < 0 || option*10000 > Inventory.items.get(2).getAmount() || option*10000 > Inventory.items.get(3).getAmount()) {
                  System.out.println("You cannot craft this many.");
                }
              }
              catch (NumberFormatException e ) {
                System.out.println("Please enter a valid number.");
                option = -1;
              }
            }
            while (option*1000 > Inventory.items.get(1).getAmount() || option*10 < 0 || option*10000 > Inventory.items.get(2).getAmount() || option*10000 > Inventory.items.get(3).getAmount());
            //Craft the item and add them to inventory
            System.out.println("You crafted "+option+" Rocket Fuel");
            Inventory.subtractItem(1, option*1000);
            Inventory.subtractItem(2, option*10000);
            Inventory.subtractItem(3, option*10000);
            Inventory.addItem(23, option);
            break;
          default: break;
        }
        break;
      default:
        System.out.println("Not an option...");
        break;
    }
  }

  /*Prints ending animation of launching the rocket from AsciiEndingAnimation file*/
  public static void ending () {
    //reading file
    try{
      BufferedReader in = new BufferedReader(new FileReader("AsciiEndingAnimation.txt"));
      for (int i = 0; i < 15; i++){
        Thread.sleep(1000);
        for (int j = 0; j < 9; j++){
          System.out.println(in.readLine());
        }
      }
      in.close();
    }
    //catching any errors in file
    catch (Exception e) {
      System.out.println("Sorry, could not find the animation file.");
    }
  }
}