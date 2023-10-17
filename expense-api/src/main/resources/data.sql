
INSERT INTO expense_categories (expense_category_title) VALUES ('Breakfast');
INSERT INTO expense_categories (expense_category_title) VALUES ('Lunch');
INSERT INTO expense_categories (expense_category_title) VALUES ('Dinner');

INSERT INTO expenses ( expense_category_id, expense_date, expense_amount, comments) VALUES (3, '2023-08-05-00.00.00', 680.00, 'Burger Hub');
INSERT INTO expenses ( expense_category_id, expense_date, expense_amount, comments) VALUES (1, '2023-08-06-00.00.00', 250.00, 'Chacha Chekka');
INSERT INTO expenses ( expense_category_id, expense_date, expense_amount, comments) VALUES (3, '2023-08-06-00.00.00', 500.00, 'My Burger');
INSERT INTO expenses ( expense_category_id, expense_date, expense_amount, comments) VALUES (2, '2023-08-06-00.00.00', 180.00, 'Karachi Birani');
INSERT INTO expenses ( expense_category_id, expense_date, expense_amount, comments) VALUES (3, '2023-08-10-00.00.00', 1250.00, 'Jalal Sons');
INSERT INTO expenses ( expense_category_id, expense_date, expense_amount, comments) VALUES (2, '2023-08-10-00.00.00', 4500.00, 'Butt Karahi');

INSERT INTO expenses ( expense_category_id, expense_date, expense_amount, comments, expense_type, scheduled_type, scheduled_at) VALUES (2, '2023-10-06-00.00.00', 25.00, 'One Time Scheduled', 'S', 'O', '2023-10-06-00.00.00');
INSERT INTO expenses ( expense_category_id, expense_date, expense_amount, comments, expense_type, scheduled_type, scheduled_at) VALUES (2, '2023-10-06-00.00.00', 15.00, 'One Time Scheduled', 'S', 'O', '2023-10-12-00.00.00');
INSERT INTO expenses ( expense_category_id, expense_date, expense_amount, comments, expense_type, scheduled_type, scheduled_at) VALUES (2, '2023-10-06-00.00.00', 10.00, 'One Time Scheduled', 'S', 'O', '2023-10-20-00.00.00');

INSERT INTO expenses ( expense_category_id, expense_date, expense_amount, comments, expense_type, scheduled_type, recurring_type, scheduled_at) VALUES (3, '2023-10-06-00.00.00', 2.00, 'Recurring Daily Scheduled', 'S', 'R', 'D', '2023-10-06-00.00.00');
INSERT INTO expenses ( expense_category_id, expense_date, expense_amount, comments, expense_type, scheduled_type, recurring_type, scheduled_at) VALUES (3, '2023-10-06-00.00.00', 3.00, 'Recurring Weekly Scheduled', 'S', 'R', 'W', '2023-10-06-00.00.00');
INSERT INTO expenses ( expense_category_id, expense_date, expense_amount, comments, expense_type, scheduled_type, recurring_type, scheduled_at) VALUES (3, '2023-10-06-00.00.00', 4.00, 'Recurring Monthly Scheduled', 'S', 'R', 'M', '2023-10-06-00.00.00');
