package advancedTasks;

public class DIA {
	int best;
	int[] bestPath;
	int[] path;
	int[][] graph;
	int N;

	public int minimumTreatmentDuration(int cityCount, int[][] duration, int[] order) {
		N = cityCount;
		path = new int[N];
		bestPath = order;
		best = 0x7fffffff;
		graph = duration;
		solve(0, -1, 0, 0);
		return best;
	}

	private void solve(int count, int last, int mask, int dur) {
		if(dur > best) return;
		
		if (count == N) {
			dur += graph[last][path[0]];
			if (dur < best) {
				// System.out.println("BEST " + dur);
				best = dur;
				for (int i = 0; i < N; i++) {
					bestPath[i] = path[i];
				}
			}
		}
		else for (int i = 0; i < N; i++) {
			int bit = 1 << i;
			if ((mask & bit) != 0) continue;
			int dist = last == -1 ? 0 : graph[last][i];
			// System.out.println(last + " -> " + i + " : " + dist);
			path[count] = i;
			solve(count + 1, i, mask | bit, dur + dist);
		}
	}

	public static void main(String[] args) {
//		Judge.run(new DIA());
	}
}