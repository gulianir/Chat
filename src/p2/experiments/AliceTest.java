package p2.experiments;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import datastructures.dictionaries.MoveToFrontList;

public class AliceTest {

    public static void main(String[] args) throws FileNotFoundException {
        // BST, AVL, CHAINING HASH, HASHTRIE

        //sSystem.out.println("BST");

        long startTime = System.currentTimeMillis();
        //extra test to make sure counts of the word "alice" are all the same
        //System.out.println(runTestBST());
        runTestBST();
        long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);
        
        

        //System.out.println("AVL");

        startTime = System.currentTimeMillis();
        //System.out.println(runTestAVL());
        runTestAVL();
        endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);
        
        
        
        //System.out.println("CHT");

        startTime = System.currentTimeMillis();
        //System.out.println(runTestCHT());
        runTestCHT();
        endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);
        
        
        
        //System.out.println("HTM");

        startTime = System.currentTimeMillis();
        //System.out.println(runTestHTM());
        runTestHTM();
        endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);
    }

    public static int runTestBST() throws FileNotFoundException {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        Scanner input = new Scanner(new File("alice.txt"));
        while (input.hasNext()) {
            String nextWord = input.next().toLowerCase();
            //if last character is not a letter, cut it off
            if (!Character.isLetter(nextWord.charAt(nextWord.length()-1))) {
                nextWord = nextWord.substring(0, nextWord.length()-1);
            }
            Integer val = bst.find(nextWord);
            if (val == null) {
                bst.insert(nextWord, 1);
            } else {
                bst.insert(nextWord, val+1);
            }
        }
        
        
        return bst.find("alice");
    }
    
    public static int runTestAVL() throws FileNotFoundException {
        AVLTree<String, Integer> avl = new AVLTree<>();
        Scanner input = new Scanner(new File("alice.txt"));
        while (input.hasNext()) {
            String nextWord = input.next().toLowerCase();
            //if last character is not a letter, cut it off
            if (!Character.isLetter(nextWord.charAt(nextWord.length()-1))) {
                nextWord = nextWord.substring(0, nextWord.length()-1);
            }
            Integer val = avl.find(nextWord);
            if (val == null) {
                avl.insert(nextWord, 1);
            } else {
                avl.insert(nextWord, val+1);
            }
        }
        return avl.find("alice");
    }
    
    public static int runTestCHT() throws FileNotFoundException {
        ChainingHashTable<String, Integer> cht = new ChainingHashTable<>(()-> new MoveToFrontList<String,Integer>());
        Scanner input = new Scanner(new File("alice.txt"));
        while (input.hasNext()) {
            String nextWord = input.next().toLowerCase();
            //if last character is not a letter, cut it off
            if (!Character.isLetter(nextWord.charAt(nextWord.length()-1))) {
                nextWord = nextWord.substring(0, nextWord.length()-1);
            }
            Integer val = cht.find(nextWord);
            if (val == null) {
                cht.insert(nextWord, 1);
            } else {
                cht.insert(nextWord, val+1);
            }
        }
        return cht.find("alice");
    }
    
      public static int runTestHTM() throws FileNotFoundException {
        HashTrieMap<Character, AlphabeticString, Integer> htm = new HashTrieMap<>(AlphabeticString.class);
        Scanner input = new Scanner(new File("alice.txt"));
        while (input.hasNext()) {
            String nextWord = input.next().toLowerCase();
            //if last character is not a letter, cut it off
            if (!Character.isLetter(nextWord.charAt(nextWord.length()-1))) {
                nextWord = nextWord.substring(0, nextWord.length()-1);
            }
            AlphabeticString word = new AlphabeticString(nextWord);
            Integer val = htm.find(word);
            if (val == null) {
                htm.insert(word, new Integer(1));
            } else {
                htm.insert(word, new Integer(val+1));
            }
        }
        return htm.find(new AlphabeticString("alice"));
    }
     
    
    
    
    

}
