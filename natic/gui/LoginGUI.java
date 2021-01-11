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

    private Mediator M = Mediator.getInstance();

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
            "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");

    private JPanel contentPane;
    private JTextField txtEmail;
    private JLabel Label_Cus;
    private JLabel Label_Q;
    private JButton btnSignUp;
    private JPasswordField pwtxtPassword;

    private static final String PLACEHOLDER_EMAIL = "Email";
    private static final String PLACEHOLDER_PASSWORD = "Password";
    
    public LoginGUI() {
        // Native LAF
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            ;
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
        GUIHelpers.addPlaceholderText(txtEmail, PLACEHOLDER_EMAIL);
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(txtEmail, "cell 1 1,alignx center");
        txtEmail.setColumns(15);

        pwtxtPassword = new JPasswordField();
        GUIHelpers.addPlaceholderText(pwtxtPassword, PLACEHOLDER_PASSWORD);
        pwtxtPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(pwtxtPassword, "cell 1 2,alignx center");
        pwtxtPassword.setColumns(15);

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
                pwtxtPassword.setText("");
                SignupGUI signup = new SignupGUI();
                signup.setVisible(true);
                signup.getRootPane().requestFocus(false);
                dispose();
            }
        });
        btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(btnSignUp, "cell 1 6,alignx center");

        JButton btnSignIn = new JButton("Sign In");
        btnSignIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String email = txtEmail.getText().trim();
                String password = new String(pwtxtPassword.getPassword());
                
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
                
                if (email.equals(PLACEHOLDER_EMAIL) || password.equals(PLACEHOLDER_PASSWORD) || email.isBlank() || password.isBlank()) {
                    GUIHelpers.showErrorDialog("Email and/or password missing", null);
                    return;
                }

                if (!matcher.matches()) {
                    GUIHelpers.showErrorDialog("Invalid email format", null);
                    return;
                }

                try {
                    AccountType oType = M.checkLogin(email, password);
                    Log.l.info(oType.toString());
                    switch (oType) {
                        case CUSTOMER:
                            String CusID = M.getIDByEmail(email);
                            txtEmail.setText("");
                            pwtxtPassword.setText("");
                            CustomerGUI customer = new CustomerGUI(CusID);
                            customer.setVisible(true);
                            dispose();
                            break;

                        case STAFF:
                            String StaffID = M.getIDByEmail(email);
                            txtEmail.setText("");
                            pwtxtPassword.setText("");
                            StaffGUI staff = new StaffGUI(StaffID);
                            staff.setVisible(true);
                            dispose();
                            break;

                        case ADMIN:
                            String AdminID = M.getIDByEmail(email);
                            txtEmail.setText("");
                            pwtxtPassword.setText("");
                            
                            AdminGUI admin = new AdminGUI(AdminID);
                            admin.setVisible(true);
                            dispose();
                            break;

                        case UNKNOWN:
                            GUIHelpers.showErrorDialog("Unable to login. Check your email and/or password again.", null);
                            break;
                    }
                }
                catch (Exception exc) {
                    GUIHelpers.showErrorDialog("Unknown error occured while logging in", exc);
                }
            }
        });

        btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(btnSignIn, "cell 1 3,alignx center");
    }
}
