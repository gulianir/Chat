package datastructures.dictionaries;

import java.util.Iterator;
import java.util.function.Supplier;

import cse332.datastructures.containers.*;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ArrayStack;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You must implement a generic chaining hashtable. You may not
 *    restrict the size of the input domain (i.e., it must accept 
 *    any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 *    shown in class).
 * 5. HashTable should be able to grow at least up to 200,000 elements. 
 * 6. We suggest you hard code some prime numbers. You can use this
 *    list: http://primes.utm.edu/lists/small/100000.txt 
 *    NOTE: Do NOT copy the whole list!
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private final Supplier<Dictionary<K, V>> newChain;  
    //prime numbers that are roughly double of the last
    private final int[] PRIME_SIZES = {3,7,13,31,61,127,241,503,1009,1999,4001,
            8009,16033,32051,64067,128761,199999};
    private Dictionary<K,V>[] buckets;
    int currSize;
    int nextPrime;
    

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        nextPrime= 1;
        currSize = 0;
        buckets = getArray(PRIME_SIZES[0]);
        for (int i = 0; i < PRIME_SIZES[0]; i++) {
            buckets[i] = newChain.get();
        }
        
    }
    

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        int hashKey = myHash(key);
        Dictionary<K,V> bucket = buckets[hashKey];
        V val = bucket.insert(key, value);
        if (val == null) {
            currSize++;
        }
        if (currSize > buckets.length * 1.5) {
            rehash();
        }
        return val;
        
    }
    
    @Override 
    public int size() {
        return this.currSize;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int hashKey = myHash(key);
        Dictionary<K,V> bucket = buckets[hashKey];
        return bucket.find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ChainIterator();
    }
    
    private class ChainIterator extends SimpleIterator<Item<K,V>> {
        
        private final WorkList<Item<K,V>> items;
        
        public ChainIterator() {
            this.items = new ArrayStack<Item<K,V>>();
            for (Dictionary<K,V> bucket : buckets) {
                Iterator<Item<K,V>> iter = bucket.iterator();
                while (iter.hasNext()) {
                    items.add(iter.next());
                }
            }
        }
        
        public boolean hasNext() {
            return items.hasWork();
        }
        
        public Item<K,V> next() {
            return items.next();
            
        }
    }
    
    private void rehash() {
        Dictionary<K,V>[] oldBuckets = buckets;
        
        if (nextPrime < PRIME_SIZES.length) {
            buckets = getArray(PRIME_SIZES[nextPrime]);
            nextPrime++;
            
        } else {
            buckets = getArray(oldBuckets.length * 2);
        }
        
        
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = newChain.get();
            
        }
         currSize = 0;
        
        for (int i = 0; i < oldBuckets.length; i++) {
            Iterator<Item<K,V>> iter = oldBuckets[i].iterator();
            while (iter.hasNext()) {
                Item<K,V> item = iter.next();
                this.insert(item.key, item.value);
            }
        }
        
        
    }
    
    @SuppressWarnings("unchecked")
    private Dictionary<K,V>[] getArray(int size) {
        return (Dictionary<K,V>[]) new Dictionary[size];
    }
    
    private int myHash(K key) {
       int hashCode = key.hashCode();
       if (hashCode < 0) {
           hashCode *= -1;
       }
       return (hashCode % buckets.length);
        
    }
}
