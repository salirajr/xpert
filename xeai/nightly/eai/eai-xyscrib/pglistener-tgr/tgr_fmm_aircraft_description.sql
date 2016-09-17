/* CREATION */
CREATE TRIGGER after_delete_fmm_iata_aircraft_subtype AFTER DELETE ON "public"."fmm_iata_aircraft_subtype" FOR EACH ROW EXECUTE PROCEDURE notify_after_delete();
CREATE TRIGGER after_insert_fmm_iata_aircraft_subtype AFTER INSERT ON "public"."fmm_iata_aircraft_subtype" FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
CREATE TRIGGER after_update_fmm_iata_aircraft_subtype AFTER UPDATE ON "public"."fmm_iata_aircraft_subtype" FOR EACH ROW EXECUTE PROCEDURE notify_after_update();


/* DROPING */
DROP TRIGGER after_delete_fmm_iata_aircraft_subtype ON fmm_iata_aircraft_subtype;
DROP TRIGGER after_insert_fmm_iata_aircraft_subtype ON fmm_iata_aircraft_subtype;
DROP TRIGGER after_update_fmm_iata_aircraft_subtype ON fmm_iata_aircraft_subtype;



/* INITIALIZED */

DROP TRIGGER after_update_fmm_iata_aircraft_subtype ON fmm_iata_aircraft_subtype;
CREATE TRIGGER initialize_fmm_iata_aircraft_subtype AFTER UPDATE ON fmm_iata_aircraft_subtype FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
update fmm_iata_aircraft_subtype set aircraft_category = aircraft_category;
DROP TRIGGER initialize_fmm_iata_aircraft_subtype ON fmm_iata_aircraft_subtype;
CREATE TRIGGER after_update_fmm_iata_aircraft_subtype AFTER UPDATE ON "public"."fmm_iata_aircraft_subtype" FOR EACH ROW EXECUTE PROCEDURE notify_after_update();
