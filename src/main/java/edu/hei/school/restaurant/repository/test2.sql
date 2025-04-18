INSERT INTO Ingredient (id_ingredient, name, unit) VALUES
                                                       (1, 'Saucisse',  'G'),
                                                       (2, 'Huile',  'L'),
                                                       (3, 'Oeuf', 'U'),
                                                       (4, 'Pain', 'U');

INSERT INTO Ingredient (id_ingredient, name, unit) VALUES
                                                       (7, 'Sel', 'G'),
                                                       (8, 'Riz', 'G');


INSERT INTO Dish (id_dish, name, unit_price) VALUES
    (1, 'Hot dog', 15000);

INSERT INTO Dish_Ingredient (id_dish, id_ingredient, required_quantity, unit) VALUES
                                                                                  (1, 1, 100, 'G'),
                                                                                  (1, 2, 0.15, 'L'),
                                                                                  (1, 3, 1, 'U'),
                                                                                  (1, 4, 1, 'U');

INSERT INTO Price (id_price, id_ingredient, unit_price, effective_date) VALUES
                                                                            (1, 1, 20, '2025-01-01 00:00'),
                                                                            (2, 2, 10000, '2025-01-01 00:00'),
                                                                            (3, 3, 1000, '2025-01-01 00:00'),
                                                                            (4, 4, 1000, '2025-01-01 00:00');




INSERT INTO StockMovement (id_ingredient, movement_type, quantity, unit, movement_date) VALUES
                                                                                            (3, 'IN', 10, 'U', '2025-02-02 10:00:00'),
                                                                                            (3, 'IN', 10, 'U', '2025-02-03 15:00:00'),
                                                                                            (4, 'IN', 20, 'U', '2025-02-05 16:00:00'),
                                                                                            (4, 'IN', 50, 'U', '2025-02-01 08:00:00');


insert into "Order" (id_order) values (1);




