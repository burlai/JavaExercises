package cnt;
import java.util.Scanner;

public class Judge {
	static long seed;
	static int nextRand() {
		seed = seed * 134775813 + 1;
		return (int)(((seed >> 31) & 0xFFFFFFFFL));
	}
	static long h;
	static void hashAdd(int value) {
		h = h * 10007 + value;
	}

	static public void run(CNT solution) {
		Scanner sc = new Scanner(System.in);
		seed = sc.nextInt();
		int TC = sc.nextInt();
		int mask = sc.nextInt();
		int fullOut = sc.nextInt();
		h = nextRand();

		for (int tc=1; tc<=TC; ++tc) {
			int value = nextRand() & mask;
			int res = solution.popCount(value);
			hashAdd(res);
			if (fullOut > 0) {
				System.out.println("#" + tc + ": " + value + " -> " + res);
			}
		}
		System.out.println("Hash: " + h);
	}
}

