// CoryCaddellUnweightedGraph.java

import java.util.*;

public class CoryCaddellUnweightedGraph<V> implements CoryCaddellGraph<V> {
	
	protected List<V> vertices = new ArrayList<>();				// Store vertices
	protected List<List<Edge>> neighbors = new ArrayList<>();	// Adjacency Edge lists
	
	/** Construct an empty graph */
	protected CoryCaddellUnweightedGraph() {
	}
	
	/** Construct a graph from vertices and edges store in arrays */
	protected CoryCaddellUnweightedGraph(V[] vertices, int[][] edges) {
		for (int i = 0; i < vertices.length; i++) {
			addVertex(vertices[i]);
		}
		
		createAdjacencyList(edges, vertices.length);
	}
	
	/** Construct a graph from vertices and edges stored in List */
	protected CoryCaddellUnweightedGraph(List<V> vertices, List<Edge> edges) {
		for (int i = 0; i < vertices.size(); i++) {
			addVertex(vertices.get(i));
		}
		
		createAdjacencyList(edges, vertices.size());
	}
	
	/** Construct a graph for integer vertices 0, 1, 2 and edge list */
	protected CoryCaddellUnweightedGraph(List<Edge> edges, int numberOfVertices) {
		for (int i = 0; i < numberOfVertices; i++) {
			addVertex((V)(Integer.valueOf(i)));
		}
		
		createAdjacencyList(edges, numberOfVertices);
	}
	
	/** Construct a graph for integer vertices 0, 1, 2 and edge list */
	protected CoryCaddellUnweightedGraph(int[][] edges, int numberOfVertices) {
		for (int i = 0; i < numberOfVertices; i++) {
			addVertex((V)(Integer.valueOf(i)));
		}
		createAdjacencyList(edges, numberOfVertices);
	}
	
	/** Create adjacency lists for each vertex */
	private void createAdjacencyList(int[][] edge, int numberOfVertices) {
		for (int i = 0; i < edge.length; i++) {
			addEdge(edge[i][0], edge[i][1]);
		}
	}
	
	/** Create adjacency lists for each vertex */
	private void createAdjacencyList( List<Edge> edges, int numberOfVertices) {
		for (Edge edge: edges) {
			addEdge(edge.u, edge.v);
		}
	}
	
	@Override
	/** Return the number of vertices in the graph */
	public int getSize() {
		return vertices.size();
	}
	
	@Override
	/** Return the vertices in the graph */
	public List<V> getVertices() {
		return vertices;
	}
	
	@Override
	/** Return the Object for the specified vertex */
	public V getVertex(int index) {
		return vertices.get(index);
	}
	
	@Override
	/** Return the index for the specified vertex object */
	public int getIndex(V v) {
		return vertices.indexOf(v);
	}
	
	@Override
	/** Return the neighbors of the specified vertex */
	public List<Integer> getNeighbors(int index) {
		List<Integer> result = new ArrayList<>();
		for (Edge e: neighbors.get(index)) {
			result.add(e.v);
		}
		return result;
	}
	
	@Override
	/** Return the degree for a specified vertex */
	public int getDegree(int v) {
		return neighbors.get(v).size();
	}
	
	@Override
	/** Print the edge */
	public void printEdges() {
		for (int u = 0; u < neighbors.size() && u < getSize(); u++) {	// added "&& u < getSize()" in case there's less vertices than neighbor size
			System.out.print(getVertex(u) + " (" + u + "): ");
			for (Edge e: neighbors.get(u)) {
				System.out.print("(" + getVertex(e.u) + ", " + 
						getVertex(e.v) + ") ");
			}
			System.out.println();
		}
	}
	
	@Override
	/** Clear the graph */
	public void clear() {
		vertices.clear();
		neighbors.clear();
	}
	
