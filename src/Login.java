import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login extends JFrame {


	private JPanel contentPane;
	private JTextField txtId;
	
	
	
	private Connection con;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	String driver = "com.mysql.jdbc.Driver";
	//url, id, pw check
	//String url = url;
	//String userId = id;
	//String userPw = pw;
	private JPasswordField txtPw;
	
	public boolean checkId(String logId, String logPw) {
		connect();
		String SQL = "select pw from account where id =?";
		boolean checkt = false;
		try {
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, logId);
		
			rs = pstmt.executeQuery();
			if(rs.next()) {
	
				if(rs.getString(1).contentEquals(logPw)) {
					return checkt = true;
				}
			else {
				return checkt = false;
			}	
		}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try{rs.close();} catch(Exception e) {}
			if (pstmt != null) try{pstmt.close();} catch(Exception e) {}
			if (con != null) try{con.close();} catch(Exception e) {}
		}
		return checkt;
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

	public Login() {
		setTitle("\uB85C\uADF8\uC778");
		
		setBounds(100, 100, 580, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton bntLogin = new JButton("\uB85C\uADF8\uC778");
		bntLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = txtId.getText();
				String p = txtPw.getText();

				if (checkId(i, p) == true) {
					Main frame = new Main();
					frame.setVisible(true);
					dispose();
					JOptionPane.showMessageDialog(null, "로그인 성공");
				} else {
					JOptionPane.showMessageDialog(null, "로그인 실패");
				}
				
				
			}
		});
		
		bntLogin.setBounds(239, 250, 91, 23);
		contentPane.add(bntLogin);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		lblNewLabel.setBounds(247, 34, 169, 94);
		contentPane.add(lblNewLabel);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(149, 141, 50, 15);
		contentPane.add(lblId);
		
		JLabel lblPw = new JLabel("PW");
		lblPw.setBounds(149, 194, 50, 15);
		contentPane.add(lblPw);
		
		txtId = new JTextField();
		txtId.setBounds(202, 138, 200, 21);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		txtPw = new JPasswordField();
		txtPw.setBounds(202, 191, 200, 21);
		contentPane.add(txtPw);
	}
}
