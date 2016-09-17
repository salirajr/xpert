DROP TRIGGER after_delete_fmm_flight ON fmm_flight;

CREATE TRIGGER after_delete_fmm_flight
  AFTER DELETE
  ON fmm_flight
  FOR EACH ROW
  EXECUTE PROCEDURE notify_after_delete();