
package com.companyname.school_timetable_system;



import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.ArrayList;

//import all the necessary libraries

public class Staff_RoomChange {
    
    private ArrayList<String> userInfo;
    private JFrame frame;
    private String weekInput;
    private String dayInput;
    private String periodInput;
    private Boolean changeComplete;
    //initialising certain variables
    
    //importing logo image
    static ImageIcon logo = new ImageIcon("logo.png");
    
    public Staff_RoomChange(ArrayList<String> userInfo, Boolean changeComplete) {
        this.userInfo = new ArrayList<>();
        this.userInfo = userInfo;
        this.changeComplete = changeComplete;
    }
    
    public void createWindow() {
        
        //setting up window
        frame = new JFrame("Room Change");
        
        //ensuring program ends when window is terminated
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //setting default frame size
        frame.setPreferredSize(new Dimension(700,500));
        
        //changing background colour of frame
        frame.getContentPane().setBackground(new java.awt.Color(2,7,93));
        
        //set layout
        frame.setLayout(null);
        
        //creating room change label
        JLabel changeLabel = new JLabel ("Room Change", SwingConstants.CENTER);
        changeLabel.setBounds(25,25,150,25);
        changeLabel.setForeground(Color.BLACK);
        changeLabel.setBackground(Color.WHITE);
        changeLabel.setOpaque(true);
        
        //creating the enter week label
        JLabel weekLabel = new JLabel ("Enter Week (1-2)", SwingConstants.CENTER);
        weekLabel.setBounds(450,25,200,50);
        weekLabel.setForeground(Color.BLACK);
        weekLabel.setBackground(Color.WHITE);
        weekLabel.setOpaque(true);
        
        //creating the enter week input box
        JTextField weekField = new JTextField();
        weekField.setBounds(450,75,200,50);
        
        //creating the enter day label
        JLabel dayLabel = new JLabel ("Enter Day (1-5)", SwingConstants.CENTER);
        dayLabel.setBounds(450,125,200,50);
        dayLabel.setForeground(Color.BLACK);
        dayLabel.setBackground(Color.WHITE);
        dayLabel.setOpaque(true);
        
        //creating the enter day input box
        JTextField dayField = new JTextField();
        dayField.setBounds(450,175,200,50);
        
        //creating the enter period label
        JLabel periodLabel = new JLabel ("Enter Period (1-5)", SwingConstants.CENTER);
        periodLabel.setBounds(450,225,200,50);
        periodLabel.setForeground(Color.BLACK);
        periodLabel.setBackground(Color.WHITE);
        periodLabel.setOpaque(true);
        
        //creating the enter period input box
        JTextField periodField = new JTextField();
        periodField.setBounds(450,275,200,50);
        
        //creating the change complete label
        JLabel changeCompleteLabel = new JLabel ("Change Complete!", SwingConstants.CENTER);
        changeCompleteLabel.setBounds(212,325,200,50);
        changeCompleteLabel.setForeground(Color.BLACK);
        changeCompleteLabel.setBackground(Color.WHITE);
        changeCompleteLabel.setOpaque(true);
        if(changeComplete) {
            frame.add(changeCompleteLabel);
        }
        
        //creating the incorrect input label
        JLabel incorrectLabel = new JLabel ("Invalid Input.", SwingConstants.CENTER);
        incorrectLabel.setBounds(450,400,200,25);
        incorrectLabel.setForeground(Color.RED);
        incorrectLabel.setBackground(Color.WHITE);
        incorrectLabel.setOpaque(true);
        
        //creating submit button
        JButton submitButton = new JButton( new AbstractAction("Submit") {
            @Override
            public void actionPerformed (ActionEvent e) {
                int week = 0;
                int day = 0;
                int period = 0;
                //update variables with contents of input boxes
                weekInput = weekField.getText();
                dayInput = dayField.getText();
                periodInput = periodField.getText();
                if(weekInput.isEmpty()) {
                } else {
                    //try to convert input to integer
                    try {
                        week = Integer.valueOf(weekInput);
                    } catch (NumberFormatException error) {
                        frame.add(incorrectLabel);
                        frame.repaint();
                    }
                }
                //same logic as week input
                if (dayInput.isEmpty()) {
                } else {
                    try {
                        day = Integer.valueOf(dayInput);
                    } catch (NumberFormatException error) {
                        frame.add(incorrectLabel);
                        frame.repaint();
                    }
                }
                //same logic as day input
                if (periodInput.isEmpty()) {
                } else {
                    try {
                        period = Integer.valueOf(periodInput);
                    } catch (NumberFormatException error) {
                        frame.add(incorrectLabel);
                        frame.repaint();
                    }
                }
                //checks to see if numbers are in correct range
                if(week >= 1 && week<=2) {
                    if (day>= 1 && day <=5) {
                        if (period>=1 && period<=5) {
                            //do it
                            //first, find the exact lesson we want
                            Timetable_Filter timetableFilter = new Timetable_Filter(week, day, period, userInfo.get(1), userInfo.get(2));
                            String chosenPeriod = timetableFilter.getPeriod();
                            
                            String[] periodParts = chosenPeriod.split(",");
                            
                            for(int i = 0; i < periodParts.length;i++) {
                                periodParts[i] = periodParts[i].substring(1,periodParts[i].length()-1);
                            }
                            
                            //creating a label for the chosen lesson
                            JLabel chosenLabel = new JLabel("<html>You have selected:", SwingConstants.CENTER);
                            
                            chosenLabel.setText(chosenLabel.getText() + "<br/>Lesson: " + periodParts[3]);
                            chosenLabel.setText(chosenLabel.getText() + "<br/>Room: " + periodParts[4]);
                            chosenLabel.setText(chosenLabel.getText() + "<br/>Class Code: " + periodParts[5]);
                            
                            //customising the chosen period label
                            chosenLabel.setBounds(212,25,200,200);
                            chosenLabel.setForeground(Color.BLACK);
                            chosenLabel.setBackground(Color.WHITE);
                            chosenLabel.setOpaque(true);
                            frame.add(chosenLabel);
                            frame.repaint();
                            
                            //creating a change room button that opens the room changing window
                            JButton changeButton = new JButton( new AbstractAction("Change Rooms") {
                                @Override
                                public void actionPerformed (ActionEvent e) {
                                    Staff_ChangeWindow staffChangeWindow = new Staff_ChangeWindow(periodParts,userInfo);
                                    staffChangeWindow.createWindow();
                                    frame.setVisible(false);
                                }
                            });
                            changeButton.setBounds(212,250,200,50);
                            changeButton.setOpaque(true);
                            frame.add(changeButton);
                            frame.repaint();
                            
                        } else {
                            frame.add(incorrectLabel);
                            frame.repaint();
                        }
                    } else {
                        frame.add(incorrectLabel);
                        frame.repaint();
                    }
                } else {
                    frame.add(incorrectLabel);
                    frame.repaint();
                }
            }
        });
        
        submitButton.setBounds(450,350,200,50);;
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
                Staff_Main staffMain = new Staff_Main(userInfo);
                staffMain.createWindow();
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
        frame.add(changeLabel);
        frame.add(logoLabel);
        frame.add(backLabel);
        frame.add(backButton);
        frame.add(weekLabel);
        frame.add(weekField);
        frame.add(dayLabel);
        frame.add(dayField);
        frame.add(periodLabel);
        frame.add(periodField);
        frame.add(submitButton);
        
        //sets window size
        frame.pack();
        
        //displaying window
        frame.setLocationRelativeTo(null);
        
        //showing window
        frame.setVisible(true);
        
        
    }
    
    
}
