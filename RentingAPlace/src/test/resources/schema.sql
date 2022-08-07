CREATE TABLE `amenity` (
	`id` INT IDENTITY PRIMARY KEY NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`description` VARCHAR(2000)
);

CREATE TABLE `location` (
	`id` INT IDENTITY PRIMARY KEY NOT NULL,
	`state` varchar(255) NOT NULL,
	`city` varchar(255) NOT NULL,
	`details` varchar(2000) NOT NULL,
	`description` varchar(255) DEFAULT NULL
);


CREATE TABLE `role` (
	`id` int IDENTITY PRIMARY KEY NOT NULL,
	`name` varchar(255) DEFAULT NULL
);

CREATE TABLE `users` (
	`id` int IDENTITY PRIMARY KEY NOT NULL,
	`phone_number` varchar(255) NOT NULL,
	`username` varchar(255) NOT NULL,
	`password` varchar(255) NOT NULL,
	`email` varchar(255) DEFAULT NULL,
	`first_name` varchar(255) DEFAULT NULL,
	`last_name` varchar(255) DEFAULT NULL,
	`role_id` int DEFAULT NULL,
	FOREIGN KEY(role_id) REFERENCES role(id)
);

CREATE TABLE `property_type` (
	`id` int IDENTITY PRIMARY KEY  NOT NULL,
	`name` varchar(255) NOT NULL
);

CREATE TABLE `property` (
	`id` int IDENTITY PRIMARY KEY NOT NULL,
	`name` varchar(255) NOT NULL,
	`description` varchar(2000) NOT NULL,
	`avg_rating` decimal(2, 1) DEFAULT '0.0',
	`rating_num` int DEFAULT '0',
	`price_by_night` int DEFAULT '0',
	`bedroom` int DEFAULT '1',
	`bathroom` int DEFAULT '1',
	`kitchen` int DEFAULT '1',
	`user_id` INTEGER DEFAULT NULL,
	`location_id` INTEGER DEFAULT NULL,
	`property_type_id` INTEGER DEFAULT NULL,
	FOREIGN KEY (location_id) REFERENCES location(id),
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (property_type_id) REFERENCES property_type(id)

);

CREATE TABLE `property_amenity` (
	`id` int IDENTITY PRIMARY KEY NOT NULL,
	`amenity_id` int DEFAULT NULL,
	`property_id` int DEFAULT NULL,
	FOREIGN KEY(amenity_id) REFERENCES amenity(id),
	FOREIGN KEY(property_id) REFERENCES property(id)
);

CREATE TABLE `property_image` (
	`id` int IDENTITY PRIMARY KEY NOT NULL,
	`name` varchar(255) NOT NULL,
	`property_id` int DEFAULT NULL,
        FOREIGN KEY (property_id) REFERENCES property(id)
);

CREATE TABLE `reservation` (
	`id` int IDENTITY PRIMARY KEY NOT NULL,
	`user_id` int DEFAULT NULL,
	`property_id` int DEFAULT NULL,
	`start_date` date NOT NULL,
	`end_date` date NOT NULL,
	`total` double DEFAULT '0',
	`status` int NOT NULL,
	FOREIGN KEY(user_id) REFERENCES users(id),
	FOREIGN KEY(property_id) REFERENCES property(id)
);


CREATE TABLE `user_rating` (
	`id` int IDENTITY PRIMARY KEY  NOT NULL,
	`user_id` int DEFAULT NULL,
	`property_id` int DEFAULT NULL,
	`rating_num` decimal(2, 1) DEFAULT NULL,
	FOREIGN KEY(property_id) REFERENCES property(id),
	FOREIGN KEY(user_id) REFERENCES users(id)
);


