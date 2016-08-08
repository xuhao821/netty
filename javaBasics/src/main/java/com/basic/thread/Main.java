package com.basic.thread;

/**
 * Created by Ian on 2016/8/6.
 */
public class Main extends Thread{
     public void run(){
         super.run();
         System.out.println("MyThread");
     }
    public static void main(String[] args){
        Main main = new Main();
             main.start();
    }
}
