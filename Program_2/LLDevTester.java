import data_structures.LinearList;
import data_structures.LinearListADT;

public class LLDevTester {

  private final int ManyItems = 100000;

  public LLDevTester() {
    runTests();
  }

  public static void main(String[] args) {
    new LLDevTester();
  }

  private void displayTestResults(String description, String expected,
      String actual) {
    System.out.println(description);
    // String.format() could come in handy here, but let's keep it simple
    System.out.println("    Expected: " + expected);
    System.out.println("    Actual: " + actual);
  }

  private void displayElapsedTime(long startingTime) {
    System.out.println("    Elapsed Time: "
        + Double.toString(((System.nanoTime() - startingTime)) / 1000000.0)
        + " mS");
  }

  /**
   * Generates a new, empty list. Although this method seems to provide little
   * advantage over directly instantiating a LinearList when needed, it provides
   * a few benefits. First, it facilitates swapping out the data structure under
   * test. Should one decide to drop in a circular array, one need only change
   * it here for it to ripple through all the tests.
   * <p>
   * Additionally, this establishes our naming strategy for the framework. As
   * shown in the subsequent getAscendingItemList method.
   * </p>
   *
   * @return a newly constructed LinearList on the heap
   */
  private static LinearListADT<Integer> getEmptyList() {
    return new LinearList<Integer>();
  }

  /**
   * Tests frequently require a known starting point. This method builds and
   * returns a LinearListADT conforming object with the specified number of
   * items already in place. It simplifies the find/contains methods.
   * <p>
   * Note: uses addLast to construct the list.
   * </p>
   *
   * @param size
   *          Number of items to insert in the array
   * @return A newly constructed LinearListADT object on the heap filled with
   *         items in ascending order from 1-size
   */
  private static LinearListADT<Integer> getAscendingItemList(int size) {
    LinearListADT<Integer> uut = getEmptyList();

    for (int i = 1; i <= size; i++) {
      uut.addLast(i);
    }
    return uut;
  }

  private void runTests() {
      
    testConstructor_initialSize_zero();
    testAddFirst_manyItems_neverFalse_timed();
    testAddFirstWhenSizeIsOne();
    addingAndRemoving();
    iCheckYourIterator();
    removingUsingRemoveOnly();
    checkFindAndContains();
  }

  private void testConstructor_initialSize_zero() {

    LinearListADT<Integer> uut = getEmptyList();

    displayTestResults("[1] Construction size", "0",
        Integer.toString(uut.size()));
  }

  private void testAddFirst_manyItems_neverFalse_timed() {

    LinearListADT<Integer> uut = getEmptyList();

    boolean testPassed = true;
    long startTime = System.nanoTime();
    for (int i = 0; i < ManyItems; i++) {
      if (uut.addFirst(i) == false) // Note: Autoboxed to Integer
      {
        testPassed = false;
        break;
      }
    }
    displayTestResults("[2] addFirst ManyItems", "true",
        Boolean.toString(testPassed));
    displayElapsedTime(startTime);
  }
  
