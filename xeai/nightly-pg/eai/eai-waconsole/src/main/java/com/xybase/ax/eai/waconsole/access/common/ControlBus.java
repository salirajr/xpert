package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xybase.ax.eai.waconsole.access.BaseController;

@Controller
public class ControlBus<T> extends BaseController {

	@RequestMapping(value = "/controlbus/home")
	@Override
	public String view() {
		// TODO Auto-generated method stub

		return "home_controlbus";
	}

	@RequestMapping(value = "/controlbus/{modal}/action")
	public String view(@PathVariable String modal) {

		return "controlbus/" + modal;
	}
}
