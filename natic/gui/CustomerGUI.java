package natic.gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.*;

import java.awt.*;
import java.awt.event.*;

import net.miginfocom.swing.MigLayout;
import java.util.*;
import java.text.*;

public class CustomerGUI extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private JTextField txtAccountName;
    private JTextField txtAccountEmail;
    private JTextField txtAccountPhone;
    private JPasswordField pwtxtOld;
    private JPasswordField pwtxtNew;
    private JPasswordField pwtxtConfirm;
    
    private JTable tblLibrary;
    private JTextField txtLibrarySearch;
    private JTextField txtLibraryISBN;
    private JTextField txtLibraryTitle;
    private JTextField txtLibraryAuthor;
    private JTextField txtLibraryVersionID;
    private JTextField txtLibraryYear;
    private JTextField txtLibraryPublisher;
    private JTextField txtLibraryGenre;
    private JTextField txtLibraryFormat;
    private JTextField txtLibraryPrice;
    
    private JButton btnBuy;
    private JButton btnRent;
    private JLabel lblPurchaseState;
    
    private JTable tblOrders;
    private JTextField txtOrderISBN;
    private JTextField txtOrderTitle;
    private JTextField txtOrderAuthor;
    private JTextField txtOrderVersionID;
    private JTextField txtOrderYear;
    private JTextField txtOrderPublisher;
    private JTextField txtOrderGenre;
    private JTextField txtOrderFormat;
    private JTextField txtOrderPrice;
    private JTextField txtOrderID;
    private JTextField txtOrderDate;
    private JTextField txtOrderStaffID;
    
    // https://stackoverflow.com/a/27873384
    // It's hard to believe Java Swing can understand HTML4 for basic formatting, but it _does_
    
    private static String styledTabName(String tabName) {
        String template = "<html><body style=\"%s\">%s</body></html>";
        String style = "margin: 20px 5px; font-family: SansSerif; font-size: 14pt; font-weight: bold;";
        return String.format(template, style, tabName);
    }
    
    private static String styledHeaderLabel(String text) {
        String template = "<html><body style=\"%s\">%s</body></html>";
        String style = "font-weight: bold; font-size: 24pt";
        return String.format(template, style, text);
    }
    
    private static String styledHeaderSmallLabel(String text) {
        String template = "<html><body style=\"%s\">%s</body></html>";
        String style = "font-weight: bold; font-size: 18pt";
        return String.format(template, style, text);
    }
    
    // adapted from https://stackoverflow.com/a/7434935
    private static void setGlobalFont(String family, int style, int size) {
        var newFont = new FontUIResource(family, style, size);
        var keys = UIManager.getLookAndFeelDefaults().keys();
        while (keys.hasMoreElements()) {
            var key = keys.nextElement();
            var value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.getLookAndFeelDefaults().replace(key, newFont);
            }
        }
    }
    
    /**
     * A non-intrusive method for adding pseudo-placeholder text in JTextFields.
     * (Why "pseudo"? There's a pretty obvious UX bug in this implementation.)
     * @param field Instance of JTextField
     * @param text Placeholder string
     */
    private static void addPlaceholderText(JTextField field, String text) {
        // Initial state should be placeholder
        field.setForeground(Color.GRAY);
        field.setText(text);
        
        // Subsequent states
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(text)) {
                    field.setForeground(Color.BLACK);
                    field.setText("");
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isBlank()) {
                    field.setForeground(Color.GRAY);
                    field.setText(text);
                }
            }
        });
    }
    
    private static void addPasswordRevealToggleEvent(JToggleButton btn, JPasswordField passfield) {
        btn.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (btn.isSelected()) {
                    passfield.setEchoChar('\u0000');
                }
                else {
                    passfield.setEchoChar('●'); // default
                }
            }
        });
    }
    
    // MigLayout "sizegroup main" constraint: https://stackoverflow.com/a/60187262
    
    public CustomerGUI() {
        
        /**
         * Base
         */
        
        // Native LAF
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();;
        }
        
        // Basics
        setTitle("NATiC: Customer");
        setGlobalFont("SansSerif", Font.PLAIN, 14);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
        
        // Base tabbed layout
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        getContentPane().add(tabbedPane, "cell 0 0,grow");
        
        
        
        /**
         * Account Panel
         */
        
        JPanel Account = new JPanel();
        tabbedPane.addTab(styledTabName("{customer}"), null, Account, null);
        Account.setLayout(new MigLayout("", "[grow,fill]", "[grow][36.00,fill]"));
        
        JPanel AccountInfoEdit = new JPanel();
        Account.add(AccountInfoEdit, "cell 0 0,grow");
        AccountInfoEdit.setLayout(new MigLayout("", "[grow,fill][grow,fill][fill]", "[][][][][][][40px,center][][][][][]"));
        
        // Separator between About and Password section
        JSeparator sepAccount = new JSeparator();
        sepAccount.setForeground(new Color(192, 192, 192));
        AccountInfoEdit.add(sepAccount, "cell 0 6 3 1");
        
        JButton btnLogOut = new JButton("Log out");
        Account.add(btnLogOut, "cell 0 1");
        
        
        
        /**
         * Account Panel -> About
         */
        
        JLabel lblHeaderAccountAbout = new JLabel(styledHeaderLabel("About you"));
        JLabel lblAccountName = new JLabel("Name");
        JLabel lblAccountEmail = new JLabel("Email");
        JLabel lblAccountPhone = new JLabel("Phone");
        txtAccountName = new JTextField();
        txtAccountEmail = new JTextField();
        txtAccountPhone = new JTextField();
        
        txtAccountName.setColumns(10);
        txtAccountEmail.setColumns(10);
        txtAccountPhone.setColumns(10);
        
        JButton btnAccountAboutSave = new JButton("Save changes");
        
        AccountInfoEdit.add(lblHeaderAccountAbout, "cell 0 0 3 1,alignx trailing");
        AccountInfoEdit.add(lblAccountName, "cell 0 1");
        AccountInfoEdit.add(lblAccountEmail, "cell 0 2,alignx trailing");
        AccountInfoEdit.add(lblAccountPhone, "cell 0 3,alignx trailing");
        AccountInfoEdit.add(txtAccountName, "cell 1 1 2 1,growx");
        AccountInfoEdit.add(txtAccountEmail, "cell 1 2 2 1,growx");
        AccountInfoEdit.add(txtAccountPhone, "cell 1 3 2 1,growx");
        AccountInfoEdit.add(btnAccountAboutSave, "cell 1 5 2 1");
        
        JLabel lblAccountDoB = new JLabel("Date of Birth");
        AccountInfoEdit.add(lblAccountDoB, "cell 0 4,alignx trailing");
        
        JFormattedTextField ftxtAccountDoB = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        ftxtAccountDoB.setValue(new Date());
        AccountInfoEdit.add(ftxtAccountDoB, "cell 1 4 2 1,growx");
        
        
        
        /**
         * Account Panel -> Password
         */
        
        JLabel lblHeaderPassword = new JLabel(styledHeaderLabel("Password"));
        JLabel lblPasswordOld = new JLabel("Old password");
        JLabel lblPasswordNew = new JLabel("New password");
        JLabel lblPasswordConfirm = new JLabel("Confirm password");
        pwtxtOld = new JPasswordField();
        pwtxtNew = new JPasswordField();
        pwtxtConfirm = new JPasswordField();
        JButton btnPasswordSave = new JButton("Change password");
        
        // \u1F441 = eye symbol
        JToggleButton btnPasswordOldReveal = new JToggleButton("👁");
        JToggleButton btnPasswordNewReveal = new JToggleButton("👁");
        JToggleButton btnPasswordConfirmReveal = new JToggleButton("👁");
        addPasswordRevealToggleEvent(btnPasswordOldReveal, pwtxtOld);
        addPasswordRevealToggleEvent(btnPasswordNewReveal, pwtxtNew);
        addPasswordRevealToggleEvent(btnPasswordConfirmReveal, pwtxtConfirm);
        
        AccountInfoEdit.add(lblHeaderPassword, "cell 0 7 3 1");
        AccountInfoEdit.add(lblPasswordOld, "cell 0 8,alignx trailing");
        AccountInfoEdit.add(lblPasswordNew, "cell 0 9,alignx trailing");
        AccountInfoEdit.add(lblPasswordConfirm, "cell 0 10,alignx trailing");
        AccountInfoEdit.add(pwtxtOld, "cell 1 8,growx");
        AccountInfoEdit.add(pwtxtNew, "cell 1 9,growx");
        AccountInfoEdit.add(pwtxtConfirm, "cell 1 10,growx");
        AccountInfoEdit.add(btnPasswordOldReveal, "cell 2 8");
        AccountInfoEdit.add(btnPasswordNewReveal, "cell 2 9");
        AccountInfoEdit.add(btnPasswordConfirmReveal, "cell 2 10");
        AccountInfoEdit.add(btnPasswordSave, "cell 1 11 2 1");
        
        
        
        /**
         * Library
         */
        
        JPanel Library = new JPanel();
        tabbedPane.addTab(styledTabName("Library"), null, Library, null);
        Library.setLayout(new MigLayout("", "[grow,sizegroup main,fill][grow,sizegroup main,fill]", "[36.00,fill][][grow]"));
        
        txtLibrarySearch = new JTextField();
        txtLibrarySearch.setColumns(20);
        addPlaceholderText(txtLibrarySearch, "Search by title or ISBN");
        
        JCheckBox checkPurchased = new JCheckBox("Show only purchased books");
        
        JPanel BookActions = new JPanel();
        
        btnBuy = new JButton("Buy");
        btnBuy.setPreferredSize(new Dimension(100, 25));
        btnRent = new JButton("Rent");
        btnRent.setPreferredSize(new Dimension(100, 25));
        
        tblLibrary = new JTable();
        
        JTextPane txtBookDetails = new JTextPane();
        txtBookDetails.setEditable(false);
        
        JScrollPane scrollLibrary = new JScrollPane();
        JScrollPane scrollBookDetails = new JScrollPane();
        
        scrollLibrary.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLibrary.setViewportView(tblLibrary);
        scrollBookDetails.setViewportView(txtBookDetails);
        
        Library.add(txtLibrarySearch, "cell 0 0,growx,aligny top");
        BookActions.add(btnBuy);
        BookActions.add(btnRent);
        
        Library.add(BookActions, "cell 1 0,grow");
        Library.add(checkPurchased, "cell 0 1");
        Library.add(scrollLibrary, "cell 0 2,grow");
        Library.add(scrollBookDetails, "cell 1 1 1 2,grow");
        
        
        /**
         * Library -> Book Details
         */
        
        JPanel BookDetails = new JPanel();
        scrollBookDetails.setViewportView(BookDetails);
        BookDetails.setLayout(new MigLayout("", "[80.00px,fill][grow,fill]", "[][][][][][][40px,center][][][][][][][]"));
        
        JLabel lblBookCover = new JLabel("");
        JLabel lblHeaderLibraryCommonFields = new JLabel(styledHeaderSmallLabel("Overview"));
        JLabel lblLibraryISBN = new JLabel("ISBN");
        JLabel lblLibraryTitle = new JLabel("Title");
        JLabel lblLibraryAuthor = new JLabel("Author");
        JLabel lblHeaderLibraryDetails = new JLabel(styledHeaderSmallLabel("Details"));
        JLabel lblLibraryVersionID = new JLabel("Version ID");
        JLabel lblLibraryYear = new JLabel("Year");
        JLabel lblLibraryPublisher = new JLabel("Publisher");
        JLabel lblLibraryGenre = new JLabel("Genre");
        JLabel lblLibraryFormat = new JLabel("Format");
        JLabel lblLibraryPrice = new JLabel("Price");
        
        txtLibraryISBN = new JTextField();
        txtLibraryTitle = new JTextField();
        txtLibraryAuthor = new JTextField();
        JSeparator sepBookDetails = new JSeparator();
        txtLibraryVersionID = new JTextField();
        txtLibraryYear = new JTextField();
        txtLibraryPublisher = new JTextField();
        txtLibraryGenre = new JTextField();
        txtLibraryFormat = new JTextField();
        txtLibraryPrice = new JTextField();
        
        txtLibraryISBN.setEditable(false);
        txtLibraryTitle.setEditable(false);
        txtLibraryAuthor.setEditable(false);
        txtLibraryVersionID.setEditable(false);
        txtLibraryYear.setEditable(false);
        txtLibraryPublisher.setEditable(false);
        txtLibraryGenre.setEditable(false);
        txtLibraryFormat.setEditable(false);
        txtLibraryPrice.setEditable(false);
        
        txtLibraryISBN.setEnabled(false);
        txtLibraryTitle.setEnabled(false);
        txtLibraryAuthor.setEnabled(false);
        txtLibraryVersionID.setEnabled(false);
        txtLibraryYear.setEnabled(false);
        txtLibraryPublisher.setEnabled(false);
        txtLibraryGenre.setEnabled(false);
        txtLibraryFormat.setEnabled(false);
        txtLibraryPrice.setEnabled(false);
        
        txtLibraryISBN.setColumns(10);
        txtLibraryTitle.setColumns(10);
        txtLibraryAuthor.setColumns(10);
        txtLibraryVersionID.setColumns(10);
        txtLibraryYear.setColumns(10);
        txtLibraryPublisher.setColumns(10);
        txtLibraryGenre.setColumns(10);
        txtLibraryFormat.setColumns(10);
        txtLibraryPrice.setColumns(10);
        
        sepBookDetails.setForeground(new Color(192, 192, 192));
        
        lblPurchaseState = new JLabel("{current rental/purchase state}");
        BookDetails.add(lblPurchaseState, "cell 0 0 2 1");
        lblPurchaseState.setHorizontalAlignment(SwingConstants.CENTER);
        
        BookDetails.add(lblBookCover, "cell 0 1 2 1");
        BookDetails.add(lblHeaderLibraryCommonFields, "cell 0 2 2 1");
        BookDetails.add(lblLibraryISBN, "cell 0 3");
        BookDetails.add(lblLibraryTitle, "cell 0 4");
        BookDetails.add(lblLibraryAuthor, "cell 0 5");
        BookDetails.add(lblHeaderLibraryDetails, "cell 0 7 2 1");
        BookDetails.add(lblLibraryVersionID, "cell 0 8");
        BookDetails.add(lblLibraryYear, "cell 0 9");
        BookDetails.add(lblLibraryPublisher, "cell 0 10");
        BookDetails.add(lblLibraryGenre, "cell 0 11");
        BookDetails.add(lblLibraryFormat, "cell 0 12");
        BookDetails.add(lblLibraryPrice, "cell 0 13");
        
        BookDetails.add(txtLibraryISBN, "cell 1 3,growx");
        BookDetails.add(txtLibraryTitle, "cell 1 4,growx");
        BookDetails.add(txtLibraryAuthor, "cell 1 5,growx");
        BookDetails.add(sepBookDetails, "cell 0 6 2 1");
        BookDetails.add(txtLibraryVersionID, "cell 1 8");
        BookDetails.add(txtLibraryYear, "cell 1 9");
        BookDetails.add(txtLibraryPublisher, "cell 1 10,growx");
        BookDetails.add(txtLibraryGenre, "cell 1 11,growx");
        BookDetails.add(txtLibraryFormat, "cell 1 12,growx");
        BookDetails.add(txtLibraryPrice, "cell 1 13");
        
        
        
        /**
         * Orders
         */
        
        JPanel Orders = new JPanel();
        tabbedPane.addTab(styledTabName("Orders"), null, Orders, null);
        Orders.setLayout(new MigLayout("", "[grow,sizegroup main,fill][grow,sizegroup main,fill]", "[grow]"));
        
        tblOrders = new JTable();
        
        JTextPane txtOrderDetails = new JTextPane();
        txtOrderDetails.setEditable(false);
        
        JScrollPane scrollOrders = new JScrollPane();
        JScrollPane scrollOrderDetails = new JScrollPane();
        
        scrollOrders.setViewportView(tblOrders);
        scrollOrderDetails.setViewportView(txtOrderDetails);
        
        Orders.add(scrollOrders, "cell 0 0,grow");
        Orders.add(scrollOrderDetails, "cell 1 0,grow");
        
        
        
        /**
         * Orders -> Order Detail
         */
        
        JPanel OrderDetails = new JPanel();
        scrollOrderDetails.setViewportView(OrderDetails);
        OrderDetails.setLayout(new MigLayout("", "[80.00px,fill][grow,fill]", "[][][][][40px,center][][][][][][][][][][]"));
        
        JLabel lblHeaderOrderOverview = new JLabel(styledHeaderSmallLabel("Order info"));
        JLabel lblOrderID = new JLabel("Order ID");
        JLabel lblOrderDate = new JLabel("Date");
        JLabel lblOrderStaffID = new JLabel("Staff ID");
        JLabel lblHeaderOrderBookDetails = new JLabel(styledHeaderSmallLabel("Book details"));
        JLabel lblOrderISBN = new JLabel("ISBN");
        JLabel lblOrderTitle = new JLabel("Title");
        JLabel lblOrderAuthor = new JLabel("Author");
        JLabel lblOrderVersionID = new JLabel("Version ID");
        JLabel lblOrderYear = new JLabel("Year");
        JLabel lblOrderPublisher = new JLabel("Publisher");
        JLabel lblOrderGenre = new JLabel("Genre");
        JLabel lblOrderFormat = new JLabel("Format");
        JLabel lblOrderPrice = new JLabel("Price");
        
        txtOrderID = new JTextField();
        txtOrderDate = new JTextField();
        txtOrderStaffID = new JTextField();
        txtOrderISBN = new JTextField();
        txtOrderTitle = new JTextField();
        txtOrderAuthor = new JTextField();
        txtOrderVersionID = new JTextField();
        txtOrderYear = new JTextField();
        txtOrderPublisher = new JTextField();
        txtOrderGenre = new JTextField();
        txtOrderFormat = new JTextField();
        txtOrderPrice = new JTextField();
        
        txtOrderID.setEditable(false);
        txtOrderDate.setEditable(false);
        txtOrderStaffID.setEditable(false);
        txtOrderISBN.setEditable(false);
        txtOrderTitle.setEditable(false);
        txtOrderAuthor.setEditable(false);
        txtOrderVersionID.setEditable(false);
        txtOrderYear.setEditable(false);
        txtOrderPublisher.setEditable(false);
        txtOrderGenre.setEditable(false);
        txtOrderFormat.setEditable(false);
        txtOrderPrice.setEditable(false);
        
        txtOrderID.setEnabled(false);
        txtOrderDate.setEnabled(false);
        txtOrderStaffID.setEnabled(false);
        txtOrderISBN.setEnabled(false);
        txtOrderTitle.setEnabled(false);
        txtOrderAuthor.setEnabled(false);
        txtOrderVersionID.setEnabled(false);
        txtOrderYear.setEnabled(false);
        txtOrderPublisher.setEnabled(false);
        txtOrderGenre.setEnabled(false);
        txtOrderFormat.setEnabled(false);
        txtOrderPrice.setEnabled(false);
        
        txtOrderID.setColumns(10);
        txtOrderDate.setColumns(10);
        txtOrderStaffID.setColumns(10);
        txtOrderISBN.setColumns(10);
        txtOrderTitle.setColumns(10);
        txtOrderAuthor.setColumns(10);
        txtOrderVersionID.setColumns(10);
        txtOrderYear.setColumns(10);
        txtOrderPublisher.setColumns(10);
        txtOrderGenre.setColumns(10);
        txtOrderFormat.setColumns(10);
        txtOrderPrice.setColumns(10);
        
        JSeparator sepOrderDetails = new JSeparator();
        sepOrderDetails.setForeground(Color.LIGHT_GRAY);
        
        OrderDetails.add(lblHeaderOrderOverview, "cell 0 0 2 1");
        OrderDetails.add(lblOrderID, "cell 0 1,alignx trailing");
        OrderDetails.add(lblOrderDate, "cell 0 2,alignx trailing");
        OrderDetails.add(lblOrderStaffID, "cell 0 3,alignx trailing");
        OrderDetails.add(sepOrderDetails, "cell 0 4 2 1");
        OrderDetails.add(lblHeaderOrderBookDetails, "cell 0 5 2 1");
        OrderDetails.add(lblOrderISBN, "cell 0 6");
        OrderDetails.add(lblOrderTitle, "cell 0 7");
        OrderDetails.add(lblOrderAuthor, "cell 0 8");
        OrderDetails.add(lblOrderVersionID, "cell 0 9");
        OrderDetails.add(lblOrderYear, "cell 0 10");
        OrderDetails.add(lblOrderPublisher, "cell 0 11");
        OrderDetails.add(lblOrderGenre, "cell 0 12");
        OrderDetails.add(lblOrderFormat, "cell 0 13");
        OrderDetails.add(lblOrderPrice, "cell 0 14");
        
        OrderDetails.add(txtOrderID, "cell 1 1,growx");
        OrderDetails.add(txtOrderDate, "cell 1 2,growx");
        OrderDetails.add(txtOrderStaffID, "cell 1 3,growx");
        OrderDetails.add(txtOrderISBN, "cell 1 6,growx");
        OrderDetails.add(txtOrderTitle, "cell 1 7,growx");
        OrderDetails.add(txtOrderAuthor, "cell 1 8,growx");
        OrderDetails.add(txtOrderVersionID, "cell 1 9");
        OrderDetails.add(txtOrderYear, "cell 1 10");
        OrderDetails.add(txtOrderPublisher, "cell 1 11,growx");
        OrderDetails.add(txtOrderGenre, "cell 1 12,growx");
        OrderDetails.add(txtOrderFormat, "cell 1 13,growx");
        OrderDetails.add(txtOrderPrice, "cell 1 14");
        
        
        
        /**
         * Cosmetics, etc.
         */
        
        // Whiten (almost) everything
                                     setBackground(Color.WHITE);
        getContentPane()            .setBackground(Color.WHITE);
        tabbedPane                  .setBackground(Color.WHITE);
        Account                     .setBackground(Color.WHITE);
        AccountInfoEdit             .setBackground(Color.WHITE);
        Library                     .setBackground(Color.WHITE);
        scrollLibrary               .setBackground(Color.WHITE);
        scrollBookDetails           .setBackground(Color.WHITE);
        Orders                      .setBackground(Color.WHITE);
        scrollOrders                .setBackground(Color.WHITE);
        scrollOrderDetails          .setBackground(Color.WHITE);
        checkPurchased              .setBackground(Color.WHITE);
        
        // Uniform border: 1px #c0c0c0 + variable padding
        CompoundBorder CBorder0 = new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(0, 0, 0, 0));
        CompoundBorder CBorder4 = new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(4, 4, 4, 4));
        CompoundBorder CBorder8 = new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(8, 8, 8, 8));
        
        AccountInfoEdit             .setBorder(CBorder8);
        BookActions                 .setBorder(CBorder0);
        scrollLibrary               .setBorder(CBorder0);
        scrollBookDetails           .setBorder(CBorder0);
        scrollOrders                .setBorder(CBorder0);
        scrollOrderDetails          .setBorder(CBorder0);
        txtLibrarySearch            .setBorder(CBorder4);
        
        // Transparent scrollpane background
        scrollLibrary             .getViewport().setOpaque(false);
        scrollBookDetails         .getViewport().setOpaque(false);
        scrollOrders              .getViewport().setOpaque(false);
        scrollOrderDetails        .getViewport().setOpaque(false);
        
        // Always select 2nd tab on startup
        tabbedPane.setSelectedIndex(1);
        
        // Library -> that checkbox: fire default state
        toggleLibraryView(checkPurchased.isSelected());
        
        /**
         * Events -> Account
         */
        
        btnAccountAboutSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: AccountAboutSave");
            }
        });
        
        btnPasswordSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: PasswordSave");
            }
        });
        
        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: LogOut");
            }
        });
        
        /**
         * Events -> Library
         */
        
        btnBuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: Buy");
            }
        });
        
        btnRent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: Rent");
            }
        });
        
        checkPurchased.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("check: LibraryPurchasedOnly: " + checkPurchased.isSelected());
                toggleLibraryView(checkPurchased.isSelected());
            }
        });
        
        /**
         * Events -> Orders
         */
    }
    
    private void toggleLibraryView(boolean purchasedOnly) {
        if (purchasedOnly) {
            btnBuy              .setEnabled(false);
            btnRent             .setEnabled(false);
            lblPurchaseState    .setVisible(true);
        }
        else {
            btnBuy              .setEnabled(true);
            btnRent             .setEnabled(true);
            lblPurchaseState    .setVisible(false);
        }
    }
}
