package com;

import java.util.Random;

public class Producer extends Thread{
    private final Container container;
    private static int id=0;
    private final Random random;
    public Producer(String name,Container container,long seed){
        super(name);
        this.container = container;
        this.random = new Random(seed);
    }

    public static synchronized int nextId(){
        return id++;
    }
    public void run(){
        try{
            while(true){
                Thread.sleep(random.nextInt(800));
                Request req = new Request(nextId());
                container.add(req);
            }
        }
        catch (InterruptedException e){

        }

    }
}
