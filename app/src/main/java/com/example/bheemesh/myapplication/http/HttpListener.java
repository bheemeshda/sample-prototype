package com.example.bheemesh.myapplication.http;

import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * 
 * Interface for callback from HTTPEngine
 * 
 */

public interface HttpListener {
	void handleHttpResponse(HttpRequest httpRequest);

	void setHttpHeaders(HttpURLConnection hc);

	void setHttpData(OutputStream os);

	void setSocketData(OutputStream os, String headers);

	void handleHttpException(Exception e, HttpRequest httpRequest);

	public static class SimpleHttpListener implements HttpListener {

		@Override
		public void handleHttpResponse(HttpRequest httpRequest) {
			// TODO Auto-generated method stub
		}

		@Override
		public void setHttpHeaders(HttpURLConnection hc) {
			// TODO Auto-generated method stub
		}

		@Override
		public void setHttpData(OutputStream os) {
			// TODO Auto-generated method stub
		}

		@Override
		public void setSocketData(OutputStream os, String headers) {
			// TODO Auto-generated method stub
		}

		@Override
		public void handleHttpException(Exception e, HttpRequest httpRequest) {
			// TODO Auto-generated method stub
		}

	}
}
