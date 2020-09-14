package climatechange;

import java.util.*;


//
// Analyzes the original data file through a variety of methods.
// Methods return specific data based on user input, which then get written to individual data files for each task. 
//


public class ClimateAnalyzer implements IClimateAnalyzer
{
	private ArrayList<ITemperature> data;


	// Constructor that creates the ArrayList of the original data that will be used in all the analyzing methods
	// Initializes a WeatherIO object that gets the needed ArrayList of data
	// Parameter of fileName in order to run the readDataFromFile method for ArrayList
	public ClimateAnalyzer(String fileName) 
	{
		WeatherIO dataSource = new WeatherIO();
		data = dataSource.readDataFromFile(fileName);
	}


	// This method was made so that the integers passed into the analyzing methods can be reverted to the String month
	// Once they are reversed to the String, it can be compared to getCountry of the ITemperature object
	// The int month gets mapped to its respective String month
	public HashMap<Integer,String> intMonthToString()
	{
		HashMap<Integer, String> intToString = new HashMap<>();
		intToString.put(1, "Jan");
		intToString.put(2, "Feb");
		intToString.put(3, "Mar");
		intToString.put(4, "Apr");
		intToString.put(5, "May");
		intToString.put(6, "Jun");
		intToString.put(7, "Jul");
		intToString.put(8, "Aug");
		intToString.put(9, "Sep");
		intToString.put(10, "Oct");
		intToString.put(11, "Nov");
		intToString.put(12, "Dec");
		return intToString;
	}


	// TASK A-1
	// For all data that matches the specified month, get the lowest temperature reading
	public ITemperature getLowestTempByMonth(String country, int month) 
	{
		// This process is how most of the A tasks will be implemented
		// TreeSet - data structure that sorts the data from low to high temperature
		TreeSet<ITemperature> monthList = new TreeSet<ITemperature>();
		for(ITemperature tempData : data) 
		{
			if(tempData.getCountry().equalsIgnoreCase(country) && tempData.getMonth().equals(intMonthToString().get(month))) 
			{
				monthList.add(tempData);
			}
		}

		// Pass the TreeSet into the ArrayList
		ArrayList<ITemperature> sorted = new ArrayList<ITemperature>(monthList);

		// Get the first value of the ArrayList - this value is the ITemperature object with the lowest temperature 
		// Since the TreeSet sorted the data, the first value is the one with the lowest temperature
		return sorted.get(0);
	}


	// TASK A-1
	// For all data that matches the specified month, get the highest temperature reading
	public ITemperature getHighestTempByMonth(String country, int month) 
	{
		TreeSet<ITemperature> monthList = new TreeSet<ITemperature>();
		for(ITemperature tempData : data) 
		{
			if(tempData.getCountry().equalsIgnoreCase(country) && tempData.getMonth().equals(intMonthToString().get(month))) 
			{
				monthList.add(tempData);
			}
		}
		ArrayList<ITemperature> sorted = new ArrayList<ITemperature>(monthList);

		// Since the TreeSet sorted the data, the last value is the one with the highest temperature 
		// The index of the last value would be the size of the ArrayList minus one
		return sorted.get(sorted.size() - 1);
	}


	// TASK A-2
	// For all data that matches the specified year, get the lowest temperature reading
	public ITemperature getLowestTempByYear(String country, int year) 
	{
		TreeSet<ITemperature> yearList = new TreeSet<ITemperature>();
		for(ITemperature tempData : data) 
		{
			if(tempData.getCountry().equalsIgnoreCase(country) && tempData.getYear() == year) 
			{
				yearList.add(tempData);
			}
		}
		ArrayList<ITemperature> sorted = new ArrayList<ITemperature>(yearList);
		return sorted.get(0);
	}


