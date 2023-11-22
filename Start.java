import java.util.*; 
import java.io.*;

/*This class initializes the whole game and displays the instructions, and will set up the terrian and inventory.*/
public class Start{

  public static boolean active = true;
  static Random rand = new Random();

  /*Constructor for Start class to call on various methods and organize game procedure*/
  public Start(){
    //calling on instructions method 
    instructions();
    //creating new objects
    Scanner scanner = new Scanner(System.in);
    ResourceGen gen = new ResourceGen();
		GameInfo game = new GameInfo();
    //controls and commands
    String[] cmdCtrl = {"GUIDE", "ADD", "LEFT", "RIGHT", "UP", "DOWN", "RETURN", "GOTO", "NEXT", "INV", "LIST", "BASES", "TALK"};
    ArrayList<String> bases;
		while (active == true) {
      gen.renderNewCoords();
      game.updateTerrain();
			game.loadMap();
			game.drawMap();
      System.out.println("CREW: " + Crew.amountOfPeople + ", SANITY: " + Crew.sanity + ", FOOD: " + Crew.food + ", INTELLIGENCE: " + Crew.intelligence);
      System.out.println("X: " + game.CoordX + ", Y: " + (-game.CoordY));
      System.out.println("GOVERNMENT TYPE: " + Crew.getGovernment() + "\033[0m");
			System.out.println("COMMAND CONTROL: 'LIST' TO LIST COMMANDS");
			switch (CheckIn.isValid(cmdCtrl)) {
        //displays a guide/instructions page
				case 0:
          System.out.println("Ѫ, Main Base, cannot be built, houses the people.\n\nϮ, Fuel Mine, 1 to build, extracts Fuel from \u001B[30m-\033[0m. End Game machine required to launch the Rocket. Costs 10 Tungsten.\n\n\033[0;90mӿ\033[0m, Saw, 2 to build, cuts down local fluora (\u001B[32mϡ\033[0m) to harvest wood. Costs 20 Tin and 10 Iron Plating.\n\nΨ, Coal Mine, 3 to build, extracts coal from \u001B[0;33m-\033[0m. Needed to build machines. Costs 30 Ore, 30 Wood and 5 Iron.\n\nɎ, Ore mine, 4 to build, extracts crude ore from \033[0;90m-\033[0m. Can be refined for rare minerals. Costs 50 Wood and 5 Iron Plating.\n\nǆ, Refiner I, 5 to build, needs to be placed on flat land (\033[0;91m_\033[0m). Refines ore for common resources. Costs 100 Ore, 30 Wood and 50 Coal.\n\nǅ, Refiner II, 6 to build, needs to be placed on flat land (\033[0;91m_\033[0m). Refines ore into rare reasources. Costs 40 Iron Plating, 100 Basic Heat Regulators and 50 Basic Wiring Kits.\n\nǄ, Refiner III, 7 to build, needs to be placed on flat land (\033[0;91m_\033[0m). Refines ore into specialist reasources. Costs 50 Steel Reinforced Plating, 100 Advanced Heat Regulators and 80 Advanced Wiring Kits.\n\nţ, Fabricator I, 8 to build, needs to be placed on flat land (\033[0;91m_\033[0m). Used to craft basic components. Costs 200 Iron, 100 Copper, 200 Wood and 100 Coal.\n\nŦ, Fabricator II, 9 to build, needs to be placed on flat land (\033[0;91m_\033[0m). Used to craft advanced components. Costs 100 Basic Wiring Kits, 100 Basic Heat Regulators, 20 Steel and 15 Silver.\n\nŤ, Fabricator III, 10 to build, needs to be placed on flat land (\033[0;91m_\033[0m). Used to craft rocket components. Costs 10 Diamonds, 5 Iridium, 150 Advanced Wiring Kit and 200 Advanced Heat Regulators.\n\nѦ, Rocket, 11 to build, needs to be placed on a mountain (\033[0;91m^\033[0m). Once built, the rocket can be repaired in order to escape the planet. Costs 50 Steel, 100 Wood, 10 Tungsten and 20 Iridium.\n\nJ, Fishing Outpost, 12 to build, fishes up local fauna from a body of water (\033[0;34m~\033[0m). Once built, the fishing outpost provides 10 food per turn. Costs 100 Wood.\n");
					break;
        //Allows the user to build a building
				case 1:
          int selection = CheckIn.validInt("BASE", 1, Bases.tiles.length);
          int baseX = CheckIn.validInt("X", 1, game.x)-1;
          int baseY = CheckIn.validInt("Y", 1, game.y)-1;
          if (game.loadMap[baseY][baseX].equals(Bases.tiles[selection]) && Bases.hasResources(selection) == true){
            game.bases.add(new Base(selection, baseX+1, baseY+1));
            Bases.subtractResources(selection);
            Bases.doFunction(selection);
            Crew.intelligence += 10;
          }
          else if (!game.loadMap[baseY][baseX].equals(Bases.tiles[selection])) {
            System.out.println("INCORRECT PLACEMENT, MUST BE ON A " + Bases.tiles[selection] + "\u001B[0m, THAT WAS " + game.loadMap[baseY][baseX] + "\u001B[0m.");
          }
          else if (Bases.hasResources(selection) == false) {
            System.out.println("NOT ENOUGH ITEMS!");
          }
          else {
            System.out.println("CANNOT PLACE BASE.");
          }
					break;
        //Move Left
        case 2:
          game.CoordX += -1;
          game.baseShift(1, 0);
          break;
        //Move Right
        case 3:
          game.CoordX += 1;
          game.baseShift(-1, 0);
          break;
        //Move Up
        case 4:
          game.CoordY += -1;
          game.baseShift(0, 1);
          break;
        //Move Down
        case 5:
          game.CoordY += 1;
          game.baseShift(0, -1);
          break;
        //Returns the user to 0,0
        case 6:
          game.baseShift(game.CoordX, game.CoordY);
          game.CoordY = 0;
          game.CoordX = 0;
          break;
        //Warps the user to the specified location
        case 7:
          try {
            game.baseShift(game.CoordX, game.CoordY);
            System.out.println("ENTER X:");
            game.CoordX = Integer.parseInt(scanner.next());
            System.out.println("ENTER Y:");
            game.CoordY = -Integer.parseInt(scanner.next());
            game.baseShift(-game.CoordX, -game.CoordY);
          }
          catch (Exception e) {
            System.out.println("INVALID NUMBER.");
          }
          break;
        //Accelerates time by 1 iteration
        case 8:
          for (int i = 0; i < game.bases.size(); i++){
            Bases.doFunction(game.bases.get(i).id);
          }
          Crew.handleCrew();
          break;
        //Displays Inventory
        case 9:
          Inventory.allItems();
          break;
        //Displays all of the options that the user can input
        case 10:
          CheckIn.printArray(cmdCtrl);
          System.out.println();
          break;
        //Opens the other menus
        case 11:
          ExtraMenus.printMenu();
          break;
        //reads file and implements dialogue
        case 12:
        //try catch to catch any errors with reading the file
          try{
            BufferedReader readerForLines = new BufferedReader(new FileReader("Dialogue.txt"));
            int lines = 0;
            while (readerForLines.readLine() != null) lines++;
            lines=lines/5;
            readerForLines.close();
            BufferedReader in = new BufferedReader(new FileReader("Dialogue.txt"));
            int dialogueOption = rand.nextInt(lines); 
            for (int i = 0; i < dialogueOption*5; i++){
              in.readLine();
            }
            String question = in.readLine();
            String[] answers = {in.readLine(), in.readLine(), in.readLine()};
            int correctAnswer = Integer.parseInt(in.readLine());
            System.out.println("CREWMATE: " + question);
            for (int i = 0; i<3; i++){
              System.out.println((i+1) + ". " + answers[i]);
            }
            if (CheckIn.validInt("ANSWER", 1, 3) == correctAnswer){
              System.out.println("CREWMATE IS HAPPY.");
              Crew.sanity+=10;
            }
            else {
              System.out.println("CREWMATE IS SAD.");
              Crew.sanity-=5;
            }
            in.close();
          }
          catch (Exception e) {
            e.printStackTrace();
            System.out.println("Sorry, could not load dialogue files.");
            Crew.sanity+=10;
          }
          
          break;
			}
		}
  }
  
  /* This method prints out the instructions at the beginning of the game to guide the users*/
  public void instructions(){
    System.out.println("---Singularity---");
    System.out.println("OBJECTIVE: to build, explore, and survive");
    System.out.println("Your goal is to collect as many resources as possible to build a rocket to win.");
    System.out.println("The map is strictly based on a coordinate system. Please enter an appropriate direction (UP, DOWN, LEFT, or RIGHT) to move.");
    System.out.println("You can build extractors, refiners, and fabricators to help you reach your goal.");
    System.out.println("Each extractor will add 5 of their respective resource to your inventory per turn (the NEXT function can add the resource to your inventory).");
    System.out.println("There are also specific government types. It will be randomized and changed based on sanity, food, and intelligence levels. You can even talk to your crew.");
    System.out.println("-------------------------------");
    System.out.println("Good Luck, it is time to begin!");
  }
}
