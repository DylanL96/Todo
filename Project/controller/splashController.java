package Project.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Project.helpers.FileManager;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class splashController implements Initializable {
  
    FileManager fileManager = new FileManager();

    @FXML
    private Button loginButton;

    @FXML
    private Button nonRegisteredButton;

    @FXML
    private Button registerButton;

    @FXML
    private ImageView pizza;

    @FXML
    void onClick_login_button(ActionEvent event) throws IOException {
      Parent root = FXMLLoader.load(getClass().getResource(("../view/signin.fxml"))); 
      fileManager.changeScene(event, root);
    }

    @FXML
    void onClick_register_button(ActionEvent event) throws IOException {
      Parent root = FXMLLoader.load(getClass().getResource(("../view/register.fxml"))); 
      fileManager.changeScene(event, root);
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