	// TASK A-2
	// For all data that matches the specified year, get the highest temperature reading
	public ITemperature getHighestTempByYear(String country, int year) 
	{
		TreeSet<ITemperature> yearList = new TreeSet<ITemperature>();
		for(ITemperature tempData : data) 
		{
			if(tempData.getCountry().equalsIgnoreCase(country) && tempData.getYear() == year) 
			{
				yearList.add(tempData);
			}
		}
		ArrayList<ITemperature> sorted = new ArrayList<ITemperature>(yearList);
		return sorted.get(sorted.size() - 1);
	}


	// TASK A-3
	// Get all temperature data that fall within the given temperature range 
	// The set is sorted from lowest to highest temperature
	// Input parameter values are in Celsius
	public TreeSet<ITemperature> getTempWithinRange(String country, double rangeLowTemp, double rangeHighTemp)
	{
		TreeSet<ITemperature> rangeList = new TreeSet<ITemperature>();
		for(ITemperature tempData : data) 
		{
			double temp = tempData.getTemperature(false);
			if(tempData.getCountry().equalsIgnoreCase(country) && temp >= rangeLowTemp && temp <= rangeHighTemp) 
			{
				rangeList.add(tempData);
			}
		}
		return rangeList;
	}


	// TASK A-4
	// 1. Get the lowest temperature reading amongst all data for that country
	public ITemperature getLowestTempYearByCountry(String country) 
	{
		TreeSet<ITemperature> countryList = new TreeSet<ITemperature>();
		for(ITemperature tempData : data) 
		{
			if(tempData.getCountry().equalsIgnoreCase(country)) 
			{
				countryList.add(tempData);
			}
		}
		ArrayList<ITemperature> sorted = new ArrayList<ITemperature>(countryList);
		return sorted.get(0);
	}


	// TASK A-4
	// 1. Get the highest temperature reading amongst all data for that country
	public ITemperature getHighestTempYearByCountry(String country) 
	{
		TreeSet<ITemperature> countryList = new TreeSet<ITemperature>();
		for(ITemperature tempData : data) 
		{
			if(tempData.getCountry().equalsIgnoreCase(country)) 
			{
				countryList.add(tempData);
			}
		}
		ArrayList<ITemperature> sorted = new ArrayList<ITemperature>(countryList);
		return sorted.get(sorted.size() - 1);
	}


	// TASK B-1
	// 1. The return list is sorted from lowest to highest temperature
	public ArrayList<ITemperature> allCountriesGetTop10LowestTemp(int month)
	{
		// This process is how most of the B tasks will be implemented 
		TreeSet<ITemperature> countryList = new TreeSet<ITemperature>();
		for(ITemperature tempData : data) 
		{
			if(tempData.getMonth().equals(intMonthToString().get(month))) 
			{
				countryList.add(tempData);
			}
		}

		// HashSet - efficient data structure that does not allow duplicates
		// Used to check whether the countries of the ITemperature objects are unique while traversing the TreeSet
		HashSet<String> checUniqCountry = new HashSet<String>();

		// A new list with the unique countries with low temperatures will be formed and returned at the end of the method
		// This list will have 10 objects, since we want the top 10 countries
		ArrayList<ITemperature> uniqList = new ArrayList<ITemperature>();

		// Since the countryList is a TreeSet, all the data is already sorted from low to high temperature
		// This means we can just traverse through the list as normal until the ArrayList is filled up
		for(ITemperature temps : countryList) 
		{
			// If the ArrayList is empty, there is no need to check against the HashSet and the object can be added
			if(uniqList.size() == 0) 
			{
				uniqList.add(temps);

				// Add the country to the HashSet to start the checking process
				checUniqCountry.add(temps.getCountry());
			} 
			else 
			{
				// If the country is unique and the size of the ArrayList size is not 10 then add the ITemperature object to the ArrayList
				if(!checUniqCountry.contains(temps.getCountry()) && uniqList.size() != 10) 
				{
					checUniqCountry.add(temps.getCountry());
					uniqList.add(temps);
				}
			}

			// If the size of the ArrayList is 10, then there is no need to continue iterating through the data (break out of the for loop)
			if(uniqList.size() == 10) 
			{
				break;
			}
		}
		return uniqList;
	}


