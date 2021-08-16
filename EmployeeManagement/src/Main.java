import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

// ��� ���� ���α׷�
public class Main extends JFrame {
	private JButton btnInsert, btnDelete, btnUpdate, btnLeave, btnSelect, btnSearch, btnClear;
	private JTextField tfId, tfName, tfAge, tfAddress, tfDept, tfSalary, tfJoindate, tfSearch;
	private JRadioButton rbId, rbName, rbDept;
	Connection conn;
	PreparedStatement pstmt; /* Statement ��� PreparedStatement�� ����Ͽ� ������ ������ */
	ResultSet rs;
	
	// ��� ��� ȭ��� JTable
	String header[] = {"ID", "�̸�", "����", "�ּ�", "�μ�", "����", "�Ի���", "�����"};
	
	// JTable �� ���� �Ұ��� �ϵ��� ����
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	JTable empTable = new JTable(model);
	JScrollPane tblSP = new JScrollPane(empTable);
	
	// ������
	public Main() {
		// â ����
		super("��� ���� ���α׷�");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// GUI ���� �޼ҵ� ȣ��
		createGUI();
		
		// ���̺� �̺�Ʈ ó�� - ���콺 �̺�Ʈ ������ (�͸� Ŭ����)
		empTable.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				// �� ����Ŭ���� ������ �࿡ ����ִ� �Ӽ��� JTextField�� ǥ����
				
				// DB�� ����� �� ��������
//				if (e.getClickCount() == 2) {
//					var selectedRow = empTable.getValueAt(empTable.getSelectedRow(), 0);
//					try {
//						conn = DBConn.dbConnection();
//						// stmt = conn.createStatement();
//						String selectSQL = "select * from emp where empid = ?";
//						pstmt = conn.prepareStatement(selectSQL);
//						pstmt.setString(1, selectedRow);
//						rs = pstmt.executeQuery();
//						
//						while(rs.next()) {
//							tfId.setText(rs.getString("empid"));
//							tfName.setText(rs.getString("name"));
//							tfAge.setText(rs.getString("age"));
//							tfAddress.setText(rs.getString("address"));
//							tfDept.setText(rs.getString("dept"));
//							tfSalary.setText(rs.getString("salary"));
//							tfJoindate.setText(rs.getString("joindate"));
//						}
//						// stmt.close();
//						conn.close(); 
//						pstmt.close();
//						
//					} catch(Exception ex) {
//						ex.printStackTrace();
//						System.err.println("- ���̺� �̺�Ʈ ó�� ���� -");
//					}
//				}
				
				// JTable�� �ִ� ������ ��������
				// (JDBC ����� �ּ�ȭ �ϱ� ���� ���)
				if (e.getClickCount() == 2) {
					int selectedRow = empTable.getSelectedRow();
					Object rowValue[] = new Object[7];
					for(int i=0; i<7; i++) 
						rowValue[i] = empTable.getValueAt(selectedRow, i);
					tfId.setText(rowValue[0].toString());
					tfName.setText(rowValue[1].toString());
					tfAge.setText(rowValue[2].toString());
					tfAddress.setText(rowValue[3].toString());
					tfDept.setText(rowValue[4].toString());
					tfSalary.setText(rowValue[5].toString());
					tfJoindate.setText(rowValue[6].toString());
				}
				
			}
		});
		
		// ��ư �̺�Ʈ ó�� - �׼� �̺�Ʈ ������ (�͸� Ŭ����)
		btnInsert.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) { dbInsert(); }});
		btnUpdate.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) { dbUpdate(); }});
		btnLeave.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e)  { dbLeave(); }});
		btnDelete.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) { dbDelete(); }});
		btnSelect.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) { dbSelect(); }});
		btnClear.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e)  { tfClear(); }});
		btnSearch.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) { dbSearch(); }}); 
		
		// �ʱ� ����� ��� ������ ��ȸ
		dbSelect();
		
		// â ũ�� ���� �Ұ�
		setResizable(false);
		
		// â ũ�� ����
		setSize(850, 410);
		setVisible(true);
	}
	
	// GUI ���� �޼ҵ�
	public void createGUI() {
		// ���� JPanel c
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		// c�� ���� �г� pLeft
		// ������ �Է� �κ� & ��ư �κ��� ���� ���� JPanel
		JPanel pLeft = new JPanel();
		pLeft.setLayout(new BorderLayout());
		pLeft.setBorder(BorderFactory.createEmptyBorder(50, 5, 0, 5));
		
		// pLeft�� ���� �г� plNorth
		// ������ �Է� �κ� JPanel
		JPanel plNorth = new JPanel();
		plNorth.setLayout(new GridLayout(7, 2, 5, 5));
		plNorth.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 5));
		
		plNorth.add(new JLabel("�����ȣ"));
		tfId = new JTextField();
		plNorth.add(tfId);
		
		plNorth.add(new JLabel("�̸�"));
		tfName = new JTextField();
		plNorth.add(tfName);
		
		plNorth.add(new JLabel("����"));
		tfAge = new JTextField();
		plNorth.add(tfAge);
		
		plNorth.add(new JLabel("�ּ�"));
		tfAddress = new JTextField();
		plNorth.add(tfAddress);
		
		plNorth.add(new JLabel("�μ�"));
		tfDept = new JTextField();
		plNorth.add(tfDept);
		
		plNorth.add(new JLabel("����"));
		tfSalary = new JTextField();
		plNorth.add(tfSalary);
		
		plNorth.add(new JLabel("�Ի���  ex)2021-06-01"));
		tfJoindate = new JTextField();
		plNorth.add(tfJoindate);
		
		pLeft.add(plNorth, BorderLayout.NORTH);
		
		// pLeft�� �����г� plMiddle
		// ��ư + �˻� �κ� JPanel
		JPanel plMiddle = new JPanel();
		plMiddle.setLayout(new BorderLayout());
		
		// plMiddle�� ���� �г� plBtn
		// ��� ������ ���� ��ư�� ���� JPanel
		JPanel plBtn = new JPanel();
		plBtn.setLayout(new GridLayout(2, 3, 5, 5));
		btnInsert = new JButton("�Է�");
		btnUpdate = new JButton("����");
		btnDelete = new JButton("����");
		btnLeave = new JButton("���");
		btnSelect = new JButton("��ȸ");
		btnClear = new JButton("�����");
		plBtn.add(btnInsert);
		plBtn.add(btnUpdate);
		plBtn.add(btnDelete);
		plBtn.add(btnLeave);
		plBtn.add(btnSelect);
		plBtn.add(btnClear);
		
		// ���� �г� plMiddle�� �߰�
		plMiddle.add(plBtn, BorderLayout.NORTH);
		
		// ���� �г� pLeft�� �߰�
		pLeft.add(plMiddle, BorderLayout.CENTER);
		
		
		// c�� ���� �г� pRight
		// �˻� & ��ȸ JTable�� ���� ������ JPanel
		JPanel pRight = new JPanel();
		pRight.setLayout(new BorderLayout());
		
		// �˻� �κ��� ���� ���� JPanel
		tfSearch = new JTextField(18);
		rbId = new JRadioButton("�����ȣ", true);
		rbName = new JRadioButton("�̸�");
		rbDept = new JRadioButton("�μ�");
		btnSearch = new JButton("�˻�");
		ButtonGroup group = new ButtonGroup();
		group.add(rbId); group.add(rbName); group.add(rbDept);
		
		// prSeacrh�� ���� �г� pn1
		// pn1  = JTextField & JButton 
		JPanel pn1 = new JPanel();
		pn1.add(tfSearch);
		pn1.add(btnSearch);
		
		// prSearch�� ���� �г� pn2
		// pn2 = JRadioButton
		JPanel pn2 = new JPanel();
		pn2.add(rbId);
		pn2.add(rbName);
		pn2.add(rbDept);
		
		// pRight�� ���� �г� prSearch
		// �˻� �κ� ���� ����(pn1 + pn2)
		JPanel prSearch = new JPanel(new BorderLayout(0, 0));
		prSearch.add(BorderLayout.NORTH, pn1);
		prSearch.add(BorderLayout.CENTER, pn2);
		TitledBorder tb = new TitledBorder("�˻�");
		prSearch.setBorder(tb);
		
		// ���� �г� pRight�� �߰�
		pRight.add(prSearch, BorderLayout.NORTH);
			
		// pRight�� JTable ����
		pRight.add(tblSP, BorderLayout.CENTER);
		
		// JTable column ũ�� ����
		empTable.getColumn("ID").setPreferredWidth(40);
		empTable.getColumn("�̸�").setPreferredWidth(60);
		empTable.getColumn("����").setPreferredWidth(40);
		empTable.getColumn("�ּ�").setPreferredWidth(120);
		empTable.getColumn("����").setPreferredWidth(40);
		
		// JTable Column �̵�, ũ������ �Ұ�
		empTable.getTableHeader().setReorderingAllowed(false);
		empTable.getTableHeader().setResizingAllowed(false);
		
		// JTable ���� ��� ���� ����
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = empTable.getColumnModel();
		for(int i=0; i<empTable.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);
		
		// ���� �г� c�� ���� �гε� �߰�
		c.add(pLeft, BorderLayout.WEST);
		c.add(pRight, BorderLayout.CENTER);
	}
	
	// ���� SQL �޼ҵ�
	public void dbInsert() {
		try {
			conn = DBConn.dbConnection();
			
			// �����͸� �Է¹޴� JTextField�κ��� �Է��� ����ϴ� �����͸� ������ ������ ����
			String id = tfId.getText().toString();
			String name = tfName.getText().toString();
			String age = tfAge.getText().toString();
			String address = tfAddress.getText().toString();
			String dept = tfDept.getText().toString();
			String salary = tfSalary.getText().toString();
			String joindate = tfJoindate.getText().toString();
			
			// ������ ���� SQL�� ����
			String insertSQL = "insert into emp values(?, ?, ?, ?, ?, ?, ?, NULL);";
			pstmt = conn.prepareStatement(insertSQL);
			// SQL)) id, age, salary�� int �ڷ��������� varchar �ڷ����� ���� ����ǥ '' ������� �Էµ� �� ����
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, age);
			pstmt.setString(4, address);
			pstmt.setString(5, dept);
			pstmt.setString(6, salary);
			pstmt.setString(7, joindate);
			pstmt.executeUpdate();
			
			System.out.println("ID: " + id + " �̸�: " + name + " - �Է� �Ϸ�\n");
			
			// ������ ���� �Ϸ� �� JTextField �ʱ�ȭ
			tfClear();
			
			pstmt.close();
			conn.close();
			dbSelect();
			
		} catch (Exception e) {
			System.err.println("- �Է� ���� �߻� -");
			e.printStackTrace();
		}
	}
	
	// ���� SQL �޼ҵ�
	public void dbUpdate() {
		try {
			conn = DBConn.dbConnection();
			
			// DB���� ������ ������ �Ӽ��� ������ ����
			var name = ""; var age = ""; var address = ""; var dept = ""; 
			var salary  = ""; var joindate = "";
			
			// TextField�� �Էµ� ���� ����
			var in_id = tfId.getText().toString();
			var in_name = tfName.getText().toString();
			var in_age = tfAge.getText().toString();
			var in_address = tfAddress.getText().toString();
			var in_dept = tfDept.getText().toString();
			var in_salary = tfSalary.getText().toString();
			var in_joindate = tfJoindate.getText().toString();
			
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
				salary  = rs.getString("salary");
				joindate = rs.getString("joindate");
			}
			
			// ����ڰ� JTextField�� �Է��� ������ �ִ��� üũ �� �Է��� ������ �ִٸ� ���� �����Ϳ� ����� �� update ����
			// JTable �̺�Ʈ ó���� ���� ����� ������� �����͸� �����ϹǷ� �� ����� ȿ�������� ����
//			if(in_name.length() > 0) name = in_name; 
//			if(in_age.length() > 0) age = in_age;
//			if(in_address.length() > 0) address = in_address;
//			if(in_dept.length() > 0) dept = in_dept;
//			if(in_salary.length() > 0) salary = in_salary;
//			if(in_joindate.length() > 0) joindate = in_joindate;
			
			// ���ʿ��� ����⸦ ���̱� ���� ������ ���ǽ�
			if(!in_name.equals(name) && in_name.length() != 0) name = in_name;
			if(!in_age.equals(age) && in_age.length() != 0) age = in_age;
			if(!in_address.equals(address) && in_address.length() != 0) address = in_address;
			if(!in_dept.equals(dept) && in_dept.length() != 0) dept = in_dept;
			if(!in_salary.equals(salary) && in_salary.length() != 0) salary = in_salary;
			if(!in_joindate.equals(joindate) && in_joindate.length() != 0) joindate = in_joindate;
			
			String updateSQL = "update emp set name = ?, age = ?, address = ?, dept = ?, salary = ?, joindate = ? where empid = ?;";
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.setString(1, name);
			pstmt.setString(2, age);
			pstmt.setString(3, address);
			pstmt.setString(4, dept);
			pstmt.setString(5, salary);
			pstmt.setString(6, joindate);
			pstmt.setString(7, in_id);
			pstmt.executeUpdate();
			
			System.out.println("ID: " + in_id + " - ���� �Ϸ�\n");
			
			// ������ ���� �Ϸ� �� JTextField �ʱ�ȭ
			tfClear();
			
			rs.close();
			pstmt.close();
			conn.close();
			dbSelect();
		} catch (Exception e) {
			System.err.println("- ���� ���� �߻� -");
			e.printStackTrace();
		}
	}
	
	// ��� SQL �޼ҵ�
	public void dbLeave() {
		try {
			conn = DBConn.dbConnection();
			
			String inputid = tfId.getText().toString();
			
			// ���� ��¥�� ��� ó�� (YYYY-MM-DD)
			String updateSQL = "UPDATE emp SET leavedate = CURRENT_DATE() WHERE empid = ?";
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.setString(1, inputid);
			pstmt.executeUpdate();
			
			System.out.println("ID: " + inputid + " - ���ó�� �Ϸ�\n");
			
			// ���ó�� �Ϸ� �� JTextField �ʱ�ȭ
			tfClear();
			
			pstmt.close();
			conn.close();
			dbSelect();
		} catch(Exception e) {
			System.err.println("- ���ó�� ���� �߻� -");
			e.printStackTrace();
		}
	}
		
	// ���� SQL �޼ҵ�
	public void dbDelete() {
		try {
			conn = DBConn.dbConnection();
			
			// JTextField�κ��� ������ ����ϴ� ID�� ������ StringŸ�� ������ ����
			String inputid = tfId.getText().toString();
			
			// ������ ���� SQL�� ����
			String deleteSQL = "delete from emp where empid = ?";
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setString(1, inputid);
			pstmt.executeUpdate();
			
			System.out.println("ID: " + inputid + " - ���� �Ϸ�\n");
			
			// ������ ���� �Ϸ� �� JTextField �ʱ�ȭ
			tfClear();
			
			pstmt.close();
			conn.close();
			dbSelect();
		} catch (Exception e) {
			System.err.println("- ���� ���� �߻� -");
			e.printStackTrace();
		}
	}
	

	// ��ü ��ȸ SQL �޼ҵ�
	public void dbSelect() {
		try {
			// JTable Ŭ����
			model.setRowCount(0);
			
			conn = DBConn.dbConnection();
			
			// empid�� ���ڸ����� ǥ���ϱ� ���� LPAD()�̿�
			String selectSQL = "select LPAD(empid, 3, '0') as empid, name, age, address, dept, salary, joindate, leavedate from emp;";
			pstmt = conn.prepareStatement(selectSQL);
			
			// ResultSet�� ���� ������ �о��
			rs = pstmt.executeQuery();
			
			// next()�� �̿��� ������ �� ���� ������ ���
			while(rs.next()) {
				var id = rs.getString("empid");
				var name = rs.getString("name");
				var age = rs.getString("age");
				var address = rs.getString("address");
				var dept = rs.getString("dept");
				var salary  = rs.getString("salary");
				var joindate = rs.getString("joindate");
				var leavedate = rs.getString("leavedate");
				
				Object data[] = {id, name, age, address, dept, salary, joindate, leavedate};
				// �� �� �� ���̺� ������ ����
				model.addRow(data);
			}
			
			System.out.println("��ü ��ȸ �Ϸ�");
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("- ��ü ��ȸ ���� �߻� -");
			e.printStackTrace();
		}
	}
	
	// ���� ��ȸ SQL �޼ҵ�
	public void dbSearch() {
		try {
			// JTable Ŭ����
			model.setRowCount(0);
			
			conn = DBConn.dbConnection();
			
			// JTextField���� �˻� Ű���带 ������
			var searchText = tfSearch.getText().toString();
			String searchSQL = null;
			
			// ��� JRadioButton�� ���� �Ǿ��ִ����� ���� �ٸ��� ��ȸ
			// SQL)) empid�� ���ڸ����� ǥ���ϱ� ���� LPAD()�̿�
			if(rbId.isSelected()) {
				searchSQL = "select LPAD(empid, 3, '0') as empid, name, age, address, dept, salary, joindate, leavedate from emp where empid like '" + "%" + searchText + "%" + "';";
			} else if(rbName.isSelected()) {
				searchSQL = "select LPAD(empid, 3, '0') as empid, name, age, address, dept, salary, joindate, leavedate from emp where name like '" + "%" + searchText + "%" + "';";
			} else if(rbDept.isSelected()) {
				searchSQL = "select LPAD(empid, 3, '0') as empid, name, age, address, dept, salary, joindate, leavedate from emp where dept like '" + "%" + searchText + "%" + "';";
			}
			
			pstmt = conn.prepareStatement(searchSQL);
			// ResultSet�� ���� ������ �о��
			rs = pstmt.executeQuery();
			
			// next()�� �̿��� ������ �� ���� ������ ���
			while(rs.next()) {
				var id = rs.getString("empid");
				var name = rs.getString("name");
				var age = rs.getString("age");
				var address = rs.getString("address");
				var dept = rs.getString("dept");
				var salary  = rs.getString("salary");
				var joindate = rs.getString("joindate");
				var leavedate = rs.getString("leavedate");
							
				Object data[] = {id, name, age, address, dept, salary, joindate, leavedate};
				// �� �� �� ���̺� ������ ����
				model.addRow(data);
			}
			
			System.out.println("���� ��ȸ �Ϸ�");
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("- ���� ��ȸ ���� �߻� -");
			e.printStackTrace();
		}
	}
	
	// �Է� �ʵ� �ʱ�ȭ �޼ҵ�
	public void tfClear() {
		tfId.setText("");
		tfName.setText("");
		tfAge.setText("");
		tfAddress.setText("");
		tfDept.setText("");
		tfSalary.setText("");
		tfJoindate.setText("");
	}
	
	public static void main(String[] args) {
		System.out.println("- ��� ���� ���α׷� -\n");
		new Main();
	}
}
