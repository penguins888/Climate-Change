package climatechange;


//
// Runs the runClimateAnalyzer method in ClimateAnalyzer. 
// By calling the method, the user gets to input values and create the unique data files.
//


public class RunClimateAnalyzer {
	
	public static void main (String[] args) {
		ClimateAnalyzer ca = new ClimateAnalyzer("world_temp_2000-2016.csv");
		ca.runClimateAnalyzer();
	}
	
}
