-- rms_aircraft_visit_op pk: aircraft_visit_seq
--
-- aircraft_visit_seq			   not null number(9)
-- afskey_arrival 				    varchar2(12)
-- afskey_departure				    varchar2(12)
-- rmskey_arrival 				    varchar2(12)
-- rmskey_departure				    varchar2(12)
----------------------------------------------------------------
CREATE OR REPLACE TRIGGER TRG_DML_ON_RMS_AC_VISIT_OP
AFTER INSERT OR UPDATE or DELETE
ON RMS.RMS_AIRCRAFT_VISIT_OP
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
vPAYLOAD VARCHAR2(1024);

BEGIN
   

   IF INSERTING THEN 
        
        vPAYLOAD := '{'|| 
        '"service": "after_insert_on_rms_aircraft_stand_parking_op",'||
        '"payload": {'||
            '"aircraft_visit_seq" : '||:new.aircraft_visit_seq||','||
            '"afskey_arrival" : "'||:new.afskey_arrival||'",'||
            '"afskey_departure" : '||:new.afskey_departure||','||
            '"rmskey_arrival" : '||:new.rmskey_arrival||','||
            '"rmskey_departure" : "'||:new.rmskey_departure||'"'||
            '},'||
         '"metadata": {'||
              '"timestamp":'|| '"'||to_char(sysdate,'yyyy-MM-dd HH24:MI:SS')||'",'||
              '"timezoneOffset": -420,'||
              '"source": "FMM"'||
           '}'||
        '}' ; 
   ELSIF UPDATING THEN
        vPAYLOAD := '{'|| 
        '"service": "after_update_on_rms_aircraft_stand_parking_op",'||
        '"payload": {'||
            '"aircraft_visit_seq" : '||:old.aircraft_visit_seq||','||
            '"afskey_arrival" : "'||:new.afskey_arrival||'",'||
            '"afskey_departure" : '||:new.afskey_departure||','||
            '"rmskey_arrival" : '||:new.rmskey_arrival||','||
            '"rmskey_departure" : "'||:new.rmskey_departure||'"'||
            '},'||
         '"metadata": {'||
              '"timestamp":'|| '"'||to_char(sysdate,'yyyy-MM-dd HH24:MI:SS')||'",'||
              '"timezoneOffset": -420,'||
              '"source": "FMM"'||
           '}'||
        '}' ;
   ELSIF DELETING THEN
        vPAYLOAD := '{'|| 
        '"service": "after_delete_on_rms_aircraft_stand_parking_op",'||
        '"payload": {'||
            '"aircraft_visit_seq" : '||:old.aircraft_visit_seq||','||
            '},'||
         '"metadata": {'||
              '"timestamp":'|| '"'||to_char(sysdate,'yyyy-MM-dd HH24:MI:SS')||'",'||
              '"timezoneOffset": -420,'||
              '"source": "FMM"'||
           '}'||
        '}' ;
   END IF;   
   
   BEGIN
            INSERT INTO TEMP_POOLING (MSG_TIMESTAMPS,PAYLOAD)
            VALUES (sysdate,vPAYLOAD);
            COMMIT;
        EXCEPTION
        WHEN OTHERS THEN NULL;
   END;

   EXCEPTION
     WHEN OTHERS THEN
       -- Consider logging the error and then re-raise
       RAISE;
END ;
/