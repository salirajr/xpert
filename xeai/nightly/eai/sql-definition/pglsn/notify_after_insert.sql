DROP FUNCTION notify_after_insert();
CREATE OR REPLACE FUNCTION notify_after_insert()
  RETURNS trigger AS
$BODY$
DECLARE
BEGIN
  PERFORM pg_notify('after_insert', '{"table_name":"' || TG_TABLE_NAME || '", "payload" : { "new_data":' || to_json(NEW) ||'}}' );
  RETURN new;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;