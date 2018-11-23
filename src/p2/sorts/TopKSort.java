package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        if (k > array.length) {
            //throw new IllegalStateException();
            k = array.length;
            
        }
        MinFourHeap<E> heap = new MinFourHeap<E>(comparator); 
        //put the first k elements into the heap 
        for (int i = 0; i < k; i++) {
            heap.add(array[i]);
        }
        
        //inv: heap contains the largest 10 elements of array[0],...,array[i]
        for (int i = k; i < array.length; i++) {
            if (comparator.compare(array[i], heap.peek()) > 0) {
                heap.next();
                heap.add(array[i]);
            }
        }
        
        //insert into the array
        for (int i = 0; i < array.length; i++) {
            if (heap.hasWork()) {
                array[i] = heap.next();
            } else {
                array[i] = null;
            }
        }
    }
}
