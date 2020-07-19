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
		System.out.println(CheckString("undefined"));

	}

}
