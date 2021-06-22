package com.dongbin.Day1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Day1 {

	public static void main(String[] args) {
		String readFileName = "C:\\Users\\kopo44\\Documents\\DataAna\\rawData2.csv";
		String writeFileName = "C:\\Users\\kopo44\\Documents\\DataAna\\rawData4.csv";

		CSVReader csvReader;
		try {
			csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName), "CP949"));
			String[] nextLine;
			while ((nextLine = csvReader.readNext()) != null) {
				System.out.println(nextLine.length + " : " + String.join("|", nextLine));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			CSVWriter cw = new CSVWriter(new FileWriter(writeFileName));
			String[] data = {"abc", "def", "ghi"};
			cw.writeNext(data);
			cw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
