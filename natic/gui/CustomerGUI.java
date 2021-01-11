package natic.gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import net.miginfocom.swing.MigLayout;

import java.util.*;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import natic.*;
import natic.account.Customer;
import natic.book.Book;
import natic.book.CustomerLibrary;
import natic.receipt.Receipt;
import natic.review.Review;

public class CustomerGUI extends JFrame {
    
    private static final long serialVersionUID = 1L;
    Mediator M = Mediator.getInstance();
    Customer customer;
    
    private JTextField txtAccountName;
    private JTextField txtAccountEmail;
    private JTextField txtAccountPhone;
    private JFormattedTextField ftxtAccountDoB;
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
    private JLabel lblLibraryRatingAvg;
    
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
    
    // MigLayout "sizegroup main" constraint: https://stackoverflow.com/a/60187262
    
    public CustomerGUI(String ID) {
        try {
            customer = M.getCustomerByID(ID);
        } catch (SQLException exec) {
            exec.printStackTrace();
        }

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
        GUIHelpers.setGlobalFont("SansSerif", Font.PLAIN, 14);
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
        tabbedPane.addTab(GUIHelpers.htmlTabName("{customer}"), null, Account, null);
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
        
        JLabel lblHeaderAccountAbout = new JLabel(GUIHelpers.htmlHeader("About you"));
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
        
        ftxtAccountDoB = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        ftxtAccountDoB.setValue(new Date());
        AccountInfoEdit.add(ftxtAccountDoB, "cell 1 4 2 1,growx");
        
        
        
        /**
         * Account Panel -> Password
         */
        
        JLabel lblHeaderPassword = new JLabel(GUIHelpers.htmlHeader("Password"));
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
        GUIHelpers.addPasswordRevealToggleEvent(btnPasswordOldReveal, pwtxtOld);
        GUIHelpers.addPasswordRevealToggleEvent(btnPasswordNewReveal, pwtxtNew);
        GUIHelpers.addPasswordRevealToggleEvent(btnPasswordConfirmReveal, pwtxtConfirm);
        
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
        tabbedPane.addTab(GUIHelpers.htmlTabName("Library"), null, Library, null);
        Library.setLayout(new MigLayout("", "[grow,sizegroup main,fill][grow,sizegroup main,fill]", "[36.00,fill][][grow]"));
        
        txtLibrarySearch = new JTextField();
        txtLibrarySearch.setColumns(20);
        GUIHelpers.addPlaceholderText(txtLibrarySearch, "Search by title or ISBN");
        
        JCheckBox checkPurchased = new JCheckBox("Show only purchased books");
        checkPurchased.setSelected(true);
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
        BookDetails.setLayout(new MigLayout("", "[80.00px,fill][grow,fill]", "[][][][][][40px,center][][][][][][][][40px,center][][][36px,fill]"));
        
        JLabel lblBookCover = new JLabel("");
        JLabel lblHeaderLibraryCommonFields = new JLabel(GUIHelpers.htmlHeaderSmall("Overview"));
        JLabel lblLibraryISBN = new JLabel("ISBN");
        JLabel lblLibraryTitle = new JLabel("Title");
        JLabel lblLibraryAuthor = new JLabel("Author");
        JLabel lblHeaderLibraryDetails = new JLabel(GUIHelpers.htmlHeaderSmall("Details"));
        JLabel lblLibraryVersionID = new JLabel("Version ID");
        JLabel lblLibraryYear = new JLabel("Year");
        JLabel lblLibraryPublisher = new JLabel("Publisher");
        JLabel lblLibraryGenre = new JLabel("Genre");
        JLabel lblLibraryFormat = new JLabel("Format");
        JLabel lblLibraryPrice = new JLabel("Price");
        JLabel lblHeaderLibraryReviews = new JLabel(GUIHelpers.htmlHeaderSmall("Reviews"));
        lblLibraryRatingAvg = new JLabel("Average rating"); 
        
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
        JSeparator sepBookDetails2 = new JSeparator();
        JButton btnLibraryShowReviews = new JButton("Show reviews");
        
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
        sepBookDetails2.setForeground(new Color(192, 192, 192));

        lblPurchaseState = new JLabel("{current rental/purchase state}");
        BookDetails.add(lblPurchaseState, "cell 0 0 2 1");
        lblPurchaseState.setHorizontalAlignment(SwingConstants.CENTER);
        
        BookDetails.add(lblBookCover, "cell 0 0 2 1");
        BookDetails.add(lblHeaderLibraryCommonFields, "cell 0 1 2 1");
        BookDetails.add(lblLibraryISBN, "cell 0 2");
        BookDetails.add(lblLibraryTitle, "cell 0 3");
        BookDetails.add(lblLibraryAuthor, "cell 0 4");
        BookDetails.add(lblHeaderLibraryDetails, "cell 0 6 2 1");
        BookDetails.add(lblLibraryVersionID, "cell 0 7");
        BookDetails.add(lblLibraryYear, "cell 0 8");
        BookDetails.add(lblLibraryPublisher, "cell 0 9");
        BookDetails.add(lblLibraryGenre, "cell 0 10");
        BookDetails.add(lblLibraryFormat, "cell 0 11");
        BookDetails.add(lblLibraryPrice, "cell 0 12");
        
        BookDetails.add(txtLibraryISBN, "cell 1 2,growx");
        BookDetails.add(txtLibraryTitle, "cell 1 3,growx");
        BookDetails.add(txtLibraryAuthor, "cell 1 4,growx");
        BookDetails.add(sepBookDetails, "cell 0 5 2 1");
        BookDetails.add(txtLibraryVersionID, "cell 1 7");
        BookDetails.add(txtLibraryYear, "cell 1 8");
        BookDetails.add(txtLibraryPublisher, "cell 1 9,growx");
        BookDetails.add(txtLibraryGenre, "cell 1 10,growx");
        BookDetails.add(txtLibraryFormat, "cell 1 11,growx");
        BookDetails.add(txtLibraryPrice, "cell 1 12");
        BookDetails.add(sepBookDetails2, "cell 0 13 2 1");
        BookDetails.add(lblHeaderLibraryReviews, "cell 0 14 2 1");
        BookDetails.add(lblLibraryRatingAvg, "cell 0 15 2 1");
        BookDetails.add(btnLibraryShowReviews, "cell 0 16 2 1");
        
        
        
        /**
         * Orders
         */
        
        JPanel Orders = new JPanel();
        tabbedPane.addTab(GUIHelpers.htmlTabName("Orders"), null, Orders, null);
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
        
        JLabel lblHeaderOrderOverview = new JLabel(GUIHelpers.htmlHeaderSmall("Order info"));
        JLabel lblOrderID = new JLabel("Order ID");
        JLabel lblOrderDate = new JLabel("Date");
        JLabel lblOrderStaffID = new JLabel("Staff ID");
        JLabel lblHeaderOrderBookDetails = new JLabel(GUIHelpers.htmlHeaderSmall("Book details"));
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
        
        



        /**
         * Events (Main)
         */

        // Library -> that checkbox: fire default state
        toggleLibraryView(checkPurchased.isSelected());

        // Tab: change on index change
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                final HashMap<Integer, String> tabMap = new HashMap<>() {{
                    put(0, "Account");
                    put(1, "Library");
                    put(2, "Orders");
                }};
                int tabIndex = tabbedPane.getSelectedIndex();
                Log.l.info("Tab changed to: " + tabMap.get(tabIndex));
                populateTab(tabIndex);
            }
        });
        
        // Tab: always select 2nd tab on startup
        tabbedPane.setSelectedIndex(1);
        
        /**
         * Events -> Account
         */
        
        btnAccountAboutSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: AccountAboutSave");

                String name = txtAccountName.getText().trim();
                String email = txtAccountEmail.getText().trim();
                String phone = txtAccountPhone.getText().trim();
                String dob = ftxtAccountDoB.getText().trim();

                if (name.isBlank()) name = null;
                if (email.isBlank()) email = null;
                if (phone.isBlank()) phone = null;
                if (dob.isBlank()) dob = null;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(dob, formatter);
                
                customer.setName(name);
                customer.setEmail(email);
                customer.setPhone(phone);
                customer.setDoB(localDate);

                try {
                    M.editAccount(customer);
                    populateAccountTab();
                }
                catch (Exception exc) {
                    GUIHelpers.showErrorDialog("Unable to edit account", exc);
                }
            }
        });
        
        btnPasswordSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: PasswordSave");
                Log.l.info("btn: PasswordSave");
                String oldPwd = pwtxtOld.getText();
                String newPwd = pwtxtNew.getText();
                String confirmPwd = pwtxtConfirm.getText();

                if (oldPwd.isBlank()) oldPwd = null;
                if (newPwd.isBlank()) newPwd = null;
                if (confirmPwd.isBlank()) confirmPwd = null;

                try {
                    if (newPwd.equals(confirmPwd)) {
                        M.changePassword(customer.getEmail(), oldPwd, newPwd);
                        pwtxtOld.setText("");
                        pwtxtNew.setText("");
                        pwtxtConfirm.setText("");
                    } else {
                        GUIHelpers.showErrorDialog("Password does not match", null);
                        pwtxtOld.setText("");
                        pwtxtNew.setText("");
                        pwtxtConfirm.setText("");
                    }
                }
                catch (Exception exc) {
                    GUIHelpers.showErrorDialog("Unable to change password", exc);
                } 
            }
        });
        
        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: LogOut");
                LoginGUI loginForm = new LoginGUI();
                loginForm.setVisible(true);
                loginForm.getRootPane().requestFocus(false);
                dispose();
                Log.l.info("CustomerGUI disposed");
            }
        });
        
        /**
         * Events -> Library
         */
        
        btnBuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: Buy");
            }
        });
        
        btnRent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: Rent");
            }
        });
        
        checkPurchased.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("check: LibraryPurchasedOnly: " + checkPurchased.isSelected());
                toggleLibraryView(checkPurchased.isSelected());
                populateLibraryTab(checkPurchased.isSelected());
            }
        });
        
        txtLibrarySearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("txt: LibrarySearch");

                String query = txtLibrarySearch.getText();

                String[] tableHeaders = { GUIHelpers.htmlBoldText("ISBN"), GUIHelpers.htmlBoldText("Title"),
                        GUIHelpers.htmlBoldText("Author"), GUIHelpers.htmlBoldText("Year") };

                ArrayList<ArrayList<Object>> tableData = new ArrayList<>();

                try {
                    if (checkPurchased.isSelected()) {
                        ArrayList<CustomerLibrary> customerLib = M.searchInCusLib(customer.getID(), query);
                        for (var book: customerLib) {
                            ArrayList<Object> record = new ArrayList<>();
                            record.add(book.getBook().getISBN());
                            record.add(book.getBook().getTitle());
                            record.add(book.getBook().getAuthor());
                            record.add(book.getBook().getYear());
                            tableData.add(record);
                        }
                    } else {
                        ArrayList<Book> books = M.searchBook(query);
                        for (var book : books) {
                            ArrayList<Object> record = new ArrayList<>();
                            record.add(book.getISBN());
                            record.add(book.getTitle());
                            record.add(book.getAuthor());
                            record.add(book.getYear());
                            tableData.add(record);
                        }
                    }
                    
                } catch (SQLException exec) {
                    exec.printStackTrace();
                }
                // extract and push each records

                CustomTableModel tbmLib = new CustomTableModel(tableHeaders, tableData);
                tblLibrary.setRowHeight(24);
                tblLibrary.setModel(tbmLib);
                Log.l.info("Library tab populated");
            }
        });

        btnLibraryShowReviews.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: LibraryShowReviews");
                try {
                    String isbn = txtLibraryISBN.getText().trim();
                    ArrayList<Review> reviews = M.getReview(isbn);
                    ReviewViewerGUI reviewViewerFrame = new ReviewViewerGUI(reviews);
                }
                catch (Exception exc) {
                    GUIHelpers.showErrorDialog("Unable to show reviews for books", exc);
                }
            }
        });
        
        tblLibrary.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tblLibrary.getSelectedRow();
                Log.l.info("Selected row index: " + selectedRow);

                if (selectedRow == -1)
                    return;

                String BookISBN = (String) tblLibrary.getModel().getValueAt(selectedRow, 0);

                if (checkPurchased.isSelected()) {
                    showBookDetails(BookISBN, true);
                } else {
                    showBookDetails(BookISBN, false);
                }
            }
        });
        /**
         * Events -> Orders
         */

        tblOrders.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tblOrders.getSelectedRow();
                Log.l.info("Selected row index: " + selectedRow);

                if (selectedRow == -1)
                    return;

                String ReceiptID = (String) tblOrders.getModel().getValueAt(selectedRow, 0);

                showBookDetails(ReceiptID, true);
            }
        });

        Log.l.info("Customer GUI init'd");
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

    private void populateTab(int tabIndex) {
        switch (tabIndex) {
            case 0: populateAccountTab(); break;
            case 1: populateLibraryTab(true); break;
            case 2: populateOrdersTab(); break;
        }
    }
    
    private void populateAccountTab() {
        txtAccountName.setText(customer.getName());
        txtAccountEmail.setText(customer.getEmail());
        txtAccountPhone.setText(customer.getPhone());
        ftxtAccountDoB.setText(String.format("%04d-%02d-%02d", customer.getDoB().getYear(), customer.getDoB().getMonth().getValue(), customer.getDoB().getDayOfMonth()));
        Log.l.info("Account tab populated");
    }
    
    private void populateLibraryTab(boolean isFilter) {
        String[] tableHeaders = {GUIHelpers.htmlBoldText("ISBN"), GUIHelpers.htmlBoldText("Title"), GUIHelpers.htmlBoldText("Author"), GUIHelpers.htmlBoldText("Year")};

        ArrayList<ArrayList<Object>> tableData = new ArrayList<>();

        try {
            if (isFilter) {
                ArrayList<CustomerLibrary> customerLib = M.getCustomerLibrary(customer.getID());
                for (var booklib: customerLib) {
                    ArrayList<Object> record = new ArrayList<>();
                    record.add(booklib.getBook().getISBN());
                    record.add(booklib.getBook().getTitle());
                    record.add(booklib.getBook().getAuthor());
                    record.add(booklib.getBook().getYear());
                    tableData.add(record);
                }
            }   
            else {
                ArrayList<Book> books = M.getAllBooks();
                for (var book: books) {
                    ArrayList<Object> record = new ArrayList<>();
                    record.add(book.getISBN());
                    record.add(book.getTitle());
                    record.add(book.getAuthor());
                    record.add(book.getYear());
                    tableData.add(record);
                }
            }
        }   
        catch (SQLException exec) {
            exec.printStackTrace();
        }
        // extract and push each records

        CustomTableModel tbmLib = new CustomTableModel(tableHeaders, tableData);
        tblLibrary.setRowHeight(24);
        tblLibrary.setModel(tbmLib);
        Log.l.info("Library tab populated");
    }
    
    private void populateOrdersTab() {
        String[] tableHeaders = {GUIHelpers.htmlBoldText("ID"), GUIHelpers.htmlBoldText("Title"), GUIHelpers.htmlBoldText("Price")};

        ArrayList<ArrayList<Object>> tableData = new ArrayList<>();

        try {
            ArrayList<Receipt> receipts = M.getAllReceipts(customer.getID());
            for (var receipt: receipts) {
                ArrayList<Object> record = new ArrayList<>();
                Book b = M.getByISBN(receipt.getISBN());
                record.add(receipt.getID());
                record.add(b.getTitle());
                record.add(receipt.getPrice());
                tableData.add(record);
            }
        } catch (SQLException exec) {
            exec.printStackTrace();
        }

        CustomTableModel tbmOrder = new CustomTableModel(tableHeaders, tableData);
        tblOrders.setRowHeight(24);
        tblOrders.setModel(tbmOrder);
        Log.l.info("Library tab populated");

        Log.l.info("Orders tab populated");
    }

    private void updateBookDetailsRating(float rating) {
        lblLibraryRatingAvg.setText(String.format("Average rating: %.1f", rating));
    }

    private void updateBookDetailsReviews(ArrayList<Review> reviews) {
        String r = "";
        for (int i = 0; i < reviews.size(); i++) {
            Review each = reviews.get(i);
            r += String.format("%s\n--%s, %d/5\n", each.getReviewText(), each.getCustomerID(), each.getReviewScore());
            if (i < reviews.size() - 1)
                r += "\n";
        }
    }

    private void showBookDetails(String BookISBN, boolean isPurchased) {
        try {
            if (!isPurchased) {
                Book b = M.getByISBN(BookISBN);

                txtLibraryISBN.setText(b.getISBN());
                txtLibraryTitle.setText(b.getTitle());
                txtLibraryAuthor.setText(b.getAuthor());
                txtLibraryVersionID.setText(Integer.toString(b.getVersionID()));
                txtLibraryYear.setText(Integer.toString(b.getYear().getValue()));
                txtLibraryPublisher.setText(b.getPublisher());
                txtLibraryPrice.setText(String.format("%.02f", b.getPrice()));
                txtLibraryGenre.setText(b.getGenre().name());
                txtLibraryFormat.setText(b.getFormat().name());

                txtLibraryISBN.setCaretPosition(0);
                txtLibraryTitle.setCaretPosition(0);
                txtLibraryAuthor.setCaretPosition(0);
                txtLibraryPublisher.setCaretPosition(0);

                updateBookDetailsRating(b.getRating());
            } else {
                CustomerLibrary customerLib = M.getCustomerLibrary(customer.getID(), BookISBN);
                LocalDate expiredDate = customerLib.getExpireDate();
                if (expiredDate.compareTo(LocalDate.now()) < 0) {
                    lblPurchaseState.setText("Expired");
                } else {
                    lblPurchaseState.setText(String.format("Available until %s", expiredDate.toString()));
                }
                Book b = customerLib.getBook();

                txtLibraryISBN.setText(b.getISBN());
                txtLibraryTitle.setText(b.getTitle());
                txtLibraryAuthor.setText(b.getAuthor());
                txtLibraryVersionID.setText(Integer.toString(b.getVersionID()));
                txtLibraryYear.setText(Integer.toString(b.getYear().getValue()));
                txtLibraryPublisher.setText(b.getPublisher());
                txtLibraryPrice.setText(String.format("%.02f", b.getPrice()));
                txtLibraryGenre.setText(b.getGenre().name());
                txtLibraryFormat.setText(b.getFormat().name());

                txtLibraryISBN.setCaretPosition(0);
                txtLibraryTitle.setCaretPosition(0);
                txtLibraryAuthor.setCaretPosition(0);
                txtLibraryPublisher.setCaretPosition(0);

                updateBookDetailsRating(b.getRating());
            }
            
        } catch (SQLException exc) {
            GUIHelpers.showErrorDialog("Unable to get selected book info", exc);
        }
    }

    private void showReceiptDetails() {
        // try {
            
        // } catch (SQLException exc) {
        //     GUIHelpers.showErrorDialog("Unable to get receipt", exc);
        // }
    }
}
