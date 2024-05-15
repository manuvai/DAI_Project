

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlets.AbstractServlet;

/**
 * Servlet implementation class preparationCommandesServlet
 */
@WebServlet("/preparationCommandesServlet")
public class preparationCommandesServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public preparationCommandesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	protected void responseGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void responsePost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
