package natic.gui;

import javax.swing.*;
import javax.swing.border.*;

import natic.Log;
import natic.Mediator;
import natic.account.AccountProvider;
import natic.account.Customer;
import natic.account.AccountEnums.AccountType;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.miginfocom.swing.MigLayout;

public class SignupGUI extends JFrame {

	private Mediator mediator = Mediator.getInstance();

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
		Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");

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
	public SignupGUI() {
        // Native LAF
	    try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();;
        }

		setBounds(100, 100, 500, 400);
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
		
		pwdField = new JPasswordField();
		pwdField.setText("Password");
		panel.add(pwdField, "cell 0 4,alignx center");
		pwdField.setColumns(25);
		
		confirmpwdField = new JPasswordField();
		confirmpwdField.setText("Confirm Password");
		panel.add(confirmpwdField, "cell 0 5,alignx center");
		confirmpwdField.setColumns(25);
		
		separator_1 = new JSeparator();
		panel.add(separator_1, "cell 0 6");
		
		btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Customer newCustomer = new Customer(mediator);
				String email = EmailField.getText();
				String phone = PhoneField.getText();
				String password = pwdField.getText();
				String confirmpwd = confirmpwdField.getText();
				Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
				if (matcher.find()) {
					newCustomer.setEmail(email);
					if (phone.length() == 10) {
						newCustomer.setPhone(phone);
						if (!password.equals(confirmpwd)) {
							newCustomer.setPass(password);
							newCustomer.setType(AccountType.CUSTOMER);
							newCustomer.setName("");
							mediator.createAccount(newCustomer);
						}
						else {
							Log.l.info("Password does not match!");
							setVisible(true);
							dispose();
							pwdField.setText("");
							confirmpwdField.setText("");
						}
					}
					else {
						Log.l.info("Phone length must be 10!");
						setVisible(true);
						dispose();
						pwdField.setText("");
						confirmpwdField.setText("");
					}
				}
				else {
					Log.l.info("Email not valid!");
					setVisible(true);
					dispose();
					pwdField.setText("");
					confirmpwdField.setText("");
				}
			}
		});
		panel.add(btnSignUp, "flowx,cell 0 7,alignx center");
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EmailField.setText("");
				PhoneField.setText("");
				pwdField.setText("");
				confirmpwdField.setText("");
				dispose();
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
			}
		});
		panel.add(btnBack, "cell 0 7,alignx center");
	}

}
