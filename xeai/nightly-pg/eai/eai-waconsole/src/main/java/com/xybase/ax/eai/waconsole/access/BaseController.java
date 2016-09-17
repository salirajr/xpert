package com.xybase.ax.eai.waconsole.access;

import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

@Controller
public abstract class BaseController{
	
	
	public abstract String view();
	
	public Gson gson = new Gson();
	
}
