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
		List<Integer> cover = new ArrayList<>();
		if (this.tableau.bestSolution == null)
			return;
		int n = this.tableau.n;
		int coverWeight = 0;
		int index = 0;
		while (coverWeight < this.K && index < n) {
			if (this.tableau.bestSolution[index] > 0) {
				cover.add(index);
				coverWeight += this.p[index];
			}
			index ++;
		}
		if (coverWeight > this.K) {
			System.out.print(Utility.genSpace(this.depth) + 
					"Found cover : ");
			for (int i : cover) {
				System.out.print((i+1) + " ");
			}
			System.out.println();
			System.out.print(Utility.genSpace(this.depth) + "Relaxation before the cut : ");
			try {
				this.tableau.displaySolution();
			}
			catch (Exception e) {
				System.out.println();
			}
			this.addCoverCutToTableau(cover);
			
			
			this.tableau.applySimplexPhase1And2();
			System.out.print(Utility.genSpace(this.depth) + "Relaxation after the cut : ");
			this.tableau.displaySolution();
		}
	}

    
	@Override
	public void branch(BBTree tree) {
		/* Solve the linear relaxation */
		tableau.applySimplexPhase1And2();
		//TODO
		int index = -1;

		if (tableau.bestSolution != null) {
			if (tree.bestSolution==null || (tableau.bestObjective < tree.bestObjective && tableau.isMinimization) || (tableau.bestObjective > tree.bestObjective && !tableau.isMinimization)) {
				
				for (int i=0; i<tableau.bestSolution.length; i++) {
					if (Utility.isFractional(tableau.bestSolution[i])) {
						index = i;
						if (this.depth == 0) {
							System.out.print("root:");
						}
						System.out.println("x[" + index + "] = " + Utility.nf.format(tableau.bestSolution[index]));
						break;
					}			
				}
				if (index == -1)
				{
					System.out.print("Integer solution: ");
					tableau.displaySolution();
					tree.bestSolution = tableau.bestSolution;
					tree.bestObjective = tableau.bestObjective;
				}
				else {
					this.generateCut();
					double newAl[] = new double[tableau.n];
					double newAr[] = new double[tableau.n];
					for (int i=0; i<tableau.n; i++) {
						newAl[i] = 0.0;
						newAr[i] = 0.0;
					}
					newAl[index] = 1;
					newAr[index] = -1;
					KnapsackBBNode newNodel = new KnapsackBBNode(this, newAl, Math.floor(tableau.bestSolution[index]), this.p, this.K);
					KnapsackBBNode newNoder = new KnapsackBBNode(this, newAr, -Math.ceil(tableau.bestSolution[index]), this.p, this.K);
					System.out.print(Utility.genSpace(this.depth+1) + "x[" + index + "] <= " + Math.floor(tableau.bestSolution[index]) + " : ");
					newNodel.branch(tree);
					System.out.print(Utility.genSpace(this.depth+1) + "x[" + index + "] >= " + Math.ceil(tableau.bestSolution[index]) + " : ");
					newNoder.branch(tree);
				}
			}
			else{
				System.out.println("Node cut : relaxation worst than bound");
				System.out.println(Utility.genSpace(this.depth) + "(node relaxation : " + Utility.nf.format(this.tableau.bestObjective) + ", best integer solution : " + (int)tree.bestObjective + ")");
			}
		}
		if (tableau.bestSolution == null) {
			System.out.println("Node cut : infeasible solution");
		}
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
