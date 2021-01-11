package natic.gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import net.miginfocom.swing.MigLayout;

import java.util.*;
import natic.*;
import natic.account.Staff;
import natic.book.Book;
import natic.book.BranchStockList;
import natic.review.Review;

public class StaffGUI extends JFrame {
    
    private static final long serialVersionUID = 1L;
    Mediator M = Mediator.getInstance();
    Staff staff;
    
    private JTextField txtAccountName;
    private JTextField txtAccountBranch;
    private JTextField txtAccountEmail;
    private JTextField txtAccountPhone;
    private JPasswordField pwtxtOld;
    private JPasswordField pwtxtNew;
    private JPasswordField pwtxtConfirm;
    
    private JTextField txtCounterISBN;
    private JTextField txtCounterEmail;
    private JTable tblCounterBookList;
    private JTable tblCounterCustomerList;
    
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
    
    private JSpinner spinnerSetStock;
    private JButton btnSetStock;
    private JButton btnAddBook;
    private JButton btnRemoveBook;
    
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
    
    public StaffGUI(String ID) {
        try {
            staff = M.getStaffByID(ID);
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
        setTitle("NATiC: Staff");
        GUIHelpers.setGlobalFont("SansSerif", Font.PLAIN, 14);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1024, 768);
        GUIHelpers.centerWindow(this);
        getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
        
        // Base tabbed layout
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        getContentPane().add(tabbedPane, "cell 0 0,grow");
        
        
        
        /**
        * Account Panel
        */
        
        JPanel Account = new JPanel();
        tabbedPane.addTab(GUIHelpers.htmlTabName("Account"), null, Account, null);
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
        JLabel lblAccountBranch = new JLabel("Branch");
        txtAccountName = new JTextField();
        txtAccountEmail = new JTextField();
        txtAccountPhone = new JTextField();
        txtAccountBranch = new JTextField();
        
        txtAccountBranch.setText("{staff branch name}");
        txtAccountBranch.setEnabled(false);
        txtAccountBranch.setEditable(false);
        txtAccountBranch.setFocusable(false);
        
        txtAccountName.setColumns(10);
        txtAccountEmail.setColumns(10);
        txtAccountPhone.setColumns(10);
        txtAccountBranch.setColumns(10);
        
        JButton btnAccountAboutSave = new JButton("Save changes");
        
        AccountInfoEdit.add(lblHeaderAccountAbout, "cell 0 0 3 1,alignx trailing");
        AccountInfoEdit.add(lblAccountName, "cell 0 1");
        AccountInfoEdit.add(lblAccountEmail, "cell 0 2,alignx trailing");
        AccountInfoEdit.add(lblAccountPhone, "cell 0 3,alignx trailing");
        AccountInfoEdit.add(lblAccountBranch, "cell 0 4,alignx trailing");
        AccountInfoEdit.add(txtAccountName, "cell 1 1 2 1,growx");
        AccountInfoEdit.add(txtAccountEmail, "cell 1 2 2 1,growx");
        AccountInfoEdit.add(txtAccountPhone, "cell 1 3 2 1,growx");
        AccountInfoEdit.add(txtAccountBranch, "cell 1 4 2 1,growx");
        AccountInfoEdit.add(btnAccountAboutSave, "cell 1 5 2 1");
        
        
        
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
        * Counter
        */
        JPanel Counter = new JPanel();
        tabbedPane.addTab(GUIHelpers.htmlTabName("Counter"), null, Counter, null);
        Counter.setLayout(new MigLayout("", "[grow,sizegroup main,fill][grow,sizegroup main,fill]", "[36.00,fill][grow][36px,fill][36.00,fill]"));
        
        txtCounterISBN = new JTextField();
        txtCounterEmail = new JTextField();
        GUIHelpers.addPlaceholderText(txtCounterISBN, "Enter ISBN");
        GUIHelpers.addPlaceholderText(txtCounterEmail, "Enter customer email");
        
        txtCounterISBN.setColumns(10);
        txtCounterEmail.setColumns(10);
        
        tblCounterBookList = new JTable();
        tblCounterCustomerList = new JTable();
        
        JScrollPane scrollCounterBookList = new JScrollPane();
        JScrollPane scrollCounterCustomerList = new JScrollPane();
        
        scrollCounterBookList.setViewportView(tblCounterBookList);
        scrollCounterCustomerList.setViewportView(tblCounterCustomerList);
        
        JButton btnCounterCreate = new JButton("Create");
        JButton btnCounterDiscard = new JButton("Discard");
        JButton btnCreateCustomer = new JButton("Create customer");
        
        Counter.add(txtCounterISBN, "cell 0 0,growx");
        Counter.add(txtCounterEmail, "cell 1 0,growx");
        Counter.add(scrollCounterBookList, "cell 0 1 1 3,grow");
        Counter.add(scrollCounterCustomerList, "cell 1 1,grow");
        
        Counter.add(btnCreateCustomer, "cell 1 2");
        Counter.add(btnCounterCreate, "cell 1 3,growx");
        Counter.add(btnCounterDiscard, "flowx,cell 1 3,growx");
        
        
        
        /**
        * Library
        */
        
        JPanel Library = new JPanel();
        tabbedPane.addTab(GUIHelpers.htmlTabName("Library"), null, Library, null);
        Library.setLayout(new MigLayout("", "[grow,sizegroup main,fill][grow,sizegroup main,fill]", "[36.00,fill][][grow]"));
        
        txtLibrarySearch = new JTextField();
        txtLibrarySearch.setColumns(20);
        
        GUIHelpers.addPlaceholderText(txtLibrarySearch, "Search by title or ISBN");
        
        JPanel BookActions = new JPanel();
        
        spinnerSetStock = new JSpinner();
        spinnerSetStock.setModel(new SpinnerNumberModel(0, 0, null, 1));
        spinnerSetStock.setPreferredSize(new Dimension(100, 25));
        
        btnSetStock = new JButton("Set stock");
        btnSetStock.setPreferredSize(new Dimension(100, 25));
        
        tblLibrary = new JTable();
        
        JTextPane txtBookDetails = new JTextPane();
        txtBookDetails.setEditable(false);
        
        JScrollPane scrollLibrary = new JScrollPane();
        JScrollPane scrollBookDetails = new JScrollPane();
        
        scrollLibrary.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLibrary.setViewportView(tblLibrary);
        scrollBookDetails.setViewportView(txtBookDetails);
        
        Library.add(txtLibrarySearch, "cell 0 0,growx,aligny top");
        Library.add(BookActions, "cell 1 0,grow");
        
        btnAddBook = new JButton("Add");
        btnAddBook.setPreferredSize(new Dimension(100, 25));
        
        btnRemoveBook = new JButton("Remove");
        btnRemoveBook.setPreferredSize(new Dimension(100, 25));
        
        BookActions.add(btnAddBook);
        BookActions.add(btnRemoveBook);
        BookActions.add(spinnerSetStock);
        BookActions.add(btnSetStock);
        
        JCheckBox checkBranchOnly = new JCheckBox("Show only books in current branch");
        checkBranchOnly.setSelected(true);
        Library.add(checkBranchOnly, "cell 0 1");
        
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
        BookDetails.add(btnLibraryShowReviews, "cell 0 16 2 1,grow");
        
        
        
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
        Counter                     .setBackground(Color.WHITE);
        scrollCounterBookList       .setBackground(Color.WHITE);
        scrollCounterCustomerList   .setBackground(Color.WHITE);
        Library                     .setBackground(Color.WHITE);
        scrollLibrary               .setBackground(Color.WHITE);
        scrollBookDetails           .setBackground(Color.WHITE);
        Orders                      .setBackground(Color.WHITE);
        scrollOrders                .setBackground(Color.WHITE);
        scrollOrderDetails          .setBackground(Color.WHITE);
        checkBranchOnly             .setBackground(Color.WHITE);
        
        // Uniform border: 1px #c0c0c0 + variable padding
        CompoundBorder CBorder0 = new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(0, 0, 0, 0));
        CompoundBorder CBorder4 = new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(4, 4, 4, 4));
        CompoundBorder CBorder8 = new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(8, 8, 8, 8));
        
        AccountInfoEdit             .setBorder(CBorder8);
        scrollCounterBookList       .setBorder(CBorder0);
        scrollCounterCustomerList   .setBorder(CBorder0);
        BookActions                 .setBorder(CBorder0);
        scrollLibrary               .setBorder(CBorder0);
        scrollBookDetails           .setBorder(CBorder0);
        scrollOrders                .setBorder(CBorder0);
        scrollOrderDetails          .setBorder(CBorder0);
        txtCounterEmail             .setBorder(CBorder4);
        txtLibrarySearch            .setBorder(CBorder4);
        txtCounterISBN              .setBorder(CBorder4);
        
        // Transparent scrollpane background
        scrollCounterBookList     .getViewport().setOpaque(false);
        scrollCounterCustomerList .getViewport().setOpaque(false);
        scrollLibrary             .getViewport().setOpaque(false);
        scrollBookDetails         .getViewport().setOpaque(false);
        scrollOrders              .getViewport().setOpaque(false);
        scrollOrderDetails        .getViewport().setOpaque(false);
        
        
        
        /**
        * Events (Main)
        */
        
        // Library -> that checkbox: fire default state
        toggleLibraryView(checkBranchOnly.isSelected());
        
        // Tab: change on index change
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                final HashMap<Integer, String> tabMap = new HashMap<>() {{
                    put(0, "Account");
                    put(1, "Counter");
                    put(2, "Library");
                    put(3, "Orders");
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
                if (name.isBlank())
                    name = null;
                if (email.isBlank())
                    email = null;
                if (phone.isBlank())
                    phone = null;
                staff.setName(name);
                staff.setEmail(email);
                staff.setPhone(phone);
                try {
                    M.editAccount(staff);
                    populateAccountTab();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        });
        
        btnPasswordSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: PasswordSave");
            }
        });
        
        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: LogOut");
                LoginGUI loginForm = new LoginGUI();
                loginForm.setVisible(true);
                loginForm.getRootPane().requestFocus(false);
                dispose();
                Log.l.info("StaffGUI disposed");
            }
        });
        
        
        
        /**
        * Events -> Counter
        */
        
        btnCounterCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: CounterCreate");
            }
        });
        
        btnCounterDiscard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: CounterDiscard");
            }
        });
        
        btnCreateCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: CreateCustomer");
            }
        });

        txtCounterISBN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("txt: CounterAddISBN");
            }
        });

        txtCounterEmail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("txt: CounterSearchCustomerEmail");
            }
        });
        
        
        
        /**
        * Events -> Library
        */
        
        btnSetStock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: SetStock");
            }
        });
        
        checkBranchOnly.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("check: BranchOnly: " + checkBranchOnly.isSelected());
                toggleLibraryView(checkBranchOnly.isSelected());
            }
        });
        
        btnAddBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: AddBook");
            }
        });
        
        btnRemoveBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: RemoveBook");
            }
        });

        txtLibrarySearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("txt: LibrarySearch");
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
        
        
        
        /**
        * Events -> Orders
        */

        Log.l.info("Staff GUI init'd");
    }
    
    private void toggleLibraryView(boolean branchOnly) {
        if (branchOnly) {
            spinnerSetStock    .setVisible(true);
            btnSetStock        .setVisible(true);
            btnAddBook         .setVisible(false);
            btnRemoveBook      .setVisible(false);
        }
        else {
            spinnerSetStock    .setVisible(false);
            btnSetStock        .setVisible(false);
            btnAddBook         .setVisible(true);
            btnRemoveBook      .setVisible(true);
        }
    }
    
    private void populateTab(int tabIndex) {
        switch (tabIndex) {
            case 0: populateAccountTab(); break;
            case 1: populateCounterTab(); break;
            case 2: populateLibraryTab(true); break;
            case 3: populateOrdersTab(); break;
        }
    }
    
    private void populateAccountTab() {
        txtAccountName.setText(staff.getName());
        txtAccountEmail.setText(staff.getEmail());
        txtAccountPhone.setText(staff.getPhone());
        txtAccountBranch.setText(staff.getBranchID());
        Log.l.info("Account tab populated");
    }
    
    private void populateCounterTab() {
        Log.l.info("Counter tab populated");
    }
    
    private void populateLibraryTab(boolean isFilter) {
        String[] tableHeaders = {GUIHelpers.htmlBoldText("Title"), GUIHelpers.htmlBoldText("Author"), GUIHelpers.htmlBoldText("Year")};

        ArrayList<ArrayList<Object>> tableData = new ArrayList<>();

        try {
            if (isFilter) {
                ArrayList<BranchStockList> booksBranch = M.getBranchStockList(staff.getBranchID());
                for (var bookBranch: booksBranch) {
                    ArrayList<Object> record = new ArrayList<>();
                    record.add(bookBranch.getBook().getTitle());
                    record.add(bookBranch.getBook().getAuthor());
                    record.add(bookBranch.getBook().getYear());
                    tableData.add(record);
                }
            }   
            else {
                ArrayList<Book> books = M.getAllBooks();
                for (var book: books) {
                    ArrayList<Object> record = new ArrayList<>();
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
        Log.l.info("Orders tab populated");
    }

    private void updateBookDetailsRating(float rating) {
        lblLibraryRatingAvg.setText(String.format("Average rating: %.1f", rating));
    }

    private void showBookDetails(String BookISBN) {
        try {
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
        } catch (SQLException exc) {
            GUIHelpers.showErrorDialog("Unable to get selected book info", exc);
        }
    }
}
