import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Main extends JFrame {

	private JPanel contentPane;

	public Main() {
		setTitle("\uBA54\uC778");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 420);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Move");
		menuBar.add(mnNewMenu);
		
		JMenuItem moveP = new JMenuItem("\uC0C1\uD488 \uC870\uD68C");
		moveP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductManage frame = new ProductManage();
				frame.setVisible(true);
				dispose();
			}
		});
		mnNewMenu.add(moveP);
		
		JMenuItem moveO = new JMenuItem("\uC0C1\uD488 \uBC1C\uC8FC");
		moveO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Order frame = new Order();
				frame.setVisible(true);
				dispose();
			}
		});
		mnNewMenu.add(moveO);
		
		JMenuItem moveE = new JMenuItem("\uD3D0\uAE30 \uAD00\uB9AC");
		moveE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Exhaust frame = new Exhaust();
				frame.setVisible(true);
				dispose();
			}
		});
		mnNewMenu.add(moveE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnOrder = new JButton("\uC0C1\uD488 \uBC1C\uC8FC");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Order frame = new Order();
				frame.setVisible(true);
				dispose();
			}
		});
		btnOrder.setBounds(303, 20, 208, 148);
		contentPane.add(btnOrder);
		
		JButton btnNewButton_2 = new JButton("\uC0C1\uD488 \uC870\uD68C");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductManage frame = new ProductManage();
				frame.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2.setBounds(42, 20, 208, 148);
		contentPane.add(btnNewButton_2);
		
		JButton btnExhaust = new JButton("\uD3D0\uAE30 \uAD00\uB9AC");
		btnExhaust.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Exhaust frame = new Exhaust();
				frame.setVisible(true);
				dispose();
			}
		});
		btnExhaust.setBounds(42, 191, 208, 148);
		contentPane.add(btnExhaust);
		
		JButton btnUser = new JButton("\uC815\uBCF4 \uC218\uC815");
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User frame = new User();
				frame.setVisible(true);
			}
		});
		btnUser.setBounds(303, 191, 208, 148);
		contentPane.add(btnUser);
	}
}
