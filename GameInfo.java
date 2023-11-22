import java.util.*;
/*This class creates the terrain/base for the game that allows for items */
public class GameInfo {
	
  //declaring variables and 2 dimensional arrays
	static int x = 11;
	static int y = 9;
	int selectBuffer = 0;
  static int CoordX = 0;
  static int CoordY = 0;
	String terrain[][] = new String[y][x];
	String loadMap[][] = new String[y][x];
  public static ResourceGen res = new ResourceGen();
  public PerlinNoise terrainNoise;

  public static ArrayList<Base> bases = new ArrayList<Base>();

  /* Constructor to intitalize starting values and call on methods immediately after an object is created*/
  public GameInfo () {
    initializeNoise();
    Inventory.initializeItems();
    initializeNoise();
    updateTerrain();
    bases.add(new Base(0, (x/2)+1, (y/2)+1));
  }
/*This method initializes perlin noise*/
  public void initializeNoise() {
    Random rand = new Random();
    terrainNoise = new PerlinNoise(rand.nextInt(2147483647) + 1, 9.1, 1000, 0.000001, 10);
  }
	/*this method updates the terrain after it has been smoothed out with perlin noise values*/
	public void updateTerrain() {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				int currentVal = (int)(terrainNoise.getHeight(i+CoordY,j+CoordX));
				terrain[i][j]=smooth(currentVal);
			}
		}
	}
	/*this method uses perlin noise to smooth out terrain*/
	public String smooth (int i) {
      //terrain
	    if (i <= 0){
	        return "\033[0;91m_";
	    }
      //resources
	    else if (i <= 10 && i > 0){
	        return "\033[0;91m-";
	    }
      //water
	    else if (i <= 50 && i > 10){
	        return "\033[0;34m~";
	    }
      //mountains
	    else if (i > 50){
	        return "\033[0;91m^";
	    }
	    return "0";
	}
	/*This method loads the map into one 2d array for usage later*/
	public void loadMap() {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
        if (res.getTile(i,j).equals("?") == true){
          loadMap[i][j] = terrain[i][j];
        }
        else {
          loadMap[i][j] = res.getTile(i,j);
        }
			}
		}
    
		for (int i = 0; i<bases.size(); i++) {
      try {
			loadMap[(bases.get(i).y)-1][(bases.get(i).x)-1] = baseLoaded(bases.get(i).id);
      }
      catch (Exception e){
      }
		}
	}
	
  /*this method prints the map*/
	public void drawMap() {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
        System.out.print(loadMap[i][j] + "\u001B[0m ");
			}
			System.out.println();
		}
	}
	/*This method finds bases based on coordinates*/
	public int findBase(int xSelection, int ySelection) {
		for (int i = 0; i < bases.size(); i++) {
			if (bases.get(i).x == xSelection && bases.get(i).y == ySelection) {
				return i;
			}
		}
		return -1;
	}
	/*This method removes bases and check to see if it is a valid base to be removed*/
	public void removeBase (int selection) {
		if (selection == -1) {
			System.out.println("NO BASE REMOVED...");
		}
		else {
			bases.remove(selection);
		}
	}
/*This method returns the corresponding symbol for the resource selected*/
  public String baseLoaded (int baseID) {
    switch (baseID){
      case 0:
        return "Ѫ";
      case 1:
        return "Ϯ";
      case 2:
        return "\033[0;90mӿ";
      case 3:
        return "Ψ";
      case 4:
        return "Ɏ";
      case 5:
        return "ǆ";
      case 6:
        return "ǅ";
      case 7:
        return "Ǆ";
      case 8:
        return "ţ";
      case 9:
        return "Ŧ";
      case 10:
        return "Ť";
      case 11:
        return "Ѧ";
      case 12:
        return "J"; 
    }
    return "X";
  }
/*this methd shifts the base based on the coordinates entered*/
  public void baseShift (int x, int y) {
    for (int i = 0; i<bases.size(); i++) {
      bases.get(i).setY(bases.get(i).y+y);
      bases.get(i).setX(bases.get(i).x+x);
		}
  }
	/*this is a get functon that return x-value*/
  public static int getX () {
    return x;
  }
/*this is a get functon that return y-value*/
  public static int getY () {
    return y;
  }
/*this is a get functon that return y-coordinate*/
  public static int getCoordY () {
    return CoordY;
  }
/*this is a get functon that return x-coordinate*/
  public static int getCoordX () {
    return CoordX;
  }
}