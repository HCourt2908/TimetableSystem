
package com.companyname.school_timetable_system;


import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.border.TitledBorder;


public class Student_Timetable {
    
    private ArrayList<String> userInfo;
    private String weekInput;
    private String dayInput;
    
    //initialising certain variables
    
    //importing logo image
    static ImageIcon logo = new ImageIcon("logo.png");
    
    public Student_Timetable(ArrayList<String> userInfo) {
        this.userInfo = new ArrayList<>();
        this.userInfo = userInfo;
    }
    
    public void createWindow() {
        
        //setting up window
        JFrame frame = new JFrame("Timetable");
        
        //ensuring program ends when window is terminated
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //setting default frame size
        frame.setPreferredSize(new Dimension(700,500));
        
        //changing background colour of frame
        frame.getContentPane().setBackground(new java.awt.Color(2,7,93));
        
        //set layout
        frame.setLayout(null);
        
        //creating your timetable label
        JLabel yourTimeLabel = new JLabel ("Your Timetable", SwingConstants.CENTER);
        yourTimeLabel.setBounds(25,25,150,25);
        yourTimeLabel.setForeground(Color.BLACK);
        yourTimeLabel.setBackground(Color.WHITE);
        yourTimeLabel.setOpaque(true);
        
        //creating the enter week label
        JLabel weekLabel = new JLabel ("Enter Week (1-2)", SwingConstants.CENTER);
        weekLabel.setBounds(400,50,200,50);
        weekLabel.setForeground(Color.BLACK);
        weekLabel.setBackground(Color.WHITE);
        weekLabel.setOpaque(true);
        
        //creating the enter week input box
        JTextField weekField = new JTextField();
        weekField.setBounds(400,100,200,50);
        
        //creating the enter day label
        JLabel dayLabel = new JLabel ("Enter Day (1-5)", SwingConstants.CENTER);
        dayLabel.setBounds(400,200,200,50);
        dayLabel.setForeground(Color.BLACK);
        dayLabel.setBackground(Color.WHITE);
        dayLabel.setOpaque(true);
        
        //creating the enter day input box
        JTextField dayField = new JTextField();
        dayField.setBounds(400,250,200,50);
        
        //creating the incorrect input label
        JLabel incorrectLabel = new JLabel ("Invalid Input.", SwingConstants.CENTER);
        incorrectLabel.setBounds(400,400,200,25);
        incorrectLabel.setForeground(Color.RED);
        incorrectLabel.setBackground(Color.WHITE);
        incorrectLabel.setOpaque(true);
        
        //creating the submit button
        JButton submitButton;
        submitButton = new JButton( new AbstractAction("Submit") {
            @Override
            public void actionPerformed (ActionEvent e) {
                int week = 0;
                int day = 0;
                //update variables with contents of input boxes
                weekInput = weekField.getText();
                dayInput = dayField.getText();
                if (weekInput.isEmpty()) {
                } else {
                    //try to convert input to integers
                    try {
                        week = Integer.valueOf(weekInput);
                        //if only contains characters, error message shows
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
                //checks to see if numbers are in correct range
                if (week >=1 && week<=2) {
                    if (day >=1 && day<=5) {
                        //do it
                        frame.remove(incorrectLabel);
                        frame.repaint();
                        
                        //begin filtering
                        
                        Timetable_Filter filter = new Timetable_Filter(week,day,userInfo.get(1),userInfo.get(2));
                        
                        String[][] days = filter.tableFormat();
                        String[] columnNames = { "Time", "Lesson", "Classroom", "Teacher" };
                        
                        //creates a new window containing the timetable
                        JTable table = new JTable(days,columnNames);
                        table.setFillsViewportHeight(true);
                        JFrame tableFrame = new JFrame();
                        JPanel tablePanel = new JPanel(new GridLayout());
                        tablePanel.setBorder(BorderFactory.createTitledBorder
                            (BorderFactory.createEtchedBorder(), "Timetable", TitledBorder.CENTER, TitledBorder.TOP));
                        tablePanel.add(new JScrollPane(table));
                        tableFrame.add(tablePanel);
                        tableFrame.setSize(550,200);
                        tableFrame.setLocationRelativeTo(null);
                        tableFrame.setVisible(true);
                        
                        
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
        
        submitButton.setBounds(400,350,200,50);
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
                Student_Main studentMain = new Student_Main(userInfo);
                studentMain.createWindow();
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
        frame.add(yourTimeLabel);
        frame.add(logoLabel);
        frame.add(weekLabel);
        frame.add(weekField);
        frame.add(dayLabel);
        frame.add(dayField);
        frame.add(submitButton);
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
