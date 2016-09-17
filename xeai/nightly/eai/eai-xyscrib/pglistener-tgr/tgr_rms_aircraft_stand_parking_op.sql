/* CREATION */
CREATE TRIGGER after_delete_rms_aircraft_stand_parking_op AFTER DELETE ON "public"."rms_aircraft_stand_parking_op" FOR EACH ROW EXECUTE PROCEDURE notify_after_delete();
CREATE TRIGGER after_insert_rms_aircraft_stand_parking_op AFTER INSERT ON "public"."rms_aircraft_stand_parking_op" FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
CREATE TRIGGER after_update_rms_aircraft_stand_parking_op AFTER UPDATE ON "public"."rms_aircraft_stand_parking_op" FOR EACH ROW EXECUTE PROCEDURE notify_after_update()


/* DROPING */
DROP TRIGGER after_delete_rms_aircraft_stand_parking_op ON rms_aircraft_stand_parking_op;
DROP TRIGGER after_insert_rms_aircraft_stand_parking_op ON rms_aircraft_stand_parking_op;
DROP TRIGGER after_update_rms_aircraft_stand_parking_op ON rms_aircraft_stand_parking_op;



/* INITIALIZED */
DROP TRIGGER after_update_rms_aircraft_stand_parking_op ON rms_aircraft_stand_parking_op;
CREATE TRIGGER initialize_rms_aircraft_stand_parking_op AFTER UPDATE ON rms_aircraft_stand_parking_op FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
update rms_aircraft_visit_op set aircraft_visit_seq = aircraft_visit_seq 
	where rmskey_departure 
		in (select rmskey from rms_operation where cast(stad as date) = cast(now() as date))
	   or rmskey_arrival in (select rmskey from rms_operation where cast(stad as date) = cast(now() as date));
		**** tomorrow
		update rms_aircraft_visit_op set aircraft_visit_seq = aircraft_visit_seq 
		where rmskey_departure 
			in (select rmskey from rms_operation where cast(stad as date) >= (DATE 'tomorrow'))
		   or rmskey_arrival in (select rmskey from rms_operation where cast(stad as date) >= (DATE 'tomorrow'));
DROP TRIGGER initialize_rms_aircraft_stand_parking_op ON rms_aircraft_stand_parking_op;
CREATE TRIGGER after_update_rms_aircraft_stand_parking_op AFTER UPDATE ON "public"."rms_aircraft_stand_parking_op" FOR EACH ROW EXECUTE PROCEDURE notify_after_update();