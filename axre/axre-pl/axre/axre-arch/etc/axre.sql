/* Alter column to have squence default values */
CREATE SEQUENCE NAME_SEQ START 1;
ALTER TABLE RMS_FLIGHT_COUNTER_PL ALTER COUNTER_SEQ SET DEFAULT NEXTVAL('NAME_SEQ'); 



/* FICTIVE STAND */
INSERT INTO RMS_STAND (STAND_CODE, STAND_TYPE, TERMINAL_CODE, MAX_CAPACITY, GATE_CODE, DESCRIPTION) 
	VALUES('W01', 'R', '2E', NULL, NULL, 'FICTIVE STAND');
INSERT INTO RMS_STAND (STAND_CODE, STAND_TYPE, TERMINAL_CODE, MAX_CAPACITY, GATE_CODE, DESCRIPTION) 
	VALUES('W02', 'R', '1C', NULL, NULL, 'FICTIVE STAND');
	
/* FICTIVE CAROUSEL */
INSERT INTO RMS_CAROUSEL (RECLAIM_NO, TERMINAL_CODE) VALUES('W1', NULL);
INSERT INTO RMS_CAROUSEL (RECLAIM_NO, TERMINAL_CODE) VALUES('W2', NULL);

/* FICTIVE CAROUSEL */
INSERT INTO RMS_COUNTER (COUNTER_CODE, DESCRIPTION) VALUES('W1', 'FICTIVE COUNTER');
INSERT INTO RMS_COUNTER (COUNTER_CODE, DESCRIPTION) VALUES('W2', 'FICTIVE COUNTER');