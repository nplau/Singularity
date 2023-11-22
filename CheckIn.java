//imports
import java.util.*;

/*Checks inputs to ensure they are valid*/
public class CheckIn {
  //variables
	static boolean checkActive = false;
	static Scanner scanner = new Scanner(System.in);

  /*Checks to make sure that the user enters the correct option*/
	public static int isValid (String[] options) {
		checkActive = true;
		while (checkActive == true) {
			String s = scanner.next();
			for (int i = 0; i<options.length; i++) {
				if (s.equalsIgnoreCase(options[i])) {
					return i;
				}
			}
      //displaying an appropriate invalid option
			System.out.print("INVALID: ");
			printArray(options);
			System.out.println();
		}
		return 0;
	}

  /*Checks to make sure the input is a valid int*/
	public static int validInt (String input, int low, int high) {
		checkActive = true;
    //making sure the input is within the range of the game
      while (checkActive == true) {
        try {
        System.out.println("ENTER VALUE FOR " + input);
        int i = Integer.parseInt(scanner.next());
        if (i < low) {
          System.out.println("TOO LOW, TRY AGAIN: ");
        }
        else if (i > high) {
          System.out.println("TOO HIGH, TRY AGAIN: ");
        }
        else {
          return i;
        }
      }
      catch (Exception e) {
        System.out.println("NOT AN OPTION, TRY AGAIN.");
      }
    }
    return 0;
	}
/*This method prints out options array*/
  public static void printArray (String[] options) {
    for (int j = 0; j<options.length; j++) {
        if (j != options.length-1){
          System.out.print(options[j] + ", ");
        }
        else {
          System.out.print(options[j] + ".");
        }
		}
  }
}
