import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductManage extends JFrame {
	DefaultTableModel model;
	Object ob[][] = new Object[0][6];
	String colName[] = {"종류", "이름", "가격", "수량", "입고일", "유통기한"};
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String driver = "com.mysql.jdbc.Driver";
	//String url = ;
	//String userId = ;
	//String userPw = ;
	
	private JTable table;
	private JTextField txtName;
	private JTextField txtPrice;
	private JTextField txtNum;
	private JTextField txtPexdate;
	private JTextField txtTag;
	private JTextField txtOpic;
	
	public String checkRow() {
		
		int index = table.getSelectedRow();
		Object clickedRow = (table.getModel().getValueAt(index, 1));
		String selectRow = clickedRow.toString();
		
		return selectRow;
	}
	
	public ProductManage() {
		setTitle("\uC0C1\uD488 \uC870\uD68C");
		
		model = new DefaultTableModel(ob, colName) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 922, 556);
		getContentPane().setLayout(null);
		
		JScrollPane js = new JScrollPane();
		js.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				connect();
				String SQL = "select * from ordered where oname =?";
				try {
					pstmt = con.prepareStatement(SQL);
					pstmt.setString(1, checkRow());
					rs = pstmt.executeQuery();
					
					if(rs.next()){
						
						txtTag.setText(rs.getString("otag"));
						txtName.setText(rs.getString("oname"));
						txtPrice.setText(rs.getString("oprice"));
						txtNum.setText(rs.getString("onum"));
						txtOpic.setText(rs.getString("opic"));
						txtPexdate.setText(rs.getString("oexdate"));
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
		js.setBounds(26, 45, 461, 464);
		getContentPane().add(js);
		
		table = new JTable(model);
		//js = new JScrollPane(table);
		js.setViewportView(table);
		
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(601, 117, 209, 27);
		getContentPane().add(txtName);
		
		JLabel lblNewLabel = new JLabel("\uC774\uB984");
		lblNewLabel.setBounds(544, 123, 50, 15);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\uAC00\uACA9");
		lblNewLabel_1.setBounds(544, 170, 50, 15);
		getContentPane().add(lblNewLabel_1);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(601, 164, 209, 27);
		getContentPane().add(txtPrice);
		
		JLabel lblNewLabel_2 = new JLabel("\uC218\uB7C9");
		lblNewLabel_2.setBounds(544, 215, 50, 15);
		getContentPane().add(lblNewLabel_2);
		
		txtNum = new JTextField();
		txtNum.setColumns(10);
		txtNum.setBounds(601, 209, 209, 27);
		getContentPane().add(txtNum);
		
		JLabel lblNewLabel_3 = new JLabel("\uC720\uD1B5\uAE30\uD55C");
		lblNewLabel_3.setBounds(544, 293, 50, 15);
		getContentPane().add(lblNewLabel_3);
		
		txtPexdate = new JTextField();
		txtPexdate.setColumns(10);
		txtPexdate.setBounds(601, 287, 209, 27);
		getContentPane().add(txtPexdate);
		
		JButton btnSave = new JButton("\uC218\uC815");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connect();
					String sql = "update ordered set otag =?, oname =?, oprice=?, onum=?, opic =?, oexdate=? where oname =?";
					pstmt = con.prepareStatement(sql);
					
					String otag = txtTag.getText();
					String oname = txtName.getText();
					String onum = txtNum.getText();
					String oprice = txtPrice.getText();
					String opic = txtOpic.getText();
					String oexdate = txtPexdate.getText();
					
					pstmt.setString(1, otag);
					pstmt.setString(2, oname);
					pstmt.setString(3, oprice);
					pstmt.setString(4, onum);
					pstmt.setString(5, opic);
					pstmt.setString(6, oexdate);
					pstmt.setString(7, checkRow());
					
					int n = pstmt.executeUpdate();
					
					System.out.println("데이터베이스 수정완료");
					
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					if (rs != null) try{rs.close();} catch(Exception e1) {}
					if (pstmt != null) try{pstmt.close();} catch(Exception e1) {}
					if (con != null) try{con.close();} catch(Exception e1) {}
				}
			}
		});
		btnSave.setBounds(654, 346, 77, 50);
		getContentPane().add(btnSave);
		
		JButton btnRemove = new JButton("\uC81C\uAC70");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRow();
				try {
					connect();
					
					String sql = "delete from ordered where oname =?";
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1, checkRow());
					
					int n = pstmt.executeUpdate();
					model.removeRow(count);
					model.fireTableDataChanged();
					
					txtTag.setText("");
					txtName.setText("");
					txtNum.setText("");
					txtPrice.setText("");
					txtOpic.setText("");
					txtPexdate.setText("");
					
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					if (rs != null) try{rs.close();} catch(Exception e1) {}
					if (pstmt != null) try{pstmt.close();} catch(Exception e1) {}
					if (con != null) try{con.close();} catch(Exception e1) {}
				}
			}
		});
		btnRemove.setBounds(768, 346, 77, 50);
		getContentPane().add(btnRemove);
		
		JButton btnAdd = new JButton("\uCD94\uAC00");
		btnAdd.setIcon(null);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
				try {
					
					String sql = "insert into ordered(ono, otag, oname, oprice, onum, opic, oexdate) values(?, ?, ?, ?, ?, ?, ?)";
					pstmt = con.prepareStatement(sql);
					
					String otag = txtTag.getText();
					String oname = txtName.getText();
					String onum = txtNum.getText();
					String oprice = txtPrice.getText();
					String opic = txtOpic.getText();
					String oexdate = txtPexdate.getText();
					
					pstmt.setString(1, null);
					pstmt.setString(2, otag);
					pstmt.setString(3, oname);
					pstmt.setString(4, oprice);
					pstmt.setString(5, onum);
					pstmt.setString(6, opic);
					pstmt.setString(7, oexdate);
					
					int n = pstmt.executeUpdate();
				
					Object data[] = {otag, oname, oprice, onum, opic, oexdate};
					model.addRow(data);
				
					
					model.fireTableDataChanged();
					System.out.println("새항목 추가완료");
					
					
					txtTag.setText("");
					txtName.setText("");
					txtNum.setText("");
					txtOpic.setText("");
					txtPrice.setText("");
					txtPexdate.setText("");
					
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					if (rs != null) try{rs.close();} catch(Exception e1) {}
					if (pstmt != null) try{pstmt.close();} catch(Exception e1) {}
					if (con != null) try{con.close();} catch(Exception e1) {}
				}
				
			}
		});
		btnAdd.setBounds(544, 346, 77, 50);
		getContentPane().add(btnAdd);
		
		txtTag = new JTextField();
		txtTag.setColumns(10);
		txtTag.setBounds(601, 69, 209, 27);
		getContentPane().add(txtTag);
		
		JLabel lblNewLabel_4 = new JLabel("\uC885\uB958");
		lblNewLabel_4.setBounds(544, 75, 50, 15);
		getContentPane().add(lblNewLabel_4);
		
		JButton btnNewButton = new JButton("\uBA54\uC778\uC73C\uB85C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main frame = new Main();
				frame.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
		btnNewButton.setBounds(12, 10, 91, 23);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2_1 = new JLabel("\uC785\uACE0\uC77C");
		lblNewLabel_2_1.setBounds(544, 256, 50, 15);
		getContentPane().add(lblNewLabel_2_1);
		
		txtOpic = new JTextField();
		txtOpic.setColumns(10);
		txtOpic.setBounds(601, 250, 209, 27);
		getContentPane().add(txtOpic);
		
		connect();
		select();
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
	
	public void select() {
		connect();
		String SQL = "select * from ordered";
		try {
			pstmt = con.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String no = rs.getString("ono");
				String tag = rs.getString("otag");
				String name = rs.getString("oname");
				String price = rs.getString("oprice");
				String number = rs.getString("onum");
				String opic = rs.getString("opic");
				String pexdate = rs.getString("oexdate");
				
				Object data[] = {tag, name, price, number, opic, pexdate};
				model.addRow(data);
			}
		} catch(Exception e) {
			System.out.println("select() 실행 오류 : " +e);
		} finally {
			if (rs != null) try{rs.close();} catch(Exception e1) {}
			if (pstmt != null) try{pstmt.close();} catch(Exception e1) {}
			if (con != null) try{con.close();} catch(Exception e1) {}
		}
	}
}
