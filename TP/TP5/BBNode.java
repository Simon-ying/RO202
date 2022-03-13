

/**
 * Represents a node of a branch-and-bound tree.
 */
public class BBNode {

	/** The tableau associated to the relaxation of this node */
//	Tableau_correction tableau;
	Tableau tableau;
	
	/** The current depth of the node (only used to display the tree with an indentation) */
	public int depth;

	/**
	 * Create a root node
	 * @param A Matrix A of the coefficient
	 * @param rhs Right-hand side vector
	 * @param obj Objective coefficients
	 * @param isMinimisation True if it is a minimization problem
	 */
	public BBNode(double[][] A, double[] rhs, double[] obj, boolean isMinimisation) {
		
		/* Create the tableau associated to the root problem */
//		tableau = new Tableau_correction(A, rhs, obj, isMinimisation);
		tableau = new Tableau(A, rhs, obj, isMinimisation);
		depth = 0;
	}

	/**
	 * Create a non-root node by adding a constraint to the problem of its parent node
	 * @param parent The parent node
	 * @param newA The coefficients of the new constraint
	 * @param newRhs The right-hand side of the new constraint
	 */
	public BBNode(BBNode parent, double[] newA, double newRhs) {

		/* Add the new constraint to A and rhs */
		int m = parent.tableau.m + 1;
		int n = parent.tableau.n;
		depth = parent.depth + 1;

		double[][] newMA = new double[m][];

		for(int cstr = 0; cstr < m - 1; cstr++) {
			newMA[cstr] = new double[n];

			for(int var = 0; var < n; var++)
				newMA[cstr][var] = parent.tableau.A[cstr][var]; 
		}

		newMA[m-1] = new double[n];

		for(int var = 0; var < n; var++)
			newMA[m-1][var] = newA[var];

		double[] newMRhs = new double[m];

		for(int cstr = 0; cstr < m - 1; cstr++)
			newMRhs[cstr] = parent.tableau.b[cstr];

		newMRhs[m - 1] = newRhs;

		/* Create the tableau with this additional constraint */
//		tableau = new Tableau_correction(newMA, newMRhs, parent.tableau.c, parent.tableau.isMinimization);
		tableau = new Tableau(newMA, newMRhs, parent.tableau.c, parent.tableau.isMinimization);

	}
	
	/**
	 * Compute the linear relaxation of the node and branch if necessary (i.e. if the relaxation provide a fractional solution)
	 * @param tree The tree in which the node is
	 */
	public void branch(BBTree tree) {

		/* Solve the linear relaxation */
		tableau.applySimplexPhase1And2();
		/* I - Description des variables et de leurs attributs que vous devrez utiliser
		 * - Variable tableau : représente le programme linéaire associé à ce sommet (c'est donc un problème continu). Ses attributs sont :
		 *    - bestSolution (tableau de double) : solution optimale continue trouvée (null si le PL est infaisable) ;
		 *    - bestObjective (double) : valeur optimale de l'objectif ;
		 *    - isMinimization (boolean) : indique si c'est un problème de minimisation ;
		 *    - n (int) : nombre de variables (et donc taille du tableau bestSolution) ;
		 *    - depth (int) : profondeur du sommet dans l'arbre (mis à jour dans le constructeur, utile pour l'indentation des affichages).
		 *    
		 * - Variable tree : représente l'arbre de branchement dans lequel se trouve le sommet. Ses attributs sont :
		 *    - bestSolution (tableau de double) : meilleure solution entière trouvée (null si aucune n'a encore été trouvée) ;
		 *    - bestObjective (double) : valeur de l'objectif de la meilleure solution entière connue. 
		 * 
  		 * II - Comment savoir si une variable d de type double est fractionnaire ?
  		 * Utiliser "Utility.isFractional(d)" qui retourne "true" si d est fractionnaire.
  		 * 
  		 * III - Comment calculer les parties entières inférieures et supérieures d'une variable de type double ?
  		 * Utiliser "Math.floor(d)" et "Math.ceil(d)".
  		 * 
  		 * IV - Comment créer un nouveau sommet en lui ajoutant une nouvelle contrainte de la forme newA * x <= newRhs ?
  		 * Utiliser le constructeur "public BBNode(BBNode parent, double[] newA, double newRhs)" dans lequel :
  		 *   - parent correspond au noeud actuellement considéré (l'objet actuellement considéré dans une classe est obtenu grâce au mot-clé "this" en java) ;
  		 *   - newA : tableau contenant les coefficients de la nouvelle contrainte 
  		 *     (pour créer un tableau contenant 10 double initialisés à la valeur 0.0, vous pouvez utiliser la syntaxe : "double[] monTableau = new double[10];") ;
  		 *   - newRhs : valeur du second membre de la nouvelle contrainte.
  		 *   
  		 * V - Comment brancher sur un objet "node" de type BBNode situé dans un arbre "tree" ?
  		 * node.branch(tree)
  		 * 
		 */
		//TODO
		int index = -1;
		/*
		 * Si R admet une solution S alors
		 *     si bestSolution = null ou si S est meilleure que bestSolution alors
		 *         si S est int alor
		 *             bestSolution = S
		 *         sinon
		 *             id = index de la 1ere variable fractionnaire dans S
		 *             creer un sommet contenant la contrainte x_id < S(x_id) et brancher dessus
		 *             creer un sommet contenant la contrainte x_id >= S(x_id) et brancher dessus
		 */
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
					double newAl[] = new double[tableau.n];
					double newAr[] = new double[tableau.n];
					for (int i=0; i<tableau.n; i++) {
						newAl[i] = 0.0;
						newAr[i] = 0.0;
					}
					newAl[index] = 1;
					newAr[index] = -1;

					BBNode newNodel = new BBNode(this, newAl, Math.floor(tableau.bestSolution[index]));
					BBNode newNoder = new BBNode(this, newAr, -Math.ceil(tableau.bestSolution[index]));
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

}
