package climatechange;

import java.io.*;
import java.util.*;


//
// Deals with IO. Takes the file the user inputs, reads it, gathers the data for use across classes.
// Also creates new data csv files based on which task is being done. 
// Once the file is created, more data can be appended to it.
//


public class WeatherIO implements IWeatherIO
{
	// Read all data from the weather data file (Implementation of method from IWeatherIO)
	public ArrayList<ITemperature> readDataFromFile(String fileName)
	{
		ArrayList<ITemperature> dataFromFile = new ArrayList<ITemperature>();
		File dirf =   new File("./data/" + fileName);

		// Read from file
		try {
			Scanner in = new Scanner(dirf, "UTF-8");
			String topic = in.nextLine();
			while(in.hasNextLine()) 
			{
				// Read each line
				String temperatureData = in.nextLine();
				String[] dataParts = temperatureData.split(", ");
				double temp = Double.parseDouble(dataParts[0]);
				int year = Integer.parseInt(dataParts[1]);

				// Form ITemperature object with components of the line of data
				ITemperature data = new Temperature(temp, year, dataParts[2], dataParts[3],dataParts[4]);

				// Add the ITemperature object to the ArrayList
				dataFromFile.add(data);
			}
		}

		// If file is not found, IOException is caught
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		return dataFromFile;
	}


	// 1. Write the subject header before dumping data returned from each ClimateAnalyzer method 
	// 2. A subject header is to be written for each ClimateAnalyzer method call
	public void writeSubjectHeaderInFile(String filename, String subject) 
	{
		File dataf = new File( "./data/", filename);
		try 
		{  
			dataf.createNewFile(); 

			// File is appendable 
			FileWriter write = new FileWriter(dataf, true);
			PrintWriter pw = new PrintWriter(write);

			// Subject is written at the top before the data
			pw.println(subject);

			// Close writers in reverse order of creation
			pw.close();
			write.close();
		}

		// No disk space
		catch (IOException x) 
		{  
			System.out.println("Stress: " + x.getMessage());
		}
	}


	// 1. File name should be called “taskXX_climate_info.csv” where XX will be replaced by the task id: A1, A2, etc 
	// 2. Use this method to store the temperature info (for each Climate Analyzer task)
	// 3. Temperature value should be formatted to use a maximum of 2 decimal places
	// 4. Temperature field should also show the Fahrenheit value (using decimal rules above) 
	public void writeDataToFile(String filename, String topic, ArrayList<ITemperature> theWeatherList) 
	{
		// Filename created in the runClimateAnalyzer method and passed on into this method
		// Should already be made when calling writeSubjectHeaderInFile
		File dataf = new File("./data/", filename);
		try 
		{  
			// File appendable
			FileWriter write = new FileWriter(dataf, true);
			PrintWriter pw = new PrintWriter(write);

			// Write the topic at the top
			pw.println(topic);
			for (ITemperature tempData : theWeatherList) 
			{
				// Get the information from the ITemperature objects using toString
				// toString takes care of 3 and 4
				pw.println(tempData.toString());	
			}
			pw.println();
			pw.close();
			write.close();
		}
		catch (IOException x) 
		{  
			x.printStackTrace();
		}
	}
}
