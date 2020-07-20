package jdmv.code;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

/**
 * Servlet implementation class UserPaymentInfo
 */
@WebServlet("/userPaymentInfo")
public class UserPaymentInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBConnection con = new DBConnection();  
    
    public UserPaymentInfo() {
        super();
   
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String document_type = request.getParameter("document_type").toString();
		String document_number = request.getParameter("document_number").toString();
		String direction = request.getParameter("direction").toString();
		String ccNumber = request.getParameter("ccNumber").toString();
		if(!con.checkString(document_type)) {
			document_type = null;
		}
		if(!con.checkString(document_number)) {
			document_number = null;
		}
		if(!con.checkString(direction)) {
			direction = null;
		}
		if(!con.checkString(ccNumber)) {
			ccNumber = null;
		}
		JSONObject json = new JSONObject();
		if(session != null) {
			System.out.println("UPDATE users SET document_type="+document_type+ ",document_number="+con.simpleQuoted(document_number)
			+",direction="+con.simpleQuoted(direction)+",ccNumber="+ccNumber+ " WHERE user_id="+session.getAttribute("userId"));
			if(con.execSql("UPDATE users SET document_type="+document_type+ ",document_number="+con.simpleQuoted(document_number)
			+",direction="+con.simpleQuoted(direction)+",cc_number="+ccNumber+ " WHERE user_id="+session.getAttribute("userId")) == 1) {
				response.setStatus(200);
				json.put("msg", "ok");			
			}else {
				response.setStatus(500);
				json.put("msg", "Server Error");			
			}
		}else {
			response.setStatus(403);
			json.put("msg", "Invalid Session please log in first");
		}
		response.getWriter().print(json.toString());
	}

}
