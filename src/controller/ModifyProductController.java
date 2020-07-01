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
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import static model.Inventory.getAllProducts;
import static model.Inventory.selectedProduct;
import model.Part;
import model.Product;

public class ModifyProductController implements Initializable {

    Product newModifyProduct;
    int newModifyProductIndex;
    @FXML private int productID;
    @FXML private String productName;
    @FXML private double productPrice;
    @FXML private int productInventoryLevel;
    @FXML private int productMin;
    @FXML private int productMax;
    @FXML private TextField productIDText;
    @FXML private TextField productNameText;    
    @FXML private TextField productPriceText;
    @FXML private TextField productInventoryLevelText;
    @FXML private TextField productMinText;
    @FXML private TextField productMaxText;
    @FXML private TextField productModifySearchBarText;
    @FXML private TableView<Part> partTableview;
    @FXML private TableView<Part> associatedPartsTableview;
    @FXML private TableColumn <Part, Integer> associatedPartIDColumn;
    @FXML private TableColumn <Part, String> associatedPartNameColumn;
    @FXML private TableColumn <Part, Integer> associatedPartInventoryLevelColumn;
    @FXML private TableColumn <Part, Double> associatedPartPriceColumn;
    @FXML private TableColumn <Part, Integer> partIDColumn;
    @FXML private TableColumn <Part, String> partNameColumn;
    @FXML private TableColumn <Part, Integer> partInventoryLevelColumn;
    @FXML private TableColumn <Part, Double> partPriceColumn;
    @FXML private Button updateProductButton;
      
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ObservableList<Part> associatedPartsArray = FXCollections.observableArrayList();
    
    @FXML
    public void sendProduct (Product modifyProduct)
    {
        newModifyProduct = modifyProduct;
        productIDText.setText(String.valueOf(newModifyProduct.getProductID()));
        productNameText.setText(newModifyProduct.getProductName());
        productInventoryLevelText.setText(String.valueOf(newModifyProduct.getProductInventoryLevel()));
        productPriceText.setText(String.valueOf(newModifyProduct.getProductPrice()));
        productMinText.setText(String.valueOf(newModifyProduct.getProductMin()));
        productMaxText.setText(String.valueOf(newModifyProduct.getProductMax()));
      
        for(Part part: newModifyProduct.getAssociatedPartsData())
        {
            associatedPartsArray.add(part);
        }
        associatedPartsTableview.setItems(newModifyProduct.associatedPartsArray);        
    }

