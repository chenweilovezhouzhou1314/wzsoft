package com.cw.auction.servlet;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.cw.auction.util.common.V;



/**
 * 拦截js
 * @author Administrator
 *
 */
public class JsServlet extends HttpServlet {
	private static final long serialVersionUID = -6482283553400408658L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		V.outPrintJs(response, FileUtils.readFileToString(new File(V.buildRequestPath(
				request, this.getClass().getResource(V.File_SEP).getPath())), V.UTF8));
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
