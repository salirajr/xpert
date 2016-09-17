-- rms_aircraft_stand_parking_op pk: aircraft_visit_seq, aircraft_sp_seq
--
-- aircraft_visit_seq			   not null number(10)
-- aircraft_sp_seq			   not null number(10)
-- stand_code					    varchar2(5)
-- on_block_time					    date
-- off_block_time 				    date
-- est_on_block_time				    date
-- est_off_block_time				    date
-- movement_type					    varchar2(1)
-- gate_code					    varchar2(5)
-- status 					    varchar2(1)
-- aion_conflict					    varchar2(1)
----------------------------------------------------------------
CREATE OR REPLACE TRIGGER TRG_DML_ON_RMS_AC_SP_OP
AFTER INSERT OR UPDATE or DELETE
ON RMS.RMS_AIRCRAFT_STAND_PARKING_OP
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
            '"aircraft_sp_seq" : '||:new.aircraft_sp_seq||','||
            '"stand_code" : "'||:new.stand_code||'",'||
            '"on_block_time" : '||to_char(:new.on_block_time,'yyyy-MM-dd HH24:MI:SS')||','||
            '"off_block_time" : "'||to_char(:new.off_block_time,'yyyy-MM-dd HH24:MI:SS')||'",'||
            '"est_on_block_time" : '||to_char(:new.est_on_block_time,'yyyy-MM-dd HH24:MI:SS')||','||
            '"est_off_block_time" : "'||to_char(:new.est_off_block_time,'yyyy-MM-dd HH24:MI:SS')||'",'||
            '"movement_type" : '||:new.movement_type||','||
            '"gate_code" : "'||:new.gate_code||'",'||
            '"status" : '||:new.status||','||
            '"aion_conflict" : "'||:new.aion_conflict||'"'||
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
            '"aircraft_sp_seq" : '||:new.aircraft_sp_seq||','||
            '"stand_code" : "'||:new.stand_code||'",'||
            '"on_block_time" : '||to_char(:new.on_block_time,'yyyy-MM-dd HH24:MI:SS')||','||
            '"off_block_time" : "'||to_char(:new.off_block_time,'yyyy-MM-dd HH24:MI:SS')||'",'||
            '"est_on_block_time" : '||to_char(:new.est_on_block_time,'yyyy-MM-dd HH24:MI:SS')||','||
            '"est_off_block_time" : "'||to_char(:new.est_off_block_time,'yyyy-MM-dd HH24:MI:SS')||'",'||
            '"movement_type" : '||:new.movement_type||','||
            '"gate_code" : "'||:new.gate_code||'",'||
            '"status" : '||:new.status||','||
            '"aion_conflict" : "'||:new.aion_conflict||'"'||
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
            '"aircraft_sp_seq" : '||:old.aircraft_sp_seq||','||
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