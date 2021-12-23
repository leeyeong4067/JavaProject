import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

public class Order extends JFrame {
	
	private Connection con;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	DefaultTableModel model;
	Object ob[][] = new Object[0][5];
	String colName[] = {"분류", "이름", "가격", "유통기한", "제조사"};
	
	DefaultTableModel model2;
	Object ob2[][] = new Object[0][8];
	String colName2[] = {"제조사", "종류", "이름", "가격", "수량", "유통기한(일)", "입고일"};

	
	String driver = "com.mysql.jdbc.Driver";
	//String url = ;
	//String userId = ;
	//String userPw = ;
	
	private JPanel contentPane;
	private JTable table;
	private JTable table2;
	private JScrollPane js2;
	
	public String checkRow() {
		
		int index = table.getSelectedRow();
		Object clickedRow = (table.getModel().getValueAt(index, 1));
		String selectRow = clickedRow.toString();
		
		return selectRow;
	}
	
	public String rcnext(int u, int y) {
		int row = 0;
		int col = 0;
	
		int countrow = u;
		int countcol = y;
		
		row = countrow;
		col = countcol;
		
		Object clickedRow = (table2.getModel().getValueAt(row, col));
		String selectRow = clickedRow.toString();	
		
		return selectRow;
	}
	
	private void connect() {
		try {
			Class.forName(driver);
			//con = DriverManager.getConnection(url, userId, userPw);
			System.out.println("연결 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Order() {
	
		setTitle("\uBC1C\uC8FC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1118, 648);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane js = new JScrollPane();
		js.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				connect();
				String SQL = "select * from product where pname =?";
				try {
					pstmt = con.prepareStatement(SQL);
					pstmt.setString(1, checkRow());
					rs = pstmt.executeQuery();
					
					String[] options = {"추가", "취소"};
					int result;
					result = JOptionPane.showOptionDialog(null, "선택한 상품을 추가합니다.", "선택", JOptionPane.YES_NO_CANCEL_OPTION,  JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
					
					if(result == 0) {
						
						String[] selections = {
								"1", "2", "3", "4", "5"
						};
						
						Object selNum = JOptionPane.showInputDialog(null, "수량 입력", "수량 입력", JOptionPane.QUESTION_MESSAGE, null, selections, "");
						
						if(selNum != null) {
							int snum = Integer.parseInt(selNum.toString());
							if(rs.next()) {
								
								Calendar cal = Calendar.getInstance();
								Calendar today = Calendar.getInstance();
								
								cal.setTime(new Date());
								today.setTime(new Date());
								
								DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
								DateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
								
							
								cal.add(Calendar.DATE, rs.getInt(6));
								today.add(Calendar.DATE, 1);
								
								String calex = df.format(cal.getTime());
								String todayex = df2.format(today.getTime());
								
								String l1 = rs.getString(7);
								String l2 = rs.getString(2);
								String l3 = rs.getString(3);
								String l4 = rs.getString(4);
								int l5 = snum;
								
								String l6 = calex;
								String l7 = todayex;
								
								Object data2[] = {l1, l2, l3, l4, l5, l6, l7};
								model2.addRow(data2);
							}
							
							JOptionPane.showMessageDialog(null, "추가 성공");
						}
						
						else {
							return;
						}
						
					}
					
					else {
						JOptionPane.showMessageDialog(null, "취소");
					}
							
					
				} catch(Exception e1) {
					e1.printStackTrace();
				} finally {
					if (rs != null) try{rs.close();} catch(Exception e1) {}
					if (pstmt != null) try{pstmt.close();} catch(Exception e1) {}
					if (con != null) try{con.close();} catch(Exception e1) {}
				}
				
			}
		});
		js.setBounds(12, 69, 494, 287);
		contentPane.add(js);
		
		model = new DefaultTableModel(ob, colName) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		model2 = new DefaultTableModel(ob2, colName2) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		table = new JTable(model);
		js.setViewportView(table);
		
		js2 = new JScrollPane();
		js2.setBounds(518, 69, 586, 287);
		contentPane.add(js2);
		
		table2 = new JTable(model2);
		js2.setViewportView(table2);
		
		JButton btnSend = new JButton("\uBC1C\uC8FC");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				connect();
					try {
					
					String sql = "insert into ordered(ocompany, otag, oname, oprice, onum, oexdate, opic) values(?, ?, ?, ?, ?, ?, ?)";
					pstmt = con.prepareStatement(sql);
					
					for(int i = 0; i < table2.getRowCount(); i++) {
						for (int j = 0; j < 1; j++) {	
					
							pstmt.setString(1, rcnext(i, 0));
							pstmt.setString(2, rcnext(i, 1));
							pstmt.setString(3, rcnext(i, 2));
							pstmt.setString(4, rcnext(i, 3));
							pstmt.setString(5, rcnext(i, 4));
							pstmt.setString(6, rcnext(i, 5));
							pstmt.setString(7, rcnext(i, 6));
							//pstmt.setString(8, rcnext(i, 7));
														
						}
						int n = pstmt.executeUpdate();
					}
					
					int line = 0;
					
					for (int i = 0; i < table2.getRowCount() +1; i++) {
						model2.removeRow(line);;
						line ++;
						model2.fireTableDataChanged();
					}
					
					JOptionPane.showMessageDialog(null, "발주 성공");
					
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					if (rs != null) try{rs.close();} catch(Exception e1) {}
					if (pstmt != null) try{pstmt.close();} catch(Exception e1) {}
					if (con != null) try{con.close();} catch(Exception e1) {}
				}
				
			}
		});
		btnSend.setBounds(1001, 380, 91, 23);
		contentPane.add(btnSend);
		
		JButton btnNewButton = new JButton("\uB9AC\uC2A4\uD2B8 \uBE44\uC6B0\uAE30");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < table2.getRowCount() + 1; i++) {
					int line = 0;
					model2.removeRow(line);;
					line ++;
					model2.fireTableDataChanged();
				}
			}
		});
		btnNewButton.setBounds(873, 380, 116, 23);
		contentPane.add(btnNewButton);
		
		JPanel image = new JPanel();
		image.setBounds(23, 366, 355, 235);
		contentPane.add(image);
		
		JButton btnNewButton_1 = new JButton("\uBA54\uC778\uC73C\uB85C");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main frame = new Main();
				frame.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(33, 24, 91, 23);
		contentPane.add(btnNewButton_1);
		
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		
		table2.getTableHeader().setReorderingAllowed(false);
		table2.getTableHeader().setResizingAllowed(false);
		
		
		
		connect();
		load();
		
	}
	
	public void load() {
		String sql = "select * from product";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				
				String pno = rs.getString("pno");
				String tag = rs.getString("tag");
				String name = rs.getString("pname");
				String price = rs.getString("pprice");
				int pexdate = rs.getInt("pexdate");
				String com = rs.getString("company");
				//String number = rs.getString("pnum");
				//String pic = rs.getString("pic");
				
				cal.add(Calendar.DATE, pexdate);
				String calex = df.format(cal.getTime());
				System.out.print(calex);
				
				Object data[] = {tag, name, price, calex, com};
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
}
