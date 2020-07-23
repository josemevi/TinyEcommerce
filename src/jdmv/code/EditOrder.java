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
 * This Servlet just edits the "status" column inside of "orders" table, this ep
 * can be only acceded by Admin types users
 */
@WebServlet("/editOrder")
public class EditOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DBConnection con = new DBConnection();   
  
    public EditOrder() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		//String orderId = request.getParameter("orderId");
		//String status = request.getParameter("status");
		JSONObject requestB = con.retrieveJson(request);
		String orderId = requestB.getString("orderId");
		String status = requestB.getString("orderStatus");
		System.out.println(orderId);
		System.out.println(status);
		JSONObject json = new JSONObject();
		if(!con.checkString(status)) {
			status = "In Progress";
		}
		if(session != null && Integer.parseInt((String) session.getAttribute("rolId")) == 0) {	
			if(con.checkString(orderId)) {				
				if(con.execSql("UPDATE orders SET order_status="+con.simpleQuoted(status)+" WHERE order_id="+orderId) == 1) {
					response.setStatus(200);
					json.put("msg", "Order Updated");	
				}else {
					response.setStatus(500);
					json.put("msg", "Server Error");
				}
			}else {
				response.setStatus(400);
				json.put("msg", "Order id empty or invalid");
			}
		}else {
			response.setStatus(403);
			json.put("msg", "Invalid Session or insufficient permissions");
		}
		response.getWriter().print(json.toString());
	}

}
