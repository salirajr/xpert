package com.ptxti.concept.ruleengine.ui.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.concept.ruleengine.archcomp.ConfigComponent;
import com.concept.ruleengine.archcomp.ConstantComponent;
import com.concept.ruleengine.archcomp.exception.ApplicationRuntimeException;
import com.concept.ruleengine.common.util.StringUtil;

public class DtXlsUtil {

	private Workbook workbook;
	private Sheet sheet;
	private Row row;

	private List<String> ruleKey;
	private List<String> decisionKey;
	private List<String> tableKey;

	private String reference;

	private int xPointer, yPointer;

	// Temporary variable, defined to keep the code simpler and controlled
	// allocated variable, uses over by over again.
	private int nRows, i, nFit;

	public static final String nvlPrefKeyPattern = ConfigComponent.CFG_DT
			.getString("dt.nvl.keypattern");

	public DtXlsUtil(String dtReference) {

		xPointer = Integer.valueOf(ConfigComponent.CFG_DT
				.getString("dt.xls.metainfo.pointer.x"));
		yPointer = Integer.valueOf(ConfigComponent.CFG_DT
				.getString("dt.xls.metainfo.pointer.y"));

		this.reference = dtReference;
		workbook = mantleRefernce(reference);

		sheet = workbook.getSheet(ConfigComponent.CFG_DT
				.getString("dt.xls.metainfo.name"));

		String temp = sheet.getRow(yPointer++).getCell(xPointer)
				.getStringCellValue();

		String ruleKeyRaw = sheet.getRow(yPointer++).getCell(xPointer)
				.getStringCellValue();
		ruleKey = StringUtil.toList(ruleKeyRaw, StringUtil.CustomRegex.COMMA);
		String decisionKeyRaw = sheet.getRow(yPointer++).getCell(xPointer)
				.getStringCellValue();
		decisionKey = StringUtil.toList(decisionKeyRaw,
				StringUtil.CustomRegex.COMMA);

		// JS, Main Sheet, contains Decision Table.
		sheet = workbook.getSheet(ConfigComponent.CFG_DT
				.getString("dt.xls.main.name"));

		xPointer = Integer.valueOf(ConfigComponent.CFG_DT
				.getString("dt.xls.main.pointer.x"));
		yPointer = Integer.valueOf(ConfigComponent.CFG_DT
				.getString("dt.xls.main.pointer.y"));

		Integer.valueOf(ConfigComponent.CFG_DT
				.getString("dt.xls.pivot.activation.x"));
		ConfigComponent.CFG_DT.getString("dt.xls.pivot.activation.value");

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
					&& (ruleKey.contains(row.getCell(i).toString()) || decisionKey
							.contains(row.getCell(i).toString()))) {
				tableKey.add(row.getCell(i).toString());
				nFit++;
			}
			if (row.getCell(i) != null
					&& i < (ruleKey.size() + decisionKey.size())) {
				temp += row.getCell(i).toString().trim() + " ";
			}
			i++;
		}

		if (nFit != (ruleKey.size() + decisionKey.size()))
			throw new ApplicationRuntimeException(
					"XYBASE Airport Definition Decision Table Exception: Invalid Schema[Expecting: {"
							+ (ruleKeyRaw + " " + decisionKeyRaw).trim()
									.replaceAll(
											StringUtil.CustomRegex.WHITESPACE,
											", ")
							+ "} instead of {"
							+ temp.trim().replaceAll(
									StringUtil.CustomRegex.WHITESPACE, ", ")
							+ "}]");

	}

	public Map<String, Object> extracts() {

		yPointer = Integer.valueOf(ConfigComponent.CFG_DT
				.getString("dt.xls.pivot.start.y"));
		xPointer = Integer.valueOf(ConfigComponent.CFG_DT
				.getString("dt.xls.pivot.start.x"));

		nRows = sheet.getPhysicalNumberOfRows();

		List<List<String>> data = new ArrayList<>();
		List<String> lRow;
		for (int i = yPointer; i < nRows; i++) {
			row = sheet.getRow(i);
			lRow = new ArrayList<>();
			for (int j = xPointer; j < tableKey.size() + xPointer; j++) {
				lRow.add(row.getCell(j).toString());
			}
			data.add(lRow);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("tableKey", tableKey);
		result.put("data", data);
		return result;

	}

	private Workbook mantleRefernce(String fileLocation) {
		FileInputStream fis;
		String prefxPath = ConstantComponent.BASEPTH;
		if (ConstantComponent.BASEPTH == null)
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Data Source Exception, [System Properties: "
							+ ConstantComponent.SYSPROBASEPTH
							+ " doesnot exist!! ]");
		try {
			fis = new FileInputStream(prefxPath + fileLocation);
			Workbook result = new HSSFWorkbook(fis);
			// Shut the stream here!!, keep the file accessible by others, by
			// keep access time low, even this only read operation!!!
			fis.close();
			return result;
		} catch (FileNotFoundException fnfe) {
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, File Not Found Exception[format: "
							+ prefxPath + fileLocation + "]");
		} catch (IOException e) {
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, IO Exception[format: "
							+ prefxPath + fileLocation + "]");
		}
	}

}
