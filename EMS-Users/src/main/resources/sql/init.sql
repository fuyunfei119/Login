CREATE DATABASE IF NOT EXISTS `test`;
USE `test`;

CREATE TABLE IF NOT EXISTS `User` (
    `id` varchar(50) PRIMARY KEY NOT NULL,
    `username` varchar(100),
    `realname` varchar(100),
    `password` varchar(100),
    `sex` varchar(10),
    `status` varchar(10),
    `registerTime` DATE
);
