package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xybase.ax.eai.waconsole.access.BaseController;

@Controller
public class References extends BaseController {

	@RequestMapping(value = "/references/home")
	@Override
	public String view() {
		// TODO Auto-generated method stub
		return "home_references";
	}

	@RequestMapping(value = "/references/{model}/action")
	public String view(@PathVariable String model) {

		return "references/" + model;
	}
	
	

}
