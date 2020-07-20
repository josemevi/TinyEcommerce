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
 * Servlet implementation class GetCart
 */
@WebServlet("/getCart")
public class GetCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DBConnection con = new DBConnection();
   
    public GetCart() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String userId = request.getParameter("userId");
		JSONObject json = new JSONObject();
		JSONObject jsonRes = new JSONObject();
		if(session != null) {
			if(con.checkString(userId)) {
				if(con.execSql("SELECT cart_id FROM cart WHERE user_id="+userId+" AND checkout=false") == 1) {									
					try {
						String result = "{\"cart\":"+con.getJSONFromDB("SELECT items  FROM cart WHERE user_id="+userId);						
						jsonRes = new JSONObject(result+"}");
					} catch (JSONException | SQLException e) {
					
						e.printStackTrace();
					}
					response.setStatus(200);
					json = new JSONObject(con.getData());
					json.put("items:", jsonRes.get("cart"));
				}else if (con.getData().length() == 0) {			
					response.setStatus(204);
					//json.put("msg", "There're no items in this category");
				}else {
					response.setStatus(500);
					json.put("msg", "Server Error");
				}
				
			}else {
				response.setStatus(400);
				json.put("msg", "User id is empty or invalid");
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
