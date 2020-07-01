/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


public class InHousePart extends Part {
   
    private int partMachineID;

    public InHousePart(int partID, String partName, int partInventoryLevel, double partPrice, int partMin, int partMax, int partMachineID) {
        super(partID, partName, partInventoryLevel, partPrice, partMin, partMax);
        this.partMachineID = partMachineID;
    }

    public int getPartMachineID() {
        return partMachineID;
    }

    public void setPartMachineID(int partMachineID) {
        this.partMachineID = partMachineID;
    }
    
    
}


