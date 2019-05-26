package com;

import java.util.Random;

public class Consumer extends Thread{
    private final Container container;
    private final Random random;
    public Consumer(String name,Container container,long seed){
        super(name);
        this.container=container;
        this.random = new Random(seed);
    }

    public void run(){
        try{
            while(true){
                Request req = container.remove();
                Thread.sleep(random.nextInt(1000));
            }
        }
        catch (InterruptedException e){

        }
    }
}
