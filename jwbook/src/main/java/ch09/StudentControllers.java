package ch09;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Servlet implementation class StudentControllers
 */
public class StudentControllers extends HttpServlet {
	private static final long serialVersionUID = 1L;
      StudentDAO dao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentControllers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
			super.init(config);
			dao = new StudentDAO();
		
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		String view = "";
		
		if(request.getParameter("action")==null) {
			getServletContext().getRequestDispatcher("/studentControl?action=list")
			.forward(request, response);
		}else {
			switch(action) {
			case "list" : view = list(request, response); break;
			case "insert" : view = insert(request, response); break;
			}
			getServletContext().getRequestDispatcher("/ch09/"+view).forward(request, response);
		}
	}
	
	public String list(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("students", dao.getAll());
		
		return "studentInfo.jsp";
	}
	
	public String insert(HttpServletRequest request, HttpServletResponse response) {
		Student s = new Student();
		try {
			BeanUtils.populate(s, request.getParameterMap());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		dao.insert(s);
		
		return list(request, response);
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
