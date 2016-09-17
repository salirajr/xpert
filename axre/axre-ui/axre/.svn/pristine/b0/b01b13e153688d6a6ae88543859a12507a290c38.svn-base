/** 
 * Modification History
 * Date         Modified By             Comments
 * **************************************************************************************
 * 20112014		Jovi Rengga Salira		Initial Creation - DAMN, spend me 5hs to Finish!!
 * 										*.xls File Specification is specified by the sample I created!,
 * 										Please followed as it is, as the retrieving logic based 
 * 										on FIX specification(ref: dt-source-alt/_xdt__template_.xls).
 * 24112014		Jovi Rengga Salira		Add configurable logic, refer to resources/configs/app/decisiontable.properties,
 * 										this logic enable to move the pivot of meta info and decision table contains.
 * 										very strict!, please followed the template!.
 * 										template ref: resources/assets/template
 * 24112014		Jovi Rengga Salira		Add re-use Decision table, only refresh the artifact of Rule Key and Decision Key,
 * 										Current logic is expensive on Instantiation. 
 * 										ref: relace()
 * **************************************************************************************
 */
package com.concept.ruleengine.dt.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.concept.ruleengine.archcomp.ConfigComponent;
import com.concept.ruleengine.archcomp.ConstantComponent;
import com.concept.ruleengine.archcomp.exception.ApplicationRuntimeException;
import com.concept.ruleengine.common.util.CustomUtil;
import com.concept.ruleengine.common.util.StringUtil;

public class DecisionTableXlsImpl implements DecisionTable {

	private static Logger logger = LogManager.getLogger(DecisionTableXlsImpl.class);

	public static final String nvlPrefKeyPattern = ConfigComponent.CFG_DT.getString("dt.nvl.keypattern");

	private Workbook workbook;
	private Sheet sheet;
	private Row row;

	private List<String> ruleKey;
	private List<String> decisionKey;
	private List<String> tableKey;

	private String referenceName;
	private HashMap<String, String> rulesArtifact;
	private HashMap<String, String> decision;

	private int xPointer, yPointer, actPivot;

	// JS, add after configurable logic imply!
	private String actPivotValue, temp;

	// Temporary variable, defined to keep the code simpler and controlled
	// allocated variable, uses over by over again.
	private int nRows, i, j, nFit;
	private boolean isOn;

	private String suffix = ConstantComponent.SUFFIX_XLS;

	private String baseLocation = ConstantComponent.DOMAIN_LOCATION_DT_XLS;

	public DecisionTableXlsImpl() {
		super();
	}

	public DecisionTableXlsImpl(String name) {
		super();
		initiate(name);
	}

