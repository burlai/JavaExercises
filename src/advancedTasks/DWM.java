package advancedTasks;

public class DWM {
	// PRIVATE BEGIN

	int height;
	int width;
	int[][] data = new int[6][12];

	int myMax(int a, int b) { return a >= b ? a : b; }

	int[][] maskList1 = new int[64][64];
	int[][] maskList2 = new int[64][64];
	int[] maskListN = new int[64];
	int[] colMask = new int[12];

	void maskGen(int mask, int todo, int cnt) {
		if (todo == 0) {
			for (int i=(1 << height)-1; i>=0; --i)
				if ((mask & i) == 0)
				{
					maskList1[i][maskListN[i]] = mask;
					maskList2[i][maskListN[i]] = cnt;
					++maskListN[i];
				}
			return;
		}
		maskGen(2 * mask, todo - 1, cnt);
		if (todo >= 2) maskGen(4 * mask + 3, todo - 2, cnt + 1);
	}

	int[][] mem = new int[12][64];

	int reqPROF(int c, int mask) {
		if (c + 1 == width) return 0;
		if (mem[c][mask] != -1) return mem[c][mask];
		int res = 0;
		int curMask = mask | colMask[c] | colMask[c + 1];
		for (int i=0; i<maskListN[curMask]; ++i) {
			int cur = maskList2[curMask][i] + reqPROF(c + 1, maskList1[curMask][i]);
			res = myMax(res, cur);
		}
		mem[c][mask] = res;
		return res;
	}

	int solvePROF(int h, int w, int[][] t) {
		int res = 0;
		height = h;
		width = w;
		for (int i=0; i<h; ++i) for (int j=0; j<w; ++j) {
			data[i][j] = 1 - t[i][j];
			if (data[i][j] == 0) ++res;
		}
		maskGen(0, height, 0);
		for (int j=0; j<width; ++j) colMask[j] = 0;
		for (int i=0; i<height; ++i) for (int j=0; j<width; ++j)
			colMask[j] = 2 * colMask[j] + data[i][j];
		for (int c=0; c<width; ++c) for (int i=0; i<(1<<height); ++i) mem[c][i] = -1;
		res -= 3 * reqPROF(0, 0);
		return res;
	}

	// PRIVATE END
	public int minimumCoursesNumber(int height, int width, int[][] virus) {
		int res = -1;
		// PRIVATE BEGIN
		res = solvePROF(height, width, virus);
		// PRIVATE END
		return res;
	}

	public static void main(String[] args) {
//		Judge.run(new DWM());
	}
}
