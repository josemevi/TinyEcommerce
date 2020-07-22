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
    
    public void GetTemplate (String param, String catalogId, String resultName, String searchString, HttpServletResponse response) {    	
    	json = new JSONObject();
    	String query = "";
    	if(!con.checkString(searchString)){
    		searchString = "";
    	}
    	if(con.checkString(catalogId) && con.checkString(param)) {
    		query = "SELECT * FROM items "+param+" AND catalog_id="+catalogId+" AND LOWER(name) LIKE LOWER("+con.simpleQuoted("%"+searchString+"%")+")";
    	}else if (con.checkString(catalogId) && !con.checkString(param)) {
    		query = "SELECT * FROM items WHERE catalog_id="+catalogId+" AND LOWER(name) LIKE LOWER("+con.simpleQuoted("%"+searchString+"%")+")";
    	}else if (!con.checkString(catalogId) && con.checkString(param)) {
    		query = "SELECT * FROM items "+param+" AND LOWER(name) LIKE LOWER("+con.simpleQuoted("%"+searchString+"%")+")";     		
    	}else {
    		query ="SELECT * FROM items WHERE LOWER(name) LIKE LOWER("+con.simpleQuoted("%"+searchString+"%")+")";
    	}
    	System.out.println(query);
    	if(con.execSql(query) == 1) {
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
		String searchItems = request.getParameter("searchString");
		String mode = request.getParameter("mode");
		System.out.println(catalogId);		
		if(!con.checkString(mode)) {
			mode = "";
		}		
		switch(mode){
			case "highlights" : {
				GetTemplate ("WHERE highlight = true", catalogId, "highlights", searchItems, response);
				break;
			}
			case "male" : {
				GetTemplate("WHERE gender = 0", catalogId, "items", searchItems, response);
				break;
			}
			case "female": {
				GetTemplate("WHERE gender = 1", catalogId, "items", searchItems, response);
				break;
			}
			case "category" : {
				GetTemplate("", catalogId, "items", searchItems, response);
				break;
			}
			default : {
				GetTemplate("", "", "items", searchItems, response);
				break;
			}			
		}
		response.getWriter().print(json.toString());		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
