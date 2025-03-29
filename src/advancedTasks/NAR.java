package advancedTasks;

public class NAR {
	int data[][];
	int dist[][];
	int N;

	int solve(int n, int[][] data) {
		this.data = data;
		this.dist = new int[n][n];
		this.N = n;
		int max = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int d = dfs(i, j);
				if (d > max) max = d;
			}
		}
		return max;
	}

	int[][] dirs = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

	private int dfs(int i, int j) {
		if (dist[i][j] != 0) return dist[i][j];
		int choice = -1;
		int min = data[i][j];
		for (int k = 0; k < 4; k++) {
			int x = i + dirs[k][0];
			int y = j + dirs[k][1];
			if (x < 0 || x >= N || y < 0 || y >= N) {
				continue;
			}
			if (data[x][y] < min) {
				min = data[x][y];
				choice = k;
			}
		}
		int answer = 1;
		if (choice > -1) {
			answer += dfs(i + dirs[choice][0], j + dirs[choice][1]);
		}
		dist[i][j] = answer;
		return dist[i][j];
	}

	public static void main(String[] args) {
//		Judge.run(new NAR());
	}
}