/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;


/**
 *
 * @author owner
 */
public class Inventory {
    
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static Part selectedPart;
    public static Product selectedProduct;
    
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }
    public static void addInHousePart (InHousePart newPart)
    {
        allParts.add(newPart);
    }
    public static void addProduct (Product newProduct)
    {
        allProducts.add(newProduct);
    }
    
    public ObservableList<Part> lookupParts(String partNameText)
    {
       return allParts.filtered(a-> a.getPartName().toLowerCase().contains(partNameText.toLowerCase()));
    }
    public ObservableList<Product> lookupProducts (String productNameText)
    {
        return allProducts.filtered(a-> a.getProductName().toLowerCase().contains(productNameText.toLowerCase()));
    }
    
    public static void updatePart(Part selectedPart, int index)
    {
        
        allParts.set(index, selectedPart);
    }
    
    public static void updateProduct (Product selectedProduct, int index)
    {
        allProducts.set(index, selectedProduct);
    }
    public static boolean deletePart (Part selectedPart)
    {
        allParts.remove(selectedPart);
        return true;
    }
    public static boolean deleteProduct (Product selectedProduct)
    {     
        allProducts.remove(selectedProduct);
        return true;
    }

    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }
       
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}
