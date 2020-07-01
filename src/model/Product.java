/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author owner
 */
public class Product {
    private int productID;
    private String productName;
    private double productPrice;
    private int productInventoryLevel;
    private int productMin;
    private int productMax;
    
    public ObservableList<Part> associatedPartsArray = FXCollections.observableArrayList();
    
    public Product(int productID, String productName, int productInventoryLevel, double productPrice, int productMin, int productMax) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInventoryLevel = productInventoryLevel;
        this.productMin = productMin;
        this.productMax = productMax;
    }
    
    public ObservableList<Part> getAssociatedPartsData()
    {
        return associatedPartsArray;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductInventoryLevel() {
        return productInventoryLevel;
    }

    public void setProductStock(int productInventoryLevel) {
        this.productInventoryLevel = productInventoryLevel;
    }

    public int getProductMin() {
        return productMin;
    }

    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }

    public int getProductMax() {
        return productMax;
    }

    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }
    
    public void addAssociatedPart(Part part)
    {
        associatedPartsArray.add(part);
    }
    public boolean deleteAssociatedPart (Part selectedAssociatedPart)
    {
        associatedPartsArray.remove(selectedAssociatedPart);
        return true;
    }
    

}
