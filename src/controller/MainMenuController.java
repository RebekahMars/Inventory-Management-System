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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import model.InHousePart;
import static model.Inventory.selectedPart;
import static model.Inventory.selectedProduct;


//Rebekah Mars WGU Software 1 Project
public class MainMenuController implements Initializable 
{
 
    @FXML private Label partsLabel;
    @FXML private Label productsLabel;
    @FXML private Button exitButton;        
    @FXML private Button addPartsButton;
    @FXML private Button modifyPartsButton;
    @FXML private Button deletePartsButton;
    @FXML private Button searchPartsButton;
    @FXML private Button searchProductsButton;
    @FXML private Button addProductsButton;
    @FXML private Button modifyProductsButton;
    @FXML private Button deleteProductsButton;
    @FXML private TableColumn <Part, Integer> partIDColumn;
    @FXML private TableColumn <Part, String> partNameColumn;
    @FXML private TableColumn <Part, Integer> partInventoryLevelColumn;
    @FXML private TableColumn <Part, Double> partPriceColumn;
    @FXML private TableColumn <Product, Integer> productIDColumn;
    @FXML private TableColumn <Product, String> productNameColumn;
    @FXML private TableColumn <Product, Integer> productInventoryLevelColumn;
    @FXML private TableColumn <Product, Double> productPriceColumn;
    @FXML private TableView<Part> partTableview;
    @FXML private TableView<Product> productTableview;
    @FXML private TextField partIDText;
    @FXML private TextField productIDText;

    @FXML public void searchPartsTest (ActionEvent event) throws IOException
    {
        String partAttribute = partIDText.getText();
        ObservableList<Part> searchPartsResults = lookupParts(partAttribute);
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

    }
   
    @FXML public void searchProductsTest (ActionEvent event) throws IOException
    {
        String productAttribute = productIDText.getText();        
        ObservableList<Product> searchProductsResults = lookupProducts(productAttribute);
        SortedList<Product> sortedProducts = new SortedList<>(searchProductsResults);
        sortedProducts.comparatorProperty().bind(productTableview.comparatorProperty());
        
        if(sortedProducts.isEmpty())
        {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle("ERROR");
            newAlert.setHeaderText("No Products Found!");
            newAlert.setContentText("No products were found containing the ID you entered.");
            newAlert.showAndWait();
            
        }
        else
        {
            productTableview.setItems(sortedProducts);
        }
    }
    public ObservableList<Part> lookupParts(String partNameText)
    {
       return Inventory.allParts.filtered(a-> a.getPartName().toLowerCase().contains(partNameText.toLowerCase()));
    }
    public ObservableList<Product> lookupProducts (String productNameText)
    {
        return Inventory.allProducts.filtered(a-> a.getProductName().toLowerCase().contains(productNameText.toLowerCase()));
    }
    

    @FXML
    private void addPartScreen (ActionEvent event) throws IOException
    {
       Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
       Scene addPartInHouseScene = new Scene(mainMenuParent);
       
       Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(addPartInHouseScene);
       window.setResizable(false);
       window.show();
    } 
    @FXML
    private void modifyPartScreen (ActionEvent event) throws IOException
    {     
        FXMLLoader loader = new FXMLLoader(); 
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        Parent parent = loader.load();
        Scene modifyPartScene = new Scene(parent);
        ModifyPartController controller = loader.getController();
        
        Inventory.selectedPart = partTableview.getSelectionModel().getSelectedItem();
        
        if(selectedPart == null)
        {
           Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
           newAlert.setTitle("ERROR");
           newAlert.setHeaderText("Modify Part Error");
           newAlert.setContentText("You cannot modify a Part without selecting one from the table first!");
           newAlert.showAndWait();
        }
        else
        {
            controller.sendPart(selectedPart);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyPartScene);
            window.setResizable(false);
            window.show();
        }   
       
    }
    @FXML
    private void addProductScreen (ActionEvent event) throws IOException
    {
       Parent addProductParent = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
       Scene addNewProductScreen = new Scene(addProductParent);
       
       Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(addNewProductScreen);
       window.setResizable(false);
       window.show();
    }
     @FXML
    private void modifyProductScreen (ActionEvent event) throws IOException
    {
       
       Product modifyProduct = productTableview.getSelectionModel().getSelectedItem();
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
       Parent parent = loader.load();
       Scene modifyProductScreen = new Scene(parent);
       ModifyProductController controller = loader.getController();
      
       if(modifyProduct == null)
       {
           Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
           newAlert.setTitle("ERROR");
           newAlert.setHeaderText("Modify Product Error");
           newAlert.setContentText("You cannot modify a Product without selecting one from the table first!");
           newAlert.showAndWait();
       }
       else
       {
           controller.sendProduct(modifyProduct);
           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
           window.setScene(modifyProductScreen);
           window.setResizable(false);
           window.show();
       }
 
    }
   
    @FXML
    private void exitApplicationButton (ActionEvent event) throws IOException
    {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
    
    @FXML
    private void deletePart(ActionEvent event) throws IOException
    {
        Part selectPart = partTableview.getSelectionModel().getSelectedItem();
        
        if(selectPart == null)
        {
           Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
           newAlert.setTitle("ERROR");
           newAlert.setHeaderText("Delete Part Error");
           newAlert.setContentText("You cannot delete a Part without selecting one from the table first!");
           newAlert.showAndWait(); 
        }
        
        else
        {
            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
            newAlert.initModality(Modality.NONE);
            newAlert.setTitle("CONFIRM DELETE OF PART");
            newAlert.setHeaderText("Confirm Delete");
            newAlert.setContentText("Are you sure you want to delete " + selectPart.getPartName()+"?");
            Optional<ButtonType> deletePartResult = newAlert.showAndWait();
        
            if(deletePartResult.get() == ButtonType.OK)
            {
                Inventory.allParts.remove(selectPart);
            }
        }
    }
    @FXML
    private void deleteProduct (ActionEvent event) throws IOException
    {
        Product selectProduct = productTableview.getSelectionModel().getSelectedItem();
        
        if(selectProduct == null)
        {
           Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
           newAlert.setTitle("ERROR");
           newAlert.setHeaderText("Delete Product Error");
           newAlert.setContentText("You cannot delete a Product without selecting one from the table first!");
           newAlert.showAndWait(); 
        }
 
        else
        {
            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
            newAlert.initModality(Modality.NONE);
            newAlert.setTitle("CONFIRM DELETE OF PRODUCT");
            newAlert.setHeaderText("Confirm Delete");
            newAlert.setContentText("Are you sure you want to delete " + selectProduct.getProductName()+"?");
            Optional<ButtonType> deleteProductResult = newAlert.showAndWait();
        
            if(deleteProductResult.get() == ButtonType.OK)
            {
                Inventory.allProducts.remove(selectProduct);
            }
        }
    }
   
       @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       partTableview.setItems(Inventory.getAllParts());
       
       partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
       partNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
       partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory <>("partInventoryLevel"));
       partPriceColumn.setCellValueFactory(new PropertyValueFactory <>("partPrice"));
       
       productTableview.setItems(Inventory.getAllProducts());
       
       productIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductID()).asObject());
       productNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
       productInventoryLevelColumn.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getProductInventoryLevel()).asObject());
       productPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProductPrice()).asObject());
       
       productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
       productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
       productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory <>("productInventoryLevel"));
       productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
       
       
       
    }  

}

