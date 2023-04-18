import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CoryCaddellAssignment3C {
	
	public static void main (String[] args) throws FileNotFoundException {
		
		// get raw data
		ArrayList<ArrayList<String>> rawData = getRawData();
		
		// get vertices
		String[] CoryCaddellVertices = getVertices(rawData);
		
		// get edges
		int[][] CoryCaddellEdges = getEdges(rawData, CoryCaddellVertices);
		
	}
	
	/** Return 2D Array List containing City, State, Population*/
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
		int[][] edges = new int[vertices.length][];
		Map<String, Integer> cityMap = getCityValues(vertices);

		
		for (int i = 0; i < vertices.length; i++) {	// cycle through vertices
			
			edges[i][0] = cityMap.get(vertices[i]);	//
			for (int j = 0; j < 2; j++) {	// cycle through rawData
				
				if(rawData.get(j).get(0) == vertices[i]) {
					edges[i][1] = cityMap.get(rawData.get(j).get(1));
				}	
			}
			
		}
		
		return edges;
	}
	
	/** Return a map with assigned numerical values for cities */
	public static Map<String, Integer> getCityValues (String[] cities) {
		Map<String,Integer> cityValues = new HashMap<>();
		
		for (int i = 0; i < cities.length; i++) {
			cityValues.put(cities[i], i);
		}
		
		return cityValues;
	}
	

}

