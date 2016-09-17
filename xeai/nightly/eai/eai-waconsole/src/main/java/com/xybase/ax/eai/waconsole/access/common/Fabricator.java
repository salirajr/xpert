package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xybase.ax.eai.waconsole.access.BaseController;

@Controller
public class Fabricator extends BaseController {

	@RequestMapping(value = "/fabricator/home")
	public String view() {
		// TODO Auto-generated method stub
		return "home_fabricator";
	}

	@RequestMapping(value = "/fabricator/{modal}/action")
	public String view(@PathVariable String modal) {

		return "fabricator/" + modal;
	}

}
