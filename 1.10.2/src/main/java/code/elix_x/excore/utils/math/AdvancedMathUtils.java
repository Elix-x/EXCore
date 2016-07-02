package code.elix_x.excore.utils.math;

public class AdvancedMathUtils {

	public static int average(int... is){
		if(is.length == 0) return 0;
		int i = 0;
		for(double ii : is){
			i += ii;
		}
		return i / is.length;
	}

	public static float average(float... fs){
		if(fs.length == 0) return 0;
		float f = 0;
		for(double ff : fs){
			f += ff;
		}
		return f / fs.length;
	}

	public static double average(double... ds){
		if(ds.length == 0) return 0;
		double d = 0;
		for(double dd : ds){
			d += dd;
		}
		return d / ds.length;
	}

}
