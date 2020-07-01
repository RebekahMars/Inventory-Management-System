/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author owner
 */
public abstract class Part {
  
    private int partID;
    private String partName;
    private int partInventoryLevel;
    private double partPrice;
    private int partMin;
    private int partMax;

    public Part(int partID, String partName, int partInventoryLevel, double partPrice, int partMin, int partMax) {
        this.partID = partID;
        this.partName = partName;
        this.partInventoryLevel = partInventoryLevel;
        this.partPrice = partPrice;
        this.partMin = partMin;
        this.partMax = partMax;
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getPartInventoryLevel() {
        return partInventoryLevel;
    }

    public void setPartInventoryLevel(int partInventoryLevel) {
        this.partInventoryLevel = partInventoryLevel;
    }

    public double getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }
    
    public int getPartMin() {
        return partMin;
    }

    public void setPartMin(int partMin) {
        this.partMin = partMin;
    }

    public int getPartMax() {
        return partMax;
    }

    public void setPartMax(int partMax) {
        this.partMax = partMax;
    }
    
}
