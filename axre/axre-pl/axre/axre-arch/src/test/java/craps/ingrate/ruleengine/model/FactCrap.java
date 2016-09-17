package craps.ingrate.ruleengine.model;

import java.util.ArrayList;
import java.util.List;

import com.concept.ruleengine.model.EphemeralFact;

public class FactCrap {
	public static void main(String[] args) {

		EphemeralFact ef = new EphemeralFact();

		ef.put("i", 1);

		System.out.println(ef.getInt("i") == 1);

		ef.put("i", ef.getInt("i") + 1);
		
		System.out.println(ef.get("i"));
		
		ef.remove("i","j");
		
		System.out.println(ef.get("i"));
		
		List<Object> ls = new ArrayList<Object>();
		
		
		ef.putList("k", ls);
		System.out.println(ef.getListItemAt("k", 2));
		
	}
}
