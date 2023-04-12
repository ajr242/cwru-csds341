# Database Tutorial

## Normalization

Normalization is the process of organizing data in a database. The main idea is to reduce data redundancy and increase data integrity.

Scenario: We are going to design the Pokeverise database.

The final Pokemon table would be like this:

Pokemon table:

| id  | name       | category | ability  | height | weight | hp  | attack | defense | sp_atk | sp_def | speed | total | generation | legendary |
| --- | ---------- | -------- | -------- | ------ | ------ | --- | ------ | ------- | ------ | ------ | ----- | ----- | ---------- | --------- |
| 1   | Bulbasaur  | Grass    | Overgrow | 0.7    | 6.9    | 45  | 49     | 49      | 65     | 65     | 45    | 318   | 1          | False     |
| 2   | Ivysaur    | Grass    | Overgrow | 1.0    | 13.0   | 60  | 62     | 63      | 80     | 80     | 60    | 405   | 1          | False     |
| 3   | Venusaur   | Grass    | Overgrow | 2.0    | 100.0  | 80  | 82     | 83      | 100    | 100    | 80    | 525   | 1          | False     |
| 4   | Charmander | Fire     | Blaze    | 0.6    | 8.5    | 39  | 52     | 43      | 60     | 50     | 65    | 309   | 1          | False     |

Let's think together about how we can design the table.

The first trivial thing would be just a name:
| name |
|------|
| Bulbasaur |
| Ivysaur |
| Venusaur |

```sql
CREATE TABLE pokemon (
    name VARCHAR(255)
)
```

Let's insert some Pokemons:

```sql
INSERT INTO pokemon (name) VALUES ('Bulbasaur');
INSERT INTO pokemon (name) VALUES ('Ivysaur');
INSERT INTO pokemon (name) VALUES ('Venusaur');
```

### First Normal Form (1NF)

1NF rules:

1. Having a table without a primary key is not allowed
2. Mixing data types in a single column is not allowed
3. Using row order to convey meaning is not allowed
4. Repeating groups of columns is not allowed

---

1. Each table must have a primary key

Let's add a primary key to the table:

| id  | name      |
| --- | --------- |
| 1   | Bulbasaur |
| 2   | Ivysaur   |
| 3   | Venusaur  |

```sql
ALTER TABLE pokemon ADD COLUMN id SERIAL PRIMARY KEY;
```

By adding a primary key, we are also adding a unique constraint to the column. So we can't have two pokemons with the same id.

2. Each entry in a column must be of the same type

Let's add height to the table:

| id  | name      | height |
| --- | --------- | ------ |
| 1   | Bulbasaur | 0.7    |
| 2   | Ivysaur   | 1.0    |
| 3   | Venusaur  | 2.0    |

```sql
ALTER TABLE pokemon ADD COLUMN height FLOAT;
```

We defined the height column as a float. So each entry in the height column must be a float. If we defined it as string, the entries would be like "0.7" or "0.7 ft", which is not allowed.

For the sake of consistency in types, we should also add **sanity checks** in the application layer.

3. Row order is nothing. We must not use it to convey meaning

If we care about the order of the entries, we must define a new column for that. For example, using the id column to order pokemons by their speed is not allowed. We must define a new column for that.

Let's add speed to the table, to list pokemons by their speed:

| id  | name      | height | speed |
| --- | --------- | ------ | ----- |
| 1   | Bulbasaur | 0.7    | 45    |
| 2   | Ivysaur   | 1.0    | 60    |
| 3   | Venusaur  | 2.0    | 80    |

```sql
ALTER TABLE pokemon ADD COLUMN speed INT;
```

4. We must avoid repeating groups of columns

Let's say each pokemon has some inventory items, like potions, pokeballs, barriers, etc. If we add a column for all inventories, we violate the 3th rule of 1NF:

| id  | name      | height | speed | inventory                       |
| --- | --------- | ------ | ----- | ------------------------------- |
| 1   | Bulbasaur | 0.7    | 45    | 2 potion, 3 pokeball, 1 barrier |
| 2   | Ivysaur   | 1.0    | 60    | 8 potion, 2 pokeball            |
| 3   | Venusaur  | 2.0    | 80    | 1 pokeball, 3 barrier           |

