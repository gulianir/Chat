package datastructures.worklists;

import java.util.Arrays;
import java.util.NoSuchElementException;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
	private int front; 
	private int back;
	private int size;
	private E[] data;
		
    @SuppressWarnings("unchecked")
	public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        front= 0; 
        back = 0;
        size = 0;
        data = (E[])new Comparable[capacity];
    }

    @Override
    public void add(E work) {
        if (super.isFull()) {
        	throw new IllegalStateException();
        }
        data[back] = work;
        size++;
        back = (back + 1) % super.capacity();        
    }

    @Override
    public E peek() {
    	return peek(0);
    }
    
    @Override
    public E peek(int i) {
    	//technically not necessary because the next check will 
    	//cover it but this throws a different exception so I kept it
    	if (!super.hasWork()) {
    		throw new NoSuchElementException();
    	}
    	
    	if (i< 0 || i>= size) {
        	throw new IndexOutOfBoundsException();
        }
    	
    	return data[(front + i) % super.capacity()];
    }
    
    @Override
    public E next() {
    	if (!super.hasWork()) {
    		throw new NoSuchElementException();
    	}
    	E next = data[front];
    	front = (front + 1) % super.capacity();
    	size--;
    	return next;
    	
    }
    
    @Override
    public void update(int i, E value) {
    	if (!super.hasWork()) {
    		throw new NoSuchElementException();
    	}
    	
    	if (i< 0 || i>= size) {
        	throw new IndexOutOfBoundsException();
        }
    	data[(front + i) % super.capacity()] = value;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public void clear() {
        front= 0;
        back = 0;
        size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        //check each element 
        //each element 0,...,i-1 in this and other are equal
        for (int i = 0; i < this.size; i++) {
            //if there's no i-th element in other, it must be shorter
            try {
                other.peek(i);
            } catch (IndexOutOfBoundsException e) {
                return 1;
            }
            
            int compare = this.peek(i).compareTo(other.peek(i));
            
            if (compare != 0) {
                return compare;
            }
        }
        
        //if we get to the end of this and there are still elements in other,
        //return other. if they are both empty and get to the end of the loop,
        //they are equal
        try {
            other.peek(this.size());
        } catch (NoSuchElementException e) {
            return 0;
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
        
        return -1;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in p2. Leave this method unchanged for p1.
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            
            if(other.size() != size) {
                return false;
            }
            
            for(int i = 0; i<size; i++) {
                if(!other.peek(i).equals(this.peek(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        
        for(int i = 0; i<size; i++) {
            //not sure if this is allowed
            hashCode = hashCode + this.peek(i).hashCode() * 31;
            hashCode *= (i*31);
        }
        
        hashCode =  31 * hashCode + this.size;
        return hashCode;
    }
}
