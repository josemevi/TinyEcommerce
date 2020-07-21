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
 * Servlet implementation class EditOrder
 */
@WebServlet("/editOrder")
public class EditOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DBConnection con = new DBConnection();   
  
    public EditOrder() {
        super();
        
    }

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String orderId = request.getParameter("orderId");
		String status = request.getParameter("status");
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
