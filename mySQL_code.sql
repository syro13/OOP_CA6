  DROP DATABASE IF EXISTS `rocket_database`;
  CREATE DATABASE `rocket_database`;
  USE `rocket_database`;
  DROP TABLE IF EXISTS `rockets`;
CREATE TABLE rockets (
  rocket_id INT AUTO_INCREMENT PRIMARY KEY,
  rocket_name VARCHAR(50),
  manufacturer VARCHAR(50),
  first_flight DATE,
  payload_capacity INT
);

INSERT INTO rockets (rocket_name, manufacturer, first_flight, payload_capacity)
VALUES
  ('Falcon 9', 'SpaceX', '2010-06-04', 22800),
  ('Atlas V', 'United Launch Alliance', '2002-08-21', 19200),
  ('Delta IV Heavy', 'United Launch Alliance', '2004-12-21', 28500),
  ('Soyuz', 'Roscosmos', '1966-11-28', 6700),
  ('Long March 2F', 'CASC', '1999-01-19', 8300),
  ('Ariane 5', 'Arianespace', '1996-06-04', 21000),
  ('Proton-M', 'Roscosmos', '2001-04-07', 23500),
  ('H-IIA', 'Mitsubishi Heavy Industries', '2001-08-29', 8200),
  ('GSLV Mk III', 'ISRO', '2014-12-18', 4000),
  ('Antares', 'Northrop Grumman', '2013-04-21', 8000);
