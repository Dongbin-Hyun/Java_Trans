package com.dongbin.Trans;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Trans {
	String writeFileName = "/Users/aries/Documents/DataFolder/01/dongbin.csv";
	String readFileName;
	CSVReader csvReader;
	HashSet<String> date = new HashSet<String>();
	List dateSort;
	int[] onBus = new int[366];
	int[] offBus = new int[366];
	int[] onMetro = new int[366];
	int[] offMetro = new int[366];
	int[] covid19Cnt = new int[366];

	public static void main(String[] args) throws CsvValidationException, IOException {
		Trans trans = new Trans();
		trans.thisyear();
		trans.gettingBus();
		trans.gettingMetro();
		trans.covid19();
		trans.writingFileMethod();
		


	}

	private void thisyear() {
		for (int  i = 1; i < 13; i ++) {
			readFileName = "/Users/aries/Documents/DataFolder/01/BUS_STATION_BOARDING_MONTH_" + i + ".csv";
			try {
				csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName), "CP949"));
				String[] nextLine;
				if ((nextLine = csvReader.readNext()) == null) {
					System.out.println("Empty File");
				}
				while ((nextLine = csvReader.readNext()) != null) {
					date.add(nextLine[0]);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		dateSort = new ArrayList(date);
		Collections.sort(dateSort);
	}

	private void gettingBus() {
		for (int i = 1; i < 13; i ++) {
			readFileName = "/Users/aries/Documents/DataFolder/01/BUS_STATION_BOARDING_MONTH_" + i + ".csv";
			try {
				String[] nextLine;
				if ((nextLine = csvReader.readNext()) == null) {
					System.out.println("Empty File");
				}
				while ((nextLine = csvReader.readNext()) != null) {
					for (int j = 0; j < dateSort.size(); j ++) {
						if (nextLine[0].equals(dateSort.get(j))) {
							try {
								onBus[j] += Integer.parseInt(nextLine[5]);
								offBus[j] += Integer.parseInt(nextLine[6]);
							} catch (NumberFormatException e) {
								onBus[j] += Integer.parseInt(nextLine[6]);
								offBus[j] += Integer.parseInt(nextLine[7]);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void gettingMetro() throws CsvValidationException, IOException {
		readFileName = "/Users/aries/Documents/DataFolder/01/metro.csv";
		csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName), "CP949"));
		String[] nextLine;
		if ((nextLine = csvReader.readNext()) == null) {
			System.out.println("Empty File");
		}
		while ((nextLine = csvReader.readNext()) != null) {
			for (int i = 0; i < dateSort.size(); i ++) {
				String modDate = nextLine[0].substring(0, 4) + nextLine[0].substring(5, 7) + nextLine[0].substring(8, 10);
				int sum = 0;
				for (int j = 5; j < 25; j ++) {
					sum += Integer.parseInt(nextLine[j]);
				}
				if (modDate.equals(dateSort.get(i))) {
					onMetro[i] += sum;
				} else {
					offMetro[i] += sum;
				}
			}
		}
	}
	
	private void covid19() throws CsvValidationException, IOException {
		readFileName = "/Users/aries/Documents/DataFolder/01/covid19.csv";
		csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName), "CP949"));
		String nextLines[];
		if ((nextLines = csvReader.readNext()) == null) {
			System.out.println("Empty File");
		}
		while ((nextLines = csvReader.readNext()) != null) {
			for (int i = 0; i < dateSort.size(); i ++) {
				String modDate = nextLines[1].substring(0, 4) + nextLines[1].substring(5, 7) + nextLines[1].substring(8, 10);
				if (modDate.equals(dateSort.get(i))) {
					covid19Cnt[i] += 1;
				}
			}
		}
	}
	
	private void writingFileMethod() {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFileName, true), "CP949"));
			String header = "일자," + "지하철승차," + "지하철하차," + "버스승차," + "버스하차," + "서울시월별코로나확진자";
			bw.write(header);
			bw.newLine();
			for (int i = 0; i < dateSort.size(); i ++) {
				String context = dateSort.get(i) + "," + onMetro[i] + "," + offMetro[i] + "," + onBus[i] + "," + offBus[i] + "," + covid19Cnt[i];
				bw.write(context);
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
}
