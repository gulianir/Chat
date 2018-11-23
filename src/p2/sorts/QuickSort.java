package p2.sorts;

import java.util.Comparator;

public class QuickSort {
    
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        if (array == null || comparator == null) {
            throw new IllegalArgumentException();
        }
        
       sortHelp(array, 0, array.length, comparator);
      
        
    }
    
    private static <E> void sortHelp(E[] array, int lo, int hi, Comparator<E> comparator) {
        //lo(inclusive) to hi(exclusive) so if there is one element then hi-lo == 1
        if ((hi - lo) <= 1) {
            return;
        }
        //if there are 2 elements, just check if they need to be swapped 
        if ((hi - lo) <= 2) {
            if(comparator.compare(array[lo], array[hi-1]) > 0) {
                swap(lo, hi-1, array);
            }
            return;
        }
        //pick a pivot 
        int pivot = lo;
        //pivot is the max of lo and hi 
        if (comparator.compare(array[pivot], array[hi - 1]) < 0) {
            pivot = hi-1;
        }
        //pivot is the median of lo, mid, and hi
        if (comparator.compare(array[pivot], array[(hi+lo) >>> 1]) > 0) {
            pivot = (hi+lo)/2;
        }
        
        //partition
        //swap pivot to move it out of the way
        swap(lo, pivot, array);
        pivot = lo;
        
        //2 fingers
        int i = lo + 1;
        int j = hi - 1;
        
        //find an element on each side of the pivot to swap
        while (i < j) {
            //if arr[j] > pivot, move on
            if (comparator.compare(array[j], array[pivot]) >= 0) {
                j--;
            }
            //if arr[i] < pivot, move on
            else if (comparator.compare(array[i], array[pivot]) <= 0) {
                i++;
            }
            //otherwise, we have a location on both sides to swap
            else {
                swap(i, j, array);
            }
        }
        
        //put pivot back 
        swap(pivot, i, array); 
        pivot = i;
        
        sortHelp(array, lo, pivot, comparator);
        sortHelp(array, pivot + 1, hi, comparator);
    }
    
    private static <E> void swap(int i, int j, E[] array) {
        E hold = array[i];
        array[i] = array[j];
        array[j] = hold;
    }
    
}
