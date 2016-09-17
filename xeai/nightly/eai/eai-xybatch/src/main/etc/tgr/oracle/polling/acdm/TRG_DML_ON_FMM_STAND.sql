-- fmm_stand pk: stand_code
--
-- stand_code				not null varchar2(5)
-- stand_type					varchar2(1)
-- description					varchar2(30)
-- lounge_code					varchar2(5)
-- terminal_code					varchar2(4)
----------------------------------------------------------------
CREATE OR REPLACE TRIGGER TRG_DML_ON_FMM_STAND
AFTER INSERT OR UPDATE or DELETE
ON AODB.FMM_STAND
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
vPAYLOAD VARCHAR2(1024);

BEGIN
   

   IF INSERTING THEN 
        
        vPAYLOAD := '{'|| 
        '"service": "after_insert_on_fmm_stand",'||
        '"payload": {'||
            '"stand_code" : "'||:new.stand_code||'",'||
            '"stand_type" : "'||:new.stand_type||'",'||
            '"description" : "'||:new.description||'",'||
            '"lounge_code" : '||:new.lounge_code||','||
            '"terminal_code" : "'||:new.terminal_code||'"'||
            '},'||
         '"metadata": {'||
              '"timestamp":'|| '"'||to_char(sysdate,'yyyy-MM-dd HH24:MI:SS')||'",'||
              '"timezoneOffset": -420,'||
              '"source": "FMM"'||
           '}'||
        '}' ; 
   ELSIF UPDATING THEN
        vPAYLOAD := '{'|| 
        '"service": "after_update_on_fmm_stand",'||
        '"payload": {'||
            '"stand_code" : "'||:old.stand_code||'",'||
            '"stand_type" : "'||:new.stand_type||'",'||
            '"description" : "'||:new.description||'",'||
            '"lounge_code" : '||:new.lounge_code||','||
            '"terminal_code" : "'||:new.terminal_code||'"'||
            '},'||
         '"metadata": {'||
              '"timestamp":'|| '"'||to_char(sysdate,'yyyy-MM-dd HH24:MI:SS')||'",'||
              '"timezoneOffset": -420,'||
              '"source": "FMM"'||
           '}'||
        '}' ;
   ELSIF DELETING THEN
        vPAYLOAD := '{'|| 
        '"service": "after_delete_on_fmm_stand",'||
        '"payload": {'||
            '"stand_code" : "'||:old.stand_code||'"'||
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