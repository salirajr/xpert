DROP FUNCTION notify_after_delete();
CREATE OR REPLACE FUNCTION notify_after_delete()
  RETURNS trigger AS
$BODY$
DECLARE
BEGIN
  PERFORM pg_notify('after_delete', '{"table_name":"' || TG_TABLE_NAME || '", "payload" : {"old_data":'|| to_json(OLD) ||'}}' );
  RETURN new;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;