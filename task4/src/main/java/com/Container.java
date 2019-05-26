package com;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Container {
    private int threshold;
    private List<Request> content;
    private long timeout;
    private int size;
    private int maxsize;
    public Container(int threshold,int timeout){
        this.threshold=threshold;
        this.timeout = timeout;
        this.size = 0;
        this.content = new ArrayList<Request>();
        this.maxsize = 100;
    }

    public synchronized void add(Request req) throws InterruptedException{
        clear_timeout();
        if(size>=maxsize){
            wait();
        }
        size++;
        content.add(req);
        System.out.println(Thread.currentThread().getName() + " send Request " + req.getValue()+", remain "+ size + " in container");
        notifyAll();
    }

    public synchronized Request remove() throws InterruptedException{
        clear_timeout();
        while(size<=0) {
            wait();
        }
        size--;
        Request req;
        if(size > threshold){
            req = content.remove(size);
            notifyAll();
            System.out.println("Remove from stack: "+Thread.currentThread().getName() + " receive Request " + req.getValue());
        }
        else{
            req = content.remove(0);
            notifyAll();
            System.out.println("Remove from queue: "+Thread.currentThread().getName() + " receive Request " + req.getValue());
        }
        return req;
    }

    public synchronized void clear_timeout(){
       for(Iterator it = content.iterator();it.hasNext();){
            Request req = (Request)it.next();
            if(!req.isAlive(timeout)){
                it.remove();
                size--;
                System.out.println("Request "+req.getValue()+" timeout.");
            }
        }
    }
}
