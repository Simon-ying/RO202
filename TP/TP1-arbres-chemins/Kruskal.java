import java.util.Collections;
import java.util.List;

public class Kruskal {
	
	public static void main(String[] args) {
		
		/* Create a graph which contains nodes a, b, c, d, e, f, g */
		Graph g = new Graph(new String[]{"a", "b", "c", "d", "e", "f", "g"});

		/* Add the edges */
		g.addEdge("a", "b",  1.0);
		g.addEdge("a", "c",  8.0);
		g.addEdge("b", "c",  2.0);
		g.addEdge("b", "d",  5.0);
		g.addEdge("b", "e",  7.0);
		g.addEdge("b", "f",  9.0);
		g.addEdge("c", "d",  4.0);
		g.addEdge("d", "e",  6.0);
		g.addEdge("d", "g", 12.0);
		g.addEdge("e", "f",  8.0);
		g.addEdge("e", "g", 11.0);
		g.addEdge("f", "g", 10.0);
		
		/* Get a minimum spanning tree of the graph */
		Graph tree = kruskal(g);
		
		/* If there is such a tree (i.e., if the graph is connex */
		if(tree != null)
			
			/* Display it */
			System.out.println(tree);
		
		else
			System.out.println("No spanning tree");
		
	}

	/**
	 * Apply Kruskal algorithm to find a minimal spanning tree of a graph
	 * @return A tree which corresponds a minimal spanning tree of the graph; null if there is none
	 */
	public static Graph kruskal(Graph g) {
		
		/* Create a new graph with the same nodes than g */
		Graph tree = new Graph(g.nodes);
		
		/* Current number of edges in the tree */
		int addedEdges = 0;
		
		/* Get all the edges from g */
		List<Edge> edges = g.getEdges();
		
		/* Sort the edges by increasing weight */
		Collections.sort(edges);
		int selected=1;
		while(selected<g.n)
		{
			if (!tree.createACycle(edges.get(0)));
			{
				tree.addEdge(edges.get(0));
				selected++;
			}
			edges.remove(0);
		}
		//TODO: mettre votre code ici
		// if(tree.createACycle(edges.get(0)))
		return tree;
}
