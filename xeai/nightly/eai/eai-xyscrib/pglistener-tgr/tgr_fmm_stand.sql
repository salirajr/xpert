/* CREATION */
CREATE TRIGGER after_delete_fmm_stand AFTER DELETE ON "public"."fmm_stand" FOR EACH ROW EXECUTE PROCEDURE notify_after_delete();
CREATE TRIGGER after_insert_fmm_stand AFTER INSERT ON "public"."fmm_stand" FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
CREATE TRIGGER after_update_fmm_stand AFTER UPDATE ON "public"."fmm_stand" FOR EACH ROW EXECUTE PROCEDURE notify_after_update()


/* DROPING */
DROP TRIGGER after_delete_fmm_stand ON fmm_stand;
DROP TRIGGER after_insert_fmm_stand ON fmm_stand;
DROP TRIGGER after_update_fmm_stand ON fmm_stand;



/* INITIALIZED */
DROP TRIGGER after_update_fmm_flight ON fmm_flight;
CREATE TRIGGER initialize_fmm_flight AFTER UPDATE ON fmm_flight FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
update fmm_flight set afskey = afskey 
	where cast(stad as date) = cast(now() as date);
		**** tomorrow
		update fmm_flight set afskey = afskey 
		where cast(stad as date) > (DATE 'tomorrow');
DROP TRIGGER initialize_fmm_flight ON fmm_flight;
CREATE TRIGGER after_update_fmm_flight AFTER UPDATE ON "public"."fmm_flight" FOR EACH ROW EXECUTE PROCEDURE notify_after_update();
