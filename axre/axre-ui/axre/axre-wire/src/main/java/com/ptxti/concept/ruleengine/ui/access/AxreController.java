package com.ptxti.concept.ruleengine.ui.access;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ptxti.concept.ruleengine.ui.util.FileUtil;

@Controller
public class AxreController {

	private Gson gson = new Gson();

	@RequestMapping(value = "/{requestedPage}")
	public String routings(@PathVariable String requestedPage) {
		return requestedPage;
	}

	@RequestMapping(value = "/assetfabrics", method = RequestMethod.POST)
	public String assetfabrics(Model model, @RequestParam("base") String base,
			@RequestParam("file") String file,
			@RequestParam("source") String source) {

		model.addAttribute("base", base);
		model.addAttribute("file", file);
		model.addAttribute("source", source);
		return "asset_fabrics";
	}

	@RequestMapping(value = "/get/{base}/ls-lrt", method = RequestMethod.GET)
	public @ResponseBody String getDirContent(@PathVariable String base,
			@RequestParam("currentDir") String currentDir,
			@RequestParam("dirName") String dirName) {
		String basePath = System.getProperty("axre.archcomp.basepath");
		if (base.equals("wiring"))
			basePath = System.getProperty("axre.archcomp.wiring.basepath");

		List<Map<String, String>> lmap = FileUtil.lsILrt(basePath, currentDir
				+ "/" + dirName);
		return gson.toJson(lmap).toString();
	}

	@RequestMapping(value = "/extracts/{base}/{type}", method = RequestMethod.GET)
	public @ResponseBody String extractFile(@PathVariable String base,
			@PathVariable String type, @RequestParam("path") String path) {
		String absolutePath = System.getProperty("axre.archcomp.basepath");
		if (base.equals("wiring")) {
			absolutePath = System.getProperty("axre.archcomp.wiring.basepath");
		}
		absolutePath += path;
		if (type.equals("drl") || type.equals("xml"))
			return FileUtil.getFilePlain(absolutePath);
		return "{only .drl and .xml file supported!!}";
	}

	@RequestMapping(value = "/extracts/wire", method = RequestMethod.GET)
	public @ResponseBody String extractWireFile(
			@RequestParam("path") String path) {
		String absolutePath = System
				.getProperty("axre.archcomp.wiring.basepath") + path;
		return FileUtil.getFilePlain(absolutePath);
	}

	@RequestMapping(value = "/get/{type}/file", method = RequestMethod.GET)
	public @ResponseBody void getFile(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String type,
			@RequestParam("path") String path) {

		String absolutePath = System.getProperty("axre.archcomp.basepath");
		if (type.equals("wiring")) {
			absolutePath = System.getProperty("axre.archcomp.wiring.basepath");
		}
		absolutePath += path;

		ServletContext context = request.getServletContext();

		File downloadFile = FileUtil.getFile(absolutePath);
		FileInputStream inputStream = null;
		OutputStream outStream = null;

		try {
			inputStream = new FileInputStream(downloadFile);

			response.setContentLength((int) downloadFile.length());
			response.setContentType(context.getMimeType(absolutePath));

			// response header
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					downloadFile.getName());
			response.setHeader(headerKey, headerValue);

			// Write response
			outStream = response.getOutputStream();
			IOUtils.copy(inputStream, outStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream)
					inputStream.close();
				if (null != inputStream)
					outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
