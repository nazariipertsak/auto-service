INSERT INTO orders (id, details) VALUES (1, 'Brakes');
INSERT INTO wares (id, name, price) VALUES (1, 'Oil', 1000);
INSERT INTO wares (id, name, price) VALUES (2, 'Belt', 1300);
INSERT INTO orders_wares (order_id, wares_id) VALUES (1, 1);
INSERT INTO orders_wares (order_id, wares_id) VALUES (1, 2);
INSERT INTO favors (id, master_id, name, price, status) VALUES (1, 1, 'Diagnosis', 500, 'NOT_PAID');
INSERT INTO orders_favors (order_id, favors_id) VALUES (1, 1);
