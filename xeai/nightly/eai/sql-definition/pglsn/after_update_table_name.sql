-- DROP TRIGGER after_update_fmm_flight ON fmm_flight;

CREATE TRIGGER after_update_fmm_flight
  AFTER UPDATE
  ON fmm_flight
  FOR EACH ROW
  EXECUTE PROCEDURE notify_after_update();