    @FXML
    private void addParttoProductList (ActionEvent event)
    {
        boolean repeat = false;
        
        Part newPartToAdd = partTableview.getSelectionModel().getSelectedItem();
        for(int index = 0; index < newModifyProduct.getAssociatedPartsData().size(); index++)
        {
            if(newModifyProduct.getAssociatedPartsData().contains(newPartToAdd))
            {
                repeat = true;
            }
        }
        
        if(repeat == true)
        {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle("ERROR");
            newAlert.setHeaderText("Part Already Added!");
            newAlert.setContentText(newModifyProduct.getProductName() + " already contains " + newPartToAdd.getPartName() + "!");
            newAlert.showAndWait();
        }
        else
        {   
            newModifyProduct.associatedPartsArray.add(newPartToAdd);
            associatedPartsTableview.setItems(newModifyProduct.associatedPartsArray);
        }

    }
   @FXML
   private void updateProduct(ActionEvent event) throws IOException 
   {
     int error = 0;
     
     try
     {
        productID = Integer.parseInt(productIDText.getText());
        productName = productNameText.getText();
        productInventoryLevel = Integer.parseInt(productInventoryLevelText.getText());
        productPrice = Double.parseDouble(productPriceText.getText());
        productMin = Integer.parseInt(productMinText.getText());
        productMax = Integer.parseInt(productMaxText.getText());
     
        if(newModifyProduct.getAssociatedPartsData().isEmpty())
        {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle("ERROR");
            newAlert.setHeaderText("Product Missing Associated Parts");
            newAlert.setContentText(newModifyProduct.getProductName() + " does not contain any Parts!");
            newAlert.showAndWait();
            error = 1;
        }
        if(productMin > productMax)
        {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle("ERROR: IVALID FORMAT");
            newAlert.setHeaderText("Cannot Update Product");
            newAlert.setContentText("Product Min must be less than Product Max");
            newAlert.showAndWait();
            error = 1;
        }
        if(productPrice < 0)
        {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle("ERROR: IVALID FORMAT");
            newAlert.setHeaderText("Cannot Update Product");
            newAlert.setContentText("Product Price must have a price greater than 0.");
            newAlert.showAndWait();
            error = 1;
        }
        if(productInventoryLevel < productMin || productInventoryLevel > productMax)
        {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle("ERROR: IVALID FORMAT");
            newAlert.setHeaderText("Cannot Update Product");
            newAlert.setContentText("Product Inventory Level must be between the Product's Min and Max values.");
            newAlert.showAndWait();
            error = 1;
        }
        if(productName.isEmpty())
        {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle("ERROR: IVALID FORMAT");
            newAlert.setHeaderText("Cannot Update Product");
            newAlert.setContentText("Product Name must be entered");
            newAlert.showAndWait();
            error = 1;
        }
        else if (error == 0)
        {
            int modifyProductID = Integer.parseInt(productIDText.getText());
            String modifyProductName = productNameText.getText();
            int modifyProductInventoryLevel = Integer.parseInt(productInventoryLevelText.getText());
            double modifyProductPrice = Double.parseDouble(productPriceText.getText());
            int modifyProductMin = Integer.parseInt(productMinText.getText());
            int modifyProductMax = Integer.parseInt(productMaxText.getText());
        
            Product newProduct = new Product(modifyProductID, modifyProductName, modifyProductInventoryLevel, modifyProductPrice, modifyProductMin, modifyProductMax);
            newProduct.setProductID(modifyProductID);
            newProduct.setProductName(modifyProductName);
            newProduct.setProductStock(modifyProductInventoryLevel);
            newProduct.setProductPrice(modifyProductPrice);
            newProduct.setProductMin(modifyProductMin);
            newProduct.setProductMax(modifyProductMax);
            newProduct.associatedPartsArray = associatedPartsTableview.getItems();
        
            for (Product allProducts : Inventory.getAllProducts()) 
            {
                newModifyProductIndex = Inventory.getAllProducts().indexOf(newModifyProduct);
            }
        
            Inventory.updateProduct(newProduct, newModifyProductIndex);
      
            Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Scene canceltoMenuScreen = new Scene(mainMenuParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(canceltoMenuScreen);
            window.show();
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
   @FXML private void searchModifyProducts (ActionEvent event) throws IOException
  {
       String partAttribute = productModifySearchBarText.getText();        
       ObservableList<Part> searchPartsResults = searchParts(partAttribute);
       SortedList<Part> sortedParts = new SortedList<>(searchPartsResults);
       sortedParts.comparatorProperty().bind(partTableview.comparatorProperty());
      
       if(sortedParts.isEmpty())
        {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle("ERROR");
            newAlert.setHeaderText("No Parts Found!");
            newAlert.setContentText("No parts were found containing the ID you entered.");
            newAlert.showAndWait();           
        }
       else
       {
            partTableview.setItems(sortedParts);          
       }
       
       ObservableList<Part> associatedPartsResults = searchAssociatedParts(partAttribute);
       SortedList<Part> sortedAssociatedParts = new SortedList<>(associatedPartsResults);
       sortedAssociatedParts.comparatorProperty().bind(associatedPartsTableview.comparatorProperty());
       if(sortedAssociatedParts.isEmpty())
        {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle("ERROR");
            newAlert.setHeaderText("No Associated Parts Found!");
            newAlert.setContentText("No associated parts were found containing the ID you entered.");
            newAlert.showAndWait();
            
        }
       else
       {
           associatedPartsTableview.setItems(sortedAssociatedParts);           
       }
       
  }
    @FXML public ObservableList<Part> searchParts (String partNameText)
    {
        return Inventory.allParts.filtered(a-> a.getPartName().toLowerCase().contains(partNameText.toLowerCase()));
    }
  @FXML public ObservableList<Part> searchAssociatedParts(String partNameText)
    {
       return newModifyProduct.associatedPartsArray.filtered(a-> a.getPartName().toLowerCase().contains(partNameText.toLowerCase()));
    }
   @FXML
    private void cancelScreen (ActionEvent event) throws IOException //cancels and exits to Main Menu screen
    {
        Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
        newAlert.initModality(Modality.NONE);
        newAlert.setTitle("CONFIRM CANCEL MODIFY PRODUCT");
        newAlert.setHeaderText("Confirm Delete");
        newAlert.setContentText("Are you sure you want to cancel modifying " + newModifyProduct.getProductName() + "?");
        Optional<ButtonType> deletePartResult = newAlert.showAndWait();
        
        if(deletePartResult.get() == ButtonType.OK)
        {
            Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Scene canceltoMenuScreen = new Scene(mainMenuParent);
       
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(canceltoMenuScreen);
            window.show();          
        }  
    }
    @FXML
    private void deletePartFromProduct (ActionEvent event)throws IOException //doesn't work?
    {
        Part removedPart = associatedPartsTableview.getSelectionModel().getSelectedItem();
        
        if(removedPart == null)
        {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle("ERROR");
            newAlert.setHeaderText("Delete Associated Parts Error");
            newAlert.setContentText("You cannot delete an Associated Part without selecting one from the table first!");
            newAlert.showAndWait(); 
        }
        else
        {
            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
            newAlert.initModality(Modality.NONE);
            newAlert.setTitle("CONFIRM DELETE OF PRODUCT");
            newAlert.setHeaderText("Confirm Cancel");
            newAlert.setContentText("Are you sure you want to delete " + removedPart.getPartName()+"?");
            Optional<ButtonType> deletePartResult = newAlert.showAndWait();
        
            if(deletePartResult.get() == ButtonType.OK)
            {
                newModifyProduct.associatedPartsArray.remove(removedPart);
                associatedPartsTableview.setItems(newModifyProduct.associatedPartsArray);   
            }  
        }
               
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //newModifyProduct = MainMenuController.
        partIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartID()).asObject());
        partNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPartName()));
        partInventoryLevelColumn.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getPartInventoryLevel()).asObject());
        partPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPartPrice()).asObject());
        partTableview.setItems(Inventory.allParts);
        
               
        associatedPartIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartID()).asObject());
        associatedPartNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPartName()));
        associatedPartInventoryLevelColumn.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getPartInventoryLevel()).asObject());
        associatedPartPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPartPrice()).asObject());
        associatedPartsTableview.setItems(associatedPartsArray);
   
    }    

}
