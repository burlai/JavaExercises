package container;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class judge {
	static long seed = 0;
	static final int MOD = 1000000007;

	static int pseudo_rand() {
		seed = seed * 13477581 + 1;
		return (((int)seed) & 0x7FFFFFFF) % MOD;
	}


    static void run(container k) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {


			long  hash = 0;
			char type=in .readLine().charAt(0);
			if (type == 'R') {
			String[] line = in .readLine().split(" ");
            int size = Integer.parseInt(line[0]);
            int operations = Integer.parseInt(line[1]);
            seed = Integer.parseInt(line[2]);
			k.init(size);
			for (int o = 0; o < operations; o++) {
				int which = pseudo_rand() % 3;
				boolean ret;
				if (which == 0) {
					ret = k.add(pseudo_rand() % size);
				} else if (which == 1) {
					ret = k.contains(pseudo_rand() % size);
				} else {
					ret = k.remove(pseudo_rand() % size);
				}
				hash = ((10009 * hash) + (ret ? 1 : 0)) % 1000000007;


			}
			} else {
				String[] line = in .readLine().split(" ");
				int size = Integer.parseInt(line[0]);
				int operations = Integer.parseInt(line[1]);
				k.init(size);
				char which;
				int a;
				for (int o = 0; o < operations; o++) {
					String l = in .readLine();
					which = l.charAt(0);
					a = Integer.parseInt(l.split(" ")[1]);
					boolean ret;
			if (which == 'A') {
				ret = k.add(a);
			} else if (which == 'C') {
				ret = k.contains(a);
			} else {
				ret = k.remove(a);
			}
			hash = ((10009 * hash) + (ret ? 1 : 0)) % 1000000007;

		}


			}


            System.out.println(hash);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}