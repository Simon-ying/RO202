import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node of a branch-and-bound tree.
 */
public class BBNode {

	/** The tableau associated to the relaxation of this node */
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
		boolean isInt = true;
		int index = -1;
		if (tableau.bestSolution != null) {
			if (tree.bestSolution==null || (tableau.bestObjective < tree.bestObjective && tableau.isMinimization)) {
				for (int i=0; i<tableau.bestSolution.length; i++) {
					if (Utility.isFractional(tableau.bestSolution[i])) {
						isInt = false;
						index = i;
						break;
					}			
				}
				if (isInt)
					tree.bestSolution = tableau.bestSolution;
				else {
					double newAl[] = new double[tableau.n];
					double newAr[] = new double[tableau.n];
					for (int i=0; i<tableau.n; i++) {
						newAl[i] = 0.0;
						newAr[i] = 0.0;
					}
					newAl[index] = 1;
					newAr[index] = -1;
					BBNode newNodel = new BBNode(this, newAl, Math.floor(index));
					
				}
			}

			
		}
	}

}
