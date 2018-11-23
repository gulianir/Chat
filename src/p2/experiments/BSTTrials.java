package p2.experiments;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;

public class BSTTrials {

    public static void main(String[] args) {
        
        //hypothesis: AVL will run significantly faster than BST as N gets large
        final int NUM_TESTS = 10;
        final int NUM_WARMUP = 3;
        final int[] N_VALUES = {1000, 5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000, 
                55000, 60000, 65000, 70000, 75000, 80000, 85000, 90000, 95000, 100000};
        
        double totalTime = 0;
        System.out.println("AVL");
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
        
        
        totalTime = 0;
        
        System.out.println("BST");
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
        
        
    }
    
    public static void runTestBST(int n) {
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        //items are inserted in sorted order so BST is a linked list
        for (int i = 0; i < n; i++) {
            bst.insert(i, i);
        }
        
        bst.find(n);       
    }
    
    public static void runTestAVL(int n) {
        AVLTree<Integer, Integer> avl = new AVLTree<>();
        //items are inserted in sorted order so BST is a linked list
        for (int i = 0; i < n; i++) {
            avl.insert(i, i);
        }
        
        avl.find(n);
        
    }

}
