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
 * Servlet implementation class GetUserInfo
 */
@WebServlet("/getUserInfo")
public class GetUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBConnection con = new DBConnection ();
       
    public GetUserInfo() {
        super();
   
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		JSONObject json = new JSONObject();
		if(session != null) {
			if(con.execSql("SELECT * FROM users WHERE user_id="+session.getAttribute("userId")) == 1) {				
				JSONObject jsonRes = new JSONObject("{\"userData\":"+con.getData()+"}");			
				response.setStatus(200);
				json.put("userData", jsonRes.get("userData"));
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
	}

}
