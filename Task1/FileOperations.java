import java.io.*;
import java.util.Scanner;

/**
 * CODTECH Internship - Task 1
 * Java program to demonstrate basic File Handling:
 * 1. Write to a file
 * 2. Read from a file
 * 3. Modify file content
 *
 * Author: Mallela Pujitha
 * Date: June 2025
 */

public class FileOperations {

    // Method to write content to a file
    public static void writeToFile(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
            System.out.println(" Content written to file successfully.");
        } catch (IOException e) {
            System.out.println(" Error writing to file: " + e.getMessage());
        }
    }

    // Method to read and display file content
    public static void readFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println(" File Content:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(" Error reading file: " + e.getMessage());
        }
    }

    // Method to modify file content by replacing a word
    public static void modifyFile(String filename, String oldWord, String newWord) {
        StringBuilder fileContent = new StringBuilder();

        // Read existing content
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                fileContent.append(line.replaceAll(oldWord, newWord)).append("\n");
            }
        } catch (IOException e) {
            System.out.println(" Error reading file for modification: " + e.getMessage());
            return;
        }

        // Write updated content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(fileContent.toString());
            System.out.println(" File content modified successfully.");
        } catch (IOException e) {
            System.out.println(" Error writing modified content: " + e.getMessage());
        }
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filename = "sample.txt";

        System.out.println("🔧 CODTECH Internship File Handling Utility 🔧");

        // Write to file
        System.out.print("\n Enter content to write to the file: ");
        String content = scanner.nextLine();
        writeToFile(filename, content);

        // Read the file
        readFromFile(filename);

        // Modify the file
        System.out.print("\n Enter the word to replace: ");
        String oldWord = scanner.nextLine();

        System.out.print(" Enter the new word: ");
        String newWord = scanner.nextLine();

        modifyFile(filename, oldWord, newWord);

        // Read again to show changes
        readFromFile(filename);

        scanner.close();
    }
}
