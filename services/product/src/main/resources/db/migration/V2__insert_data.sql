-- Inserting data into category table
INSERT INTO category (id, name, description) VALUES (1, 'Electronics', 'Devices and gadgets');
INSERT INTO category (id, name, description) VALUES (2, 'Books', 'Various kinds of books');
INSERT INTO category (id, name, description) VALUES (3, 'Clothing', 'Men and women clothing');
INSERT INTO category (id, name, description) VALUES (4, 'Sports', 'Sporting goods and accessories');
INSERT INTO category (id, name, description) VALUES (5, 'Home Appliances', 'Appliances for home use');

-- Inserting data into product table
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES (1, 'Smartphone', 'Latest model smartphone', 50, 699.99, 1);
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES (2, 'Laptop', 'High-performance laptop', 30, 1299.99, 1);
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES (3, 'Novel', 'Best-selling novel', 100, 19.99, 2);
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES (4, 'T-shirt', '100% cotton T-shirt', 200, 9.99, 3);
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES (5, 'Basketball', 'Official size basketball', 75, 29.99, 4);
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES (6, 'Microwave Oven', 'Compact microwave oven', 40, 89.99, 5);
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES (7, 'Headphones', 'Noise-cancelling headphones', 60, 199.99, 1);
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES (8, 'Cookbook', 'Delicious recipes', 50, 25.00, 2);
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES (9, 'Jeans', 'Denim jeans', 150, 39.99, 3);
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES (10, 'Tennis Racket', 'Lightweight tennis racket', 30, 79.99, 4);
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES (11, 'Vacuum Cleaner', 'High-suction vacuum cleaner', 25, 149.99, 5);
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES (12, 'Tablet', '10-inch screen tablet', 45, 499.99, 1);
