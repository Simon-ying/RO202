

import java.util.ArrayList;
import java.util.List;

public class Dijkstra {

	public static void main(String[] args) {

		/* Create the oriented graph */
		Graph g = new Graph(new String[] {"Paris", "Hambourg", "Londres", "Amsterdam", "Edimbourg", "Berlin", "Stockholm", "Rana", "Oslo"});
		
		g.addArc("Paris", "Hambourg", 7);
		g.addArc("Paris",  "Londres", 4);
		g.addArc("Paris",  "Amsterdam", 3);
		g.addArc("Hambourg",  "Stockholm", 1);
		g.addArc("Hambourg",  "Berlin", 1);
		g.addArc("Londres",  "Edimbourg", 2);
		g.addArc("Amsterdam",  "Hambourg", 2);
		g.addArc("Amsterdam",  "Oslo", 8);
		g.addArc("Stockholm",  "Oslo", 2);
		g.addArc("Stockholm",  "Rana", 5);
		g.addArc("Berlin",  "Amsterdam", 2);
		g.addArc("Berlin",  "Stockholm", 1);
		g.addArc("Berlin",  "Oslo", 3);
		g.addArc("Edimbourg",  "Oslo", 7);
		g.addArc("Edimbourg",  "Amsterdam", 3);
		g.addArc("Edimbourg",  "Rana", 6);
		g.addArc("Oslo",  "Rana", 2);
		
		/* Apply Dijkstra algorithm to get an arborescence */
		Graph tree = dijkstra(g, "Paris");
		
		System.out.println(tree);

	}
	
	/**
	 * Apply Dijkstra algorithm on a graph
	 * @param g The graph considered
	 * @param origin The starting node of the paths
	 * @return A graph which is an arborescence and represent the shortest paths from the origin to all the other nodes 
	 */
	public static Graph dijkstra(Graph g, String origin) {
		
		/* Get the index of the origin */
		int r = g.indexOf(origin);

		/* Next node considered */
		int pivot = r;
		
		/* Create a list that will contain the nodes which have been considered */
		List<Integer> V2 = new ArrayList<>();
		V2.add(r);	
		
		int[] pred = new int[g.n];
		pred[r] = r;

		double[] pi = new double[g.n];
		pi[r] = 0.0;
		
		/* Initially, the distance between r and the other nodes is the infinity */
		for(int v = 0; v < g.n; v++)
			if(v != r)
				pi[v] = Double.POSITIVE_INFINITY;

		// Mettre votre code ici
		for (int j=1; j<g.n; j++)
		{
			for (int k=0; k<g.n; k++)
			{
				if (k != pivot && g.adjacency[pivot][k] != 0)
				{
					if (pi[pivot] + g.adjacency[pivot][k] < pi[k])
					{
						pi[k] = pi[pivot] + g.adjacency[pivot][k];
						pred[k] = pivot;

					}
				}
			}

			double temp = 0;
			for (int i=0; i<pi.length; i++)
			{
				if(pi[i] < temp && !V2.contains(i))
				{
					temp = pi[i];
					pivot = i;
				}

			}
			V2.add(pivot);
		}
		Graph tree = new Graph(g.nodes);
		for (int i = 0; i < g.n; i++)
		{
			if (pi[i] != 0.0)
				tree.addEdge(pred[i], i, g.adjacency[pred[i]][i]);
		}
		return tree;
	}
}
