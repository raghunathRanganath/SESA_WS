package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegEmgCntDetails extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());

		String patientId = request.getParameter("pid");
		String edtPtContactNo = request.getParameter("edtPtContactNo");
		String edEmgContactPersonName = request.getParameter("edEmgContactPersonName");
		String edtEmgContactNo = request.getParameter("edtEmgContactNo");
		String edtPtAge = request.getParameter("edtPtAge");
		String edtPtBloodGrp = request.getParameter("edtPtBloodGrp");

		System.out.println(edtPtContactNo);
		System.out.println(patientId);
		System.out.println(edEmgContactPersonName);
		System.out.println(edtEmgContactNo);

		if (registration(patientId, edtPtContactNo, edEmgContactPersonName, edtEmgContactNo, edtPtAge, edtPtBloodGrp)) {
			out.writeObject("Successfully Registered");
		} else {
			out.writeObject("Registration cannot be created");
		}

		out.close();
	}


	private boolean registration(String patientId, String edtPtContactNo, String edEmgContactPersonName, String edtEmgContactNo, String edtPtAge, String edtPtBloodGrp) {
		boolean status = false;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sesa_dba", "sesa123");

			String sqlQuery = " update master_patient "
					+ "set  contact_number=?, "
					+ " emergency_contact = ?, "
					+ " emergency_contact_number = ?, "
					+ " patient_age = ?, "
					+ " patient_blood_group = ?"
					+ " where patient_id= ?";

			PreparedStatement ps = con
					.prepareStatement(sqlQuery);
			ps.setString(1, edtPtContactNo);
			ps.setString(2, edEmgContactPersonName);
			ps.setString(3, edtEmgContactNo);
			ps.setString(4, edtPtAge);
			ps.setString(5, edtPtBloodGrp);
			ps.setString(6, patientId);

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
