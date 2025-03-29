package advancedTasks;

public class DOM {
	// PRIVATE BEGIN

	final int MAX_N = 50;
	final int MAX_D = 100;
	int n;
	int h;
	int w;
	int[][] board = new int[66][66];
	int[][] result = new int[66][66];
	int[] todo = new int[MAX_D];
	int[][][] usedCell = new int[MAX_D][66][66];
	int[][][] usedBrick = new int[MAX_D][66][66];

	int[][][] cnt  = new int[MAX_D][MAX_N][MAX_N];
	int[][][] posr = new int[MAX_D][MAX_N][MAX_N];
	int[][][] posc = new int[MAX_D][MAX_N][MAX_N];
	int[][][] orie = new int[MAX_D][MAX_N][MAX_N];

	void mySolve1(int nn, int hh, int ww, int[][] bb, int[][] rr) {
		// copy data from params => own structure
		n = nn;
		h = hh;
		w = ww;
		for (int r=0; r<=h+1; ++r) board[r][0] = board[r][w+1] = n + 1;
		for (int c=0; c<=w+1; ++c) board[0][c] = board[h+1][c] = n + 1;
		for (int i=0; i<h; ++i) for (int j=0; j<w; ++j) board[i+1][j+1] = bb[i][j];

		// fill init state
		int sid = 0;
		todo[sid] = w * h / 2;
		for (int r=0; r<=h+1; ++r) for (int c=0; c<=w+1; ++c)
			usedCell[sid][r][c] = board[r][c] <= n ? -1 : 9999;
		for (int i=0; i<=n; ++i) for (int j=i; j<=n; ++j)
			usedBrick[sid][i][j] = -1;

		mySolve2(sid);

		// copy result from own structure => param bufor
		for (int i=0; i<h; ++i) for (int j=0; j<w; ++j) rr[i][j] = result[i+1][j+1];
	}

	void mySolve2(int sid) {
		if (todo[sid] == 0) {
			for (int r=1; r<=h; ++r) for (int c=1; c<=w; ++c) result[r][c] = usedCell[sid][r][c];
			return;
		}

		int orgTodo = todo[sid];

		// single neighbour of cell
		for (int r=1; r<=h; ++r) for (int c=1; c<=w; ++c) if (usedCell[sid][r][c] < 0) {
			int count = 0;
			if (usedCell[sid][r-1][c] < 0) ++count;
			if (usedCell[sid][r+1][c] < 0) ++count;
			if (usedCell[sid][r][c-1] < 0) ++count;
			if (usedCell[sid][r][c+1] < 0) ++count;
			if (count == 0) return;
			if (count > 1) continue;
			int a1 = r;
			int b1 = c;
			int a2 = r;
			int b2 = c;
			if (usedCell[sid][r-1][c] < 0) --a2;
			if (usedCell[sid][r+1][c] < 0) ++a2;
			if (usedCell[sid][r][c-1] < 0) --b2;
			if (usedCell[sid][r][c+1] < 0) ++b2;
			int i = board[a1][b1];
			int j = board[a2][b2];
			if (i > j) { int t=i; i=j; j=t; }
			int bid = 100 * i + j;
			usedCell[sid][a1][b1] = bid;
			usedCell[sid][a2][b2] = bid;
			usedBrick[sid][i][j] = 0;
			--todo[sid];
		}

		// single brick possibility
		for (int i=0; i<=n; ++i) for (int j=i; j<=n; ++j) cnt[sid][i][j] = 0;
		for (int r=1; r<=h; ++r) for (int c=1; c<=w; ++c) {
			if (usedCell[sid][r][c] >= 0) continue;
			if (usedCell[sid][r+1][c] < 0) {
				int a = board[r][c];
				int b = board[r+1][c];
				if (a > b) { int t=a; a=b; b=t; }
				if (usedBrick[sid][a][b] < 0) {
					cnt[sid][a][b]++;
					posr[sid][a][b] = r;
					posc[sid][a][b] = c;
					orie[sid][a][b] = 1; // vertical
				}
			}
			if (usedCell[sid][r][c+1] < 0) {
				int a = board[r][c];
				int b = board[r][c+1];
				if (a > b) { int t=a; a=b; b=t; }
				if (usedBrick[sid][a][b] < 0) {
					cnt[sid][a][b]++;
					posr[sid][a][b] = r;
					posc[sid][a][b] = c;
					orie[sid][a][b] = 2; // horizontal
				}
			}
		}
		for (int i=0; i<=n; ++i) for (int j=i; j<=n; ++j) if (usedBrick[sid][i][j] < 0 && cnt[sid][i][j] == 0) return;
		for (int i=0; i<=n; ++i) for (int j=i; j<=n; ++j) if (usedBrick[sid][i][j] < 0 && cnt[sid][i][j] == 1) {
			int bid = 100 * i + j;
			// brick = i, j
			int a1 = posr[sid][i][j];
			int b1 = posc[sid][i][j];
			// cell1 = a1, b1
			int a2 = a1;
			int b2 = b1;
			if (orie[sid][i][j] == 1) ++a2; else ++b2;
			// cell2 = a2, b2

			usedCell[sid][a1][b1] = bid;
			usedCell[sid][a2][b2] = bid;
			usedBrick[sid][i][j] = 0;
			--todo[sid];
		}

		if (orgTodo != todo[sid]) {
			mySolve2(sid);
			return;
		}

		// split solution on first unassigned cell
		for (int r=1; r<=h; ++r) for (int c=1; c<=w; ++c) if (usedCell[sid][r][c] < 0) {
			int a1 = r;
			int b1 = c;
			{
				todo[sid+1] = todo[sid];
				for (int rrr=h+1; rrr>=0; --rrr) for (int ccc=w+1; ccc>=0; --ccc) {
					usedCell[sid+1][rrr][ccc] = usedCell[sid][rrr][ccc];
					usedBrick[sid+1][rrr][ccc] = usedBrick[sid][rrr][ccc];
				}
				int a2 = a1 + 1;
				int b2 = b1;
				int i = board[a1][b1];
				int j = board[a2][b2];
				if (i > j) { int t=i; i=j; j=t; }
				int bid = 100 * i + j;
				usedCell[sid+1][a1][b1] = bid;
				usedCell[sid+1][a2][b2] = bid;
				usedBrick[sid+1][i][j] = 0;
				--todo[sid+1];
				mySolve2(sid + 1);
			}

			{
				int a2 = a1;
				int b2 = b1 + 1;
				int i = board[a1][b1];
				int j = board[a2][b2];
				if (i > j) { int t=i; i=j; j=t; }
				int bid = 100 * i + j;
				usedCell[sid][a1][b1] = bid;
				usedCell[sid][a2][b2] = bid;
				usedBrick[sid][i][j] = 0;
				--todo[sid];
				mySolve2(sid);
			}
			return;
		}
	}

	// PRIVATE END

	void solve(int count, int height, int width, int[][] board, int[][] result) {
		// PRIVATE BEGIN
		mySolve1(count, height, width, board, result);
		// PRIVATE END
	}

	public static void main(String[] args) {
//		Judge.run(new DOM());
	}
}
