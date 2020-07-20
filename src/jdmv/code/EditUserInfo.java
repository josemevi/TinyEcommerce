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
@WebServlet("/editUserInfo")
public class EditUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBConnection con = new DBConnection();  
    
    public EditUserInfo() {
        super();
   
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Signup mail = new Signup();
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String lastName = request.getParameter("lastName");
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
			if(con.checkString(name) && con.checkString(lastName) && con.checkString(email)) {
				if(!mail.checkEmail(email)) {					
					if(con.execSql("UPDATE users SET email= "+con.simpleQuoted(email)+",name="+con.simpleQuoted(name)+",lastname="+con.simpleQuoted(lastName)
					+",document_type="+document_type+ ",document_number="+con.simpleQuoted(document_number)+",direction ="+con.simpleQuoted(direction)
					+",cc_number="+ccNumber+ " WHERE user_id="+session.getAttribute("userId")) == 1) {
						response.setStatus(200);
						json.put("msg", "ok");			
					}else {
						response.setStatus(500);
						json.put("msg", "Server Error");			
					}
				}else {
					response.setStatus(400);
					json.put("msg", "Email Already In Use");
				}
			}else {
				response.setStatus(400);
				json.put("msg", "Invalid values in important fields detected, please check all the fields");
			}			
		}else {
			response.setStatus(403);
			json.put("msg", "Invalid Session please log in first");
		}
		response.getWriter().print(json.toString());
	}

}
