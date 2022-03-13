
public class Test {
	public static void main(String args[]) {
		double[][] A = new double[][] {
			{-2, 2},
			{2, 3},
			{9, -2}
		};
		double[] rhs = new double[] {7, 18, 36};
		double[] obj = new double[] {3, 2};
		BBNode root = new BBNode(A, rhs, obj, false);
		BBTree tree = new BBTree(root);
		tree.solve();
		tree.printSolution();
	}
}
