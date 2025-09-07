-- Initialize OutScout database
CREATE DATABASE IF NOT EXISTS outscout;
USE outscout;

-- Create user if not exists
CREATE USER IF NOT EXISTS 'outscout'@'%' IDENTIFIED BY 'outscout';
GRANT ALL PRIVILEGES ON outscout.* TO 'outscout'@'%';
FLUSH PRIVILEGES;
