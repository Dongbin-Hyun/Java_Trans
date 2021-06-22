package com.dongbin.Day1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Test2 {
	String readFileName = "C:\\Users\\kopo44\\Documents\\DataAna\\data1.csv";
	String readFileName2 = "C:\\Users\\kopo44\\Documents\\DataAna\\data0.csv";
	String writeFileName = "C:\\Users\\kopo44\\Documents\\DataAna";
	CSVReader csvReader;
	CSVWriter cw;
	HashSet<String> date = new HashSet<String>();
	ArrayList<String> dateArray = new ArrayList<String>();
	HashSet<String> date2 = new HashSet<String>();
	ArrayList<String> dateArray2 = new ArrayList<String>();
	int[] onBusSum = new int[31];
	int[] offBusSum = new int[31];
	int[] onMetroSum = new int[366];
	int[] offMetroSum = new int[366];

	public static void main(String[] args) throws IOException {
		Test2 test2 = new Test2();
		test2.busDate();
		//test2.onOffBus();
		test2.metroDate();

	}

	//   private void fileRead() throws IOException {
	//      Files.walk(Paths.get("C:\\Users\\정현정\\Downloads\\download_2021-06-21_10-56-20\\bus")).forEach(filePath -> {
	//          if (Files.isRegularFile(filePath)) {
	//              System.out.println(filePath);
	//              busDate(filePath)
	//          }
	//      });
	//   }

	private void busDate() {

		try {
			csvReader = new CSVReader(new FileReader(readFileName));
			String[] nextLine;

			if ((nextLine  = csvReader.readNext()) == null) {
				System.out.println("빈파일 입니다.");
			} else {
				while ((nextLine = csvReader.readNext()) != null) {
					date.add(nextLine[0]);
				}
				Iterator<String> iter = date.iterator();
				while (iter.hasNext()) {
					dateArray.add(iter.next());
				}

				Collections.sort(dateArray);

			}
			csvReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void onOffBus() {
		try {
			csvReader = new CSVReader(new FileReader(readFileName));
			String[] nextLine;

			if ((nextLine  = csvReader.readNext()) == null) {
				System.out.println("빈파일 입니다.");
			} else {
				while ((nextLine = csvReader.readNext()) != null) {
					for (int i = 0 ; i < dateArray.size(); i++) {
						if (nextLine[0].equals(dateArray.get(i))) {
							onBusSum[i] += Integer.parseInt(nextLine[5]);
							offBusSum[i] += Integer.parseInt(nextLine[6]);
						}
					}

				}
				for (int i = 0; i < onBusSum.length; i ++) {
					System.out.println(dateArray.get(i) + " : " + onBusSum[i] + "," + offBusSum[i]);
				}

			}
			csvReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	private void metroDate() {
		try {
			csvReader = new CSVReader(new FileReader(readFileName2));
			String[] nextLine;

			if ((nextLine  = csvReader.readNext()) == null) {
				System.out.println("빈파일 입니다.");
			} else {
				while ((nextLine = csvReader.readNext()) != null) {
					date2.add(nextLine[0]);
				}
				Iterator<String> iter2 = date2.iterator();
				while (iter2.hasNext()) {
					dateArray.add(iter2.next());
				}
				Collections.sort(dateArray2);

			}
			System.out.println(dateArray2.size());
			csvReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}   

	}



}