  private void testAddFirstWhenSizeIsOne(){
      LinearListADT<Integer> uut = getEmptyList();
      boolean testpassed = true;
      long startTime = System.nanoTime();
      Integer checkContents = new Integer(-1);
      /*
      Tests to see if linearlist at size 1 = head=tail
      Also tests clear();
      */
      uut.addFirst(1);
      if(uut.peekFirst()==uut.peekLast()){
          
      }
      else{
          System.out.println(" Test Failed: Your AddFirst doesn't have a head!=tail when size =1");
          testpassed = false;
      }
      uut.clear();
      uut.addLast(1);
      if(uut.peekFirst() == uut.peekLast()){
          
      }
      else{
          System.out.println(" Test Failed: Your Addlast doesn't have a head!=tail when size =1");
          testpassed = false;
      }
      /*
        Tests contents of linearlist
      */
      checkContents = uut.peekFirst();
      if(checkContents == -1){
          System.out.println(" Test failed, you did not give your item the data.");
      }
      else if(checkContents ==1);
      else
          System.out.println(" Something weird going on.");
         
          
      displayTestResults("[3] AddFirst one item", "true", Boolean.toString(testpassed));
      displayElapsedTime(startTime);
      
  }
  private void addingAndRemoving(){
      LinearListADT<Integer> uut = getEmptyList();
      boolean testpassed = true;
      long startTime = System.nanoTime();
      uut.addFirst(1);
      uut.addFirst(2);
      uut.removeLast();
      if(uut.peekFirst()==2 && uut.size()==1){}
      else{
          testpassed = false;
          System.out.println(" Check your addFirst and RemoveLast Methods.");
      }
      if(uut.remove(2)==2){}
      else System.out.println( "Failed to remove item when size is 1.");
      for(int i = 0; i< 5; i++){
          uut.addFirst(i);
          uut.addLast(i);
      }
      while(uut.size()>2){
          if(uut.removeFirst() == uut.removeLast()){}
          else {
              testpassed = false;
              System.out.println("Failed to return mirror data");
          }
      }    
      if(uut.peekFirst()==uut.peekLast()){}
      else{
          testpassed = false;
          System.out.println(" Data is not null when it should be.");
      }
       displayTestResults("[4] Adding and Removing a few elements", "true",
       Boolean.toString(testpassed));
       displayElapsedTime(startTime);
          
      }
  private void iCheckYourIterator(){
      LinearListADT<Integer> uut = getAscendingItemList(100);
      long startTime = System.nanoTime();
      System.out.println();
      displayTestResults("[5] Checking Iterator", "1-100",
      "I got ...\n");
      for(Integer iIterate: uut){
          System.out.printf("%5d", iIterate);
          if(iIterate%10==0) System.out.println();
      }
      displayElapsedTime(startTime);
  }   
  private void removingUsingRemoveOnly(){
      LinearListADT<Integer> uut = getAscendingItemList(50);
      boolean testpassed = true;
      System.out.println(uut.size());
      for(int i = 1; i<=50; i++){
          Integer tmp = (Integer) uut.removeFirst();
          if(tmp == i ){}
          else{
              testpassed = false;
              System.out.println("Failed to remove items appropriately");
          }
          
      }
      
      System.out.println(uut.size());
      for(Integer nothingHere: uut){
          System.out.println("I should be empty : " + nothingHere);
          testpassed = false;
      }
      displayTestResults("[6] Removing using remove ONLY", "true",
      Boolean.toString(testpassed));
  }
  public void checkFindAndContains(){
    LinearListADT<Integer> uut = getAscendingItemList(50);
    
    if(uut.find(1)==1){}
    else System.out.println("Cannot find the value at head when size is 50");
    if(uut.find(50)==50){}
    else System.out.println("Cannot find the value at the tail when size is 50");
    if(uut.contains(1)){}
    else System.out.println("Error in contains!");
    if(uut.contains(50)){}
    else System.out.println("Error in contains!");
    while(uut.size()!=3){
        uut.removeLast();
  }
   if(uut.find(1)==1){}
   else System.out.println("Cannot find the value at head when size is 3");
   if(uut.find(2)==2){}
   else System.out.println("Cannot find the value at middle when size is 3");
   if(uut.find(3)==3){}
   else System.out.println("Cannot find the value at tail when size is 3");
   if(uut.contains(1)){}
   else System.out.println("Error in contains!");
   if(uut.contains(2)){}
   else System.out.println("Error in contains!");
   if(uut.contains(3)){}
   else System.out.println("Error in contains!");
   uut.removeLast();
   uut.removeLast();
   if(uut.contains(1)){}
   else System.out.println("Error in contains!");
   if(uut.find(1)==1){}
   else System.out.println("Cannot find the value at head when size is 3");
   
   displayTestResults("[7] Find and Contain tester", "No errors",
      "Nothing should be printed Between [6] and [7]");
}  
}