package craps.ingrate.ruleengine.reference.mapper;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.concept.ruleengine.reference.mapper.QueryMapper;
import com.concept.ruleengine.reference.mapper.QueryMapperXlsImpl;

import craps.ingrate.ruleengine.session.context.SessionContext;

public class MapperCrap {
	private static ClassPathXmlApplicationContext ac;

	public static void main(String[] args) {
		SessionContext.alterSession();
		ac = new ClassPathXmlApplicationContext(
				new String[] { "sessionContext-locals.xml" });

		QueryMapper mapper = ac.getBean("xlsQueryMapper",
				QueryMapperXlsImpl.class);

		mapper.relace("_sqlmap_sdpl", "count_bulk_aircraftvisit");
		mapper.parameterized("ETAD", "2015-06-01 13:10:00");

		mapper.getQuery();

	}
}
