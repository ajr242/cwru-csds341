package com.cwru.pokeverse;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.cwru.pokeverse.dao.abstraction.PokemonDAO;
import com.cwru.pokeverse.dao.implementation.PokemonImp;
import com.cwru.pokeverse.models.Pokemon;
import com.cwru.pokeverse.utils.InitDatabase;

public class App {
    public static void main(String[] args) {
        System.out.println("Let's go!");

        try {
            initDB();
            insertPokemon();
            retrieveById();
            retrieveAll();
            update();
            delete();
            retrieveByCategory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initDB() throws SQLException {

        InitDatabase initDatabase = new InitDatabase();

        String query = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='pokemon' AND xtype='U') " 
             + "BEGIN " 
             + "CREATE TABLE pokemon (id INT IDENTITY(1,1) PRIMARY KEY, name VARCHAR(255), category VARCHAR(255), weight FLOAT) "
             + "END";

        try {
            initDatabase.createTable(query);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    private static void insertPokemon() throws SQLException {

        Pokemon pokemon = new Pokemon(1,  "pikachu", "Fire", 13.2f);

        PokemonDAO pokemonImp = new PokemonImp();

        int result = pokemonImp.create(pokemon);

        System.out.println(result);
    }

    private static void retrieveById() throws SQLException {
        PokemonImp pokemonImp = new PokemonImp();

        Optional<Pokemon> optionalPokemon = pokemonImp.getById(32);

        if (optionalPokemon.isPresent()) {
            System.out.println("Pokemon found");
            Pokemon pokemon = optionalPokemon.get();
            System.out.println(pokemon);
        } else {
            System.out.println("Pokemon not found");
        }

    }

    private static void retrieveAll() throws SQLException {
        PokemonDAO pokemonImp = new PokemonImp();

        List<Pokemon> pokemons = pokemonImp.getAll();

        for (Pokemon pokemon : pokemons) {
            System.out.println(pokemon);
        }
    }

    private static void update() throws SQLException {
        Pokemon pokemon = new Pokemon(1, "Pikachur", "Electric", 13.2f);

        PokemonImp pokemonImp = new PokemonImp();

        int result = pokemonImp.update(4, pokemon);

        System.out.println(result);
    }

    private static void delete() throws SQLException {
        PokemonImp pokemonImp = new PokemonImp();

        int result = pokemonImp.delete(2);

        System.out.println(result);
    }

    private static void retrieveByCategory() throws SQLException {
        PokemonDAO pokemonImp = new PokemonImp();

        List<Pokemon> pokemons = pokemonImp.getByCategory("Fire");

        for (Pokemon pokemon : pokemons) {
            System.out.println(pokemon);
        }
    }

}
