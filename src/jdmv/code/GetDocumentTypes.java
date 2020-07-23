package jdmv.code;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONObject;

/**
 * This servlet provides all the document_types inside the "document_types" table. I decided to do this in order to adapt more identifications
 * types in the future, or in the case the current document types doesn't match in the current country (e.g In Venezuela is used CI instead of DNI / NIE)
 * this make the system scalable and easy adaptive.
 */
@WebServlet("/getDocumentTypes")
public class GetDocumentTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBConnection con = new DBConnection();

       
  
    public GetDocumentTypes() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject json = new JSONObject();		 
		if(con.execSql("SELECT * FROM document_types") == 1) {
			JSONObject jsonRes = new JSONObject("{\"documents\":["+con.getData()+"]}");			
			response.setStatus(200);
			json.put("documents", jsonRes.get("documents"));
		}else {			
			response.setStatus(500);
			json.put("msg", "Server Error");
		}
		response.getWriter().print(json.toString());		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
