CREATE OR REPLACE FUNCTION public.notify_after_insert()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
BEGIN
  PERFORM pg_notify('after_insert', '{"table_name":"' || TG_TABLE_NAME || '", "payload" : { "new_data":' || to_json(NEW) ||'}}' );
  RETURN new;
END;
$function$
