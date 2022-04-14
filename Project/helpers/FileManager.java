package Project.helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import Project.model.Data;
import Project.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class FileManager {
  File fileDataEntry = new File("Project/databases/dataEntry.txt"); 
  private Stage stage;
  private Scene scene;
  


/**
 * <p> 
 * Method to register the user. It reads the ArrayList of type Person and saves the information to the file that was passed.
 * <p>
 * @param file File object containing path to desired file on disk
 * @param wordList ArrayList of type Person and contains the username and password of the registered user.
*/
  public void registerUser(File file, ArrayList<Person> wordList){
    try (PrintWriter printWriter = new PrintWriter(file)){
      for(Person string : wordList){
        printWriter.println(string.toString() + "\n");
      }
    }catch(FileNotFoundException error){
      System.out.println("FILE CANT BE FOUND");
    }
  }

/**
 * <p> 
 * Method to log the user in. It will iterate through the file and compare the username and password inside the registration textfile to the username and password that the user had entered. 
 * <p>
 * @param file File object containing path to desired file on disk
 * @param loginUserName TextField object called loginUserName
 * @param loginPassword TextField object called loginPassword
 * @return true
*/
  public boolean loginUser(File file, TextField loginUserName, PasswordField loginPassword){
    try (Scanner scanner = new Scanner(file)) {
      String registeredUserName = scanner.nextLine();
      String registeredPassword = scanner.nextLine();
      if(registeredUserName.equals(loginUserName.getText()) && registeredPassword.equals(loginPassword.getText()))
        return true;
    } catch (FileNotFoundException e) {
      System.out.println("Cannot find the file for registration details");
    }
    return false;
  }

/**
 * <p> 
 * Displays a welcome message of the user. The scanner will find the first string in the file, which is the username and set it to the registeredUserName variable and set the text to create a more personalized message to the logged in user.
 * <p>
 * @param file File object containing path to desired file on disk
 * @param banner TextField object called loginUserName
*/
  public void bannerMessage(File file, Label banner){
    try(Scanner scanner = new Scanner(file)){
      String registeredUserName = scanner.nextLine();
      banner.setText(registeredUserName.toUpperCase() + "'s TODO LIST");
    }catch(FileNotFoundException e){
      System.out.println("Cannot find the file for registration details");
    }
  }

/**
 * <p> 
 * Method to read the data and display it in the Table. It will iterate through the file and create new Data objects for every line of data that was entered by the user and append it to the table to be viewed.
 * <p>
 * @param file File object containing path to desired file on disk
 * @param table TableView<Data> object
*/

  public void displayDataToTable(File file, TableView<Data> table){
    ObservableList<Data> datas = FXCollections.observableArrayList();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      String[] array;
      // while the line is not null
      while((line = br.readLine()) !=null){
        array = line.split(" ");
        table.getItems().add(new Data(array[0], array[1], LocalDate.parse(array[2])));
        Data dataToAdd = new Data(array[0], array[1], LocalDate.parse(array[2]));
        datas.add(dataToAdd);
        // table.setItems(datas);
      }
    } catch (FileNotFoundException e) {
      System.out.println("Cannot find the file to read the data");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

/**
 * <p> 
 * Method to allow the user to add data to the table.
 * <p>
 * @param data object called data that is of type Data
 * @param table TableView<Data> object
*/
  public void addDataToTable(Data data, TableView<Data> table){
    table.getItems().add(data);
    try (FileWriter fileWriter = new FileWriter(fileDataEntry, true)) {
      fileWriter.append(data.getTask() + " " + data.getDescription() + " " + data.getDeadLine() + " " + data.getStatus() + "\n");
    } catch (Exception e) {
      System.out.println("Cannot add data to the file");
    }
  }

/**
 * <p> 
 * Method to allow the user to delete data in the table. It uses the row number to delete the row of data that the user has selected. The method was to copy the file row by row, except for the line the user did not want and then save it to a temporary file. Then delete the original file, and then rename the temporary file as the original file
 * <p>
 * @param file File object containing path to desired file on disk
 * @param table TableView<Data> object
*/

  public void deleteDataFromTable(TableView<Data> table, int row){
    File tempFile = new File("Project/databases/dataEntry2.txt");
    try (
      BufferedReader reader = new BufferedReader(new FileReader(fileDataEntry));     
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
      LineNumberReader lineNumber = new LineNumberReader(new FileReader(fileDataEntry));
      ){
        String currentLine;
        int count = -1;
        while((currentLine = reader.readLine()) != null) {
          count++;
          if(count == row){
            continue;
          }
          writer.write(currentLine + System.lineSeparator());
        }
        fileDataEntry.delete();
        tempFile.renameTo(fileDataEntry);
        table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
      }catch(FileNotFoundException e){
        System.out.println("Cannot find file to delete data");
      }catch(Exception e){
        System.out.println("An error has occured");
      }
  }

/**
 * <p> 
 * Method that allows the users to edit the cells in the table. It creates an array of Strings called arr which splits it up between spaces of each data. Then it determines whether the cell clicked was either a "Task" column or other column. If it was a "Task" column, it determines that the word to be replaced is the very first element in the array arr. It will then go through the original file and replace each old text with a new text that the user inputted, write it to a temporary file and renaming that temporary file to the original temporary file
 * <p>
 * @param table TableView<Data> object
 * @param editedCell CellEditEvent<Data, String> event that is fired when a user wants to perform an edit on a TableView Cell
*/

  public void editDataInTable(TableView<Data> table, CellEditEvent<Data, String> editedCell){
    File tempFile = new File("Project/databases/dataEntry2.txt");
    String input = "";
    int row = table.getSelectionModel().getSelectedIndex();
    try (
      BufferedReader reader = new BufferedReader(new FileReader(fileDataEntry));     
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
      ){
        String currentLine;
        String[] arr = Files.readAllLines(Paths.get("Project/databases/dataEntry.txt")).get(row).split(" ");

        if(editedCell.getTableColumn().getText().equals("Task")){
          String wordToBeReplaced = arr[0];
          String newWord = editedCell.getNewValue().toString();
          while((currentLine = reader.readLine()) != null){
            input = currentLine + System.lineSeparator();
            input = input.replace(wordToBeReplaced, newWord);
            writer.write(input);
            tempFile.renameTo(fileDataEntry);
          }  
        } else {
          String wordToBeReplaced = arr[1];
          String newWord = editedCell.getNewValue().toString();
          while((currentLine = reader.readLine()) != null){
            input = currentLine + System.lineSeparator();
            input = input.replace(wordToBeReplaced, newWord);
            writer.write(input);
            tempFile.renameTo(fileDataEntry);
          }  
        }      
      }catch(FileNotFoundException e){
        System.out.println("File cannot be find to edit data");
    }catch(Exception e){
      
    }
  }

/**
 * <p> 
 * This method changes the scene when the event is clicked. For example, clicking on the register button will bring you to the register page. Clicking on the back button will bring you back to the home page.
 * <p>
 * @param event ActionEvent representing some sort of event happening.
 * @param root The base class for all nodes that have children in the scene graph.
*/
  public void changeScene(ActionEvent event, Parent root){
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
