package jdmv.code;

import java.sql.SQLException;

public class Tese {
	
	static DBConnection Dbc = new DBConnection();
	
	
	public static boolean CheckString(String var) {
		if(var != null && !var.isEmpty() && !var.equals("undefined")) {
			return true;
		}else {
			return false;
		}				
	}

	public static void main(String[] args) throws SQLException {
		System.out.println(Dbc.getJSONFromDB("SELECT items  FROM cart WHERE user_id=5"));		
		//System.out.println(Dbc.getData());
		/*System.out.println("INSERT INTO users (document_type, document_number, direction, ccNumber) "
				+ "VALUES ("+document_type+","+con.simpleQuoted(document_number)+","
				+ con.simpleQuoted(direction) +","+ccNumber+") WHERE user_id="
				+ session.getAttribute("userId"));
		System.out.println(CheckString("undefined"));
		 */
	}

}
