
public class Test {
	public static void main(String args[]) {
		double[][] A = new double[][] {
			{3, 7, 9, 6},
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
		};
		double[] rhs = new double[] {17, 1, 1, 1, 1};
		double[] obj = new double[] {8, 18, 20, 11};
		BBNode root = new BBNode(A, rhs, obj, false);
		BBTree tree = new BBTree(root);
		tree.solve();
		tree.printSolution();
	}
}
