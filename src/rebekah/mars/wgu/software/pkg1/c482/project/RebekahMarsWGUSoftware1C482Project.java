/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rebekah.mars.wgu.software.pkg1.c482.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHousePart;
import model.Inventory;
import model.OutsourcedPart;
import model.Part;
import model.Product;

/**
 *
 * @author owner
 */
public class RebekahMarsWGUSoftware1C482Project extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
      /*Add sample InHouse Part data*/
      Part examplepart1 = new InHousePart(1, "InHouse Part 1", 84, 2.00, 1, 100, 34);  
      Inventory.addPart(examplepart1);
      Part examplepart2 = new InHousePart(2, "InHouse Part 2", 18, 80.00, 1, 100, 15);  
      Inventory.addPart(examplepart2);
      Part examplepart3 = new InHousePart(3, "InHouse Part 3", 56, 93.00, 1, 100, 254);  
      Inventory.addPart(examplepart3);
      
      /*Add sample Outsourced Part data*/
      Part examplepart4 = new OutsourcedPart(4, "Outsourced Part 1", 12, 86.95, 1, 100, "Company 1");
      Inventory.addPart(examplepart4);
      Part examplepart5 = new OutsourcedPart(5, "Outsourced Part 2", 20, 98.90, 1, 100, "Company 2");
      Inventory.addPart(examplepart5);
      Part examplepart6 = new OutsourcedPart(5, "Outsourced Part 3", 34, 98.90, 1, 100, "Company 3");
      Inventory.addPart(examplepart6);
      
      /*Add sample Product data and associated Parts to Products created*/
      Product exampleproduct1 = new Product(1, "Product 1", 37, 500.00, 1, 50);
      Inventory.addProduct(exampleproduct1);
      exampleproduct1.addAssociatedPart(examplepart1);
      exampleproduct1.addAssociatedPart(examplepart2);
      Product exampleproduct2 = new Product(2, "Product 2", 45, 255.00, 1, 50);
      Inventory.addProduct(exampleproduct2);
      exampleproduct2.addAssociatedPart(examplepart3);
      exampleproduct2.addAssociatedPart(examplepart4);
      Product exampleproduct3 = new Product(3, "Product 3", 29, 90.00, 1, 50);
      Inventory.addProduct(exampleproduct3);
      exampleproduct3.addAssociatedPart(examplepart5);
      exampleproduct3.addAssociatedPart(examplepart6);
        
        launch(args);
    }
    
}
