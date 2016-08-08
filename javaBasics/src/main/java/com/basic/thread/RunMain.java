package com.basic.thread;

/**
 * Created by Ian on 2016/8/6.
 */
public class RunMain {
    public static void main(String[] args){
        Thread thread=new Thread(new TheThread());
        thread.start();
    }
}

class TheThread implements Runnable{
    public void run() {
        System.out.println("hello");
    }
}
