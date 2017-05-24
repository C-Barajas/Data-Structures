/*  Christian Barajas
    masc0319
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E> {
    private int currentSize, maxSize;
    private int front, rear;
    private E[] storage;
    
    public ArrayLinearList(int size){
    	maxSize = size;  
    	currentSize = front = rear = 0;
    	storage = (E[])new Object[maxSize];
    }
    
    public ArrayLinearList(){
    	this(DEFAULT_MAX_CAPACITY);
    }
    
//  Adds the Object obj to the beginning of list and returns true if the list is not full.
//  returns false and aborts the insertion if the list is full.
    public boolean addFirst(E obj){
    	if(currentSize == maxSize)
    		return false;
    	else{
    		if( currentSize == 0){
    			storage[front] = obj;
    			rear = front;
    			currentSize++;
    		}
    		else{       
    			front--;
    			if(front < 0)
    				front = maxSize-1;
    			storage[front] = obj;
    			currentSize++;
    		}
    		return true;
    	}
    }
    
//  Adds the Object obj to the end of list and returns true if the list is not full.
//  returns false and aborts the insertion if the list is full..  
    public boolean addLast(E obj){
    	if(currentSize == maxSize)
    		return false;
    	else{
    		if( currentSize == 0){
    			storage[rear] = obj;
    			front = rear;
    			currentSize++;
    			return true;   
    		}
    		else{
    			rear++;
    			if(rear > maxSize-1)
    				rear = 0;
    			storage[rear] = obj;
    			currentSize++;
    			return true;
    		}
    	}
    }
    
//  Removes and returns the parameter object obj in first position in list if the list is not empty,  
//  null if the list is empty. 
    public E removeFirst(){
    	if(currentSize == 0)
    		return null;
    	else if(currentSize == 1 ){
    		E removeObj = storage[front];
    		front = rear = 0;
			currentSize--;
			return removeObj;
    	}
    	else{
    		E removeObj = storage[front];
    		front++;
    		currentSize--;
    		if(front > maxSize-1)
    			front = 0;
    		return removeObj; 
    	}
    }
    
//  Removes and returns the parameter object obj in last position in list if the list is not empty, 
//  null if the list is empty. 
    public E removeLast(){		
    	if(currentSize == 0)
    		return null;
    	else if(front == rear){
    		E removeObj = storage[rear];
			currentSize = 0;
			currentSize--;
			return removeObj;
    	}
    	else{
    		E removeObj = storage[rear];
    		rear--;
    		currentSize--;
    		if(rear < 0)
    			rear = maxSize-1;
    		return removeObj;	
    	}
    }
    
//  Removes and returns the parameter object obj from the list if the list contains it, null otherwise.
//  The ordering of the list is preserved.  The list may contain duplicate elements.  This method
//  removes and returns the first matching element found when traversing the list from first position.
//  Note that you may have to shift elements to fill in the slot where the deleted element was located.
    public E remove(E obj){
    	if(currentSize == 0)
    		return null;
    	else{
    		E removeObj;
    		int index = front;
    		int count = 0;
    		while(count != currentSize){
    			if(((Comparable<E>)obj).compareTo(storage[index]) == 0){
    				removeObj = storage[index];
    				if(front == rear){
    					currentSize = 0;
    					return storage[index];
    				}
    				else if(index == front){
    					front++;
    					if(front > maxSize-1)
    						front = 0; 
    				}
    				else if(index == rear){
    					rear--;
    					if(rear < 0) 
    						rear = maxSize-1;
    				}
    				else if(front > rear){ 
    					if(index > rear){
    						for(int i = index; i > front; i-- )
        						storage[i] = storage[i-1];	
    						front++;
    						if(front > maxSize-1)
    							front = 0;
    					}
    					else if(index < rear){
    						for(int i = index; i < rear; i++)
    							storage[i] = storage[i+1];
    						rear--;
    						if(rear < 0)
    							rear = maxSize -1;
    					}
    				}
    				else{
    					for(int i = index; i > front; i--)
    						storage[i] = storage[i-1];
    					front++; 
    				}
    				currentSize--; 
    				return removeObj;
    			}
    			index++;
    			if(index == maxSize)
    				index = 0;
    			count++;
    		}
    		return null;
    	}
    }
    
//  Returns the first element in the list, null if the list is empty.
//  The list is not modified.
    public E peekFirst(){
    	if(currentSize == 0)
    		return null;
    	else
    		return storage[front];
    }
    
//  Returns the last element in the list, null if the list is empty.
//  The list is not modified.
    public E peekLast(){
    	if(currentSize == 0)
    		return null;
    	else
    		return storage[rear];
    }

//  Returns true if the parameter object obj is in the list, false otherwise.
//  The list is not modified.
    public boolean contains(E obj){
    	if(currentSize == 0)
    		return false;
    	else{
    		int index = front;
    		int count = 0;
    		while(count != currentSize){
    			if(((Comparable<E>)obj).compareTo(storage[index]) == 0)
    				return true;
    			index++;
    			if(index == maxSize)
    				index = 0;
    			count++;
    		}
    		return false;
    	}
    }
    
//  Returns the element matching obj if it is in the list, null otherwise.
//  In the case of duplicates, this method returns the element closest to front.
//  The list is not modified.
    public E find(E obj){
    	if(currentSize == 0)
    		return null;
    	else{
    		int index = front;
    		int count = 0;
    		while(count != currentSize){
    			if(((Comparable<E>)obj).compareTo(storage[index]) == 0)
    				return storage[index];
    			index++;
    			if(index == maxSize)
    				index = 0;
    			count++;
    		}
    		return null;
    	}
    }

//  The list is returned to an empty state.
    public void clear(){
    	currentSize = front = rear = 0;
    	storage = (E[]) new Object[maxSize];
    }

//  Returns true if the list is empty, otherwise false
    public boolean isEmpty(){
    	if(currentSize == 0)
    		return true;
    	else
    		return false;
    }
    
//  Returns true if the list is full, otherwise false
    public boolean isFull(){
    	if(currentSize == maxSize)
    		return true;
    	else
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
    	private int iteratorIndex;
    	private int count;
    	public IteratorHelper(){
    		iteratorIndex = front;
    		count=0;
    	}
    	public boolean hasNext(){
    		return count != currentSize;
    	}
    	public E next(){
    		if(!hasNext())
    			throw new NoSuchElementException();
    		E removeObj = storage[iteratorIndex++];
    		if(iteratorIndex == maxSize){
    			iteratorIndex = 0;
    		}
    		count++;
    		return removeObj;
    	}
    	public void remove(){
    		throw new UnsupportedOperationException();
    	}
    }
}



