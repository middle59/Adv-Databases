-- create database
DROP DATABASE IF EXISTS project;
CREATE DATABASE project;

-- select database
USE project;

-- create tables
CREATE TABLE zomato_table
(
  res_id            INT             PRIMARY KEY   AUTO_INCREMENT,
  res_name          VARCHAR(50)     NOT NULL      UNIQUE,
  res_address       VARCHAR(100)    NOT NULL,
  res_lat           DOUBLE          NOT NULL,
  res_lon           DOUBLE          NOT NULL,
  res_avg_rating    FLOAT           NOT NULL
);