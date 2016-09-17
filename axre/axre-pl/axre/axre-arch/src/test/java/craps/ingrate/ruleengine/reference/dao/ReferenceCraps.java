package craps.ingrate.ruleengine.reference.dao;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.concept.ruleengine.model.EphemeralFact;
import com.concept.ruleengine.reference.dao.ReferenceDAO;
import com.concept.ruleengine.reference.dao.ReferenceDAOImpl;
import com.concept.ruleengine.reference.mapper.QueryMapper;
import com.concept.ruleengine.reference.mapper.QueryMapperXlsImpl;

import craps.ingrate.ruleengine.session.context.SessionContext;

public class ReferenceCraps {
	private static ClassPathXmlApplicationContext ac;

	public static void main(String[] args) {
		SessionContext.alterSession();
		ac = new ClassPathXmlApplicationContext(new String[] { "sessionContext-locals.xml" });

		ReferenceDAO dao = ac.getBean("rmsReference", ReferenceDAOImpl.class);
		QueryMapper mapper = ac.getBean("xlsQueryMapper", QueryMapperXlsImpl.class);

		// mapper.relace();
		//
		// mapper.relace("_sqlmap_sd","retrieve_standconflictoperation");
		//
		// mapper.parameterized("STAND_CODE", "D31");
		// mapper.parameterized("AIRCRAFT_VISIT_SEQ", 34847 );
		// Timestamp estOnBlockTime =
		// StringUtil.toTimestamp("2015-05-11 09:02:00", "yyyy-MM-dd HH:mm:ss");
		//
		// mapper.parameterized(
		// "EST_ON_BLOCK_TIME_WITH_TIMEBUFFER",
		// DateUtil.toString(
		// AirportMath.leapTimestamp(estOnBlockTime, 1),
		// "yyyy-MM-dd HH:mm:ss"));
		//
		// Timestamp estOffBlockTime =
		// StringUtil.toTimestamp("2015-05-11 10:12:00", "yyyy-MM-dd HH:mm:ss");
		// mapper.parameterized(
		// "EST_OFF_BLOCK_TIME_WITH_TIMEBUFFER",
		// DateUtil.toString(
		// AirportMath.leapTimestamp(estOffBlockTime, 1),
		// "yyyy-MM-dd HH:mm:ss"));
		//
		// Map<String, Object> map = dao.getMap(mapper.getQuery());
		//
		 EphemeralFact ef = new EphemeralFact();
		// ef.putMap("standconflictoperation", map);
		//
		// // System.out.println(map);
		// System.out.println(ef.getMapItemInt("standconflictoperation",
		// "count"));
		//
		// mapper.relace("_sqlmap_sd", "retrieve_standinoperation");
		// // STAND_CODE, INOP_START_TIME, INOP_END_TIME
		// mapper.parameterized("STAND_CODE", "D31");
		//
		// mapper.parameterized("INOP_START_TIME", "2015-05-11 09:02:00");
		//
		// mapper.parameterized("INOP_END_TIME", "2015-05-11 10:12:00");
		//
		// EphemeralMantle em = new EphemeralMantle();
		// EphemeralFact ef = new EphemeralFact();
		// em.setFacts(ef);
		// em.facts.put("standinoperation", dao.get(mapper.getQuery()));
		//
		// System.out.println(em.facts.getInt("standinoperation"));

		// mapper.relace("_sqlmap_sd", "retrieve_standmaxcapacity");
		// mapper.parameterized("STAND_CODE", "D41");
		
		mapper.relace("_sqlmap_sd","retrieve_iatasubtypewingspan");
		mapper.parameterized("IATA_SUBTYPE_CODE", "763");
		
		ef.put("numerics", dao.getNumeric(mapper.getQuery()));
		
		double numerics = ef.getNumeric("numerics");
		
		System.out.println(numerics);
		
		

	}
}
