CREATE TABLE `member`
(
  `member_no` SERIAL PRIMARY KEY,
  `name` varchar (128) NOT NULL,
  `user_id` varchar (40) NOT NULL,
  `password` varchar (100) NOT NULL,
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
);