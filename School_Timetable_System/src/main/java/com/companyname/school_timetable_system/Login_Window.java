
package com.companyname.school_timetable_system;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;

//imported the code libraries needed

public class Login_Window implements ActionListener{
    
    //initialising certain variables
    private JTextField usernameField;
    private JPasswordField passwordField;
    private ArrayList<String> userList;
    private ArrayList<String> passList;
    private ArrayList<String> accessList;
    private ArrayList<String> forenameList;
    private ArrayList<String> surnameList;
    private JFrame frame;
    private JLabel incorrectLabel;
    
    
    //importing the logo image
    static ImageIcon logo = new ImageIcon("portal_logo.png");
    
    
    public Login_Window() {
        
        //initialising the lists that will contain the usernames and passwords
        userList = new ArrayList<>();
        passList = new ArrayList<>();
        accessList = new ArrayList<>();
        forenameList = new ArrayList<>();
        surnameList = new ArrayList<>();
        
        //attempting to access the csv file
        try (Scanner scanner = new Scanner(new File("login.csv"))){
            while(scanner.hasNextLine()) {
                //splits up each individual line into username, password and access rights
                String oneLogin = scanner.nextLine();
                String[] parts = oneLogin.split(",");
                //adds the username, password and access rights from each line to three different lists
                userList.add(parts[0]);
                passList.add(parts[1]);
                accessList.add(parts[2]);
                forenameList.add(parts[3]);
                surnameList.add(parts[4]);
            }
        } catch (Exception e) {
            //in case file is not present
            System.out.println(e);
        }
        
    }
    
    public void createWindow(){
        
        
        //setting up window
        frame = new JFrame("Login");
        
        //ensuring program ends when window is terminated
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //setting default frame size
        frame.setPreferredSize(new Dimension(550,450));
        
        //changing background colour of frame
        frame.getContentPane().setBackground(new java.awt.Color(2,7,93));
        
        //set layout
        frame.setLayout(null);
        
        //creating the username label
        JLabel usernameLabel = new JLabel("Username", SwingConstants.CENTER);
        usernameLabel.setBounds(175,200,200,25);
        usernameLabel.setForeground(Color.WHITE);
        
        //creating the username input box
        usernameField = new JTextField();
        usernameField.setBounds(175,225,200,25);
        
        //creating the password label
        JLabel passwordLabel = new JLabel("Password", SwingConstants.CENTER);
        passwordLabel.setBounds(175,250,200,25);
        passwordLabel.setForeground(Color.WHITE);
        
        //creating the password input box
        passwordField = new JPasswordField();
        passwordField.setBounds(175,275,200,25);
        
        //creating the incorrect password label
        incorrectLabel = new JLabel("Incorrect Username or Password.", SwingConstants.CENTER);
        incorrectLabel.setBounds(175,375,200,25);
        incorrectLabel.setForeground(Color.RED);
        
        //creating the submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(225,325,100,25);
        
        
        //adding an action listener
        submitButton.addActionListener(this);
        
        //importing the school logo
        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(100,25,350,150);
        logoLabel.setIcon(logo);
        
        //setting a custom window icon
        ImageIcon img = new ImageIcon("icon.png");
        frame.setIconImage(img.getImage());
        
        //adding all the parts to the window
        frame.add(usernameField);
        frame.add(usernameLabel);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(submitButton);
        frame.add(logoLabel);
        
        //sets window size
        frame.pack();
        
        
        //displaying the window
        frame.setLocationRelativeTo(null);
        
        
        //showing window
        frame.setVisible(true);
        
        
    
    
}
    public void actionPerformed(ActionEvent evt) {
        //on button press, this happens
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        //getting a value for whether the username and password match values in the two lists
        ArrayList<String> allowAccess = this.access(username, password);
        if (Integer.valueOf(allowAccess.get(0)) == 1) {
            frame.setVisible(false);
            Student_Main student_main = new Student_Main(allowAccess);
            student_main.createWindow();
        } else if (Integer.valueOf(allowAccess.get(0)) == 2) {
            frame.setVisible(false);
            Staff_Main staff_main = new Staff_Main(allowAccess);
            staff_main.createWindow();
        } else {
            frame.add(incorrectLabel);
            frame.repaint();
        }
    }
    
    public ArrayList<String> access(String username, String password) {
        ArrayList<String> infoList = new ArrayList<>();
        //iterates over the username list
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).equals(username)) {
                //only checks the corresponding password for validity
                if (passList.get(i).equals(password)) {
                    //if both match, return access level
                    infoList.add(accessList.get(i));
                    infoList.add(forenameList.get(i));
                    infoList.add(surnameList.get(i));
                    
                }
            }
        }
        //no matches, return empty array
        infoList.add("0");
        return infoList;
    }

    
    
}

