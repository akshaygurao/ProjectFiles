
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummyproject;

/**
 *
 * @author aksha
 */
public class Location {
    private double[] currentLocation;

    public Location(double[] currentLocation) {
        this.currentLocation = currentLocation;
    }

    public double[] getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(double[] currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public String toString() {
        return "Location{" + "currentLocation=" + currentLocation + '}';
    }
    
    
    
}
