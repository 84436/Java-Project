package gui;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Signup extends JFrame {

	private JPanel contentPane;
	private JTextField EmailField;
	private JTextField PhoneField;
	private JTextField pwdField;
	private JTextField confirmpwdField;
	private JButton btnSignUp;
	private JButton btnBack;
	private JSeparator separator_1;

	/**
	 * Create the frame.
	 */
	public Signup() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[grow]", "[25px][25px][25px][25px][25px][25px][][]"));
		
		JLabel lblNewLabel = new JLabel("NATiC");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel.add(lblNewLabel, "cell 0 0,alignx center");
		
		JSeparator separator = new JSeparator();
		panel.add(separator, "cell 0 1");
		
		EmailField = new JTextField();
		EmailField.setText("Email");
		panel.add(EmailField, "cell 0 2,alignx center");
		EmailField.setColumns(25);
		
		PhoneField = new JTextField();
		PhoneField.setText("Phone");
		panel.add(PhoneField, "cell 0 3,alignx center");
		PhoneField.setColumns(25);
		
		pwdField = new JTextField();
		pwdField.setText("Password");
		panel.add(pwdField, "cell 0 4,alignx center");
		pwdField.setColumns(25);
		
		confirmpwdField = new JTextField();
		confirmpwdField.setText("Confirm Password");
		panel.add(confirmpwdField, "cell 0 5,alignx center");
		confirmpwdField.setColumns(25);
		
		separator_1 = new JSeparator();
		panel.add(separator_1, "cell 0 6");
		
		btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(btnSignUp, "flowx,cell 0 7,alignx center");
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(btnBack, "cell 0 7,alignx center");
	}

}
