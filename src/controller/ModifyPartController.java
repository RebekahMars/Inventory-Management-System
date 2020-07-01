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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.InHousePart;
import model.Inventory;
import static model.Inventory.selectedPart;
import model.OutsourcedPart;
import model.Part;

/**
 * FXML Controller class
 *
 * @author owner
 */
public class ModifyPartController implements Initializable {

    @FXML private TextField partIDText;
    @FXML private TextField partNameText;
    @FXML private TextField partInventoryLevelText;
    @FXML private TextField partPriceText;
    @FXML private TextField partMinText;
    @FXML private TextField partMaxText;
    @FXML private TextField partTextField;
    @FXML private RadioButton partInHouseButton;
    @FXML private RadioButton partOutsourcedButton;
    @FXML private Button savePartButton;
    @FXML private TableView<Part> partTableview;
    @FXML private Label partLabel;
    @FXML private Part newPart;
    @FXML private int newPartIndex;

    
    @FXML
    public void selectInHousePart (ActionEvent event) throws IOException
    {
       partLabel.setText("Machine ID"); 
       partOutsourcedButton.setSelected(false);
    }
    @FXML 
    public void selectOutsourcedPart(ActionEvent event) throws IOException
    {
        partLabel.setText("Company Name");
        partInHouseButton.setSelected(false);
    }
   
    @FXML
    public void sendPart (Part selectedPart)
    {
        if(selectedPart instanceof InHousePart)
        {
            partLabel.setText("Machine ID");
            partInHouseButton.setSelected(true);
            
            InHousePart modifiedPart = (InHousePart) selectedPart;
            partIDText.setText(String.valueOf(modifiedPart.getPartID()));
            partNameText.setText(modifiedPart.getPartName());
            partInventoryLevelText.setText(String.valueOf(modifiedPart.getPartInventoryLevel()));
            partPriceText.setText(String.valueOf(modifiedPart.getPartPrice()));
            partMinText.setText(String.valueOf(modifiedPart.getPartMin()));
            partMaxText.setText(String.valueOf(modifiedPart.getPartMax()));
            partTextField.setText(String.valueOf(modifiedPart.getPartMachineID())); 
        }
        else
        {
            partLabel.setText("Company Name");
            partOutsourcedButton.setSelected(true);
            
            OutsourcedPart modifiedPart = (OutsourcedPart) selectedPart;
            partIDText.setText(String.valueOf(modifiedPart.getPartID()));
            partNameText.setText(modifiedPart.getPartName());
            partInventoryLevelText.setText(String.valueOf(modifiedPart.getPartInventoryLevel()));
            partPriceText.setText(String.valueOf(modifiedPart.getPartPrice()));
            partMinText.setText(String.valueOf(modifiedPart.getPartMin()));
            partMaxText.setText(String.valueOf(modifiedPart.getPartMax()));
            partTextField.setText(String.valueOf(modifiedPart.getPartOutsourcedCompanyName())); //null??
                     
        }
    }
    
    @FXML
    private void cancelScreen (ActionEvent event) throws IOException
    {
        Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
        newAlert.initModality(Modality.NONE);
        newAlert.setTitle("CONFIRM CANCEL ADD PART");
        newAlert.setHeaderText("Confirm Delete");
        newAlert.setContentText("Are you sure you want to cancel modifying the Part?");
        Optional<ButtonType> cancelModifyPartResult = newAlert.showAndWait();
        
        if(cancelModifyPartResult.get() == ButtonType.OK)
        {
            Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Scene canceltoMenuScreen = new Scene(mainMenuParent);
       
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(canceltoMenuScreen);
            window.show();
        }
    } 
    @FXML
    private void updatePart(ActionEvent event)throws IOException
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
     
            if(partMin > partMax)
            {
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
                newAlert.setTitle("ERROR: IVALID FORMAT");
                newAlert.setHeaderText("Cannot Modify Part");
                newAlert.setContentText("Part Min must be less than Part Max");
                newAlert.showAndWait();
                error = 1;
            }
            if(partPrice < 0)
            {
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
                newAlert.setTitle("ERROR: IVALID FORMAT");
                newAlert.setHeaderText("Cannot Modify Part");
                newAlert.setContentText("Part Price must have a price greater than 0.");
                newAlert.showAndWait();
                error = 1;
            }
            if(partInventoryLevel < partMin || partInventoryLevel > partMax)
            {
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
                newAlert.setTitle("ERROR: IVALID FORMAT");
                newAlert.setHeaderText("Cannot Modify Part");
                newAlert.setContentText("Part Inventory Level must be between the Part's Min and Max values.");
                newAlert.showAndWait();
                error = 1;
            }
        if(partName.isEmpty())
            {
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
                newAlert.setTitle("ERROR: IVALID FORMAT");
                newAlert.setHeaderText("Cannot Modify Part");
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
                    newAlert.setHeaderText("Cannot Modify Part");
                    newAlert.setContentText("Part Machine ID must be greater than 0.");
                    newAlert.showAndWait();
                    error = 1;                   
                }
            
                else if (error == 0)
                {
                    InHousePart newPart = new InHousePart(partID, partName, partInventoryLevel, partPrice, partMin, partMax, partMachineID);
            
                    newPart.setPartID(partID);
                    newPart.setPartName(partName);
                    newPart.setPartInventoryLevel(partInventoryLevel);
                    newPart.setPartPrice(partPrice);
                    newPart.setPartMin(partMin);
                    newPart.setPartMax(partMax);
                    newPart.setPartMachineID(partMachineID);
                    for(int index = 0; index < Inventory.getAllParts().size(); index++)
                    {
                        newPartIndex = Inventory.getAllParts().indexOf(selectedPart);
                    }
                
                    Inventory.updatePart(newPart, newPartIndex);
                    
                    Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Scene toMenuScreen = new Scene(mainMenuParent);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(toMenuScreen);
                    window.show();
            }
            
        }
        else if(partOutsourcedButton.isSelected())
        {
            String partCompanyName = partTextField.getText();
            
            if(partCompanyName.isEmpty())
                {             
                    Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
                    newAlert.setTitle("ERROR: IVALID FORMAT");
                    newAlert.setHeaderText("Cannot Modify Part");
                    newAlert.setContentText("Part Company Name must be entered.");
                    newAlert.showAndWait();
                    error = 1;
                    
                }
            else if (error == 0)
            {
                OutsourcedPart newPart = new OutsourcedPart(partID, partName, partInventoryLevel, partPrice, partMin, partMax, partCompanyName);
                newPart.setPartID(partID);
                newPart.setPartName(partName);
                newPart.setPartInventoryLevel(partInventoryLevel);
                newPart.setPartPrice(partPrice);
                newPart.setPartMin(partMin);
                newPart.setPartMax(partMax);
                newPart.setPartOutsourcedCompanyName(partCompanyName);
                for(int index = 0; index < Inventory.getAllParts().size(); index++)
                {
                    newPartIndex = Inventory.getAllParts().indexOf(selectedPart);
                }
                
                Inventory.updatePart(newPart, newPartIndex);
                
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
            newAlert.setHeaderText("Cannot Update Product");
            newAlert.setContentText("Invalid format.");
            newAlert.showAndWait();
            error = 1; 
        }
     
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
       
    }
    
}
