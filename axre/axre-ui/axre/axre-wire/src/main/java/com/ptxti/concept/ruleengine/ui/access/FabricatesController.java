package com.ptxti.concept.ruleengine.ui.access;

import java.io.File;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ptxti.concept.ruleengine.ui.util.FileUtil;

@Controller
public class FabricatesController {

	@RequestMapping(value = "fabricates/{model}", method = RequestMethod.GET)
	public String routings(@PathVariable String model) {
		return model + "_fabrics";
	}

	@RequestMapping(value = "fabricates/files/{model}")
	public @ResponseBody String getFiles(@PathVariable String model) {

		if (model.equals("RuntimeConfiguration")) {
			ArrayList<File> files = new ArrayList<>();
			FileUtil.fileScreening(
					System.getProperty("axre.archcomp.wiring.basepath"), files,
					"wireContext-locals.xml", ".xml");
			return FileUtil.filesFilter(files);
		} else if (model.equals("ContextWire")) {
			ArrayList<File> files = new ArrayList<>();
			FileUtil.fileScreening(
					System.getProperty("axre.archcomp.wiring.basepath"), files,
					"wire-definition\\wireContext", ".xml");
			return FileUtil.filesFilter(files);
		} else if (model.equals("RoutingMappings")) {
			ArrayList<File> files = new ArrayList<>();
			FileUtil.fileScreening(
					System.getProperty("axre.archcomp.wiring.basepath"), files,
					"wireservice-definition\\wireserviceContext", ".xml");
			return FileUtil.filesFilter(files);
		} else if (model.equals("ServiceWiringDefinition")) {
			ArrayList<File> files = new ArrayList<>();
			FileUtil.fileScreening(
					System.getProperty("axre.archcomp.basepath"), files,
					"\\assets\\session\\sessionContext", ".xml");
			return FileUtil.filesFilter(files);
		}
		return "[{}]";
	}
}
