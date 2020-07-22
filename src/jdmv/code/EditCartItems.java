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
 * Servlet implementation class AddCartItem
 */
@WebServlet("/editCartItems")
public class EditCartItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DBConnection con = new DBConnection();    
    //JSONObject jsonRes = new JSONObject();
    
   
    public EditCartItems() {
        super();
       
    }
    
    /*public int CheckCart (String userId, String itemId) {
		String result = "";
    	try {
			result = "{\"items\":"+con.getJSONFromDB("SELECT items  FROM cart WHERE user_id="+userId);
			if(result.contains("\"item_id\": "+con.doubleQuoted(itemId))) {
				return 0;
			}else if (result.contains("null")) {
				return 1;
			}
		
		} catch (JSONException | SQLException e) {
		
			e.printStackTrace();
		}
    	return 2;
    }*/
    
    public boolean CartCreated (String user_id) {
		if(con.execSql("SELECT * FROM cart WHERE user_id= "+con.simpleQuoted(user_id)+" AND checkout=false") == 1 ) {
			return true;
		}else {
			
			return false;
		}			 
   }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		JSONObject requestB = con.retrieveJson(request);
		//String items = request.getParameter("items");
		String items = requestB.get("items").toString();		
		JSONObject json = new JSONObject();
		if(session != null) {		
			if(CartCreated(session.getAttribute("userId").toString())) {				
				if(con.execSql("UPDATE cart SET items="+con.simpleQuoted(items)+" WHERE user_id="+session.getAttribute("userId")+" AND checkout=false") == 1){					
					response.setStatus(200);
					json.put("msg", "Cart Modified");
					session.setAttribute("cart", requestB.get("items"));
			    }else{
			    	response.setStatus(500);
					json.put("msg", "Server error");
			    }		
			}else {
				if(con.execSql("INSERT INTO cart values (DEFAULT, "+con.simpleQuoted(items)+", "+" DEFAULT, "+session.getAttribute("userId")+")") == 1){
					response.setStatus(201);
					json.put("msg", "Cart Created");
					session.setAttribute("cart", requestB.get("items"));
			    }else{
			    	response.setStatus(500);
					json.put("msg", "Server error");
			    }		
			}
		}else {
			response.setStatus(403);
			json.put("msg", "Invalid Session please log in first");
		}
		response.getWriter().print(json.toString());	
	}

}
