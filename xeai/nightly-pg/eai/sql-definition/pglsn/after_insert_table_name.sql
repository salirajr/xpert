DROP TRIGGER after_insert_table_name ON table_name;

CREATE TRIGGER after_insert_table_name
  AFTER INSERT
  ON table_name
  FOR EACH ROW
  EXECUTE PROCEDURE notify_after_insert();