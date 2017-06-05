/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hash;

/**
 *
 * @author aksha
 */
public class Hashing {
    public static int tableSize = 99997;
    int[] hashTable;
    double loadFactor;
    int keys;
    
    public Hashing() {
        hashTable = new int[tableSize];
        keys = 0;
        loadFactor = 0.0;
    }
    
    private void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }
    
    private int hash(int input) {
        return input % tableSize;
    }
    
    public double calculateLoadFactor() {
        loadFactor = ((double) keys) / tableSize;
        return loadFactor;
    }
    
    public boolean add(int input) {
        boolean success= false;
        if (loadFactor!=1) {
            int index = hash(input);
            if (hashTable[index] == 0) {
                hashTable[index] = input;
                keys++;
                
                if(calculateLoadFactor()>= 0.75F) 
                    increaseTableSize();
                
                return success = true;
            } else {
                keys++;
                collisionResolution(index, input);
                if(calculateLoadFactor()>=  0.75F) 
                    increaseTableSize();
        
                return success=false;
            }
        }
        
        return success;
    }
    
     private void collisionResolution(int index, int input) {
         //linearProbing(index, input);
         //quadraticProbing(index, input);
         doubleHashProbing(index, input);
    }
     
     private boolean linearProbing(int index, int input) {
        boolean flag = true;
        while (true) {
            if (hashTable.length > index && hashTable[index] == 0) {
                hashTable[index] = input;
                flag = true;
                break;
            }

            if (hashTable.length <= index) {
                flag = false;
                break;
            }

            index++;
        }

        return flag;
    }
     
     private boolean quadraticProbing(int index, int input) {
        boolean flag = true;
        int quadraticIndex = index;
        int i = 1;
        while (true) {
            if (hashTable[quadraticIndex] == 0 && hashTable.length > quadraticIndex) {
                hashTable[quadraticIndex] = input;
                flag = true;
                break;
            }

            if (hashTable.length <= quadraticIndex) {
                flag = false;
                break;
            }

            quadraticIndex = (index + i ^ 2) % tableSize;;
            i++;
        }
        return flag;
    }
     
     private boolean doubleHashProbing(int index, int input) {
        boolean flag = true;
        int stepSize = 11;
        while (true) {
            if (hashTable.length > index && hashTable[index] == 0) {
                hashTable[index] = input;
                flag = true;
                break;
            }

            if (hashTable.length <= index) {
                flag = false;
                break;
            }
            index = index + stepSize;
        }
        return flag;
    }
     
     private void increaseTableSize() {
        
        //hashTable = Arrays.copyOf(hashTable, hashTable.length + TABLE_INCREMENT_FACTOR);
        
        tableSize = hashTable.length + 10000;
        keys = 0;
        int [] oldArray = hashTable;
        hashTable = new int[tableSize];
        
        for(int i = 0 ; i < oldArray.length ; i++) {
            if(oldArray[i] != 0)
           add(oldArray[i]);
        }
        System.out.println("New Length:  :" + tableSize);
    }
     
     public int search(int key) {
        int index = -1;
        int hashedIndex = hash(key);
        while(hashTable[hashedIndex] != 0) {
            
            if(hashedIndex < hashTable.length && hashTable[hashedIndex] == key) {
                return hashedIndex;
            } 
            
            if(hashedIndex > hashTable.length)
                return -1;
            hashedIndex++;
        }
        return index;
        
    }
}
