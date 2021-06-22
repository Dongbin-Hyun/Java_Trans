package com.dongbin.Day1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class PublicTrans {
	//String readFileName = "C:\\Users\\kopo44\\Documents\\DataAna\\data1.csv";
	String readFileName;//bus
	String readFileName2 = "C:\\Users\\kopo44\\Documents\\DataAna\\data0.csv";//metro
	String writeFileName = "C:\\Users\\kopo44\\Documents\\DataAna\\rawData4.csv";
	CSVReader csvReader;
	CSVWriter cw;
	HashSet<String> date = new HashSet<String>();
	ArrayList<String> dateArray = new ArrayList<String>();
	//HashSet<String> date2 = new HashSet<String>();
	//ArrayList<String> dateArray2 = new ArrayList<String>();
	int[] onBusSum = new int[366];
	int[] offBusSum = new int[366];
	int[] onMetroSum = new int[366];
	int[] offMetroSum = new int[366];

	public static void main(String[] args) throws IOException {
		PublicTrans publicTrans = new PublicTrans();
		for (int i = 1; i < 13; i++) {
			String readFileName = "C:\\Users\\kopo44\\Documents\\DataAna\\data" + i + ".csv";
			publicTrans.busDate(readFileName);
			publicTrans.gettingBus(readFileName);
		}
		
		
		//publicTrans.metroDate();
		//publicTrans.gettingMetro();

	}

	public void fileRead() throws IOException {

	}

	private void busDate(String readFileName) {
		try {
			csvReader = new CSVReader(new FileReader(readFileName));
			String[] nextLine;
			if ((nextLine  = csvReader.readNext()) == null) {
				System.out.println("빈파일 입니다.");
			} else {
				while ((nextLine = csvReader.readNext()) != null) {
					date.add(nextLine[0]);
				}
			}
			csvReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void gettingBus(String readFileName) {
		try {
			csvReader = new CSVReader(new FileReader(readFileName));
			String[] nextLine;
			if ((nextLine = csvReader.readNext()) == null) {
				System.out.println("빈 파일입니다.");
			} else {
				while ((nextLine = csvReader.readNext()) != null) {
					for (int j = 0; j < dateArray.size(); j++) {
						if (nextLine[0].equals(dateArray.get(j))) {
							try {
								onBusSum[j] += Integer.parseInt(nextLine[5]);
								offBusSum[j] += Integer.parseInt(nextLine[6]);
							} catch (NumberFormatException e) {
								onBusSum[j] += Integer.parseInt(nextLine[6]);
								offBusSum[j] += Integer.parseInt(nextLine[7]);
							}
						}
					}
				}
			}
			for (int i = 0; i < dateArray.size(); i ++) {
				System.out.println(dateArray.get(i) + " : " + onBusSum[i] + "," + offBusSum[i]);
			}
			csvReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*private void metroDate() {
		try {
			csvReader = new CSVReader(new FileReader(readFileName2));
			String[] nextLine;
			if ((nextLine  = csvReader.readNext()) == null) {
				System.out.println("빈파일 입니다.");
			} else {
				while ((nextLine = csvReader.readNext()) != null) {
					date.add(nextLine[0]);
				}
			}
			Iterator<String> iter2 = date.iterator();
			while (iter2.hasNext()) {
				dateArray.add(iter2.next());
			}
			Collections.sort(dateArray);
			//System.out.println(dateArray.size());
			csvReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	private void gettingMetro() {
		try {
			csvReader = new CSVReader(new FileReader(readFileName2));
			String[] nextLine;
			if ((nextLine = csvReader.readNext()) == null) {
				System.out.println("빈 파일입니다.");
			}

			while ((nextLine = csvReader.readNext()) != null) {
				for (int j = 0; j < dateArray.size(); j++) {
					if (nextLine[0].equals(dateArray.get(j))) {
						try {
							if (nextLine[4].equals("승차")) {
								for (int k = 5; k < 25; k ++) {
									onMetroSum[k] += Integer.parseInt(nextLine[k]);
								}
							}
							if (nextLine[4].equals("하차")) {
								for (int l = 5; l < 25; l ++) {
									offMetroSum[l] += Integer.parseInt(nextLine[l]);
								}
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

			for (int i = 0; i < dateArray.size(); i ++) {
				System.out.println(dateArray.get(i) + " : " + onMetroSum[i] + "," + offMetroSum[i]);
			}
			csvReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}









