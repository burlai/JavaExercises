package partition;

import java.util.HashSet;
import java.util.Set;

public class Partition {
    boolean checkPartition(int[] array, int n) {
        int totalSum = 0;
        for (int num : array) {
            totalSum += num;
        }
        
        if (totalSum % 2 != 0) {
            return false;
        }
        
        int target = totalSum / 2;
        Set<Integer> possibleSums = new HashSet<>();
        possibleSums.add(0);
        
        for (int num : array) {
            Set<Integer> newSums = new HashSet<>();
            for (int sum : possibleSums) {
                int currentSum = sum + num;
                if (currentSum == target) {
                    return true;
                }
                newSums.add(currentSum);
            }
            possibleSums.addAll(newSums);
        }
        
        return possibleSums.contains(target);
    }

    public static void main(String[] args) {
        judge.run(new Partition());
    }
}
