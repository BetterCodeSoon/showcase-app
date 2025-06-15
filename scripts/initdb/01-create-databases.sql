CREATE DATABASE IF NOT EXISTS showcase_dev;
-- ensure auto_increment
--CREATE TABLE app_info (
--    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
--    version VARCHAR(255),
--    description VARCHAR(255)
--    -- other fields...
--);

-- Activate autoincrement after table is generated
-- ALTER TABLE app_info MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY;

-- Add more databases when needed
-- CREATE DATABASE IF NOT EXISTS showcase_master;