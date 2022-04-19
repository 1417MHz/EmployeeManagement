import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class CreateGUI extends JFrame {
    public static JButton btnInsert, btnUpdate, btnDelete, btnLeave, btnClear, btnExit,btnSearch;
    public static JTextField tfId, tfName, tfAge, tfAddress, tfDept, tfPosition, tfSalary, tfJoindate;
    public static JTextField tfSearch;
    public static JRadioButton rbId, rbName, rbDept;

    // ��� ��� ȭ��� JTable
    static String[] header = {"ID", "�̸�", "����", "�ּ�", "�μ�", "����", "����", "�Ի���", "�����"};

    // JTable �� ���� �Ұ��� �ϵ��� ����
    static DefaultTableModel model = new DefaultTableModel(header, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    static JTable empTable = new JTable(model);
    JScrollPane tblSP = new JScrollPane(empTable);

    // ������
    public CreateGUI() {
        // â ����
        super("��� ���� ���α׷�");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //
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


        // â ũ�� ���� �Ұ�
        setResizable(false);

        // â ũ�� ����
        setSize(900, 410);
        setVisible(true);
    }
}
