
package com.companyname.school_timetable_system;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
//import needed libraries
public class Classroom_Filter {
    
    private int week;
    private int day;
    private int period;
    private String chosenPeriod;
    
    public Classroom_Filter(int week, int day, int period) {
        this.week = week;
        this.day = day;
        this.period = period;
        try (Scanner scanner = new Scanner(new File("room_timetables.csv"))) {
            //iterates until no records remain
            while(scanner.hasNextLine()) {
                
                String oneRecord = scanner.nextLine();
                String[] parts = oneRecord.split(",");
                
                //if the week in the record matches the week in the variable
                if (Integer.valueOf(parts[0].substring(1,2)) == week) {
                    //if the day in the record matches the day in the variable
                    if (Integer.valueOf(parts[1].substring(1,2)) == day) {
                        //if the period in the record matches the period in the variable
                        if (Integer.valueOf(parts[2].substring(1,2)) == period) {
                            chosenPeriod = oneRecord;
                            
                            break;
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    public ArrayList<String> filter() {
        String[] parts = chosenPeriod.split(",");
        
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].substring(1,parts[i].length() - 1);
        }
        
        ArrayList<String> freeRooms = new ArrayList<>();
        
        //will need to check from index 3 - 12 (inclusive)
        //index 3 matches to room 1 --> subtract 2 from value to get free room no. 
        for (int i = 3; i <= 12; i++) {
            if (Boolean.valueOf(parts[i]) == false) {
                freeRooms.add(String.valueOf(i-2));
        }
        }
        
        return freeRooms;
    }
    
    public ArrayList<String> freeRoomFilter() {
        
        String[] parts = chosenPeriod.split(",");
        
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].substring(1, parts[i].length()-1);
        }
        ArrayList<String> freeRooms = new ArrayList<>();
        
        //have to check from index 3 - 12
        for (int i = 3; i <= 12; i++) {
            if (Boolean.valueOf(parts[i]) == false) {
                freeRooms.add(String.valueOf(i-2));
            }
        }
        return freeRooms;
    }
    
}
