package com.cwru.petecommerce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

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
    
                          if (optionalCustomer.isPresent()) {
                              System.out.println("Customer found");
                              Customer customer = optionalCustomer.get();
                              System.out.println(customer);
                          } else {
                              System.out.println("Customer not found");
                          }
            
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
    
                          if (optionalProduct.isPresent()) {
                              System.out.println("Product found");
                              Product product = optionalProduct.get();
                              System.out.println(product);
                          } else {
                              System.out.println("Product not found");
                          }
            
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
                          JFrame readSellerFrame1 = new JFrame("Read Seller");
                         readSellerFrame1.setSize(300, 300);
                          JPanel formPanel1 = new JPanel();
                          formPanel1.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
                          if (optionalSeller.isPresent()) {                      
                              Seller seller = optionalSeller.get();
                              JLabel sellerFound = new JLabel("Seller found" + seller);
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
    
                          if (optionalCategory.isPresent()) {
                              System.out.println("Category found");
                              Category category = optionalCategory.get();
                              System.out.println(category);
                          } else {
                              System.out.println("Category not found");
                          }
            
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


        //
        //
        //
        //
        //
        frame.add(panel);
        
        frame.setVisible(true);
    }
}