	public void initiate(String name) {

		// Initiate
		this.referenceName = name;

		/*
		 * JS, Initial Pointer of retrieving information in .xls, Placed on
		 * Cell: C4(x:2, y:3), location of Decision Table Name; xPointer
		 * represented in Letter(A,B,C, ...) in .xls, X-AXIS; yPointer
		 * represented in Letter(1,2,3, ...) in .xls, Y-AXIS;
		 */
		xPointer = Integer.valueOf(ConfigComponent.CFG_DT.getString("dt.xls.metainfo.pointer.x"));
		yPointer = Integer.valueOf(ConfigComponent.CFG_DT.getString("dt.xls.metainfo.pointer.y"));

		workbook = mantledFIS(referenceName);
		// [1st]Is a SOP, where I purposed named it METAINFO in every
		// Decision Table
		// Reference .xls File!!
		sheet = workbook.getSheet(ConfigComponent.CFG_DT.getString("dt.xls.metainfo.name"));

		// [2nd] Extracting Decision Rule Name! -D2, sheet named: METAINFO
		String temp = sheet.getRow(yPointer++).getCell(xPointer).getStringCellValue();

		if (!temp.equals(referenceName))
			throw new ApplicationRuntimeException("XYBASE Airport Rule Engine Exception, Invalid Decision Table[name: "
					+ temp + " instead of" + referenceName + "]");

		// [3rd] Extracting Rule Key, Decision Key, as a reference! -D3, -D4,
		// METAINFO
		String ruleKeyRaw = sheet.getRow(yPointer++).getCell(xPointer).getStringCellValue();
		ruleKey = StringUtil.toList(ruleKeyRaw, StringUtil.CustomRegex.COMMA);
		String decisionKeyRaw = sheet.getRow(yPointer++).getCell(xPointer).getStringCellValue();
		decisionKey = StringUtil.toList(decisionKeyRaw, StringUtil.CustomRegex.COMMA);

		i = 0;
		nFit = 0;

		// [4th] Checking Unique Constraints within Decision Table. NO DECISION
		// TABLE WILL HAVE SAME KEY ON DECISION AND RULE CONSTRAINTS
		while (i < ruleKey.size()) {
			if (decisionKey.contains(ruleKey.get(i)))
				nFit++;
			i++;
		}

		if (nFit > 0)
			throw new ApplicationRuntimeException(
					"XYBASE Airport Definition Decision Table Exception: Invalid Shema[Rule Key + Decision Key should be in a uniq constraints]");

		if (decisionKeyRaw == null || decisionKeyRaw.replace(StringUtil.CustomRegex.WHITESPACE, "").equals(""))
			throw new ApplicationRuntimeException(
					"XYBASE Airport Definition Decision Table Exception: Shema Violation[No Decision Key Found], you need at least have one decision key ");

		// JS, Main Sheet, contains Decision Table.
		sheet = workbook.getSheet(ConfigComponent.CFG_DT.getString("dt.xls.main.name"));

		xPointer = Integer.valueOf(ConfigComponent.CFG_DT.getString("dt.xls.main.pointer.x"));
		yPointer = Integer.valueOf(ConfigComponent.CFG_DT.getString("dt.xls.main.pointer.y"));

		actPivot = Integer.valueOf(ConfigComponent.CFG_DT.getString("dt.xls.pivot.activation.x"));
		actPivotValue = ConfigComponent.CFG_DT.getString("dt.xls.pivot.activation.value");

		row = sheet.getRow(yPointer);
		i = xPointer;
		nFit = 0;
		temp = "";
		tableKey = new ArrayList<String>();

		/*
		 * [5th] Validating header of Decision Table with definition set of Rule
		 * Key, and Decision Key
		 */
		while ((ruleKey.size() + decisionKey.size() + xPointer) > i) {
			if (row.getCell(i) != null
					&& (ruleKey.contains(row.getCell(i).toString()) || decisionKey.contains(row.getCell(i).toString()))) {
				tableKey.add(row.getCell(i).toString());
				nFit++;
			}
			if (row.getCell(i) != null && i < (ruleKey.size() + decisionKey.size())) {
				temp += row.getCell(i).toString().trim() + " ";
			}
			i++;
		}

		if (nFit != (ruleKey.size() + decisionKey.size()))
			throw new ApplicationRuntimeException(
					"XYBASE Airport Definition Decision Table Exception: Invalid Schema[Expecting: {"
							+ (ruleKeyRaw + " " + decisionKeyRaw).trim().replaceAll(StringUtil.CustomRegex.WHITESPACE,
									", ") + "} instead of {"
							+ temp.trim().replaceAll(StringUtil.CustomRegex.WHITESPACE, ", ") + "}]");

		logger.debug("XYBASE Airport Decision Table Initiation: " + this.referenceName + " Key"
				+ Arrays.toString(ruleKey.toArray()) + ", Decision" + Arrays.toString(decisionKey.toArray()));
		rulesArtifact = new HashMap<String, String>();
		decision = new HashMap<String, String>();

	}

	public void set(String key, Object value) {
		if (!ruleKey.contains(key))
			throw new ApplicationRuntimeException("XYBASE Airport Definition Decision Table Exception: No Key[" + key
					+ "] Description on " + referenceName);
		rulesArtifact.put(key, StringUtil.toString(value));
	}

	// JS, retrieve decision table result, after laced!
	public String get(String key) {
		if (decisionKey.contains(key)) {
			String result = decision.get(key);
			if (logger.isDebugEnabled())
				logger.debug("Decision Table " + this.referenceName + " [" + key + ":" + result + "]");
			return result;
		} else
			throw new ApplicationRuntimeException("XYBASE Airport Definition Decision Table Exception: No Key[" + key
					+ "] Description on " + referenceName);
	}

	public DecisionTableXlsImpl relace() {
		rulesArtifact = new HashMap<String, String>();
		decision = new HashMap<String, String>();
		return this;
	}

