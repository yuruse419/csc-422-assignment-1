import java.util.Scanner;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    // instantiate a Scanner object for user input
    Scanner inputObj = new Scanner(System.in);

    // initialize Pets ArrayList
    ArrayList<Pet> pets = new ArrayList<Pet>();

    // boolean for keeping the system running
    boolean running = true;

    while(running) {
      System.out.println("Enter the number for the corresponding action: ");
      System.out.println("\t0. Quit \n\t1. Display Pets\n\t2. Insert Pet");

      // initialize a String for user input
      String userInput = inputObj.nextLine();

      // blank line
      System.out.println();

      if(userInput.equals("0")) {
        running = false;
      }
      // display pets
      else if(userInput.equals("1")) {
        System.out.println(displayPets(pets));
      }
      // else insert pet
      else if(userInput.equals("2")) {
        // until user enters a string that contains "done"
        while(userInput.indexOf("done") == -1) {
          System.out.println("\nEnter new pet info: <name> <age> done");

          userInput = inputObj.nextLine().toLowerCase();
        }

        // try to parse input and add to pets
        try {
          String petName = userInput.substring(0, userInput.indexOf(" "));
          int petAge = Integer.parseInt(userInput.substring(petName.length() + 1, userInput.indexOf("done") - 1));

          pets.add(new Pet(petName, petAge));
        }
        catch(NumberFormatException exception) {
          System.out.println("Invalid age");
        }
      }

      // blank line
      System.out.println();
    }

    // close the Scanner object
    inputObj.close();
  }

  public static String displayPets(ArrayList<Pet> pets) {
    String table = "ID\tName\tAge";
    table += "\n--\t----\t---";

    for(int i = 0; i < pets.size(); i++) {
      table += "\n" + i + "\t" + pets.get(i).getName() + "\t" + pets.get(i).getAge();
    }

    table += "\n--\t----\t---";
    table += "\nEntries: " + pets.size();

    return table;
  }
}