DROP TRIGGER initiate_table_name ON table_name;

CREATE TRIGGER initiate_table_name
  AFTER UPDATE
  ON table_name
  FOR EACH ROW
  EXECUTE PROCEDURE notify_after_insert();
  
ALTER TABLE table_name DISABLE TRIGGER initiate_table_name;