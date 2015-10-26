package code.elix_x.excore.utils.math;

public class AdvancedMathUtils {

	public static int average(int i, int... is) {
		for(double ii : is){
			i += ii;
		}
		return i / (is.length + 1);
	}
	
	public static double average(double d, double... ds){
		for(double dd : ds){
			d += dd;
		}
		return d / (ds.length + 1);
	}
	
}
