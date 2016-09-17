package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xybase.ax.eai.waconsole.access.BaseController;

@Controller
public class Group extends BaseController {

	@RequestMapping(value = "/group/home")
	@Override
	public String view() {
		// TODO Auto-generated method stub
		return "home_group";
	}

}
