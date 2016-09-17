package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xybase.ax.eai.waconsole.access.BaseController;

@Controller
public class Endpoint extends BaseController{
	
	
	@RequestMapping(value = "/endpoint/home")
	@Override
	public String view() {
		// TODO Auto-generated method stub
		return "home_endpoint";
	}

	
	@RequestMapping(value = "/endpoint/{model}/action")
	public String view(@PathVariable String model) {

		return "events/" + model;
	}
}
