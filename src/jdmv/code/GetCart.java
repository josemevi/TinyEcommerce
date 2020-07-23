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
 * This Servlet principal function is to provided the cart_id inside cart table before the checkout (also provided all the items inside user cart).
 * This is because in the way i approach to the solution i decided that all the shopping carts will be dynamic and can be either destroyed or edited 
 * with the only restriction if there's a order associated inside "orders" table (constraint made inside the DB).
 * 
 */
@WebServlet("/getCart")
public class GetCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DBConnection con = new DBConnection();
	JSONObject jsonRes = new JSONObject();
   
    public GetCart() {
        super();
        
    }
    
    public void GetCartJson (String userId) {
    	try {
			String result = "{\"items\":"+con.getJSONFromDB("SELECT items  FROM cart WHERE user_id="+userId);
			if(result.contains("null")) {
				result = "{\"items\":\"\"";
			}
			jsonRes = new JSONObject(result+"}");
		} catch (JSONException | SQLException e) {
		
			e.printStackTrace();
		}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		JSONObject json = new JSONObject();
		if(session != null) {			
			if(con.execSql("SELECT cart_id FROM cart WHERE user_id="+session.getAttribute("userId")+" AND checkout=false") == 1) {									
				GetCartJson(session.getAttribute("userId").toString());
				response.setStatus(200);
				json = new JSONObject(con.getData());
				json.put("items:", jsonRes.get("items"));
			}else if (con.getData().length() == 0) {			
				response.setStatus(204);
				//json.put("msg", "There're no items in this category");
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
