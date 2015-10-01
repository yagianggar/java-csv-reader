package com.yagi.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSV {
	public static void main(String[] args) {
		String csvFile = "src/com/yagi/reader/IATA_airports.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String timezoneLocation = "Europe/London";
		
		/*
		String sqlQuery = "UPDATE `locations` l "+
							"JOIN airports a on a.`location_id` = l.`id` "+
							"JOIN countries c on l.`country_id` = c.`id` "+
							"SET iana_time_zone = \""+timezoneLocation+"\" ";
		*/
		
		String sqlQuery = "UPDATE `locations` l "+
				"SET iana_time_zone = \""+timezoneLocation+"\" ";

		String sqlFilter = " WHERE l.`code` = ";
		try {

			br = new BufferedReader(new FileReader(csvFile));
			int counter = 0;
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] locations = line.split(cvsSplitBy);
				if (locations[3].equals(timezoneLocation)) {
					System.out.println("Locations [code : "+locations[0]+", iana_time_zone="+locations[3]+"]");
					if (counter == 0) {
						sqlFilter += "\""+locations[0]+"\" ";
					} else {
						sqlFilter += "OR l.`code` = \""+locations[0]+"\" ";
					}
					counter++;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Full QUery : "+sqlQuery+sqlFilter);
		System.out.println("Done");
	}
}
