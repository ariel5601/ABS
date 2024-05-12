package body.tables.customer;

import body.bodyCustomerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class addSceneController {
    @FXML
    private TextField amountToAddField;
    private int amountToAdd;
    private bodyCustomerController parentController;
    @FXML
    private Label errorLabel;
    public addSceneController(){

    }

    @FXML
    private Button chooseButton;

    @FXML
    private void initialize(){

    }

    @FXML
    void chooseButtonAction(ActionEvent event) {
        try {
            amountToAdd = Integer.parseInt(amountToAddField.getText());
            Stage stage = (Stage) chooseButton.getScene().getWindow();

            stage.close();
        }catch (NumberFormatException e){
            errorLabel.setText("error please choose an integer number");
            errorLabel.setVisible(true);
        }

    }
    public int getAmountToAdd(){
        return this.amountToAdd;
    }

}

