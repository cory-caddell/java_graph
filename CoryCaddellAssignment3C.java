import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CoryCaddellAssignment3C {
	
	public static void main (String[] args) throws FileNotFoundException {
		
		// get raw data
		ArrayList<ArrayList<String>> rawData = getRawData();
		rawData.forEach(e->System.out.println(e));	// DEBUG
		
		// get vertices
		
		// get edges
		
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
			
			rawData.add(new ArrayList<String>(Arrays.asList(dataSplit)));	// add to collection
		}
		input.close();
		
		return rawData;
	}
	

}

