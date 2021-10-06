DELETE FROM `scooter`.`users_to_roles`;
DELETE FROM `scooter`.`rentals`;
DELETE FROM `scooter`.`roles`;
DELETE FROM `scooter`.`users`;
DELETE FROM `scooter`.`discounts`;
DELETE FROM `scooter`.`tariffs`;
DELETE FROM `scooter`.`vehicles`;
DELETE FROM `scooter`.`rental_offices`;
DELETE FROM `scooter`.`vehicle_models`;
DELETE FROM `scooter`.`locations`;


INSERT INTO `scooter`.`vehicle_models` (`id`, `name`) VALUES ('1', 'Volten SLF 350W');
INSERT INTO `scooter`.`vehicle_models` (`id`, `name`) VALUES ('2', 'Eltreco XT-850 500W');
INSERT INTO `scooter`.`vehicle_models` (`id`, `name`) VALUES ('3', 'Volteco Cyber 500W');

INSERT INTO `scooter`.`locations` (`id`, `city`, `district`, `street`) VALUES ('1', '0', '0', '0');
INSERT INTO `scooter`.`locations` (`id`, `city`, `district`, `street`) VALUES ('2', '0', '1', '1');
INSERT INTO `scooter`.`locations` (`id`, `city`, `street`) VALUES ('3', '1', '2');

INSERT INTO `scooter`.`rental_offices` (`id`, `name`, `location_id`, `latitude`, `longtitude`) VALUES ('1', 'Central №1', '1', '53.925070', '27.663240');
INSERT INTO `scooter`.`rental_offices` (`id`, `name`, `location_id`, `latitude`, `longtitude`) VALUES ('2', 'Victorias №2', '2', '53.921903', '27.526627');
INSERT INTO `scooter`.`rental_offices` (`id`, `name`, `location_id`, `latitude`, `longtitude`) VALUES ('3', 'Lenina', '3', '53.095547', '25.319279');

INSERT INTO `scooter`.`vehicles` (`id`, `vehicle_model_id`, `current_rental_office_id`, `registration_number`, `registration_date`, `charge_level`, `technical_condition_level`) VALUES ('1', '1', '1', 'sn-1234', '2020-01-01', '4', '4');
INSERT INTO `scooter`.`vehicles` (`id`, `vehicle_model_id`, `current_rental_office_id`, `registration_number`, `registration_date`, `charge_level`, `technical_condition_level`) VALUES ('2', '1', '1', 'sn-2234', '2020-01-01', '4', '4');
INSERT INTO `scooter`.`vehicles` (`id`, `vehicle_model_id`, `current_rental_office_id`, `registration_number`, `registration_date`, `charge_level`, `technical_condition_level`) VALUES ('3', '2', '1', 'sn-3234', '2020-01-01', '4', '4');
INSERT INTO `scooter`.`vehicles` (`id`, `vehicle_model_id`, `current_rental_office_id`, `registration_number`, `registration_date`, `charge_level`, `technical_condition_level`) VALUES ('4', '2', '2', 'sn-4234', '2020-01-05', '2', '3');
INSERT INTO `scooter`.`vehicles` (`id`, `vehicle_model_id`, `current_rental_office_id`, `registration_number`, `registration_date`, `charge_level`, `technical_condition_level`) VALUES ('5', '3', '2', 'sn-5234', '2020-01-05', '1', '4');
INSERT INTO `scooter`.`vehicles` (`id`, `vehicle_model_id`, `current_rental_office_id`, `registration_number`, `registration_date`, `charge_level`, `technical_condition_level`) VALUES ('6', '3', '3', 'sn-6234', '2020-05-01', '4', '2');

INSERT INTO `scooter`.`tariffs` (`id`, `name`, `description`, `timely_price`, `per_unit_time`) VALUES ('1', 'Minutely', 'Minutely tariff', '.2', '0');
INSERT INTO `scooter`.`tariffs` (`id`, `name`, `description`, `timely_price`, `per_unit_time`) VALUES ('2', 'Hourly', 'Hourly tariff', '10', '1');
INSERT INTO `scooter`.`tariffs` (`id`, `name`, `description`, `timely_price`, `per_unit_time`) VALUES ('3', 'Daily', 'Daily tariff', '150', '2');

INSERT INTO `scooter`.`discounts` (`id`, `name`, `description`, `discount_percentage`) VALUE ('1', 'Every 5th day', 'Every the 5th day gratis', '30');
INSERT INTO `scooter`.`discounts` (`id`, `name`, `description`, `discount_percentage`) VALUES ('2', 'Holidays', 'Every holidays', '15');

INSERT INTO `scooter`.`users` (`id`, `login`, `password`, `name`, `email`, `address`) VALUES ('1', 'user', '$2y$12$xkgnxUUPtSSdXGiwZtBxWeBmI9dTCBZL50a5NEGZPKDE1hZ94RxXS', 'user', 'user@gmail.com', 'Slonim');
INSERT INTO `scooter`.`users` (`id`, `login`, `password`, `name`, `email`, `address`) VALUES ('2', 'admin', '$2y$12$MG/zQhRu8GHH1mWmTFy2IuxxcXARzzQqI9Qp30WWhG/iKlmM7/1Si', 'admin', 'admin@gmail.com', 'Minsk');

INSERT INTO `scooter`.`roles` (`id`, `role`) VALUES ('1', 'ADMIN');
INSERT INTO `scooter`.`roles` (`id`, `role`) VALUES ('2', 'USER');

INSERT INTO `scooter`.`users_to_roles` (`user_id`, `role_id`) VALUES ('1', '2');
INSERT INTO `scooter`.`users_to_roles` (`user_id`, `role_id`) VALUES ('2', '1');
INSERT INTO `scooter`.`users_to_roles` (`user_id`, `role_id`) VALUES ('2', '2');


INSERT INTO `scooter`.`rentals` (`id`, `user_id`, `vehicle_id`, `status`, `start_rental_date_time`, `start_rental_office_id`, `tariff_id`, `discount_id`, `finish_rental_date_time`, `finish_rental_office_id`, `discount_amount`, `total_cost`) VALUES ('1', '1', '1', '2', '2020-02-01 12:48:00', '1', '1', '1', '2020-02-01 12:58:00', '1', '0', '10');

