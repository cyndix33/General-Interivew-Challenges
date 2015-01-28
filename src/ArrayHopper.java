import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayHopper {
    public static void main(String args[] ) throws Exception {
    	try {
    		// input format: size, #, #.......
    		Scanner sc = new Scanner(System.in);
    		int size = sc.nextInt();
    		int[] arr = new int[size];
    		for (int i = 0; i < size; i++) {
    			arr[i] = sc.nextInt();
    		}
    		sc.close();
    		System.out.print(hopIt(arr));
    	} catch (IllegalArgumentException e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * returns a String that shows the shortest path
     * to hop within the array arr
     * 
     * @param arr the array that we are hopping on
     * @return a String of result 
     * @throws IllegalArgumentException when the length of the arr
     * 		is 0, or no route can be found, or 0 at zero index
     */
    // used the Dijsktra's Algorithm to generate the shortest path
    // treating the array as a graph with all weight as 1
    public static String hopIt(int[] arr) {
    	if (arr.length == 0 || arr[0] == 0) {
    		throw new IllegalArgumentException("Invalid Argument Input");
    	}
    	
    	// keeps track of all the visited nodes
    	Queue<Integer> visited = new LinkedList<Integer>();
    	// keeps track of all nodes and its corresponding path
    	Map<Integer, ArrayList<Integer>> path = new HashMap<Integer, ArrayList<Integer>>();
    	
    	// initializes the find path
    	ArrayList<Integer> tempPath = new ArrayList<Integer>();
    	tempPath.add(0);
    	visited.add(0);
    	path.put(0, tempPath);
    	
    	while (!visited.isEmpty()) {
    		int currentIndex = visited.remove();
    		
    		// if we reaches the end
    		if (currentIndex == arr.length - 1) {
    			String result = getResult(path.get(currentIndex));
    			return result;
    		}
    		
    		for (int i = 1; i <= arr[currentIndex]; i++) {
    			int tempIndex = currentIndex + i;
    			
    			// if the tempIndex is not in the set, we add it
    			// else treat as nothing
    			if (!path.keySet().contains(tempIndex)) {
    				tempPath = path.get(currentIndex);
    				
    				// necessary copy out
    				ArrayList<Integer> tempPathCOut = new ArrayList<Integer>();
    				for(int c: tempPath) {
    					tempPathCOut.add(c);
    				}
    				
    				// set up the new index --> path
    				tempPathCOut.add(tempIndex);
    				path.put(tempIndex, tempPathCOut);
    				visited.add(tempIndex);
    			}
    		}
    	}
    	// when the end is not reachable
    	throw new IllegalArgumentException("Invalid Argument Input");
    }
    
    /**
     * Generate the needed format of result from ArrayList of route found
     * 
     * @param al the ArrayList of route found
     * @return a string in the format of "index, index, index...., out"
     */
    public static String getResult(ArrayList<Integer> al) {
    	String ret = "";
    	for (int i : al) {
    		ret += i + ", ";
    	}
    	ret += "out";
    	return ret;
    }
    
    @Test
	public void testArrayHopper(){
		int[] arr = {5, 6, 0, 4, 2, 4, 1, 0, 0, 4}; 
		String result = "0, 5, 9, out";
		System.out.println(hopIt(arr));
		assertTrue(hopIt(arr).equals(result));
	}
    
    @Test(expected=IllegalArgumentException.class)
	public void testZeroException(){
		int[] arr = {0, 5, 6, 0, 4, 2, 4, 1, 0, 0, 4}; 
		hopIt(arr);
	}
    
    @Test(expected=IllegalArgumentException.class)
	public void testEmptyException(){
		int[] arr = {}; 
		hopIt(arr);
	}
    
    @Test(expected=IllegalArgumentException.class)
    public void testNoResultException(){
    	int[] arr = {5, 0, 0, 0, 0, 0, 0};
    	hopIt(arr);
    }
}