	// TASK B-1
	// 1. The return list is sorted from lowest to highest temperature
	public ArrayList<ITemperature> allCountriesGetTop10HighestTemp(int month)
	{
		TreeSet<ITemperature> countryList = new TreeSet<ITemperature>();
		for(ITemperature tempData : data) 
		{
			if(tempData.getMonth().equals(intMonthToString().get(month)))
			{
				countryList.add(tempData);
			}
		}

		// TreeSet sorts from low to high temperature but we want the highest temperatures 
		// Reverse the order of the TreeSet so that the highest temperatures come first
		TreeSet<ITemperature> countryListR = (TreeSet<ITemperature>) countryList.descendingSet();
		ArrayList<ITemperature> uniqList = new ArrayList<ITemperature>();
		HashSet<String> checUniqCountry = new HashSet<String>();

		// Since the highest temperatures come first after reverse, traverse like before
		for(ITemperature temps : countryListR) 
		{
			if(uniqList.size() == 0) 
			{
				uniqList.add(temps);
			} 
			else 
			{
				if(!checUniqCountry.contains(temps.getCountry()) && uniqList.size() != 10) 
				{
					checUniqCountry.add(temps.getCountry());
					uniqList.add(temps);
				}
			}
			if(uniqList.size() == 10) 
			{
				break;
			}
		}

		// Want the top 10 list to be ordered from low temperature to high temperature
		// Use descending for loop to access the elements in reverse order and store them in another ArrayList
		ArrayList<ITemperature> uniqListR = new ArrayList<ITemperature>();
		for(int i = uniqList.size()-1; i >= 0; i--) 
		{
			uniqListR.add(uniqList.get(i));
		}
		return uniqListR;
	}


	// TASK B-2
	// 1. The return list is sorted from lowest to highest temperature
	public ArrayList<ITemperature> allCountriesGetTop10LowestTemp()
	{
		// Pass the original data into a TreeSet so it gets sorted
		TreeSet<ITemperature> allCountryList = new TreeSet<ITemperature>(data);
		HashSet<String> checUniqCountry = new HashSet<String>();
		ArrayList<ITemperature> uniqList = new ArrayList<ITemperature>();
		for(ITemperature temps : allCountryList) 
		{
			if(uniqList.size() == 0) 
			{
				uniqList.add(temps);
				checUniqCountry.add(temps.getCountry());
			} 
			else 
			{
				if(!checUniqCountry.contains(temps.getCountry()) && uniqList.size() != 10) 
				{
					checUniqCountry.add(temps.getCountry());
					uniqList.add(temps);
				}
			}
			if(uniqList.size() == 10) 
			{
				break;
			}
		}
		return uniqList;
	}


	// TASK B-2
	// 1. The return list is sorted from lowest to highest temperature
	public ArrayList<ITemperature> allCountriesGetTop10HighestTemp()
	{
		TreeSet<ITemperature> countryList = new TreeSet<ITemperature>(data);

		// Reverse the order of the sorted data
		TreeSet<ITemperature> countryListR = (TreeSet<ITemperature>) countryList.descendingSet();
		HashSet<String> checUniqCountry = new HashSet<String>();
		ArrayList<ITemperature> uniqList = new ArrayList<ITemperature>();
		for(ITemperature temps : countryListR) 
		{
			if(uniqList.size() == 0) 
			{
				uniqList.add(temps);
				checUniqCountry.add(temps.getCountry());
			} 
			else 
			{
				if(!checUniqCountry.contains(temps.getCountry()) && uniqList.size() != 10) 
				{
					checUniqCountry.add(temps.getCountry());
					uniqList.add(temps);
				}
			}
			if(uniqList.size() == 10) 
			{
				break;
			}
		}
		ArrayList<ITemperature> uniqListR = new ArrayList<ITemperature>();
		for(int i = uniqList.size()-1; i >= 0; i--) 
		{
			uniqListR.add(uniqList.get(i));
		}
		return uniqListR;
	}


