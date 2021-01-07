package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JLabel Label_Cus;
	private JLabel Label_Q;
	private JButton btnSignUp;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("debug, fillx", "[][grow][]", "[][][][][][grow][]"));
		
		JLabel Label = new JLabel("NATiC");
		Label.setFont(new Font("Tahoma", Font.BOLD, 40));
		contentPane.add(Label, "cell 1 0,alignx center");
		
		txtUsername = new JTextField();
		txtUsername.setText("Username");
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(txtUsername, "cell 1 1,alignx center");
		txtUsername.setColumns(15);
		
		passwordField = new JPasswordField();
		passwordField.setText("Password");
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(passwordField, "cell 1 2,alignx center");
		passwordField.setColumns(15);
		
		JButton btnSignIn = new JButton("Sign In");
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
				
			}
		});
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(btnSignUp, "cell 1 6,alignx center");
	}

}
