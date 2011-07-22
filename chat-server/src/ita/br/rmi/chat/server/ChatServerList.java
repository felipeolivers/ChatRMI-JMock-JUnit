package ita.br.rmi.chat.server;

import ita.br.rmi.chat.common.INotify;

import java.util.ArrayList;
import java.util.Collection;

public class ChatServerList {   
    
    private Collection<INotify> threadList = new ArrayList<INotify>();   
    private int counter = 0;   
      
    // Add Item
    public synchronized void add(INotify item) {   
        try {   
        	// Wait
            while (this.counter > 0) { wait(); }   
            threadList.add(item);   
        } catch (InterruptedException e) {   
            e.printStackTrace();   
        } finally{   
            notifyAll();   
        }   
    }   
       
    // Remove Item   
    public synchronized void remove(INotify item) {   
        try {   
        	// Wait
            while (this.counter > 0) { wait(); }   
            threadList.remove(item);   
        } catch (InterruptedException e) {   
            e.printStackTrace();   
        } finally {   
            notifyAll();   
        }   
    }   
       
    // Counters   
    public synchronized void incCounter() {   
        this.counter++;   
        notifyAll();   
    }   
       
    public synchronized void decCounter() {   
        this.counter--;   
        notifyAll();   
    }   
       
    // Collection to Iterator 
    public Collection<?> getCollection() { return this.threadList; }
    
}  
