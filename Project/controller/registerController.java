package Project.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.util.Duration;
import Project.helpers.FileManager;
import Project.model.Person;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;



public class registerController implements Initializable {
  
  private final String REGISTERDETAILS = "Project/databases/registration.txt";
  private ArrayList <Person> wordList = new ArrayList<>();
  FileManager fileManager = new FileManager();
  PauseTransition pause = new PauseTransition(Duration.seconds(2));

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Button registerBackButton;

    @FXML
    private ImageView pizza;

    @FXML
    private Label registerErrorMessage;

    @FXML
    void onClick_Register_Back_Button(ActionEvent event) throws IOException {
      Parent root = FXMLLoader.load(getClass().getResource(("../view/splash.fxml"))); 
      fileManager.changeScene(event, root);
    }

    @FXML
    void onClick_Btn_Register(ActionEvent event) throws IOException {
      if(usernameField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty()){
        registerErrorMessage.setText("Missing fields");
        pause.setOnFinished(b -> registerErrorMessage.setText(null));
        pause.play();
      } else {
      // instantiate new File object with the pathway
      File file = new File(REGISTERDETAILS);
      wordList.add(new Person(usernameField.getText().toString(), passwordField.getText().toString()));
      fileManager.registerUser(file, wordList);

      // change scene to log in upon successful entry saving to registration.txt
      Parent root = FXMLLoader.load(getClass().getResource(("../view/signin.fxml"))); 
      fileManager.changeScene(event, root);
      }
  }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      RotateTransition rotate = new RotateTransition();
      rotate.setNode(pizza);
      rotate.setDuration(Duration.millis(3000));
      rotate.setCycleCount(TranslateTransition.INDEFINITE);
      rotate.setByAngle(360);
      rotate.play();
      
    }

}
