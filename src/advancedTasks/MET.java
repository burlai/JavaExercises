package advancedTasks;

public class MET {
    // PRIVATE BEGIN
    int[] a = new int[10];
    int[] b = new int[10];
    int[] c = new int[10];
    int[] d = new int[10];
    int[] t = new int[10];
    int n;
    int[] used = new int[5];

    int dist(int srcX, int srcY, int dstX, int dstY) {
        int dx = srcX - dstX; if (dx < 0) dx = 0 - dx;
        int dy = srcY - dstY; if (dy < 0) dy = 0 - dy;
        return dx + dy;
    }

    int solve(int srcX, int srcY, int dstX, int dstY) {
        int res = dist(srcX, srcY, dstX, dstY);
        for (int i=0; i<n; ++i) if (used[i/2] == 0) {
            used[i/2] = 1;
            int cur = dist(srcX, srcY, a[i], b[i]) + t[i] + solve(c[i], d[i], dstX, dstY);
            if (cur < res)
                res = cur;
            used[i/2] = 0;
        }
        return res;
    }

    // PRIVATE END
    void init() {
        // PRIVATE BEGIN
        n = 0;
        for (int i=0; i<5; ++i) used[i] = 0;
        // PRIVATE END
    }

    void addSubway(int srcX, int srcY, int dstX, int dstY, int tripDuration) {
        // PRIVATE BEGIN
        a[n] = srcX;
        b[n] = srcY;
        c[n] = dstX;
        d[n] = dstY;
        t[n] = tripDuration;
        ++n;
        c[n] = srcX;
        d[n] = srcY;
        a[n] = dstX;
        b[n] = dstY;
        t[n] = tripDuration;
        ++n;
        // PRIVATE END
    }

    int getTripDuration(int srcX, int srcY, int dstX, int dstY) {
        // PRIVATE BEGIN
        return solve(srcX, srcY, dstX, dstY);
        // PRIVATE END
        // PUBLIC return -1;
    }

    public static void main(String[] args) {
//        Judge.run(new MET());
    }
}