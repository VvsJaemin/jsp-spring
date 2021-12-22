package ch08;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductController
 */
@WebServlet("/pcontrol")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductService service;
	/**
	 * @see HttpServlet#HttpServlet()
	 */

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);

		service = new ProductService();
	}


	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		String view = "";

		if (request.getParameter("action") == null) {
			getServletContext().getRequestDispatcher("/pcontrol?action=list").forward(request, response);
		} else {
			switch (action) {
			case "list":
				view = list(request, response);
				break;
			case "info":
				view = info(request, response);
				break;
			}
			getServletContext().getRequestDispatcher("/ch08/" + view).forward(request, response);
		}
	}
	
	private String info(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("p", service.find(request.getParameter("id")));
		return "productInfo.jsp";
	}
	
	private String list(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("products", service.findAll());
		return "productList.jsp";
	}

}
