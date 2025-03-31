package log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LOG {
    private static final int LOGO_SIZE = 10;
    private static final int STAMP_SIZE = 4;

    public int solve(int n, char[][] logo, char stamps[][][]) {
        // Generate all possible placements for each stamp
        List<int[]>[] validPlacements = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            validPlacements[i] = new ArrayList<>();
        }
        
        // Count cells to cover in the logo
        int cellsToCover = 0;
        for (int i = 0; i < LOGO_SIZE; i++) {
            for (int j = 0; j < LOGO_SIZE; j++) {
                if (logo[i][j] == 1) cellsToCover++;
            }
        }
        
        if (cellsToCover == 0) return 0;
        
        // For each stamp, calculate all valid placements and what cells they cover
        for (int stampId = 0; stampId < n; stampId++) {
            for (int rotation = 0; rotation < 4; rotation++) {
                char[][] rotatedStamp = rotateStamp(stamps[stampId], rotation);
                
                // Try all possible positions
                for (int x = 0; x <= LOGO_SIZE - STAMP_SIZE; x++) {
                    for (int y = 0; y <= LOGO_SIZE - STAMP_SIZE; y++) {
                        // Check if this placement covers any logo cells
                        boolean coversAny = false;
                        boolean isValid = true;
                        
                        for (int i = 0; i < STAMP_SIZE && isValid; i++) {
                            for (int j = 0; j < STAMP_SIZE && isValid; j++) {
                                if (rotatedStamp[i][j] == 1) {
                                    if (logo[x+i][y+j] == 0) {
                                        isValid = false;
                                    } else {
                                        coversAny = true;
                                    }
                                }
                            }
                        }
                        
                        if (isValid && coversAny) {
                            validPlacements[stampId].add(new int[]{x, y, rotation});
                        }
                    }
                }
            }
        }
        
        // Start the search
        boolean[] used = new boolean[n];
        boolean[][] covered = new boolean[LOGO_SIZE][LOGO_SIZE];
        int[] best = {n + 1}; // Store best result
        
        search(0, 0, used, covered, validPlacements, stamps, logo, best, cellsToCover);
        
        return best[0] <= n ? best[0] : -1;
    }
    
    private void search(int index, int usedCount, boolean[] used, boolean[][] covered, 
                        List<int[]>[] validPlacements, char[][][] stamps, char[][] logo, 
                        int[] best, int totalCells) {
        
        // Check if we found a better solution
        int coveredCount = countCovered(covered);
        if (coveredCount == totalCells) {
            best[0] = Math.min(best[0], usedCount);
            return;
        }
        
        // If we've used too many stamps already or tried all stamps, return
        if (usedCount >= best[0] || index >= used.length) {
            return;
        }
        
        // Try not using this stamp
        search(index + 1, usedCount, used, covered, validPlacements, stamps, logo, best, totalCells);
        
        // Try using this stamp
        used[index] = true;
        
        // Try each valid placement for this stamp
        for (int[] placement : validPlacements[index]) {
            int x = placement[0];
            int y = placement[1];
            int rotation = placement[2];
            char[][] rotatedStamp = rotateStamp(stamps[index], rotation);
            
            // Apply the stamp
            boolean[][] originalCovered = new boolean[LOGO_SIZE][LOGO_SIZE];
            for (int i = 0; i < LOGO_SIZE; i++) {
                System.arraycopy(covered[i], 0, originalCovered[i], 0, LOGO_SIZE);
            }
            
            boolean addedCoverage = false;
            for (int i = 0; i < STAMP_SIZE; i++) {
                for (int j = 0; j < STAMP_SIZE; j++) {
                    if (rotatedStamp[i][j] == 1 && logo[x+i][y+j] == 1 && !covered[x+i][y+j]) {
                        covered[x+i][y+j] = true;
                        addedCoverage = true;
                    }
                }
            }
            
            // Only continue if this placement added new coverage
            if (addedCoverage) {
                search(index + 1, usedCount + 1, used, covered, validPlacements, stamps, logo, best, totalCells);
            }
            
            // Restore the original coverage
            for (int i = 0; i < LOGO_SIZE; i++) {
                System.arraycopy(originalCovered[i], 0, covered[i], 0, LOGO_SIZE);
            }
        }
        
        used[index] = false;
    }
    
    private char[][] rotateStamp(char[][] stamp, int rotation) {
        char[][] rotated = new char[STAMP_SIZE][STAMP_SIZE];
        
        switch (rotation) {
            case 0: // 0 degrees
                for (int i = 0; i < STAMP_SIZE; i++) {
                    for (int j = 0; j < STAMP_SIZE; j++) {
                        rotated[i][j] = stamp[i][j];
                    }
                }
                break;
                
            case 1: // 90 degrees
                for (int i = 0; i < STAMP_SIZE; i++) {
                    for (int j = 0; j < STAMP_SIZE; j++) {
                        rotated[i][j] = stamp[STAMP_SIZE - 1 - j][i];
                    }
                }
                break;
                
            case 2: // 180 degrees
                for (int i = 0; i < STAMP_SIZE; i++) {
                    for (int j = 0; j < STAMP_SIZE; j++) {
                        rotated[i][j] = stamp[STAMP_SIZE - 1 - i][STAMP_SIZE - 1 - j];
                    }
                }
                break;
                
            case 3: // 270 degrees
                for (int i = 0; i < STAMP_SIZE; i++) {
                    for (int j = 0; j < STAMP_SIZE; j++) {
                        rotated[i][j] = stamp[j][STAMP_SIZE - 1 - i];
                    }
                }
                break;
        }
        
        return rotated;
    }
    
    private int countCovered(boolean[][] covered) {
        int count = 0;
        for (int i = 0; i < LOGO_SIZE; i++) {
            for (int j = 0; j < LOGO_SIZE; j++) {
                if (covered[i][j]) count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        Judge.run(new LOG());
    }
}