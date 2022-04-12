import java.sql.*;

public class DBQuery {
	static Connection conn = DBConn.dbConnection();
	static PreparedStatement pstmt; 
	static ResultSet rs;
	
	// ���� SQL �޼ҵ�
	public static void insertQ(String id, String name, String age, String address, String dept, String position, String salary, String joindate) {
		try {
			// ������ ���� SQL�� ����
			String insertSQL = "insert into emp values(?, ?, ?, ?, ?, ?, ?, ?, NULL);";
			pstmt = conn.prepareStatement(insertSQL);
			// SQL)) id, age, salary�� int �ڷ��������� varchar �ڷ����� ���� ����ǥ '' ������� �Էµ� �� ����
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, age);
			pstmt.setString(4, address);
			pstmt.setString(5, dept);
			pstmt.setString(6, position);
			pstmt.setString(7, salary);
			pstmt.setString(8, joindate);
			pstmt.executeUpdate();
			
//			rs.close(); pstmt.close(); conn.close();
		} catch (Exception e) {
			System.err.println("- �Է� ���� �߻� -");
			e.printStackTrace();
		}
	}
	
	// ���� SQL �޼ҵ�
	public static void updateQ(String in_id, String in_name, String in_age, String in_address, String in_dept, String in_position, String in_salary, String in_joindate) {
		// DB���� ������ ������ �Ӽ��� ������ ����
		String name = ""; String age = ""; String address = ""; String dept = ""; 
		String position = ""; String salary  = ""; String joindate = "";
		
		try {
			String selectSQL = "select * from emp where empid = ?";
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, in_id);
			// ResultSet�� ���� ������ �о��
			rs = pstmt.executeQuery();
			while(rs.next()) {
				name = rs.getString("name");
				age = rs.getString("age");
				address = rs.getString("address");
				dept = rs.getString("dept");
				position = rs.getString("position");
				salary  = rs.getString("salary");
				joindate = rs.getString("joindate");
			}
			
			// ����ڰ� JTextField�� ���ο� �����͸� �Է��ߴ��� Ȯ��
			// ���ʿ��� ����⸦ ���̱� ���� ������ ���ǽ�
			if(!in_name.equals(name) && in_name.length() != 0) name = in_name;
			if(!in_age.equals(age) && in_age.length() != 0) age = in_age;
			if(!in_address.equals(address) && in_address.length() != 0) address = in_address;
			if(!in_dept.equals(dept) && in_dept.length() != 0) dept = in_dept;
			if(!in_position.equals(position) && in_position.length() != 0) position = in_position;
			if(!in_salary.equals(salary) && in_salary.length() != 0) salary = in_salary;
			if(!in_joindate.equals(joindate) && in_joindate.length() != 0) joindate = in_joindate;
			
			String updateSQL = "update emp set name = ?, age = ?, address = ?, dept = ?, position = ?, salary = ?, joindate = ? where empid = ?;";
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.setString(1, name);
			pstmt.setString(2, age);
			pstmt.setString(3, address);
			pstmt.setString(4, dept);
			pstmt.setString(5, position);
			pstmt.setString(6, salary);
			pstmt.setString(7, joindate);
			pstmt.setString(8, in_id);
			pstmt.executeUpdate();
			
//			rs.close(); pstmt.close(); conn.close();
		} catch (Exception e) {
			System.err.println("- ���� ���� �߻� -");
			e.printStackTrace();
		}
	}
		
	// ��� SQL �޼ҵ�
	public static void leaveQ(String userID) {
		try {
			// ���� ��¥�� ��� ó�� (YYYY-MM-DD)
			String updateSQL = "UPDATE emp SET leavedate = CURRENT_DATE() WHERE empid = ?";
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.setString(1, userID);
			pstmt.executeUpdate();
			
//			pstmt.close(); conn.close();
		} catch (Exception e) {
			System.err.println("- ���ó�� ���� �߻� -");
			e.printStackTrace();
		}
	}
	
	// ���� SQL �޼ҵ�
	public static void deleteQ(String userId) {
		try {
			// ������ ���� SQL�� ����
			String deleteSQL = "delete from emp where empid = " + userId;
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.executeUpdate();
			
//			rs.close(); pstmt.close(); conn.close();
		} catch (Exception e) {
			System.err.println("- ���� ���� �߻� -");
			e.printStackTrace();
		}
	}
	
	// ��ü ��ȸ SQL �޼ҵ�
	public static ResultSet selectQ() {
		try {
			// empid�� ���ڸ����� ǥ���ϱ� ���� LPAD()�̿�
			String selectSQL = "select LPAD(empid, 3, '0') as empid, name, age, address, dept, position, salary, joindate, leavedate from emp;";
			pstmt = conn.prepareStatement(selectSQL);
			
			// ResultSet�� ���� ������ �о��
			rs = pstmt.executeQuery();
		} 
		catch(Exception e) {
			System.err.println("- ��ȸ ���� �߻� -");
			e.printStackTrace();
		}
		// �����Ͱ� ��� ResultSet ����
		return rs;
	}
	
	// ���� ��ȸ SQL �޼ҵ�
	public static ResultSet searchQ(String searchText, String selectedOption) {
		try {
			String searchSQL = null;
			// ��� JRadioButton�� ���� �Ǿ��ִ����� ���� �ٸ��� ��ȸ
			// SQL)) empid�� ���ڸ����� ǥ���ϱ� ���� LPAD()�̿�
			if(selectedOption == "Id") {
				searchSQL = "select LPAD(empid, 3, '0') as empid, name, age, address, dept, position, salary, joindate, leavedate from emp where empid like '" + "%" + searchText + "%" + "';";
			} else if(selectedOption == "Name") {
				searchSQL = "select LPAD(empid, 3, '0') as empid, name, age, address, dept, position, salary, joindate, leavedate from emp where name like '" + "%" + searchText + "%" + "';";
			} else if(selectedOption == "Dept") {
				searchSQL = "select LPAD(empid, 3, '0') as empid, name, age, address, dept, position, salary, joindate, leavedate from emp where dept like '" + "%" + searchText + "%" + "';";
			}
			pstmt = conn.prepareStatement(searchSQL);
			
			// ResultSet�� ���� ������ �о��
			rs = pstmt.executeQuery();
		}
		catch(Exception e) {
			System.err.println("- ���� ��ȸ ���� �߻� -");
			e.printStackTrace();
		}
		
		// �����Ͱ� ��� ResultSet ����
		return rs;
	}
}
