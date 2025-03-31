package nad;
import java.util.Arrays;

public class NAD {

    public long getMinimumCost(int nn, int kk, long[][] cc) {
        // Create memoization table with dimensions [tasks+1][all_possible_masks]
        long[][] memo = new long[kk + 1][1 << nn];
        for (long[] row : memo) Arrays.fill(row, -1);
        
        return findMinCost(0, 0, nn, kk, cc, memo);
    }

    private long findMinCost(int task, int mask, int n, int k, 
                           long[][] cost, long[][] memo) {
        // Base case: all tasks assigned
        if (task == k) return 0;
        
        // Return precomputed value if available
        if (memo[task][mask] != -1) return memo[task][mask];
        
        long min = Long.MAX_VALUE;
        
        // Try assigning current task to each available employee
        for (int emp = 0; emp < n; emp++) {
            if ((mask & (1 << emp)) == 0) { // Check if employee is available
                long newCost = cost[emp][task] + 
                    findMinCost(task + 1, mask | (1 << emp), n, k, cost, memo);
                min = Math.min(min, newCost);
            }
        }
        
        return memo[task][mask] = min;
    }

    public static void main(String[] args) {
        Judge.run(new NAD());
    }
}