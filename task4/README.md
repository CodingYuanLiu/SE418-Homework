# Task 4: Implement a Request Container
## Model 
The request container is expected to be used in the Producer-Consumer model, where producers send requests and stored in the container, and consumers take requests out of the container and handle it.
## Features
### Maxsize and Minsize
If the number of requests in the container is more than the maxsize, producers will wait until a request is removed. If the container is empty, consumers will wait until a request is added to the container. The default maxsize of the container is 100, and the default minsize is 0 apparently.

### Threshold
If the number of requests in the container is less than the threshold, then the container acts like a queue. Else, it acts like a stack

### Timeout
If the time that a request staying in the container is longer than timeout, we discard it.

## Code
### Request
``` java
/* Request.java */

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

```
### Container
``` java
/* Container.java */

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

```

### Producer
``` java
/* Producer.java */

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

```
### Consumer
``` java
/* Consumer.java */

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

```

## Test
2 producer threads are created and 2 consumer threads are created. We set the threshold=5 and timeout = 2000ms. What's more, each producer is expected to produce a request every 800ms and each consumer is expected to consume a request every 1000ms.
> We produce requests faster so that the remaining of the container is likely to ascending.
``` java
/* App.java */

package com;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Container container = new Container(5,2000);

        new Producer("Producer 1",container,System.currentTimeMillis()).start();
        new Producer("Producer 2",container,System.currentTimeMillis()).start();
        new Consumer("Consumer 1",container,System.currentTimeMillis()).start();
        new Consumer("Consumer 2",container,System.currentTimeMillis()).start();

    }
}

```

The output is as follows (one possible output):

