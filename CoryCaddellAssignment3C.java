// CoryCaddellAssignment3C.java

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CoryCaddellAssignment3C {
	
	/** Program to demonstrate utility of graph data structure */
	public static void main (String[] args) throws FileNotFoundException {
		
		// get raw data
		ArrayList<ArrayList<String>> rawData = getRawData();
		
		// get vertices
		String[] CoryCaddellVertices = getVertices(rawData);
		
		// get edges
		int[][] CoryCaddellEdges = getEdges(rawData, CoryCaddellVertices);
	
		// get graph
		CoryCaddellUnweightedGraph graph = new CoryCaddellUnweightedGraph(CoryCaddellVertices, CoryCaddellEdges);
		
		// print graph
		graph.CoryCaddellPrintGraph();
		System.out.println();
		
		// print vertices in depth first order
		graph.CoryCaddellPrintDepthFirst("Chicago");
		System.out.println();
		
		// print vertices in breadth first order
		graph.CoryCaddellPrintBreadthFirst("Chicago");
		System.out.println();
		
		// print size of graph
		System.out.println("Size of graph: " + graph.getSize() + "\n");
		
		// print vertices of graph
		System.out.println("getVertices(): "
						+ "\n---------------");
		graph.getVertices().forEach(e->System.out.println(e));
		System.out.println();
		
		// print vertex at index
		System.out.println("getVertex(0): " + graph.getVertex(0) + "\n");
		
		// print index of vertex
		System.out.println("getIndex(Albany): " + graph.getIndex("Albany") + "\n");
		
		// print neighbors of vertex
		System.out.print("getNeighbors(0): "); 
		graph.getNeighbors(0).forEach(e -> System.out.print(graph.getVertex((int) e) + " "));
		System.out.println("\n");
		
		// print degree of vertex
		System.out.println("getDegree(0): " + graph.getDegree(0) + "\n");
		
		// print edges
		System.out.println("printEdges(): "
						+ "\n-------------");
		graph.printEdges();
		System.out.println();
		
		// print empty graph
		graph.clear();
		System.out.println("clear(): " + graph.getSize() + "\n");
		
		// print new vertex the graph
		System.out.println("addVertex(Dallas) & addVertex(Ft. Worth):"
						+ "\n-----------------------------------------");
		graph.addVertex("Dallas");
		graph.addVertex("Ft. Worth");
		graph.printEdges();
		System.out.println();
		
		// print new edge to graph
		System.out.println("addEdge(0, 1) and addEdge(1, 0):" 
						+ "\n---------------------------------");
		graph.addEdge(0, 1);
		graph.addEdge(1, 0);
		graph.printEdges();
		System.out.println();
				
		// print new index of remaining vertex and respective neighbors
		System.out.println("remove(Dallas): "
						+ "\n----------------");
		graph.remove("Dallas");
		graph.printEdges();

		
	}
	
	/** Return 2D ArrayList containing City, State, Population*/
	public static ArrayList<ArrayList<String>> getRawData( ) throws FileNotFoundException {
		String dataPerLine;		// contents of each line of data from file
		String[] dataSplit;		// split content of data to array
		ArrayList<ArrayList<String>> rawData = new ArrayList<>();	// contain all cities, states, population
		
		// Read data from file
		File file = new File("Assignment3CData.txt");
		if (!file.exists())
		{
			System.out.print("File does not exist.");
			System.exit(0);
		}

		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			dataPerLine = input.nextLine();
			dataSplit = dataPerLine.split("	");	// temp variable to store elements of each line
			
			rawData.add(new ArrayList<String>(Arrays.asList(dataSplit)));	// add temp to collection
		}
		input.close();
		
		return rawData;
	}
	
	/** Return a String array containing all cities */
	public static String[] getVertices(ArrayList<ArrayList<String>> rawData) {
		Set<String> citiesSet = new TreeSet<>();	// use set structure since no duplicates are allowed
		
		for(int i = 0; i < rawData.size(); i++) {
			citiesSet.add(rawData.get(i).get(0));	// first two elements are cities	
			citiesSet.add(rawData.get(i).get(1));
		}
		
		return citiesSet.toArray(new String[citiesSet.size()]);	// string array
	}	
	
	/** Return a 2D array containing edges of graph */
	public static int[][] getEdges(ArrayList<ArrayList<String>> rawData, String[] vertices) {
		Map<String, Integer> cityMap = getCityValues(vertices);			// assign numerical value to each city for edge array
		Map<String, TreeSet<String>> neighborMap = getNeighborMap(rawData, vertices);	// key is cities and values are set each city's respective neighbor
		ArrayList<ArrayList<String>> adjacentCityList = getAdjacentCityList(neighborMap, vertices);	// store each pair of city and neighbor in list
		int[][] edges = new int[adjacentCityList.size()][2];

		for (int i = 0; i < adjacentCityList.size(); i++) {
			edges[i][0] = cityMap.get(adjacentCityList.get(i).get(0));	// assign numerical value to vertex
			edges[i][1] = cityMap.get(adjacentCityList.get(i).get(1));	// assign numerical value to neighbor
		}
		
		return edges;
	}
	
	/** Return a map with assigned numerical values for cities */
	public static Map<String, Integer> getCityValues (String[] cities) {
		Map<String,Integer> cityValues = new TreeMap<>(); 
		
		for (int i = 0; i < cities.length; i++) {	// cycle through vertices
			cityValues.put(cities[i], i);			// assign a value for each vertex
		}
							
		return cityValues;
	}
	
	/** Return a map of city's and all of their respective neighbors */
	public static Map<String, TreeSet<String>> getNeighborMap (ArrayList<ArrayList<String>> rawData, String[] cities) {
		Map<String,TreeSet<String>> neighborMap = new TreeMap<>(); 
		TreeSet<String> neighborList;
		
		for (int i = 0; i < cities.length; i++) {
			
			neighborList = new TreeSet<>();
			for (int j = 0; j < rawData.size(); j++) {	// cycle through rawData			
				
				if (cities[i].compareTo(rawData.get(j).get(0)) == 0) {
					neighborList.add(rawData.get(j).get(1));	// assign vertex to first element of edge

				}
				
				if (cities[i].compareTo(rawData.get(j).get(1)) == 0) {
					neighborList.add(rawData.get(j).get(0));	// assign vertex to first element of edge

				}
			}
						
			neighborMap.put(cities[i], neighborList);
		}
					
		return neighborMap;
	}	

	/** Return a list of a city and neighbor combo */
	public static ArrayList<ArrayList<String>> getAdjacentCityList(Map<String, TreeSet<String>> neighborMap, String[] vertices) {
		ArrayList<ArrayList<String>> adjacentCityList = new ArrayList<>();
		String[] temp = new String[2];
		
		for (int i = 0; i < neighborMap.size(); i++) {
			for (String x: neighborMap.get(vertices[i])) {
				temp[0] = vertices[i];	// city
				temp[1] = x;			// neighbor
				adjacentCityList.add(new ArrayList<String>(Arrays.asList(temp)));	// add temp to collection
			}
		}
		
		return adjacentCityList;
	}




}


