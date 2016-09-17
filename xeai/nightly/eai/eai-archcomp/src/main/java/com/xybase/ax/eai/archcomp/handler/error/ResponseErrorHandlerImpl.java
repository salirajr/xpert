package com.xybase.ax.eai.archcomp.handler.error;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class ResponseErrorHandlerImpl extends DefaultErrorHandler implements
		ResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse arg0) throws IOException {
		// TODO Auto-generated method stub
		System.out.println(arg0);
	}

	@Override
	public boolean hasError(ClientHttpResponse arg0) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
