package advancedTasks;

public class FUN {

	long f(long x, int A, int B, int C, int D, int E) {
		long a = x;
		long b = x * log(x);
		long c = x * sqrt(x);
		long d = x * x;
		long e = x * x * x;
		a = A * a;
		b = B * b;
		c = C * c;
		d = D * d;
		e = E * e;
		if (a < 0) return -1;
		if (b < 0) return -1;
		if (c < 0) return -1;
		if (d < 0) return -1;
		if (e < 0) return -1;
		//System.out.println("f: " + A + " + " + B + " + " + C + " + " + D + " + " + E);
		//System.out.println("f(" + x + ") = " + a + " + " + b + " + " + c + " + " + d + " + " + e);
		// System.out.println("f(" + x + ") = " + (a + b + c + d + e));
		return a + b + c + d + e;
	}

	private long sqrt(long x) {
		if(x < 4) return 1;
		long a = 2;
		long b = x;
		while(a + 1 < b) {
			long c = (a + b) / 2;
			long cc = c * c;
			if(cc == x) return c;
			else if(cc >= 0 && cc < x) a = c;
			else b = c;
		}
		return a;
	}

	static final long A = 1 << 62;

	private int log(long x) {
		if (x >= (1L << 62)) return 62;
		for (int i = 1; i < 62; i++) {
			long pow = 1L << i;
			if (x < pow) return i - 1;
		}
		return -1;
	}

	public long getX(int A, int B, int C, int D, int E, long value) {
		//System.out.println("getX: " + A + " + " + B + " + " + C + " + " + D + " + " + E);
		long x = 2;
		for (;; x = x + x / 8 + 1) {
			long v = f(x, A, B, C, D, E);
			if (v <= 0) break;
			if (v >= value) break;
		}
		long min = x / 2;
		long max = x;

		while (min + 1 < max) {
			x = (min + max) / 2;
			long v = f(x, A, B, C, D, E);
			if (v >= 0 && v < value) {
				min = x;
			} else {
				max = x;
			}
		}

		long v = f(min, A, B, C, D, E);
		if (v == value) return min;

		v = f(max, A, B, C, D, E);
		if (v == value) return max;
		
		return -1;
	}

	public static void main(String[] args) {
//		Judge.run(new FUN());
	}
}