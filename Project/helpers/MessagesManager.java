package Project.helpers;

import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;

public class MessagesManager{
  PauseTransition pause = new PauseTransition(Duration.seconds(2));

/**
 * <p> 
 * Method used to create a success message to the user upon successful completion of a task, such as adding an entry to the table. It takes in a parameter called type and determines if it is an "Add" type or other. If it is an "Add" type, it indicates that the user is attemping to add something to the table. It will set the messageLabel to indicate the user has successfully added data to the table, changing the colour of the message label and displaying briefly, before dissapearing
 * <p>
 * @param type String object that helps determine whether the user is trying to add data to the table or delete data from the table
 * @param messageLabel Label that is used to provide feedback for the user
*/
    public void successMessage(String type, Label messageLabel){
      if(type.equals("Add")){
        messageLabel.setText("SUCCESSFULLY ADDED");
        messageLabel.setStyle("-fx-text-fill: green");
        pause.setOnFinished(e -> messageLabel.setText(null));
        pause.play();
      } else {
        messageLabel.setText("SUCCESSFULLY DELETED");
        messageLabel.setStyle("-fx-text-fill: green");
        pause.setOnFinished(e -> messageLabel.setText(null));
        pause.play();
      }
    }

/**
 * <p> 
 * Method used to create an error message to the user upon failing to complete a task, such as adding an entry to the table. It takes in a parameter called type and determines if it is an "Add" type or other. If it is an "Add" type, it indicates that the user is attemping to add something to the table. It will set the messageLabel to indicate the user has failed to add data to the table, changing the colour of the message label and displaying briefly, before dissapearing
 * <p>
 * @param type String object that helps determine whether the user is trying to add data to the table or delete data from the table
 * @param messageLabel Label that is used to provide feedback for the user
*/
    public void errorMessage(String type, Label messageLabel){
      if(type.equals("Add")){
        messageLabel.setText("ERROR ADDING MESSAGE");
        messageLabel.setStyle("-fx-text-fill: red");
        pause.setOnFinished(e -> messageLabel.setText(null));
        pause.play();
      } else {
        messageLabel.setText("ERROR DELETING MESSAGE");
        messageLabel.setStyle("-fx-text-fill: red");
        pause.setOnFinished(e -> messageLabel.setText(null));
        pause.play();
      }
    }
    
}
