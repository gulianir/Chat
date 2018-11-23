package tests.gitlab.ckpt1;

import java.util.Iterator;

import cse332.datastructures.containers.Item;
import datastructures.dictionaries.MoveToFrontList;
import datastructures.worklists.CircularArrayFIFOQueue;

public class Tests {
    
    protected static CircularArrayFIFOQueue<String> init() {
        return new CircularArrayFIFOQueue<String>(10);
    }

    public static void main(String[] args) {
        MoveToFrontList<String, Integer> list = new MoveToFrontList<>();

        list.insert("a", 1);
        list.insert("b", 2);
        
        System.out.println(list.size());
        Iterator<Item<String,Integer>> iter = list.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        
        System.out.println();
        
        list.find("a");
        
        Iterator<Item<String,Integer>> iter2 = list.iterator();
        while (iter2.hasNext()) {
            System.out.println(iter2.next());
        }
    }

}
