package mvc.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/index.do")
// 이 컨트롤러를 통해서 index.jsp가 열리게 된다. 무조건 컨트롤러를 통하기!
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String view = "index.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		//뷰에 디스팻쳐를 뽑아서 rd라는애로 만들어서.
		rd.forward(request, response);
		//rd의 forward메소드로 이동하게 된다. 
	}
}
