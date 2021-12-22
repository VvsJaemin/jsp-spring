package ch08;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calcControl")
public class CalcController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public CalcController() {
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int n1 =Integer.parseInt(req.getParameter("n1"));
		int n2 =Integer.parseInt(req.getParameter("n2"));
		
		long result = 0;
		
		switch (req.getParameter("op")) {
		case "+": result = n1+n2; break;
		case "-": result = n1-n2; break;
		case "*": result = n1*n2; break;
		case "/": result = n1/n2; break;
		}
		
		req.setAttribute("result", result);
		getServletContext().getRequestDispatcher("/ch08/calcResult.jsp")
		.forward(req, resp);
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	
}
