package Project.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import Project.helpers.FileManager;
import Project.helpers.MessagesManager;
import Project.model.Data;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Duration;

public class mainViewController implements Initializable {
  
  public static Label static_label;
  protected ArrayList<Data> list = new ArrayList<Data>();
  private CellEditEvent<Data, String> editedCell;

  PauseTransition pause = new PauseTransition(Duration.seconds(2)); 
  File fileRegistration = new File("Project/databases/registration.txt");
  File fileDataEntry = new File("Project/databases/dataEntry.txt"); 
  FileManager fileManager = new FileManager();
  MessagesManager message = new MessagesManager();
  Random random = new Random();
  String[] quotesArray = {
    "The pessimist sees difficulty in\n every opportunity. The\n optimist sees opportunity in\n every difficulty.\n— Winston Churchill",
    "Don't let yesterday take up too\n much of today. — Will Rogers",
    "You learn more from failure\n than from success. Don't let it\n stop you. Failure builds\n character.\n — Unknown", 
     "If you are working on \nsomething that you really\ncare about, you don't \nhave to be pushed. The vision \npulls you. — Steve Jobs",
    "Experience is a hard teacher\n because she gives the test \nfirst, the lesson afterwards. \n―Vernon Sanders Law",
     "To know how much there \nis to know is the beginning of\nlearning to live.” \n— Dorothy West",
    "Goal setting is the secret to a \ncompelling future.” \n— Tony Robbins"
  };
  int randomNumber = random.nextInt(quotesArray.length);

    @FXML
    private Button addButton;

    @FXML
    private ComboBox<String> comboBoxInput;

    @FXML
    private TableColumn<Data, LocalDate> deadlineColumn;

    @FXML
    private DatePicker deadlineInput;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Data, String> descriptionColumn;

    @FXML
    private TextField descriptionInput;

    @FXML
    private TableColumn<Data, String> taskColumn;

    @FXML
    private TextField taskInput;

    @FXML
    private TableColumn<Data, String> statusColumn;

    @FXML
    private TableView<Data> table;

    @FXML
    protected Label messageLabel;

    @FXML
    private Label countLabel;

    @FXML
    public Label countLabel1;

    @FXML
    private Label incompleteLabel;

    @FXML
    private Label banner;
  
    @FXML
    private Label quoteArea;

    @FXML
    void backAction(ActionEvent event) throws IOException {
      Parent root = FXMLLoader.load(getClass().getResource(("../view/splash.fxml"))); 
      fileManager.changeScene(event, root);
    }

    @FXML
    void changeDescriptionCellEvent(CellEditEvent<Data, String> editedCell) {
      // when you click on one of the cells, it will return this selected item.
      Data taskSelected = table.getSelectionModel().getSelectedItem(); 
      taskSelected.setDescription(editedCell.getNewValue().toString()); 
      fileManager.editDataInTable(table, editedCell);
    }

    @FXML
    void changeTaskCellEvent(CellEditEvent<Data, String> editedCell) {
      Data taskSelected = table.getSelectionModel().getSelectedItem(); 
      taskSelected.setTask(editedCell.getNewValue().toString()); 
      fileManager.editDataInTable(table, editedCell);
    }

    @FXML
    void addData(ActionEvent event){
      LocalDate deadline = deadlineInput.getValue();
      String messageEvent = (((Button) event.getSource()).getText());
      // checks to see if all fields are completed
      if(taskInput == null || descriptionInput == null || deadline == null || comboBoxInput.getValue() == null){
        message.errorMessage(messageEvent, messageLabel);
      } else {
        // adds the information from the completed fields
        Data newData = new Data(taskInput.getText(), descriptionInput.getText(), deadline);

      // calls function to append data to the table
      fileManager.addDataToTable(newData, table);
      clearFields();
      message.successMessage(messageEvent, messageLabel);
      list.add(newData);
      totalAdded();
      }
    }

    @FXML
    void deleteData(ActionEvent event) throws IOException {
      // gets the index of selected row
      int row = table.getSelectionModel().getSelectedIndex();
      String messageEvent = (((Button) event.getSource()).getText());
      if(row >= 0){
        fileManager.deleteDataFromTable(table, row);
        message.successMessage(messageEvent, messageLabel);
        totalAdded();
      } else {
        message.errorMessage(messageEvent, messageLabel);
      }
    }

    // creates observableList of <Data> objects and is called datas
    ObservableList<Data> datas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    static_label = countLabel1;
    quoteArea.setText(quotesArray[randomNumber]);

    countLabel1.setText(String.valueOf(Data.counter));
    table.setPlaceholder(new Label("")); // removes the initial message "No Content in table"
    if(fileDataEntry.exists()){
      System.out.println(fileDataEntry + " already exists. No need to recreate database.");
      totalAdded();
    } else {
        try {
          fileDataEntry.createNewFile();
          System.out.println(fileDataEntry + " has been created");
        }catch(Exception e){
          System.out.println("Error occured creating the database.");
      }
    } 
    
    // creates welcome message to logged in user
    fileManager.bannerMessage(fileRegistration, banner);
      
    // update table to be editable
    table.setEditable(true);
    // creates textfield when you double click cell, then call our method to commit the change
    taskColumn.setCellFactory(TextFieldTableCell.forTableColumn()); 
    descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn()); 

    // adding data to dropdown menu
    ObservableList<String> comboList = FXCollections.observableArrayList("IMPORTANT", "UNIMPORTANT");
    comboBoxInput.setItems(comboList);

    // setting the columns for the table
    taskColumn.setCellValueFactory(new PropertyValueFactory<>("Task"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("button"));
    deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("DeadLine"));

    //loads table with data
    fileManager.displayDataToTable(fileDataEntry,table);

    // edits data in table
    fileManager.editDataInTable(table, editedCell);
    }
    
    // clear the input fields
    public void clearFields(){
      taskInput.clear();
      descriptionInput.clear();
      deadlineInput.getEditor().clear();
      comboBoxInput.valueProperty().set(null);
    }

    // reads the number of lines in the fileDataEntry.txt, which is the number of tasks added
    public int totalAdded(){
      int lines = 0;
        try(LineNumberReader lineNumber = new LineNumberReader(new FileReader(fileDataEntry))){
          while(lineNumber.readLine() !=null){
            lines = lineNumber.getLineNumber();
          }
        }catch(FileNotFoundException e){
          System.out.println("fileDataEntry.txt not found");
        }catch(Exception e){
          System.out.println("Error");
        }
        countLabel.setText(String.valueOf(lines));
        return lines;
    }
    

}
