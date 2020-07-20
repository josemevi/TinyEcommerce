package jdmv.code;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class GetItems
 */
@WebServlet("/getItems")
public class GetItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DBConnection con = new DBConnection();
    JSONObject json = new JSONObject();
    
    public GetItems() {
        super();
        
    }
    
    public void GetTemplate (String param, String resultName, HttpServletResponse response) {
    	System.out.println("SELECT * FROM items "+param);
    	json = new JSONObject();
    	if(con.execSql("SELECT * FROM items "+param) == 1) {
			JSONObject jsonRes = new JSONObject("{\""+resultName+"\":["+con.getData()+"]}");			
			response.setStatus(200);
			json.put(resultName, jsonRes.get(resultName));
		}else if (con.getData().length() == 0) {			
			response.setStatus(204);
			//json.put("msg", "There're no items in this category");
		}else {
			response.setStatus(500);
			json.put("msg", "Server Error");
		}
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String catalogId = request.getParameter("catalogId");
		String mode = request.getParameter("mode");
		System.out.println(mode);
		if(!con.checkString(mode)) {
			mode = "";
		}		
		switch(mode){
			case "highlights" : {
				GetTemplate ("WHERE highlight = true", "highlights",response);
				break;
			}
			case "male" : {
				GetTemplate("WHERE gender = 0","items",response);
				break;
			}
			case "female": {
				GetTemplate("WHERE gender = 1","items",response);
				break;
			}
			case "category": {
				if(con.checkString(catalogId)) {
					GetTemplate("WHERE catalog_id ="+catalogId, "items",response);					
				}else {
					json = new JSONObject();
					response.setStatus(400);
					json.put("msg", "catalog id is empty or invalid");
				}
				break;
			}
			default : {
				GetTemplate("", "items",response);
				break;
			}			
		}
		response.getWriter().print(json.toString());		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
