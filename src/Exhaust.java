import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Exhaust extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	private Connection con;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	String driver = "com.mysql.jdbc.Driver";
	//String url = ";
	//String userId = ;
	//String userPw = ;
	
	DefaultTableModel model;
	Object ob[][] = new Object[0][5];
	String colName[] = {"분류", "이름", "수량", "입고일", "유통기한"};
	
	private void connect() {
		try {
			Class.forName(driver);
			//con = DriverManager.getConnection(url, userId, userPw);
			System.out.println("연결 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		String SQL = "select * from ordered where oexdate = ?";
		try {

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			String calex = df.format(cal.getTime());
			
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, calex);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				String otag = rs.getString("otag");
				String oname = rs.getString("oname");
				String opic = rs.getString("opic");
				String onum = rs.getString("onum");
				String opexdate = rs.getString("oexdate");
		
				Object data[] = {otag, oname, onum, opic, opexdate};
				model.addRow(data);
			}
		} catch(Exception e) {
			System.out.println("select() 실행 오류 : " +e);
		} finally {
			if (rs != null) try{rs.close();} catch(Exception e) {}
			if (pstmt != null) try{pstmt.close();} catch(Exception e) {}
			if (con != null) try{con.close();} catch(Exception e) {}
		}
	
	}
	
	public Exhaust() {
		setTitle("\uD3D0\uAE30\uAD00\uB9AC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 861, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane js = new JScrollPane();
		js.setBounds(22, 79, 811, 428);
		contentPane.add(js);
		
		model = new DefaultTableModel(ob, colName) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		table = new JTable(model);
		js.setViewportView(table);
		
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		
		JButton btnMain = new JButton("Main");
		btnMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main frame = new Main();
				frame.setVisible(true);
				dispose();
			}
		});
		btnMain.setBounds(22, 46, 91, 23);
		contentPane.add(btnMain);
		
		connect();
		load();
	}
}
