/* CREATION */
CREATE TRIGGER after_delete_fmm_flight AFTER DELETE ON "public"."fmm_flight" FOR EACH ROW EXECUTE PROCEDURE notify_after_delete();
CREATE TRIGGER after_insert_fmm_flight AFTER INSERT ON "public"."fmm_flight" FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
CREATE TRIGGER after_update_fmm_aircraft_visit AFTER UPDATE OF aircraft_visit_seq, afskey_arrival, afskey_departure ON "public"."fmm_aircraft_visit" FOR EACH ROW EXECUTE PROCEDURE notify_after_update()


/* DROPING */
DROP TRIGGER after_delete_fmm_flight ON fmm_flight;
DROP TRIGGER after_insert_fmm_flight ON fmm_flight;
DROP TRIGGER after_update_fmm_flight ON fmm_flight;



/* INITIALIZED */
DROP TRIGGER after_update_fmm_aircraft_visit ON fmm_aircraft_visit;
CREATE TRIGGER initialize_fmm_aircraft_visit AFTER UPDATE ON fmm_aircraft_visit FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
update fmm_aircraft_visit set aircraft_visit_seq = aircraft_visit_seq
	where afskey_arrival in (select afskey from fmm_flight where cast(stad as date) = cast(now() as date) )
	   or afskey_departure in (select afskey from fmm_flight where cast(stad as date) = cast(now() as date) );;
		**** tomorrow
		update fmm_aircraft_visit set aircraft_visit_seq = aircraft_visit_seq
		where afskey_arrival in (select afskey from fmm_flight where cast(stad as date) >= (DATE 'tomorrow') )
		   or afskey_departure in (select afskey from fmm_flight where cast(stad as date) >= (DATE 'tomorrow') );
DROP TRIGGER initialize_fmm_aircraft_visit ON fmm_aircraft_visit;
CREATE TRIGGER after_update_fmm_aircraft_visit AFTER UPDATE OF aircraft_visit_seq, afskey_arrival, afskey_departure ON "public"."fmm_aircraft_visit" FOR EACH ROW EXECUTE PROCEDURE notify_after_update()