package ch10;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewsController
 */
@WebServlet(urlPatterns = "/news.nhn")
@MultipartConfig(maxFileSize = 1024*1024*2, location = "c:/Temp/img")
public class NewsController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private NewsDAO dao;
	private ServletContext ctx;
	
	// 웹 리소스 기본 경로 저장
	private final String START_PAGE = "ch10/newsList.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		dao = new NewsDAO();
		ctx = getServletContext();
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		dao = new NewsDAO();
		
		// 자바 리플랙션을 사용 해 if(switch) 없이 요청에 따라 구현 메서드가 실행되도록 구성
		Method m;
		String view = null;
		
		if(action ==null) {
			action ="listNews";
		}
		
		try {
			// 현재 클래스에서 action 이름과 HttpServletRequest를 파라미터로 하는 메서드를 찾음
			m = this.getClass().getMethod(action, HttpServletRequest.class);
			
			// 메서드 실행 후 리턴 값 받아옴
			view = (String)m.invoke(this, request);
		}catch (NoSuchMethodException e) {
			// TODO: handle exception
			e.printStackTrace();
			ctx.log("요청 action 없음!! ");
			request.setAttribute("error", "action 파라미터가 잘못되었습니다.");
			view = START_PAGE;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// post 요청에서는 리디렉션 방법으로 이동하도록 분기
		if(view.startsWith("redirect:/")) {
			String rview = view.substring("redirect:/".length()); // redirect:/ 문자열 이후 경로만 가져옴
			response.sendRedirect(rview);
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response); //  저장된 뷰로 포워딩, 포워딩 시 콘텍스트 경로는 필요 없음
		}
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