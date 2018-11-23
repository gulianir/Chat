package datastructures.worklists;

import java.util.NoSuchElementException;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
	
	//TEST ECLIPSE GIT
	
	
	/*
	 * [1, 2, 3, 4, 5]
	 * stack.add(9)
	 * [1, 2, 3, 4, 5, 9]
	 * stack.peek()
	 * 9
	 * stack.next()
	 * 9
	 * [1, 2, 3, 4, 5] 
	 * 
	 */
	
	private E[] data;
	private int size;

    @SuppressWarnings("unchecked")
	public ArrayStack() {
        data = (E[])new Object[10];
        size = 0;
    }

    @Override
    public void add(E work) {
    	
    	resizeIfNeeded();
    	
        data[size] = work;
        size++;
    }

    @Override
    public E peek() {
        if(!hasWork()) {
        	throw new NoSuchElementException();
        }
        
        return data[size-1];
    }

    @Override
    public E next() {
    	if(!hasWork()) {
        	throw new NoSuchElementException();
        }
    	E work = data[size-1];
    	size--;
    	return work;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
    }
    
    @SuppressWarnings("unchecked")
	private void resizeIfNeeded() {
    	if(size==data.length) {
    		E[] newData = (E[]) new Object[2*data.length];
    		for(int i = 0; i<size; i++) {
    			newData[i] = data[i];
    		}
    		data = newData;
    	}
    }
    
    
}
