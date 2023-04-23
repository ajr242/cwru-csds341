package com.cwru.petecommerce.dao.abstraction;

import java.sql.SQLException;
import java.util.List;

import com.cwru.petecommerce.models.Pokemon;

public interface PokemonDAO extends CRUD<Pokemon> {
    // // Create
    // int createCategory(List<Pokemon> pokemonList);

    // // Read
    List<Pokemon> getByCategory(String category) throws SQLException;

    // // Update
    // int findByNameAndUpdate(String name, String[] params);

    // // Delete
    // int findByNameAndDelete(String name);

    // int deleteByCategory(String category);

}