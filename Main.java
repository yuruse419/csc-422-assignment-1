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
      // prompt user for action
      System.out.println("\nEnter the number for the corresponding action:");
      System.out.println("\t0. Quit \n\t1. Display Pets\n\t2. Insert Pet\n\t3. Search Pets\n\t4. Edit Pet\n\t5. Remove Pet");

      // initialize a String for user input
      String userInput = inputObj.nextLine();

      // quit
      if(userInput.equals("0")) {
        running = false;
      }
      // else display pets
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
      // else search by pet name or age
      else if(userInput.equals("3")) {
        System.out.println("\nHow do you wish to search?");

        // until user chooses a valid action
        while(!userInput.equals("1") && !userInput.equals("2")) {
          System.out.println("\t1. By Name\n\t2. By Age");

          userInput = inputObj.nextLine();
        }

        System.out.println("Enter search parameter:");

        // search by name
        if(userInput.equals("1")) {
          String searchName = inputObj.nextLine().toLowerCase();

          System.out.println(displayPets(pets, searchName));
        }
        // else try search by age
        else if(userInput.equals("2")) {
          try {
            int searchAge = Integer.parseInt(inputObj.nextLine());

            System.out.println(displayPets(pets, searchAge));
          }
          catch(NumberFormatException exception) {
            System.out.println("Invalid age");
          }
        }
      }
      // else update pet
      else if(userInput.equals("4")) {
        // display pets for user reference
        System.out.println(displayPets(pets));

        System.out.println("\nSelect a pet ID from the above table:");

        try {
          int id = Integer.parseInt(inputObj.nextLine());

          System.out.println("Enter new pet info: <name> <age> done:");

          userInput = inputObj.nextLine();

          // parse input
          String petName = userInput.substring(0, userInput.indexOf(" "));
          int petAge = Integer.parseInt(userInput.substring(petName.length() + 1, userInput.indexOf("done") - 1));

          // set attributes of pet at diven index
          pets.get(id).setName(petName);
          pets.get(id).setAge(petAge);
        }
        catch(NumberFormatException exception) {
          System.out.println("Invalid ID or info");
        }
        catch(IndexOutOfBoundsException exception) {
          System.out.println("Invalid ID");
        }
      }
      // else remove pet
      else if(userInput.equals("5")) {
        // display pets for user reference
        System.out.println(displayPets(pets));

        System.out.println("Select a pet ID from the above table:");

        try {
          int id = Integer.parseInt(inputObj.nextLine());

          pets.remove(id);
        }
        catch(NumberFormatException exception) {
          System.out.println("Invalid ID");
        }
        catch(IndexOutOfBoundsException exception) {
          System.out.println("Invalid ID");
        }
      }
    }

    // close the Scanner object
    inputObj.close();
  }

  // generate table of pets
  public static String displayPets(ArrayList<Pet> pets) {
    String table = "\nID\tName\tAge";
    table += "\n--\t----\t---";

    for(int i = 0; i < pets.size(); i++) {
      table += "\n" + i + "\t" + pets.get(i).getName() + "\t" + pets.get(i).getAge();
    }

    table += "\n--\t----\t---";
    table += "\nEntries: " + pets.size() + "\n";

    return table;
  }

  // override displayPets for name searching
  public static String displayPets(ArrayList<Pet> pets, String filter) {
    String table = "\nID\tName\tAge";
    table += "\n--\t----\t---";

    int entries = 0;

    for(int i = 0; i < pets.size(); i++) {
      if(pets.get(i).getName().indexOf(filter) != -1) {
        table += "\n" + i + "\t" + pets.get(i).getName() + "\t" + pets.get(i).getAge();

        entries++;
      }
    }

    table += "\n--\t----\t---";
    table += "\nEntries: " + entries + "\n";

    return table;
  }

  // override displayPets for age searching
  public static String displayPets(ArrayList<Pet> pets, int filter) {
    String table = "\nID\tName\tAge";
    table += "\n--\t----\t---";

    int entries = 0;

    for(int i = 0; i < pets.size(); i++) {
      if(pets.get(i).getAge() == filter) {
        table += "\n" + i + "\t" + pets.get(i).getName() + "\t" + pets.get(i).getAge();

        entries++;
      }
    }

    table += "\n--\t----\t---";
    table += "\nEntries: " + entries + "\n";

    return table;
  }
}