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
 * This servlet creates users with the minimun necessary information. Also validates if the user email is already inside the "users" table
 */
@WebServlet("/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBConnection con = new DBConnection();
	
       
 
    public Signup() {
        super();
       
    }
    
   public boolean checkEmail (String mail) {
		if(con.execSql("SELECT email FROM users WHERE email= "+con.simpleQuoted(mail)) == 1) {
			return true;
		}else {
			return false;
		}			 
   }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		JSONObject requestJson = con.retrieveJson(request);
		String email = requestJson.getString("email");
		String password = requestJson.getString("password");
		String name = requestJson.getString("name");
		String lastName = requestJson.getString("lastname");
		JSONObject json = new JSONObject();		
		//String email = request.getParameter("email");
		//String password = request.getParameter("password");
		//String name = request.getParameter("name");
		//String lastName = request.getParameter("lastName");
		if(session == null) {
			if(con.checkString(email) && con.checkString(password) && con.checkString(name) && con.checkString(lastName)) {
				if(checkEmail(email)) {
					response.setStatus(400);
					json.put("msg", "Email Already In Use");
				}else {					
					if(con.execSql("INSERT INTO users values (DEFAULT, "+con.simpleQuoted(email)+", DEFAULT,"
							+ con.simpleQuoted(password)+", "+con.simpleQuoted(name)+", "+con.simpleQuoted(lastName)+")") == 1){
						response.setStatus(201);
						json.put("msg", "Created");
				    }else{
				    	response.setStatus(500);
						json.put("msg", "Server error");
				    }		
				}
					
			}else {
				response.setStatus(400);
				json.put("msg", "Invalid values in important fields detected, please check all the fields");
			}
		}else {
			response.setStatus(403);
			json.put("msg", "Already logged");
		}
		
		response.getWriter().print(json.toString().replaceAll("\n", ""));
	}

}
