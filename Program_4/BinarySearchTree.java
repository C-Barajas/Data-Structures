/*  Christian Barajas
    masc 0319
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BinarySearchTree<K,V> implements DictionaryADT<K,V> {
	private Node<K,V> root;
	private int currentSize, modCounter;
	
	class Node<K,V>{
		private K key;
		private V value;
		private Node<K,V> left;
		private Node<K,V> right;
		
		public Node(K k,V v){
			key = k;
			value = v;
			left = right = null;
		}
	}
	public BinarySearchTree(){
		root = null;
		currentSize = modCounter = 0;
	}
// Returns true if the dictionary has an object identified by
// key in it, otherwise false.
	public boolean contains(K key){
		Node<K,V> current = root;
		if (root == null){
			return false;
		}
		while (true){
			int compare = ((Comparable<K>)key).compareTo(current.key);
			if (compare < 0){
				if (current.left == null)
					return false;
				current = current.left;
			}
		    else if (compare > 0){
				if (current.right == null)
					return false;
				current = current.right;
			}
			else	   
				return false;
		   }
	}

// Adds the given key/value pair to the dictionary.  Returns
// false if the dictionary is full, or if the key is a duplicate.
// Returns true if addition succeeded.
	public boolean add(K key, V value){
		currentSize++;
		modCounter++;
		Node<K,V> newNode = new Node<K,V>(key, value);
		Node<K,V> current = root;
		
		if (root == null){
			root = newNode;
			return true;
		}
		while (true){
			int compare = ((Comparable<K>)key).compareTo(current.key);
			if (compare < 0){
				if (current.left == null){
					current.left = newNode;
					return true;
				}
				current = current.left;
			}
			else if (compare > 0){
				if (current.right == null){
					current.right = newNode;
					return true;
				}
				current = current.right;
			}
			else{
				currentSize--;
				modCounter--;
				return false;
			}   
		}
	}

// Deletes the key/value pair identified by the key parameter.
// Returns true if the key/value pair was found and removed,
// otherwise false.
	private boolean removed;
	private Node<K,V> getParent(Node<K,V> n){
		if (n.left == null)
			return n;
		return getParent(n.left);
	}
	private Node<K,V> checkRemoved(K key, Node<K,V> current){
		
		if (current == null){
			removed = false;
			return current;
		}
		int compare = ((Comparable<K>)key).compareTo(current.key);
		if (compare < 0)
			current.left = checkRemoved(key, current.left);
		else if (compare > 0)
			current.right = checkRemoved(key, current.right); 
		else if (current.left != null && current.right != null){
			current.key = getParent(current.right).key;
			current.right = checkRemoved(current.key, current.right);
		}
		else{
			if (current.left != null)
				current = current.left;
			else
				current = current.right;
			removed = true;
		}
		return current;
		
	}
	public boolean delete(K key){
		root = checkRemoved(key, root);
		if (removed){
			modCounter++;
			currentSize--;
			return removed;
		}
		return false;
	}

// Returns the value associated with the parameter key.  Returns
// null if the key is not found or the dictionary is empty.
	public V getValue(K key){
		Node<K,V> current = root;
		if (root == null)
			return null;
		while (current != null){
			int comp = ((Comparable<K>)key).compareTo(current.key);
			if (comp == 0)
				return current.value;
			if (comp < 0)
				current = current.left;
			else
				current = current.right;
		}
		return null;
	}

// Returns the key associated with the parameter value.  Returns
// null if the value is not found in the dictionary.  If more
// than one key exists that matches the given value, returns the
// first one found.
	private K foundKey;
	private void valueFinder(V value, Node<K,V> current){
		if (current == null)
			return;
		int compare = ((Comparable<V>)value).compareTo(current.value);
		if (compare == 0){
			foundKey = current.key;
			return;
		}
		valueFinder(value, current.left);
		valueFinder(value, current.right);
	}
	public K getKey(V value){
		foundKey = null;
		valueFinder(value, root);
		return foundKey;
	
	}

// Returns the number of key/value pairs currently stored
// in the dictionary
	public int size(){
		return currentSize;
	}

// Returns true if the dictionary is at max capacity
	public boolean isFull(){
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
		root = null;   
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
	
	class KeyIteratorHelper<K> implements Iterator<K>{
		private int index;
		private long modCheck;
		private Node<K,V>[] nodes;
		
		public KeyIteratorHelper(){
			modCheck = modCounter;
			nodes = new Node[currentSize];
			FillArrayOrdered((Node<K,V>) root);
			index = 0;
		}
		public boolean hasNext(){
			if (modCheck != modCounter)
				throw new ConcurrentModificationException();
			return index < currentSize;
		}
		public K next(){
			if (!hasNext())
				throw new NoSuchElementException();
			return (K)nodes[index++].key;
		}
		public void remove(){
			throw new UnsupportedOperationException();
		}
		private void FillArrayOrdered(Node<K,V> n){
			if (n == null)
				return;
			FillArrayOrdered(n.left);
			nodes[index++] = n;
			FillArrayOrdered(n.right);
		}
	}
	
	protected class ValueIteratorHelper<V> implements Iterator<V>{
		Iterator<K> iter;
		
		public ValueIteratorHelper(){
			iter = keys();
		}
		public boolean hasNext(){
			return iter.hasNext();
		}
		public V next(){
			return (V)getValue(iter.next());
		}
		public void remove(){
			iter.remove();
		}
	}
}
