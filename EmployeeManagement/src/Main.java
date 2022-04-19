import java.awt.event.*;

// 사원 관리 프로그램
public class Main {
	
	// 생성자
	public Main() {
		// GUI 생성 클래스 호출
		new CreateGUI();
		
		// 테이블 이벤트 처리 - 마우스 이벤트 리스너 (익명 클래스)
		CreateGUI.empTable.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				// 셀 더블클릭시 선택한 행에 들어있는 속성을 JTextField에 표시함
				
				// JTable에 있는 값으로 가져오기
				if (e.getClickCount() == 2) {
					int selectedRow = CreateGUI.empTable.getSelectedRow();
					Object[] rowValue = new Object[8];
					for(int i=0; i<8; i++) 
						rowValue[i] = CreateGUI.empTable.getValueAt(selectedRow, i);
					CreateGUI.tfId.setText(rowValue[0].toString());
					CreateGUI.tfName.setText(rowValue[1].toString());
					CreateGUI.tfAge.setText(rowValue[2].toString());
					CreateGUI.tfAddress.setText(rowValue[3].toString());
					CreateGUI.tfDept.setText(rowValue[4].toString());
					CreateGUI.tfPosition.setText(rowValue[5].toString());
					CreateGUI.tfSalary.setText(rowValue[6].toString());
					CreateGUI.tfJoindate.setText(rowValue[7].toString());
				}
				
			}
		});
		
		// 버튼 이벤트 처리 - 액션 이벤트 리스너 (익명 클래스 람다 식)
		CreateGUI.btnInsert.addActionListener(e -> dbInsert());
		CreateGUI.btnUpdate.addActionListener(e -> dbUpdate());
		CreateGUI.btnLeave.addActionListener(e -> dbLeave());
		CreateGUI.btnDelete.addActionListener(e -> dbDelete());
		CreateGUI.btnClear.addActionListener(e -> tfClear());
		CreateGUI.btnExit.addActionListener(e -> System.exit(0));
		CreateGUI.btnSearch.addActionListener(e -> dbSearch());
		
		// 초기 실행시 모든 데이터 조회
		dbSelect();
	}

	// 삽입 메소드
	public void dbInsert() {
		// 데이터를 입력받는 JTextField로부터 입력을 희망하는 데이터를 가져와 변수에 대입
		String id = CreateGUI.tfId.getText().toString();
		String name = CreateGUI.tfName.getText().toString();
		String age = CreateGUI.tfAge.getText().toString();
		String address = CreateGUI.tfAddress.getText().toString();
		String dept = CreateGUI.tfDept.getText().toString();
		String position = CreateGUI.tfPosition.getText().toString();
		String salary = CreateGUI.tfSalary.getText().toString();
		String joindate = CreateGUI.tfJoindate.getText().toString();
		
		//
		DBQuery.insertQ(id, name, age, address, dept, position, salary, joindate);
		
		System.out.println("ID: " + id + " 이름: " + name + " - 입력 완료\n");
		
		// 데이터 삽입 완료 후 JTextField 초기화
		tfClear();
	}
	
	// 수정 메소드
	public void dbUpdate() {
		// TextField에 입력된 정보 저장
		String in_id = CreateGUI.tfId.getText().toString();
		String in_name = CreateGUI.tfName.getText().toString();
		String in_age = CreateGUI.tfAge.getText().toString();
		String in_address = CreateGUI.tfAddress.getText().toString();
		String in_dept = CreateGUI.tfDept.getText().toString();
		String in_position = CreateGUI.tfPosition.getText().toString();
		String in_salary = CreateGUI.tfSalary.getText().toString();
		String in_joindate = CreateGUI.tfJoindate.getText().toString();
			
		//
		DBQuery.updateQ(in_id, in_name, in_age, in_address, in_dept, in_position, in_salary, in_joindate);
			
		System.out.println("ID: " + in_id + " 이름: " + in_name + " - 수정 완료\n");
			
		// 데이터 수정 완료 후 JTextField 초기화
		tfClear();
	}
	
	// 퇴사 메소드
	public void dbLeave() {
		// 퇴사처리 할 사원의 ID 받아옴
		String inputid = CreateGUI.tfId.getText().toString();
		
		//
		DBQuery.leaveQ(inputid);
			
		System.out.println("ID: " + inputid + " - 퇴사처리 완료\n");
			
		// 퇴사처리 완료 후 JTextField 초기화
		tfClear();
	}
		
	// 삭제 메소드
	public void dbDelete() {
		// JTextField로부터 삭제를 희망하는 ID를 가져와 String타입 변수에 대입
		String inputid = CreateGUI.tfId.getText().toString();
			
		DBQuery.deleteQ(inputid);
			
		System.out.println("ID: " + inputid + " - 삭제 완료\n");
			
		// 데이터 삭제 완료 후 JTextField 초기화
		tfClear();
	}
	

	// 전체 조회 메소드
	public void dbSelect() {
		// JTable 클리어
		CreateGUI.model.setRowCount(0);

		// select 쿼리를 작동시킴
		DBQuery.selectQ();
	}
	
	// 조건 조회 메소드
	public void dbSearch() {
		// JTable 클리어
		CreateGUI.model.setRowCount(0);

		// JTextField에서 검색 키워드를 가져옴
		String searchText = CreateGUI.tfSearch.getText();
		String selectedOption = null;

		// 어느 JRadioButton이 선택 되어있는지에 따라 다르게 조회
		if(CreateGUI.rbId.isSelected()) {
			selectedOption = "Id";
		} else if(CreateGUI.rbName.isSelected()) {
			selectedOption = "Name";
		} else if(CreateGUI.rbDept.isSelected()) {
			selectedOption = "Dept";
		}

		// search 쿼리를 작동시킴
		DBQuery.searchQ(searchText, selectedOption);
	}
	
	// 데이터 입력, 검색 JTextField 초기화 메소드
	public void tfClear() {
		CreateGUI.tfId.setText("");
		CreateGUI.tfName.setText("");
		CreateGUI.tfAge.setText("");
		CreateGUI.tfAddress.setText("");
		CreateGUI.tfDept.setText("");
		CreateGUI.tfPosition.setText("");
		CreateGUI.tfSalary.setText("");
		CreateGUI.tfJoindate.setText("");
		CreateGUI.tfSearch.setText("");
		dbSelect();
	}
	
	public static void main(String[] args) {
		System.out.println("- Employee Management -\n");
		new Main();
	}
}
