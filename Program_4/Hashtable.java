/*  Christian Barajas
    masc 0319
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class Hashtable<K,V> implements DictionaryADT<K,V>{
	private LinearListADT<Wrapper<K,V>>[] list;
	private int currentSize;
	private int maxSize;
	private int tableSize;
	private long modCounter;
	
	class Wrapper<K,V> implements Comparable<Wrapper<K,V>>{
		K key;
		V value;
		
		public Wrapper(K k,V v){
			key = k;
			value = v;
		}
		public int compareTo(Wrapper<K,V> w){
			return ((Comparable<K>)key).compareTo(w.key);
		}
	}
	
	public Hashtable(int n){
		currentSize = 0;
		modCounter = 0;
		maxSize = n;
		tableSize = (int)(1.3f * n);
		list = new LinearList[tableSize];
		for (int i = 0; i < tableSize; i++)
			list[i] = new LinearList<Wrapper<K,V>>();
	}
	private int getIndex(K key){
		return (key.hashCode() & 0x7FFFFFFF) % tableSize;
	}
	
// Returns true if the dictionary has an object identified by
// key in it, otherwise false.
	public boolean contains(K key){
		return list[getIndex(key)].contains(new Wrapper<K,V>(key, null));
	}

// Adds the given key/value pair to the dictionary.  Returns
// false if the dictionary is full, or if the key is a duplicate.
// Returns true if addition succeeded.
	public boolean add(K key, V value){
		if (currentSize == maxSize)
			return false;
		if (contains(key))
			return false;
		Wrapper<K,V> newWrapper = new Wrapper<K,V>(key, value);
		list[getIndex(key)].addLast(newWrapper);
		currentSize++;
		modCounter++;
		return true;
	}

// Deletes the key/value pair identified by the key parameter.
// Returns true if the key/value pair was found and removed,
// otherwise false.
	public boolean delete(K key){
		Wrapper<K,V> temp = list[getIndex(key)].remove(new Wrapper<K,V>(key, null));
		if (temp != null){
			currentSize--;
			modCounter++;
			return true;
		}
		return false;
	}

// Returns the value associated with the parameter key.  Returns
// null if the key is not found or the dictionary is empty.
	public V getValue(K key){
		Wrapper<K,V> temp = list[getIndex(key)].find(new Wrapper<K,V>(key, null));
		if (temp == null)
			return null;
		return temp.value;
	
	}

// Returns the key associated with the parameter value.  Returns
// null if the value is not found in the dictionary.  If more
// than one key exists that matches the given value, returns the
// first one found.
	public K getKey(V value){
		for (int i = 0; i < tableSize; i++){
			for (Wrapper<K,V> w: list[i])
				if(((Comparable<V>)value).compareTo(w.value) == 0)
					return w.key;
		}
		return null;
	}

// Returns the number of key/value pairs currently stored
// in the dictionary
	public int size(){
		return currentSize;
	}

// Returns true if the dictionary is at max capacity
	public boolean isFull(){
		if (currentSize == maxSize)
			return true;
		return false;
	}

// Returns true if the dictionary is empty
	public boolean isEmpty(){
		if (currentSize == 0)
			return true;
		return false;
	}

// Returns the Dictionary object to an empty state.
	public void clear(){
		currentSize = 0;
		modCounter++;
		for (int i = 0; i < tableSize; i++)
			list[i].clear();
	}

// Returns an Iterator of the keys in the dictionary, in ascending
// sorted order.  The iterator must be fail-fast.
	public Iterator<K> keys(){
		return new KeyIteratorHelper();
	}

// Returns an Iterator of the values in the dictionary.  The
// order of the values must match the order of the keys.
// The iterator must be fail-fast.
	public Iterator<V> values(){
		return new ValueIteratorHelper();
	}
	
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected Wrapper<K,V>[] nodes;
		protected int index;
		protected long modCheck;
		
		private Wrapper[] shellSort(Wrapper array[]){
			Wrapper[] w = array;
			Wrapper temp;
			int in, out, h = 1;
			int size = w.length;
			
			while (h <= size/3)
				h = h * 3 + 1;
			while (h > 0){
				for (out = h; out < size; out++){
					temp = w[out];
					in = out;
					while (in > h-1 && ((Comparable<Wrapper>)w[in-h]).compareTo(temp) >= 0){
						w[in] = w[in-h];
						in -= h;
					}
					w[in] = temp;
				}
				h = (h-1)/3;
			}
			return w;
		}
		public IteratorHelper(){
			nodes = new Wrapper[currentSize];
			modCheck = modCounter;
			index = 0;
			int j = 0;
			for (int i = 0; i < tableSize; i++)
				for (Wrapper w: list[i])
					nodes[j++] = w;
			nodes = (Wrapper<K,V>[]) shellSort(nodes);
		}
		
		public boolean hasNext(){
			if (modCheck != modCounter)
				throw new ConcurrentModificationException();
			return index < currentSize;
		}
		
		public abstract E next();
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
	
	class KeyIteratorHelper<K> extends IteratorHelper<K>{
		public KeyIteratorHelper(){
			super(); 
		}
		public K next(){
			if (!hasNext())
				throw new NoSuchElementException();
			return (K) nodes[index++].key;
		}
	}
	
	class ValueIteratorHelper<V> extends IteratorHelper<V>{
		public ValueIteratorHelper(){
			super();
		}
		public V next(){
			if (!hasNext())
				throw new NoSuchElementException();
			return (V) nodes[index++].value;
		}
	}
}


