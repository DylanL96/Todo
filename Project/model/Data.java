package Project.model;

import java.time.LocalDate;

import Project.controller.mainViewController;
import javafx.scene.control.Button;

public class Data extends mainViewController{
  protected String task;
  protected String description;
  protected Button button;
  protected boolean toggle;
  protected LocalDate deadline;
  protected String status;
  public static int counter = 0;

  public Data(String task, String description, LocalDate deadline ) {
      this.task = task;
      this.description = description;
      this.button = new Button("INCOMPLETE");
      this.deadline = deadline;
      this.status = "INCOMPLETE";
  }

  public void setButton(Button button){
    this.button = button;
  }

  public Button getButton(){
    this.button.setOnAction(value -> {
      toggle = !toggle;
      if(toggle){
        setStatus("COMPLETE");
        this.button.setStyle("-fx-background-color: green");
        this.button.setText("COMPLETE");
        counter++;
        static_label.setText(String.valueOf(counter));
      } else {
        setStatus("INCOMPLETE");
        this.button.setText("INCOMPLETE");
        this.button.setStyle("-fx-background-color: transparent");
        counter--;
        static_label.setText(String.valueOf(counter));
      }
    });
    return button;
}

  public void setDeadLine(LocalDate deadline){
      this.deadline = deadline;
  }

  public LocalDate getDeadLine(){
      return deadline;
  }

  public String getStatus(){
      return status;
  }

  public void setStatus(String status){
    this.status = status;
  }

  public String getDescription() {
      return description;
  }

  public void setDescription(String description) {
      this.description = description;
  }

  public String getTask() {
      return task;
  }

  public void setTask(String task) {
      this.task = task;
  }  

  public int getCounter(){
    return counter;
  }

  @Override
  public String toString(){
      return this.task;
  }


}