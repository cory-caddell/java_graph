// CoryCaddellGraph.java

import java.util.List;

public interface CoryCaddellGraph<V> {
	
	/** Return number of vertices in graph */
	public int getSize();
	
	/** Return the vertices in the graph */
	public List<V> getVertices();
	
	/** Return the object for the specified vertex index */
	public V getVertex(int index);
	
	/** Return the index for the specified vertex object */
	public int getIndex(V v);
	
	/** Return the neighbors of vertex with the specified index */
	public List<Integer> getNeighbors(int index);
	
	/** Return the degree for a specified vertex */
	public int getDegree(int v);
	
	/** Print the edges */
	public void printEdges();
	
	/** Clear the graph */
	public void clear();
	
	/** Add a vertex to the graph */
	public boolean addVertex(V vertex);
	
	/** Add an edge to the graph */
	public boolean addEdge(int u, int v);
	
	/** Add an edge to the graph */
	public boolean addEdge(Edge e);
	
	/** Remove a vertex v from the graph, return true if success full */
	public boolean remove(V v);
	
	/** Remove an edge (u, v) from the graph, return true if successful */
	public boolean remove(int u, int v);
	
	/** Obtain a depth-first search tree */
	public CoryCaddellUnweightedGraph<V>.SearchTree dfs(int v);
	
	/** Obtain a breadth-first search tree */
	public CoryCaddellUnweightedGraph<V>.SearchTree bfs(int v);
	
	public class Edge {
		int u;
		int v;
		
		public Edge(int u, int v) {
			this.u = u;
			this.v = v;
		}
		
		public boolean equals(Object o) {
			return u == ((Edge)o).u && v == ((Edge)o).v;
		}
	}
}
