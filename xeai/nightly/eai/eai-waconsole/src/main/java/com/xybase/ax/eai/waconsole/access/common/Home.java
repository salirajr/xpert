package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xybase.ax.eai.waconsole.access.BaseController;

@Controller
public class Home extends BaseController {

	private final String basePage = "home";

	@Override
	@RequestMapping("/" + basePage)
	public String view() {
		return basePage;
	}

}
