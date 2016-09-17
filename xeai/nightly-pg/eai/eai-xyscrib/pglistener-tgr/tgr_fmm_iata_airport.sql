/* CREATION */
CREATE TRIGGER after_delete_fmm_iata_airport AFTER DELETE ON "public"."fmm_iata_airport" FOR EACH ROW EXECUTE PROCEDURE notify_after_delete();
CREATE TRIGGER after_insert_fmm_iata_airport AFTER INSERT ON "public"."fmm_iata_airport" FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
CREATE TRIGGER after_update_fmm_iata_airport AFTER UPDATE ON "public"."fmm_iata_airport" FOR EACH ROW EXECUTE PROCEDURE notify_after_update()


/* DROPING */
DROP TRIGGER after_delete_fmm_iata_airport ON fmm_iata_airport;
DROP TRIGGER after_insert_fmm_iata_airport ON fmm_iata_airport;
DROP TRIGGER after_update_fmm_iata_airport ON fmm_iata_airport;



/* INITIALIZED */

DROP TRIGGER after_update_fmm_iata_airport ON fmm_iata_airport;
CREATE TRIGGER initialize_fmm_iata_airport AFTER UPDATE ON fmm_iata_airport FOR EACH ROW EXECUTE PROCEDURE notify_after_insert();
update fmm_iata_airport set aircraft_reg_no = aircraft_reg_no;
DROP TRIGGER initialize_fmm_iata_airport ON fmm_iata_airport;
CREATE TRIGGER after_update_fmm_iata_airport AFTER UPDATE ON "public"."fmm_iata_airport" FOR EACH ROW EXECUTE PROCEDURE notify_after_update()
