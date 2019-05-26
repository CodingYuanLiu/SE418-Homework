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
