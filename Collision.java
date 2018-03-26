package project5;

import java.util.ArrayList;
/* *
 * Stores information about a particular collision
 * @author aubreyzhou
 *
 */
public class Collision implements Comparable<Collision> {
	//data of a collision
	private String zip;
	private Date date;
	private String key;
	private int personsInjured;
	private int pedestriansInjured;
	private int cyclistsInjured;
	private int motoristsInjured;
	private int personsKilled;
	private int pedestriansKilled;
	private int cyclistsKilled;
	private int motoristsKilled;
	
	/**
	 * Constructs a collision object given the arraylist which contains information
	 * of the collision
	 * @param entries an arraylist that stores the information of the collision
	 * @throws IllegalArgumentException
	 */
	public Collision (ArrayList<String> entries)throws IllegalArgumentException{
		//validate if the arraylist is not null or not empty
		if(entries==null||entries.isEmpty()) {
			throw new IllegalArgumentException();
		}
		String zip1= entries.get(3);
		//validate if the zip code is 5 character string with digits
		//as all of its characters
		if(!(zip1.length()==5&&isInteger(zip1))) {
			throw new IllegalArgumentException();
		}
		zip=zip1;
		try {
			//test if date entry represents a valid date object
			Date date1=new Date(entries.get(0));
		}
		catch(IllegalArgumentException e){
			throw new IllegalArgumentException();
		}
		//validate that date entry is not empty
		if(entries.get(0)==null) {
			throw new IllegalArgumentException();
		}
		date=new Date(entries.get(0));
		//validate that key entry is not an empty string
		if(entries.get(23)==null||!(entries.get(23)instanceof String)) {
			throw new IllegalArgumentException();
		}
		key=entries.get(23);
		//validate that these entries are non-negative integers
		try {
			Integer.parseInt(entries.get(10));
		}
		catch(NumberFormatException e){
			throw new IllegalArgumentException();
		}
		int personsInjured1=Integer.parseInt(entries.get(10));
		if(personsInjured1<0) {
			throw new IllegalArgumentException();
		}
		personsInjured=personsInjured1;
		try {
			Integer.parseInt(entries.get(11));
		}
		catch(NumberFormatException e){
			throw new IllegalArgumentException();
		}
		int personsKilled1=Integer.parseInt(entries.get(11));
		if(personsKilled1<0) {
			throw new IllegalArgumentException();
		}
		personsKilled=personsKilled1;
		try {
			Integer.parseInt(entries.get(12));
		}
		catch(NumberFormatException e){
			throw new IllegalArgumentException();
		}
		int pedestriansInjured1=Integer.parseInt(entries.get(12));
		if(pedestriansInjured1<0) {
			throw new IllegalArgumentException();
		}
		pedestriansInjured=pedestriansInjured1;
		try {
			Integer.parseInt(entries.get(13));
		}
		catch(NumberFormatException e){
			throw new IllegalArgumentException();
		}
		int pedestrainsKilled1=Integer.parseInt(entries.get(13));
		if(pedestrainsKilled1<0) {
			throw new IllegalArgumentException();
		}
		pedestriansKilled=pedestrainsKilled1;
		try {
			Integer.parseInt(entries.get(14));
		}
		catch(NumberFormatException e){
			throw new IllegalArgumentException();
		}
		int cyclistsInjured1=Integer.parseInt(entries.get(14));
		if(cyclistsInjured1<0) {
			throw new IllegalArgumentException();
		}
		cyclistsInjured=cyclistsInjured1;
		try {
			Integer.parseInt(entries.get(15));
		}
		catch(NumberFormatException e){
			throw new IllegalArgumentException();
		}
		int cyclistsKilled1=Integer.parseInt(entries.get(15));
		if(cyclistsKilled1<0) {
			throw new IllegalArgumentException();
		}
		cyclistsKilled=cyclistsKilled1;
		try {
			Integer.parseInt(entries.get(16));
		}
		catch(NumberFormatException e){
			throw new IllegalArgumentException();
		}
		int motoristsInjured1=Integer.parseInt(entries.get(16));
		if(motoristsInjured1<0) {
			throw new IllegalArgumentException();
		}
		motoristsInjured=motoristsInjured1;
		try {
			Integer.parseInt(entries.get(17));
		}
		catch(NumberFormatException e){
			throw new IllegalArgumentException();
		}
		int motoristsKilled1=Integer.parseInt(entries.get(17));
		if(motoristsKilled1<0) {
			throw new IllegalArgumentException();
		}
		motoristsKilled=motoristsKilled1;
	}
	/**
	 * Returns zip code of the collision
	 * @return zip code as String
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * Returns date of the collision
	 * @return a date object of the collision
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Returns key of the collision
	 * @return key as String
	 */
	public String getKey() {
		return key;
	}
	/**
	 * Returns number of people injured in the collision
	 * @return number of injured people
	 */
	public int getPersonsInjured() {
		return personsInjured;
	}
	/**
	 * Returns number of pedestrians injured in the collision
	 * @return number of injured pedestrians
	 */
	public int getPedestriansInjured() {
		return pedestriansInjured;
	}
	/**
	 * Returns number of cyclists injured in the collision
	 * @return number of injured cyclists
	 */
	public int getCyclistsInjured() {
		return cyclistsInjured;
	}
	/**
	 * Returns number of motorists injured in the collision
	 * @return number of injured motorists
	 */
	public int getMotoristsInjured() {
		return motoristsInjured;
	}
	/**
	 * Returns number of persons killed in the collision
	 * @return number of killed person
	 */
	public int getPersonsKilled() {
		return personsKilled;
	}
	/**
	 * Returns number of pedestrian killed in the collision
	 * @return number of killed pedestrian
	 */
	public int getPedestriansKilled() {
		return pedestriansKilled;
	}
	/**
	 * Returns number of cyclists killed in the collision
	 * @return number of killed cyclists
	 */
	public int getCyclistsKilled() {
		return cyclistsKilled;
	}
	/**
	 * Returns number of motorists killed in the collision
	 * @return number of killed motorists
	 */
	public int getMotoristsKilled() {
		return motoristsKilled;
	}
	/**
	 * Compares two collision objects based on their zip code, date and key
	 * @param the collision object "this" is comparing to
	 *
	 */
	public int compareTo(Collision o) throws NullPointerException{
		if(o==null) throw new NullPointerException();
		if (this == o)
		return 0;
		if(this.zip.equals(o.zip)) {
			if(this.date.equals(o.date)) {
				if(this.key.equalsIgnoreCase(o.key)) {
					return 0;
				}
				else
					return this.key.compareToIgnoreCase(o.key);
			}
			else
				return this.date.compareTo(o.date);
		}
		else
			return this.zip.compareTo(o.zip);
	}
	/**
	 * Check if two collision objects are equal based on their zip code,
	 * date and key
	 * @param the object that "this" is testing equality to
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collision other = (Collision) obj;
		//test if zip is the same
		if (!zip.equals(other.zip))
			return false;
		//test if date is the same
		if (!date.equals(other.date))
			return false;
		//test if key is the same
		if (!key.equalsIgnoreCase(other.key))
			return false;
		return true;
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
}

