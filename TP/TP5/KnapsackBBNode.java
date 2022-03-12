import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node of a branch and bound tree for a knapsack problem
 * @author zach
 *
 */
public class KnapsackBBNode extends BBNode{

	/* Weight of the objects */
	double[] p;
	
	/* Capacity of the knapsack */
	double K;

        /** Create a leaf */ 
	public KnapsackBBNode(BBNode parent, double[] newA, double newRhs, double[] p, double K) {
		super(parent, newA, newRhs);
		this.p = p;
		this.K = K;
	}
	
	/** Create a root */
	public KnapsackBBNode(double[][] A, double[] rhs, double[] obj, boolean isMinimisation, double[] p, double K) {
		super(A, rhs, obj, isMinimisation);
		this.p = p;
		this.K = K;
	}

	public static void main(String[] args) {
		
		BBTree kTree = exKnapsack();
		kTree.solve();
		
	}

	public static BBTree exKnapsack() {

		double[] p = new double[]{3, 7, 9, 6};
		double K = 17;
		
		double[][] mA = new double[][] {p,
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}};

		double[] rhs = new double[] {K, 1, 1, 1, 1};
		double[] obj = new double[] {8, 18, 20, 11};
		boolean isMinimization = false;

		return new BBTree(new KnapsackBBNode(mA, rhs, obj, isMinimization, mA[0], K));
	}
	
	/** Generate a cover inequality to cut the optimal solution of the linear relaxation */
	private void generateCut() {
		
		// TODO
	}

    
	@Override
	public void branch(BBTree tree) {

	    // TODO
	    
	}

	/**
	 * Add the cover cut which contain the objects which index are in the list cover
	 * @param cover List of the index of the objects in the cover
	 */
	private void addCoverCutToTableau(List<Integer> cover) {
		
		/* Add the constraints which corresponds to the cover */
		int m = tableau.m + 1;
		int n = tableau.n;

		double[][] newMA = new double[m][];

		for(int cstr = 0; cstr < m - 1; cstr++) {
			newMA[cstr] = new double[n];

			for(int var = 0; var < n; var++)
				newMA[cstr][var] = tableau.A[cstr][var]; 
		}

		newMA[m-1] = new double[n];

		for(int var = 0; var < n; var++)
			if(cover.contains(var))
				newMA[m-1][var] = 1.0;
			else
				newMA[m-1][var] = 0.0;

		double[] newMRhs = new double[m];

		for(int cstr = 0; cstr < m - 1; cstr++)
			newMRhs[cstr] = tableau.b[cstr];

		newMRhs[m - 1] = cover.size() - 1;

		tableau = new Tableau(newMA, newMRhs, tableau.c, tableau.isMinimization);
			
	}
}
