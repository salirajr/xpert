package com.ptxti.concept.ruleengine.ui.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.concept.ruleengine.archcomp.ConfigComponent;
import com.concept.ruleengine.archcomp.ConstantComponent;
import com.concept.ruleengine.archcomp.exception.ApplicationRuntimeException;
import com.concept.ruleengine.common.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class QmXlsUtil {

	private Workbook workbook;
	private Sheet sheet;
	private Row row;

	private HashMap<String, String> queryParameters;
	private List<String> parameterKey;
	private List<String> tableKey;

	private int nRows, i, nFit;
	private boolean isOn;

	private int xPointer, yPointer, actPivot;

	// JS, add after configurable logic imply!
	private String actPivotValue, pivotValue, temp, tempContainer;

	public QmXlsUtil(String mapNames) {
		xPointer = Integer.valueOf(ConfigComponent.CFG_QM
				.getString("qm.xls.activation.pivot.x"));
		actPivot = xPointer;
		yPointer = Integer.valueOf(ConfigComponent.CFG_QM
				.getString("qm.xls.activation.pivot.y"));

		// .xls File Name
		workbook = mantleReference(mapNames);

		// Sheet Name, define on querymapper.properties
		sheet = workbook.getSheet(ConfigComponent.CFG_QM
				.getString("qm.xls.sheet"));

		tableKey = StringUtil.toList(
				ConfigComponent.CFG_QM.getString("qm.xls.sheet.header.plot"),
				StringUtil.CustomRegex.COMMA);

		i = 0;
		isOn = true;
		/*
		 * JS, Checking if the header definition is unique!
		 */
		while (isOn && i < tableKey.size()) {
			temp = tableKey.get(i);
			if (Collections.frequency(tableKey, temp) > 1)
				isOn = false;
			i++;
		}
		if (!isOn)
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, Duplicated Query Mapper Template Definition[Reference: "
							+ tableKey + "]");

		pivotValue = sheet.getRow(yPointer).getCell(xPointer).toString();
		temp = ConfigComponent.CFG_QM.getString("qm.xls.activation.head");

		/*
		 * JS, Checking if the is activation flag on the row definition and the
		 * .xls sheet
		 */
		if (!pivotValue.equals(temp))
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, Invalid Query Mapper Template[Expecting Activation Header "
							+ temp + " instead of " + pivotValue + "]");

		xPointer = Integer.valueOf(ConfigComponent.CFG_QM
				.getString("qm.xls.sheet.header.pivot.x"));
		yPointer = Integer.valueOf(ConfigComponent.CFG_QM
				.getString("qm.xls.sheet.header.pivot.y"));
		actPivotValue = ConfigComponent.CFG_QM
				.getString("qm.xls.activation.pivot.value");

		i = 0;
		nFit = 0;
		tempContainer = "";

		/*
		 * JS, Checking if the header definition is fit with the screening .xls
		 * scheme!
		 */
		while (i < tableKey.size()) {
			temp = sheet.getRow(yPointer).getCell(xPointer + i).toString();
			if (tableKey.contains(temp.trim()))
				nFit++;
			i++;
			tempContainer += temp + " ";
		}

		if (nFit != tableKey.size())
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, Invalid Query Mapper Template[Expecting Header "
							+ tableKey.toString()
							+ " instead of ["
							+ tempContainer.trim().replaceAll(
									StringUtil.CustomRegex.WHITESPACE, ", ")
							+ "]]");

		nRows = sheet.getPhysicalNumberOfRows();

		yPointer++;
	}

	public String extracts() {
		Gson gson = new Gson();
		List<String> iTableKey = new ArrayList<>();
		iTableKey.add(ConfigComponent.CFG_QM.getString("qm.xls.activation.head"));
		iTableKey.addAll(1, tableKey);
		String result = "{\"tableKey\":" + gson.toJson(iTableKey);

		List<List<Object>> data = new ArrayList<>();
		List<Object> rDataList;
		boolean isAdd = false;
		Object cell;
		int maxLength = 10000;
		for (i = yPointer; i < nRows; i++) {
			rDataList = new ArrayList<>();
			row = sheet.getRow(i);
			if (row != null) {
				isAdd = false;
				for (int j = actPivot; j <= actPivot + tableKey.size(); j++) {
					cell = row.getCell(j);
					if (cell != null)
						isAdd = true;

					if (cell != null
							&& row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING) {
						if (cell.toString().length() > maxLength) {
							cell = cell.toString().substring(0, maxLength);
						}
						cell = jsonEscape(cell.toString());
					}
					rDataList.add("\""+cell+"\"");
				}
				if (isAdd)
					data.add(rDataList);
			}
		}
		result += ",";
		result += "\"data\":" + data + "}";
		return result;
	}

	public String jsonEscape(String text) {
		text = text.replaceAll("[\\n]", "");
		text = text.replaceAll("[\\t]", "");
		text = text.replaceAll("[\\s]+", " ");
		return text;
	}

	private Workbook mantleReference(String fileLocation) {
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
