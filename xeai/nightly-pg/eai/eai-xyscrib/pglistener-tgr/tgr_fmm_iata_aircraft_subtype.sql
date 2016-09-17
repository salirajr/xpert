/* CREATION */
CREATE TRIGGER after_delete_fmm_aircraft_description AFTER DELETE ON "public"."fmm_aircraft_description" FOR EACH ROW EXECUTE PROCEDURE notify_after_delete();
CREATE TRIGGER after_insert_fmm_aircraft_description AFTER INSERT ON "public"."fmm_aircraft_description" FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
CREATE TRIGGER after_update_fmm_aircraft_description AFTER UPDATE ON "public"."fmm_aircraft_description" FOR EACH ROW EXECUTE PROCEDURE notify_after_update()


/* DROPING */
DROP TRIGGER after_delete_fmm_aircraft_description ON fmm_aircraft_description;
DROP TRIGGER after_insert_fmm_aircraft_description ON fmm_aircraft_description;
DROP TRIGGER after_update_fmm_aircraft_description ON fmm_aircraft_description;



/* INITIALIZED */

DROP TRIGGER after_update_fmm_aircraft_description ON fmm_aircraft_description;
CREATE TRIGGER initialize_fmm_aircraft_description AFTER UPDATE ON fmm_aircraft_description FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
update fmm_aircraft_description set aircraft_reg_no = aircraft_reg_no;
DROP TRIGGER initialize_fmm_aircraft_description ON fmm_aircraft_description;
CREATE TRIGGER after_update_fmm_aircraft_description AFTER UPDATE ON "public"."fmm_aircraft_description" FOR EACH ROW EXECUTE PROCEDURE notify_after_update()
