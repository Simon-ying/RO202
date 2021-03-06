

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utility {

	public static NumberFormat nf = new DecimalFormat("#0.00");

	/**
	 * Test if a double is fractional
	 * @param d The tested double
	 * @return True if the double is fractional, false if it is an integer.
	 */
	public static boolean isFractional(double d) {
		return Math.abs(Math.round(d) - d) > 1E-6;
	}
	
	public static double[][] copyArray(double[][] input){
		
		double[][] copy = new double[input.length][];
		
		for(int i = 0; i < input.length; i++)
			copy[i] = copyArray(input[i]);
		
		return copy;
	}
	
	public static double[] copyArray(double[] input) {
		
		double[] copy = new double[input.length];
		
		for(int i = 0; i < input.length; i++)
			copy[i] = input[i];
		
		return copy;
	}
	
	public static String genSpace(int n) {
		int m = n * 2;
		String spaces = "";
		if (m < 0) return spaces;
		else {
			for (int i=0; i<m; i++) {
				spaces += " ";
			}
			return spaces;
		}
	}
	
	public static void print_table(double[] A) {
		System.out.print("[");
		for (double i : A) {
			System.out.print(i+ " ");
		}
		System.out.print("]\n");
	}
	
	public static double[] neg(double[] p) {
		double[] np = new double[p.length];
		for (int i=0; i<p.length; i++) {
			np[i] = -p[i];
		}
		return np;
	}

}
