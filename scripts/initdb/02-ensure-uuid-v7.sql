-------------------------------------------------------
-- This will ensure that UUID v7 is used on the database layer.
--
-- REMINDER: When this script is used the Entity class should use id like this:
--
--     @Id
--     @Column(columnDefinition = "UUID")
--     private UUID id;
--
--
-- By default the script is run only on the database defined in the compose.yaml
-- for the MariaDB container under the MARIADB_DATABASE variable.
--
-- To run the script on other databases as well copy the file and change the database below:
USE showcase_dev;

-- This script changes the table for the "Note.java" entity class.
-- If you want to use this for a different entity change or copy the script accordingly..

-- Use this block to add manual SQL statement (in a tool like DBeaver)
CREATE TABLE note (
  id UUID NOT NULL PRIMARY KEY
);
-- --------------------

DELIMITER $$
-- Then use this block to add manual SQL statement (in a tool like DBeaver)
CREATE TRIGGER before_insert_note
BEFORE INSERT ON note
FOR EACH ROW
BEGIN
  IF NEW.id IS NULL THEN
    SET NEW.id = UUID_v7();
  END IF;
END$$ -- The block ends BEFORE the $$ (do not execute with $$ included in DBeaver)
DELIMITER ;

SELECT 'Table note for entity class Note will now use UUIDv7 for id generation' AS info;