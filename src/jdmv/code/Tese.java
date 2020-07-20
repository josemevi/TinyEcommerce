package jdmv.code;

import java.sql.SQLException;

import org.json.JSONException;

public class Tese {
	
	static DBConnection con = new DBConnection();
	
	
	/*public static Boolean GetCartJson (String userId, String itemId) {
		String result = "";
    	try {
			result = "{\"items\":"+con.getJSONFromDB("SELECT items  FROM cart WHERE user_id="+userId);
			if(result.contains("\"item_id\": "+con.doubleQuoted(itemId))) {
				return true;
			}
		
		} catch (JSONException | SQLException e) {
		
			e.printStackTrace();
		}
    	return false;
    }*/

	public static void main(String[] args) throws SQLException {
		//System.out.println(Dbc.getJSONFromDB("SELECT items  FROM cart WHERE user_id=5"));		
		//System.out.println(Dbc.getData());
		/*System.out.println("INSERT INTO users (document_type, document_number, direction, ccNumber) "
				+ "VALUES ("+document_type+","+con.simpleQuoted(document_number)+","
				+ con.simpleQuoted(direction) +","+ccNumber+") WHERE user_id="
				+ session.getAttribute("userId"));
		System.out.println(CheckString("undefined"));
		 */
		//System.out.println(GetCartJson("5","4"));
		
	}

}
