/*
 * Christian Barajas
 * masc 0319
 */
public class GenericSorter {
	
	public static  <E> E[] insertionSort(E[]array){
		for (int i = 1; i < array.length; i++){
			E obj = array[i];
			int x;
			for (x = i-1; x >= 0 && (((Comparable)array[x]).compareTo(obj) > 0); x--){
				array[x+1] = array[x];
			}
			array[x+1] = obj;
		}
		return array;
	}
	
	public static <E> E[] shellSort(E[]array){
		for (int i = array.length/2; i > 0; i /= 2){
			for (int x = i; x < array.length; x++){
				E obj = array[x];
				int y;
				for (y = x; y >= i && ((Comparable)array[y-i]).compareTo(obj) > 0; y -= i){
					array[y] = array[y-i];
				}
				array[y] = obj;
			}
		}
		return array;
	}
	
	public static <E> E[] quickSort(E[]array){
		E p = (E) array;
		qSort(null,0,array.length-1);
		return (E[]) p;
	}
	private static <E> void qSort(E[]array, int left, int right){
		if (right-left <= 0)
			return;
		E pivot = array[right];
		int partition = getPartition(left, right, pivot, array);
		qSort(array, left, partition-1);
		qSort(array, partition+1, right);
	}
	private static <E> int getPartition(int left, int right, E pivot, E[] p){
		int leftPtr = left-1;
		int rightPtr = right;
		for (;;){
			while ((((Comparable)p[++leftPtr]).compareTo(pivot) < 0)){
				;
			}
			while (rightPtr > 0 && (((Comparable)p[--rightPtr]).compareTo(pivot) > 0)){
				;
			}
			if ( leftPtr >= rightPtr)
				break;
			else
				swap(leftPtr,rightPtr, null);
		}
		swap(leftPtr,right, null);
		return leftPtr;
	}
	private static <E> void swap(int one, int two, E[] p){
		E tmp = p[one];
		p[one] = p[two];
		p[two] = tmp;
	}

	
	
	
	
	
}
