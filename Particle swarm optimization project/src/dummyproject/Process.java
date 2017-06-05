/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummyproject;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author aksha
 */
public class Process {

    public int SWARM_SIZE = 50;
    public int PROBLEM_DIMENSION = 2;
    public static int LOC_X_LOW = -5;
    public static int LOC_X_HIGH = 5;
    public static int LOC_Y_LOW = -5;
    public static int LOC_Y_HIGH = 5;
    public Vector<Bots> botSwarm = new Vector<>();
    public Vector<Victims> victimSwarm = new Vector<>();
    Random generator = new Random();
    public double[] pBest = new double[SWARM_SIZE];
    public double gBest = 0;
    public double LOC_X_TARGET = 2.0;
    public double LOC_Y_TARGET = 5.0;

    public void initializeSwarm() {
        Bots b;

        for (int i = 0; i < SWARM_SIZE; i++) {
            b = new Bots();
            // randomize location inside a space defined in Problem Set
            double[] loc = new double[PROBLEM_DIMENSION];
            loc[0] = LOC_X_LOW + generator.nextFloat() * (LOC_X_HIGH - LOC_X_LOW);
            loc[1] = LOC_Y_LOW + generator.nextFloat() * (LOC_Y_HIGH - LOC_Y_LOW);
            Location location = new Location(loc);

            // randomize location inside a space defined in Problem Set
            double[] vel = new double[PROBLEM_DIMENSION];
            vel[0] = LOC_X_LOW + generator.nextFloat() * (LOC_X_HIGH - LOC_X_LOW);
            vel[1] = LOC_Y_LOW + generator.nextFloat() * (LOC_Y_HIGH - LOC_Y_LOW);
            Velocity velocity = new Velocity(vel);
            b.setVelocity(velocity);
            b.setLocation(location);
            botSwarm.add(b);
        }
    }
    
    public void initializeVictims() {
        Victims v;

        for (int i = 0; i < SWARM_SIZE; i++) {
            v = new Victims();
            // randomize location inside a space defined in Problem Set
            double[] loc = new double[PROBLEM_DIMENSION];
            loc[0] = LOC_X_LOW + generator.nextFloat() * (LOC_X_HIGH - LOC_X_LOW);
            loc[1] = LOC_Y_LOW + generator.nextFloat() * (LOC_Y_HIGH - LOC_Y_LOW);
            Location location = new Location(loc);
            v.setLocation(location);
            victimSwarm.add(v);
        }
    }

    public Vector<Bots> getSwarm() {
        return botSwarm;
    }
    
    public Vector<Victims> getVictimSwarm() {
        return victimSwarm;
    }

    public void printSwarm() {
        for (Bots b : botSwarm) {
            System.out.println("X coordinate of location: " + b.getLocation().getCurrentLocation()[0] + "Y coordinate of location: " + b.getLocation().getCurrentLocation()[1] + "Velocity x coord: " + b.getVelocity().getCurrentVelocity()[0] + "Velocity of y coord: " + b.getVelocity().getCurrentVelocity()[1]);
        }
    }

    public double[] getpBest() {
        int j = 0;
        for (Bots b : getSwarm()) {
            double[] loc = b.getLocation().getCurrentLocation();
            double distance = Math.sqrt(Math.pow((LOC_X_TARGET - loc[0]), 2) + Math.pow((LOC_Y_TARGET - loc[1]), 2));
            float dist = (float) distance;
            pBest[j] = dist;
            j++;
        }

        return pBest;
    }

    public void execute() {
        double w_upper = 0.9;
        double w_lower = 0.4;
        double C1 = 1, C2 = 1;
        double w = 0.5;
        while ((checkArray(pBest)) != 0) {
            for (Bots b : getSwarm()) {

                //double r1 = generator.nextDouble();
                //double r2 = generator.nextDouble();
                double[] newLoc = new double[PROBLEM_DIMENSION];
                newLoc[0] = b.getLocation().getCurrentLocation()[0] + b.getVelocity().getCurrentVelocity()[0];
                newLoc[1] = b.getLocation().getCurrentLocation()[1] + b.getVelocity().getCurrentVelocity()[1];
                Location location = new Location(newLoc);
                b.setLocation(location);

                //System.out.println(maxValue + "  " + minValue);
                double[] newVel = new double[PROBLEM_DIMENSION];
                //newVel[0] = (b.getVelocity().getCurrentVelocity()[0]) *(minValue/maxValue);
                //newVel[1] = (b.getVelocity().getCurrentVelocity()[1])* (minValue/maxValue);
                newVel[0] = ((LOC_X_TARGET - b.getLocation().getCurrentLocation()[0]) / 1.2) - b.getLocation().getCurrentLocation()[0];
                newVel[1] = ((LOC_Y_TARGET - b.getLocation().getCurrentLocation()[1]) / 1.2) - b.getLocation().getCurrentLocation()[1];
                Velocity velocity = new Velocity(newVel);
                b.setVelocity(velocity);

                //System.out.println("Gbest "+gBest);
                //break;
            }
            double maxValue = getMax(pBest);
            double minValue = getMin(pBest);
            pBest = getpBest();
            System.out.println("new");
            printArray(pBest);
            gBest = minValue;
        }
        System.out.println("final");
        printArray(pBest);
    }

    public int checkArray(double[] temp) {
        int count = 0;

        for (int i = 0; i < temp.length; i++) {

            if (temp[i] < 3 && temp[i] > 1) {
                //System.out.println("temp "+temp[i]);
                count++;
            }
        }
        if (count == temp.length - 1) {

            return 0;
        } else {
            return 1;
        }
    }

    public void printArray(double[] pBest) {
        for (int i = 0; i < pBest.length; i++) {
            System.out.println(Math.abs(pBest[i] - 2));
        }
    }

    public double getMax(double[] pBest) {
        Arrays.sort(pBest);
        //System.out.println(pBest[pBest.length - 1]);
        return pBest[pBest.length - 1];
    }

    public double getMin(double[] pBest) {
        Arrays.sort(pBest);
        //System.out.println(pBest[0]);
        return pBest[0];
    }

//    public void printpBest() {
//        for (int i = 0; i < pBest.length; i++) {
//            System.out.println(pBest[i]);
//        }
//        getMin(pBest);
//        getMax(pBest);
//    }
}
