package advancedTasks;

public class SHI {

	class vector {
		public int[] buf = new int[2];
		int mCapacity = 2;
		int mSize = 0;
		
		void grow() {
			mCapacity *= 2;
			int[] old = buf;
			buf = new int[mCapacity];
			for (int i=0; i<mSize; ++i)
				buf[i] = old[i];
		}
		
		public void push_back(int v) { if (mSize == mCapacity) grow(); buf[mSize++] = v; }
		public void pop_back() { --mSize; }
		public int back() { return buf[mSize - 1]; }
		public int size() { return mSize; }
		public void clear() { mSize = 0; }
	}

	int[][] sum = new int[65][65];
	int[][] cnt = new int[65][65];
	int[][] done = new int[64][64];
	int[][] solution = new int[64][64];

	static final int MAX_N = 64 * 64;
	int[] x1 = new int[MAX_N];
	int[] y1 = new int[MAX_N];
	int[] x2 = new int[MAX_N];
	int[] y2 = new int[MAX_N];
	int[] area = new int[MAX_N];
	int n;
	vector[][] rect = new vector[64][64];

	vector used = new vector();
	int h;
	int w;

	int myMin(int a, int b) { return a <= b ? a : b; }
	int myMax(int a, int b) { return a >= b ? a : b; }

	boolean colision(int i, int j) {
		return myMax(x1[i], x1[j]) <= myMin(x2[i], x2[j]) && myMax(y1[i], y1[j]) <= myMin(y2[i], y2[j]);
	}

	void foo(int i, int j) {
		while (true) {
			if (j == w) {
				j = 0;
				++i;
				if (i == h) {
					for (int x=0; x<h; ++x) for (int y=0; y<w; ++y)
						solution[x][y] = done[x][y];
					return;
				}
			}
			if (done[i][j] >= 0) {
				++j;
				continue;
			}
			break;
		}

		if (done[i][j] >= 0) {
			foo(i, j+1);
			return;
		}
		for (int k=0; k<rect[i][j].size(); ++k) {
			boolean ok = true;
			for (int p=0; p<used.size(); ++p)
				if (colision(used.buf[p], rect[i][j].buf[k]))
					{ ok = false; break; }
			if (ok == false)
				continue;
			used.push_back(rect[i][j].buf[k]);
			for (int ii=x1[used.back()]; ii<=x2[used.back()]; ++ii)
				for (int jj=y1[used.back()]; jj<=y2[used.back()]; ++jj)
					done[ii][jj] = used.back();
			foo(i, j+1);
			for (int ii=x1[used.back()]; ii<=x2[used.back()]; ++ii)
				for (int jj=y1[used.back()]; jj<=y2[used.back()]; ++jj)
					done[ii][jj] = -1;
			used.pop_back();
		}
	}

	public void solve(int height, int width, int[][] board) {
		// PRIVATE BEGIN
		h = height;
		w = width;
		for (int i=0; i<64; ++i) for (int j=0; j<64; ++j)
			rect[i][j] = new vector();
		for (int i=0; i<65; ++i)
			sum[i][0] = sum[0][i] = cnt[i][0] = cnt[0][i] = 0;
		for (int i=1; i<=h; ++i) for (int j=1; j<=w; ++j) {
			sum[i][j] = sum[i-1][j] + sum[i][j-1] - sum[i-1][j-1] + board[i-1][j-1];
			cnt[i][j] = cnt[i-1][j] + cnt[i][j-1] - cnt[i-1][j-1] + (board[i-1][j-1] > 0 ? 1 : 0);
		}
		n = 0;
		for (int i=0; i<h; ++i) for (int j=0; j<w; ++j) for (int ii=i; ii<h; ++ii) for (int jj=j; jj<w; ++jj) {
			if (cnt[ii+1][jj+1] + cnt[i][j] - cnt[ii+1][j] - cnt[i][jj+1] != 1)
				continue;
			area[n] = sum[ii+1][jj+1] + sum[i][j] - sum[ii+1][j] - sum[i][jj+1];
			if (area[n] != (ii-i+1) * (jj-j+1))
				continue;
			x1[n] = i;
			y1[n] = j;
			x2[n] = ii;
			y2[n] = jj;
			rect[i][j].push_back(n);
			++n;
		}
		for (int i=0; i<h; ++i) for (int j=0; j<w; ++j) done[i][j] = -1;
		used.clear();

		foo(0, 0);

		for (int i=0; i<h; ++i) for (int j=0; j<w; ++j)
			board[i][j] = solution[i][j];
		// PRIVATE END
	}

	public static void main(String[] args) {
//		Judge.run(new SHI());
	}
}