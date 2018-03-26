package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/* *
 * This class is the runnable program containing the main() method. This class is responsible 
 * for parsing the command line argument,reading the input file, creating the CollisionsData 
 * object based on the input file, interacting with the user, and calling all the methods
 * of the CollisionsData object needed for the output.
 * @author aubreyzhou
 */
public class CollisionInfo {
	/**
	 * main method
	 * @param command line arguments 
	 * 
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws FileNotFoundException,IllegalArgumentException{
		//check if the program is running without arguments
		if(args.length==0) {
			//if so, display an error message
			System.err.println("USAGE ERROR: the program expects file name as an argument.");
			//terminate
			System.exit(1);
		}
		//create a File object using the first argument
		File file = new File (args[0]);
		//check if the file exists
		if (!file.exists()) {
			//if so, display an error message
			System.err.printf("ERROR: cannot find file %s.\n\n", args[0]);
			//terminate
			System.exit(1);
		}
			//check if the file is readable
			else if(!file.canRead()) {
				//if not, display an error message
				System.err.printf("ERROR: cannot read file %s.\n\n", args[0]);
				//terminate
				System.exit(1);
			}
		
		//open the file for reading using the Scanner object
		Scanner nypd = new Scanner(file);
		//construct a CollisionData object to store data
		CollisionsData data=new CollisionsData();
		
		//skip the first line
		nypd.nextLine();
		
		while(nypd.hasNext()){
			//read the next line from file
			String line=nypd.nextLine();
			//if there are less than 24 entries in a line, ignore it
			if(splitCSVLine(line).size()<24) continue;
			//try to create a collision object and add it to the CollisionData
			//using the entries read from the file
			try {
				Collision a =new Collision(splitCSVLine(line));
				data.add(a);
			}
			//catch the IllegalArgumentException thrown because of invalid entries
			catch (IllegalArgumentException e){
				
			}
		}
			//close the file
			nypd.close();
		
			while(true) {
				System.out.println("Enter a zip code ('quit'to exit):");
				//get the input from the user
				Scanner input=new Scanner(System.in);
				//read zip code entered by user
				String zip=input.next();
				//if user enters "quit", terminate
				if (zip.equalsIgnoreCase("quit")) {
					System.exit(0);
				}
				//if zip code is invalid, display an error message
				else if(!(zip.length()==5&&isInteger(zip))) {
					System.err.println("Invalid zip code. Try again.");
					continue;
				}
				System.out.println("Enter start date(MM/DD/YYYY):");
				//read start date entered by user
				String start=input.next();
				//try to create a date object with it
				try {
					Date startDate= new Date(start);
				}
				//catch the IllegalArgumentException thrown because of invalid start date 
				//entered and display an error message
				catch(IllegalArgumentException e){
					System.err.println("Invalid date format. Try again.");
					continue;
				}
				//create a date object with the valid start date
				Date startDate= new Date(start);
				System.out.println("Enter end date(MM/DD/YY):");
				//read end date entered by user
				String end=input.next();
				try {
					Date endDate=new Date(end);
				}
				catch(IllegalArgumentException e) {
					System.err.println("Invalid date format. Try again.");
					continue;
				}
				//check if start date is earlier than end date. If not, display an error message
				if(new Date(end).compareTo(new Date(start))<0) {
					System.err.println("End date is earlier than start date. Try again.");
					continue;
				}
				Date endDate=new Date(end);
				System.out.println("Motor Vehicle Collisions for zipcode "+zip+" ("+startDate.toString()
				+"-"+endDate.toString()+")"+"\n"+
				"===========================================================================");
				System.out.println(data.getReport(zip, startDate, endDate));
			}
		
	}
	/**
	 * Test if a String is an integer
	 * @param s String that this method is testing
	 * @return whether s is an integer
	 */
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may contain commas)
	 * @author Joanna Klukowska
	 * @param textLine	a line of text to be passed
	 * @return an Arraylist object containing all individual entries found on that line
	 */
	public static ArrayList<String> splitCSVLine(String textLine){
		
		ArrayList<String> entries = new ArrayList<String>(); 
		int lineLength = textLine.length(); 
		StringBuffer nextWord = new StringBuffer(); 
		char nextChar; 
		boolean insideQuotes = false; 
		boolean insideEntry= false;
		
		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);
			
			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') {
					
				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false; 
					insideEntry = false;
				}else {
					insideQuotes = true; 
					insideEntry = true;
				}
			} else if (Character.isWhitespace(nextChar)) {
				if ( insideQuotes || insideEntry ) {
				// add it to the current entry 
					nextWord.append( nextChar );
				}else { // skip all spaces between entries
					continue; 
				}
			} else if ( nextChar == ',') {
				if (insideQuotes){ // comma inside an entry
					nextWord.append(nextChar); 
				} else { // end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			} else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			} 
			
		}
		// add the last word ( assuming not empty ) 
		// trim the white space before adding to the list 
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}
		return entries;
	}
}
