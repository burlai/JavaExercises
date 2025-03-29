package advancedTasks;

public class TUR {
	// PRIVATE BEGIN
	int n;
	final int MAXN = 100;
	int[] data = new int[MAXN];
	void bcopy(int[] src, int[] dst) {
		for (int i=0; i<n; ++i) dst[i] = src[i];
	}
	int bresult(int[] src) {
		int res = 0;
		for (int i=0; i<n; ++i) res += src[i];
		return res;
	}
	void bmove(int[] src, int pos, int val) {
//System.err.println("P " + pos);
		src[pos] = val;

		int left = pos - 1;
//System.err.println("A");
		while (left >= 0 && src[left] == -val) {
			--left;
//System.err.println("B " + left);
		}
		if (left < 0 || src[left] == val) {
			for (int i=left+1; i<pos; ++i) {
//System.err.println("C " + i);
				src[i] = val;
			}
		}

		int right = pos + 1;
//System.err.println("D " + right + " " + n);
		while (right < n && src[right] == -val) {
			++right;
//System.err.println("E " + right);
		}
//System.err.println("Y");
		if (right >= n || src[right] == val) {
			for (int i=pos+1; i<right; ++i) {
//System.err.println("F " + i);
				src[i] = val;
			}
		}
//System.err.println("X");
	}
	int best;
	int[][] state = new int[4][MAXN];
	int[] state2 = new int[MAXN];
	void solve(int steps) {
//System.err.println("solve ( " + steps + ")");
		if (0 == steps) {
			int cur = bresult(data);
			if (cur > best) best = cur;
			return;
		}
		bcopy(data, state[steps]);
//System.err.println("start " + steps);
		for (int i=0; i<n; ++i) if (data[i] == 0) {
//System.err.println("solve " + steps + " i=" + i);
			bmove(data, i, 1);
			bcopy(data, state2);
			int localBest = MAXN + 1;
			int localMove = -1;
			for (int j=0; j<n; ++j) if (data[j] == 0) {
				bmove(data, j, -1);
				int localCur = bresult(data);
//System.err.println("j=" + j + " localCur=" + localCur + " localBest=" + localBest);
				if (localCur < localBest) {
					localBest = localCur;
					localMove = j;
				}
				bcopy(state2, data);
			}
//System.err.println("PUPA " + localMove);
			bmove(data, localMove, -1);
			solve(steps - 1);
			bcopy(state[steps], data);
		}
	}
	// PRIVATE END
	public int getBestResult(int[] board, int size) {
		// PRIVATE BEGIN
		n = size;
		for (int i=0; i<n; ++i) {
			data[i] = board[i];
			if (data[i] == 2) data[i] = -1;
		}
		best = 0 - MAXN - 1;
		solve(3);
		return best;
		// PRIVATE END
		// PUBLIC return -1;
	}
	public static void main(String[] args) {
//		Judge.run(new TUR());
	}
}

