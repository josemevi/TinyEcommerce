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
 * Servlet implementation class AddOrder
 */
@WebServlet("/addOrder")
public class AddOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DBConnection con = new DBConnection();
   
    public AddOrder() {
        super();
        
    }
    
    public boolean checkOrder (String orderId) {
		if(con.execSql("SELECT cart_id FROM orders WHERE cart_id= "+con.simpleQuoted(orderId)) == 1) {
			return true;
		}else {
			return false;
		}			 
   }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		//String cartId = request.getParameter("cartId");
		JSONObject requestB = con.retrieveJson(request);
		String cartId = requestB.getString("cartId");
		System.out.println(cartId);
		JSONObject json = new JSONObject();		
		if (session != null) {
			if(con.checkString(cartId) && !checkOrder(cartId)) {			
				if(con.execSql("UPDATE cart SET checkout="+true+" WHERE cart_id="+cartId) == 1){
					if(con.execSql("INSERT INTO orders VALUES (DEFAULT, "+cartId+", DEFAULT)") == 1) {
						session.removeAttribute("cart");						
						response.setStatus(201);
						json.put("msg", "Order Created");
					}
			    }else{
			    	response.setStatus(500);
					json.put("msg", "Server error");
			    }		 
			}else {
				json.put("msg","Cart id is empty or invalid or already a order is created with this id");
			}
		}else {
			response.setStatus(403);
			json.put("msg", "Invalid Session please log in first");
		}
		response.getWriter().print(json.toString());
	}

}
