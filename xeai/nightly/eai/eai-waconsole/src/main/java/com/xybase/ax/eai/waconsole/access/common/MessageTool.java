package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageTool {

	@RequestMapping(value = "/messagetool/home")
	public String view() {
		// TODO Auto-generated method stub
		return "home_messagetool";
	}
}
