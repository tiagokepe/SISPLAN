package br.ifpr.sisplan.jsf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoffServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpServletRequest request = req;

		request.getSession().removeAttribute("usuario");
		request.getSession().invalidate();

		Object logon = request.getSession().getAttribute("logon");
		
		res.sendRedirect("/SISPLAN/");

	}
}
