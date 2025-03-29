package advancedTasks;

public class LCH {
	boolean[][] graph;
	char[] tree;

	int graphSize = 0;
	int lastLeaf = -1;
	int firstLeaf = -1;

	void printGraph() {
		for (int i = 0; i < graphSize; i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < graphSize; j++) {
				if (graph[i][j]) System.out.print(j + ", ");
			}
			System.out.println();
		}
		System.out.println();
	}

	void add(int a, int b) {
		graph[a][b] = true;
		graph[b][a] = true;
	}

	int load(int parent, int index) {
		if (index >= tree.length) return 0;
		int size = tree[index++] - '0';
		int v = graphSize++;
		add(parent, v);
		for (int i = 0; i < size; i++) {
			index = load(v, index);
		}
		if (size == 0) {
			if (firstLeaf == -1) {
				firstLeaf = v;
				lastLeaf = v;
			} else {
				add(v, lastLeaf);
				lastLeaf = v;
			}
		}
		return index;
	}

	boolean[] used;

	int countCycles() {
		used = new boolean[graphSize];
		return countCycles(0, 1);
	}

	int countCycles(int pos, int size) {
//		System.out.println(size + " " + pos);
		if (size == graphSize) return graph[0][pos] ? 1 : 0;
		used[pos] = true;
		int counter = 0;

		for (int i = 0; i < graphSize; i++) {
			if (used[i] || (graph[pos][i] == false)) continue;
			counter += countCycles(i, size + 1);
		}

		used[pos] = false;
		return counter;
	}

	public int getHamiltonCycleCount(char[] tree) {
		graph = new boolean[32][32];
		this.tree = tree;
		graphSize = 1;
		lastLeaf = -1;
		firstLeaf = -1;

		int index = 1;
		for (int i = 0; i < 3; i++) {
			index = load(0, index);
		}
		if (firstLeaf != lastLeaf) {
			add(firstLeaf, lastLeaf);
		}

		//printGraph();

		return countCycles() / 2;
	}

	public static void main(String[] args) {
//		Judge.run(new LCH());
	}
}