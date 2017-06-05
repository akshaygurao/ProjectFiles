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
public class QueueTimer extends TimerTask 
{
    Timer timer;
    Queue<MemoryBlock> memoryBlockQueue;
    public QueueTimer(Timer timer, Queue<MemoryBlock> memoryBlockQueue)
    {
        this.timer = timer;
        this.memoryBlockQueue = memoryBlockQueue;
    }
    @Override
    public void run() 
    {
        if(memoryBlockQueue.size() != 0)
        {
           System.out.println(memoryBlockQueue.remove() + ""); 
        }  
        if(memoryBlockQueue.size() == 0)
        {
            System.out.println("Memory blocks in Queue after pop: " + memoryBlockQueue);
            timer.cancel();
        }
       
    }
    
}
