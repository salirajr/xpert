package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xybase.ax.eai.waconsole.access.BaseController;

@Controller
public class AuditLog extends BaseController {

	@Override
	public String view() {
		// TODO Auto-generated method stub
		return "home_logs";
	}

	@RequestMapping(value = "/auditlog/home")
	public String view(Model model,
			@RequestParam(value = "xeai-id", required = false) String xeaiId) {
		model.addAttribute("xeaiId", xeaiId);
		return view();
	}

}
