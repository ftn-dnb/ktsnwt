INSERT INTO `authorities` (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO `authorities` (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO `users` (id, email, activated_account, first_name, last_name, last_password_reset_date, password, username, image_path) VALUES (1,'john@doe.com', true, 'John', 'Doe','2017-10-01 21:58:58', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'john.doe', 'http://res.cloudinary.com/dhibcw0v1/image/upload/v1572021937/bgcbrwpatbahnbdkdboy.png');
INSERT INTO `users` (id, email, activated_account, first_name, last_name, last_password_reset_date, password, username, image_path) VALUES (2,'jane@doe.com', true, 'Jane', 'Doe','2017-09-01 22:40:00', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'jane.doe', 'http://res.cloudinary.com/dhibcw0v1/image/upload/v1572021937/bgcbrwpatbahnbdkdboy.png');

INSERT INTO `user_authority` (user_id, authority_id) VALUES (1, 1);
INSERT INTO `user_authority` (user_id, authority_id) VALUES (2, 2);


INSERT INTO `addresses` (id, city, country, google_api_id, latitude, longitude, postal_code, street_name, street_number) VALUES (1, 'Novi Sad', 'Srbija', 12, 12, 12, '21000', 'Sutjeska', 2);

INSERT INTO `locations` (id, name, address_id) VALUES (1, 'SPENS', 1);

INSERT INTO `halls` (id, name, location_id) VALUES (1, 'Mala sala', 1);
INSERT INTO `halls` (id, name, location_id) VALUES (2, 'Velika sala', 1);

INSERT INTO `sectors` (id, capacity, name, num_columns, num_rows, type, hall_id) VALUES (1, 15, 'left', 5, 3, 1, 1);
INSERT INTO `sectors` (id, capacity, name, num_columns, num_rows, type, hall_id) VALUES (2, 20, 'main', 5, 4, 1, 1);
INSERT INTO `sectors` (id, capacity, name, num_columns, num_rows, type, hall_id) VALUES (3, 10, 'right', 5, 2, 0, 1);
INSERT INTO `sectors` (id, capacity, name, num_columns, num_rows, type, hall_id) VALUES (4, 6, 'right', 2, 3, 0, 2);

INSERT INTO `events` (id, description, end_date, image_path, name, purchase_limit, start_date, tickets_per_user, type, hall_id) VALUES (1, 'Ovo je event', '2020-02-12 00:00:00', 'path', 'Koncert', 2, '2020-02-11 00:00:00', 2, 1, 1);

INSERT INTO `event_days` (id, date, description, name, status, event_id) VALUES (1, '2020-02-11 00:00:00', 'dan 1', 'DAY1', 0, 1);
INSERT INTO `event_days` (id, date, description, name, status, event_id) VALUES (2, '2020-02-12 00:00:00', 'dan 2', 'DAY2', 0, 1);

INSERT INTO `pricings` (id, price, event_day_id, sector_id) VALUES (1, 199, 1, 1);
INSERT INTO `pricings` (id, price, event_day_id, sector_id) VALUES (2, 399, 1, 2);
INSERT INTO `pricings` (id, price, event_day_id, sector_id) VALUES (3, 199, 1, 3);

insert into `tickets` (id, column_num, date_purchased, purchased, row_num, event_day_id, pricing_id, user_id) values (1, 1, '2019-11-24 21:58:58', true, 1, 1, 1, 1);
insert into `tickets` (id, column_num, date_purchased, purchased, row_num, event_day_id, pricing_id, user_id) values (2, 1, '2019-11-24 21:58:58', true, 2, 1, 1, 1);
insert into `tickets` (id, column_num, date_purchased, purchased, row_num, event_day_id, pricing_id, user_id) values (3, 1, '2019-11-24 21:58:58', true, 4, 1, 1, 2);
insert into `tickets` (id, column_num, date_purchased, purchased, row_num, event_day_id, pricing_id, user_id) values (4, 1, '2019-11-24 21:58:58', true, 1, 2, 2, 1);