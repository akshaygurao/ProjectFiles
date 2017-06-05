/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hash;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author aksha
 */
public class HashTest {
    static Hashing hashtable = new Hashing();
    static Random random = new Random();
    public static int dataPointPer = 2;
    static int successful =0;
    static int collisions = 0;
    
    public static void main(String[] args){
        int length = Hashing.tableSize;
        long time = 0;
        double loadFactor = 0;
        double roundOff = 0;
        for(int i = 0; i < length; i++){
            collisions=0;
            long start = System.nanoTime();
            boolean result = hashtable.add(random.nextInt(10000) +10000);
            long stop = System.nanoTime();
            if(i%dataPointPer == 0){
            loadFactor = hashtable.calculateLoadFactor();
            roundOff = (double) Math.round(loadFactor * 100) / 100;
            time = stop - start;

        }
            if(result){
                successful++;
            } else {
                collisions++;
            }
            System.out.println("LoadFactor:"+roundOff + "  Collisions:" +collisions + "  Time:" +time);
    }
    }
        
}
