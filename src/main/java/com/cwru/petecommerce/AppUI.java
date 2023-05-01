package com.cwru.petecommerce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

import com.cwru.petecommerce.utils.DatabaseConnection;
import com.cwru.petecommerce.dao.implementation.*;
import com.cwru.petecommerce.models.*;
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

        //Add Customer
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

        //Add Product
        JButton addProductButton = new JButton("Add Product");
        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display a new window to add a Product
                JFrame addProductFrame = new JFrame("Add Product");
                addProductFrame.setSize(300, 300);

                // Create a panel to hold the form elements
                JPanel formPanel = new JPanel();
                formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

                // Add text fields for the Product's name and email
                JLabel sellerID = new JLabel("Seller ID (0 if null):");
                JTextField sellerIDTxt = new JTextField(10);
                JLabel nameLbl = new JLabel("Name:");
                JTextField nameTxt = new JTextField(10);
                JLabel descripLbl = new JLabel("Description:");
                JTextField descripTxt = new JTextField(10);
                JLabel catIDLbl = new JLabel("Category ID (0 if null):");
                JTextField catIDTxt = new JTextField(10);
                JLabel priceLbl = new JLabel("Price:");
                JTextField priceTxt = new JTextField(10);
                JLabel stockLbl = new JLabel("Stock:");
                JTextField stockTxt = new JTextField(10);
                formPanel.add(sellerID);
                formPanel.add(sellerIDTxt);
                formPanel.add(nameLbl);
                formPanel.add(nameTxt);
                formPanel.add(descripLbl);
                formPanel.add(descripTxt);
                formPanel.add(catIDLbl);
                formPanel.add(catIDTxt);
                formPanel.add(priceLbl);
                formPanel.add(priceTxt);
                formPanel.add(stockLbl);
                formPanel.add(stockTxt);
                
                // Add a button to submit the form
                JButton submitButton = new JButton("Add Product");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent a) {
                        // Get the values from the form fields and add the Product to the database
                        int sellerID = Integer.parseInt(sellerIDTxt.getText());
                        String name = nameTxt.getText();
                        String descrip = descripTxt.getText();
                        int catID = Integer.parseInt(catIDTxt.getText());
                        int price = Integer.parseInt(priceTxt.getText());
                        int stock = Integer.parseInt(stockTxt.getText());
                        
                        //Add the Product to the database
                        Product product = new Product(0, sellerID, name, descrip, catID, price, stock);
                        Connection connection = null;
                        try {
                            connection = DatabaseConnection.getConnection(); // get the database connection
                            connection.setAutoCommit(false); // start the transaction
                            CRUD<Product> productImp = new ProductImp(connection);
                            int result = productImp.create(product);
                            System.out.println(result);
                            connection.commit();
                        } catch (SQLException e) {
                            if (connection != null) {
                                try {
                                    connection.rollback();
                                } catch (SQLException e1) {
                                    System.out.println("Could not add new product nor rollback. See error stack trace.");
                                    e1.printStackTrace();
                                } // rollback the transaction if an exception occurs
                            }
                            System.out.println("Could not add new product. See error stack trace.");
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
                        
                        
                        // Close the "Add Product" window
                        addProductFrame.dispose();
                    }
                });
                formPanel.add(submitButton);

                // Add the form panel to the window
                addProductFrame.add(formPanel);

                addProductFrame.setVisible(true);
            }
        });
        panel.add(addProductButton);


        //Add Seller
        JButton addSellerButton = new JButton("Add Seller");
        addSellerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display a new window to add a Seller
                JFrame addSellerFrame = new JFrame("Add Seller");
                addSellerFrame.setSize(300, 300);

                // Create a panel to hold the form elements
                JPanel formPanel = new JPanel();
                formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

                // Add text fields for the Seller's name and email
                JLabel nameLbl = new JLabel("Name:");
                JTextField nameTxt = new JTextField(10);
                JLabel emailLbl = new JLabel("Email:");
                JTextField emailTxt = new JTextField(10);
                formPanel.add(nameLbl);
                formPanel.add(nameTxt);
                formPanel.add(emailLbl);
                formPanel.add(emailTxt);
                
                // Add a button to submit the form
                JButton submitButton = new JButton("Add Seller");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent a) {
                        // Get the values from the form fields and add the Seller to the database
                        String name = nameTxt.getText();
                        String email = emailTxt.getText();
                        
                        //Add the Seller to the database
                        Seller seller = new Seller(0, name, email);
                        Connection connection = null;
                        try {
                            connection = DatabaseConnection.getConnection(); // get the database connection
                            connection.setAutoCommit(false); // start the transaction
                            CRUD<Seller> sellerImp = new SellerImp(connection);
                            int result = sellerImp.create(seller);
                            System.out.println(result);
                            connection.commit();
                        } catch (SQLException e) {
                            if (connection != null) {
                                try {
                                    connection.rollback();
                                } catch (SQLException e1) {
                                    System.out.println("Could not add new seller nor rollback. See error stack trace.");
                                    e1.printStackTrace();
                                } // rollback the transaction if an exception occurs
                            }
                            System.out.println("Could not add new seller. See error stack trace.");
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
                        
                        
                        // Close the "Add Seller" window
                        addSellerFrame.dispose();
                    }
                });
                formPanel.add(submitButton);

                // Add the form panel to the window
                addSellerFrame.add(formPanel);

                addSellerFrame.setVisible(true);
            }
        });
        panel.add(addSellerButton);

        //Add Category
        JButton addCategoryButton = new JButton("Add Category");
        addCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display a new window to add a Category
                JFrame addCategoryFrame = new JFrame("Add Category");
                addCategoryFrame.setSize(300, 300);

                // Create a panel to hold the form elements
                JPanel formPanel = new JPanel();
                formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

                // Add text fields for the Category's name and email
                JLabel nameLbl = new JLabel("Name:");
                JTextField nameTxt = new JTextField(10);
                JLabel parentCatIDLbl = new JLabel("Parent Cat ID (0 if null):");
                JTextField parentCatIDTxt = new JTextField(10);
                            
                formPanel.add(nameLbl);
                formPanel.add(nameTxt);
                formPanel.add(parentCatIDLbl);
                formPanel.add(parentCatIDTxt);
                
                // Add a button to submit the form
                JButton submitButton = new JButton("Add Category");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent a) {
                        // Get the values from the form fields and add the Category to the database
                        String name = nameTxt.getText();
                        Integer parentCatID = Integer.parseInt(parentCatIDTxt.getText());
                        if(parentCatID == 0){
                            parentCatID = null;
                        }

                        //Add the Category to the database
                        Category category = new Category(0, name, parentCatID);
                        Connection connection = null;
                        try {
                            connection = DatabaseConnection.getConnection(); // get the database connection
                            connection.setAutoCommit(false); // start the transaction
                            CRUD<Category> categoryImp = new CategoryImp(connection);
                            int result = categoryImp.create(category);
                            System.out.println(result);
                            connection.commit();
                        } catch (SQLException e) {
                            if (connection != null) {
                                try {
                                    connection.rollback();
                                } catch (SQLException e1) {
                                    System.out.println("Could not add new category nor rollback. See error stack trace.");
                                    e1.printStackTrace();
                                } // rollback the transaction if an exception occurs
                            }
                            System.out.println("Could not add new category. See error stack trace.");
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
                        
                        
                        // Close the "Add Category" window
                        addCategoryFrame.dispose();
                    }
                });
                formPanel.add(submitButton);

                // Add the form panel to the window
                addCategoryFrame.add(formPanel);

                addCategoryFrame.setVisible(true);
            }
        });
        panel.add(addCategoryButton);

        //
        //
        //
        //
        //
        frame.add(panel);
        
        frame.setVisible(true);
    }
}
