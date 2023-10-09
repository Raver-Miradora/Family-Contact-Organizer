/*
 * Family Contact Organizer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Family Contact Organizer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Family Contact Organizer. If not, see 
 * https://github.com/Raver-Miradora/Family-Contact-Organizer.
 */

import java.io.*;
import java.util.Scanner;

public class Family_Contact_Organizer{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        do{
            menu(); //Print the Menu

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch(choice){
                case 1:
                    addContactInformation();
                    break;
                case 2:
                    printAllContactInformation();
                    break;
                case 3:
                    deleteContactInformation();
                    break;
                case 4:
                    searchContactInformation();
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!\n");
                    break;
                default:
                    System.out.println("Invalid input. Please try again.\n");
                    break;
            }
        } while (choice != 5);
    }

    // Display the menu options
    static void menu(){
        System.out.print("----------------------------------------------------------\n");
        System.out.println("           \tFAMILY CONTACT ORGANIZER");
        System.out.print("----------------------------------------------------------\n");
        System.out.println("[1]. Add Family Contact");
        System.out.println("[2]. Print All Contacts");
        System.out.println("[3]. Delete Family Contact");
        System.out.println("[4]. Search Contact");
        System.out.println("[5]. Exit");
        System.out.print("Enter your choice: ");
    }

    // Add contact information to the file
    private static void addContactInformation(){
        Scanner scanner = new Scanner(System.in);
        try{
            // Gather contact information
            System.out.print("\nEnter full name       : ");
            String fullName = scanner.nextLine();
            System.out.print("Enter relationship    : ");
            String relationship = scanner.nextLine();
            System.out.print("Enter contact info    : ");
            String contactInfo = scanner.nextLine();
            System.out.print("Enter address         : ");
            String address = scanner.nextLine();
            System.out.print("Enter email           : ");
            String email = scanner.nextLine();

            // Save the information to a file
            FileWriter fileWriter = new FileWriter("family_contacts.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Write the formatted contact information
            printWriter.println("Full Name   \t: " + fullName);
            printWriter.println("Relationship    : " + relationship);
            printWriter.println("Contact Info    : " + contactInfo);
            printWriter.println("Address     \t: " + address);
            printWriter.println("Email       \t: " + email);
            printWriter.println();
            printWriter.close();

            System.out.print("\nFamily contact information saved successfully!\n");
        }catch(IOException e){
            System.out.println("An error occurred while saving the family contact information.\n");
            e.printStackTrace();
        }
    }

    // Print all contact information
    private static void printAllContactInformation(){
        try{
            FileReader fileReader = new FileReader("family_contacts.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            boolean isEmpty = true;
            StringBuilder contactInfo = new StringBuilder();
            System.out.println();
            while((line = bufferedReader.readLine()) != null){
                if (!line.isEmpty()) {
                    contactInfo.append(line).append("\n");
                    isEmpty = false;
                } else {
                    if (contactInfo.length() > 0) {
                        System.out.println(contactInfo.toString());
                        contactInfo.setLength(0);
                    }
                }
            }

            if(contactInfo.length() > 0) {
                System.out.println(contactInfo.toString());
            }
            bufferedReader.close();

            if(isEmpty){
                System.out.println("No contact information available to print.\n");
            }
        }catch(FileNotFoundException e){
            System.out.print("\nFamily contact file not found.\n");
        }catch(IOException e){
            System.out.println("An error occurred while reading the family contact information.\n");
            e.printStackTrace();
        }
    }

    // Delete a contact
    private static void deleteContactInformation(){
        Scanner scanner = new Scanner(System.in);
        try{
            File inputFile = new File("family_contacts.txt");
            if(!inputFile.exists() || inputFile.length() == 0){
                System.out.print("No contact information available to delete.\n");
                return;
            }

            System.out.print("Enter the full name of the contact you want to delete: ");
            String fullNameToDelete = scanner.nextLine().trim(); // Trim any leading or trailing spaces

            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = "Full Name   \t: " + fullNameToDelete;
            String currentLine;
            boolean found = false;

            while((currentLine = reader.readLine()) != null){
                if(currentLine.trim().equals(lineToRemove)){ // Trim spaces for comparison
                    for (int i = 0; i < 4; i++){ 
                        reader.readLine();
                    }
                    found = true;
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

            if(found){
                System.out.print("\nContact information deleted successfully!\n");
            }else{
                System.out.print("\nNo contact information found for the given name.\n");
            }
        }catch(IOException e){
            System.out.println("An error occurred while deleting the contact information.\n");
            e.printStackTrace();
        }
    }

    // Search for a contact by full name
    private static void searchContactInformation(){
        Scanner scanner = new Scanner(System.in);
        try {
            File inputFile = new File("family_contacts.txt");

            if (!inputFile.exists() || inputFile.length() == 0) {
                System.out.print("No family contact available to search.\n");
                return;
            }

            System.out.print("Enter the full name of the contact you want to search: ");
            String fullNameToSearch = scanner.nextLine();
            System.out.println();

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            String currentLine;
            boolean found = false;

            while((currentLine = reader.readLine()) != null){
                if(currentLine.contains("Full Name") && currentLine.contains(fullNameToSearch)){
                    for(int i = 0; i < 5; i++){  
                        System.out.println(currentLine);
                        currentLine = reader.readLine();
                    }
                    found = true;
                    break;
                }
            }
            reader.close();

            if(!found){
                System.out.print("No contact information found for the given name.\n");
            }
        }catch(IOException e){
            System.out.println("An error occurred while searching for family contact information.\n");
            e.printStackTrace();
        }
    }
}
