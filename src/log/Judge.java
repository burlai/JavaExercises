package log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Judge {
	static char s0[][] = {
			{0, 0, 0, 1},
			{0, 1, 1, 1},
			{0, 1, 0, 1},
			{0, 1, 1, 1}
	};
	static char s1[][] = {
			{0, 0, 0, 0},
			{0, 1, 1, 0},
			{0, 0, 0, 0},
			{0, 0, 0, 0}
	};
	static char s2[][] = {
			{0, 0, 1, 1},
			{0, 0, 1, 0},
			{0, 1, 1, 1},
			{0, 0, 0, 0}
	};
	static char s3[][] = {
			{0, 1, 1, 1},
			{0, 0, 0, 1},
			{0, 1, 1, 1},
			{0, 0, 0, 1}
	};
	static char p1[][] = {
		    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		    {1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
		    {0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
		    {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
		    {0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
		    {1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
		    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	};
	
	static char[][] pattern = new char[10][10];
	static char[][][] stamps = new char[10][4][4];


	private static void read(BufferedReader br, int n, char[][] output) throws IOException {
		for(int i = 0; i < n; i++) {
			String[] line = br.readLine().split(" ");
			for(int j = 0; j < n; j++) {
				output[i][j] = (char) Integer.parseInt(line[j]);
			}
		}
	}
	
	public static void run(LOG solution) throws IOException {
		System.out.println("log Judge run");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int t = 0; t < T; t++) {
			read(br, 10, pattern);
			int N = Integer.parseInt(br.readLine());
			for(int i = 0; i < N; i++) {
				read(br, 4, stamps[i]);
			}
			
			int result = solution.solve(N, pattern, stamps);
			System.out.println(result);
		}
	}
}
