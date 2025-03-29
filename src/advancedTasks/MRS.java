package advancedTasks;

public class MRS {
	int[][] COUNT;
	int[] ALL;
	int N;

	int count(int n, int sum) {
		int answer = 0;
		int limit = 9;
		if (sum < limit)
			limit = sum;
		for (int i = 0; i <= limit; i++) {
			answer += COUNT[n - 1][sum - i];
		}
		return answer;
	}

	MRS(int N) {
		this.N = N;
		ALL = new int[N + 1];
		COUNT = new int[N + 1][9 * N + 1];

		ALL[0] = COUNT[0][0] = 1;
		for (int i = 1; i <= N; i++) {
			for (int j = 0; j <= 9 * i; j++) {
				COUNT[i][j] = count(i, j);
				ALL[i] += COUNT[i][j];
//				System.out.printf("%4d ", COUNT[i][j]);
			}
//			System.out.println("(" + ALL[i] + ")");
		}
	}

	int countSmaller(int digits, int sum) {
		int answer = 0;
		for (int i = 1; i < sum; i++) {
			// System.out.print(" + " + COUNT[digits][i] + " - " + COUNT[digits - 1][i]);
			answer += COUNT[digits][i] - COUNT[digits - 1][i];
		}
		// System.out.println(" countSmaller("+digits+" "+sum + ") = " + answer);
		return answer;
	}

	int countEqual(int digits, int value, int sum, int minStart) {
		if (digits == 1)
			return 0;
		int first = value / ALL[digits - 1];
		int answer = 0;

		int maxStart = first - 1;
		if (sum < maxStart)
			maxStart = sum;

		for (int i = minStart; i <= maxStart; i++) {
			answer += COUNT[digits - 1][sum - i];
		}
		return answer + countEqual(digits - 1, value - first * ALL[digits - 1], sum - first, 0);
	}

	public int getPosition(int value) {
		int digits = 0;
		for (int i = 1; i <= N; i++) {
			if (ALL[i] > value) {
				digits = i;
				break;
			}
		}
		int copy = value;
		int sum = 0;
		while (copy > 0) {
			sum += copy % 10;
			copy /= 10;
		}
		int smaller = countSmaller(digits, sum);
		int equal = countEqual(digits, value, sum, 1);
//		System.out.println(value + "("+(smaller+equal)+") d:" + digits + " s:" + sum + " sm:" + smaller + " eq:" + equal);
		return smaller + equal;
	}

	public static void main(String[] args) {
		MRS solution = new MRS(3);
//		solution.getPosition(10); // => 0
//		solution.getPosition(11); // => 1
//		solution.getPosition(12); // => 3
//		solution.getPosition(13); // => 6
//		solution.getPosition(95); // => 79
//		solution.getPosition(96); // => 83
//		solution.getPosition(97); // => 86
//		solution.getPosition(98); // => 88
//		solution.getPosition(100); // => 0
//		solution.getPosition(101); // => 1
//		solution.getPosition(110); // => 2
//		solution.getPosition(200); // => 3
//		solution.getPosition(102); // => 4
//		solution.getPosition(111); // => 5
//		solution.getPosition(120); // => 6
//		solution.getPosition(201); // => 7
//		Judge.run(new MRS(8));
	}
}