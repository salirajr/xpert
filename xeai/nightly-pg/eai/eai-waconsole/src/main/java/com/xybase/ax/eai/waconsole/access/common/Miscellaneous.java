package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xybase.ax.eai.waconsole.access.BaseController;

@Controller
public class Miscellaneous extends BaseController {

	@RequestMapping("/misc")
	@Override
	public String view() {
		// TODO Auto-generated method stub
		return "miscellaneous";
	}

	@RequestMapping("/errors/{errortype}")
	public String errorView(@PathVariable String errortype) {
		// TODO Auto-generated method stub
		return "errors/" + errortype;
	}

}
