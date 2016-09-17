package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xybase.ax.eai.waconsole.access.BaseController;

@Controller
public class BusinessFlow extends BaseController {
	
	
	@RequestMapping(value = "/businessflow/home")
	@Override
	public String view() {
		// TODO Auto-generated method stub
		return "home_businessfl";
	}

	@RequestMapping(value = "/businessflow/action")
	public String view(Model model) {
		return "businessfl/" + "action_businessfl";
	}
	
}
