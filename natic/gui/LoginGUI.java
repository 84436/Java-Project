package natic.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import net.miginfocom.swing.MigLayout;
import natic.*;
import natic.account.AccountEnums.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginGUI extends JFrame {

	private Mediator mediator = Mediator.getInstance();

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
		Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");

	private JPanel contentPane;
	private JTextField txtEmail;
	private JLabel Label_Cus;
	private JLabel Label_Q;
	private JButton btnSignUp;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public LoginGUI() throws SQLException {
        // Native LAF
	    try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();;
        }
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
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
		
		// JButton btnSignIn = new JButton("Sign In");
		// btnSignIn.addActionListener(new ActionListener() {
		// 	public void actionPerformed(ActionEvent arg0)  {
		// 		String email = txtEmail.getText();
		// 		String password = txtEmail.getText();
		// 		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		// 		if (email.equals("") || password.equals("")) {
		// 			String message = "Missing username/password";
		// 			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
		// 		} else {
		// 			if (mediator.checkLogin(email, password) != null) {
		// 				AccountType oType = mediator.checkLogin(email, password);
		// 				switch (oType.getClass().getName()) {
		// 					case "Customer":
		// 						txtEmail.setText("");
		// 						passwordField.setText("");
		// 						CustomerGUI customer = new CustomerGUI();
		// 						customer.setVisible(true);
		// 						break;
								
		// 					case "Staff":
		// 						txtEmail.setText("");
		// 						passwordField.setText("");
		// 						StaffGUI staff = new StaffGUI();
		// 						staff.setVisible(true);
		// 						break;

		// 					case "Admin":
		// 						txtEmail.setText("");
		// 						passwordField.setText("");
		// 						AdminGUI admin = new AdminGUI();
		// 						admin.setVisible(true);
		// 						break;
							
		// 					case "Unknown":
		// 						txtEmail.setText("");
		// 						passwordField.setText("");
		// 						Log.l.info("GUI for Unknown does not exist!");
		// 						break;
		// 					}
		// 				}
		// 			else {
		// 				String message = "Wrong username/password";
		// 				JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
		// 			}
		// 		}
		// 	}
		// });
		// btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		// contentPane.add(btnSignIn, "cell 1 3,alignx center");
		
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
				SignupGUI signup = new SignupGUI();
				signup.setVisible(true);
				dispose();
			}
		});
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(btnSignUp, "cell 1 6,alignx center");
	}

}
