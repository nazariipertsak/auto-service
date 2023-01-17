INSERT INTO "car-owners" (id, full_name) VALUES (1, 'Alexey');
INSERT INTO cars (id, brand, model, number, year) VALUES (1, 'Toyota', 'Camry', '11111', 2016);
INSERT INTO cars (id, brand, model, number, year) VALUES (2, 'Audi', 'Q7', '2222', 2018);
INSERT INTO "car-owners_cars" (car_owner_id, cars_id) VALUES (1, 1);
INSERT INTO "car-owners_cars" (car_owner_id, cars_id) VALUES (1, 2);
INSERT INTO orders (id, car_id, car_owner_id, details) VALUES (1, 1, 1, 'Noise');
INSERT INTO "car-owners_orders" (car_owner_id, orders_id) VALUES (1, 1);