	@Override
	/** Add a vertex to the graph */
	public boolean addVertex(V vertex) {
		if (!vertices.contains(vertex)) {
			vertices.add(vertex);
			neighbors.add(new ArrayList<Edge>());
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	/** Add an edge to the graph */
	public boolean addEdge(Edge e) {
		if (e.u < 0 || e.u > getSize() - 1) {
			throw new IllegalArgumentException("No such index: " +
					e.u);
		}
		
		if (e.v < 0 || e.v > getSize() - 1) {
			throw new IllegalArgumentException("No such index: " + 
				e.v);
		}
		
		if (!neighbors.get(e.u).contains(e)) {
			neighbors.get(e.u).add(e);
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	/** Add an edge to the graph */
	public boolean addEdge(int u, int v) {
		return addEdge(new Edge(u, v));
	}
	
	@Override
	/** Obtain a DFS tree starting from vertex v */
	public SearchTree dfs(int v) {
		List<Integer> searchOrder = new ArrayList<>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = -1;	// Initialize parent[i] to -1
		}
		
		// Mark visited vertices
		boolean[] isVisited = new boolean[vertices.size()];
		
		// Recursively search
		dfs(v, parent, searchOrder, isVisited);
		
		// Return a search tree
		return new SearchTree(v, parent, searchOrder);
	}
	
	/** Recursive method for DFS search */
	private void dfs (int v, int[] parent, List<Integer> searchOrder, boolean[] isVisited) {
		// Store the visited vertex
		searchOrder.add(v);
		isVisited[v] = true;	// Vertex v visited
		
		for (Edge e: neighbors.get(v)) {	// e.u is v
			int w = e.v;	// e.v is w
			if (!isVisited[w]) {
				parent[w] = v;	// The parent of vertex w is v
				dfs(w, parent, searchOrder, isVisited); // Recursive search
			}
		}
	}
	
	@Override
	/** Starting bfs search from vertex v */
	public SearchTree bfs(int v) {
		List<Integer> searchOrder = new ArrayList<>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = -1;	// Initialize parent[i] to -1
		}
		
		LinkedList<Integer> queue = new LinkedList<>();	// list used as a queue
		boolean[] isVisited = new boolean[vertices.size()];
		queue.offer(v);	// Enqueue v
		isVisited[v] = true;	// Mark it visited
		
		while (!queue.isEmpty()) {
			int u = queue.poll();	// Dequeue to u
			searchOrder.add(u);	// u searched
		
			for (Edge e: neighbors.get(u)) {
				int w = e.v;	// e.v is w
				if (!isVisited[w]) {	
					queue.offer(w);	// Enqueue w
					parent[w] = u;	// The parent of w is u
					isVisited[w] = true;	// Mark it visited
				}
			}
		}
		
		return new SearchTree(v, parent, searchOrder);
		
	}
	
	/** Tree inner class inside the UnweightedGraph class */
	public class SearchTree {
		private int root;	// The root of the tree
		private int[] parent;	// Store the parent of each vertex
		private List<Integer> searchOrder; // Store the searcch order
		
		
		/** Construct a tree with root, parent, and searchOrder */
		public SearchTree(int root, int[] parent, List<Integer> searchOrder) {
			this.root = root;
			this.parent = parent;
			this.searchOrder = searchOrder;
		}
		
		/** Return the root of the tree */
		public int getRoot() {
			return root;
		}
		
		/** Return the parent of vertex v */
		public int getParent(int v) {
			return parent[v];
		}
		
		/** Return an array representing search order */
		public List<Integer> getSearchOrder() {
			return searchOrder;
		}
		
		/** Return number of vertices found */
		public int getNumberOfVerticesFound() {
			return searchOrder.size();
		}
		
		/** Return the path of vertices from a vertex to the root */
		public List<V> getPath(int index) {
			ArrayList<V> path = new ArrayList<>();
			
			do {
				path.add(vertices.get(index));
				index = parent[index];
			}
			while (index != -1);
			
			return path;
		}
		
		public void printPath(int index) {
			List<V> path = getPath(index);
			System.out.print("A path from " + vertices.get(root) + " to " + vertices.get(index) + ": ");
			for (int i = path.size() - 1; i >= 0; i--) {
				System.out.print(path.get(i) + " " );
			}
		}
		
		public void printTree() {
			System.out.println("Root is: " + vertices.get(root));
			System.out.print("Edges: ");
			for (int i = 0; i < parent.length; i++) {
				if (parent[i] != -1) {
					System.out.print("(" + vertices.get(parent[i]) + ", " + vertices.get(i) + ") ");
				}
			}
			System.out.println();
			
		}
	}
	
	@Override
	public boolean remove(V v) {
		if (vertices.contains(v)) {
			
			
			// get index of v
			int vertexIndex = vertices.indexOf(v);

			// get neighbors of v
			List<Integer> neighborList = getNeighbors(vertexIndex);
			
			// remove v from neighbors
			for (Integer neighborIndex: neighborList) {
				remove(vertexIndex, neighborIndex);
				remove(neighborIndex,vertexIndex);
			}
			
			// remove v from vertices
			vertices.remove(v);
			
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean remove(int u, int v) {
		Edge e = new Edge(u, v);
		
		if (e.u < 0 || e.u > getSize() - 1) {
			throw new IllegalArgumentException("No such index: " +
					e.u);
		}
		
		if (e.v < 0 || e.v > getSize() - 1) {
			throw new IllegalArgumentException("No such index: " + 
				e.v);
		}
		
		if (neighbors.get(u).contains(e)) {
			neighbors.get(u).remove(e);
			return true;
		}
	
		return false;

	}
		
		
	
	public void CoryCaddellPrintGraph() {
		List<Integer> neighborList;
		
		System.out.println("Number of Vertices: " + getSize());
		
		System.out.println("\nCities and their Neighbors: "
						 + "\n---------------------------");
		for (int i = 0; i < getSize(); i++) {
			System.out.print(vertices.get(i) + ": ");
			
			neighborList = getNeighbors(i);
			for (int j = 0; j < neighborList.size(); j++) {
				System.out.print(((j + 1) > (neighborList.size() - 1)) ?
						getVertex(neighborList.get(j)) :
						getVertex(neighborList.get(j)) + ", ");			
			}
			System.out.println();
		}
				
	}
	
	public void CoryCaddellPrintDepthFirst(V vertex) {
		CoryCaddellUnweightedGraph<V>.SearchTree dfs = dfs(getIndex(vertex));
		List<Integer> searchOrders = dfs.getSearchOrder();
		
		System.out.println("Printing in Depth First Order Beginning With: " + vertex
					   + "\n--------------------------------------------------------");
		for (int i = 0; i < searchOrders.size(); i++) {
			System.out.println(getVertex(searchOrders.get(i)) + " ");
		}
		
		
		
	}
	
	public void CoryCaddellPrintBreadthFirst(V vertex) {
		CoryCaddellUnweightedGraph<V>.SearchTree bfs = bfs(getIndex(vertex));
		List<Integer> searchOrders = bfs.getSearchOrder();
		
		System.out.println("Printing in Breadth First Order Beginning With: " + vertex
					   + "\n--------------------------------------------------------");
		for (int i = 0; i < searchOrders.size(); i++) {
			System.out.println(getVertex(searchOrders.get(i)) + " ");
		}
	}

}
