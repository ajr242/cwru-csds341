package com.cwru.petecommerce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.List;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cwru.petecommerce.utils.DatabaseConnection;
import com.cwru.petecommerce.dao.implementation.*;
import com.cwru.petecommerce.models.*;
import com.cwru.petecommerce.dao.abstraction.CRUD;
import com.cwru.petecommerce.dao.abstraction.CRUDComposite;

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
                        Integer sellerID = Integer.parseInt(sellerIDTxt.getText());
                        if (sellerID == 0){
                            sellerID = null;
                        }
                        String name = nameTxt.getText();
                        String descrip = descripTxt.getText();
                        Integer catID = Integer.parseInt(catIDTxt.getText());
                        if (catID == 0){
                            catID = null;
                        }
                        Integer price = Integer.parseInt(priceTxt.getText());
                        Integer stock = Integer.parseInt(stockTxt.getText());
                        
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

        //Add Review
        JButton addReviewButton = new JButton("Add Review");
        addReviewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display a new window to add a Review
                JFrame addReviewFrame = new JFrame("Add Review");
                addReviewFrame.setSize(300, 300);

                // Create a panel to hold the form elements
                JPanel formPanel = new JPanel();
                formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

                // Add text fields for the Review
                JLabel productID = new JLabel("Product ID (0 if null):");
                JTextField productIDTxt = new JTextField(10);
                JLabel customerID = new JLabel("Customer ID (0 if null):");
                JTextField customerIDTxt = new JTextField(10);
                JLabel ratingLbl = new JLabel("Rating:");
                JTextField ratingTxt = new JTextField(10);
                JLabel descripLbl = new JLabel("Description:");
                JTextField descripTxt = new JTextField(10);
                
                formPanel.add(productID);
                formPanel.add(productIDTxt);
                formPanel.add(customerID);
                formPanel.add(customerIDTxt);
                formPanel.add(ratingLbl);
                formPanel.add(ratingTxt);
                formPanel.add(descripLbl);
                formPanel.add(descripTxt);
                
                // Add a button to submit the form
                JButton submitButton = new JButton("Add Review");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent a) {
                        // Get the values from the form fields and add the Review to the database
                        Integer productID = Integer.parseInt(productIDTxt.getText());
                        if (productID == 0){
                            productID = null;
                        }
                        Integer customerID = Integer.parseInt(customerIDTxt.getText());
                        if (customerID == 0){
                            customerID = null;
                        }
                        Integer rating = Integer.parseInt(ratingTxt.getText());
                        String descrip = descripTxt.getText();
                        Date date = new Date();
                        
                        //Add the Review to the database
                        Review review = new Review(0, date, productID, customerID, rating, descrip);
                        Connection connection = null;
                        try {
                            connection = DatabaseConnection.getConnection(); // get the database connection
                            connection.setAutoCommit(false); // start the transaction
                            CRUD<Review> reviewImp = new ReviewImp(connection);
                            int result = reviewImp.create(review);
                            System.out.println(result);
                            connection.commit();
                        } catch (SQLException e) {
                            if (connection != null) {
                                try {
                                    connection.rollback();
                                } catch (SQLException e1) {
                                    System.out.println("Could not add new review nor rollback. See error stack trace.");
                                    e1.printStackTrace();
                                } // rollback the transaction if an exception occurs
                            }
                            System.out.println("Could not add new review. See error stack trace.");
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
                        
                        
                        // Close the "Add Review" window
                        addReviewFrame.dispose();
                    }
                });
                formPanel.add(submitButton);

                // Add the form panel to the window
                addReviewFrame.add(formPanel);

                addReviewFrame.setVisible(true);
            }
        });
        panel.add(addReviewButton);

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


      //Read Customer
      JButton readCustomerButton = new JButton("Read Customer");
      readCustomerButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to read a Customer
              JFrame readCustomerFrame = new JFrame("Read Customer");
              readCustomerFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Read text fields for the Customer's name and email
              JLabel idLbl = new JLabel("Insert Customer ID:");
              JTextField idTxt = new JTextField(10);
              formPanel.add(idLbl);
              formPanel.add(idTxt);

              // Read a button to submit the form
              JButton submitButton = new JButton("Read Customer");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and read the Customer to the database
                      Integer id = Integer.parseInt(idTxt.getText());
                      
                      //Read the Customer to the database
                      
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
                          CRUD<Customer> customerImp = new CustomerImp(connection);
                          Optional<Customer> optionalCustomer = customerImp.getById(id);
                          JFrame readFrame1 = new JFrame("Product Information");
                          readFrame1.setSize(500, 150);
                           JPanel formPanel1 = new JPanel();
                           formPanel1.setLayout(new BoxLayout(formPanel1, BoxLayout.Y_AXIS));
                          if (optionalCustomer.isPresent()) {
                              Customer customer = optionalCustomer.get();
                              JLabel found = new JLabel("Customer found. " + customer.toString());
                              formPanel1.add(found);
                          } else {
                            JLabel found = new JLabel("Customer not found");
                            formPanel1.add(found);
                        }
                        readFrame1.add(formPanel1);
                        readFrame1.setVisible(true);
            
                          connection.commit();
                      } catch (SQLException e) {
                          if (connection != null) {
                              try {
                                  connection.rollback();
                              } catch (SQLException e1) {
                                  System.out.println("Could not read new customer nor rollback. See error stack trace.");
                                  e1.printStackTrace();
                              } // rollback the transaction if an exception occurs
                          }
                          System.out.println("Could not read new customer. See error stack trace.");
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
                      
                      
                      // Close the "Read Customer" window
                      readCustomerFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Read the form panel to the window
              readCustomerFrame.add(formPanel);

              readCustomerFrame.setVisible(true);
          }
      });
      panel.add(readCustomerButton);


      //Read Product
      JButton readProductButton = new JButton("Read Product");
      readProductButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to read a Product
              JFrame readProductFrame = new JFrame("Read Product");
              readProductFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Read text fields for the Product's name and email
              JLabel idLbl = new JLabel("Insert product ID:");
              JTextField idTxt = new JTextField(10);
              formPanel.add(idLbl);
              formPanel.add(idTxt);

              // Read a button to submit the form
              JButton submitButton = new JButton("Read Product");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and read the Product to the database
                      Integer id = Integer.parseInt(idTxt.getText());
                      
                      //Read the Product to the database
                      
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
                          CRUD<Product> productImp = new ProductImp(connection);
                          Optional<Product> optionalProduct = productImp.getById(id);
                          JFrame readFrame1 = new JFrame("Product Information");
                          readFrame1.setSize(500, 150);
                           JPanel formPanel1 = new JPanel();
                           formPanel1.setLayout(new BoxLayout(formPanel1, BoxLayout.Y_AXIS));
                          if (optionalProduct.isPresent()) {
                              Product product = optionalProduct.get();
                              JLabel found = new JLabel("Product found. " + product.toString());
                              formPanel1.add(found);
                          } else {
                            JLabel found = new JLabel("Product not found");
                            formPanel1.add(found);
                        }
                        readFrame1.add(formPanel1);
                        readFrame1.setVisible(true);

                          connection.commit();
                      } catch (SQLException e) {
                          if (connection != null) {
                              try {
                                  connection.rollback();
                              } catch (SQLException e1) {
                                  System.out.println("Could not read new product nor rollback. See error stack trace.");
                                  e1.printStackTrace();
                              } // rollback the transaction if an exception occurs
                          }
                          System.out.println("Could not read new product. See error stack trace.");
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
                      
                      
                      // Close the "Read Product" window
                      readProductFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Read the form panel to the window
              readProductFrame.add(formPanel);

              readProductFrame.setVisible(true);
          }
      });
      panel.add(readProductButton);

            //Read Review
            JButton readReviewButton = new JButton("Read Review");
            readReviewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Display a new window to read a Review
                    JFrame readReviewFrame = new JFrame("Read Review");
                    readReviewFrame.setSize(300, 300);
      
                    // Create a panel to hold the form elements
                    JPanel formPanel = new JPanel();
                    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
      
                    // Read text fields for the Review's name and email
                    JLabel idLbl = new JLabel("Insert Review ID:");
                    JTextField idTxt = new JTextField(10);
                    formPanel.add(idLbl);
                    formPanel.add(idTxt);
      
                    // Read a button to submit the form
                    JButton submitButton = new JButton("Read Review");
                    submitButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent a) {
                            // Get the values from the form fields and read the Review to the database
                            Integer id = Integer.parseInt(idTxt.getText());
                            
                            //Read the Review to the database
                            
                            Connection connection = null;
                            try {
                                connection = DatabaseConnection.getConnection(); // get the database connection
                                connection.setAutoCommit(false); // start the transaction
                                CRUD<Review> reviewImp = new ReviewImp(connection);
                                Optional<Review> optionalReview = reviewImp.getById(id);
                                JFrame readFrame1 = new JFrame("Review Information");
                                readFrame1.setSize(500, 150);
                                 JPanel formPanel1 = new JPanel();
                                 formPanel1.setLayout(new BoxLayout(formPanel1, BoxLayout.Y_AXIS));
                                if (optionalReview.isPresent()) {
                                    Review review = optionalReview.get();
                                    JLabel found = new JLabel("Review found. " + review.toString());
                                    formPanel1.add(found);
                                } else {
                                  JLabel found = new JLabel("Review not found");
                                  formPanel1.add(found);
                              }
                              readFrame1.add(formPanel1);
                              readFrame1.setVisible(true);
      
                                connection.commit();
                            } catch (SQLException e) {
                                if (connection != null) {
                                    try {
                                        connection.rollback();
                                    } catch (SQLException e1) {
                                        System.out.println("Could not read new review nor rollback. See error stack trace.");
                                        e1.printStackTrace();
                                    } // rollback the transaction if an exception occurs
                                }
                                System.out.println("Could not read new review. See error stack trace.");
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
                            
                            
                            // Close the "Read Review" window
                            readReviewFrame.dispose();
                        }
                    });
                    formPanel.add(submitButton);
      
                    // Read the form panel to the window
                    readReviewFrame.add(formPanel);
      
                    readReviewFrame.setVisible(true);
                }
            });
            panel.add(readReviewButton);


      //Read Seller
      JButton readSellerButton = new JButton("Read Seller");
      readSellerButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to read a Seller
              JFrame readSellerFrame = new JFrame("Read Seller");
              readSellerFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Read text fields for the Seller's name and email
              JLabel idLbl = new JLabel("Enter Seller ID:");
              JTextField idTxt = new JTextField(10);
              formPanel.add(idLbl);
              formPanel.add(idTxt);

              // Read a button to submit the form
              JButton submitButton = new JButton("Read Seller");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and read the Seller to the database
                      Integer id = Integer.parseInt(idTxt.getText());
                      
                      //Read the Seller to the database
                      
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
                          CRUD<Seller> sellerImp = new SellerImp(connection);
                          Optional<Seller> optionalSeller = sellerImp.getById(id);
                          JFrame readSellerFrame1 = new JFrame("Seller Information");
                         readSellerFrame1.setSize(500, 150);
                          JPanel formPanel1 = new JPanel();
                          formPanel1.setLayout(new BoxLayout(formPanel1, BoxLayout.Y_AXIS));
                          if (optionalSeller.isPresent()) {                      
                              Seller seller = optionalSeller.get();
                              JLabel sellerFound = new JLabel("Seller found" + seller.toString());
                              formPanel1.add(sellerFound);
                          } else {
                              JLabel sellerFound = new JLabel("Seller not found");
                              formPanel1.add(sellerFound);
                          }
                          readSellerFrame1.add(formPanel1);

                          readSellerFrame1.setVisible(true);
                          connection.commit();
                      } catch (SQLException e) {
                          if (connection != null) {
                              try {
                                  connection.rollback();
                              } catch (SQLException e1) {
                                  System.out.println("Could not read new seller nor rollback. See error stack trace.");
                                  e1.printStackTrace();
                              } // rollback the transaction if an exception occurs
                          }
                          System.out.println("Could not read new seller. See error stack trace.");
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
                      
                      
                      // Close the "Read Seller" window
                      readSellerFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Read the form panel to the window
              readSellerFrame.add(formPanel);

              readSellerFrame.setVisible(true);
          }
      });
      panel.add(readSellerButton);
       
      
      //Read Category
      JButton readCategoryButton = new JButton("Read Category");
      readCategoryButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to read a Category
              JFrame readCategoryFrame = new JFrame("Enter Category ID");
              readCategoryFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Read text fields for the Category's name and email
              JLabel idLbl = new JLabel("First Name:");
              JTextField idTxt = new JTextField(10);
              formPanel.add(idLbl);
              formPanel.add(idTxt);

              // Read a button to submit the form
              JButton submitButton = new JButton("Read Category");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and read the Category to the database
                      Integer id = Integer.parseInt(idTxt.getText());
                      
                      //Read the Category to the database
                      
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
                          CRUD<Category> categoryImp = new CategoryImp(connection);
                          Optional<Category> optionalCategory = categoryImp.getById(id);
                          JFrame readCatFrame1 = new JFrame("Category Information");
                          readCatFrame1.setSize(500, 150);
                           JPanel formPanel1 = new JPanel();
                           formPanel1.setLayout(new BoxLayout(formPanel1, BoxLayout.Y_AXIS));
                          if (optionalCategory.isPresent()) {
                              Category category = optionalCategory.get();
                              JLabel found = new JLabel("Category found. " + category.toString());
                              formPanel1.add(found);
                          } else {
                            JLabel found = new JLabel("Category not found");
                            formPanel1.add(found);
                        }
                        readCatFrame1.add(formPanel1);
                        readCatFrame1.setVisible(true);
            
                          connection.commit();
                      } catch (SQLException e) {
                          if (connection != null) {
                              try {
                                  connection.rollback();
                              } catch (SQLException e1) {
                                  System.out.println("Could not read new category nor rollback. See error stack trace.");
                                  e1.printStackTrace();
                              } // rollback the transaction if an exception occurs
                          }
                          System.out.println("Could not read new category. See error stack trace.");
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
                      
                      
                      // Close the "Read Category" window
                      readCategoryFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Read the form panel to the window
              readCategoryFrame.add(formPanel);

              readCategoryFrame.setVisible(true);
          }
      });
      panel.add(readCategoryButton);

      //Update Customer
      JButton updateCustomerButton = new JButton("Update Customer");
      updateCustomerButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to update a Customer
              JFrame updateCustomerFrame = new JFrame("Update Customer");
              updateCustomerFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Update text fields for the Customer's name and email
              JLabel idLbl = new JLabel("Customer ID:");
              JTextField idTxt = new JTextField(10);
              JLabel firstNameLbl = new JLabel("New First Name:");
              JTextField firstNameTxt = new JTextField(10);
              JLabel lastNameLbl = new JLabel("New Last Name:");
              JTextField lastNameTxt = new JTextField(10);
              JLabel emailLbl = new JLabel("New Email:");
              JTextField emailTxt = new JTextField(10);
              JLabel passLbl = new JLabel("New Password:");
              JTextField passTxt = new JTextField(10);
              JLabel addressLbl = new JLabel("New Address:");
              JTextField addressTxt = new JTextField(10);
              formPanel.add(idLbl);
              formPanel.add(idTxt);
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


              // Update a button to submit the form
              JButton submitButton = new JButton("Update Customer");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and add the Customer to the database
                      Integer id = Integer.parseInt(idTxt.getText());
                      String firstName = firstNameTxt.getText();
                      String lastName = lastNameTxt.getText();
                      String email = emailTxt.getText();
                      String pass = passTxt.getText();
                      String address = addressTxt.getText();
                      
                      //Update the Customer to the database
                      Customer customer = new Customer(0, firstName, lastName, email, pass, address);
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
                          CRUD<Customer> customerImp = new CustomerImp(connection);
                          int result = customerImp.update(id, customer);
                          System.out.println(result);
                          connection.commit();
                      } catch (SQLException e) {
                          if (connection != null) {
                              try {
                                  connection.rollback();
                              } catch (SQLException e1) {
                                  System.out.println("Could not update new customer nor rollback. See error stack trace.");
                                  e1.printStackTrace();
                              } // rollback the transaction if an exception occurs
                          }
                          System.out.println("Could not update new customer. See error stack trace.");
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
                      
                      
                      // Close the "Update Customer" window
                      updateCustomerFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Update the form panel to the window
              updateCustomerFrame.add(formPanel);

              updateCustomerFrame.setVisible(true);
          }
      });
      panel.add(updateCustomerButton);

      //Update Product
      JButton updateProductButton = new JButton("Update Product");
      updateProductButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to update a Product
              JFrame updateProductFrame = new JFrame("Update Product");
              updateProductFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Update text fields for the Product's name and email
              JLabel idLbl = new JLabel("Product ID:");
              JTextField idTxt = new JTextField(10);
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
              formPanel.add(idLbl);
              formPanel.add(idTxt);
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
              
              // Update a button to submit the form
              JButton submitButton = new JButton("Update Product");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and update the Product to the database
                      Integer id = Integer.parseInt(idTxt.getText());
                      Integer sellerID = Integer.parseInt(sellerIDTxt.getText());
                      if (sellerID == 0){
                          sellerID = null;
                      }
                      String name = nameTxt.getText();
                      String descrip = descripTxt.getText();
                      Integer catID = Integer.parseInt(catIDTxt.getText());
                      if (catID == 0){
                          catID = null;
                      }
                      Integer price = Integer.parseInt(priceTxt.getText());
                      Integer stock = Integer.parseInt(stockTxt.getText());
                      
                      //Update the Product to the database
                      Product product = new Product(0, sellerID, name, descrip, catID, price, stock);
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
                          CRUD<Product> productImp = new ProductImp(connection);
                          int result = productImp.update(id, product);
                          System.out.println(result);
                          connection.commit();
                      } catch (SQLException e) {
                          if (connection != null) {
                              try {
                                  connection.rollback();
                              } catch (SQLException e1) {
                                  System.out.println("Could not update new product nor rollback. See error stack trace.");
                                  e1.printStackTrace();
                              } // rollback the transaction if an exception occurs
                          }
                          System.out.println("Could not update new product. See error stack trace.");
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
                      
                      
                      // Close the "Update Product" window
                      updateProductFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Update the form panel to the window
              updateProductFrame.add(formPanel);

              updateProductFrame.setVisible(true);
          }
      });
      panel.add(updateProductButton);

      //Update Review
      JButton updateReviewButton = new JButton("Update Review");
      updateReviewButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to update a Review
              JFrame updateReviewFrame = new JFrame("Update Review");
              updateReviewFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Update text fields for the Review's name and email
              JLabel idLbl = new JLabel("Review ID:");
              JTextField idTxt = new JTextField(10);
              JLabel productID = new JLabel("Product ID (0 if null):");
              JTextField productIDTxt = new JTextField(10);
              JLabel customerID = new JLabel("Customer ID (0 if null):");
              JTextField customerIDTxt = new JTextField(10);
              JLabel ratingLbl = new JLabel("Rating:");
              JTextField ratingTxt = new JTextField(10);
              JLabel descripLbl = new JLabel("Description:");
              JTextField descripTxt = new JTextField(10);
              
              formPanel.add(idLbl);
              formPanel.add(idTxt);
              formPanel.add(productID);
              formPanel.add(productIDTxt);
              formPanel.add(customerID);
              formPanel.add(customerIDTxt);
              formPanel.add(ratingLbl);
              formPanel.add(ratingTxt);
              formPanel.add(descripLbl);
              formPanel.add(descripTxt);
              
              // Update a button to submit the form
              JButton submitButton = new JButton("Update Review");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and update the Review to the database
                      Integer id = Integer.parseInt(idTxt.getText());
                      Date date = new Date();
                      Integer productID = Integer.parseInt(productIDTxt.getText());
                      if (productID == 0){
                          productID = null;
                      }
                      Integer customerID = Integer.parseInt(customerIDTxt.getText());
                      if (customerID == 0){
                          customerID = null;
                      }
                      Integer rating = Integer.parseInt(ratingTxt.getText());
                      String descrip = descripTxt.getText();
                      
                      //Update the Product to the database
                      Review review = new Review(0, date, productID, customerID, rating, descrip);
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
                          CRUD<Review> reviewImp = new ReviewImp(connection);
                          int result = reviewImp.update(id, review);
                          System.out.println(result);
                          connection.commit();
                      } catch (SQLException e) {
                          if (connection != null) {
                              try {
                                  connection.rollback();
                              } catch (SQLException e1) {
                                  System.out.println("Could not update new review nor rollback. See error stack trace.");
                                  e1.printStackTrace();
                              } // rollback the transaction if an exception occurs
                          }
                          System.out.println("Could not update new review. See error stack trace.");
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
                      
                      
                      // Close the "Update Review" window
                      updateReviewFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Update the form panel to the window
              updateReviewFrame.add(formPanel);

              updateReviewFrame.setVisible(true);
          }
      });
      panel.add(updateReviewButton);

      //Update Seller
      JButton updateSellerButton = new JButton("Update Seller");
      updateSellerButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to update a Seller
              JFrame updateSellerFrame = new JFrame("Update Seller");
              updateSellerFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Update text fields for the Seller's name and email
              JLabel idLbl = new JLabel("Seller ID:");
              JTextField idTxt = new JTextField(10);
              JLabel nameLbl = new JLabel("Name:");
              JTextField nameTxt = new JTextField(10);
              JLabel emailLbl = new JLabel("Email:");
              JTextField emailTxt = new JTextField(10);
              formPanel.add(idLbl);
              formPanel.add(idTxt);
              formPanel.add(nameLbl);
              formPanel.add(nameTxt);
              formPanel.add(emailLbl);
              formPanel.add(emailTxt);
              
              // Update a button to submit the form
              JButton submitButton = new JButton("Update Seller");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and update the Seller to the database
                      Integer id = Integer.parseInt(idTxt.getText());
                      String name = nameTxt.getText();
                      String email = emailTxt.getText();
                      
                      //Update the Seller to the database
                      Seller seller = new Seller(0, name, email);
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
                          CRUD<Seller> sellerImp = new SellerImp(connection);
                          int result = sellerImp.update(id, seller);
                          System.out.println(result);
                          connection.commit();
                      } catch (SQLException e) {
                          if (connection != null) {
                              try {
                                  connection.rollback();
                              } catch (SQLException e1) {
                                  System.out.println("Could not update new seller nor rollback. See error stack trace.");
                                  e1.printStackTrace();
                              } // rollback the transaction if an exception occurs
                          }
                          System.out.println("Could not update new seller. See error stack trace.");
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
                      
                      
                      // Close the "Update Seller" window
                      updateSellerFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Update the form panel to the window
              updateSellerFrame.add(formPanel);

              updateSellerFrame.setVisible(true);
          }
      });
      panel.add(updateSellerButton);

      //Update Category
      JButton updateCategoryButton = new JButton("Update Category");
      updateCategoryButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to update a Category
              JFrame updateCategoryFrame = new JFrame("Update Category");
              updateCategoryFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Update text fields for the Category's name and email
              JLabel idLbl = new JLabel("Category ID:");
              JTextField idTxt = new JTextField(10);
              JLabel nameLbl = new JLabel("Name:");
              JTextField nameTxt = new JTextField(10);
              JLabel parentCatIDLbl = new JLabel("Parent Cat ID (0 if null):");
              JTextField parentCatIDTxt = new JTextField(10);
              formPanel.add(idLbl);
              formPanel.add(idTxt);           
              formPanel.add(nameLbl);
              formPanel.add(nameTxt);
              formPanel.add(parentCatIDLbl);
              formPanel.add(parentCatIDTxt);
              
              // Update a button to submit the form
              JButton submitButton = new JButton("Update Category");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and update the Category to the database
                      Integer id = Integer.parseInt(idTxt.getText());
                      String name = nameTxt.getText();
                      Integer parentCatID = Integer.parseInt(parentCatIDTxt.getText());
                      if(parentCatID == 0){
                          parentCatID = null;
                      }

                      //Update the Category to the database
                      Category category = new Category(0, name, parentCatID);
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
                          CRUD<Category> categoryImp = new CategoryImp(connection);
                          int result = categoryImp.update(id, category);
                          System.out.println(result);
                          connection.commit();
                      } catch (SQLException e) {
                          if (connection != null) {
                              try {
                                  connection.rollback();
                              } catch (SQLException e1) {
                                  System.out.println("Could not update new category nor rollback. See error stack trace.");
                                  e1.printStackTrace();
                              } // rollback the transaction if an exception occurs
                          }
                          System.out.println("Could not update new category. See error stack trace.");
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
                      
                      
                      // Close the "Update Category" window
                      updateCategoryFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Update the form panel to the window
              updateCategoryFrame.add(formPanel);

              updateCategoryFrame.setVisible(true);
          }
      });
      panel.add(updateCategoryButton);


       //Delete Customer
       JButton deleteCustomerButton = new JButton("Delete Customer");
       deleteCustomerButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               // Display a new window to delete a Customer
               JFrame deleteCustomerFrame = new JFrame("Delete Customer");
               deleteCustomerFrame.setSize(300, 300);
 
               // Create a panel to hold the form elements
               JPanel formPanel = new JPanel();
               formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
 
               // Delete text fields for the Customer's name and email
               JLabel idLbl = new JLabel("Insert Customer ID:");
               JTextField idTxt = new JTextField(10);
               formPanel.add(idLbl);
               formPanel.add(idTxt);
 
               // Delete a button to submit the form
               JButton submitButton = new JButton("Delete Customer");
               submitButton.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent a) {
                       // Get the values from the form fields and delete the Customer to the database
                       Integer id = Integer.parseInt(idTxt.getText());
                       
                       //Delete the Customer to the database
                       
                       Connection connection = null;
                       try {
                           connection = DatabaseConnection.getConnection(); // get the database connection
                           connection.setAutoCommit(false); // start the transaction
                           CRUD<Customer> customerImp = new CustomerImp(connection);
                           int result = customerImp.delete(id);
                           System.out.println(result);
                           connection.commit();
                       } catch (SQLException e) {
                           if (connection != null) {
                               try {
                                   connection.rollback();
                               } catch (SQLException e1) {
                                   System.out.println("Could not delete new customer nor rollback. See error stack trace.");
                                   e1.printStackTrace();
                               } // rollback the transaction if an exception occurs
                           }
                           System.out.println("Could not delete new customer. See error stack trace.");
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
                       
                       
                       // Close the "Delete Customer" window
                       deleteCustomerFrame.dispose();
                   }
               });
               formPanel.add(submitButton);
 
               // Delete the form panel to the window
               deleteCustomerFrame.add(formPanel);
 
               deleteCustomerFrame.setVisible(true);
           }
       });
       panel.add(deleteCustomerButton);
 
 
       //Delete Product
       JButton deleteProductButton = new JButton("Delete Product");
       deleteProductButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               // Display a new window to delete a Product
               JFrame deleteProductFrame = new JFrame("Delete Product");
               deleteProductFrame.setSize(300, 300);
 
               // Create a panel to hold the form elements
               JPanel formPanel = new JPanel();
               formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
 
               // Delete text fields for the Product's name and email
               JLabel idLbl = new JLabel("Insert product ID:");
               JTextField idTxt = new JTextField(10);
               formPanel.add(idLbl);
               formPanel.add(idTxt);
 
               // Delete a button to submit the form
               JButton submitButton = new JButton("Delete Product");
               submitButton.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent a) {
                       // Get the values from the form fields and delete the Product to the database
                       Integer id = Integer.parseInt(idTxt.getText());
                       
                       //Delete the Product to the database
                       
                       Connection connection = null;
                       try {
                           connection = DatabaseConnection.getConnection(); // get the database connection
                           connection.setAutoCommit(false); // start the transaction
                           CRUD<Product> productImp = new ProductImp(connection);
                           int result = productImp.delete(id);
                          System.out.println(result);
                           connection.commit();
                       } catch (SQLException e) {
                           if (connection != null) {
                               try {
                                   connection.rollback();
                               } catch (SQLException e1) {
                                   System.out.println("Could not delete new product nor rollback. See error stack trace.");
                                   e1.printStackTrace();
                               } // rollback the transaction if an exception occurs
                           }
                           System.out.println("Could not delete new product. See error stack trace.");
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
                       
                       
                       // Close the "Delete Product" window
                       deleteProductFrame.dispose();
                   }
               });
               formPanel.add(submitButton);
 
               // Delete the form panel to the window
               deleteProductFrame.add(formPanel);
 
               deleteProductFrame.setVisible(true);
           }
       });
       panel.add(deleteProductButton);

       //Delete Review
       JButton deleteReviewButton = new JButton("Delete Review");
       deleteReviewButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               // Display a new window to delete a Review
               JFrame deleteReviewFrame = new JFrame("Delete Review");
               deleteReviewFrame.setSize(300, 300);
 
               // Create a panel to hold the form elements
               JPanel formPanel = new JPanel();
               formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
 
               // Delete text fields for the Product's name and email
               JLabel idLbl = new JLabel("Insert Review ID:");
               JTextField idTxt = new JTextField(10);
               formPanel.add(idLbl);
               formPanel.add(idTxt);
 
               // Delete a button to submit the form
               JButton submitButton = new JButton("Delete Review");
               submitButton.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent a) {
                       // Get the values from the form fields and delete the Product to the database
                       Integer id = Integer.parseInt(idTxt.getText());
                       
                       //Delete the Product to the database
                       
                       Connection connection = null;
                       try {
                           connection = DatabaseConnection.getConnection(); // get the database connection
                           connection.setAutoCommit(false); // start the transaction
                           CRUD<Review> reviewImp = new ReviewImp(connection);
                           int result = reviewImp.delete(id);
                          System.out.println(result);
                           connection.commit();
                       } catch (SQLException e) {
                           if (connection != null) {
                               try {
                                   connection.rollback();
                               } catch (SQLException e1) {
                                   System.out.println("Could not delete new review nor rollback. See error stack trace.");
                                   e1.printStackTrace();
                               } // rollback the transaction if an exception occurs
                           }
                           System.out.println("Could not delete new review. See error stack trace.");
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
                       
                       
                       // Close the "Delete Product" window
                       deleteReviewFrame.dispose();
                   }
               });
               formPanel.add(submitButton);
 
               // Delete the form panel to the window
               deleteReviewFrame.add(formPanel);
 
               deleteReviewFrame.setVisible(true);
           }
       });
       panel.add(deleteReviewButton);
 
 
       //Delete Seller
       JButton deleteSellerButton = new JButton("Delete Seller");
       deleteSellerButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               // Display a new window to delete a Seller
               JFrame deleteSellerFrame = new JFrame("Delete Seller");
               deleteSellerFrame.setSize(300, 300);
 
               // Create a panel to hold the form elements
               JPanel formPanel = new JPanel();
               formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
 
               // Delete text fields for the Seller's name and email
               JLabel idLbl = new JLabel("Enter Seller ID:");
               JTextField idTxt = new JTextField(10);
               formPanel.add(idLbl);
               formPanel.add(idTxt);
 
               // Delete a button to submit the form
               JButton submitButton = new JButton("Delete Seller");
               submitButton.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent a) {
                       // Get the values from the form fields and delete the Seller to the database
                       Integer id = Integer.parseInt(idTxt.getText());
                       
                       //Delete the Seller to the database
                       
                       Connection connection = null;
                       try {
                           connection = DatabaseConnection.getConnection(); // get the database connection
                           connection.setAutoCommit(false); // start the transaction
                           CRUD<Seller> sellerImp = new SellerImp(connection);
                           int result = sellerImp.delete(id);
                          System.out.println(result);
                           connection.commit();
                       } catch (SQLException e) {
                           if (connection != null) {
                               try {
                                   connection.rollback();
                               } catch (SQLException e1) {
                                   System.out.println("Could not delete new seller nor rollback. See error stack trace.");
                                   e1.printStackTrace();
                               } // rollback the transaction if an exception occurs
                           }
                           System.out.println("Could not delete new seller. See error stack trace.");
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
                       
                       
                       // Close the "Delete Seller" window
                       deleteSellerFrame.dispose();
                   }
               });
               formPanel.add(submitButton);
 
               // Delete the form panel to the window
               deleteSellerFrame.add(formPanel);
 
               deleteSellerFrame.setVisible(true);
           }
       });
       panel.add(deleteSellerButton);
        
       
       //Delete Category
       JButton deleteCategoryButton = new JButton("Delete Category");
       deleteCategoryButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               // Display a new window to delete a Category
               JFrame deleteCategoryFrame = new JFrame("Enter Category ID");
               deleteCategoryFrame.setSize(300, 300);
 
               // Create a panel to hold the form elements
               JPanel formPanel = new JPanel();
               formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
 
               // Delete text fields for the Category's name and email
               JLabel idLbl = new JLabel("First Name:");
               JTextField idTxt = new JTextField(10);
               formPanel.add(idLbl);
               formPanel.add(idTxt);
 
               // Delete a button to submit the form
               JButton submitButton = new JButton("Delete Category");
               submitButton.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent a) {
                       // Get the values from the form fields and delete the Category to the database
                       Integer id = Integer.parseInt(idTxt.getText());
                       
                       //Delete the Category to the database
                       
                       Connection connection = null;
                       try {
                           connection = DatabaseConnection.getConnection(); // get the database connection
                           connection.setAutoCommit(false); // start the transaction
                           CRUD<Category> categoryImp = new CategoryImp(connection);
                           int result = categoryImp.delete(id);
                          System.out.println(result);
                           connection.commit();
                       } catch (SQLException e) {
                           if (connection != null) {
                               try {
                                   connection.rollback();
                               } catch (SQLException e1) {
                                   System.out.println("Could not delete new category nor rollback. See error stack trace.");
                                   e1.printStackTrace();
                               } // rollback the transaction if an exception occurs
                           }
                           System.out.println("Could not delete new category. See error stack trace.");
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
                       
                       
                       // Close the "Delete Category" window
                       deleteCategoryFrame.dispose();
                   }
               });
               formPanel.add(submitButton);
 
               // Delete the form panel to the window
               deleteCategoryFrame.add(formPanel);
 
               deleteCategoryFrame.setVisible(true);
           }
       });
       panel.add(deleteCategoryButton);


        //Add to cart UI
      JButton addToCartButton = new JButton("Add to Cart");
      addToCartButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to update a Product
              JFrame addToCartFrame = new JFrame("Add to Cart");
              addToCartFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Update text fields for the Product's name and email
              JLabel prodIDdLbl = new JLabel("Product ID:");
              JTextField prodIdTxt = new JTextField(10);
              JLabel custIdLbl = new JLabel("Customer ID:");
              JTextField custIdTxt = new JTextField(10);
              JLabel quantityLbl = new JLabel("Quantity:");
              JTextField quantityTxt = new JTextField(10);
              formPanel.add(prodIDdLbl);
              formPanel.add(prodIdTxt);
              formPanel.add(custIdLbl);
              formPanel.add(custIdTxt);
              formPanel.add(quantityLbl);
              formPanel.add(quantityTxt);
              
              // Update a button to submit the form
              JButton submitButton = new JButton("Add to Cart");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and update the Product to the database
                      Integer prodId = Integer.parseInt(prodIdTxt.getText());
                      Integer custId = Integer.parseInt(custIdTxt.getText());
                      Integer quantity = Integer.parseInt(quantityTxt.getText());
                      
                      
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
                          
                          // create the cart
                          Cart cart = new Cart(custId, prodId, quantity);
                          CRUDComposite<Cart> cartImp = new CartImp(connection);
                          int result = cartImp.create(cart); // pass the connection to the create method
                          System.out.println(result); // print the ID of the created cart
                          
                          connection.commit(); // commit the transaction
                      } catch (SQLException e) {
                        if (connection != null) {
                            try {
                                connection.rollback();
                            } catch (SQLException e1) {
                                System.out.println("Could not add to cart or rollback. See error stack trace.");
                                e1.printStackTrace();
                            } // rollback the transaction if an exception occurs
                        }
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
                      
                      // Close the "Add to Cart" window
                      addToCartFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Update the form panel to the window
              addToCartFrame.add(formPanel);

              addToCartFrame.setVisible(true);
          }
      });
      panel.add(addToCartButton);
       
      //Checkout UI
      JButton checkoutButton = new JButton("Checkout");
      checkoutButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to update a Product
              JFrame checkoutFrame = new JFrame("Checkout");
              checkoutFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Update text fields for the Product's name and email
              JLabel custIdLbl = new JLabel("Customer ID:");
              JTextField custIdTxt = new JTextField(10);
              JLabel paymentTypeLbl = new JLabel("Payment Type:");
              JTextField paymentTypeTxt = new JTextField(10);
              formPanel.add(custIdLbl);
              formPanel.add(custIdTxt);
              formPanel.add(paymentTypeLbl);
              formPanel.add(paymentTypeTxt);
              
              // Update a button to submit the form
              JButton submitButton = new JButton("Checkout");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and update the Product to the database
                      Integer custId = Integer.parseInt(custIdTxt.getText());
                      String paymentType = paymentTypeTxt.getText();
                      
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
              
                          // get all items in the customer's cart
                          CartImp cartImp = new CartImp(connection);
                          List<Cart> cartItems = cartImp.getAllByCustomerID(custId);
                          
                          // check if cart is empty
                          if(cartItems.isEmpty()) {
                              System.out.println("Cart is empty");
                              return;
                          }
                          
                          // get current date
                          Date date = new Date();
                          
                          // create new purchase record
                          PurchaseImp purchaseImp = new PurchaseImp(connection);
                          Purchase purchase = new Purchase(0, paymentType, 0, date, custId, false);
                          int purchaseID = purchaseImp.create(purchase);

                          // insert items into PurchaseProduct table and update product stock
                          ProductImp productImp = new ProductImp(connection);
                          PurchaseProductImp purchaseProductImp = new PurchaseProductImp(connection);
                          int totalAmount = 0;
                          for(Cart cartItem: cartItems) {
                            System.out.println(cartItem.getProductID());
                            Optional<Product> optionalProduct = productImp.getById(cartItem.getProductID());

                            if (optionalProduct.isPresent()) {
                                Product product = optionalProduct.get();

                                // TODO: Deal with purchases where this is not the case
                                if(product.getStock() >= cartItem.getQuantity()) {
                                    // reduce product stock by cart item quantity
                                    productImp.updateStock(product.getId(), -cartItem.getQuantity());
                        
                                    // insert item into PurchaseProduct table
                                    PurchaseProduct purchaseProduct = new PurchaseProduct(purchaseID, cartItem.getProductID(), product.getPrice(), cartItem.getQuantity());
                                    purchaseProductImp.create(purchaseProduct);
                        
                                    // update total amount
                                    totalAmount += product.getPrice() * cartItem.getQuantity();
                                }
                            }
                          }
                      
                          // update totalAmount in Purchase table
                          purchase.setTotalAmount(totalAmount);
                          purchaseImp.update(purchaseID, purchase);
                          
                          // delete all items in customer's cart
                          cartImp.deleteAllByCustomerID(custId);

                          connection.commit(); // commit the transaction
                      } catch (SQLException e) {
                        try {
                            connection.rollback();
                        } catch (SQLException e1) {
                            System.out.println("Could not checkout or rollback. See error stack trace.");
                            e1.printStackTrace();
                        } // rollback the transaction if an exception occurs
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

                      
                      // Close the "Add to Cart" window
                      checkoutFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Update the form panel to the window
              checkoutFrame.add(formPanel);

              checkoutFrame.setVisible(true);
          }

          

      });
      panel.add(checkoutButton);


     //Products by Seller UI
      JButton productsBySellerButton = new JButton("Products by Seller");
      productsBySellerButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to update a Product
              JFrame productsBySellerFrame = new JFrame("Products by Seller");
              productsBySellerFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Update text fields for the Product's name and email
              JLabel sellerIDLbl = new JLabel("Seller ID:");
              JTextField sellerIdTxt = new JTextField(10);
              formPanel.add(sellerIDLbl);
              formPanel.add(sellerIdTxt);
              
              // Update a button to submit the form
              JButton submitButton = new JButton("Products by Seller");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and update the Product to the database
                      Integer sellerID = Integer.parseInt(sellerIdTxt.getText());
                      
                      
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
                          
                          // create the cart
                          ProductImp productImp = new ProductImp(connection);
                          List<Product> result = productImp.getBySeller(sellerID); // pass the connection to the create method
                          
                          JFrame readFrame1 = new JFrame("Category Information");
                          readFrame1.setSize(500, 150);
                           JPanel formPanel1 = new JPanel();
                           formPanel1.setLayout(new BoxLayout(formPanel1, BoxLayout.Y_AXIS));
                           for (Product p : result){
                                JLabel found = new JLabel(p.toString());
                                formPanel1.add(found);
                           }
                        readFrame1.add(formPanel1);
                        readFrame1.setVisible(true);
                          connection.commit(); // commit the transaction
                      } catch (SQLException e) {
                        if (connection != null) {
                            try {
                                connection.rollback();
                            } catch (SQLException e1) {
                                System.out.println("See error stack trace.");
                                e1.printStackTrace();
                            } // rollback the transaction if an exception occurs
                        }
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
                      
                      // Close the "Add to Cart" window
                      productsBySellerFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Update the form panel to the window
              productsBySellerFrame.add(formPanel);

              productsBySellerFrame.setVisible(true);
          }
      });
      panel.add(productsBySellerButton);


      //GetRevenueByCategoryID UI
      JButton getrevenuebycategoryidButton = new JButton("Get Revenue By Category ID");
      getrevenuebycategoryidButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to update a Product
              JFrame getrevenuebycategoryidFrame = new JFrame("Get Revenue By Category ID");
              getrevenuebycategoryidFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Update text fields for the Product's name and email
              JLabel catIdLbl = new JLabel("Category ID:");
              JTextField catIdTxt = new JTextField(10);
              formPanel.add(catIdLbl);
              formPanel.add(catIdTxt);
              
              // Update a button to submit the form
              JButton submitButton = new JButton("Get Revenue By Category ID");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and update the Product to the database
                      Integer catId = Integer.parseInt(catIdTxt.getText());
                      
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
              
                          PurchaseProductImp purchaseproductImp = new PurchaseProductImp(connection);
                          int revenue = purchaseproductImp.getTotalRevenueByCategory(catId);
                           System.out.println(revenue / 100);

                          connection.commit(); // commit the transaction
                      } catch (SQLException e) {
                        try {
                            connection.rollback();
                        } catch (SQLException e1) {
                            System.out.println("Could not get revenue by category id or rollback. See error stack trace.");
                            e1.printStackTrace();
                        } // rollback the transaction if an exception occurs
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

                      // Close the "get revenue by category id" window
                      getrevenuebycategoryidFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Update the form panel to the window
              getrevenuebycategoryidFrame.add(formPanel);

              getrevenuebycategoryidFrame.setVisible(true);
          }

      });
      panel.add(getrevenuebycategoryidButton);


      //GgetBestSellingProduts UI
      JButton getBestSellingProdutsButton = new JButton("Get Best Selling Products");
      getBestSellingProdutsButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to update a Product
              JFrame getBestSellingProdutsFrame = new JFrame("Get Best Selling Products");
              getBestSellingProdutsFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Update text fields for the Product's name and email
              JLabel numPLbl = new JLabel("Top number of products:");
              JTextField numPTxt = new JTextField(10);
              formPanel.add(numPLbl);
              formPanel.add(numPTxt);
              
              // Update a button to submit the form
              JButton submitButton = new JButton("Get Best Selling Products");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and update the Product to the database
                      Integer numProducts = Integer.parseInt(numPTxt.getText());
                      
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
              
                          ProductImp productImp = new ProductImp(connection);
                          List<Product> best_products = productImp.getBestSellingProducts(numProducts);
                          
                          JFrame readFrame1 = new JFrame("Top Product Information");
                          readFrame1.setSize(500, 150);
                           JPanel formPanel1 = new JPanel();
                           formPanel1.setLayout(new BoxLayout(formPanel1, BoxLayout.Y_AXIS));
                           for (Product p : best_products){
                                JLabel found = new JLabel(p.toString());
                                formPanel1.add(found);
                           }
                        readFrame1.add(formPanel1);
                        readFrame1.setVisible(true);

                          connection.commit(); // commit the transaction
                      } catch (SQLException e) {
                        try {
                            connection.rollback();
                        } catch (SQLException e1) {
                            System.out.println("Could not get best selling product or rollback. See error stack trace.");
                            e1.printStackTrace();
                        } // rollback the transaction if an exception occurs
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

                      // Close the "get revenue by category id" window
                      getBestSellingProdutsFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Update the form panel to the window
              getBestSellingProdutsFrame.add(formPanel);

              getBestSellingProdutsFrame.setVisible(true);
          }

      });
      panel.add(getBestSellingProdutsButton);

      //GgetBestSellingProduts UI
      JButton getBestReviewedProductsButton = new JButton("Get Best Reviewed Products");
      getBestReviewedProductsButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to update a Product
              JFrame getBestReviewedProductsFrame = new JFrame("Get Best Reviewed Products");
              getBestReviewedProductsFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Update text fields for the Product's name and email
              JLabel numPLbl = new JLabel("Top number of Best Reviewed Products:");
              JTextField numPTxt = new JTextField(10);
              formPanel.add(numPLbl);
              formPanel.add(numPTxt);
              
              // Update a button to submit the form
              JButton submitButton = new JButton("Get Best Reviewed Products");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and update the Product to the database
                      Integer numProducts = Integer.parseInt(numPTxt.getText());
                      
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
              
                          ProductImp productImp = new ProductImp(connection);
                          List<Product> best_review_products = productImp.getBestReviewedProducts(numProducts);
                          
                          JFrame readFrame1 = new JFrame("Top Product Information");
                          readFrame1.setSize(500, 150);
                           JPanel formPanel1 = new JPanel();
                           formPanel1.setLayout(new BoxLayout(formPanel1, BoxLayout.Y_AXIS));
                           for (Product p : best_review_products){
                                JLabel found = new JLabel(p.toString());
                                formPanel1.add(found);
                           }
                        readFrame1.add(formPanel1);
                        readFrame1.setVisible(true);

                          connection.commit(); // commit the transaction
                      } catch (SQLException e) {
                        try {
                            connection.rollback();
                        } catch (SQLException e1) {
                            System.out.println("Could not get best reviewed product or rollback. See error stack trace.");
                            e1.printStackTrace();
                        } // rollback the transaction if an exception occurs
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

                      // Close the "get revenue by category id" window
                      getBestReviewedProductsFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Update the form panel to the window
              getBestReviewedProductsFrame.add(formPanel);

              getBestReviewedProductsFrame.setVisible(true);
          }

      });
      panel.add(getBestReviewedProductsButton);

      //Filter by average rating
      JButton filterByAvgRatingButton = new JButton("Filter By Average Rating");
      filterByAvgRatingButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Display a new window to update a Product
              JFrame filterByAvgRatingFrame = new JFrame("Filter by Average Rating");
              filterByAvgRatingFrame.setSize(300, 300);

              // Create a panel to hold the form elements
              JPanel formPanel = new JPanel();
              formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

              // Update text fields for the Product's name and email
              JLabel minRatingLbl = new JLabel("Minimum Rating");
              JTextField minRatingTxt = new JTextField(10);
              JLabel maxRatingLbl = new JLabel("Maximum Rating");
              JTextField maxRatingTxt = new JTextField(10);
              formPanel.add(minRatingLbl);
              formPanel.add(minRatingTxt);
              formPanel.add(maxRatingLbl);
              formPanel.add(maxRatingTxt);
              
              // Update a button to submit the form
              JButton submitButton = new JButton("Filter by Ratings");
              submitButton.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent a) {
                      // Get the values from the form fields and update the Product to the database
                      Integer minRating = Integer.parseInt(minRatingTxt.getText());
                      Integer maxRating = Integer.parseInt(maxRatingTxt.getText());
                      
                      Connection connection = null;
                      try {
                          connection = DatabaseConnection.getConnection(); // get the database connection
                          connection.setAutoCommit(false); // start the transaction
              
                          ProductImp productImp = new ProductImp(connection);
                          List<Product> filter_avg = productImp.filterByAvgRating(minRating, maxRating);
                          
                          JFrame readFrame1 = new JFrame("Filtered Product Information");
                          readFrame1.setSize(500, 150);
                           JPanel formPanel1 = new JPanel();
                           formPanel1.setLayout(new BoxLayout(formPanel1, BoxLayout.Y_AXIS));
                           for (Product p : best_review_products){
                                JLabel found = new JLabel(p.toString());
                                formPanel1.add(found);
                           }
                        readFrame1.add(formPanel1);
                        readFrame1.setVisible(true);

                          connection.commit(); // commit the transaction
                      } catch (SQLException e) {
                        try {
                            connection.rollback();
                        } catch (SQLException e1) {
                            System.out.println("Could not get best filtered product or rollback. See error stack trace.");
                            e1.printStackTrace();
                        } // rollback the transaction if an exception occurs
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

                      // Close the "get revenue by category id" window
                      filterByAvgRatingFrame.dispose();
                  }
              });
              formPanel.add(submitButton);

              // Update the form panel to the window
              filterByAvgRatingFrame.add(formPanel);

              filterByAvgRatingFrame.setVisible(true);
          }

      });
      panel.add(filterByAvgRatingButton);
        //
        //
        //
        //
        //
        frame.add(panel);
        
        frame.setVisible(true);
    }

    
}
