package com.xybase.ax.eai.waconsole.appliance.mock;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xybase.ax.eai.archcomp.constant.ServiceBinderConstant;
import com.xybase.ax.eai.archcomp.constant.XeaiConstants;
import com.xybase.ax.eai.waconsole.appliance.mock.model.Mock;

@Controller
public class MockUp {

	private Gson gson;

	public MockUp() {
		// TODO Auto-generated constructor stub

		gson = new Gson();
	}

	@RequestMapping(value = "/appliance/mockup", method = RequestMethod.POST)
	public String mockup(Model model,
			@RequestParam("fileContext") String fileContext,
			@RequestParam("context") String context,
			@RequestParam("requestOut") String requestOut) {

		model.addAttribute("fileContext", fileContext);
		model.addAttribute("context", context);
		model.addAttribute("requestOut", requestOut);

		System.out.println("assigned Context: " + context);

		GenericApplicationContext gAContext = new GenericApplicationContext();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(gAContext);
		reader.loadBeanDefinitions(new ByteArrayResource(context.getBytes()));
		gAContext.refresh();

		MessageChannel channel = gAContext.getBean("defaultChannelIn",
				MessageChannel.class);
		channel.send(new GenericMessage<String>(requestOut));
		PollableChannel channelOut = gAContext.getBean("defaultChannelOut",
				PollableChannel.class);

		Object responseIn = channelOut.receive().getPayload();
		model.addAttribute("responseIn", responseIn);

		System.out.println(responseIn);

		return "miscellaneous";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/appliance/async/mockup", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody String mockup(@RequestBody Mock mock) {
		JsonObject response = new JsonObject();
		try {
			GenericApplicationContext gAContext = new GenericApplicationContext();
			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(
					gAContext);
			reader.loadBeanDefinitions(new ByteArrayResource(mock.getContext()
					.getBytes()));
			gAContext.refresh();
			MessageChannel channel = gAContext.getBean(
					ServiceBinderConstant.CHANNELIN, MessageChannel.class);
			channel.send(new GenericMessage<String>(mock.getRequestPayload()));
			response.addProperty("headers", "NO HEADER PRODUCE!".toString());
			response.addProperty("payload", mock.getRequestPayload());
			if (mock.getIntegrationType().equals(XeaiConstants.SYNCHRONOUS)) {
				PollableChannel channelOut = gAContext
						.getBean(ServiceBinderConstant.CHANNELOUT,
								PollableChannel.class);
				Message responseIn = channelOut.receive();
				response.addProperty("payload", responseIn.getPayload()
						.toString());
				response.addProperty("headers", responseIn.getHeaders()
						.toString());
			}
			response.addProperty("message", mock.getIntegrationType()
					.toUpperCase() + " MOCK SUCCESS");
		} catch (Exception e) {
			response.addProperty("message", e.getMessage());
			e.printStackTrace();
		}
		System.out.println("response mock up" + gson.toJson(response));
		return gson.toJson(response);
	}

}
