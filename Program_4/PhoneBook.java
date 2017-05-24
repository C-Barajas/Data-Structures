/*  Christian Barajas
    masc 0319
*/

import data_structures.*;
import java.util.Iterator;
import java.io.*;

public class PhoneBook{
	private DictionaryADT<PhoneNumber,String> lookup;
    // Constructor.  There is no argument-less constructor, or default size
    public PhoneBook(int maxSize){
    	lookup = new Hashtable<PhoneNumber,String>(maxSize);
    			//new BinarySearchTree<PhoneNumber,String>();
    			//new BalancedTree<PhoneNumber,String>();
    }
    // Reads PhoneBook data from a text file and loads the data into
    // the PhoneBook.  Data is in the form "key=value" where a phoneNumber
    // is the key and a name in the format "Last, First" is the value.
    public void load(String filename){
    	String line;
    	try {
    	@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(filename));
    	while((line = in.readLine()) != null) { 
    		PhoneNumber key = new PhoneNumber(line.substring(0,12));
    		String value = line.substring(13);
    		if(!addEntry(key, value)) 
    			throw new RuntimeException("Duplicate Entry!!");
    		}
    	in.close();
    	// System.out.println("The data file " + filename + " has been loaded"); 
    	}
    	catch(IOException excp) {
    		System.out.println("Sorry, error reading the file." + excp);
    	}
    	catch(Exception e) {
    		System.out.println("ERROR " + e);
    	}
    }
           
    // Returns the name associated with the given PhoneNumber, if it is
    // in the PhoneBook, null if it is not.
    public String numberLookup(PhoneNumber number){
    	return lookup.getValue(number);
    }
       
    // Returns the PhoneNumber associated with the given name value.
    // There may be duplicate values, return the first one found.
    // Return null if the name is not in the PhoneBook.
    public PhoneNumber nameLookup(String name){
    	return lookup.getKey(name);
    }
       
    // Adds a new PhoneNumber = name pair to the PhoneBook.  All
    // names should be in the form "Last, First".
    // Duplicate entries are *not* allowed.  Return true if the
    // insertion succeeds otherwise false (PhoneBook is full or
    // the new record is a duplicate).  Does not change the datafile on disk.
    public boolean addEntry(PhoneNumber number, String name){
    	return lookup.add(number, name);
    }
       
    // Deletes the record associated with the PhoneNumber if it is
    // in the PhoneBook.  Returns true if the number was found and
    // its record deleted, otherwise false.  Does not change the datafile on disk.
    public boolean deleteEntry(PhoneNumber number){
    	return lookup.delete(number);
    }
       
    // Prints a directory of all PhoneNumbers with their associated
    // names, in sorted order (ordered by PhoneNumber).
    public void printAll(){
    	 Iterator<PhoneNumber> numIter = lookup.keys();
    	 Iterator<String> nameIter = lookup.values();
    	 while(numIter.hasNext())
    		 System.out.println(numIter.next() + " = " + nameIter.next());
    }
       
    // Prints all records with the given Area Code in ordered
    // sorted by PhoneNumber.
    public void printByAreaCode(String code){
    	Iterator<PhoneNumber> numIter = lookup.keys();
    	while(numIter.hasNext()){
    		PhoneNumber number = numIter.next();
    		String tmp = number.getAreaCode();
    		if (((Comparable<String>)tmp).compareTo(code) == 0)
    			System.out.println(number);
    	}
    }
   
    // Prints all of the names in the directory, in sorted order (by name,
    // not by number).  There may be duplicates as these are the values.       
    public void printNames(){
    	int i = 0;
    	String [] nodes = new String[lookup.size()];
    	Iterator<String> values = lookup.values();
		while(values.hasNext()) {
			nodes[i++] = values.next();
		}
		nodes = (String[]) shellSort(nodes);
		for(i = 0; i < lookup.size(); i++) {
			System.out.println(nodes[i] + " ");
		}
    }
    public String[] shellSort(String[] array) {
    	String[] n = array;
		int in, out, h=1;
		String tmp;
		int size = n.length;

		while(h <= size/3) //calculate gaps
			h = h*3+1;

		while(h > 0) {
			for(out = h; out < size; out++) {
				tmp = n[out];
				in = out;
				while(in > h-1 && n[in-h].compareTo((String) tmp) >= 0) {
					n[in] = n[in-h];
					in -= h;
				}
			n[in] = tmp;
			}//end for
			h = (h-1)/3;
		}//end while

		return n;
		} 
}


