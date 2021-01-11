package natic.gui;

import javax.swing.*;
import javax.swing.border.*;

import natic.*;
import natic.account.*;
import natic.account.AccountEnums.AccountType;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

import net.miginfocom.swing.MigLayout;

public class SignupGUI extends JFrame {

    private Mediator M = Mediator.getInstance();

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
            "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");

    private JPanel contentPane;
    private JTextField EmailField;
    private JTextField PhoneField;
    private JTextField pwdField;
    private JTextField confirmpwdField;
    private JButton btnSignUp;
    private JSeparator separator_1;

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
        GUIHelpers.addPlaceholderText(EmailField, PLACEHOLDER_EMAIL);
        panel.add(EmailField, "cell 0 2,alignx center");
        EmailField.setColumns(25);

        PhoneField = new JTextField();
        GUIHelpers.addPlaceholderText(PhoneField, PLACEHOLDER_PHONE);
        panel.add(PhoneField, "cell 0 3,alignx center");
        PhoneField.setColumns(25);

        pwdField = new JPasswordField();
        GUIHelpers.addPlaceholderText(pwdField, PLACEHOLDER_PASSWORD);
        panel.add(pwdField, "cell 0 4,alignx center");
        pwdField.setColumns(25);

        confirmpwdField = new JPasswordField();
        GUIHelpers.addPlaceholderText(confirmpwdField, PLACEHOLDER_PASSWORD_CONFIRM);
        panel.add(confirmpwdField, "cell 0 5,alignx center");
        confirmpwdField.setColumns(25);

        separator_1 = new JSeparator();
        panel.add(separator_1, "cell 0 6");

        btnSignUp = new JButton("Sign Up");
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Customer newCustomer = new Customer(M);
                String email = EmailField.getText().trim();
                String phone = PhoneField.getText().trim();
                String password = pwdField.getText().trim();
                String confirmpwd = confirmpwdField.getText().trim();

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
                    pwdField.setText("");
                    confirmpwdField.setText("");
                    GUIHelpers.showErrorDialog("Passwords do not match", null);
                    return;
                }

                newCustomer.setEmail(email);
                newCustomer.setPhone(phone);
                newCustomer.setPass(password);
                newCustomer.setType(AccountType.CUSTOMER);
                newCustomer.setName("");

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
        panel.add(btnSignUp, "flowx,cell 0 7,alignx center");
    }

}
