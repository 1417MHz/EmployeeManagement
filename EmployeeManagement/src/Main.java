import java.awt.event.*;

// ��� ���� ���α׷�
public class Main {
	
	// ������
	public Main() {
		// GUI ���� Ŭ���� ȣ��
		new CreateGUI();
		
		// ���̺� �̺�Ʈ ó�� - ���콺 �̺�Ʈ ������ (�͸� Ŭ����)
		CreateGUI.empTable.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				// �� ����Ŭ���� ������ �࿡ ����ִ� �Ӽ��� JTextField�� ǥ����
				
				// JTable�� �ִ� ������ ��������
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
		
		// ��ư �̺�Ʈ ó�� - �׼� �̺�Ʈ ������ (�͸� Ŭ���� ���� ��)
		CreateGUI.btnInsert.addActionListener(e -> dbInsert());
		CreateGUI.btnUpdate.addActionListener(e -> dbUpdate());
		CreateGUI.btnLeave.addActionListener(e -> dbLeave());
		CreateGUI.btnDelete.addActionListener(e -> dbDelete());
		CreateGUI.btnClear.addActionListener(e -> tfClear());
		CreateGUI.btnExit.addActionListener(e -> System.exit(0));
		CreateGUI.btnSearch.addActionListener(e -> dbSearch());
		
		// �ʱ� ����� ��� ������ ��ȸ
		dbSelect();
	}

	// ���� �޼ҵ�
	public void dbInsert() {
		// �����͸� �Է¹޴� JTextField�κ��� �Է��� ����ϴ� �����͸� ������ ������ ����
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
		
		System.out.println("ID: " + id + " �̸�: " + name + " - �Է� �Ϸ�\n");
		
		// ������ ���� �Ϸ� �� JTextField �ʱ�ȭ
		tfClear();
	}
	
	// ���� �޼ҵ�
	public void dbUpdate() {
		// TextField�� �Էµ� ���� ����
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
			
		System.out.println("ID: " + in_id + " �̸�: " + in_name + " - ���� �Ϸ�\n");
			
		// ������ ���� �Ϸ� �� JTextField �ʱ�ȭ
		tfClear();
	}
	
	// ��� �޼ҵ�
	public void dbLeave() {
		// ���ó�� �� ����� ID �޾ƿ�
		String inputid = CreateGUI.tfId.getText().toString();
		
		//
		DBQuery.leaveQ(inputid);
			
		System.out.println("ID: " + inputid + " - ���ó�� �Ϸ�\n");
			
		// ���ó�� �Ϸ� �� JTextField �ʱ�ȭ
		tfClear();
	}
		
	// ���� �޼ҵ�
	public void dbDelete() {
		// JTextField�κ��� ������ ����ϴ� ID�� ������ StringŸ�� ������ ����
		String inputid = CreateGUI.tfId.getText().toString();
			
		DBQuery.deleteQ(inputid);
			
		System.out.println("ID: " + inputid + " - ���� �Ϸ�\n");
			
		// ������ ���� �Ϸ� �� JTextField �ʱ�ȭ
		tfClear();
	}
	

	// ��ü ��ȸ �޼ҵ�
	public void dbSelect() {
		// JTable Ŭ����
		CreateGUI.model.setRowCount(0);

		// select ������ �۵���Ŵ
		DBQuery.selectQ();
	}
	
	// ���� ��ȸ �޼ҵ�
	public void dbSearch() {
		// JTable Ŭ����
		CreateGUI.model.setRowCount(0);

		// JTextField���� �˻� Ű���带 ������
		String searchText = CreateGUI.tfSearch.getText();
		String selectedOption = null;

		// ��� JRadioButton�� ���� �Ǿ��ִ����� ���� �ٸ��� ��ȸ
		if(CreateGUI.rbId.isSelected()) {
			selectedOption = "Id";
		} else if(CreateGUI.rbName.isSelected()) {
			selectedOption = "Name";
		} else if(CreateGUI.rbDept.isSelected()) {
			selectedOption = "Dept";
		}

		// search ������ �۵���Ŵ
		DBQuery.searchQ(searchText, selectedOption);
	}
	
	// ������ �Է�, �˻� JTextField �ʱ�ȭ �޼ҵ�
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
