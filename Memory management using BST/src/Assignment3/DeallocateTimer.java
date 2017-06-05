/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment3;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Oza Sagar
 */
public class DeallocateTimer extends TimerTask
{
    Timer timer;
    Queue<MemoryBlock> memoryQueue;
    public DeallocateTimer(Timer timer, Queue<MemoryBlock> memoryQueue)
    {
        this.timer = timer;
        this.memoryQueue = memoryQueue;
    }
    
    @Override
    public void run() 
    {
        MemoryTree memoryTree = new MemoryTree();
        if(!memoryQueue.isEmpty())
        {
            MemoryBlock memoryBlock = memoryQueue.poll();
            System.out.println("Popped Block: " + memoryBlock);
            if(memoryBlock.isIsFree() == false)
            {
                //memoryBlock.setSize(memoryBlock.getRequestSize());
                MemoryTree.addDescendants(memoryBlock, memoryBlock.getRequestSize());
                MemoryTree.addSuccessors(memoryBlock, memoryBlock.getRequestSize());
            }
            //MemoryTree.printTree(MemoryPool.root);
        }
        if(memoryQueue.isEmpty())
        {
            System.out.println("Printing complete tree after DeAllocating all busy nodes: ");
            memoryTree.printTree(MemoryPool.root);
            timer.cancel();
        }
        
    }
    
}
