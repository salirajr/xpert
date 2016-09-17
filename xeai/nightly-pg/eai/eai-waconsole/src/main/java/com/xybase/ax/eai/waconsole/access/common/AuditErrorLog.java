/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Feb 12, 2016	15:15:36 PM			Abdul Azis Nur			Initial Creation
 * 
 * **************************************************************************************
 * 
 */

package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xybase.ax.eai.waconsole.access.BaseController;

@Controller
public class AuditErrorLog extends BaseController {

	
	@Override
	public String view() {
		// TODO Auto-generated method stub
		return "home_error_logs";
	}
	
	@RequestMapping(value = "/auditerrorlog/home")
	public String view(Model model,
			@RequestParam(value = "xeaiIdTemp", required = false) String xeaiIdTemp,
			@RequestParam(value = "eventNameTemp", required = false) String eventNameTemp,
			@RequestParam(value = "dateStartTemp", required = false) String dateStartTemp,
			@RequestParam(value = "timeStartTemp", required = false) String timeStartTemp,
			@RequestParam(value = "dateEndTemp", required = false) String dateEndTemp,
			@RequestParam(value = "timeEndTemp", required = false) String timeEndTemp) {
		model.addAttribute("xeaiIdTemp", xeaiIdTemp);
		model.addAttribute("eventNameTemp", eventNameTemp);
		model.addAttribute("dateStartTemp", dateStartTemp);
		model.addAttribute("timeStartTemp", timeStartTemp);
		model.addAttribute("dateEndTemp", dateEndTemp);
		model.addAttribute("timeEndTemp", timeEndTemp);
		
		return view();
	}

	@RequestMapping(value = "/auditerrorlog/{model}/action")
	public String view(@PathVariable String model) {

		return "auditerrorlog/" + model;
	}

}
