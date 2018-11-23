package p2.wordsuggestor;

import java.util.Comparator;

import java.util.function.Supplier;
import java.util.Iterator;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.Dictionary;
import cse332.misc.LargeValueFirstItemComparator;
import cse332.types.AlphabeticString;
import cse332.types.NGram;
import p2.sorts.HeapSort;
import p2.sorts.TopKSort;

public class NGramToNextChoicesMap {
    //commenting to test push
    private final Dictionary<NGram, Dictionary<AlphabeticString, Integer>> map;
    private final Supplier<Dictionary<AlphabeticString, Integer>> newInner;

    public NGramToNextChoicesMap(
            Supplier<Dictionary<NGram, Dictionary<AlphabeticString, Integer>>> newOuter,
            Supplier<Dictionary<AlphabeticString, Integer>> newInner) {
        this.map = newOuter.get();
        this.newInner = newInner;
    }

    /**
     * Increments the count of word after the particular NGram ngram.
     */
    public void seenWordAfterNGram(NGram ngram, String word) {
        //maybe unnecessary
        if (ngram == null || word == null) {
            throw new IllegalArgumentException();
        }
        
        Dictionary<AlphabeticString, Integer> innerDict = map.find(ngram);
        
        if (innerDict == null) {
            innerDict = newInner.get();
            map.insert(ngram, innerDict);
        }
        
        AlphabeticString alphaWord = new AlphabeticString(word);
        if (innerDict.find(alphaWord) == null) {
            innerDict.insert(alphaWord, 1);
        } else {
            int currentTotal = innerDict.find(alphaWord);
            innerDict.insert(alphaWord, currentTotal+1);
        }
        
    }
    
    @SuppressWarnings("unchecked")
    private Item<String,Integer>[] makeItemArray(int size) {
        return (Item<String,Integer>[]) new Item[size];
    }

    /**
     * Returns an array of the DataCounts for this particular ngram. Order is
     * not specified.
     *
     * @param ngram
     *            the ngram we want the counts for
     * 
     * @return An array of all the Items for the requested ngram.
     */
    public Item<String, Integer>[] getCountsAfter(NGram ngram) {
        Dictionary<AlphabeticString, Integer> innerDict = map.find(ngram);
        if (innerDict == null) {
            return makeItemArray(0);
            

        }

        Item<String, Integer>[] itemArr = makeItemArray(innerDict.size());
        
        int i = 0; 
        Iterator<Item<AlphabeticString, Integer>> itr = innerDict.iterator();
        int numNonNull = 0;
        while(itr.hasNext()) {
            Item<AlphabeticString, Integer> item = itr.next();
            Item<String, Integer> realItem = new Item<>(item.key.toString(), item.value);
            itemArr[i] = realItem;
            numNonNull++;
            i++;
        }
        
        Item<String, Integer>[] itemArrNonNull = makeItemArray(numNonNull);
        int currIndex = 0;
        for (int j = 0; j < itemArr.length; j++) {
            if (itemArr[j] != null) {
                itemArrNonNull[currIndex] = itemArr[j];
                currIndex++;
            }
        }
        
        return itemArrNonNull;
        
    }

    public String[] getWordsAfter(NGram ngram, int k) {
        Item<String, Integer>[] afterNGrams = getCountsAfter(ngram);

        Comparator<Item<String, Integer>> comp = new LargeValueFirstItemComparator<String, Integer>();
        
        if (k < 0) {
            HeapSort.sort(afterNGrams, comp);
        }
        else {
          //Comparator<Item<String, Integer>> comp2 = new TopKComparator<String, Integer>();
            TopKSort.sort(afterNGrams, k, comp.reversed());
            int nonNull = 0;
            for (int i = 0; i < afterNGrams.length; i++) {
                if (afterNGrams[i] != null) {
                    nonNull++;
                }
            }
            Item<String, Integer>[] afterNGramsSizeK = makeItemArray(nonNull);
            for (int i = 0; i < afterNGramsSizeK.length; i++) {
                afterNGramsSizeK[i] = afterNGrams[i];
            }
            HeapSort.sort(afterNGramsSizeK, comp);
            afterNGrams = afterNGramsSizeK;
        }

        String[] nextWords = new String[k < 0 ? afterNGrams.length : k];
        for (int l = 0; l < afterNGrams.length && l < nextWords.length
                && afterNGrams[l] != null; l++) {
            nextWords[l] = afterNGrams[l].key;
        }
        return nextWords;
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