	// TASK B-3
	// 1. The return list is sorted from lowest to highest temperature
	public ArrayList<ITemperature> allCountriesGetAllDataWithinTempRange(double lowRangeTemp, double highRangeTemp)
	{
		TreeSet<ITemperature> rangeList = new TreeSet<ITemperature>();
		for(ITemperature tempData : data) 
		{
			if(tempData.getTemperature(false) >= lowRangeTemp && tempData.getTemperature(false) <= highRangeTemp) 
			{
				rangeList.add(tempData);
			}
		}

		// No need top ten or unique countries
		ArrayList<ITemperature> rangeTemps = new ArrayList<ITemperature>(rangeList);
		return rangeTemps;
	}


	// TASK C-1
	// 1. The countries with the largest temperature differences (absolute value) of the same month between 2 given years. 
	// 2. The return list is sorted from lowest to highest temperature delta
	public ArrayList<ITemperature> allCountriesTop10TempDelta(int month, int year1, int year2)
	{
		// Not starting with TreeSet because want the order natural order of the data to be preserved
		// This way the years for the same country will be next to each other and easier to access for calculations
		ArrayList<ITemperature> deltaList = new ArrayList<ITemperature>();
		for(ITemperature tempData : data) 
		{
			if(tempData.getMonth().equals(intMonthToString().get(month))) 
			{
				if(tempData.getYear() == year1 || tempData.getYear() == year2) 
				{
					deltaList.add(tempData);
				}
			}
		}

		// Once the new data with the deltas is formed, it can be put into a TreeSet to be sorted
		TreeSet<ITemperature> deltas = new TreeSet<ITemperature>();

		// For loop iterates by two because each country has two ITemperature objects, one for each year
		for(int i = 0; i < deltaList.size(); i = i + 2) 
		{
			// To get the temperature delta, subtract the temperatures of the ITemperature objects next to each other and absolute value it
			double deltaTemp = Math.abs(deltaList.get(i + 1).getTemperature(false) - deltaList.get(i).getTemperature(false));

			// To get the year delta, subtract the years of the ITemperature objects and absolute value it, in case the user enters the larger year first
			int deltaYear = Math.abs(deltaList.get(i + 1).getYear() - deltaList.get(i).getYear());

			// Create a new ITemperature object with the delta temperature and delta year and add it to the TreeSet
			ITemperature newDel = new Temperature(deltaTemp, deltaYear, deltaList.get(i).getMonth(), deltaList.get(i).getCountry(), deltaList.get(i).getCountry3LetterCode());
			deltas.add(newDel);
		}

		// Since we want the highest -> descendingSet for TreeSet
		// Same implementation as task B
		TreeSet<ITemperature> deltasR = (TreeSet<ITemperature>) deltas.descendingSet();
		HashSet<String> checUniqCountry = new HashSet<String>();
		ArrayList<ITemperature> uniqList = new ArrayList<ITemperature>();
		for(ITemperature temps : deltasR) 
		{
			if(uniqList.size() == 0) 
			{
				uniqList.add(temps);
				checUniqCountry.add(temps.getCountry());
			} 
			else 
			{
				if(!checUniqCountry.contains(temps.getCountry()) && uniqList.size() != 10) 
				{
					checUniqCountry.add(temps.getCountry());
					uniqList.add(temps);
				}
			}
			if(uniqList.size() == 10) 
			{
				break;
			}
		}
		ArrayList<ITemperature> uniqListR = new ArrayList<ITemperature>();
		for(int i = uniqList.size()-1; i >= 0; i--) 
		{
			uniqListR.add(uniqList.get(i));
		}
		return uniqListR;
	}


