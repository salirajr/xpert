package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SystemHealth {

	@RequestMapping("/syshealth/home")
	public String view() {
		// TODO Auto-generated method stub
		return "home_systemhealth";
	}

	@RequestMapping("/syshealth/{type}/home")
	public String view(@PathVariable String type) {
		// TODO Auto-generated method stub
		return "syshealth/sysh" + type;
	}

}
