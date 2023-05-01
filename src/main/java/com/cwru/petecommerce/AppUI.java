package com.cwru.petecommerce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

import com.cwru.petecommerce.utils.DatabaseConnection;
import com.cwru.petecommerce.dao.implementation.CustomerImp;
import com.cwru.petecommerce.models.Customer;
import com.cwru.petecommerce.dao.abstraction.CRUD;

public class AppUI {
    //private static Connection connection;
    public static void main(String[] args) {
       // try {
       //     connection = DatabaseConnection.getConnection();
       //} catch (SQLException e) {
       //     throw new RuntimeException(e);
       //}
        JFrame frame = new JFrame("Our Pet Store");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a menu bar and add it to the frame
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        
        // Create a panel to hold the buttons
        JPanel panel = new JPanel();
        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display a new window to add a Customer
                JFrame addCustomerFrame = new JFrame("Add Customer");
                addCustomerFrame.setSize(300, 300);

                // Create a panel to hold the form elements
                JPanel formPanel = new JPanel();
                formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

                // Add text fields for the Customer's name and email
                JLabel firstNameLbl = new JLabel("First Name:");
                JTextField firstNameTxt = new JTextField(10);
                JLabel lastNameLbl = new JLabel("Last Name:");
                JTextField lastNameTxt = new JTextField(10);
                JLabel emailLbl = new JLabel("Email:");
                JTextField emailTxt = new JTextField(10);
                JLabel passLbl = new JLabel("Password:");
                JTextField passTxt = new JTextField(10);
                JLabel addressLbl = new JLabel("Address:");
                JTextField addressTxt = new JTextField(10);
                formPanel.add(firstNameLbl);
                formPanel.add(firstNameTxt);
                formPanel.add(lastNameLbl);
                formPanel.add(lastNameTxt);
                formPanel.add(emailLbl);
                formPanel.add(emailTxt);
                formPanel.add(passLbl);
                formPanel.add(passTxt);
                formPanel.add(addressLbl);
                formPanel.add(addressTxt);

                // Add a button to submit the form
                JButton submitButton = new JButton("Add Customer");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent a) {
                        // Get the values from the form fields and add the Customer to the database
                        String firstName = firstNameTxt.getText();
                        String lastName = lastNameTxt.getText();
                        String email = emailTxt.getText();
                        String pass = passTxt.getText();
                        String address = addressTxt.getText();
                        //Add the Customer to the database
                        Customer customer = new Customer(0, firstName, lastName, email, pass, address);
                        Connection connection = null;
                        try {
                            connection = DatabaseConnection.getConnection(); // get the database connection
                            connection.setAutoCommit(false); // start the transaction
                            CRUD<Customer> customerImp = new CustomerImp(connection);
                            int result = customerImp.create(customer);
                            System.out.println(result);
                            connection.commit();
                        } catch (SQLException e) {
                            if (connection != null) {
                                try {
                                    connection.rollback();
                                } catch (SQLException e1) {
                                    System.out.println("Could not add new customer nor rollback. See error stack trace.");
                                    e1.printStackTrace();
                                } // rollback the transaction if an exception occurs
                            }
                            System.out.println("Could not add new customer. See error stack trace.");
                            e.printStackTrace();
                        } finally {
                            if (connection != null) {
                                try {
                                    connection.close(); // close the connection
                                } catch (SQLException e) {
                                    System.out.println("Connection could not close. See error stack trace.");
                                    e.printStackTrace();
                                } 
                            }
                        }
                        
                        
                        // Close the "Add Customer" window
                        addCustomerFrame.dispose();
                    }
                });
                formPanel.add(submitButton);

                // Add the form panel to the window
                addCustomerFrame.add(formPanel);

                addCustomerFrame.setVisible(true);
            }
        });
        panel.add(addCustomerButton);
        JButton addProductButton = new JButton("Add Product");
        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display a new window to add a product
                JFrame addProductFrame = new JFrame("Add Product");
                addProductFrame.setSize(300, 300);
                addProductFrame.setVisible(true);
            }
        });
        panel.add(addProductButton);
        frame.add(panel);
        
        frame.setVisible(true);
    }
}