	// 1. This method starts the climate-change task activities
	// 2. The ClimateChange methods must be called in the order as listed in the [description section], (first with the Task A 
	// 	  methods, second are the Task B methods, and third are the Task C methods)
	// 3. For each of the ClimateChange methods that require input parameters, this method must ask the user to
	//    enter the required information for each of the tasks.
	// 4. Each ClimateAnalyzer method returns data, so the data results must be written to data files
	public void runClimateAnalyzer() 
	{
		// Process:
		// Scanner asks for input
		// Input is put into the climate change methods 
		// The climate change methods return a ITemperature object or ArrayList
		// Write those lists to the files using the WeatherIO methods
		// End up with 8 data files
		WeatherIO write = new WeatherIO();

		// Same for all tasks except C1
		String topic = "Temperature, Year, Month, Country, Country_Code";

		// Created to check whether the user inputs a valid country
		HashSet<String> allCountries = new HashSet<String>();
		for(ITemperature i : data) 
		{
			allCountries.add(i.getCountry().toLowerCase());
		}

		// TASK A1 processing
		System.out.println("Task A1 - Get Lowest and Highest Temperature By Month for a Country");
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter country:");
		String countryA1 ="";

		// Check whether the country is valid. If not, then prompt user to re-enter
		while(scan.hasNextLine()) 
		{
			countryA1 = scan.nextLine();
			if(!allCountries.contains(countryA1.toLowerCase().trim())) 
			{
				System.out.println("Not a valid country. Please re-enter: ");
			}
			else 
			{
				countryA1 = countryA1.trim();
				break;
			}
		}
		System.out.println("Enter numeric value of the month (1-12):");
		int monthA1 = 0;

		// This is how most of the number checking will be done
		// Checks if the user inputs something other than an int
		while(!scan.hasNextInt()) 
		{
			System.out.println("Invalid month. Please enter a numeric month (1-12):");
			scan.next();
			if(scan.hasNextInt()) 
			{
				break;
			}
		}

		// Checks the int that the user inputs to make sure it is a valid one
		while(scan.hasNextInt()) 
		{
			monthA1 = scan.nextInt();
			if(monthA1 > 12 || monthA1 < 1) 
			{
				System.out.println("Not a valid month. Please enter a numeric month (1-12): ");
			}
			else 
			{
				break;
			}
		}

		// Creates ArrayList and adds the ITemperature object returned by the method to it
		ArrayList<ITemperature> lowTempByMonth = new ArrayList<ITemperature>();
		lowTempByMonth.add(getLowestTempByMonth(countryA1, monthA1));
		ArrayList<ITemperature> highTempByMonth = new ArrayList<ITemperature>();
		highTempByMonth.add(getHighestTempByMonth(countryA1, monthA1));

		// Creating and writing to file
		String subjectA1L = "Task A1: Lowest Temperature for " + countryA1 + " for the Month of " + intMonthToString().get(monthA1);
		write.writeSubjectHeaderInFile("taskA1_climate_info.csv", subjectA1L);
		write.writeDataToFile("taskA1_climate_info.csv", topic, lowTempByMonth);
		String subjectA1H = "Task A1: Highest Temperature for " + countryA1 + " for the Month of " + intMonthToString().get(monthA1);
		write.writeSubjectHeaderInFile("taskA1_climate_info.csv", subjectA1H);
		write.writeDataToFile("taskA1_climate_info.csv", topic, highTempByMonth);


		// TASK A2 processing
		System.out.println("Task A2 - Get Lowest and Highest Temperature By Year for a Country");
		scan.nextLine();
		System.out.println("Enter country:");
		String countryA2 ="";

		// Check whether the country is valid. If not, then prompt user to re-enter
		while(scan.hasNextLine()) 
		{
			countryA2 = scan.nextLine();
			if(!allCountries.contains(countryA2.toLowerCase().trim())) 
			{
				System.out.println("Not a valid country. Please re-enter: ");
			}
			else 
			{
				countryA2 = countryA2.trim();
				break;
			}
		}
		System.out.println("Enter year (2000-2016):");
		int yearA2  = 0;

		// Checks if the user enters something other than an int
		while(!scan.hasNextInt()) 
		{
			System.out.println("Invalid year. Please enter a numeric year:");
			scan.next();
			if(scan.hasNextInt()) 
			{
				break;
			}
		}

		// Checks to see if the int is within the accepted range
		while(scan.hasNextInt()) 
		{
			yearA2 = scan.nextInt();
			if(yearA2 > 2016 || yearA2 < 2000) 
			{
				System.out.println("Not a valid year. Please enter a year between 2000 and 2016: ");
			}
			else 
			{
				break;
			}
		}

		// Creates ArrayList and adds the ITemperature object returned by the method to it
		ArrayList<ITemperature> lowTempByYear = new ArrayList<ITemperature>();
		lowTempByYear.add(getLowestTempByYear(countryA2, yearA2));
		ArrayList<ITemperature> highTempByYear = new ArrayList<ITemperature>();
		highTempByYear.add(getHighestTempByYear(countryA2, yearA2));

		// Creating and writing to file
		String subjectA2L = "Task A2: Lowest Temperature for " + countryA2 + " for the Year " + yearA2;
		write.writeSubjectHeaderInFile("taskA2_climate_info.csv", subjectA2L);
		write.writeDataToFile("taskA2_climate_info.csv", topic, lowTempByYear);
		String subjectA2H = "Task A2: Highest Temperature for " + countryA2 + " for the Year " + yearA2;
		write.writeSubjectHeaderInFile("taskA2_climate_info.csv", subjectA2H);
		write.writeDataToFile("taskA2_climate_info.csv", topic, highTempByYear);


		// TASK A3 processing
		System.out.println("Task A3 - Get Temperature for a Country between a Temperature Range");
		scan.nextLine();
		System.out.println("Enter country:");
		String countryA3 ="";

		// Check whether the country is valid. If not, then prompt user to re-enter
		while(scan.hasNextLine()) 
		{
			countryA3 = scan.nextLine();
			if(!allCountries.contains(countryA3.toLowerCase().trim())) 
			{
				System.out.println("Not a valid country. Please re-enter: ");
			}
			else 
			{
				countryA3 = countryA3.trim();
				break;
			}
		}
		System.out.println("Enter the lowest temperature in Celsius:");
		
		// Check that the temperature is a number
		while(!scan.hasNextDouble()) 
		{
			System.out.println("Invalid temperature. Please enter a number:");
			scan.next();
			if(scan.hasNextDouble()) 
			{ 
				break;
			}
		}
		double lowTemp = scan.nextDouble();
		double highTemp = 100;
		System.out.println("Enter the highest temperature in Celsius:");

		// Check the temperature. Should be greater than the previous.
		while(!scan.hasNextDouble()) 
		{
			System.out.println("Invalid temperature. Please enter a number:");
			scan.next();
			if(scan.hasNextDouble()) 
			{ 
				break;
			}
		}
		while(scan.hasNextDouble()) 
		{
			highTemp = scan.nextDouble();
			if(highTemp < lowTemp) 
			{
				System.out.println("Invalid. Please enter a temperature greater than the one before: ");
			}
			else 
			{
				break;
			}
		}

		// Creates TreeSet and passes it into ArrayList to write to file 
		TreeSet<ITemperature> getTemps = getTempWithinRange(countryA3, lowTemp, highTemp);
		ArrayList<ITemperature> sortedTemps = new ArrayList<ITemperature>(getTemps);

		// Creating and writing to file
		String subjectA3 = "Task A3: Temperature for " + countryA3 + " within " + lowTemp + "-" + highTemp;
		write.writeSubjectHeaderInFile("taskA3_climate_info.csv", subjectA3);
		write.writeDataToFile("taskA3_climate_info.csv", topic, sortedTemps);


		// TASK A4 processing
		System.out.println("Task A4 - Get Lowest and Highest Temperature for a Country");
		scan.nextLine();
		System.out.println("Enter country:");
		String countryA4 ="";

		// Check whether the country is valid. If not, then prompt user to re-enter
		while(scan.hasNextLine()) 
		{
			countryA4 = scan.nextLine();
			if(!allCountries.contains(countryA4.toLowerCase().trim())) 
			{
				System.out.println("Not a valid country. Please re-enter: ");
			}
			else 
			{
				countryA4 = countryA4.trim();
				break;
			}
		}

		// Creates ArrayList and adds the ITemperature object returned by the method to it
		ArrayList<ITemperature> lowTempCountry = new ArrayList<ITemperature>();
		lowTempCountry.add(getLowestTempYearByCountry(countryA4));
		ArrayList<ITemperature> highTempCountry = new ArrayList<ITemperature>();
		highTempCountry.add(getHighestTempYearByCountry(countryA4));

		// Creating and writing to file
		String subjectA4L = "Task A4: Lowest Temperature for " + countryA4;
		write.writeSubjectHeaderInFile("taskA4_climate_info.csv", subjectA4L);
		write.writeDataToFile("taskA4_climate_info.csv", topic, lowTempCountry);
		String subjectA4H = "Task A4: Highest Temperature for " + countryA4;
		write.writeSubjectHeaderInFile("taskA4_climate_info.csv", subjectA4H);
		write.writeDataToFile("taskA4_climate_info.csv", topic, highTempCountry);


		// TASK B1 processing
		System.out.println("Task B1 - Get Lowest and Highest Temperature By Month for all Countries");
		System.out.println("Enter numeric value of the month:");
		int monthB1 = 0;

		// Check the month
		while(!scan.hasNextInt()) 
		{
			System.out.println("Invalid month. Please enter a numeric month (1-12):");
			scan.next();
			if(scan.hasNextInt()) 
			{ 
				break;
			}
		}
		while(scan.hasNextInt()) 
		{
			monthB1 = scan.nextInt();
			if(monthB1 > 12 || monthB1 < 1) 
			{
				System.out.println("Not a valid month. Please enter a numeric month (1-12): ");
			}
			else 
			{
				break;
			}
		}

		// Creates ArrayList and assigns it to the ArrayList returned from method 
		ArrayList<ITemperature> lowTen = allCountriesGetTop10LowestTemp(monthB1);
		ArrayList<ITemperature> highTen = allCountriesGetTop10HighestTemp(monthB1);

		// Creating and writing to file
		String subjectB1L = "Task B1: Countries with Lowest Temperature for the Month of " + intMonthToString().get(monthB1);
		write.writeSubjectHeaderInFile("taskB1_climate_info.csv", subjectB1L);
		write.writeDataToFile("taskB1_climate_info.csv", topic, lowTen);
		String subjectB1H = "Task B1: Countries with Highest Temperature for the Month of " + intMonthToString().get(monthB1);
		write.writeSubjectHeaderInFile("taskB1_climate_info.csv", subjectB1H);
		write.writeDataToFile("taskB1_climate_info.csv", topic, highTen);


		// TASK B2 processing
		System.out.println("Task B2 - Get Lowest and Highest Temperature for all Countries");

		// Creates ArrayList and assigns it to the ArrayList returned from method 
		ArrayList<ITemperature> lowTenAll = allCountriesGetTop10LowestTemp();
		ArrayList<ITemperature> highTenAll = allCountriesGetTop10HighestTemp();

		// Creating and writing to file
		String subjectB2L = "Task B2: Countries with Lowest Temperature";
		write.writeSubjectHeaderInFile("taskB2_climate_info.csv", subjectB2L);
		write.writeDataToFile("taskB2_climate_info.csv", topic, lowTenAll);
		String subjectB2H = "Task B2: Countries with Highest Temperature";
		write.writeSubjectHeaderInFile("taskB2_climate_info.csv", subjectB2H);
		write.writeDataToFile("taskB2_climate_info.csv", topic, highTenAll);
		System.out.println("Done!");


		// TASK B3 processing
		System.out.println("Task B3 - Get Temperature for All Countries between a Temperature Range");
		System.out.println("Enter the lowest temperature in Celsius:");

		// Check that the temperature is a number
		while(!scan.hasNextDouble())
		{
			System.out.println("Invalid temperature. Please enter a number:");
			scan.next();
			if(scan.hasNextDouble()) 
			{ 
				break;
			}
		}
		double lowTempB3 = scan.nextDouble();
		double highTempB3 = 100;
		System.out.println("Enter the highest temperature in Celsius:");

		// Check the temperature. Should be greater than the previous one.
		while(!scan.hasNextDouble()) 
		{
			System.out.println("Invalid temperature. Please enter a number:");
			scan.next();
			if(scan.hasNextDouble()) 
			{ 
				break;
			}
		}
		while(scan.hasNextDouble()) 
		{
			highTempB3 = scan.nextDouble();
			if(highTempB3 < lowTempB3) 
			{
				System.out.println("Invalid. Please enter a temperature greater than the one before: ");
			}
			else 
			{
				break;
			}
		}

		// Creates ArrayList and assigns it to the ArrayList returned from method 
		ArrayList<ITemperature> allCountryRange = allCountriesGetAllDataWithinTempRange(lowTempB3, highTempB3);

		// Creating and writing to file
		String subjectB3 = "Task B3: Temperature for Countries within " + lowTempB3 + "-" + highTempB3;
		write.writeSubjectHeaderInFile("taskB3_climate_info.csv", subjectB3);
		write.writeDataToFile("taskB3_climate_info.csv", topic, allCountryRange);


		// TASK C1 processing
		System.out.println("Task C1 - Get Top 10 Largest Temperature Change within a Country in the same Month between Two Years");

		// Check the month
		System.out.println("Enter numeric value of the month:");
		int monthC1 = 0;
		while(!scan.hasNextInt()) 
		{
			System.out.println("Invalid month. Please enter a numeric month (1-12).");
			scan.next();
			if(scan.hasNextInt())
			{ 
				break;
			}
		}
		while(scan.hasNextInt()) 
		{
			monthC1 = scan.nextInt();
			if(monthC1 > 12 || monthC1 < 1) 
			{
				System.out.println("Not a valid month. Please enter a numeric month (1-12): ");
			}
			else 
			{
				break;
			}
		}
		System.out.println("Enter one of the two years:");
		int year1 = 0;

		// Check the year
		while(!scan.hasNextInt())
		{
			System.out.println("Invalid year. Please enter a numeric year.");
			scan.next();
			if(scan.hasNextInt())
			{
				break;
			}
		}
		while(scan.hasNextInt()) 
		{
			year1 = scan.nextInt();
			if(year1 > 2016 || year1 < 2000) 
			{
				System.out.println("Not a valid year. Please enter a year between 2000 and 2016: ");
			}
			else 
			{
				break;
			}
		}
		System.out.println("Enter the other of the two years:");
		int year2 = 0;

		// Also checks whether the inputted year is the same as the other
		while(!scan.hasNextInt()) 
		{
			System.out.println("Invalid year. Please enter a numeric year.");
			scan.next();
			if(scan.hasNextInt()) 
			{
				break;
			}
		}
		while(scan.hasNextInt()) 
		{
			year2 = scan.nextInt();
			if(year2 > 2016 || year2 < 2000 || year2 == year1)
			{
				System.out.println("Not a valid year. Please enter a year between 2000 and 2016 that is different from the first year: ");
			}
			else 
			{
				break;
			}
		}

		// Creates ArrayList and assigns it to the ArrayList returned from method 
		ArrayList<ITemperature> deltasTen = allCountriesTop10TempDelta(monthC1, year1, year2);

		// Creating and writing to file
		String subjectC1 = "Task C1: Countries with the Largest Temperature Change in the Month of " + intMonthToString().get(monthC1) + " between the years " + year1 + " and " + year2;
		write.writeSubjectHeaderInFile("taskC1_climate_info.csv", subjectC1);
		write.writeDataToFile("taskC1_climate_info.csv", "Temperature Delta, Year Delta, Month, Country, Country_Code", deltasTen);
		System.out.println("Done!");
	}
}
