import java.sql.*;

public class DBQuery {
	static Connection conn = DBConn.dbConnection();
	static PreparedStatement pstmt; 
	static ResultSet rs;
	
	// 삽입 SQL 메소드
	public static void insertQ(String id, String name, String age, String address, String dept, String position, String salary, String joindate) {
		try {
			// 데이터 삽입 SQL문 실행
			String insertSQL = "insert into emp values(?, ?, ?, ?, ?, ?, ?, ?, NULL);";
			pstmt = conn.prepareStatement(insertSQL);
			// SQL)) id, age, salary는 int 자료형이지만 varchar 자료형과 같은 따옴표 '' 방식으로 입력될 수 있음
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
			System.err.println("- 입력 에러 발생 -");
			e.printStackTrace();
		}
	}
	
	// 수정 SQL 메소드
	public static void updateQ(String in_id, String in_name, String in_age, String in_address, String in_dept, String in_position, String in_salary, String in_joindate) {
		// DB에서 가져올 데이터 속성을 저장할 변수
		String name = ""; String age = ""; String address = ""; String dept = ""; 
		String position = ""; String salary  = ""; String joindate = "";
		
		try {
			String selectSQL = "select * from emp where empid = ?";
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, in_id);
			// ResultSet를 통해 데이터 읽어옴
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
			
			// 사용자가 JTextField에 새로운 데이터를 입력했는지 확인
			// 불필요한 덮어쓰기를 줄이기 위해 개선된 조건식
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
			System.err.println("- 수정 에러 발생 -");
			e.printStackTrace();
		}
	}
		
	// 퇴사 SQL 메소드
	public static void leaveQ(String userID) {
		try {
			// 현재 날짜로 퇴사 처리 (YYYY-MM-DD)
			String updateSQL = "UPDATE emp SET leavedate = CURRENT_DATE() WHERE empid = ?";
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.setString(1, userID);
			pstmt.executeUpdate();
			
//			pstmt.close(); conn.close();
		} catch (Exception e) {
			System.err.println("- 퇴사처리 에러 발생 -");
			e.printStackTrace();
		}
	}
	
	// 삭제 SQL 메소드
	public static void deleteQ(String userId) {
		try {
			// 데이터 삭제 SQL문 실행
			String deleteSQL = "delete from emp where empid = " + userId;
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.executeUpdate();
			
//			rs.close(); pstmt.close(); conn.close();
		} catch (Exception e) {
			System.err.println("- 삭제 에러 발생 -");
			e.printStackTrace();
		}
	}
	
	// 전체 조회 SQL 메소드
	public static ResultSet selectQ() {
		try {
			// empid를 세자릿수로 표시하기 위해 LPAD()이용
			String selectSQL = "select LPAD(empid, 3, '0') as empid, name, age, address, dept, position, salary, joindate, leavedate from emp;";
			pstmt = conn.prepareStatement(selectSQL);
			
			// ResultSet를 통해 데이터 읽어옴
			rs = pstmt.executeQuery();
		} 
		catch(Exception e) {
			System.err.println("- 조회 에러 발생 -");
			e.printStackTrace();
		}
		// 데이터가 담긴 ResultSet 리턴
		return rs;
	}
	
	// 조건 조회 SQL 메소드
	public static ResultSet searchQ(String searchText, String selectedOption) {
		try {
			String searchSQL = null;
			// 어느 JRadioButton이 선택 되어있는지에 따라 다르게 조회
			// SQL)) empid를 세자릿수로 표사하기 위해 LPAD()이용
			if(selectedOption == "Id") {
				searchSQL = "select LPAD(empid, 3, '0') as empid, name, age, address, dept, position, salary, joindate, leavedate from emp where empid like '" + "%" + searchText + "%" + "';";
			} else if(selectedOption == "Name") {
				searchSQL = "select LPAD(empid, 3, '0') as empid, name, age, address, dept, position, salary, joindate, leavedate from emp where name like '" + "%" + searchText + "%" + "';";
			} else if(selectedOption == "Dept") {
				searchSQL = "select LPAD(empid, 3, '0') as empid, name, age, address, dept, position, salary, joindate, leavedate from emp where dept like '" + "%" + searchText + "%" + "';";
			}
			pstmt = conn.prepareStatement(searchSQL);
			
			// ResultSet를 통해 데이터 읽어옴
			rs = pstmt.executeQuery();
		}
		catch(Exception e) {
			System.err.println("- 조건 조회 에러 발생 -");
			e.printStackTrace();
		}
		
		// 데이터가 담긴 ResultSet 리턴
		return rs;
	}
}
