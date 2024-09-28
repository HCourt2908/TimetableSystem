
package com.companyname.school_timetable_system;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.wildbit.java.postmark.Postmark;
import com.wildbit.java.postmark.client.ApiClient;
import com.wildbit.java.postmark.client.data.model.message.Message;
import com.wildbit.java.postmark.client.data.model.message.MessageResponse;
import com.wildbit.java.postmark.client.exception.PostmarkException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


//import all the necessary libraries

public class Staff_ChangeWindow {
    
    private JFrame frame;
    private String[] periodParts;
    private ArrayList<String> userInfo;
    //initialising certain variables
    
    //importing logo image
    static ImageIcon logo = new ImageIcon("logo.png");
    
    public Staff_ChangeWindow(String[] periodParts,ArrayList<String> userInfo) {
        this.periodParts = periodParts;
        this.userInfo = new ArrayList<>();
        this.userInfo = userInfo;
        
    }
    
    public void createWindow() {
        
        //setting up window
        frame = new JFrame("Change Rooms");
        
        //ensuring program doesnt end when window is terminated
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //setting default frame size
        frame.setPreferredSize(new Dimension(700,500));
        
        //changing background colour of frame
        frame.getContentPane().setBackground(new java.awt.Color(2,7,93));
        
        //set layout
        frame.setLayout(null);
        
        //creating the class change window label
        JLabel changeWindow = new JLabel("Class Change Window");
        changeWindow.setBounds(25,25,150,25);
        changeWindow.setForeground(Color.BLACK);
        changeWindow.setBackground(Color.WHITE);
        changeWindow.setOpaque(true);
        
        //creating the lesson selected label
        JLabel selectedLabel = new JLabel("<html>You have selected: ", SwingConstants.CENTER);
        selectedLabel.setText(selectedLabel.getText() + "<br/>Week: " + periodParts[0]);
        selectedLabel.setText(selectedLabel.getText() + "<br/>Day: " + periodParts[1]);
        selectedLabel.setText(selectedLabel.getText() + "<br/>Period: " + periodParts[2]);
        selectedLabel.setText(selectedLabel.getText() + "<br/>Subject: " + periodParts[3]);
        selectedLabel.setText(selectedLabel.getText() + "<br/>Current Room: " + periodParts[4]);
        selectedLabel.setText(selectedLabel.getText() + "<br/>Class Code: " + periodParts[5]);
        selectedLabel.setBounds(225,25,200,200);
        selectedLabel.setBackground(Color.WHITE);
        selectedLabel.setForeground(Color.BLACK);
        selectedLabel.setOpaque(true);
        
        //creating the free rooms label
        JLabel freeLabel = new JLabel("<html>The Free Rooms Are: ", SwingConstants.CENTER);
        Classroom_Filter classroomFilter = new Classroom_Filter(
                Integer.valueOf(periodParts[0]),Integer.valueOf(periodParts[1]),Integer.valueOf(periodParts[2]));
        ArrayList<String> freeRooms = classroomFilter.freeRoomFilter();
        for (int i = 0; i < freeRooms.size(); i++) {
            freeLabel.setText(freeLabel.getText() + "<br/>Room " + freeRooms.get(i));
        }
        freeLabel.setBounds(225,250,200,200);
        freeLabel.setBackground(Color.WHITE);
        freeLabel.setForeground(Color.BLACK);
        freeLabel.setOpaque(true);
        
        //formatting free rooms for use in a dropdown list
        ArrayList<String> roomChoiceList = new ArrayList<>();
        roomChoiceList.add("Select A Room:");
        for(int i = 0; i < freeRooms.size(); i++) {
            roomChoiceList.add("Room " + freeRooms.get(i));
        }
        //converting list to array
        String[] roomChoiceArray = new String[roomChoiceList.size()];
        roomChoiceList.toArray(roomChoiceArray);
        
        //creating the dropdown list
        JComboBox<String> roomChoice = new JComboBox<>(roomChoiceArray);
        roomChoice.setBounds(475,100,150,50);
        roomChoice.setBackground(Color.WHITE);
        roomChoice.setOpaque(true);
        
        //creating the permanent change option label
        JLabel permanentLabel = new JLabel("Permanent Change?", SwingConstants.CENTER);
        permanentLabel.setBounds(475,175,150,50);
        permanentLabel.setBackground(Color.WHITE);
        permanentLabel.setForeground(Color.BLACK);
        permanentLabel.setOpaque(true);
        
        //creating the yes checkbox
        JCheckBox yesBox = new JCheckBox("Yes");
        yesBox.setBounds(475,250,75,50);
        
        //creating the no checkbox
        JCheckBox noBox = new JCheckBox("No");
        noBox.setBounds(550,250,75,50);
        
        //creating the submit button
        JButton submitButton = new JButton( new AbstractAction("Submit") {
            @Override
            public void actionPerformed (ActionEvent e) {
                //get the number of the room chosen
                if(roomChoice.getSelectedIndex() == 0) {
                } else {
                    String selectedRoom = roomChoice.getItemAt(roomChoice.getSelectedIndex());
                    int chosenNumber = Integer.valueOf(selectedRoom.substring(5));
                    
                    
                    if (noBox.isSelected() && !yesBox.isSelected()) {
                        //if not permanent change, only send email
                        
                        //sending email
                        
                        ApiClient client = Postmark.getApiClient("b9ee62f9-92e0-4ce4-a5b0-09cca21cc0df");
                        Message message = new Message("16.h.court@oldfieldschool.com", "henrycourt7@icloud.com", "Non-permanent Room Change",
                                "The lesson in room " + periodParts[4] + " on week " + periodParts[0] + ", day " + periodParts[1] + ", period " + periodParts[2] + " has been moved to room " + String.valueOf(chosenNumber));
                        message.setMessageStream("coursework_room_changes");
                        try {
                            MessageResponse response = client.deliverMessage(message);
                        } catch (PostmarkException ex) {
                            Logger.getLogger(Staff_ChangeWindow.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(Staff_ChangeWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else if (yesBox.isSelected() && !noBox.isSelected()) {
                        //if permanent change, edit csv and send email
                        
                        //sending email
                        ApiClient client = Postmark.getApiClient("b9ee62f9-92e0-4ce4-a5b0-09cca21cc0df");
                        Message message = new Message("16.h.court@oldfieldschool.com", "henrycourt7@icloud.com", "Permanent Room Change", 
                                "The lesson in room " + periodParts[4] + " on week " + periodParts[0] + ", day " + periodParts[1] + ", period " + periodParts[2] + " has been moved to room " + String.valueOf(chosenNumber));
                        message.setMessageStream("coursework_room_changes");
                        try {
                            MessageResponse response = client.deliverMessage(message);
                        } catch (PostmarkException ex) {
                            Logger.getLogger(Staff_ChangeWindow.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(Staff_ChangeWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        try {
                            //editing csv
                            updateFiles(chosenNumber);
                        } catch (IOException ex) {
                            Logger.getLogger(Staff_ChangeWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        frame.setVisible(false);
                        changeCompleted();
                        
                        
                        
                    }
                }
                
        
            }
        });
        submitButton.setBounds(475,350,200,50);
        submitButton.setOpaque(true);
        
        //creating back label
        JLabel backLabel = new JLabel ("Back", SwingConstants.CENTER);
        backLabel.setBounds(25,150,150,25);
        backLabel.setForeground(Color.BLACK);
        backLabel.setBackground(Color.WHITE);
        backLabel.setOpaque(true);
        
        //creating back button
        JButton backButton = new JButton( new AbstractAction("Back") {
            @Override
            public void actionPerformed( ActionEvent e) {
                frame.setVisible(false);
                Staff_RoomChange staffRoomChange = new Staff_RoomChange(userInfo,false);
                staffRoomChange.createWindow();
            }
        });
        backButton.setBounds(25,150,150,25);
        backButton.setOpaque(true);
        
        //setting a custom window icon
        ImageIcon img = new ImageIcon("icon.png");
        frame.setIconImage(img.getImage());
        
        //importing the school logo
        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(25,300,150,135);
        logoLabel.setIcon(logo);
        logoLabel.setOpaque(true);
        
        //adding all the parts to the window
        frame.add(logoLabel);
        frame.add(changeWindow);
        frame.add(selectedLabel);
        frame.add(freeLabel);
        frame.add(roomChoice);
        frame.add(permanentLabel);
        frame.add(yesBox);
        frame.add(noBox);
        frame.add(submitButton);
        frame.add(backLabel);
        frame.add(backButton);
        
        //sets window size
        frame.pack();
        
        //displaying window
        frame.setLocationRelativeTo(null);
        
        //showing window
        frame.setVisible(true);
    }
    
    public void updateFiles(int chosenNumber) throws IOException{
        
        
        String filename = userInfo.get(1) + userInfo.get(2) + ".csv";
        File inputFile = new File(filename);
        List<String[]> csvStaffBody;
        
        //read from staff timetable csv

        CSVReader staffReader = new CSVReader(new FileReader(inputFile), ',') ;
        csvStaffBody = staffReader.readAll();
        for (int i = 0; i <csvStaffBody.size(); i++) {
            String[] strArray = csvStaffBody.get(i);
            if (strArray[0].equalsIgnoreCase(String.valueOf(periodParts[0]))) {
                if (strArray[1].equalsIgnoreCase(String.valueOf(periodParts[1]))) {
                    if (strArray[2].equalsIgnoreCase(String.valueOf(periodParts[2]))) {
                        if (strArray[4].equalsIgnoreCase(String.valueOf(periodParts[4]))) {
                            csvStaffBody.get(i)[4] = String.valueOf(chosenNumber);
                        }
                    }
                }
            }

        }
        staffReader.close();
        
        //write to staff timetable csv
        CSVWriter staffWriter = new CSVWriter(new FileWriter(inputFile), ',');
        staffWriter.writeAll(csvStaffBody);
        staffWriter.flush();
        staffWriter.close();
        
        
        List<String[]> csvRoomBody;
        //read from room timetable csv
        CSVReader roomReader = new CSVReader(new FileReader("room_timetables.csv"), ',') ;
        csvRoomBody = roomReader.readAll();
        for(int i = 0; i<csvRoomBody.size();i++) {
            String[] strArray = csvRoomBody.get(i);
            if(strArray[0].equalsIgnoreCase(String.valueOf(periodParts[0]))) {
                if(strArray[1].equalsIgnoreCase(String.valueOf(periodParts[1]))) {
                    if(strArray[2].equalsIgnoreCase(String.valueOf(periodParts[2]))) {
                        csvRoomBody.get(i)[chosenNumber+2] = "TRUE";
                        csvRoomBody.get(i)[Integer.valueOf(periodParts[4])+2] = "FALSE";
                    }
                }
            }
        }
        roomReader.close();
        
        //write to room timetable csv
        CSVWriter roomWriter = new CSVWriter(new FileWriter("room_timetables.csv"), ',');
        roomWriter.writeAll(csvRoomBody);
        roomWriter.flush();
        roomWriter.close();
        
        
        
    }
    
    public void changeCompleted() {
        Staff_RoomChange staffRoomChange = new Staff_RoomChange(userInfo,true);
        staffRoomChange.createWindow();
        frame.setVisible(false);
    }
    
}
