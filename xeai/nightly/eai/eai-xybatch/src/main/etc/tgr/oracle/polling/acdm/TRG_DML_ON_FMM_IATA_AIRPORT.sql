-- FMM_IATA_AIRPORT iata_airport_code
--
-- iata_airport_code			   not null varchar2(3)
-- shortname				   not null varchar2(8)
-- longname					    varchar2(30)
-- iata_city_code 			   not null varchar2(3)
-- airport_category				    varchar2(1)
-- icao_airport_code				    varchar2(4)
-- iata_country_code				    varchar2(3)
-- time_zone_name 				    varchar2(24)
-- route_arrival					    varchar2(20)
-- route_departure				    varchar2(20)
-- utc						    varchar2(5)
-- dst_indicator					    varchar2(1)
----------------------------------------------------------------
CREATE OR REPLACE TRIGGER TRG_DML_ON_FMM_IATA_AIRPORT
AFTER INSERT OR UPDATE or DELETE
ON AODB.FMM_IATA_AIRPORT
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
vPAYLOAD VARCHAR2(1024);

BEGIN
   

   IF INSERTING THEN 
        
        vPAYLOAD := '{'|| 
        '"service": "after_insert_on_fmm_iata_airport",'||
        '"payload": {'||
            '"iata_airport_code" : "'||:new.iata_airport_code||'",'||
            '"shortname" : "'||:new.shortname||'",'||
            '"longname" : "'||:new.longname||'",'||
            '"iata_city_code" : '||:new.iata_city_code||','||
            '"airport_category" : "'||:new.airport_category||'",'||
            '"icao_airport_code" : "'||:new.icao_airport_code||'",'||
            '"iata_country_code" : '||:new.iata_country_code||','||
            '"time_zone_name" : '||:new.time_zone_name||','||
            '"route_arrival" : '||:new.route_arrival||','||
            '"route_departure" : "'||:new.route_departure||','||
            '"utc" : "'||:new.utc||','||
            '"dst_indicator" : "'||:new.dst_indicator||'"'||
            '},'||
         '"metadata": {'||
              '"timestamp":'|| '"'||to_char(sysdate,'yyyy-MM-dd HH24:MI:SS')||'",'||
              '"timezoneOffset": -420,'||
              '"source": "FMM"'||
           '}'||
        '}' ; 
   ELSIF UPDATING THEN
        vPAYLOAD := '{'|| 
        '"service": "after_update_on_fmm_iata_airport",'||
        '"payload": {'||
            '"iata_airport_code" : "'||:old.iata_airport_code||'",'||
            '"shortname" : "'||:new.shortname||'",'||
            '"longname" : "'||:new.longname||'",'||
            '"iata_city_code" : '||:new.iata_city_code||','||
            '"airport_category" : "'||:new.airport_category||'",'||
            '"icao_airport_code" : "'||:new.icao_airport_code||'",'||
            '"iata_country_code" : '||:new.iata_country_code||','||
            '"time_zone_name" : '||:new.time_zone_name||','||
            '"route_arrival" : '||:new.route_arrival||','||
            '"route_departure" : "'||:new.route_departure||','||
            '"utc" : "'||:new.utc||','||
            '"dst_indicator" : "'||:new.dst_indicator||'"'||
            '},'||
         '"metadata": {'||
              '"timestamp":'|| '"'||to_char(sysdate,'yyyy-MM-dd HH24:MI:SS')||'",'||
              '"timezoneOffset": -420,'||
              '"source": "FMM"'||
           '}'||
        '}' ;
   ELSIF DELETING THEN
        vPAYLOAD := '{'|| 
        '"service": "after_delete_on_fmm_iata_airport",'||
        '"payload": {'||
            '"iata_airport_code" : "'||:old.iata_airport_code||'"'||
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