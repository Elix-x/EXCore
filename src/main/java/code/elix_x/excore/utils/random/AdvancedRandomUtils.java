package code.elix_x.excore.utils.random;

import java.util.Random;

public class AdvancedRandomUtils {

	public static double nextDouble(Random random, double d1, double d2){
		if(d1 == d2){
			return d1;
		}
		double l = Math.min(d1, d2);
		double u = Math.max(d1, d2);
		int add = 0;
		while(l < 0){
			l++;
			u++;
			add++;
		}
		while(true){
			double d = nextDouble(random, u);
			if(l < d && d < u){
				return d - add;
			}
		}
	}

	public static double nextDouble(Random random, double range){
		return nextDouble(random, range, 1000000);
	}

	public static double nextDouble(Random random, double range, int precisement){
		return nextDouble(random, range, precisement, false);
	}

	public static double nextDouble(Random random, double range, int precisement, boolean negAccepted){
		return random.nextBoolean() || !negAccepted ? random.nextInt((int) (range * precisement)) / (double) precisement : -(random.nextInt((int) (range * precisement)) / (double) precisement);
	}

}
