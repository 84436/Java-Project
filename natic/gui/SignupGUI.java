package natic.gui;

import javax.swing.*;
import javax.swing.border.*;

import natic.*;
import natic.account.*;
import natic.account.AccountEnums.AccountType;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.regex.*;

import net.miginfocom.swing.MigLayout;

public class SignupGUI extends JFrame {

    private Mediator M = Mediator.getInstance();

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
            "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");

    private JPanel contentPane;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField pwtxtNew;
    private JTextField pwtxtConfirm;

    private static final String PLACEHOLDER_EMAIL = "Email";
    private static final String PLACEHOLDER_PHONE = "Phone";
    private static final String PLACEHOLDER_PASSWORD = "password";
    private static final String PLACEHOLDER_PASSWORD_CONFIRM = "password";

    /**
     * Create the frame.
     */
    public SignupGUI() {
        // Native LAF
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                LoginGUI loginForm = new LoginGUI();
                loginForm.setVisible(true);
                loginForm.getRootPane().requestFocus(false);
                dispose();
            }
        });

        

        setTitle("NATiC: Sign Up");
        setBounds(0, 0, 500, 400);
        GUIHelpers.centerWindow(this);
        GUIHelpers.setGlobalFont("SansSerif", Font.PLAIN, 14);

        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("fillx", "[36px][grow,fill][36px]", "[72px][36px,fill][36px,fill][36px,fill][36px,fill][36px,fill][24px][36px,center][grow]"));

        setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);

        JLabel lblSignUpHeader = new JLabel("Welcome to NATiC");
        lblSignUpHeader.setHorizontalAlignment(SwingConstants.CENTER);
        lblSignUpHeader.setFont(new Font("SansSerif", Font.BOLD, 36));
        contentPane.add(lblSignUpHeader, "cell 1 0");

        txtEmail = new JTextField();
        GUIHelpers.addPlaceholderText(txtEmail, PLACEHOLDER_EMAIL);
        contentPane.add(txtEmail, "cell 1 1");
        txtEmail.setColumns(25);

        txtPhone = new JTextField();
        GUIHelpers.addPlaceholderText(txtPhone, PLACEHOLDER_PHONE);
        contentPane.add(txtPhone, "cell 1 2");
        txtPhone.setColumns(25);

        pwtxtNew = new JPasswordField();
        GUIHelpers.addPlaceholderText(pwtxtNew, PLACEHOLDER_PASSWORD);
        contentPane.add(pwtxtNew, "cell 1 3");
        pwtxtNew.setColumns(25);

        pwtxtConfirm = new JPasswordField();
        GUIHelpers.addPlaceholderText(pwtxtConfirm, PLACEHOLDER_PASSWORD_CONFIRM);
        contentPane.add(pwtxtConfirm, "cell 1 4");
        pwtxtConfirm.setColumns(25);

        JLabel lblSignInPrompt = new JLabel("<html><body><b>Already have an account?</b> Close this window to sign in.</body></html>");
        lblSignInPrompt.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblSignInPrompt, "cell 1 7");

        CompoundBorder CBorder4 = new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(4, 4, 4, 4));
        txtEmail.setBorder(CBorder4);
        txtPhone.setBorder(CBorder4);
        pwtxtNew.setBorder(CBorder4);
        pwtxtConfirm.setBorder(CBorder4);

        JButton btnSignUp = new JButton("Sign Up");
        contentPane.add(btnSignUp, "flowx, cell 1 5");
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Customer newCustomer = new Customer(M);
                String email = txtEmail.getText().trim();
                String phone = txtPhone.getText().trim();
                String password = pwtxtNew.getText().trim();
                String confirmpwd = pwtxtConfirm.getText().trim();

                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

                if (email.equals(PLACEHOLDER_EMAIL) || email.isBlank()) {
                    GUIHelpers.showErrorDialog("Blank email", null);
                    return;
                }

                if (!matcher.matches()) {
                    GUIHelpers.showErrorDialog("Invalid email format", null);
                    return;
                }

                if (phone.equals(PLACEHOLDER_PHONE) || phone.isBlank()) {
                    GUIHelpers.showErrorDialog("Blank phone", null);
                    return;
                }

                if (phone.length() != 10) {
                    GUIHelpers.showErrorDialog("Phone length must be exactly 10 characters.", null);
                    return;
                }

                if (password.equals(PLACEHOLDER_PASSWORD) || password.isBlank() || confirmpwd.equals(PLACEHOLDER_PASSWORD_CONFIRM) || confirmpwd.isBlank()) {
                    GUIHelpers.showErrorDialog("Blank password / password cannot be \"password\" itself", null);
                    return;
                }

                if (!password.equals(confirmpwd)) {
                    pwtxtNew.setText("");
                    pwtxtConfirm.setText("");
                    GUIHelpers.showErrorDialog("Passwords do not match", null);
                    return;
                }

                newCustomer.setEmail(email);
                newCustomer.setPhone(phone);
                newCustomer.setPass(password);
                newCustomer.setType(AccountType.CUSTOMER);
                newCustomer.setName("");
                newCustomer.setDoB(LocalDate.now());
                newCustomer.setAddress("");
                newCustomer.setSignUpDate(LocalDate.now());

                try {
                    M.createAccount(newCustomer);
                    String customerID = M.getIDByEmail(email);
                    CustomerGUI customerFrame = new CustomerGUI(customerID);
                    customerFrame.setVisible(true);
                    dispose();
                }
                catch (Exception exc) {
                    GUIHelpers.showErrorDialog("Unable to sign up. Check all your fields again.", exc);
                }
            }
        });
    }
}
