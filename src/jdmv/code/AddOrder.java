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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String cartId = request.getParameter("cartId");
		JSONObject json = new JSONObject();
		System.out.println(cartId);
		if (session != null) {
			if(con.checkString(cartId)) {			
				if(con.execSql("UPDATE cart SET checkout="+true+" WHERE cart_id="+cartId) == 1){
					if(con.execSql("INSERT INTO orders VALUES (DEFAULT, "+cartId+", DEFAULT)") == 1) {
						response.setStatus(201);
						json.put("msg", "Order Created");
					}
			    }else{
			    	response.setStatus(500);
					json.put("msg", "Server error");
			    }		 
			}else {
				json.put("msg","Cart id is empty or invalid");
			}
		}else {
			response.setStatus(403);
			json.put("msg", "Invalid Session please log in first");
		}
		response.getWriter().print(json.toString());
	}

}
