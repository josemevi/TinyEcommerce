package jdmv.code;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBConnection con = new DBConnection();
	JSONObject cart = new JSONObject();
  
     
    public Login() {
        super();
   
    }
    
    public String GetCartInfo (String userId) {
    	cart = new JSONObject();
    	String result = "";
    	try {
			result = "{\"items\":"+con.getJSONFromDB("SELECT items  FROM cart WHERE user_id="+userId);
			if(result.equals("{\"items\":")) {
				result = "{\"items\":\"\""+"}";
			}			
			cart = new JSONObject(result+"}");			
		} catch (JSONException | SQLException e) {
		
			e.printStackTrace();
		}
    	return result;
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		//String email = request.getParameter("email");
		//String password = request.getParameter("password");
		JSONObject requestJson = con.retrieveJson(request);
		JSONObject json = new JSONObject();		
		if(session == null) {			
			String email = requestJson.get("email").toString();
			String password = requestJson.get("password").toString();			
						
			if(con.execSql("SELECT user_id, email, password, rol_type FROM users WHERE email= "+con.simpleQuoted(email)) == 1) {
				JSONObject result = new JSONObject(con.getData());
				if(password.equals(result.get("password"))) {
					response.setStatus(200);					
					session = request.getSession();
					session.setAttribute("userId", result.get("user_id"));
					session.setAttribute("rolId", result.get("rol_type"));					
					GetCartInfo(result.getString("user_id"));
					session.setAttribute("cart", cart.get("items"));
					json.put("login", true);
					json.put("userId", result.get("user_id"));
					json.put("rol", result.get("rol_type"));
					json.put("cart", cart.get("items"));					
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
			json.put("userId", session.getAttribute("userId"));
			json.put("rol", session.getAttribute("rol"));
			json.put("cart", session.getAttribute("cart"));
			json.put("msg", "Already logged");
		}
		
		response.getWriter().print(json.toString());
	}

}
