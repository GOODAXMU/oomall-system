CREATE DATABASE IF NOT EXISTS oomall DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

CREATE USER IF NOT EXISTS 'gooda'@'localhost' IDENTIFIED BY '123456';
CREATE USER IF NOT EXISTS 'gooda'@'%' IDENTIFIED BY '123456';

GRANT ALL ON oomall.* TO 'gooda'@'localhost';
GRANT ALL ON oomall.* TO 'gooda'@'%';

FLUSH PRIVILEGES;