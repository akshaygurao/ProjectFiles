/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment3;

import static Assignment3.MemoryTree.allocatedCountstartTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;

/**
 *
 * @author jocel
 */
public class MemoryPool {

    public static MemoryBlock root;
    public static int availableMemory;
    public static double totalElapsedTime;
    //public static double elapsedTime;
    public static double failedCountTime;
    public static double totalFailedCountTime;
    
    public static double allocatedCountTime;
    public static double totalallocatedCountTime;
    
    public static double allocatedCountstartTime;
    public static double allocatedCountendTime;
    
    
    
    public static void main(String[] args) {
        MemoryTree tree = new MemoryTree();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> requestMemoryCountsList = new ArrayList<Integer>();
        HashMap<Integer,Integer> requestMap = new HashMap<Integer,Integer>();
        
        int blockSize=0;
        System.out.println("Please enter the size of the tree: ");
        try
        {
            blockSize = scanner.nextInt();
            root = new MemoryBlock((int) Math.pow(2, blockSize));
            availableMemory = root.getSize();
            tree.insertNode(root);
            System.out.println("Printing tree InOrder form: ");
            //tree.displayInorderTree(root);
            MemoryTree.printTree(root);

        }
        catch(Exception ex)
        {
            System.out.println("Please enter valid numbers!!!");
        }
        
        
        for(int i=1; i<=100; i++)
        {
            System.out.println("");
            System.out.println("Iteration number: " + i);
            
            int requestedMemoryRange = tree.generateRequest(blockSize/5); //20 percent
            System.out.println("Requesting memory of size " + requestedMemoryRange + " : ");
            
            if(!requestMemoryCountsList.contains(requestedMemoryRange))
            {
                int count = 0;
                requestMemoryCountsList.add(requestedMemoryRange);
                requestMap.put(requestedMemoryRange, ++count);
            }
            else
            {
                int counter = requestMap.get(requestedMemoryRange);
                requestMap.put(requestedMemoryRange, ++counter);
            }
            
           
            tree.requestMemory(root, requestedMemoryRange);
            
            MemoryTree.printTree(root);
            failedCountTime=MemoryTree.failedCountEndTime-MemoryTree.failedCountStartTime;
            System.out.println("Failed Count time:" + failedCountTime + "nanoseconds\n");
            allocatedCountTime=MemoryTree.allocatedCountendTime-MemoryTree.allocatedCountstartTime;
            System.out.println("Allocated Count time:" + allocatedCountTime + "nanoseconds\n");
            
            System.out.println("");
            System.out.println("");
        }
        int allocatedCount = 100-MemoryTree.failedCount;
        System.out.println("Failed Counts : " + MemoryTree.failedCount); 
        System.out.println("Allocated  Count : " +allocatedCount); 
        
        totalFailedCountTime=totalFailedCountTime+failedCountTime;
        System.out.println("Total Failed Count Time : " + totalFailedCountTime );
        
        
        totalallocatedCountTime=totalallocatedCountTime+allocatedCountTime;
        System.out.println("Total Allocated Count Time : " + totalallocatedCountTime );
        
        
        totalElapsedTime=((allocatedCount*totalallocatedCountTime)+(100-allocatedCount)*totalFailedCountTime)/100;
        System.out.println("Total Elapsed Time : " +totalElapsedTime);
        //double averageElapsedTime=totalElapsedTime/100;
        //System.out.println("Total Elapsed Time : " +averageElapsedTime);
        
        
        
       
        Iterator it = requestMap.entrySet().iterator();
        while (it.hasNext()) 
        {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println("Memory Size " + pair.getKey() + " = " + pair.getValue() + " requests");
            it.remove(); // avoids a ConcurrentModificationException
        }

        System.out.println("Initial Memory: " + availableMemory + "KB");
        System.out.println("Memory Left: " + root.getSize() + "KB");
        System.out.println("");
        System.out.println("Memory blocks in Queue: " + MemoryTree.memoryBlockQueue);
        
        Timer timer = new Timer();
//        timer.schedule(new QueueTimer(timer, MemoryTree.memoryBlockQueue), 0, 1000);

//        System.out.println("Memory blocks in Queue: " + MemoryTree.memoryBlockQueue);
        
//        Timer timer = new Timer();
//        timer.schedule(new QueueTimer(timer), 0, 1000);
//        if(MemoryTree.memoryBlockQueue.isEmpty())
//        {
//            timer.cancel();
//        }

        timer.schedule(new DeallocateTimer(timer, MemoryTree.memoryBlockQueue), 0, 2000);
        if(MemoryTree.memoryBlockQueue.isEmpty())
        {
            MemoryTree memoryTree = new MemoryTree();
            MemoryTree.printTree(MemoryPool.root);
            timer.cancel();
        }

    }

    
}
