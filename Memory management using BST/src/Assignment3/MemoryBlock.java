/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment3;

/**
 *
 * @author jocel
 */
public class MemoryBlock 
{
    public MemoryBlock rightNode;
    public MemoryBlock leftNode;
    public MemoryBlock parent;
    public String status;
    public int blockValue;
    public boolean isFree;
    public int requestSize;
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRequestSize() {
        return requestSize;
    }

    public void setRequestSize(int requestSize) {
        this.requestSize = requestSize;
    }
    
    public int id;
    public int size;
    public static int count = 0;

    public int getBlockValue() {
        return blockValue;
    }

    public void setBlockValue(int blockValue) {
        this.blockValue = blockValue;
    }
    
   
    public MemoryBlock(int size)
     {
         this.leftNode = null;
         this.rightNode = null;
         this.size=size;
         
         this.blockValue = size;
         id = count;
        count++;
     }
    public MemoryBlock()
     {
         leftNode = null;
         rightNode = null;
        
     }
    public MemoryBlock getRightNode() {
        return rightNode;
    }

    public void setRightNode(MemoryBlock rightNode) {
        this.rightNode = rightNode;
    }

    public MemoryBlock getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(MemoryBlock leftNode) {
        this.leftNode = leftNode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MemoryBlock getParent() {
        return parent;
    }

    public void setParent(MemoryBlock parent) {
        this.parent = parent;
    }

    public boolean isIsFree() {
        return isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }

    @Override
    public String toString() {
        return "MemoryBlock{" + "blockValue=" + blockValue + ", isFree=" + isFree + ", requestSize=" + requestSize + ", id=" + id + ", size=" + size + '}';
    }
   
}
