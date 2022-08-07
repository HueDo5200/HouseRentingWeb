
CREATE TABLE `users` (
  `id` int IDENTITY PRIMARY KEY NOT NULL,
  `username` varchar(255) NOT NULL
);
CREATE TABLE `chat_channel` (
  `id` int IDENTITY PRIMARY KEY NOT NULL,
  `date_created` timestamp DEFAULT NULL,
  `channel_name` varchar(255) NOT NULL,
  `customer_id` int DEFAULT NULL,
  `owner_id` int DEFAULT NULL,
  FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`),
  FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
);


CREATE TABLE `message` (
  `id` int NOT NULL IDENTITY PRIMARY KEY,
  `chat_channel_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `date_posted` timestamp DEFAULT NULL,
   FOREIGN KEY (`chat_channel_id`) REFERENCES `chat_channel` (`id`),
 FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);


