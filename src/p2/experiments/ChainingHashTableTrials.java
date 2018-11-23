package p2.experiments;

import java.util.function.Supplier;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.Dictionary;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;

public class ChainingHashTableTrials {
    
    private static Supplier<Dictionary<Integer,Integer>> sup1 = ()-> new MoveToFrontList<Integer,Integer>();
    
    private static Supplier<Dictionary<Integer,Integer>> sup2 = ()-> new BinarySearchTree<Integer,Integer>();
    
    private static Supplier<Dictionary<Integer,Integer>> sup3 = ()-> new AVLTree<Integer,Integer>();

    public static void main(String[] args) {
        
        final int NUM_TESTS = 10;
        final int NUM_WARMUP = 3;
        final int[] N_VALUES = {1000, 5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000, 
                55000, 60000, 65000, 70000, 75000, 80000, 85000, 90000, 95000, 100000};
        
        //larger N values to try later:  
        
        System.out.println("MTF");
        double totalTime = 0;
        for (int n = 0; n < N_VALUES.length; n++) {
            for (int i = 0; i < NUM_TESTS; i++) {
                long startTime = System.currentTimeMillis();
                runTestMTF(N_VALUES[n]);
                long endTime = System.currentTimeMillis();
                if (NUM_WARMUP <= i) {
                    totalTime += (endTime - startTime);
                }
            }

            double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUP);
            System.out.println(averageRuntime);
        }
        
        System.out.println("BST");
        totalTime = 0;
        for (int n = 0; n < N_VALUES.length; n++) {
            for (int i = 0; i < NUM_TESTS; i++) {
                long startTime = System.currentTimeMillis();
                runTestBST(N_VALUES[n]);
                long endTime = System.currentTimeMillis();
                if (NUM_WARMUP <= i) {
                    totalTime += (endTime - startTime);
                }
            }

            double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUP);
            System.out.println(averageRuntime);
        }
        
        System.out.println("AVL");
        totalTime = 0;
        for (int n = 0; n < N_VALUES.length; n++) {
            for (int i = 0; i < NUM_TESTS; i++) {
                long startTime = System.currentTimeMillis();
                runTestAVL(N_VALUES[n]);
                long endTime = System.currentTimeMillis();
                if (NUM_WARMUP <= i) {
                    totalTime += (endTime - startTime);
                }
            }

            double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUP);
            System.out.println(averageRuntime);
        }
         
        
    }
    
    public static void runTestMTF(int n) {
        ChainingHashTable<Integer,Integer> mtf = new ChainingHashTable<Integer,Integer>(sup1);
        for (int i = 0; i < n; i++) {
            Integer num = n + ((-1)^i * i);
            mtf.insert(num, num);
        }
        
        for (int i = 0; i < n; i++) {
            //find something there half the time, find something not there half the time
            if (i % 2 == 0) {
                Integer num = n + ((-1)^i * i);
                mtf.find(num);
            } else {
                mtf.find(-1000);
            }
        }
    }
    
    public static void runTestBST(int n) {
        ChainingHashTable<Integer,Integer> bst = new ChainingHashTable<Integer,Integer>(sup2);
        for (int i = 0; i < n; i++) {
            //alternates between adding numbers greater and smaller than n
            Integer num = n + ((-1)^i * i);
            bst.insert(num, num);
        }
        
        for (int i = 0; i < n; i++) {
            //find something there half the time, find something not there half the time
            if (i % 2 == 0) {
                Integer num = n + ((-1)^i * i);
                bst.find(num);
            } else {
                bst.find(-1000);
            }
        }
    }
    
    public static void runTestAVL(int n) {
        ChainingHashTable<Integer,Integer> avl = new ChainingHashTable<Integer,Integer>(sup3);
        for (int i = 0; i < n; i++) {
            Integer num = n + ((-1)^i * i);
            avl.insert(num, num);
        }
        
        for (int i = 0; i < n; i++) {
            //find something there half the time, find something not there half the time
            if (i % 2 == 0) {
                Integer num = n + ((-1)^i * i);
                avl.find(num);
            } else {
                avl.find(-1000);
            }
        }
    }
    
    

}
