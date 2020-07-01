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
public class OutsourcedPart extends Part{
    
    private String partOutsourcedCompanyName;

    public OutsourcedPart(int partID, String partName, int partInventoryLevel, double partPrice, int partMin, int partMax, String partOutsourcedCompanyName) {
        super(partID, partName, partInventoryLevel, partPrice, partMin, partMax);
        this.partOutsourcedCompanyName = partOutsourcedCompanyName;
    }

  
    public String getPartOutsourcedCompanyName() {
        return partOutsourcedCompanyName;
    }

    public void setPartOutsourcedCompanyName(String partOutsourcedCompanyName) {
        this.partOutsourcedCompanyName = partOutsourcedCompanyName;
    }
    
    
}
