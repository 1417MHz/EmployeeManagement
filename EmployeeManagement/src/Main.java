import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

// ��� ���� ���α׷�
public class Main extends JFrame {
	public JButton btnInsert, btnUpdate, btnDelete, btnLeave, btnClear, btnExit,btnSearch;
	public JTextField tfId, tfName, tfAge, tfAddress, tfDept, tfPosition, tfSalary, tfJoindate, tfSearch;
	public JRadioButton rbId, rbName, rbDept;
//	Connection conn;
//	PreparedStatement pstmt; /* Statement ��� PreparedStatement�� ����Ͽ� ������ ������ */
	ResultSet rs;
	
	// ��� ��� ȭ��� JTable
	String header[] = {"ID", "�̸�", "����", "�ּ�", "�μ�", "����", "����", "�Ի���", "�����"};
	
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
				
				// JTable�� �ִ� ������ ��������
				if (e.getClickCount() == 2) {
					int selectedRow = empTable.getSelectedRow();
					Object rowValue[] = new Object[8];
					for(int i=0; i<8; i++) 
						rowValue[i] = empTable.getValueAt(selectedRow, i);
					tfId.setText(rowValue[0].toString());
					tfName.setText(rowValue[1].toString());
					tfAge.setText(rowValue[2].toString());
					tfAddress.setText(rowValue[3].toString());
					tfDept.setText(rowValue[4].toString());
					tfPosition.setText(rowValue[5].toString());
					tfSalary.setText(rowValue[6].toString());
					tfJoindate.setText(rowValue[7].toString());
				}
				
			}
		});
		
		// ��ư �̺�Ʈ ó�� - �׼� �̺�Ʈ ������ (�͸� Ŭ����)
		btnInsert.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) { dbInsert(); }});
		btnUpdate.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) { dbUpdate(); }});
		btnLeave.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e)  { dbLeave(); }});
		btnDelete.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) { dbDelete(); }});
		btnClear.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e)  { tfClear(); }});
		btnExit.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) { System.exit(0); }});
		btnSearch.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) { dbSearch(); }}); 
		
		// �ʱ� ����� ��� ������ ��ȸ
		dbSelect();
		
		// â ũ�� ���� �Ұ�
		setResizable(false);
		
		// â ũ�� ����
		setSize(900, 410);
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
		pLeft.setBorder(BorderFactory.createEmptyBorder(40, 5, 0, 5));
		
		// pLeft�� ���� �г� plNorth
		// ������ �Է� �κ� JPanel
		JPanel plNorth = new JPanel();
		plNorth.setLayout(new GridLayout(8, 2, 5, 5));
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
		tfPosition = new JTextField();
		plNorth.add(tfPosition);
		
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
		btnInsert = new JButton("�Է�"); btnInsert.setBackground(new Color(255, 255, 255));
		btnUpdate = new JButton("����"); btnUpdate.setBackground(new Color(255, 255, 255));
		btnDelete = new JButton("����"); btnDelete.setBackground(new Color(255, 255, 255));
		btnLeave = new JButton("���"); btnLeave.setBackground(new Color(255, 255, 255));
		btnClear = new JButton("�ʱ�ȭ"); btnClear.setBackground(new Color(255, 255, 255));
		btnExit = new JButton("����"); btnExit.setBackground(new Color(220, 220, 220));
		plBtn.add(btnInsert);
		plBtn.add(btnUpdate);
		plBtn.add(btnDelete);
		plBtn.add(btnLeave);
		plBtn.add(btnClear);
		plBtn.add(btnExit);
		
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
		btnSearch.setBackground(new Color(255, 255, 255));
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
		empTable.getColumn("����").setPreferredWidth(50);
		
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
	
	// ���� �޼ҵ�(set)
	public void dbInsert() {
		// �����͸� �Է¹޴� JTextField�κ��� �Է��� ����ϴ� �����͸� ������ ������ ����
		String id = tfId.getText().toString();
		String name = tfName.getText().toString();
		String age = tfAge.getText().toString();
		String address = tfAddress.getText().toString();
		String dept = tfDept.getText().toString();
		String position = tfPosition.getText().toString();
		String salary = tfSalary.getText().toString();
		String joindate = tfJoindate.getText().toString();
		
		//
		DBQuery.insertQ(id, name, age, address, dept, position, salary, joindate);
		
		System.out.println("ID: " + id + " �̸�: " + name + " - �Է� �Ϸ�\n");
		
		// ������ ���� �Ϸ� �� JTextField �ʱ�ȭ
		tfClear();
		
//		pstmt.close(); conn.close();
	}
	
	// ���� �޼ҵ�(set)
	public void dbUpdate() {
		// TextField�� �Էµ� ���� ����
		String in_id = tfId.getText().toString();
		String in_name = tfName.getText().toString();
		String in_age = tfAge.getText().toString();
		String in_address = tfAddress.getText().toString();
		String in_dept = tfDept.getText().toString();
		String in_position = tfPosition.getText().toString();
		String in_salary = tfSalary.getText().toString();
		String in_joindate = tfJoindate.getText().toString();
			
		//
		DBQuery.updateQ(in_id, in_name, in_age, in_address, in_dept, in_position, in_salary, in_joindate);
			
		System.out.println("ID: " + in_id + " �̸�: " + in_name + " - ���� �Ϸ�\n");
			
		// ������ ���� �Ϸ� �� JTextField �ʱ�ȭ
		tfClear();
	}
	
	// ��� �޼ҵ�(set)
	public void dbLeave() {
		// ���ó�� �� ����� ID �޾ƿ�
		String inputid = tfId.getText().toString();
		
		//
		DBQuery.leaveQ(inputid);
			
		System.out.println("ID: " + inputid + " - ���ó�� �Ϸ�\n");
			
		// ���ó�� �Ϸ� �� JTextField �ʱ�ȭ
		tfClear();
	}
		
	// ���� �޼ҵ�(set)
	public void dbDelete() {
		// JTextField�κ��� ������ ����ϴ� ID�� ������ StringŸ�� ������ ����
		String inputid = tfId.getText().toString();
			
		DBQuery.deleteQ(inputid);
			
		System.out.println("ID: " + inputid + " - ���� �Ϸ�\n");
			
		// ������ ���� �Ϸ� �� JTextField �ʱ�ȭ
		tfClear();
	}
	

	// ��ü ��ȸ �޼ҵ�(get)
	public void dbSelect() {
		try {
			// JTable Ŭ����
			model.setRowCount(0);
			
			// select ������ �۵����� ResultSet�� �����͸� ���� �޾ƿ�
			rs = DBQuery.selectQ();
			
			// next()�� �̿��� ������ �� ���� ������ ���
			while(rs.next()) {
				String id = rs.getString("empid");
				String name = rs.getString("name");
				String age = rs.getString("age");
				String address = rs.getString("address");
				String dept = rs.getString("dept");
				String position = rs.getString("position");
				String salary  = rs.getString("salary");
				String joindate = rs.getString("joindate");
				String leavedate = rs.getString("leavedate");
				
				Object data[] = {id, name, age, address, dept, position, salary, joindate, leavedate};
				// �� �� �� ���̺� ������ ����
				model.addRow(data);
			}
			
		} catch (Exception e) {
			System.err.println("- ��ü ��ȸ ���� �߻� -");
			e.printStackTrace();
		}
	}
	
	// ���� ��ȸ �޼ҵ�(get)
	public void dbSearch() {
		try {
			// JTable Ŭ����
			model.setRowCount(0);
			
			// JTextField���� �˻� Ű���带 ������
			String searchText = tfSearch.getText().toString();
			String selectedOption = null;
			
			// ��� JRadioButton�� ���� �Ǿ��ִ����� ���� �ٸ��� ��ȸ
			if(rbId.isSelected()) {
				selectedOption = "Id";
			} else if(rbName.isSelected()) {
				selectedOption = "Name";
			} else if(rbDept.isSelected()) {
				selectedOption = "Dept";
			}
			
			rs = DBQuery.searchQ(searchText, selectedOption);
			
			// next()�� �̿��� ������ �� ���� ������ ���
			while(rs.next()) {
				String id = rs.getString("empid");
				String name = rs.getString("name");
				String age = rs.getString("age");
				String address = rs.getString("address");
				String dept = rs.getString("dept");
				String position = rs.getString("position");
				String salary  = rs.getString("salary");
				String joindate = rs.getString("joindate");
				String leavedate = rs.getString("leavedate");
							
				Object data[] = {id, name, age, address, dept, position, salary, joindate, leavedate};
				// �� �� �� ���̺� ������ ����
				model.addRow(data);
			}
			
//			rs.close(); pstmt.close(); conn.close();
		} catch (Exception e) {
			System.err.println("- ���� ��ȸ ���� �߻� -");
			e.printStackTrace();
		}
	}
	
	// ������ �Է�, �˻� JTextField �ʱ�ȭ �޼ҵ�
	public void tfClear() {
		tfId.setText("");
		tfName.setText("");
		tfAge.setText("");
		tfAddress.setText("");
		tfDept.setText("");
		tfPosition.setText("");
		tfSalary.setText("");
		tfJoindate.setText("");
		tfSearch.setText("");
		dbSelect();
	}
	
	public static void main(String[] args) {
		System.out.println("- ��� ���� ���α׷� -\n");
		new Main();
	}
}
