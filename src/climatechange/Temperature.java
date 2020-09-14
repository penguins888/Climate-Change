package climatechange;

import java.util.HashMap;


//
// This class models the components of one line of data in the text file as a Temperature object.
// This class provides the methods to get the data of each component from the line of data by implementing the ITemperature interface.
// These temperature objects can also be sorted from the implementation of the compareTo method, as well as equals and hashcode to obey the contract.
//


public class Temperature implements ITemperature, Comparable<ITemperature> 
{ 
	private String 	country;
	private String 	countryCode;
	private String 	month;
	private int 	year;
	private double 	temperature;

	
	// Constructs a new Temperature object using all the components from the line of data
	public Temperature(double theTemp, int theYear, String theMonth, String theCountry, String theCountryCode) 
	{
		country 	= theCountry;
		countryCode = theCountryCode;
		month 		= theMonth;
		year 		= theYear;
		temperature = theTemp;
	}

	
	// Get the name of the country (Implementation of method in ITemperature interface)
	public String getCountry() 
	{
		return country;
	}

	
	// Get the 3-letter code of the country (Implementation of method in ITemperature interface)
	public String getCountry3LetterCode() 
	{
		return countryCode;
	}

	
	// Get the month (Implementation of method in ITemperature interface)
	public String getMonth() 
	{
		return month;
	}

	
	// Get the year (Implementation of method in ITemperature interface)
	public int getYear() 
	{
		return year;
	}

	
	// Get temperature (Implementation of method in ITemperature interface)
	// If the input parameter is false = return Celsius value, otherwise convert to Fahrenheit
	public double getTemperature(boolean getFahrenheit) 
	{
		double temp = temperature;
		if(getFahrenheit == true) 
		{
			temp = ((double)9/5) * temperature + 32;
		}
		return temp;
	}

	
	// Returns a String representation of the object, similar to how the line of data is formatted in the data file
	// Temperature value should be formatted to use a maximum of 2 decimal places
	// Will be called when writing data to a file
	public String toString()
	{
		return String.format("%.2f", getTemperature(false)) + "(C) " + String.format("%.2f", getTemperature(true)) 
		+ " (F)" + ", "+  year + ", "+ month + ", "+ country + ", "+ countryCode;
	}

	
	// Implementation of the compareTo method in the Comparable interface
	// Sorts the objects in order of: temperature, country name, year, month
	@Override
	public int compareTo(ITemperature diffObject) 
	{
		if(temperature != diffObject.getTemperature(false))
		{
			return Double.compare(temperature, diffObject.getTemperature(false));
		}
		else if(!country.equals(diffObject.getCountry()))
		{
			return country.compareTo(diffObject.getCountry());
		}
		else if(year != diffObject.getYear()) 
		{
			return Integer.compare(year, diffObject.getYear());
		}
		else 
		{
			return Integer.compare(stringMonthToInt().get(month), stringMonthToInt().get(diffObject.getMonth()));
		}
	}


	// Checks whether two Temperature objects are equivalent 
	// Compatible with the method compareTo of the Comparable interface
	public boolean equals(Object diffObject) 
	{ 
		// objects then cast
		Temperature diff = (Temperature) diffObject;
		if(this.compareTo(diff) == 0)
		{
			return true;
		}
		return false;
	}

	
	// Creates a unique code for each object
	// Compatible with the method compareTo of the Comparable interface
	public int hashCode() 
	{
		return (int)temperature + country.hashCode() + year + month.hashCode(); 
	}

	
	// This method was made so that the months can be compared and ordered more easily 
	// The String month gets mapped to its respective Integer from 1-12
	public HashMap<String, Integer> stringMonthToInt() 
	{
		HashMap<String, Integer> stringToInt = new HashMap<>();
		stringToInt.put("Jan", 1);
		stringToInt.put("Feb", 2);
		stringToInt.put("Mar", 3);
		stringToInt.put("Apr", 4);
		stringToInt.put("May", 5);
		stringToInt.put("Jun", 6);
		stringToInt.put("Jul", 7);
		stringToInt.put("Aug", 8);
		stringToInt.put("Sep", 9);
		stringToInt.put("Oct", 10);
		stringToInt.put("Nov", 11);
		stringToInt.put("Dec", 12);
		return stringToInt;
	}
}