To avoid repeating groups of columns, we must define a new column for each inventory item.

| id  | name      | height | speed | potion | pokeball | barrier |
| --- | --------- | ------ | ----- | ------ | -------- | ------- |
| 1   | Bulbasaur | 0.7    | 45    | 2      | 3        | 1       |
| 2   | Ivysaur   | 1.0    | 60    | 8      | 2        | 0       |
| 3   | Venusaur  | 2.0    | 80    | 0      | 1        | 3       |

That is, however, not a good design. We will see better designs later, and it's still violating 1NF, because the `potion`, `pokeball` and `barrier` columns are repeating groups of columns indicating the same thing (inventory).

The way that not violates 1NF is to create a new table for the inventory:

Pokemon table:
| id | name | height | speed |
| --- | --------- | ------ | ----- |
| 1 | Bulbasaur | 0.7 | 45 |
| 2 | Ivysaur | 1.0 | 60 |
| 3 | Venusaur | 2.0 | 80 |

Inventory table:
| id | pokemon_id | inventory | quantity |
| --- | ---------- | --------- | -------- |
| 1 | 1 | potion | 2 |
| 2 | 1 | pokeball | 3 |
| 3 | 1 | barrier | 1 |
| 4 | 2 | potion | 8 |
| 5 | 2 | pokeball | 2 |
| 6 | 3 | pokeball | 1 |
| 7 | 3 | barrier | 3 |

We can remove `id` column from the inventory table, because it is not needed. We can use the `pokemon_id` + `inventory` columns as a composite primary key. The result will be:

| pokemon_id | inventory | quantity |
| ---------- | --------- | -------- |
| 1          | potion    | 2        |
| 1          | pokeball  | 3        |
| 1          | barrier   | 1        |
| 2          | potion    | 8        |
| 2          | pokeball  | 2        |
| 3          | pokeball  | 1        |
| 3          | barrier   | 3        |

```sql
CREATE TABLE inventory (
    pokemon_id INT REFERENCES pokemon(id),
    inventory VARCHAR(255),
    quantity INT,
    PRIMARY KEY (pokemon_id, inventory)
)
```

```sql
CREATE TABLE inventory (
    id SERIAL PRIMARY KEY,
    pokemon_id INT REFERENCES pokemon(id),
    inventory VARCHAR(255),
    quantity INT
)
```

```sql
ALTER TABLE pokemon ADD COLUMN potion INT;
ALTER TABLE pokemon ADD COLUMN pokeball INT;
ALTER TABLE pokemon ADD COLUMN barrier INT;
```

Also we can define a new table for inventories:

| id  | pokemon_id | potion | pokeball | barrier |
| --- | ---------- | ------ | -------- | ------- |
| 1   | 1          | 2      | 3        | 1       |
| 2   | 2          | 8      | 2        | 0       |
| 3   | 3          | 0      | 1        | 3       |

```sql
CREATE TABLE inventory (
    id SERIAL PRIMARY KEY,
    pokemon_id INT REFERENCES pokemon(id),
    potion INT,
    pokeball INT,
    barrier INT
)
```

1NF violations:

- A table without a primary key
- Multiple data types in a single column
- Using row order to convey meaning
- Storing multiple values in a single column

### Second Normal Form (2NF)

By now, we have 2 tables:

Pokemon table:
| id | name | height | speed |
| --- | --------- | ------ | ----- |
| 1 | Bulbasaur | 0.7 | 45 |
| 2 | Ivysaur | 1.0 | 60 |
| 3 | Venusaur | 2.0 | 80 |

Inventory table:
| pokemon_id | inventory | quantity |
| ---------- | --------- | -------- |
| 1 | potion | 2 |
| 1 | pokeball | 3 |
| 1 | barrier | 1 |
| 2 | potion | 8 |
| 2 | pokeball | 2 |
| 3 | pokeball | 1 |
| 3 | barrier | 3 |

We want to add `damage` column. Notice 1NF allows us to add it to both tables, but 2NF prevents us from adding it to the `inventory` table, because it is not dependent on the primary key on the `inventory` table. Why?

