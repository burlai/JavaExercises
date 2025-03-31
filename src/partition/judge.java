package partition;
import java.util.Scanner;

public class judge {
	static void run(Partition solution) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int t = 0; t < T; t++) {
			int N = sc.nextInt();
			int[] array = new int[N];
			for(int i=0;i<N;i++) {
				array[i] = sc.nextInt();
			}
			System.out.println(solution.checkPartition(array, array.length) ? 1 : 0);
		}
	}
}
