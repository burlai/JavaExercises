package advancedTasks;

class State {
	int[][] data = new int[5][256];
	int[] h = new int[5];
	boolean[][] remove = new boolean[5][256];
	int score = 0;

	boolean DEBUG1 = false;

	int eval() {
		if (DEBUG1) {
			System.out.println("BEFORE");
			print();
			System.out.print("\n");
		}
		int value = 0;
		int stepValue = 1;
		for (int step = 1; stepValue != 0; step++) {
			stepValue = step() * step;
			// System.out.println("Step value: " + stepValue);
			value += stepValue;
		}
		score += value;
		if (DEBUG1) {
			System.out.println("SCORE: " + (score - value) + " + " + value + " = " + score);
			if (value > 0) {
				System.out.println("AFTER");
				print();
			}
			System.out.print("\n\n");
		}
		return value;
	}

	private int step() {
		int lines = 0;
		int sum = 0;
		resetRemove();

		// VERTICAL LINES
		int last = 0;
		int count = 0;
		for (int i = 0; i < 5; i++) {
			last = 0;
			count = 0;
			for (int j = 0; j <= h[i]; j++) {
				if (data[i][j] == last) {
					count++;
				} else {
					if (count >= 3) {
						// System.out.println("Line: Vertical " + count + " (" + i + "," + j + ")");
						lines++;
						sum += count * count;
						for (int k = j - 1; k + count >= j; k--) {
							remove[i][k] = true;
						}
					}
					last = i > 4 ? 0 : data[i][j];
					count = 1;
				}
			}
		}

		// HORIZONTAL LINES
		int H = getH();
		for (int j = 0; j < H; j++) {
			last = 0;
			count = 0;
			for (int i = 0; i < 6; i++) {
				if (i < 5 && data[i][j] != 0 && data[i][j] == last) {
					count++;
				} else {
					if (count >= 3) {
						// System.out.println("Line: Horizontal " + count + " (" + i + "," + j + ")");
						lines++;
						sum += count * count;
						for (int k = i - 1; k + count >= i; k--) {
							remove[k][j] = true;
						}
					}
					last = i > 4 ? 0 : data[i][j];
					count = 1;
				}
			}
		}

		// DIAGONAL LINES A
		for (int j = 0; j < H + 6; j++) {
			last = 0;
			count = 0;
			for (int i = 0; i < 6; i++) {
				int jj = j - i;
				if (i < 5 && jj >= 0 && data[i][jj] != 0 && data[i][jj] == last) {
					count++;
				} else {
					if (count >= 3) {
						// System.out.println("Line: DiagA " + count + " (" + i + "," + j + ")");
						lines++;
						sum += count * count;
						for (int k = 1; k <= count; k++) {
							remove[i - k][jj + k] = true;
						}
					}
					last = i > 4 ? 0 : jj < 0 ? 0 : data[i][jj];
					count = 1;
				}
			}
		}

		// DIAGONAL LINES B
		for (int j = 0; j < H + 6; j++) {
			last = 0;
			count = 0;
			for (int i = 0; i < 6; i++) {
				int ii = 4 - i;
				int jj = j - i;
				if (i < 5 && jj >= 0 && data[ii][jj] != 0 && data[ii][jj] == last) {
					count++;
				} else {
					if (count >= 3) {
						// System.out.println("Line: DiagA " + count + " (" + i + "," + j + ")");
						lines++;
						sum += count * count;
						for (int k = 1; k <= count; k++) {
							remove[ii + k][jj + k] = true;
						}
					}
					last = i > 4 ? 0 : jj < 0 ? 0 : data[ii][jj];
					count = 1;
				}
			}
		}

		applyRemove();

		// System.out.println("Lines: " + lines + " value: " + sum);

		return sum * lines;
	}

	private int getH() {
		int H = 0;
		for (int i = 0; i < 5; i++)
			if (h[i] > H) H = h[i];
		return H;
	}

