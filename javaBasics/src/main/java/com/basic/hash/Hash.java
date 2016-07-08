package com.basic.hash;

import java.util.Hashtable;

/**
 * Created by Ian on 2016/7/5.
 */
public class Hash {
    public static void main(String[] args) {
        Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
        numbers.put("one", 1);
        numbers.put("two", 2);
        numbers.put("three", 3);
        numbers.put("four", 4);
        numbers.put("five", 5);

        Integer n = numbers.get("two");
        Integer nn = numbers.get("six");
        System.out.println(numbers.containsKey("three"));
        if(n != null)
            System.out.println(n);
        System.out.println(nn);
    }
}
