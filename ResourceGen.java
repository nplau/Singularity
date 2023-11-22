//imports
import java.util.*;

/*Generates resources for the player to use in gameplay*/
class ResourceGen {
  //variables and objects
  static int [][] oreMap = new int [GameInfo.getY()][GameInfo.getX()];//mid priority
  static int [][] woodMap = new int [GameInfo.getY()][GameInfo.getX()];//lowest priority
  static int [][] fuelMap = new int [GameInfo.getY()][GameInfo.getX()];//highest priority
  static Random rand = new Random();
  // PerlinNoise noise = new PerlinNoise(rand.nextInt(2147483647), 5, 10, 0.000001, 10);
  static PerlinNoise noise = new PerlinNoise(rand.nextInt(2147483647), 5, 10, 0.000001, 10);
  static double noiseVal = 0;

/*This method adds resource deposits to the map*/
  public static void renderNewCoords() {
    //fills oreMap
    for (int i = 0; i < GameInfo.getY(); i++) {
      for (int j = 0; j < GameInfo.getX(); j++) {
        noiseVal = noise.getHeight(i+GameInfo.getCoordY(), j+GameInfo.getCoordX());
        //get rid of negatives
        if (noiseVal < 0) {
          noiseVal = noiseVal * -1;
        }
        //add an ore deposit
        if (noiseVal <= 0.15) {
          oreMap[i][j] = 1;
          // print2DArray(oreMap);
        }
        //add a coal deposit
        else if (noiseVal <= 0.25) {
          oreMap[i][j] = 2;
        }
        //adds blank tile
        else {
          oreMap[i][j] = 0;
        }
      }
    }
    //fills woodMap
    for (int i = 0; i < GameInfo.getY(); i++) {
      for (int j = 0; j < GameInfo.getX(); j++) {
        noiseVal = noise.getHeight(i+GameInfo.getCoordY(), j+GameInfo.getCoordX());
        
        //get rid of negatives
        if (noiseVal < 0) {
          noiseVal = noiseVal * -1;
        }
        //add tree
        if (noiseVal <= 0.3) {
          woodMap[i][j] = 1;
        }
        //adds blank tile
        else {
          woodMap[i][j] = 0;
        }
      }
    }
    //fills fuelMap
    for (int i = 0; i < GameInfo.getY(); i++) {
      for (int j = 0; j < GameInfo.getX(); j++) {
        noiseVal = noise.getHeight(i+GameInfo.getCoordY(), j+GameInfo.getCoordX());
        //get rid of negatives
        if (noiseVal < 0) {
          noiseVal = noiseVal * -1;
        }
        //add fuel pocket
        if (noiseVal <= 0.05) {
          fuelMap[i][j] = 1;
        }
        else {
          fuelMap[i][j] = 0;
        }
      }
    }
  }

  /*Given a coordinate, compares the three arrays and returns the correct value for the resource in the tile*/
  public static String getTile(int y, int x) {
    String resource = "?";
    if (fuelMap[y][x] != 0) {
      resource = "\u001B[30m-";
    }
    else if (oreMap[y][x] != 0) {
      //ore
      if (oreMap[y][x] == 1) {
        resource = "\033[0;90m-";
      }
      //coal
      else {
        resource = "\u001B[0;33m-";
      }
    }
    else if (woodMap[y][x] != 0) {
      resource = "\u001B[32mϡ";
    }
    else {
      resource = "?";
    }
    return resource;
  }
/*This method prints the 2D array maps*/
  public void print2DArray(int[][] arr) {
    //traversing array
    for (int i = 0; i < arr.length; i++){
      for (int j = 0; j < arr[0].length; j++){
        System.out.print("  " + arr[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println(GameInfo.CoordY);
  }
}