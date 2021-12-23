import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class User extends JFrame {

	private JPanel contentPane;
	private JPasswordField pw1;
	private JPasswordField pw2;
	
	private Connection con;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	String driver = "com.mysql.jdbc.Driver";
	//String url = ;
	//String userId = ;
	//String userPw = ;
	private JPasswordField before;

	private void connect() {
		try {
			Class.forName(driver);
			//con = DriverManager.getConnection(url, userId, userPw);
			System.out.println("연결 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User() {
		setBounds(100, 100, 522, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uBE44\uBC00\uBC88\uD638 \uBCC0\uACBD");
		lblNewLabel.setBounds(190, 47, 120, 24);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		contentPane.add(lblNewLabel);
		
		pw1 = new JPasswordField();
		pw1.setBounds(133, 141, 241, 24);
		contentPane.add(pw1);
		
		pw2 = new JPasswordField();
		pw2.setBounds(133, 189, 241, 24);
		contentPane.add(pw2);
		
		JLabel lblNewLabel_1 = new JLabel("\uBCC0\uACBD\uD560 \uBE44\uBC00\uBC88\uD638");
		lblNewLabel_1.setBounds(22, 145, 99, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("\uD655\uC778");
		lblNewLabel_1_1.setBounds(85, 193, 49, 15);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnSave = new JButton("\uBCC0\uACBD");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pass1 = pw1.getText();
				String pass2 = pw2.getText();
				String pass0 = before.getText();
				
				if (pass1.equals(pass2)) {
					try {
						connect();
						String sql = "update account set pw =? where pw =?";
						pstmt = con.prepareStatement(sql);
						
						pstmt.setString(1, pass2);
						pstmt.setString(2, pass0);
						
						int n = pstmt.executeUpdate();
						
						System.out.println("데이터베이스 수정완료");
						JOptionPane.showMessageDialog(null, "변경 성공");
					
						dispose();
						
					} catch (Exception e1) {
						e1.printStackTrace();
					} finally {
						if (rs != null) try{rs.close();} catch(Exception e1) {}
						if (pstmt != null) try{pstmt.close();} catch(Exception e1) {}
						if (con != null) try{con.close();} catch(Exception e1) {}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "변경 실패");
				}
			}
		});
		btnSave.setBounds(202, 245, 91, 23);
		contentPane.add(btnSave);
		
		before = new JPasswordField();
		before.setBounds(133, 93, 241, 24);
		contentPane.add(before);
		
		JLabel lblNewLabel_1_2 = new JLabel("\uBCC0\uACBD\uC804 \uBE44\uBC00\uBC88\uD638");
		lblNewLabel_1_2.setBounds(22, 97, 99, 15);
		contentPane.add(lblNewLabel_1_2);
	}
}