	private void resetRemove() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < h[i]; j++) {
				remove[i][j] = false;
			}
		}
	}

	private void applyRemove() {
		int[] temp = new int[256];

		for (int i = 0; i < 5; i++) {
			int t = 0;
			for (int j = 0; j < h[i]; j++) {
				if (remove[i][j] == false) {
					temp[t++] = data[i][j];
				}
			}
			for (int j = 0; j < t; j++) {
				data[i][j] = temp[j];
			}
			for (int j = t; j < h[i]; j++) {
				data[i][j] = 0;
			}
			h[i] = t;
		}
	}

	void load(int[][] state) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < state[i].length; j++) {
				data[i][j] = state[i][j];
				if (data[i][j] > 0) h[i] = j + 1;
			}
		}
	}

	void print() {
		int H = getH();
		for (int j = H - 1; j >= 0; j--) {
			for (int i = 0; i < 5; i++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		}
	}

	State() {

	}

	State(State other) {
		for (int i = 0; i < 5; i++) {
			h[i] = other.h[i];
			for (int j = 0; j < h[i]; j++) {
				data[i][j] = other.data[i][j];
			}
		}
		score = other.score;
	}

	void addPiece(int col, int[] piece) {
		for (int i = 0; i < 3; i++) {
			data[col][h[col] + i] = piece[i];
		}
		h[col] += 3;
	}
}

class Answer {
	Answer(int score, String history, State state) {
		this.score = score;
		this.history = history;
		this.state = state;
	}

	int score;
	String history;
	State state;
}

public class TET {
	int[] up;
	int[] mid;
	int[] down;
	int[] col;
	int piecesCount;

	Answer solve(State s, int move, int changesLeft, String history) {
		if (move == piecesCount) {
			// System.out.println(history + " " + s.score);
			return new Answer(s.score, history, s);
		}

		Answer best = new Answer(0, "ERROR", null);

		int[] piece = { down[move], mid[move], up[move] };
		if (changesLeft > 0) {
			for (int rot = 0; rot < 3; rot++) {
				for (int i = 0; i < 5; i++) {
					// if (move != 6 || i != 3 || rot != 1) continue;
					State ss = new State(s);
					ss.addPiece(i, piece);
					ss.eval();
					Answer score = solve(ss, move + 1, changesLeft - 1,
							history + " move:" + move + " col" + i + " rot" + rot);
					if (score.score > best.score) best = score;
				}

				int tmp = piece[0];
				piece[0] = piece[1];
				piece[1] = piece[2];
				piece[2] = tmp;
			}
		}
		s.addPiece(col[move], piece);
		s.eval();
		Answer score = solve(s, move + 1, changesLeft, history);
		if (score.score > best.score) best = score;
		return best;
	}

	boolean done = false;

	public int getMaxScore(int count, int[] up, int[] mid, int[] down, int[] col, int changeCount) {
		if (done) {
			System.exit(0);
		}
		piecesCount = count;
		this.up = up;
		this.mid = mid;
		this.down = down;
		this.col = col;
		Answer res = solve(new State(), 0, changeCount, "");
//		System.out.println(res.history);
//		res.state.print();
//		done = true;
		return res.score;
	}

	public static void main(String[] args) {
		int[][] test = {
				{ 2, 2, 1, 2 },
				{ 2, 1, 2, 2 },
				{ 1, 1, 2, 0 },
				{ 2, 1, 3 },
				{ 2, 1, 3 },
		};
		int[][] test2 = {
				{ 2, 1, 3 },
				{ 2, 1, 3 },
				{ 1, 1, 2, 0 },
				{ 2, 1, 2, 2 },
				{ 2, 2, 1, 2 },
		};
		
		int[][] test3 = {
				{ 1, 2, 2 },
				{ 2, 1, 2 },
				{ 2, 2, 1 },
				{ 1, 1, 2 },
				{ 1, 1, 2 },
		};
//				
//				2 2 1 2 2 
//				2 1 2 1 1 
//				1 2 2 1 1 


		
//		State s = new State();
//		s.load(test3);
//		System.out.println(s.eval());
//		Judge.run(new TET());
	}
}