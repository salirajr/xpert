/* CREATION */
CREATE TRIGGER after_delete_rms_aircraft_visit_op AFTER DELETE ON "public"."rms_aircraft_visit_op" FOR EACH ROW EXECUTE PROCEDURE notify_after_delete();
CREATE TRIGGER after_insert_rms_aircraft_visit_op AFTER INSERT ON "public"."rms_aircraft_visit_op" FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
CREATE TRIGGER after_update_rms_aircraft_visit_op AFTER UPDATE ON "public"."rms_aircraft_visit_op" FOR EACH ROW EXECUTE PROCEDURE notify_after_update()


/* DROPING */
DROP TRIGGER after_delete_rms_aircraft_visit_op ON rms_aircraft_visit_op;
DROP TRIGGER after_insert_rms_aircraft_visit_op ON rms_aircraft_visit_op;
DROP TRIGGER after_update_rms_aircraft_visit_op ON rms_aircraft_visit_op;



/* INITIALIZED */
DROP TRIGGER after_update_rms_aircraft_visit_op ON rms_aircraft_visit_op;
CREATE TRIGGER initialize_rms_aircraft_visit_op AFTER UPDATE ON rms_aircraft_visit_op FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
update fmm_aircraft_visit set aircraft_visit_seq = aircraft_visit_seq
	where afskey_arrival in (select afskey from fmm_flight where cast(stad as date) = cast(now() as date) )
	   or afskey_departure in (select afskey from fmm_flight where cast(stad as date) = cast(now() as date) );;
		**** tomorrow
		update fmm_aircraft_visit set aircraft_visit_seq = aircraft_visit_seq
		where afskey_arrival in (select afskey from fmm_flight where cast(stad as date) >= (DATE 'tomorrow') )
		   or afskey_departure in (select afskey from fmm_flight where cast(stad as date) >= (DATE 'tomorrow') );
DROP TRIGGER initialize_rms_aircraft_visit_op ON rms_aircraft_visit_op;
CREATE TRIGGER after_update_rms_aircraft_visit_op AFTER UPDATE ON "public"."rms_aircraft_visit_op" FOR EACH ROW EXECUTE PROCEDURE notify_after_update();