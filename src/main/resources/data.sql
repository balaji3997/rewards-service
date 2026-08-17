-- =========================================================
-- RETAIL REWARDS - H2 TEST DATA
-- =========================================================

INSERT INTO customers (customer_id, customer_name, email) VALUES ('CUST00001', 'Amit', 'amit@service.com');
INSERT INTO customers (customer_id, customer_name, email) VALUES ('CUST00002', 'Birla', 'birla@mail.com');

INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount, purchase_desc) VALUES ('T001', 'CUST00001', '2026-05-05 10:15:00', 120.00, 'Laptop accessories');
INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount, purchase_desc) VALUES ('T002', 'CUST00001', '2026-06-18 14:30:00', 80.00, 'Home supplies');
INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount, purchase_desc) VALUES ('T003', 'CUST00001', '2026-07-07 11:00:00', 150.00, 'Electronics');
INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount, purchase_desc) VALUES ('T004', 'CUST00001', '2026-07-21 16:45:00', 40.00, 'Groceries');
INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount, purchase_desc) VALUES ('T005', 'CUST00001', '2026-08-03 09:30:00', 200.00, 'Furniture');
INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount, purchase_desc) VALUES ('T006', 'CUST00001', '2026-06-19 18:20:00', 70.00, 'Books');
INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount, purchase_desc) VALUES ('T007', 'CUST00002', '2026-07-10 10:00:00', 100.00, 'Clothing');
INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount, purchase_desc) VALUES ('T008', 'CUST00002', '2026-07-22 13:15:00', 49.00, 'Food');
INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount, purchase_desc) VALUES ('T009', 'CUST00002', '2026-06-12 15:30:00', 120.00, 'Appliances');
INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount, purchase_desc) VALUES ('T010', 'CUST00002', '2026-04-08 11:45:00', 55.00, 'Books');
INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount, purchase_desc) VALUES ('T011', 'CUST00002', '2026-08-25 17:00:00', 250.00, 'Mobile phone');
