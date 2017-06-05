 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment3;

import static Assignment3.MemoryPool.allocatedCountendTime;
import static Assignment3.MemoryPool.allocatedCountstartTime;
import static java.time.Clock.system;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author jocel
 */
public class MemoryTree {

    public MemoryBlock root;
    public MemoryBlock current;
    public static int failedCount;
    public static double failedCountStartTime;
    public static double failedCountEndTime;
    public static double allocatedCountstartTime;
    public static double allocatedCountendTime;
    

    public static Queue<MemoryBlock> memoryBlockQueue = new LinkedList<MemoryBlock>();
    
    public void insertNode(MemoryBlock node) 
    {

        current = node;

        while (checkLastNode(node).getSize() != 1) 
        {
            if (current.getSize() != 1) {
                MemoryBlock leftNode = new MemoryBlock(current.getSize() / 2);
                MemoryBlock rightNode = new MemoryBlock(current.getSize() / 2);

                current.setLeftNode(leftNode);
                current.setRightNode(rightNode);
                leftNode.setParent(current);
                rightNode.setParent(current);
                insertNode(leftNode);
                insertNode(rightNode);
            }
        }

    }

    public MemoryBlock checkLastNode(MemoryBlock node) 
    {
        current = node;
        while (current.getLeftNode() != null) {
            current = current.getLeftNode();
        }
        return current;
    }

    public void displayInorderTree(MemoryBlock node) 
    {
        if (node == null) 
        {
            return;
        }
        
        displayInorderTree(node.getLeftNode());
        System.out.print(node.getSize() + " " );
        displayInorderTree(node.getRightNode());

    }
    
 

   

    public int generateRequest(int n) {

        int request = (int) (Math.random() * n);
        request = (int) Math.pow(2, request);
        return request;
    }

    public void requestMemory(MemoryBlock root, int requestSize) 
    {
        if(requestSize > root.getSize())
        {
            failedCountStartTime=System.nanoTime();
            failedCount++;
            System.out.println("Memory can't be allocatedfor size " + requestSize);
            failedCountEndTime=System.nanoTime();
            return;
        }
        if(root.getSize() == requestSize) 
        {
            allocatedCountstartTime=System.nanoTime();
            memoryBlockQueue.add(root);
            root.setSize(root.getSize()- requestSize);
            root.setRequestSize(requestSize);
            setDescendantSize(root, requestSize);
            setSuccessorSize(root, requestSize);
            allocatedCountendTime=System.nanoTime();
           
        }
        else if(requestSize <= root.getLeftNode().getSize())
        {
             
            requestMemory(root.getLeftNode(), requestSize);
            
        }
        else if(requestSize <= root.getRightNode().getSize())
        {
            
            requestMemory(root.getRightNode(), requestSize);
          
        }
         
           
    }

    private void setDescendantSize(MemoryBlock root, int requestSize) 
    {
        if(root.getLeftNode() != null)
        {
            root.setSize(0);
            setDescendantSize(root.getLeftNode(), requestSize);
        }
        if(root.getRightNode() != null)
        {
            root.setSize(0);
            setDescendantSize(root.getRightNode(), requestSize);
        }
        if( root.getLeftNode() == null && root.getRightNode() == null)
        {
            root.setSize(0);
        }
    }

    private void setSuccessorSize(MemoryBlock root, int requestSize) 
    {       
        if (root.getParent() != null) 
        {
            root.getParent().setSize(root.getParent().getSize() - requestSize);
            setSuccessorSize(root.getParent(), requestSize);
        } 

    }
    
    static void addDescendants(MemoryBlock memoryBlock, int newSize) 
    {
        if(memoryBlock.getLeftNode() != null)
        {
            if (memoryBlock.getLeftNode().getBlockValue() != memoryBlock.getLeftNode().getSize()) 
            {
                if(memoryBlock.getBlockValue() != memoryBlock.getSize())
                {
                    memoryBlock.setSize(memoryBlock.getSize() + newSize);
                    memoryBlock.setIsFree(true);
                }
                printTree(MemoryPool.root);
                if(memoryBlock.getParent() == null)
                {
                    addDescendants(memoryBlock.getLeftNode(), newSize);
                }
                else
                {
                    addDescendants(memoryBlock.getLeftNode(), memoryBlock.getSize() / 2);
                }
                if(memoryBlock.getSize() > memoryBlock.getBlockValue())
                {
                    memoryBlock.setSize(memoryBlock.getBlockValue());
                }

            }
                   }
        if(memoryBlock.getRightNode() != null)
        {
            if (memoryBlock.getRightNode().getBlockValue() != memoryBlock.getRightNode().getSize()) 
            {
                if(memoryBlock.getBlockValue() != memoryBlock.getSize())
                {
                    memoryBlock.setSize(memoryBlock.getSize() + newSize);
                    memoryBlock.setIsFree(true);
                }
                //printTree(MemoryPool.root);
                if(memoryBlock.getParent() == null)
                {
                    addDescendants(memoryBlock.getRightNode(), newSize);
                }
                else 
                {
                   addDescendants(memoryBlock.getRightNode(), memoryBlock.getSize() / 2); 
                }
                
                if(memoryBlock.getSize() > memoryBlock.getBlockValue())
                {
                    memoryBlock.setSize(memoryBlock.getBlockValue());
                }
            }
            
        }
        if(memoryBlock.getLeftNode() == null && memoryBlock.getRightNode() == null)
        {
            if(memoryBlock.getBlockValue() != memoryBlock.getSize())
            {
                memoryBlock.setSize(newSize);
                memoryBlock.setIsFree(true);
            }
            if(memoryBlock.getSize() > memoryBlock.getBlockValue())
            {
                memoryBlock.setSize(memoryBlock.getBlockValue());
            }
            
        }
        
    }
    
    static void addSuccessors(MemoryBlock memoryBlock, int requestSize) 
    {
        if (memoryBlock.getParent() != null)
        {
            memoryBlock.getParent().setSize(memoryBlock.getParent().getSize() + requestSize);
            addSuccessors(memoryBlock.getParent(), requestSize);
        } 
    }

    
    public static void printTree(MemoryBlock node) 
    {

       Queue<MemoryBlock> currentLevel = new LinkedList<MemoryBlock>();
       Queue<MemoryBlock> nextLevel = new LinkedList<MemoryBlock>();

       currentLevel.add(node);

       while (!currentLevel.isEmpty()) {
           Iterator<MemoryBlock> iter = currentLevel.iterator();
           while (iter.hasNext()) {
               MemoryBlock current = iter.next();
               if (current.getLeftNode() != null) {
                   nextLevel.add(current.getLeftNode());
               }
               if (current.getRightNode() != null) {
                   nextLevel.add(current.getRightNode());
               }
               if (!current.isIsFree()) {
                   System.out.print("-" + current.getSize() + "-");
               } else {
                 
                   System.out.print(current.getSize() + " ");
               }
           }
           System.out.println();
           currentLevel = nextLevel;
           nextLevel = new LinkedList<MemoryBlock>();

       }

   }
    
    

}
