
package com.companyname.school_timetable_system;

import java.awt.Dimension;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

//imported necessary components

public class Timetable_Filter {
    
    private int week;
    private int day;
    private int period;
    private String forename;
    private String surname;
    private String filename;
    private ArrayList<String> filteredDay;
    private String filteredPeriod;
    private JTable timetable;
    
    //initialised needed variables
    
    public Timetable_Filter(int week, int day, String forename, String surname) {
        
        //takes the chosen week and day as filtering parameters
        //takes forename and surname as parameters to calculate the filename
        this.week = week;
        this.day = day;
        this.forename = forename;
        this.surname = surname;
        filename = forename + surname + ".csv";
        this.filteredDay = new ArrayList<>();
        
        //creates variables from constructor parameters
        
        try (Scanner scanner = new Scanner(new File(filename))) {
            
            //iterates until no records remain
            while(scanner.hasNextLine()) {
                
            
                
                String oneRecord = scanner.nextLine();
                String[] parts = oneRecord.split(",");

                //if the week in the record matches the week in the variable
                if (Integer.valueOf(parts[0].substring(1,parts[0].length()-1)) == week) {
                    //if the day in the record matches the day in the variable
                    if (Integer.valueOf(parts[1].substring(1,parts[1].length()-1)) == day) {
                       
                        filteredDay.add(oneRecord);
                    }
                }
            
        }   
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    public Timetable_Filter(int week, int day, int period, String forename, String surname) {
        //takes the chosen week, day **AND PERIOD** as filtering parameters
        //takes forename and surname as parameters to calculate the filename
        this.week = week;
        this.day = day;
        this.period = period;
        filename = forename + surname + ".csv";
        
        //creates variables from constructor parameters
        
        try (Scanner scanner = new Scanner(new File(filename))) {
            //iterates until no records remain
            while(scanner.hasNextLine()) {
                
                String oneRecord = scanner.nextLine();
                String[] parts = oneRecord.split(",");
                
                //if the week in the record matches the week in the variable
                if (Integer.valueOf(parts[0].substring(1,2)) == week) {
                    //if the day in the record matches the day in the variable
                    if (Integer.valueOf(parts[1].substring(1,2)) == day) {
                        //if the period in the record matches the day in the variable
                        if (Integer.valueOf(parts[2].substring(1,2)) == period) {
                            filteredPeriod = oneRecord;
                        }
                    }
                }
                
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    public String[][] tableFormat() {
        
        //creates 5 arrays, each containing information for each period of the day

        String[] lesson1 = filteredDay.get(0).split(",");
        for (int i = 0; i < lesson1.length; i++) {
            lesson1[i] = lesson1[i].substring(1,lesson1[i].length()-1);
        }
        if (lesson1[3].equals("free")) {
            lesson1[4] = "N/A";
            lesson1[5] = "N/A";
        }
        String[] lesson2 = filteredDay.get(1).split(",");
        for (int i = 0; i < lesson2.length; i++) {
            lesson2[i] = lesson2[i].substring(1,lesson2[i].length()-1);
        }
        if (lesson2[3].equals("free")) {
            lesson2[4] = "N/A";
            lesson2[5] = "N/A";
        }
        String[] lesson3 = filteredDay.get(2).split(",");
        for (int i = 0; i < lesson3.length; i++) {
            lesson3[i] = lesson3[i].substring(1,lesson3[i].length()-1);
        }
        if (lesson3[3].equals("free")) {
            lesson3[4] = "N/A";
            lesson3[5] = "N/A";
        }
        String[] lesson4 = filteredDay.get(3).split(",");
        for (int i = 0; i < lesson4.length; i++) {
            lesson4[i] = lesson4[i].substring(1,lesson4[i].length()-1);
        }
        if (lesson4[3].equals("free")) {
            lesson4[4] = "N/A";
            lesson4[5] = "N/A";
        }
        String[] lesson5 = filteredDay.get(4).split(",");
        for (int i = 0; i < lesson5.length; i++) {
            lesson5[i] = lesson5[i].substring(1,lesson5[i].length()-1);
        }
        if (lesson5[3].equals("free")) {
            lesson5[4] = "N/A";
            lesson5[5] = "N/A";
        }
        
        //creates the 2D array that contains all the information to be displayed on the timetable window
        
        String[][] days = {
            {"8:50-9:50",lesson1[3],lesson1[4],lesson1[5]},
            {"9:50-10:50",lesson2[3],lesson2[4],lesson2[5]},
            {"10:50-11:10","BREAK","BREAK","BREAK"},
            {"11:10-12:10",lesson3[3],lesson3[4],lesson3[5]},
            {"12:10-13:10",lesson4[3],lesson4[4],lesson4[5]},
            {"13:10-13:50","LUNCH","LUNCH","LUNCH"},
            {"13:50-14:50",lesson5[3],lesson5[4],lesson5[5]},   
        };
        
        //returns this array
        return days;
        
        
    }
    
    public String getPeriod() {
        return filteredPeriod;
    }
    
    
    
}
