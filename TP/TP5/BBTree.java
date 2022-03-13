
public class BBTree {
	public double[] bestSolution;
	public double bestObjective;
	public BBNode root;
	public BBTree(BBNode root) {
		this.root = root;
		this.bestObjective = 0.0;
		this.bestSolution = null;
	}
	public void solve() {
		root.branch(this);
	}
	public void printSolution() {
		System.out.print("\nBest solution : z = " + (int)(bestObjective) + ", ");

		String variables = "(";
		String values = "(";
		for(int i = 0; i < bestSolution.length; i++) 
			if(bestSolution[i] != 0.0) {
				variables += "x" + (i+1) + ", ";
				values += (int)bestSolution[i] + ", ";
			}

		variables = variables.substring(0, Math.max(0, variables.length() - 2));
		values = values.substring(0, Math.max(0, values.length() - 2));
		System.out.println(variables + ") = " + values + ")");
	}
}
