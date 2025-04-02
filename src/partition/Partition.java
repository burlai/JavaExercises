package partition;
import java.util.HashSet;

public class Partition {
    boolean checkPartition(int[] array, int n) {
    	
    	int totalSum = 0;
    	
    	for (int number : array) {
    		totalSum += number;
    	}
    	
//    	System.out.println(totalSum);
    	
    	if (totalSum % 2 != 0) {
    		System.out.println("return false");
    		return false;
    	}
    	
    	int targetSum = totalSum / 2;
    	
    	HashSet<Integer> possibleSums = new HashSet<>();
    	possibleSums.add(0);
    	
//    	System.out.println(targetSum);
    	
    	for (int numberInArray : array) {
    		System.out.println(numberInArray);
    		
    		HashSet<Integer> newSums = new HashSet<>();
    		
    		for (int sum : possibleSums) {
    			int currentSum = sum + numberInArray;
    			
    			if (currentSum == targetSum) {
    				System.out.println("true!!");
    				return true;
    			}
    			
    			newSums.add(currentSum);
//    			System.out.println(newSums);
    		}
    		
    		possibleSums.addAll(newSums);
    		
    	}
    	
        return false;
    }
    
    

    public static void main(String[] args) {
//        judge.run(new Partition());
    	
  
    	Partition examplePartition = new Partition();
    	
    	int[] exampleArray = {1,2,1,4};
    	
    	examplePartition.checkPartition(exampleArray, 4);
    }
}