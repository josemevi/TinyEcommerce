package jdmv.code;

public class Tese {
	
	static DBConnection Dbc = new DBConnection();
	
	
	public static boolean CheckString(String var) {
		if(var != null && !var.isEmpty() && !var.equals("undefined")) {
			return true;
		}else {
			return false;
		}				
	}

	public static void main(String[] args) {
		//Dbc.execSql("SELECT email, password FROM users WHERE email= 'admin'");
		//System.out.println(Dbc.getData());
		/*System.out.println("INSERT INTO users (document_type, document_number, direction, ccNumber) "
				+ "VALUES ("+document_type+","+con.simpleQuoted(document_number)+","
				+ con.simpleQuoted(direction) +","+ccNumber+") WHERE user_id="
				+ session.getAttribute("userId"));
		System.out.println(CheckString("undefined"));
		 */
	}

}
