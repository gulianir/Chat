package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private Comparator<E> comp;

    
    @SuppressWarnings("unchecked")
	public MinFourHeap(Comparator<E> comp) {
    	size = 0;
        data = (E[]) new Object[1];
        this.comp = comp;
        
    }

    @Override
    public boolean hasWork() {
    	return super.hasWork();
    }

    @Override
    public void add(E work) {
        if (work == null) {
            System.err.println("ADDING NULL WORK");
        }
    	if (size == data.length) {
    		resize();
    	}
    	
        data[size] = work; 
        percolateUp();
        size++;
        
    }
    
    //NOT FINISHED
    @SuppressWarnings("unchecked")
	private void resize() {
    	int newMax = size*2;
    	E[] newData = (E[]) new Object[newMax];
    	for(int i = 0; i<size; i++) {
			newData[i] = data[i];
		}
		data = newData;
    }
    
    /**
     * 
     * 
     */
    private void percolateUp() {
    	int parent = getParent(size);
    	int child = size;
    	
    	while(comp.compare(data[child], data[parent]) < 0) {
    		// move child up; parent down
    		E temp = data[parent];
    		data[parent] = data[child];
    		data[child] = temp;
    		// update parent and child
    		child = parent;
    		parent = getParent(parent);
    	}
    }

    @Override
    public E peek() {
    	if (!super.hasWork()) {
        	throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
    	if(size == 0) {
        	throw new NoSuchElementException();
        }
        E originalRoot = data[0];
        // bring last element to the beginning
        data[0] = data[size-1];
        // remove last "node"
        data[size-1] = null;
        percolateDown();        
        size--;
        return originalRoot;
    }
    
    private void percolateDown() {
    	int smallestChild = 0;
        int node = 0;

        while(true) {
        	smallestChild = getSmallestChild(node);   
        	
        	if(smallestChild == -1 || comp.compare(data[node], data[smallestChild]) <= 0) {
        		break;
        	}
        	// swap node with its smallest child
        	E temp = data[node];
        	data[node] = data[smallestChild];
        	data[smallestChild] = temp;
        	node = smallestChild;
        }
    }

    @Override
    public int size() {
        return size;
    }
    
    /** 
     * 
     */
    private int getSmallestChild(int k) {
    	
    	int[] children=  new int[4];
    	for(int i = 0; i < 4; i++) {
    		int index = 4*k+i+1;
    		if(index>data.length-1) {
    			children[i] = -1;
    		} else {
    			children[i] = index;
    		}
    	}
    	
    	int smallest;
    	if(children[0] == -1 || data[children[0]] == null) {
    		return -1;
    	} else {
    		smallest=children[0];
    	}
    	for(int i : children) {
    	    
    		if(i != -1 && data[i] != null && comp.compare(data[i], data[smallest]) < 0) {
    			smallest = i;
    		}
    	}
    	return smallest;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void clear() {
    	//check on this later
        size = 0;
        //data = (E[]) new Comparable[1];

    }
    
    /**
     * 
     */
    private int getParent(int k) {
    	if (k == 0) {
    		return 0;
    	}
    	return (k-1)/4;
    }
}
