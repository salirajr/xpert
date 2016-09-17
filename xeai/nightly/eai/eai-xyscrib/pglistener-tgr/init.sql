/*                   */
DROP TRIGGER after_update_fmm_flight ON fmm_flight;
CREATE TRIGGER initialize_fmm_flight AFTER UPDATE ON fmm_flight FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
update fmm_flight set afskey = afskey 
	where cast(stad as date) = cast(now() as date);
		**** tomorrow
		update fmm_flight set afskey = afskey 
		where cast(stad as date) >= (DATE 'tomorrow');
DROP TRIGGER initialize_fmm_flight ON fmm_flight;
CREATE TRIGGER after_update_fmm_flight AFTER UPDATE ON "public"."fmm_flight" FOR EACH ROW EXECUTE PROCEDURE notify_after_update();


/*                   */
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

/*                   */
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

/*                   */
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
CREATE TRIGGER after_update_fmm_aircraft_visit AFTER UPDATE ON "public"."fmm_aircraft_visit" FOR EACH ROW EXECUTE PROCEDURE notify_after_update();
