package com.cwru.petecommerce.dao.abstraction;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUDComposite<T> {

    // Create a new record in the table
    int create(T t) throws SQLException;

    // Read a record from the table
    Optional<T> getById(int id1, int id2) throws SQLException;

    // Read all records from the table
    List<T> getAll() throws SQLException;

    // Update a record in the table
    int update(int id1, int id2, T t) throws SQLException;

    // Delete a record from the table
    int delete(int id1, int id2) throws SQLException;

}
