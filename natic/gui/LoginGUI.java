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
    private JPasswordField pwtxtCurrent;

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

        setTitle("NATiC: Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 500, 400);
        GUIHelpers.centerWindow(this);
        GUIHelpers.setGlobalFont("SansSerif", Font.PLAIN, 14);

        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("fillx", "[36px][grow,fill][36px]", "[72px][36px,fill][36px,fill][36px,fill][36px,fill][][36px,fill][grow]"));

        setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);

        JLabel lblAppName = new JLabel("NATiC");
        lblAppName.setHorizontalAlignment(SwingConstants.CENTER);
        lblAppName.setFont(new Font("SansSerif", Font.BOLD, 36));
        contentPane.add(lblAppName, "cell 1 0");

        txtEmail = new JTextField();
        GUIHelpers.addPlaceholderText(txtEmail, PLACEHOLDER_EMAIL);
        contentPane.add(txtEmail, "cell 1 1");
        txtEmail.setColumns(20);

        pwtxtCurrent = new JPasswordField();
        GUIHelpers.addPlaceholderText(pwtxtCurrent, PLACEHOLDER_PASSWORD);
        contentPane.add(pwtxtCurrent, "cell 1 2");
        pwtxtCurrent.setColumns(20);

        JLabel lblSignUpPrompt = new JLabel("<html><body><b>Customers only:</b> No account yet?<body></html>");
        lblSignUpPrompt.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblSignUpPrompt, "flowx,cell 1 5");

        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setPreferredSize(new Dimension(100, 25));
        contentPane.add(btnSignUp, "cell 1 6");
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                txtEmail.setText("");
                pwtxtCurrent.setText("");
                SignupGUI signup = new SignupGUI();
                signup.setVisible(true);
                signup.getRootPane().requestFocus(false);
                dispose();
            }
        });

        CompoundBorder CBorder4 = new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(4, 4, 4, 4));
        txtEmail.setBorder(CBorder4);
        pwtxtCurrent.setBorder(CBorder4);

        JButton btnSignIn = new JButton("Sign In");
        contentPane.add(btnSignIn, "cell 1 3");
        btnSignIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String email = txtEmail.getText().trim();
                String password = new String(pwtxtCurrent.getPassword());
                
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
                            pwtxtCurrent.setText("");
                            CustomerGUI customer = new CustomerGUI(CusID);
                            customer.setVisible(true);
                            dispose();
                            break;

                        case STAFF:
                            String StaffID = M.getIDByEmail(email);
                            txtEmail.setText("");
                            pwtxtCurrent.setText("");
                            StaffGUI staff = new StaffGUI(StaffID);
                            staff.setVisible(true);
                            dispose();
                            break;

                        case ADMIN:
                            String AdminID = M.getIDByEmail(email);
                            txtEmail.setText("");
                            pwtxtCurrent.setText("");
                            
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
    }
}
