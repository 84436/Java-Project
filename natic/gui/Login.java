package natic.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;

import net.miginfocom.swing.MigLayout;
import natic.*;
import natic.account.AccountEnums;
import natic.account.AccountEnums.AccountType;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtEmail;
	private JLabel Label_Cus;
	private JLabel Label_Q;
	private JButton btnSignUp;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("fillx", "[][grow][]", "[][][][][][grow][]"));
		
		JLabel Label = new JLabel("NATiC");
		Label.setFont(new Font("Tahoma", Font.BOLD, 40));
		contentPane.add(Label, "cell 1 0,alignx center");
		
		txtEmail = new JTextField();
		txtEmail.setText("Email");
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(txtEmail, "cell 1 1,alignx center");
		txtEmail.setColumns(15);
		
		passwordField = new JPasswordField();
		passwordField.setText("Password");
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(passwordField, "cell 1 2,alignx center");
		passwordField.setColumns(15);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String email = txtEmail.getText();
				String password = txtEmail.getText();
				if (email.equals("") || password.equals("")) {
					String message = "Missing username/password";
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
				} else {
					if (Mediator.getEmailLogin(email)) {
						if (Mediator.getPasswordLogin(password)) {
							AccountType oType = Mediator.getType(email, password);
							switch (oType.getClass().getName()) {
								case "Customer":
									txtEmail.setText("");
									passwordField.setText("");
									Customer customer = new Customer();
									customer.setVisible(true);
									break;
									
								case "Staff":
									txtEmail.setText("");
									passwordField.setText("");
									Staff staff = new Staff();
									staff.setVisible(true);
									break;

								case "Admin":
									txtEmail.setText("");
									passwordField.setText("");
									Admin admin = new Admin();
									admin.setVisible(true);
									break;
								
								case "Unknown":
									txtEmail.setText("");
									passwordField.setText("");
									Log.l.info("GUI for Unknown does not exist!");
									break;
							}
						}
						else {
							String message = "Wrong password";
							JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						String message = "Missing username";
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(btnSignIn, "cell 1 3,alignx center");
		
		Label_Cus = new JLabel("Customer only:");
		Label_Cus.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(Label_Cus, "flowx,cell 1 5,alignx center");
		
		Label_Q = new JLabel("No account yet?");
		Label_Q.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(Label_Q, "cell 1 5");
		
		btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtEmail.setText("");
				passwordField.setText("");
				Signup signup = new Signup();
				signup.setVisible(true);
			}
		});
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(btnSignUp, "cell 1 6,alignx center");
	}

}
