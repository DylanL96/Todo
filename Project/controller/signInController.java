package Project.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Project.helpers.FileManager;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


public class signInController implements Initializable {

  File file = new File("Project/databases/registration.txt");
  PauseTransition pause = new PauseTransition(Duration.seconds(2));
  FileManager fileManager = new FileManager();
  
    @FXML
    private Label errorMessage;
    
    @FXML
    private Button close;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private TextField loginUserName;

    @FXML
    private ImageView pizza;

    @FXML
    public void onClick_btn_Exit(ActionEvent event) throws IOException{
      Parent root = FXMLLoader.load(getClass().getResource(("../view/splash.fxml"))); 
      fileManager.changeScene(event, root);
    }

    @FXML
    public void onClick_btn_Login(ActionEvent event) throws IOException{
      
      if(!fileManager.loginUser(file, loginUserName, loginPassword)){
        errorMessage.setText("INCORRECT USERNAME OR PASSWORD");
        pause.setOnFinished(b -> errorMessage.setText(null));
        pause.play();
      } else {
        // change scene upon successful login
        Parent root = FXMLLoader.load(getClass().getResource(("../view/mainView.fxml"))); 
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