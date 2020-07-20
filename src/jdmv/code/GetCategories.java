package jdmv.code;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class getCategories
 */
@WebServlet("/getCategories")
public class GetCategories extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DBConnection con = new DBConnection();
    
    
    public GetCategories() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject json = new JSONObject();		 
		if(con.execSql("SELECT * FROM catalog") == 1) {
			JSONObject jsonRes = new JSONObject("{\"categories\":["+con.getData()+"]}");			
			response.setStatus(200);
			json.put("categories", jsonRes.get("categories"));
		}else {			
			response.setStatus(500);
			json.put("msg", "Server Error");
		}
		response.getWriter().print(json.toString());		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
