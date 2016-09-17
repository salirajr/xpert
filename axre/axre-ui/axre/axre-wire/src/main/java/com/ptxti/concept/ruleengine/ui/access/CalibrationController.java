package com.ptxti.concept.ruleengine.ui.access;

import java.io.File;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ptxti.concept.ruleengine.ui.util.DaoUtil;
import com.ptxti.concept.ruleengine.ui.util.DtXlsUtil;
import com.ptxti.concept.ruleengine.ui.util.FileUtil;
import com.ptxti.concept.ruleengine.ui.util.QmXlsUtil;

@Controller
public class CalibrationController {

	private DaoUtil rmsDaoUtil;

	private Gson gson = new Gson();

	public CalibrationController() throws NamingException {
		this.rmsDaoUtil = new DaoUtil();
		JndiTemplate jndi = new JndiTemplate();
		DataSource ds = (DataSource) jndi.lookup("java:jboss/datasources/rms");
		this.rmsDaoUtil.setDataSource(ds);
	}

	@RequestMapping(value = "reference/{model}/calibrate")
	public String routings(@PathVariable String model) {
		return model + "_reference";
	}

	@RequestMapping(value = "reference/get/{model}/calibrate")
	public @ResponseBody String getReference(@PathVariable String model) {
		if (model.equals("rms"))
			return rmsDaoUtil.getJson("select * from rms_parameter");
		else if (model.equals("querymapper")) {
			ArrayList<File> files = new ArrayList<>();
			FileUtil.fileScreening(
					System.getProperty("axre.archcomp.basepath"), files,
					"queriesmap", ".xls");
			return FileUtil.filesFilter(files);
		} else if (model.equals("dtsource")) {
			ArrayList<File> files = new ArrayList<>();
			FileUtil.fileScreening(
					System.getProperty("axre.archcomp.basepath"), files,
					"dtsource", ".xls");
			return FileUtil.filesFilter(files);
		} else if (model.equals("drools")) {
			ArrayList<File> files = new ArrayList<>();
			FileUtil.fileScreening(
					System.getProperty("axre.archcomp.basepath"), files, "",
					".drl");
			return FileUtil.filesFilter(files);
		}

		return "[{}]";
	}

	@RequestMapping(value = "reference/get/{model}/calibrate/content")
	public @ResponseBody String getContent(@PathVariable String model,
			@RequestParam("file") String file) {
		if (model.equals("querymapper")) {
			return new QmXlsUtil(file).extracts().toString();
		} else if (model.equals("dtsource")) {
			return gson.toJson(new DtXlsUtil(file).extracts());
		}
		return "[{}]";
	}

}
