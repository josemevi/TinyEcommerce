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
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBConnection con = new DBConnection();
  
     
    public Login() {
        super();
   
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String email = request.getParameter("email").toString();
		String password = request.getParameter("password").toString();
		//JSONObject requestJson = con.retrieveJson(request);		
		JSONObject json = new JSONObject();
		if(session == null) {			
			//String email = requestJson.get("email").toString();
			//String password = requestJson.get("password").toString();			
			con.execSql("SELECT user_id, email, password FROM users WHERE email= "+con.simpleQuoted(email));			
			if(con.getData().length() > 0) {
				JSONObject result = new JSONObject(con.getData());
				if(password.equals(result.get("password"))) {
					response.setStatus(200);
					json.put("login", true);
					json.put("userId", result.get("user_id"));
					session = request.getSession();
					session.setAttribute("userId", result.get("user_id"));
				}else {
					response.setStatus(401);
					json.put("login", false);
					json.put("msg", "invalid credentials");
				}
			} else {
				response.setStatus(401);
				json.put("login", false);
				json.put("msg", "invalid credentials");
			}
		}else {
			response.setStatus(200);
			json.put("login", true);
			json.put("msg", "Already logged");
		}
		
		response.getWriter().print(json.toString());
	}

}
