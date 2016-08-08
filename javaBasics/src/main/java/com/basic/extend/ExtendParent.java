package com.basic.extend;

/**
 * Created by Ian on 2016/8/6.
 */
public class ExtendParent extends Parent{
    @Override
    public void abc(){
        System.out.println("World");
    }

    public static void main(String[] args){
        System.out.println(Thread.currentThread().getName());
    }

}
