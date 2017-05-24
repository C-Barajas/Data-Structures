/*  Christian Barajas
    masc 0319
*/

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator; 
import java.util.NoSuchElementException;

public class LinearList<E> implements LinearListADT<E> {
	private int currentSize;
	private Node<E> head;
	private Node<E> tail;
	
	public class Node<T>{
		T data;
		Node<T> previous;
		Node<T> next;
		
		public Node(T data){
			this.data = data;
			previous = next = null;
		}
	}
	
	public LinearList(){
		head = tail = null;
		currentSize = 0;
	} 
    
//  Adds the Object obj to the beginning of list and returns true if the list is not full.
//  returns false and aborts the insertion if the list is full.
    public boolean addFirst(E obj){
    	Node<E> newNode = new Node<E>(obj);
    	if (currentSize == 0)
    		head = tail = newNode;
    	else{
    		newNode.next = head;
    		head.previous = newNode;
    		head = newNode;
    	}
    	currentSize++;
    	return true;
    	
    }
    
//  Adds the Object obj to the end of list and returns true if the list is not full.
//  returns false and aborts the insertion if the list is full..  
    public boolean addLast(E obj){
    	Node<E> newNode = new Node<E>(obj);
    	if (currentSize == 0)
    		head = tail = newNode;
    	else{
    		tail.next = newNode;
    		newNode.previous = tail;
    		tail = newNode;
    	}
    	currentSize++;
    	return true;
    	
    }
    
//  Removes and returns the parameter object obj in first position in list if the list is not empty,  
//  null if the list is empty. 
    public E removeFirst(){
    	if (currentSize == 0)
    		return null;
    	E removedData = head.data;
    	head = head.next;
    	if (head == null)
    		tail = null;
    	else
    		head.previous = null;
    	currentSize--;
    	return removedData;
    			
    }
    
//  Removes and returns the parameter object obj in last position in list if the list is not empty, 
//  null if the list is empty. 
    public E removeLast(){
    	if (currentSize == 0)
    		return null;
    	E removedData = tail.data;
    	tail = tail.previous;
    	if (tail == null)
    		head = null;
    	else
    		tail.next = null;
    	currentSize--;
    	return removedData;
    }
    
//  Removes and returns the parameter object obj from the list if the list contains it, null otherwise.
//  The ordering of the list is preserved.  The list may contain duplicate elements.  This method
//  removes and returns the first matching element found when traversing the list from first position.
//  Note that you may have to shift elements to fill in the slot where the deleted element was located.
    public E remove(E obj){
    	Node<E> current = head;
    	while(current != null && ((Comparable<E>)obj).compareTo(current.data) != 0)
    		current = current.next;
    	if (current == null)
    		return null;
    	else if (current == head)
    		return removeFirst();
    	else if (current == tail)
    		return removeLast();
    	else{
    		current.previous.next = current.next;
    		current.next.previous = current.previous;
    		currentSize--;
    		return current.data;
    	}
    }
    
//  Returns the first element in the list, null if the list is empty.
//  The list is not modified.
    public E peekFirst(){
    	if (currentSize == 0)
    		return null;
    	else 
    		return head.data;
    }
    
//  Returns the last element in the list, null if the list is empty.
//  The list is not modified.
    public E peekLast(){
    	if (currentSize == 0)
    		return null;
    	else
    		return tail.data;
    }

//  Returns true if the parameter object obj is in the list, false otherwise.
//  The list is not modified.
    public boolean contains(E obj){
    	Node<E> current = head;
    	while(current != null && ((Comparable<E>)obj).compareTo(current.data) != 0)
    		current = current.next;
    	if (current == null) 
    		return false;
    	return true;
    }
   
//  Returns the element matching obj if it is in the list, null otherwise.
//  In the case of duplicates, this method returns the element closest to front.
//  The list is not modified.
    public E find(E obj){
    	Node<E> current = head;
    	while(current != null && ((Comparable<E>)obj).compareTo(current.data) != 0)
    		current = current.next;
    	if (current == null) 
    		return null;
    	return current.data;
    }

//  The list is returned to an empty state.
    public void clear(){
    	currentSize = 0;
    	head = tail = null;
    }

//  Returns true if the list is empty, otherwise false
    public boolean isEmpty(){
    	if (currentSize == 0)
    		return true;
    	else
    		return false;
    }
    
//  Returns true if the list is full, otherwise false
    public boolean isFull(){
    	return false;
    }

//  Returns the number of Objects currently in the list.
    public int size(){
    	return currentSize;
    }
    
//  Returns an Iterator of the values in the list, presented in
//  the same order as the underlying order of the list. (front first, rear last)
    public Iterator<E> iterator(){
    	return new IteratorHelper();
    }
    	 class IteratorHelper implements Iterator<E>{
    	    	Node<E> iterPointer;
    	    	public IteratorHelper(){
    	    		iterPointer = head;
    	    	}
    	    	public boolean hasNext(){
    	    		return (iterPointer != null);
    	    	}
    	    	public E next(){
    	    		if(hasNext()){
    	    			Node <E> temp = iterPointer;
    	    			iterPointer = iterPointer.next;
    	    			return temp.data;
    	    		}
    	    		else {
    	    			throw new NoSuchElementException();
    	    		}
    	    	}
    	    	public void remove(){
    	    		throw new UnsupportedOperationException();
    	    	}
    	    }
    
}
