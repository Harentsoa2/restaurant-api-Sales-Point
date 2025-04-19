-- Delete from child tables first (respecting FK constraints)
DELETE FROM DishOrderStatusHistory;
DELETE FROM OrderStatusHistory;
DELETE FROM DishOrder;
DELETE FROM StockMovement;
DELETE FROM Price;
DELETE FROM Dish_Ingredient;
DELETE FROM "Order";
DELETE FROM Ingredient;
DELETE FROM Dish;



DROP TABLE IF EXISTS DishOrderStatusHistory;
DROP TABLE IF EXISTS DishOrder;
DROP TABLE IF EXISTS OrderStatusHistory;
DROP TABLE IF EXISTS "Order";


DROP TABLE IF EXISTS StockMovement;
DROP TABLE IF EXISTS Price;
DROP TABLE IF EXISTS Dish_Ingredient;

DROP TABLE IF EXISTS Ingredient;
DROP TABLE IF EXISTS Dish;




DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'unit') THEN
CREATE TYPE Unit AS ENUM ('G', 'L', 'U');
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'movement_type') THEN
CREATE TYPE Movement_type AS ENUM ('IN', 'OUT');
END IF;
END $$;


CREATE TABLE IF NOT EXISTS Dish (
    id_dish SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    unit_price DECIMAL(10,2) NOT NULL CHECK (unit_price >= 0)
);


CREATE TABLE IF NOT EXISTS Ingredient (
    id_ingredient SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    unit Unit NOT NULL

);


CREATE TABLE IF NOT EXISTS Dish_Ingredient (
    id_dish INT NOT NULL,
    id_ingredient INT NOT NULL,
    required_quantity DECIMAL(10,2) NOT NULL CHECK (required_quantity > 0),
    unit Unit NOT NULL,
    PRIMARY KEY (id_dish, id_ingredient),  -- Composite Primary Key
    FOREIGN KEY (id_dish) REFERENCES Dish(id_dish) ON DELETE CASCADE,
    FOREIGN KEY (id_ingredient) REFERENCES Ingredient(id_ingredient) ON DELETE CASCADE

 );

CREATE TABLE Price (

    id_price SERIAL PRIMARY KEY,
    id_ingredient INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL CHECK (unit_price >= 0),
    effective_date TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (id_ingredient) REFERENCES Ingredient(id_ingredient) ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS StockMovement (

    id_movement SERIAL PRIMARY KEY,
    id_ingredient INT NOT NULL,
    movement_type Movement_type NOT NULL,
    quantity DECIMAL(10,2) NOT NULL CHECK (quantity > 0),
    unit Unit NOT NULL,
    movement_date TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (id_ingredient) REFERENCES Ingredient(id_ingredient) ON DELETE CASCADE

    );

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'order_status') THEN
CREATE TYPE OrderStatus AS ENUM (
    'CREATED', 'CONFIRMED', 'IN_PROGRESS', 'FINISHED', 'DELIVERED'
);
END IF;
END $$;


CREATE TABLE IF NOT EXISTS "Order" (
    id_order varchar(20) PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);


CREATE TABLE IF NOT EXISTS DishOrder (
    id_dish_order SERIAL unique,
    id_order  varchar(20) not NULL,
    id_dish INT NOT NULL,
    quantity int not null,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id_order, id_dish),
    FOREIGN KEY (id_order) REFERENCES "Order"(id_order) ON DELETE CASCADE,
    FOREIGN KEY (id_dish) REFERENCES Dish(id_dish) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS OrderStatusHistory (

    id_status_history SERIAL,
    id_order varchar(20) not NULL,
    new_status OrderStatus NOT NULL,
    change_date TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id_order, new_status),
    FOREIGN KEY (id_order) REFERENCES "Order"(id_order) ON DELETE CASCADE

);


CREATE TABLE IF NOT EXISTS DishOrderStatusHistory (

    id_status_history SERIAL,
    id_dish_order INT NOT NULL,
    new_status OrderStatus NOT NULL,
    change_date TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id_dish_order, new_status),
    FOREIGN KEY (id_dish_order) REFERENCES DishOrder(id_dish_order) ON DELETE CASCADE

);


