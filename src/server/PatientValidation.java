package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PatientValidation extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());

		String patientName = request.getParameter("pname");
		String patientId = request.getParameter("pid");
		System.out.println(patientName);
		System.out.println(patientId);

		if (validate(patientName, patientId)) {
			out.writeObject("SESA-001");

		} else {
			out.writeObject("SESA-009");
		}

		out.close();
	}

	private boolean validate(String patientName, String patientId) {
		boolean status = false;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sesa_dba", "sesa123");

			PreparedStatement ps = con
					.prepareStatement("select * from master_patient where patient_name=? and patient_id=?");
			ps.setString(1, patientName);
			ps.setString(2, patientId);

			ResultSet rs = ps.executeQuery();
			status = rs.next();

		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
