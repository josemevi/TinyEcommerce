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
 * Servlet implementation class GetOrders
 */
@WebServlet("/getOrders")
public class GetOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DBConnection con = new DBConnection();
    
    
    public GetOrders() {
        super();
        // 
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		JSONObject json = new JSONObject();
		if(session != null && Integer.parseInt((String) session.getAttribute("rolId")) == 0) {							
			if(con.execSql("SELECT * FROM orders") == 1) {				
				JSONObject jsonRes = new JSONObject("{"+con.doubleQuoted("orders")+":["+con.getData()+"]}");				
				response.setStatus(200);
				json.put("orders", jsonRes.get("orders"));
			}else {
				response.setStatus(500);
				json.put("msg", "Server Error");
			}
			
		}else {
			response.setStatus(403);
			json.put("msg", "Invalid Session or insufficient permissions");
		}
		response.getWriter().print(json.toString());
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
