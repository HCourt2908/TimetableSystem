
package com.companyname.school_timetable_system;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.AbstractAction;

//import the code libraries needed

public class Staff_Main {
    
    
    //initialising certain variables
    private JFrame frame;
    private String forename;
    private String surname;
    private ArrayList<String> userInfo;
    
    //importing logo image
    static ImageIcon logo = new ImageIcon("logo.png");
    
    public Staff_Main(ArrayList<String> userInfo) {
        
        //copying name data and capitalising the first letter
        forename = userInfo.get(1);
        forename = forename.substring(0,1).toUpperCase() + forename.substring(1).toLowerCase();
        surname = userInfo.get(2);
        surname = surname.substring(0,1).toUpperCase() + surname.substring(1).toLowerCase();
        this.userInfo = new ArrayList<>();
        this.userInfo = userInfo;    
    }
    
    public void createWindow() {
        
        //setting up window
        frame = new JFrame("Main Menu");
        
        //ensuring program ends when window is terminated
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //setting default frame size
        frame.setPreferredSize(new Dimension(700,500));
        
        //changing background colour of frame
        frame.getContentPane().setBackground(new java.awt.Color(2,7,93));
        
        //set layout
        frame.setLayout(null);
        
        //creating welcome label
        JLabel welcomeLabel = new JLabel ("Welcome " + forename + " " + surname, SwingConstants.CENTER);
        welcomeLabel.setBounds(25,25,150,25);
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setBackground(Color.WHITE);
        welcomeLabel.setOpaque(true);
        
        //creating timetable label
        JLabel timeLabel = new JLabel ("Click here to view timetable", SwingConstants.CENTER);
        timeLabel.setForeground(Color.BLACK);
        timeLabel.setBackground(Color.WHITE);
        timeLabel.setOpaque(true);
        timeLabel.setBounds(375,25,300,200);
        
        //creating hidden timetable button
        JButton timeButton = new JButton (new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e) {
                Staff_Timetable staffTimetable = new Staff_Timetable(userInfo);
                frame.setVisible(false);
                staffTimetable.createWindow();
            }
        });
        timeButton.setBounds(375,25,300,200);
        timeButton.setOpaque(false);
        
        
        //creating class change label
        JLabel changeLabel = new JLabel ("Click here to change a classroom", SwingConstants.CENTER);
        changeLabel.setBounds(375,240,300,200);
        changeLabel.setForeground(Color.BLACK);
        changeLabel.setBackground(Color.WHITE);
        changeLabel.setOpaque(true);
        
        //creating hidden class change button
        JButton changeButton = new JButton ( new AbstractAction() {
            @Override
            public void actionPerformed (ActionEvent e) {
                Staff_RoomChange staffRoomChange = new Staff_RoomChange(userInfo,false);
                frame.setVisible(false);
                staffRoomChange.createWindow();
            }
        });
        changeButton.setBounds(375,240,300,200);
        changeButton.setOpaque(false);
        
        //creating back label
        JLabel backLabel = new JLabel ("Back", SwingConstants.CENTER);
        backLabel.setBounds(25,150,150,25);
        backLabel.setForeground(Color.BLACK);
        backLabel.setBackground(Color.WHITE);
        backLabel.setOpaque(true);
        
        //creating hidden back button
        JButton backButton = new JButton( new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e) {
                frame.setVisible(false);
                Login_Window login = new Login_Window();
                login.createWindow();
            }
        });
        backButton.setBounds(25,150,150,25);
        backButton.setOpaque(false);
        
        //setting a custom window icon
        ImageIcon img = new ImageIcon("icon.png");
        frame.setIconImage(img.getImage());
        
        //importing the school logo
        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(25,300,150,135);
        logoLabel.setIcon(logo);
        logoLabel.setOpaque(true);
        
        //adding all the parts to the window
        frame.add(welcomeLabel);
        frame.add(logoLabel);
        frame.add(timeLabel);
        frame.add(changeLabel);
        frame.add(timeButton);
        frame.add(changeButton);
        frame.add(backLabel);
        frame.add(backButton);
        
        //sets window size
        frame.pack();
        
        //displaying the window
        frame.setLocationRelativeTo(null);
        
        //showing window
        frame.setVisible(true);
    }
    
    
}
