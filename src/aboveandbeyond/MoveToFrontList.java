package aboveandbeyond;

import java.util.Iterator;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the 
 *    list. This means you remove the node from its current position 
 *    and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list.
 */
public class MoveToFrontList<K, V> extends Dictionary<K, V> {
    
    private ItemNode front;
    
    private class ItemNode {
        Item<K,V> item;
        ItemNode next;
        
        public ItemNode(Item<K,V> item) {
            this.item = item;
        }
    }
    
    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is
     * replaced.
     *
     * @param key
     *            key with which the specified value is to be associated
     * @param value
     *            value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
     *         if there was no mapping for <tt>key</tt>.
     * @throws IllegalArgumentException
     *             if either key or value is null.
     */
    
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        ItemNode newItem = new ItemNode(new Item<>(key,value));
        V oldValue = null;
        
        if (front == null) {
            front = newItem;
            this.size++;
            return oldValue;
        }
        if (front.item.key.equals(key)) {
            oldValue = front.item.value;
            newItem.next = front.next;
            front = newItem;
            return oldValue;

        } else {
            ItemNode curr = front;
            while (curr.next != null) {
                if (curr.next.item.key.equals(key)) {
                    oldValue = curr.next.item.value;
                    curr.next = curr.next.next;
                    break;
                }
                curr = curr.next;
            }
            //gets to the end of the loop if value is not present        
        }
        if (oldValue == null) {
            this.size++;
        }
        newItem.next = front;
        front = newItem;
        return oldValue;
        
    }

    /**
     * Returns the value to which the specified key is mapped, or {@code null}
     * if this map contains no mapping for the key.
     *
     * @param key
     *            the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or {@code null}
     *         if this map contains no mapping for the key
     * @throws IllegalArgumentException
     *             if key is null.
     */
    @Override
    public V find(K key) {
        
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (front == null) {
            return null;
        }
        if (front.item.key.equals(key)) {
            return front.item.value;
        }
        
        ItemNode curr = front;
        Item<K,V> found;
        while (curr.next != null) {
            if (curr.next.item.key.equals(key)) {
                found = curr.next.item;
                ItemNode newFront = new ItemNode(found);
                
                curr.next = curr.next.next;
                newFront.next = front;
                this.front = newFront;
                
                return newFront.item.value;
            }
            
            curr = curr.next;
            
        }
        
        return null;
    }
    
    @Override
    public void clear() {
        front = null;
    }
    
    @Override
    public void delete(K key) {
        
        ItemNode node = this.findNode(key);
        
        if(node == null) {
            return;
        }
        if(node == front) {
            this.front = this.front.next;
        } else {
            node.next = node.next.next;            
        } 
        
        
    }
    
    
    private ItemNode findNode(K key){
        if(front == null) {
            return null;
        }
        
        if(front.item.key == key) {
            return front;
        }
        
        ItemNode curr = front;
        while(curr.next != null) {
            if(curr.next.item.key.equals(key)) {
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    /**
     * An iterator over the keys of the map
     */
    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontIterator();
    }
    
    private class MoveToFrontIterator extends SimpleIterator<Item<K,V>> {
        
        private ItemNode current;
        
        public MoveToFrontIterator() {
            if (MoveToFrontList.this.front != null) {
                this.current = MoveToFrontList.this.front;
            }
        }

        @Override
        public Item<K, V> next() {
            Item<K,V> next = current.item;
            current = current.next;
            return next;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }
        
    }
    
    public static void main(String[] args) {
        
        MoveToFrontList<Integer, Integer> list1 = new MoveToFrontList<>();
        
        for(int i = 0; i < 25; i++) {
            list1.insert(i, i);
        }
        System.out.println(list1);
        list1.delete(20);
        System.out.println(list1);
    }
    
    
}