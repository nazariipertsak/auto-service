INSERT INTO masters (id, full_name) VALUES (1, 'Sergey');
INSERT INTO favors (master_id, name, price, status) VALUES (1, 'Change belt', 1300, 'NOT_PAID');
INSERT INTO favors (master_id, name, price, status) VALUES (1, 'Change oil', 1000, 'PAID');
INSERT INTO masters_favors (master_id, favors_id) VALUES (1, 1);
INSERT INTO masters_favors (master_id, favors_id) VALUES (1, 2);
