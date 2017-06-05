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
public class Bots {
    private Location location;
    private Velocity velocity;
    private double target;

    public Bots(Location location, Velocity velocity, double target) {
        this.location = location;
        this.velocity = velocity;
        this.target = target;
    }

    public Bots() {
    }
    
    

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Bots{" + "location=" + location + ", velocity=" + velocity + ", target=" + target + '}';
    }
    
    
}
