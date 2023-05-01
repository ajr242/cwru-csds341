package com.cwru.petecommerce.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabase {

     private final Connection connection;

     public InitDatabase() {
          try {
               connection = DatabaseConnection.getConnection();
          } catch (SQLException e) {
               throw new RuntimeException(e);
          }
     }

     public void createTable(String query) throws SQLException {
          try (Statement stmt = connection.createStatement()) {
               stmt.execute(query);
               System.out.println("Table created successfully");
          } catch (SQLException e) {
               e.printStackTrace();
               throw e;
          }
     }

     public void createTrigger(String query) throws SQLException {
          try (Statement stmt = connection.createStatement()) {
               stmt.execute(query);
               System.out.println("Trigger created successfully");
          } catch (SQLException e) {
               e.printStackTrace();
               throw e;
          }

     }
}