Let's add `damage` column to the `inventory` table:

| pokemon_id | inventory | quantity | damage |
| ---------- | --------- | -------- | ------ |
| 1          | potion    | 2        | 10     |
| 1          | pokeball  | 3        | 10     |
| 1          | barrier   | 1        | 10     |
| 2          | potion    | 8        | 20     |
| 2          | pokeball  | 2        | 20     |
| 3          | pokeball  | 1        | 30     |
| 3          | barrier   | 3        | 30     |

In this case, we might encounter with the following problems:

#### Challenges with 1NF:

- Insertion anomaly - inserting a row may cause the insertion of unrelated data
- Deletion anomaly - deleting a row may cause the deletion of unrelated data
- Update anomaly - updating a row may cause the update of unrelated data

Deletion anomaly:
Let's say `Ivyasaur` misses all the inventories, so the inventory table will be:

| pokemon_id | inventory | quantity | damage |
| ---------- | --------- | -------- | ------ |
| 1          | potion    | 2        | 10     |
| 1          | pokeball  | 3        | 10     |
| 1          | barrier   | 1        | 10     |
| 3          | pokeball  | 1        | 30     |
| 3          | barrier   | 3        | 30     |

Now `Ivysaur` has no inventories, but they are still alive and can survive without inventories. They might have some food and drinks in their backpacks, but even if their damage decreases, we can't do that because we no longer have access to `damage` entities for `Ivysaur``.

Insertion anomaly:
Imagine `Ivysaur` finds a new inventory, `barrier`. We can't add it to the table because we don't know the `damage` of the `barrier` for `Ivysaur`. We can't add it to the `pokemon` table because it is not related to the `pokemon` entity.

Update anomaly:

We want to update the damage of `Ivysaur` to 5. We must do that for all the inventories of `Ivysaur`. We miss one of them we ended up with an inconsistent table like this:

| pokemon_id | inventory    | quantity | damage |
| ---------- | ------------ | -------- | ------ |
| 1          | potion       | 2        | 10     |
| 1          | pokeball     | 3        | 10     |
| 1          | barrier      | 1        | 10     |
| **2**      | **potion**   | **8**    | **5**  |
| **2**      | **pokeball** | **2**    | **20** |
| 3          | pokeball     | 1        | 30     |
| 3          | barrier      | 3        | 30     |

To avoid these problems, we should add `damage` column to the `pokemon` table. In other words, 2NF enforces us to add `damage` column to the `pokemon` table:

| id  | name      | height | speed | damage |
| --- | --------- | ------ | ----- | ------ |
| 1   | Bulbasaur | 0.7    | 45    | 10     |
| 2   | Ivysaur   | 1.0    | 60    | 20     |
| 3   | Venusaur  | 2.0    | 80    | 30     |

Now we have 2 tables whose non-key columns are only dependent on the primary key.

some terms to better understand 2NF:

- Primary key - a column or a group of columns that uniquely identifies a row
  Primary key can be a single column or a group of columns(Composite key).
- Composite key - a group of columns that uniquely identifies a row
- Functional dependency - a relationship between two columns, where the value of one column determines the value of another column

- Non-key column - a column that is not part of the primary key
- Non-key attribute - a column that is not part of the primary key

2NF rules:

1NF +:

1. All non-key attributes (columns) in a relation should depend on the entire primary key. so
   1. No transitive dependencies
   2. No partial dependencies

### Third Normal Form (3NF) or Boyce-Codd Normal Form (BCNF)


3NF rules:

So far, we have 2 tables: `pokemon` and `inventory`. After a hard battle, all pokemon were damaged. Let's add `survival` column to predict the survival of the pokemons. The survival is calculated by the following formula:

if `damage` = {10,20,30} then `survival` = Indomitable
if `damage` = {40,50,60} then `survival` = Resilient
if `damage` = {60,70,80} then `survival` = Tenuous
if `damage` = {90, 100} then `survival` = Precarious

Let's see what would happen if we add `survival` column to the `pokemon` table:

Before the battle:

