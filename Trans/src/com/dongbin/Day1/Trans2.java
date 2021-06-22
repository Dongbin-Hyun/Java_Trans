package com.dongbin.Day1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class Trans2 {
  static String writeFileName = "C:\\Users\\kopo44\\Documents\\DataAna\\DataFolder\\01\\dongbin.csv";
  static CSVReader csvReader;
  static File file = new File(writeFileName);
  static HashSet<String> date = new HashSet<String>();
  static List datesort;
  static int[] onBus = new int[366];
  static int[] offBus = new int[366];
  static int[] onMetro = new int[366];
  static int[] offMetro = new int[366];
  static int[] covidCnt = new int[366];
  
  public static void main(String[] args) throws CsvValidationException, IOException {
    hashdate();
    setBusData();
    setMetroData();
    setMetroData();
    setCovidData();
    dataWrite();
  }
  
  
  
  public static void hashdate() {
    for (int i = 1; i < 13; i++) {
      String readFileName = "C:\\Users\\kopo44\\Documents\\DataAna\\DataFolder\\01\\BUS_STATION_BOARDING_MONTH_" + i + ".csv";
      
      try {
        csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName),"CP949"));
        String[] nextLine;
        if ((nextLine = csvReader.readNext()) == null) {}
        
        while ((nextLine = csvReader.readNext()) != null) {
          date.add(nextLine[0]);
        }
        
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    datesort = new ArrayList(date);
    Collections.sort(datesort);
  }
  
  public static void setBusData() {
    for (int i = 1; i < 13; i++) {
      String readFileName = "C:\\Users\\kopo44\\Documents\\DataAna\\DataFolder\\01\\BUS_STATION_BOARDING_MONTH_" + i + ".csv";
      
      try {
        csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName),"CP949"));
        String[] nextLine;
        if ((nextLine = csvReader.readNext()) == null) {}
        
        while ((nextLine = csvReader.readNext()) != null) {
          for (int j = 0; j < datesort.size(); j++) {
            if (nextLine[0].equals(datesort.get(j))) {
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
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  public static void setMetroData() throws CsvValidationException, IOException {
    
    String readFileName = "C:\\Users\\kopo44\\Documents\\DataAna\\DataFolder\\01\\metro.csv";
    csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName),"CP949"));
    String[] nextLine;
    if ((nextLine = csvReader.readNext()) == null) {}
    
    while ((nextLine = csvReader.readNext()) != null) {
      for (int j = 0; j < datesort.size(); j++) {
        String barDate = nextLine[0].substring(0, 4) + nextLine[0].substring(5, 7) + nextLine[0].substring(8, 10);
        int sum = 0;
        for (int i = 5; i < 25; i++) {
          sum += Integer.parseInt(nextLine[i]);
        }
        if (barDate.equals(datesort.get(j))) {
          if (nextLine[4].equals("승차")) {
            onMetro[j] += sum;
          } else if (nextLine[4].equals("하차")) {
            offMetro[j] += sum;
          }
        }
      }
    }
  }
  
  public static void setCovidData() throws CsvValidationException, IOException {
    String readCovidFileName = "C:\\Users\\kopo44\\Documents\\DataAna\\DataFolder\\01\\covid19.csv";
    csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readCovidFileName),"CP949"));
    String[] nextLines;
    if ((nextLines = csvReader.readNext()) == null) {}
    while ((nextLines = csvReader.readNext()) != null) {
      
      for (int j = 0; j < datesort.size(); j++) {
        String barDate = nextLines[1].substring(0, 4) + nextLines[1].substring(5, 7) + nextLines[1].substring(8, 10);
        if (datesort.get(j).equals(barDate)) {
          covidCnt[j] += 1;
        }
      }
    }
  }
  
  public static void dataWrite() {
    try {
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFileName, true), "CP949"));
      String head = "일자," + "지하철승차," + "지하철하차," + "버스승차," + "버스하차," + "서울 일별 코로나 확진자";
      bw.write(head);
      bw.newLine();
      for (int q = 0; q < datesort.size(); q++) {
        String context = datesort.get(q) + "," + onMetro[q] + "," + offMetro[q] + "," + onBus[q] + "," + offBus[q] + "," + covidCnt[q];
        bw.write(context);
        bw.newLine();
      }
      bw.flush();
      bw.close();
      System.out.println("파일쓰기 완료");
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}