	public DecisionTableXlsImpl laced() {
		System.out.println(rulesArtifact);
		if (ruleKey.size() != rulesArtifact.size())
			throw new ApplicationRuntimeException(
					"XYBASE Airport Definition Decision Table Exception: Rule Artifact is not ready!");
		nRows = sheet.getPhysicalNumberOfRows();

		// JS, at this point sheet that uses is Main!!
		yPointer = Integer.valueOf(ConfigComponent.CFG_DT.getString("dt.xls.pivot.start.y"));
		xPointer = Integer.valueOf(ConfigComponent.CFG_DT.getString("dt.xls.pivot.start.x"));

		// [6th]
		isOn = true;
		while (isOn && yPointer < nRows) {
			row = sheet.getRow(yPointer);
			i = xPointer;
			nFit = 0;

			/*
			 * [7th] JS, simple logic producing finite loop extractive
			 * expression, ASKME!!!
			 */
			while (isOn
					/*
					 * JS, DONOT CHANGES THIS!!!, SPENT 3 HOURS TO SOLVE;[Block
					 * of End of Row!]
					 */
					&& row != null
					&& (row.getCell(0) != null && row.getPhysicalNumberOfCells() != 1)
					// JS, Block of End of Row!

					/*
					 * JS, HOLD logic to screening decision table to get the fit
					 * rule-key, very simple: check the consecutive of cell on
					 * the row, if all the row fit with the rule key, it's the
					 * decision.
					 */
					&& (row.getCell(actPivot).getStringCellValue().equals(actPivotValue))
					&& (isIdentical(row.getCell(i), rulesArtifact.get(tableKey.get(i - xPointer))) && ruleKey
							.contains(tableKey.get(i - xPointer)))
					// JS, read this:
					// http://logicallylogic.weebly.com/thelife/imperative-and-short-circuit-evaluation
					& i++ < (ruleKey.size() + xPointer)) {
				// Increment the Fitness value.
				nFit++;
				
			}

			// Will applied event in Zeroed Rule key!!
			if (nFit == ruleKey.size())
				isOn = false;

			yPointer++;

		}

		temp = "";
		if (isOn) {
			row = sheet.getRow(Integer.valueOf(ConfigComponent.CFG_DT.getString("dt.nvl.pivot.y")));
			temp = nvlPrefKeyPattern;
		}

		// [8th]
		j = 0;
		i = xPointer + ruleKey.size() - 1;
		while (j < decisionKey.size()) {
			if (isOn) {
				decision.put(decisionKey.get(j), null);
			}
			decision.put(temp + decisionKey.get(j++), CustomUtil.isNull(row.getCell(++i)) ? StringUtil.valueNull : row
					.getCell(i).toString());

		}
		return this;
	}

	// JS, Required to have NULL VALUE EVALUATION!!, current logic is processing
	// any value by the String altitude!, NO NEED TO CARE THE DIFFERENT TYPE!!
	private boolean isIdentical(Object value, Object comparisonValue) {
		boolean result = (value != null && comparisonValue != null && value.toString().equals(
				comparisonValue.toString()))
				|| (value == null && comparisonValue == null);
		return result;
	}

	// JS, 11112014
	public String getNVL(String key) {
		key = nvlPrefKeyPattern + key;
		try {
			return decision.get(key);
		} catch (ApplicationRuntimeException are) {
			throw new ApplicationRuntimeException(
					"XYBASE Airport Definition Decision Table Exception: No Default ifnull(NVL)-Key["
							+ key.replaceAll(nvlPrefKeyPattern, "") + "] Description on " + referenceName);
		}
	}

	private Workbook mantledFIS(String dtName) {
		FileInputStream fis;
		String prefxPath = ConstantComponent.BASEPTH;
		if (ConstantComponent.BASEPTH == null)
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Data Source Exception, [System Properties: "
							+ ConstantComponent.SYSPROBASEPTH + " doesnot exist!! ]");
		try {
			fis = new FileInputStream(prefxPath + baseLocation + referenceName + suffix);
			Workbook result = new HSSFWorkbook(fis);
			// Shut the stream here!!, keep the file accessible by others, by
			// keep access time low, even this only read operation!!!
			fis.close();
			return result;
		} catch (FileNotFoundException fnfe) {
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, File Not Found Exception[format: " + prefxPath
							+ baseLocation + dtName + suffix + "]");
		} catch (IOException e) {
			throw new ApplicationRuntimeException("XYBASE Airport Rule Engine Exception, IO Exception[format: "
					+ prefxPath + baseLocation + dtName + suffix + "]");
		}
	}

	@Override
	public DecisionTable relace(String name) {
		initiate(name);
		return this;
	}
}
