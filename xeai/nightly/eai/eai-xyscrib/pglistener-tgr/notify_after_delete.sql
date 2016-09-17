CREATE OR REPLACE FUNCTION public.notify_after_delete()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
BEGIN
  PERFORM pg_notify('after_delete', '{"table_name":"' || TG_TABLE_NAME || '", "payload" : {"old_data":'|| to_json(OLD) ||'}}' );
  RETURN new;
END;
$function$