package application;

import java.sql.*;

public class ConnexionMysql {
	
	public Connection cn=null;
	public static Connection ConnexionDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		Connection	cn= DriverManager.getConnection("jdbc:mysql://localhost:3306/busdevoyage","root","");
		System.out.println("cnx reussite");
		return cn;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("connexion echouee");
			e.printStackTrace();
			return null;
		}
	}
}
