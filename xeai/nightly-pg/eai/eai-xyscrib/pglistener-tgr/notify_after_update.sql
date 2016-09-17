CREATE OR REPLACE FUNCTION public.notify_after_update()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$DECLARE
BEGIN
  IF ''||to_json(OLD) not like ''||to_json(NEW) THEN
	PERFORM pg_notify('after_update', '{"table_name":"' || TG_TABLE_NAME || '", "payload" : { "old_data":'|| to_json(OLD) || ',"new_data":' || to_json(NEW) ||'}}' );
  END IF;
  RETURN new;
END;$function$
