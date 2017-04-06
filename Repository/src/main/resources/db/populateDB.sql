DELETE FROM laptop_images;
DELETE FROM order_to_laptop_join;
DELETE FROM laptops;
DELETE FROM brands;
DELETE FROM user_roles;
DELETE FROM orders;
DELETE FROM users;

ALTER SEQUENCE order_to_laptop_join_id_seq RESTART WITH 1;
ALTER SEQUENCE order_seq RESTART WITH 1;
ALTER SEQUENCE user_seq RESTART WITH 1;
ALTER SEQUENCE brands_seq RESTART WITH 1;
ALTER SEQUENCE image_seq RESTART WITH 1;
ALTER SEQUENCE item_seq RESTART WITH 1;

INSERT INTO brands(short_name, full_name, description)
VALUES
  ('Sony', 'sony-full-name', 'japanese company'),
  ('MSI', NULL, 'chinese company'),
  ('Apple', NULL, 'american'),
  ('Вован-пк', 'Вован юкрейн сомрани инкорплрейтд', 'украина');

INSERT INTO laptops(model, description, price, ram, cpu_frequency, brand_id, stock)
VALUES
  ('sony-model-1', 'good laptop', 2900010, 8, 3.2, 1, 20),
  ('sony-model-2', 'average laptop', 1400092, 4, 2, 1, 10),
  ('msi-1', 'very good laptop',	3403056, 8, 4, 2, 0),
  ('v-104', 'best laptop', 1500000, 16,	8, 4, 2),
  ('v-105', 'the best laptop', 2100000, 32, 16, 4, 1);

INSERT INTO users(name, password)
VALUES ('user', '$2a$10$AKgT/Sw2JmNCUjAYOkoA.Ot35iAW5xHLlAOMwCnKCdX2/Z9GcUAGS'),
  ('admin', '$2a$10$/sM.K6bQCrzoJtnSlEE9m.Gv0c64dHOQLAUlmA4i.LwHxS4BiIfYu'),
  ('admiin', '$2a$10$vDvvpSNenBl18qviUX0FROgceK56W2HUWyHngM6q9SRjAug0QC8D2');

INSERT INTO user_roles(id, name)
VALUES (1,'USER'), (2, 'ADMIN'), (3, 'ADMIN');


INSERT INTO laptop_images (path, laptop_id)
VALUES ('resources/img/laptop_photos/sony-1.jpg', 1),
  ('resources/img/laptop_photos/sony-2.jpg', 2),
  (DEFAULT , 3),
  ('resources/img/laptop_photos/vovan-1.jpg', 4),
  (DEFAULT , 5);

INSERT INTO orders (address, account_id, received)
    VALUES
      ('user-address', 1, '2017-03-27 15:23:02.47206'),
      ('user-other-address', 1, '2017-03-27 15:23:02.47206'),
      ('user-address', 1, '2017-03-27 15:23:02.47206');

INSERT INTO order_to_laptop_join(order_id, laptop_id, quantity)
  VALUES
    (1, 1, 1),
    (2, 1, 1),
    (2, 3, 3),
    (3, 1, 2),
    (1, 4, 3);