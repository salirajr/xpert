DROP FUNCTION notify_after_update();
CREATE OR REPLACE FUNCTION notify_after_update()
  RETURNS trigger AS
$BODY$
DECLARE
BEGIN
  IF ''||to_json(OLD) not like ''||to_json(NEW) THEN
	PERFORM pg_notify('after_update', '{"table_name":"' || TG_TABLE_NAME || '", "payload" : { "old_data":'|| to_json(OLD) || ',"new_data":' || to_json(NEW) ||'}}' );
  END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