| id  | name      | height | speed | damage | survival    |
| --- | --------- | ------ | ----- | ------ | ----------- |
| 1   | Bulbasaur | 0.7    | 45    | 10     | Indomitable |
| 2   | Ivysaur   | 1.0    | 60    | 40     | Resilient   |
| 3   | Venusaur  | 2.0    | 80    | 60     | Tenuous     |

After the battle, `Venusaur` is really damaged and we need to update the `survival` column:

| id  | name      | height | speed | damage | survival    |
| --- | --------- | ------ | ----- | ------ | ----------- |
| 1   | Bulbasaur | 0.7    | 45    | 40     | Resilient   |
| 2   | Ivysaur   | 1.0    | 60    | 70     | Tenuous     |
| 3   | Venusaur  | 2.0    | 80    | 90     | **Tenuous** |

We would expect `Venusaur` to be `Precarious` after the battle. But we ended up with `Tenuous`. This is because `survival` is not dependent on the entire primary key. `survival` is dependent on `damage` and `damage` is not part of the primary key. We have a transitive dependency between `damage` and `survival`.

Transitive dependency:
Transitive dependency is a relationship between two columns, where the value of one column determines the value of another column, and the value of the second column determines the value of the third column.

In our case, `damage` determines `survival` and `survival` determines `damage`. We have a transitive dependency between `damage` and `survival`.

Partial dependency:
Partial dependency is a relationship between two columns, where the value of one column determines the value of another column, but the value of the second column does not determine the value of the first column.

In our case, `damage` determines `survival` but `survival` does not determine `damage`. We have a partial dependency between `damage` and `survival`.

1NF and 2NF are not enough to avoid transitive, partial and multivalued dependencies. We need to add more tables to avoid these dependencies.



NF3 rules:

1NF + 2NF +:

- There must be no transitive dependencies in the relation.

BCNF and 3NF:

BCNF (Boyce-Codd Normal Form) is a stricter form of normalization than 3NF. A relation that is in BCNF is also in 3NF, but the opposite is not always true.
A relation is in BCNF if and only if every determinant is a candidate key.
A relation is in 3NF if and only if it is in 2NF and has no transitive dependencies.
Both BCNF and 3NF are commonly used in database design.


For our pokeverse database, NF3 enforces us to create a new table called `survival`, instead of adding `survival` column to the `pokemon` table. The `survival` table has the following columns:

| damage | survival    |
| ------ | ----------- |
| 10     | Tenuous     |
| 20     | Tenuous     |
| 30     | Tenuous     |
| 40     | Resilient   |
| 50     | Resilient   |
| 60     | Resilient   |
| 70     | Indomitable |
| 80     | Indomitable |
| 90     | Indomitable |

### Fourth Normal Form (4NF)

By now, we have 3 tables, pokemon, inventory and survival.

| id  | name      | type   | weakness |
| --- | --------- | ------ | -------- |
| 1   | Bulbasaur | Grass  | Psychic  |
| 2   | Bulbasaur | Poison | Fire     |
| 3   | Ivysaur   | Grass  | Flying   |
| 4   | Ivysaur   | Poison | Ice      |

4NF enforces us to create a new table called `type` with the following columns:

| id  | name      | type   |
| --- | --------- | ------ |
| 1   | Bulbasaur | Grass  |
| 2   | Bulbasaur | Poison |
| 3   | Ivysaur   | Grass  |
| 4   | Ivysaur   | Poison |

and a new table called `weakness` with the following columns:

| id  | name      | weakness |
| --- | --------- | -------- |
| 1   | Bulbasaur | Psychic  |
| 2   | Bulbasaur | Fire     |
| 3   | Ivysaur   | Flying   |
| 4   | Ivysaur   | Ice      |

We represent the multi-valued dependency between `pokemon` and `type` and `pokemon` and `weakness` with the following symbols:

{pokemon} ↠ {type}

{pokemon} ↠ {weakness}

4NF rules:
1NF + 2NF + 3NF +:
- No multi-valued dependencies
- Multi-valued dependencies in a table must be multivalued dependencies on the primary key.


### Fifth Normal Form (5NF)
