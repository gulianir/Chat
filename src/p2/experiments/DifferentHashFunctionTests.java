package p2.experiments;

import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;

public class DifferentHashFunctionTests {
    public static class SlowCode {
        protected String stringField;
        protected int intField;
        
        public SlowCode(String str) {
            stringField = str;
            intField = (int) ((Math.random()*100) + 3);
        }
        
        public int hashCode() {
            int code = intField;
            //System.out.println("intField = " + intField);
            for (int i = 0; i < stringField.length(); i++) {
                for (int j = i+1; j < stringField.length(); j++) {
                    code = (int) Math.pow(code, (stringField.substring(i, j).hashCode()) * 31);
                    //System.out.println(code);
                }
            }
            return code;
        }
        
    }
    
    public static class FastCode extends SlowCode {
        public FastCode(String str) {
            super(str);
        }

        @Override 
        public int hashCode() {
            return 0;
        }
    }
    
    public static class NormalCode extends SlowCode {
        public NormalCode(String str) {
            super(str);
        }

        @Override 
        public int hashCode() {
            int code = 31;
            code = code * (stringField.hashCode() * 31);
            code = code * intField;
            return code;
        }
        
    }
    public static void main(String[] args) {
        
        
        final int NUM_TESTS = 10;
        final int NUM_WARMUP = 3;
        final int[] N_VALUES = {1000, 2000, 3000, 4000, 5000};
        
        //larger N values to try later: 1000, 5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000, 
        //55000, 60000, 65000, 70000, 75000, 80000, 85000, 90000, 95000, 100000 

        
        
        System.out.println("FAST CODE");
        double totalTime = 0;
        for (int n = 0; n < N_VALUES.length; n++) {
            for (int i = 0; i < NUM_TESTS; i++) {
                long startTime = System.currentTimeMillis();
                runTestFast(N_VALUES[n]);
                long endTime = System.currentTimeMillis();
                if (NUM_WARMUP <= i) {
                    totalTime += (endTime - startTime);
                }
            }

            double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUP);
            System.out.println(averageRuntime);
        }
        
        
        System.out.println("SLOW CODE");
        totalTime = 0;
        for (int n = 0; n < N_VALUES.length; n++) {
            for (int i = 0; i < NUM_TESTS; i++) {
                long startTime = System.currentTimeMillis();
                runTestSlow(N_VALUES[n]);
                long endTime = System.currentTimeMillis();
                if (NUM_WARMUP <= i) {
                    totalTime += (endTime - startTime);
                }
            }

            double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUP);
            System.out.println(averageRuntime);
        }
        
        System.out.println("NORMAL CODE");
        
        totalTime = 0;
        for (int n = 0; n < N_VALUES.length; n++) {
            for (int i = 0; i < NUM_TESTS; i++) {
                long startTime = System.currentTimeMillis();
                runTestNormal(N_VALUES[n]);
                long endTime = System.currentTimeMillis();
                if (NUM_WARMUP <= i) {
                    totalTime += (endTime - startTime);
                }
            }

            double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUP);
            System.out.println(averageRuntime);
        }
        

    }
    
    public static void runTestFast(int n) {
        ChainingHashTable<FastCode, Integer> fastCode = new ChainingHashTable<>(()-> new MoveToFrontList<FastCode,Integer>());
        
        for (int i = 0; i < n; i++) {
            FastCode newItem = new FastCode(i + " hello world");
            fastCode.insert(newItem, i);
        }
    }
    
    public static void runTestSlow(int n) {
        ChainingHashTable<SlowCode, Integer> slowCode = new ChainingHashTable<>(()-> new MoveToFrontList<SlowCode,Integer>());
        
        for (int i = 0; i < n; i++) {
            SlowCode newItem = new SlowCode(i + " hello world");
            slowCode.insert(newItem, i);
        }
    }
    
    public static void runTestNormal(int n) {
        ChainingHashTable<NormalCode, Integer> normalCode = new ChainingHashTable<>(()-> new MoveToFrontList<NormalCode,Integer>());
        for (int i = 0; i < n; i++) {
            NormalCode newItem = new NormalCode(i + " hello world");
            normalCode.insert(newItem, i);
        }
    }

}
