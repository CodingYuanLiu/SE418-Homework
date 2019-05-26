package com;

public class Request {
    private int value;
    private long time;
    public int getValue(){
        return  this.value;
    }
    public long getTime(){
        return this.time;
    }
    public void setValue(int value){
        this.value = value;
    }
    public void setTime(long time){
        this.time = time;
    }
    public boolean isAlive(long timeout){
        long now=System.currentTimeMillis();
        long diff = now - time;
        return diff<timeout;
    }
    public Request(int value,long time){
        this.value = value;
        this.time = time;
    }
    public Request(int value){
        this.value = value;
        this.time = System.currentTimeMillis();
    }
}
