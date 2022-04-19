import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class CreateGUI extends JFrame {
    public static JButton btnInsert, btnUpdate, btnDelete, btnLeave, btnClear, btnExit,btnSearch;
    public static JTextField tfId, tfName, tfAge, tfAddress, tfDept, tfPosition, tfSalary, tfJoindate;
    public static JTextField tfSearch;
    public static JRadioButton rbId, rbName, rbDept;

    // 결과 출력 화면용 JTable
    static String[] header = {"ID", "이름", "나이", "주소", "부서", "직위", "연봉", "입사일", "퇴사일"};

    // JTable 셀 수정 불가능 하도록 설정
    static DefaultTableModel model = new DefaultTableModel(header, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    static JTable empTable = new JTable(model);
    JScrollPane tblSP = new JScrollPane(empTable);

    // 생성자
    public CreateGUI() {
        // 창 제목
        super("사원 관리 프로그램");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //
        // 메인 JPanel c
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        // c의 하위 패널 pLeft
        // 데이터 입력 부분 & 버튼 부분을 위한 왼쪽 JPanel
        JPanel pLeft = new JPanel();
        pLeft.setLayout(new BorderLayout());
        pLeft.setBorder(BorderFactory.createEmptyBorder(40, 5, 0, 5));

        // pLeft의 하위 패널 plNorth
        // 데이터 입력 부분 JPanel
        JPanel plNorth = new JPanel();
        plNorth.setLayout(new GridLayout(8, 2, 5, 5));
        plNorth.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 5));

        plNorth.add(new JLabel("사원번호"));
        tfId = new JTextField();
        plNorth.add(tfId);

        plNorth.add(new JLabel("이름"));
        tfName = new JTextField();
        plNorth.add(tfName);

        plNorth.add(new JLabel("나이"));
        tfAge = new JTextField();
        plNorth.add(tfAge);

        plNorth.add(new JLabel("주소"));
        tfAddress = new JTextField();
        plNorth.add(tfAddress);

        plNorth.add(new JLabel("부서"));
        tfDept = new JTextField();
        plNorth.add(tfDept);

        plNorth.add(new JLabel("직위"));
        tfPosition = new JTextField();
        plNorth.add(tfPosition);

        plNorth.add(new JLabel("연봉"));
        tfSalary = new JTextField();
        plNorth.add(tfSalary);

        plNorth.add(new JLabel("입사일  ex)2021-06-01"));
        tfJoindate = new JTextField();
        plNorth.add(tfJoindate);

        pLeft.add(plNorth, BorderLayout.NORTH);

        // pLeft의 하위패널 plMiddle
        // 버튼 + 검색 부분 JPanel
        JPanel plMiddle = new JPanel();
        plMiddle.setLayout(new BorderLayout());

        // plMiddle의 하위 패널 plBtn
        // 기능 수행을 위한 버튼이 모인 JPanel
        JPanel plBtn = new JPanel();
        plBtn.setLayout(new GridLayout(2, 3, 5, 5));
        btnInsert = new JButton("입력"); btnInsert.setBackground(new Color(255, 255, 255));
        btnUpdate = new JButton("수정"); btnUpdate.setBackground(new Color(255, 255, 255));
        btnDelete = new JButton("삭제"); btnDelete.setBackground(new Color(255, 255, 255));
        btnLeave = new JButton("퇴사"); btnLeave.setBackground(new Color(255, 255, 255));
        btnClear = new JButton("초기화"); btnClear.setBackground(new Color(255, 255, 255));
        btnExit = new JButton("종료"); btnExit.setBackground(new Color(220, 220, 220));
        plBtn.add(btnInsert);
        plBtn.add(btnUpdate);
        plBtn.add(btnDelete);
        plBtn.add(btnLeave);
        plBtn.add(btnClear);
        plBtn.add(btnExit);

        // 상위 패널 plMiddle에 추가
        plMiddle.add(plBtn, BorderLayout.NORTH);

        // 상위 패널 pLeft에 추가
        pLeft.add(plMiddle, BorderLayout.CENTER);

        // c의 하위 패널 pRight
        // 검색 & 조회 JTable을 위한 오른쪽 JPanel
        JPanel pRight = new JPanel();
        pRight.setLayout(new BorderLayout());

        // 검색 부분을 위한 별도 JPanel
        tfSearch = new JTextField(18);
        rbId = new JRadioButton("사원번호", true);
        rbName = new JRadioButton("이름");
        rbDept = new JRadioButton("부서");
        btnSearch = new JButton("검색");
        btnSearch.setBackground(new Color(255, 255, 255));
        ButtonGroup group = new ButtonGroup();
        group.add(rbId); group.add(rbName); group.add(rbDept);

        // prSeacrh의 하위 패널 pn1
        // pn1  = JTextField & JButton
        JPanel pn1 = new JPanel();
        pn1.add(tfSearch);
        pn1.add(btnSearch);

        // prSearch의 하위 패널 pn2
        // pn2 = JRadioButton
        JPanel pn2 = new JPanel();
        pn2.add(rbId);
        pn2.add(rbName);
        pn2.add(rbDept);

        // pRight의 하위 패널 prSearch
        // 검색 부분 최종 구현(pn1 + pn2)
        JPanel prSearch = new JPanel(new BorderLayout(0, 0));
        prSearch.add(BorderLayout.NORTH, pn1);
        prSearch.add(BorderLayout.CENTER, pn2);
        TitledBorder tb = new TitledBorder("검색");
        prSearch.setBorder(tb);

        // 상위 패널 pRight에 추가
        pRight.add(prSearch, BorderLayout.NORTH);

        // pRight에 JTable 삽입
        pRight.add(tblSP, BorderLayout.CENTER);

        // JTable column 크기 조정
        empTable.getColumn("ID").setPreferredWidth(40);
        empTable.getColumn("이름").setPreferredWidth(60);
        empTable.getColumn("나이").setPreferredWidth(40);
        empTable.getColumn("주소").setPreferredWidth(120);
        empTable.getColumn("연봉").setPreferredWidth(50);

        // JTable Column 이동, 크기조절 불가
        empTable.getTableHeader().setReorderingAllowed(false);
        empTable.getTableHeader().setResizingAllowed(false);

        // JTable 셀에 가운데 정렬 적용
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        TableColumnModel tcm = empTable.getColumnModel();
        for(int i=0; i<empTable.getColumnCount(); i++)
            tcm.getColumn(i).setCellRenderer(dtcr);

        // 메인 패널 c에 하위 패널들 추가
        c.add(pLeft, BorderLayout.WEST);
        c.add(pRight, BorderLayout.CENTER);


        // 창 크기 조절 불가
        setResizable(false);

        // 창 크기 설정
        setSize(900, 410);
        setVisible(true);
    }
}
