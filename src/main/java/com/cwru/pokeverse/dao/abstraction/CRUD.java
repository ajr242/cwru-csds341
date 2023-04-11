package com.cwru.pokeverse.dao.abstraction;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUD<T> {

    // Create a new record in the table
    int create(T t) throws SQLException;

    // Read a record from the table
    Optional<T> getById(int id) throws SQLException;

    // Read all records from the table
    List<T> getAll() throws SQLException;

    // Update a record in the table
    int update(int id, T t) throws SQLException;

    // Delete a record from the table
    int delete(int id) throws SQLException;

}
