package com.cwru.petecommerce.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cwru.petecommerce.dao.abstraction.PokemonDAO;
import com.cwru.petecommerce.models.Pokemon;
import com.cwru.petecommerce.utils.DatabaseConnection;

public class PokemonImp implements PokemonDAO {

    // Create a new pokemon
    // No need to pass in an id, the database will auto generate one (SERIAL)
    @Override
    public int create(Pokemon pokemon) throws SQLException {

        String query = "INSERT INTO pokemon (name, category, weight) VALUES (?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {

            pstmt.setString(1, pokemon.getName());
            pstmt.setString(2, pokemon.getCategory());
            pstmt.setFloat(3, pokemon.getWeight());

            int result = pstmt.executeUpdate();

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve a pokemon by id
    // If the pokemon does not exist, return an empty Optional
    @Override
    public Optional<Pokemon> getById(int id) throws SQLException {

        String query = "SELECT * FROM pokemon WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int resId = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                float weight = rs.getFloat("weight");

                Pokemon pokemon = new Pokemon(resId, name, category, weight);
                return Optional.of(pokemon);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve all pokemon
    // If there are no pokemon, return an empty list
    // Dangerous, if there are a lot of pokemon, this could be a memory hog
    // Solution: use a cursor, page through the results, or stream the results
    @Override
    public List<Pokemon> getAll() throws SQLException {

        String query = "SELECT * FROM pokemon";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();

        ) {

            List<Pokemon> pokemons = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                float weight = rs.getFloat("weight");

                Pokemon pokemon = new Pokemon(id, name, category, weight);

                pokemons.add(pokemon);
            }
            return pokemons;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    // Update a pokemon
    @Override
    public int update(int id, Pokemon pokemon) throws SQLException {

        String query = "UPDATE pokemon SET name = ?, category = ?, weight = ? WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {

            pstmt.setString(1, pokemon.getName());
            pstmt.setString(2, pokemon.getCategory());
            pstmt.setFloat(3, pokemon.getWeight());
            pstmt.setInt(4, pokemon.getId());

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    // Delete a pokemon
    @Override
    public int delete(int id) throws SQLException {

        String query = "DELETE FROM pokemon WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    // Retrieve all pokemon by category
    @Override
    public List<Pokemon> getByCategory(String category) throws SQLException {
        String query = "SELECT * FROM pokemon WHERE category = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {

            List<Pokemon> pokemons = new ArrayList<>();
            pstmt.setString(1, category);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    float weight = rs.getFloat("weight");

                    Pokemon pokemon = new Pokemon(id, name, category, weight);

                    pokemons.add(pokemon);
                }
                return pokemons;

            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

}
