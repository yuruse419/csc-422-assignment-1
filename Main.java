import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
  public static void main(String[] args) throws FileNotFoundException, IOException {
    // instantiate a Scanner object for user input
    Scanner inputObj = new Scanner(System.in);

    // create a Path reference to the pets file
    Path petsFilePath = Paths.get("stored_pets.txt");

    // if the pets file does not exist
    if(!Files.exists(petsFilePath)) {
      // create the pets file
      Files.createFile(petsFilePath);
    }

    // initialize empty Pets ArrayList
    ArrayList<Pet> pets = new ArrayList<Pet>();

    // create Iterator for the pets file
    Iterator<String> petsDataIterator = Files.readAllLines(petsFilePath).listIterator();

    // while a next pet entry exists
    while(petsDataIterator.hasNext()) {
      // if five (5) pet entries have already been added
      if(pets.size() >= 5) {
        // notify of capacity reached
        System.out.println("Pet capacity reached.");

        // terminate pet insertions
        break;
      }

      // store the next pet entry and advance the pointer
      String petEntry = petsDataIterator.next();

      try {
        // try to store the index of the name–age delimiter
        int delimiterIndex = petEntry.indexOf(" ");

        // try to parse pet name and age
        String petName = petEntry.substring(0, delimiterIndex);
        int petAge = Integer.parseInt(petEntry.substring(delimiterIndex + 1));
  
        // if age between 0 and 21, exclusively
        if(petAge > 0 && petAge < 21) {
          // add pet to pets ArrayList
          pets.add(new Pet(petName, petAge));
        }
        // else if age out of bounds
        else {
          System.out.println("Invalid entry skipped (age out of bounds)...");
        }
      }
      // catch for invalid age
      catch(NumberFormatException exception) {
        System.out.println("Invalid entry skipped (non-numerical age)...");
      }
      // catch for absence of delimiter (i.e., " ")
      catch(IndexOutOfBoundsException exception) {
        System.out.println("Invalid entry skipped (no delimiter)...");
      }
    }

    // user-controlled loop
    while(true) {
      // prompt user for action
      System.out.println("\nEnter the number for the corresponding action:");
      System.out.println("\t0. Quit \n\t1. Display Pets\n\t2. Insert Pet\n\t3. Search Pets\n\t4. Edit Pet\n\t5. Remove Pet");

      // initialize a String for user input
      String userInput = inputObj.nextLine();

      // quit
      if(userInput.equals("0")) {
        break;
      }
      // else display pets
      else if(userInput.equals("1")) {
        System.out.println(displayPets(pets));
      }
      // else insert pet
      else if(userInput.equals("2")) {
        // if stored pets is not at capacity
        if(pets.size() < 5) {
          // prompt for pet info or termination
          System.out.println("\nEnter new pet info or type \"done\" to finish. \n\tFormat: <name> <age>");
        }

        // loop for pet-info input
        while(true && pets.size() < 5) {
          // store input
          userInput = inputObj.nextLine().toLowerCase();

          // if input equals "done," break loop
          if(userInput.equals("done")) {
            break;
          }

          try {
            // try to store the index of the name–age delimiter
            int delimiterIndex = userInput.indexOf(" ");

            // try to parse input
            String petName = userInput.substring(0, delimiterIndex);
            int petAge = Integer.parseInt(userInput.substring(delimiterIndex + 1));
  
            // add to pets ArrayList
            pets.add(new Pet(petName, petAge));

            byte[] newPetAsBytes = (petName + " " + petAge + "\n").getBytes();

            // add pet to pets file
            Files.write(petsFilePath, newPetAsBytes, StandardOpenOption.APPEND);

            // output confirmation
            System.out.println("Pet added.");
          }
          // catch for invalid age
          catch(NumberFormatException exception) {
            System.out.println("Invalid age");
          }
        }

        // if stored pets is at capacity
        if(pets.size() >= 5) {
          // notify user to remove a pet to store another 
          System.out.println("\nPets capacity reached. Please remove a pet to store another.");
        }
      }
      // else search by pet name or age
      else if(userInput.equals("3")) {
        // prompt for search by name or by age
        System.out.println("\nHow do you wish to search?");

        // until user chooses a valid action
        while(!userInput.equals("1") && !userInput.equals("2")) {
          // prompt for selection
          System.out.println("\t1. By Name\n\t2. By Age");

          // store user selection
          userInput = inputObj.nextLine();
        }

        // prompt for search parameter
        System.out.println("Enter search parameter:");

        // search by name
        if(userInput.equals("1")) {
          // store search parameter
          String searchName = inputObj.nextLine().toLowerCase();

          // display matching pets
          System.out.println(displayPets(pets, searchName));
        }
        // else try search by age
        else if(userInput.equals("2")) {
          try {
            // try to store search parameter
            int searchAge = Integer.parseInt(inputObj.nextLine());

            // display matching pets
            System.out.println(displayPets(pets, searchAge));
          }
          // catch for invalid age
          catch(NumberFormatException exception) {
            System.out.println("Invalid age");
          }
        }
      }
      // else update pet
      else if(userInput.equals("4")) {
        // display pets for user reference
        System.out.println(displayPets(pets));

        // prompt for pet ID
        System.out.println("\nSelect a pet ID from the above table:");

        try {
          // try to parse pet ID from input
          int id = Integer.parseInt(inputObj.nextLine());

          // prompt for and store new pet info
          System.out.println("Enter new pet info. \n\tFormat: <name> <age>");
          userInput = inputObj.nextLine();

          // parse and store input
          String petName = userInput.substring(0, userInput.indexOf(" "));
          int petAge = Integer.parseInt(userInput.substring(petName.length() + 1));

          // set attributes of pet at given index
          pets.get(id).setName(petName);
          pets.get(id).setAge(petAge);
        }
        // catch for invalid ID or age
        catch(NumberFormatException exception) {
          System.out.println("Invalid ID or age");
        }
        // additional catch for invalid ID
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

    // close the user-input Scanner object
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