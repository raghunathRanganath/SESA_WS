package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PhysicianList extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());

		out.writeObject(getPhysicianList());
		out.close();
	}

	private ArrayList<String> getPhysicianList() {
		ArrayList<String> physicianList = new ArrayList<String>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sesa_dba", "sesa123");

			PreparedStatement ps = con
					.prepareStatement("select physician_name from master_physician");

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				physicianList.add(rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return physicianList;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