``` bash
Producer 1 send Request 0, remain 1 in container
Remove from queue: Consumer 1 receive Request 0
Producer 1 send Request 1, remain 1 in container
Remove from queue: Consumer 2 receive Request 1
Producer 2 send Request 2, remain 1 in container
Producer 2 send Request 3, remain 2 in container
Producer 2 send Request 4, remain 3 in container
Remove from queue: Consumer 1 receive Request 2
Producer 1 send Request 5, remain 3 in container
Remove from queue: Consumer 2 receive Request 3
Producer 2 send Request 6, remain 3 in container
Producer 1 send Request 7, remain 4 in container
Producer 2 send Request 8, remain 5 in container
Remove from queue: Consumer 1 receive Request 4
Producer 1 send Request 9, remain 5 in container
Producer 2 send Request 10, remain 6 in container
Remove from queue: Consumer 2 receive Request 5
Producer 2 send Request 11, remain 6 in container
Producer 2 send Request 12, remain 7 in container
Producer 1 send Request 13, remain 8 in container
Producer 2 send Request 14, remain 9 in container
Remove from stack: Consumer 1 receive Request 14
Remove from stack: Consumer 2 receive Request 13
Remove from stack: Consumer 1 receive Request 12
Producer 2 send Request 15, remain 7 in container
Request 6 timeout.
Request 7 timeout.
Producer 1 send Request 16, remain 6 in container
Remove from queue: Consumer 2 receive Request 8
Remove from queue: Consumer 1 receive Request 9
Remove from queue: Consumer 2 receive Request 10
Producer 1 send Request 17, remain 4 in container
Producer 2 send Request 18, remain 5 in container
Producer 1 send Request 19, remain 6 in container
Remove from queue: Consumer 1 receive Request 11
Remove from queue: Consumer 1 receive Request 15
Remove from queue: Consumer 2 receive Request 16
Remove from queue: Consumer 2 receive Request 17
Producer 2 send Request 20, remain 3 in container
Producer 1 send Request 21, remain 4 in container
Producer 2 send Request 22, remain 5 in container
Producer 1 send Request 23, remain 6 in container
Remove from queue: Consumer 1 receive Request 18
Remove from queue: Consumer 1 receive Request 19
Producer 2 send Request 24, remain 5 in container
Producer 1 send Request 25, remain 6 in container
Remove from queue: Consumer 2 receive Request 20
Remove from queue: Consumer 2 receive Request 21
Producer 1 send Request 26, remain 5 in container
Producer 1 send Request 27, remain 6 in container
Remove from queue: Consumer 1 receive Request 22
Remove from queue: Consumer 2 receive Request 23
Producer 2 send Request 28, remain 5 in container
Producer 2 send Request 29, remain 6 in container
Producer 1 send Request 30, remain 7 in container
Producer 2 send Request 31, remain 8 in container
Remove from stack: Consumer 1 receive Request 31
Remove from stack: Consumer 2 receive Request 30
Producer 1 send Request 32, remain 7 in container
Producer 2 send Request 33, remain 8 in container
Remove from stack: Consumer 1 receive Request 33
Producer 1 send Request 34, remain 8 in container
Producer 1 send Request 35, remain 9 in container
Request 24 timeout.
Request 25 timeout.
Producer 2 send Request 36, remain 8 in container
Remove from stack: Consumer 2 receive Request 36
Request 26 timeout.
Request 27 timeout.
Producer 1 send Request 37, remain 6 in container
Producer 1 send Request 38, remain 7 in container
Producer 2 send Request 39, remain 8 in container
Producer 2 send Request 40, remain 9 in container
Request 28 timeout.
Producer 1 send Request 41, remain 9 in container
Remove from stack: Consumer 1 receive Request 41
Producer 2 send Request 42, remain 9 in container
Producer 2 send Request 43, remain 10 in container
Remove from stack: Consumer 1 receive Request 43
Request 29 timeout.
Remove from stack: Consumer 2 receive Request 42
Remove from stack: Consumer 2 receive Request 40
Producer 1 send Request 44, remain 7 in container
Producer 1 send Request 45, remain 8 in container
Producer 1 send Request 46, remain 9 in container
Producer 2 send Request 47, remain 10 in container
Request 32 timeout.
Producer 2 send Request 48, remain 10 in container
Remove from stack: Consumer 1 receive Request 48
Producer 1 send Request 49, remain 10 in container
Remove from stack: Consumer 2 receive Request 49
Request 34 timeout.
Request 35 timeout.
Producer 1 send Request 50, remain 8 in container
Producer 1 send Request 51, remain 9 in container
Producer 2 send Request 52, remain 10 in container
Producer 1 send Request 53, remain 11 in container
Producer 2 send Request 54, remain 12 in container
Request 37 timeout.
Request 38 timeout.
Request 39 timeout.
Remove from stack: Consumer 1 receive Request 54
Remove from stack: Consumer 1 receive Request 53
Producer 1 send Request 55, remain 8 in container
Remove from stack: Consumer 2 receive Request 55
Producer 1 send Request 56, remain 8 in container
Producer 2 send Request 57, remain 9 in container
Producer 2 send Request 58, remain 10 in container
Remove from stack: Consumer 2 receive Request 58
Request 44 timeout.
Request 45 timeout.
Remove from stack: Consumer 1 receive Request 57
Producer 2 send Request 59, remain 7 in container
Request 46 timeout.
Request 47 timeout.
Producer 1 send Request 60, remain 6 in container
Remove from queue: Consumer 2 receive Request 50
Producer 2 send Request 61, remain 6 in container
Producer 1 send Request 62, remain 7 in container
Remove from stack: Consumer 1 receive Request 62
Producer 1 send Request 63, remain 7 in container
Request 51 timeout.
Producer 2 send Request 64, remain 7 in container
Request 52 timeout.
Remove from queue: Consumer 2 receive Request 56
Producer 2 send Request 65, remain 6 in container
Producer 1 send Request 66, remain 7 in container
Producer 1 send Request 67, remain 8 in container
Remove from stack: Consumer 1 receive Request 67
Remove from stack: Consumer 2 receive Request 66
Producer 2 send Request 68, remain 7 in container
Request 59 timeout.
Request 60 timeout.
Producer 1 send Request 69, remain 6 in container
Request 61 timeout.
Producer 2 send Request 70, remain 6 in container
Remove from queue: Consumer 1 receive Request 63
Producer 1 send Request 71, remain 6 in container
Producer 1 send Request 72, remain 7 in container
Remove from stack: Consumer 2 receive Request 72
Producer 2 send Request 73, remain 7 in container
Producer 2 send Request 74, remain 8 in container
Request 64 timeout.
Producer 2 send Request 75, remain 8 in container
Producer 1 send Request 76, remain 9 in container
Producer 2 send Request 77, remain 10 in container
Producer 2 send Request 78, remain 11 in container
Remove from stack: Consumer 1 receive Request 78
Request 65 timeout.
Remove from stack: Consumer 1 receive Request 77
Remove from stack: Consumer 2 receive Request 76
Remove from stack: Consumer 1 receive Request 75
Remove from queue: Consumer 2 receive Request 68
Producer 1 send Request 79, remain 6 in container
Remove from queue: Consumer 2 receive Request 69
Producer 2 send Request 80, remain 6 in container
Producer 1 send Request 81, remain 7 in container
Request 70 timeout.
Request 71 timeout.
Remove from queue: Consumer 1 receive Request 73
Producer 2 send Request 82, remain 5 in container
Producer 1 send Request 83, remain 6 in container
Remove from queue: Consumer 2 receive Request 74
Remove from queue: Consumer 1 receive Request 79
Producer 1 send Request 84, remain 5 in container
Producer 2 send Request 85, remain 6 in container
Remove from queue: Consumer 2 receive Request 80
Producer 2 send Request 86, remain 6 in container
Producer 2 send Request 87, remain 7 in container
Producer 1 send Request 88, remain 8 in container
Remove from stack: Consumer 1 receive Request 88
Remove from stack: Consumer 2 receive Request 87
Producer 1 send Request 89, remain 7 in container
Producer 2 send Request 90, remain 8 in container
```