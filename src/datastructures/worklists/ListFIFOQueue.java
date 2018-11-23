package datastructures.worklists;

import java.util.NoSuchElementException;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
		
	private Node front;
	private Node back;
	private int size;
    
    public ListFIFOQueue() {
    	front = null;
    	back = null;
        size = 0;
    }

    @Override
    public void add(E work) {
    	Node newNode = new Node(work);
    	if (!hasWork()) {
    		front = newNode; 		
    	}
    	else {
    		back.next = newNode;
    	} 
    	back = newNode;
        size++;
    }

    @Override
    /**
     *  
     */
    public E peek() {
    	if (!hasWork()) {
    		throw new NoSuchElementException();
    	}
        return front.work;
    }

    @Override
    public E next() {
    	if (!hasWork()) {
    		throw new NoSuchElementException();
    	}
        Node oldFront = front; 
        front = front.next;
        size--;
        return oldFront.work;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        front = null;
        back = null;
        size = 0;
    }
    
    private class Node {
    	//mutable
    	public Node next;
    	public E work;
    	
    	public Node(Node next, E work) {
    		this.next = next;
    		this.work = work;
    	}
    	
    	public Node(E work) {
    		this(null, work);
    	}
    }
}
