package datastructures.dictionaries;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.BString;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.trie.TrieMap;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Dictionary<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(() -> new MoveToFrontList<>());
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return new SimpleIterator();
        }
        
        private class SimpleIterator implements Iterator<Entry<A,HashTrieMap<A,K,V>.HashTrieNode>> {
            private Iterator<Item<A, HashTrieMap<A,K,V>.HashTrieNode>> data = pointers.iterator();
            
            public SimpleEntry <A,HashTrieNode> next() {
                Item<A,HashTrieMap<A,K,V>.HashTrieNode> item = data.next();
                return new SimpleEntry<A, HashTrieNode>(item.key,item.value);
            }
            
            public boolean hasNext() {
                return data.hasNext();
            }
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
    	if (key == null || value == null) {
    		throw new IllegalArgumentException();
    	}
    	V oldValue = null;
    	if (key.isEmpty()) {
    		
    		if(root.value!=null) {
    			oldValue=root.value;
    			this.size--;
    		}
    		
    		root().value = value;
    		this.size++;
    		return oldValue;
    	}    	
    	
    	HashTrieNode curr = root();
    	
    	//go through every "character" in the key, create new nodes as necessary
    	Iterator<A> iter = key.iterator();
    	while(iter.hasNext()) {
    		A letter = iter.next();
    		if (curr.pointers.find(letter) == null) {
    			curr.pointers.insert(letter, new HashTrieNode());
    		}
    		curr = curr.pointers.find(letter);
    	}
    	
    	oldValue = curr.value;
    	curr.value = value;
    	
    	this.size++;
    	return oldValue;
    }

    @Override
    public V find(K key) {
    	if (key == null) {
    		throw new IllegalArgumentException();
    	}
        HashTrieNode curr = root();
        Iterator<A> iter = key.iterator();
        
        while(iter.hasNext()) {
        	A letter = iter.next();
        	if (curr.pointers.find(letter) != null) {
        		curr = curr.pointers.find(letter);
        	} else {
        		//interface says return {@code null} but don't know what that means
            	return null;
        	}
        	
        }
        return curr.value;
    }

    @Override
    public boolean findPrefix(K key) {
       /* 
    	if (key == null) {
    		throw new IllegalArgumentException();
    	}
    	HashTrieNode curr = root();
        Iterator<A> iter = key.iterator();
        while(iter.hasNext()) {
        	A letter = iter.next();
        	
        	if (curr.pointers.find(letter) == null) {
        		return false;
        	}
        	curr = curr.pointers.find(letter);
        }
        return true;
        */
        
        return (find(key) != null);
    }

    @Override
    public void delete(K key) {
        /*
         
    	if (key == null) {
    		throw new IllegalArgumentException();
    	}
    
    	HashTrieNode curr = root();
    	HashTrieNode branchParent = null;
    	A branchTransition = null;
    	
    	if (!key.isEmpty()) {
    		branchParent = root();
        	branchTransition = key.iterator().next();
    	}
    	
    	
    	Iterator<A> iter = key.iterator();
    	
    	while (iter.hasNext()) {
    		A letter = iter.next();
    		//if the given key doesn't exist, exit
    		if (curr.pointers.find(letter) == null) {
    			return;
    		}
    		
    		//keep track of most recent relevant node
    		if (curr.pointers.size() > 1 || (curr.value != null && iter.hasNext())) {
    			branchParent = curr;
    			branchTransition = letter;
    		}
    		
    		curr = curr.pointers.find(letter);
    		
    		
    	}
    	
    	//exits the loop when we've finished traversing the key
    	//getting here means that the key exists in the trie
    	
    	//if key leads to a leaf
    	if (curr.pointers.isEmpty()) {
    		if (curr.equals(root())) {
    			clear();
    		} else {
    			size--;
    			branchParent.pointers.remove(branchTransition);
    		}	
    	} else {
    		if (curr.value != null) {
    			size--;
    			curr.value = null;
    		}
    	}
    	*/
        
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        this.root = new HashTrieNode();
        this.size = 0;
    }
    
    @SuppressWarnings("unchecked")
	private HashTrieNode root() {
    	return (HashTrieNode) this.root;
    }
    
}
