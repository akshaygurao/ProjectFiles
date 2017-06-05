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
public class Velocity {
    
    private double[] currentVelocity;

    public Velocity(double[] currentVelocity) {
        this.currentVelocity = currentVelocity;
    }

    public double[] getCurrentVelocity() {
        return currentVelocity;
    }

    public void setCurrentVelocity(double[] currentVelocity) {
        this.currentVelocity = currentVelocity;
    }

    @Override
    public String toString() {
        return "Velocity{" + "currentVelocity=" + currentVelocity + '}';
    }
      
    
}
