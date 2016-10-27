package code.elix_x.excore.utils.lists;

import java.util.ArrayList;

public class ListUtils {

	public static ArrayList<Boolean> newArrayList(boolean... bs){
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		for(boolean b : bs){
			list.add(b);
		}
		return list;
	}

	public static ArrayList<Byte> newArrayList(byte... bs){
		ArrayList<Byte> list = new ArrayList<Byte>();
		for(byte b : bs){
			list.add(b);
		}
		return list;
	}

	public static ArrayList<Short> newArrayList(short... ss){
		ArrayList<Short> list = new ArrayList<Short>();
		for(short s : ss){
			list.add(s);
		}
		return list;
	}

	public static ArrayList<Integer> newArrayList(int... is){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i : is){
			list.add(i);
		}
		return list;
	}

	public static ArrayList<Long> newArrayList(long... ls){
		ArrayList<Long> list = new ArrayList<Long>();
		for(long l : ls){
			list.add(l);
		}
		return list;
	}

	public static ArrayList<Float> newArrayList(float... fs){
		ArrayList<Float> list = new ArrayList<Float>();
		for(float f : fs){
			list.add(f);
		}
		return list;
	}

	public static ArrayList<Double> newArrayList(double... ds){
		ArrayList<Double> list = new ArrayList<Double>();
		for(double d : ds){
			list.add(d);
		}
		return list;
	}

}
