/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.InHousePart;
import model.Inventory;
import model.OutsourcedPart;

/**
 * FXML Controller class
 *
 * @author rebek
 */
public class AddPartController implements Initializable {

    @FXML
    private Label partTextLabel;
    @FXML
    private RadioButton partInHouseButton;
    @FXML 
    private RadioButton partOutsourcedButton;
    @FXML
    private ToggleGroup InHousePart;
    @FXML
    private ToggleGroup OutsourcedPart;
    @FXML
    private TextField partIDText;
    @FXML
    private TextField partNameText;
    @FXML
    private TextField partInventoryLevelText;
    @FXML
    private TextField partPriceText;
    @FXML
    private TextField partMinText;
    @FXML
    private TextField partMaxText;
    @FXML
    private TextField partTextField;
    @FXML
    private Button savePartInHouseButton;

    @FXML
    public void selectInHousePart (ActionEvent event) throws IOException
    {
       partTextLabel.setText("Machine ID"); 
       partInHouseButton.setSelected(true);
       partOutsourcedButton.setSelected(false);
    }
    @FXML 
    public void selectOutsourcedPart(ActionEvent event) throws IOException
    {
        partTextLabel.setText("Company Name");
        partInHouseButton.setSelected(false);
        partOutsourcedButton.setSelected(true);
    }
    @FXML
    private void saveNewPart(ActionEvent event) throws IOException
    {
        int error = 0;
        
        try
        {
            int partID = Integer.parseInt(partIDText.getText());
            String partName = partNameText.getText();
            int partInventoryLevel = Integer.parseInt(partInventoryLevelText.getText());
            double partPrice = Double.parseDouble(partPriceText.getText());
            int partMin = Integer.parseInt(partMinText.getText());
            int partMax = Integer.parseInt(partMaxText.getText());
            
            if(!(partInHouseButton.isSelected() || partOutsourcedButton.isSelected()))
            {
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
                newAlert.setTitle("ERROR: IVALID FORMAT");
                newAlert.setHeaderText("Cannot Add Part");
                newAlert.setContentText("You must select either the InHouse button or Outsourced button.");
                newAlert.showAndWait();
                error = 1;
            }
            if(partMin > partMax)
            {
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
                newAlert.setTitle("ERROR: IVALID FORMAT");
                newAlert.setHeaderText("Cannot Add Part");
                newAlert.setContentText("Part Min must be less than Part Max");
                newAlert.showAndWait();
                error = 1;
            }
            if(partPrice < 0)
            {
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
                newAlert.setTitle("ERROR: IVALID FORMAT");
                newAlert.setHeaderText("Cannot Add Part");
                newAlert.setContentText("Part Price must have a price greater than 0.");
                newAlert.showAndWait();
                error = 1;
            }
            if(partInventoryLevel < partMin || partInventoryLevel > partMax)
            {
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
                newAlert.setTitle("ERROR: IVALID FORMAT");
                newAlert.setHeaderText("Cannot Add Part");
                newAlert.setContentText("Part Inventory Level must be between the Part's Min and Max values.");
                newAlert.showAndWait();
                error = 1;
            }
            if(partName.isEmpty())
            {
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
                newAlert.setTitle("ERROR: IVALID FORMAT");
                newAlert.setHeaderText("Cannot Add Part");
                newAlert.setContentText("Part Name must be entered");
                newAlert.showAndWait();
                error = 1;
            }         
            if(partInHouseButton.isSelected())
            {
                int partMachineID = Integer.parseInt(partTextField.getText());
                if(partMachineID < 0)
                {             
                    Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
                    newAlert.setTitle("ERROR: IVALID FORMAT");
                    newAlert.setHeaderText("Cannot Add Part");
                    newAlert.setContentText("Part Machine ID must be greater than 0.");
                    newAlert.showAndWait();
                    error = 1;
                    
                }
                if(error == 0)
                {
                    Inventory.addPart(new InHousePart(partID, partName, partInventoryLevel, partPrice, partMin, partMax, partMachineID));
                    Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Scene toMenuScreen = new Scene(mainMenuParent);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(toMenuScreen);
                    window.show(); 
                    
                }
            }
            if(partOutsourcedButton.isSelected())
            {
                String partCompanyName = partTextField.getText();
                if(partCompanyName.isEmpty())
                {             
                    Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
                    newAlert.setTitle("ERROR: IVALID FORMAT");
                    newAlert.setHeaderText("Cannot Add Part");
                    newAlert.setContentText("Part Company Name must be entered.");
                    newAlert.showAndWait();
                    error = 1;
                    
                }
                if(error == 0)
                {
                    Inventory.addPart(new OutsourcedPart(partID, partName, partInventoryLevel, partPrice, partMin, partMax, partCompanyName));
                    Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Scene toMenuScreen = new Scene(mainMenuParent);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(toMenuScreen);
                    window.show(); 
                }
            }
            
        }
        catch(NumberFormatException e)
        {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle("ERROR: IVALID FORMAT");
            newAlert.setHeaderText("Cannot Add Part");
            newAlert.setContentText("Invalid format.");
            newAlert.showAndWait();
            error = 1;      
        }

    }
    @FXML
    private void cancelScreen(ActionEvent event) throws IOException
    {
        Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
        newAlert.initModality(Modality.NONE);
        newAlert.setTitle("CONFIRM CANCEL ADD PART");
        newAlert.setHeaderText("Confirm Delete");
        newAlert.setContentText("Are you sure you want to cancel adding a new Part?");
        Optional<ButtonType> cancelAddPartResult = newAlert.showAndWait();
        
        if(cancelAddPartResult.get() == ButtonType.OK)
        {
            Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Scene canceltoMenuScreen = new Scene(mainMenuParent);
       
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(canceltoMenuScreen);
            window.show();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       partIDText.setText("Part ID"); 
       partNameText.setText("Part Name");
       partInventoryLevelText.setText("Part Inventory Level");
       partPriceText.setText("Part Price");
       partMinText.setText("Part Min");
       partMaxText.setText("Part Max");
    }    
    
}
