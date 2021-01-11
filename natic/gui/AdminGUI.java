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
import natic.account.Account;
import natic.account.Admin;
import natic.account.Staff;
import natic.book.Book;
import natic.branch.Branch;

public class AdminGUI extends JFrame {
    
    private static final long serialVersionUID = 1L;
    Mediator M = Mediator.getInstance();
    Admin admin;

    private JTextField txtAccountName;
	private JTextField txtAccountEmail;
	private JTextField txtAccountPhone;
	private JPasswordField pwtxtOld;
	private JPasswordField pwtxtNew;
	private JPasswordField pwtxtConfirm;
    
    private JTable tblBranches;
    private JTextField txtBranchSearch;
    private JTextField txtBranchID;
    private JTextField txtBranchName;
    private JTextField txtBranchAddress;
    
    private JTable tblStaff;
    private JTextField txtStaffSearch;
    private JTextField txtStaffID;
    private JTextField txtStaffName;
    private JTextField txtStaffEmail;
    private JTextField txtStaffPhone;
    
    private JTable tblLibrary;
    private JTextField txtLibrarySearch;
    private JTextField txtLibraryISBN;
    private JTextField txtLibraryTitle;
    private JTextField txtLibraryAuthor;
    private JTextField txtLibraryPublisher;
	
	// MigLayout "sizegroup main" constraint: https://stackoverflow.com/a/60187262
	
	public AdminGUI(String ID) {
        try {
            admin = M.getAdminByID(ID);
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
        setTitle("NATiC: Admin");
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
		tabbedPane.addTab(GUIHelpers.htmlTabName("admin"), null, Account, null);
		Account.setLayout(new MigLayout("", "[grow,fill]", "[grow][36.00,fill]"));
		
		JPanel AccountInfoEdit = new JPanel();
		Account.add(AccountInfoEdit, "cell 0 0,grow");
        AccountInfoEdit.setLayout(new MigLayout("", "[grow,fill][grow,fill][fill]", "[][][][][][40px,center][][][][][]"));
		
        // Separator between About and Password section
        JSeparator sepAccount = new JSeparator();
        sepAccount.setForeground(new Color(192, 192, 192));
        AccountInfoEdit.add(sepAccount, "cell 0 5 3 1");
        
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
        AccountInfoEdit.add(btnAccountAboutSave, "cell 1 4 2 1");
        
        
        
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
        
		AccountInfoEdit.add(lblHeaderPassword, "cell 0 6 3 1");
		AccountInfoEdit.add(lblPasswordOld, "cell 0 7,alignx trailing");
		AccountInfoEdit.add(lblPasswordNew, "cell 0 8,alignx trailing");
		AccountInfoEdit.add(lblPasswordConfirm, "cell 0 9,alignx trailing");
		AccountInfoEdit.add(pwtxtOld, "cell 1 7,growx");
		AccountInfoEdit.add(pwtxtNew, "cell 1 8,growx");
		AccountInfoEdit.add(pwtxtConfirm, "cell 1 9,growx");
		AccountInfoEdit.add(btnPasswordOldReveal, "cell 2 7");
        AccountInfoEdit.add(btnPasswordNewReveal, "cell 2 8");
        AccountInfoEdit.add(btnPasswordConfirmReveal, "cell 2 9");
        AccountInfoEdit.add(btnPasswordSave, "cell 1 10 2 1");
		
        
        
		/**
		 * Branches
		 */
		
		JPanel Branches = new JPanel();
		tabbedPane.addTab(GUIHelpers.htmlTabName("Branches"), null, Branches, null);
		Branches.setLayout(new MigLayout("", "[grow,sizegroup main,fill][grow,sizegroup main,fill]", "[36.00,fill][grow]"));
		
		txtBranchSearch = new JTextField();
		txtBranchSearch.setColumns(20);
        GUIHelpers.addPlaceholderText(txtBranchSearch, "Search by name or address");
		
		JPanel BranchActions = new JPanel();
		
		JButton btnBranchAdd = new JButton("Add");
        btnBranchAdd.setPreferredSize(new Dimension(100, 25));
        BranchActions.add(btnBranchAdd);
        
        JButton btnBranchRemove = new JButton("Remove");
        btnBranchRemove.setPreferredSize(new Dimension(100, 25));
        BranchActions.add(btnBranchRemove);
		
		tblBranches = new JTable();
        
        JScrollPane scrollBranches = new JScrollPane();
        JScrollPane scrollBranchDetails = new JScrollPane();
        
        scrollBranches.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBranches.setViewportView(tblBranches);
        
        Branches.add(txtBranchSearch, "cell 0 0,growx,aligny top");
        Branches.add(BranchActions, "cell 1 0,grow");
        Branches.add(scrollBranches, "cell 0 1,grow");
        Branches.add(scrollBranchDetails, "cell 1 1,grow");
        
        
        /**
         * Branches -> Branch Details
         */
        
        JPanel BranchDetails = new JPanel();
        scrollBranchDetails.setViewportView(BranchDetails);
        BranchDetails.setLayout(new MigLayout("", "[80.00px,fill][grow,fill]", "[][][][grow][36px,fill]"));
        
        JLabel lblBranchID = new JLabel("ID");
        JLabel lblBranchName = new JLabel("Name");
        JLabel lblBranchAddress = new JLabel("Email");
        
        txtBranchID = new JTextField();
        txtBranchName = new JTextField();
        txtBranchAddress = new JTextField();
        JButton btnBranchSave = new JButton("Save changes");
        
        txtBranchID.setEditable(false);
        txtBranchID.setEnabled(false);
        
        txtBranchID.setColumns(10);
        txtBranchName.setColumns(10);
        txtBranchAddress.setColumns(10);
        
        BranchDetails.add(lblBranchID, "cell 0 0");
        BranchDetails.add(lblBranchName, "cell 0 1");
        BranchDetails.add(lblBranchAddress, "cell 0 2");
        
        BranchDetails.add(txtBranchID, "cell 1 0,growx");
        BranchDetails.add(txtBranchName, "cell 1 1,growx");
        BranchDetails.add(txtBranchAddress, "cell 1 2,growx");
        
        BranchDetails.add(btnBranchSave, "cell 0 4 2 1");
        
		
		
        /**
         * Staff
         */
        
        JPanel Staff = new JPanel();
        tabbedPane.addTab(GUIHelpers.htmlTabName("Staff"), null, Staff, null);
        Staff.setLayout(new MigLayout("", "[grow,sizegroup main,fill][grow,sizegroup main,fill]", "[36.00,fill][grow]"));
        
        txtStaffSearch = new JTextField();
        txtStaffSearch.setColumns(20);
        GUIHelpers.addPlaceholderText(txtStaffSearch, "Search by name or email");
        
        JPanel StaffActions = new JPanel();
        
        JButton btnStaffAdd = new JButton("Add");
        btnStaffAdd.setPreferredSize(new Dimension(100, 25));
        StaffActions.add(btnStaffAdd);
        
        JButton btnStaffRemove = new JButton("Remove");
        btnStaffRemove.setPreferredSize(new Dimension(100, 25));
        StaffActions.add(btnStaffRemove);
        
        tblStaff = new JTable();
        
        JScrollPane scrollStaff = new JScrollPane();
        JScrollPane scrollStaffDetails = new JScrollPane();
        
        scrollStaff.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollStaff.setViewportView(tblStaff);
        
        Staff.add(txtStaffSearch, "cell 0 0,growx,aligny top");
        Staff.add(StaffActions, "cell 1 0,grow");
        Staff.add(scrollStaff, "cell 0 1,grow");
        Staff.add(scrollStaffDetails, "cell 1 1,grow");
        
        
        /**
         * Staff -> Staff Details
         */
        
        JPanel StaffDetails = new JPanel();
        scrollStaffDetails.setViewportView(StaffDetails);
        StaffDetails.setLayout(new MigLayout("", "[80.00px,fill][grow,fill]", "[][][][][][grow][36px,fill]"));
        
        JLabel lblStaffID = new JLabel("ID");
        JLabel lblStaffName = new JLabel("Name");
        JLabel lblStaffEmail = new JLabel("Email");
        JLabel lblStaffPhone = new JLabel("Phone");
        JLabel lblStaffBranch = new JLabel("Branch");
        
        txtStaffID = new JTextField();
        txtStaffName = new JTextField();
        txtStaffEmail = new JTextField();
        txtStaffPhone = new JTextField();
        JComboBox cboxStaffBranch = new JComboBox();
        JButton btnStaffSave = new JButton("Save changes");
        
        txtStaffID.setEditable(false);
        txtStaffID.setEnabled(false);
        // txtStaffEmail.setEditable(false);
        // txtStaffEmail.setEnabled(false);
        // txtStaffPhone.setEditable(false);
        // txtStaffPhone.setEnabled(false);
        
        txtStaffID.setColumns(10);
        txtStaffName.setColumns(10);
        txtStaffEmail.setColumns(10);
        txtStaffPhone.setColumns(10);
        
        StaffDetails.add(lblStaffID, "cell 0 0");
        StaffDetails.add(lblStaffName, "cell 0 1");
        StaffDetails.add(lblStaffEmail, "cell 0 2");
        StaffDetails.add(lblStaffPhone, "cell 0 3");
        StaffDetails.add(lblStaffBranch, "cell 0 4");
        
        StaffDetails.add(txtStaffID, "cell 1 0,growx");
        StaffDetails.add(txtStaffName, "cell 1 1,growx");
        StaffDetails.add(txtStaffEmail, "cell 1 2,growx");
        StaffDetails.add(txtStaffPhone, "cell 1 3,growx");
        StaffDetails.add(cboxStaffBranch, "cell 1 4,growx");
        
        StaffDetails.add(btnStaffSave, "cell 0 6 2 1");
        
        
        
        /**
         * Library
         */
        
        JPanel Library = new JPanel();
        tabbedPane.addTab(GUIHelpers.htmlTabName("Library"), null, Library, null);
        Library.setLayout(new MigLayout("", "[grow,sizegroup main,fill][grow,sizegroup main,fill]", "[36.00,fill][grow]"));
        
        txtLibrarySearch = new JTextField();
        txtLibrarySearch.setColumns(20);
        GUIHelpers.addPlaceholderText(txtLibrarySearch, "Search by title or ISBN");
        
        JPanel BookActions = new JPanel();
        
        JButton btnLibraryAdd = new JButton("Add");
        btnLibraryAdd.setPreferredSize(new Dimension(100, 25));
        BookActions.add(btnLibraryAdd);
        
        tblLibrary = new JTable();
        
        JScrollPane scrollLibrary = new JScrollPane();
        JScrollPane scrollBookDetails = new JScrollPane();
        
        scrollLibrary.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLibrary.setViewportView(tblLibrary);
        
        Library.add(txtLibrarySearch, "cell 0 0,growx,aligny top");
        Library.add(BookActions, "cell 1 0,grow");
        Library.add(scrollLibrary, "cell 0 1,grow");
        Library.add(scrollBookDetails, "cell 1 1,grow");
        
        
        /**
         * Library -> Book Details
         */
        
        JPanel BookDetails = new JPanel();
        scrollBookDetails.setViewportView(BookDetails);
        BookDetails.setLayout(new MigLayout("", "[80.00px,fill][grow,fill]", "[][][][][][40px,center][][][][][][][][grow][36px,fill]"));
        
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
        
        txtLibraryISBN = new JTextField();
        txtLibraryTitle = new JTextField();
        txtLibraryAuthor = new JTextField();
        JSeparator sepBookDetails = new JSeparator();
        JSpinner spinnerLibraryVersionID = new JSpinner();
        JSpinner spinnerLibraryYear = new JSpinner();
        txtLibraryPublisher = new JTextField();
        JComboBox cboxLibraryGenre = new JComboBox();
        JComboBox cboxLibraryFormat = new JComboBox();
        JSpinner spinnerLibraryPrice = new JSpinner();
        JButton btnLibrarySave = new JButton("Save changes");
        
        txtLibraryISBN.setEditable(false);
        txtLibraryISBN.setEnabled(false);
        txtLibraryISBN.setColumns(10);
        sepBookDetails.setForeground(new Color(192, 192, 192));
        txtLibraryTitle.setColumns(10);
        txtLibraryAuthor.setColumns(10);
        txtLibraryPublisher.setColumns(10);

        spinnerLibraryVersionID.setModel(new SpinnerNumberModel(Byte.valueOf((byte) 1), Byte.valueOf((byte) 1), Byte.valueOf((byte) 127), Byte.valueOf((byte) 1)));
        spinnerLibraryYear.setModel(new SpinnerNumberModel(Short.valueOf((short) 2021), Short.valueOf((short) 1900), Short.valueOf((short) 2100), Short.valueOf((short) 1)));
        spinnerLibraryPrice.setModel(new SpinnerNumberModel(Float.valueOf((float) 0), Float.valueOf((float) 0), null, Float.valueOf((float) 1)));
        
        // https://stackoverflow.com/a/12453923
        JSpinner.NumberEditor ne_spinnerLibraryYear = new JSpinner.NumberEditor(spinnerLibraryYear, "####");
        JSpinner.NumberEditor ne_spinnerLibraryVersionID = new JSpinner.NumberEditor(spinnerLibraryVersionID, "####");
        JSpinner.NumberEditor ne_spinnerLibraryPrice = new JSpinner.NumberEditor(spinnerLibraryPrice, "0.00");
        
        // https://stackoverflow.com/a/22702058
        ne_spinnerLibraryYear.getTextField().setHorizontalAlignment(JTextField.LEFT);
        ne_spinnerLibraryVersionID.getTextField().setHorizontalAlignment(JTextField.LEFT);
        ne_spinnerLibraryPrice.getTextField().setHorizontalAlignment(JTextField.LEFT);
        
        spinnerLibraryYear.setEditor(ne_spinnerLibraryYear);
        spinnerLibraryVersionID.setEditor(ne_spinnerLibraryVersionID);
        spinnerLibraryPrice.setEditor(ne_spinnerLibraryPrice);
        
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
        BookDetails.add(spinnerLibraryVersionID, "cell 1 7");
        BookDetails.add(spinnerLibraryYear, "cell 1 8");
        BookDetails.add(txtLibraryPublisher, "cell 1 9,growx");
        BookDetails.add(cboxLibraryGenre, "cell 1 10,growx");
        BookDetails.add(cboxLibraryFormat, "cell 1 11,growx");
        BookDetails.add(spinnerLibraryPrice, "cell 1 12");
        
        BookDetails.add(btnLibrarySave, "cell 0 14 2 1");
        
        
        
        /**
		 * Cosmetics, etc.
		 */
        
        // Whiten (almost) everything
                                     setBackground(Color.WHITE);
        getContentPane()            .setBackground(Color.WHITE);
        tabbedPane                  .setBackground(Color.WHITE);
        Account                     .setBackground(Color.WHITE);
        AccountInfoEdit             .setBackground(Color.WHITE);
        Branches                    .setBackground(Color.WHITE);
        scrollBranches              .setBackground(Color.WHITE);
        scrollBranchDetails         .setBackground(Color.WHITE);
        Staff                       .setBackground(Color.WHITE);
        scrollStaff                 .setBackground(Color.WHITE);
        scrollStaffDetails          .setBackground(Color.WHITE);
        Library                     .setBackground(Color.WHITE);
        scrollLibrary               .setBackground(Color.WHITE);
        scrollBookDetails           .setBackground(Color.WHITE);
        
        // Uniform border: 1px #c0c0c0 + variable padding
        CompoundBorder CBorder0 = new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(0, 0, 0, 0));
        CompoundBorder CBorder4 = new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(4, 4, 4, 4));
        CompoundBorder CBorder8 = new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(8, 8, 8, 8));
        
        AccountInfoEdit             .setBorder(CBorder8);
        BranchActions               .setBorder(CBorder0);
        StaffActions                .setBorder(CBorder0);
        BookActions                 .setBorder(CBorder0);
        scrollBranches              .setBorder(CBorder0);
        scrollBranchDetails         .setBorder(CBorder0);
        scrollStaff                 .setBorder(CBorder0);
        scrollStaffDetails          .setBorder(CBorder0);
        scrollLibrary               .setBorder(CBorder0);
        scrollBookDetails           .setBorder(CBorder0);
        txtBranchSearch             .setBorder(CBorder4);
        txtStaffSearch              .setBorder(CBorder4);
        txtLibrarySearch            .setBorder(CBorder4);
		
		// Transparent scrollpane background
        scrollBranches        .getViewport().setOpaque(false);
        scrollBranchDetails   .getViewport().setOpaque(false);
        scrollStaff           .getViewport().setOpaque(false);
        scrollStaffDetails    .getViewport().setOpaque(false);
		scrollLibrary         .getViewport().setOpaque(false);
        scrollBookDetails     .getViewport().setOpaque(false);

        // Table: minimum height = viewport height
        tblBranches .setFillsViewportHeight(true);
        tblLibrary  .setFillsViewportHeight(true);
        tblStaff    .setFillsViewportHeight(true);

        // Table: single selection only
        tblBranches .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblLibrary  .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblStaff    .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        /**
         * Events (Main)
         */

        // Tab: change on index change
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                final HashMap<Integer, String> tabMap = new HashMap<>() {{
                    put(0, "Account");
                    put(1, "Branches");
                    put(2, "Staff");
                    put(3, "Library");
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
                if (name.isBlank()) name = null;
                if (email.isBlank()) email = null;
                if (phone.isBlank()) phone = null;
                admin.setName(name);
                admin.setEmail(email);
                admin.setPhone(phone);
                try {
                    M.editAccount(admin);
                    populateAccountTab();
                }
                catch (Exception exc) {
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
            }
        });
        


        /**
         * Events -> Branches
         */

        txtBranchSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("txt: BranchSearch");
            }
        });
        
        btnBranchAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: BranchAdd");
            }
        });
        
        btnBranchRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: BranchRemove");
            }
        });
        
        btnBranchSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: BranchSave");
            }
        });

        tblBranches.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tblBranches.getSelectedRow();
                Log.l.info("Selected row index: " + selectedRow);
                
                if (selectedRow == -1)
                    return;
                
                String BranchID = (String) tblBranches.getModel().getValueAt(selectedRow, 0);
                String BranchName = (String) tblBranches.getModel().getValueAt(selectedRow, 1);
                Log.l.info("Selected row: " + BranchID + " " + BranchName);

                showBranchDetails();
            }
        });
        


        /**
         * Events -> Staff
         */
        
        txtStaffSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("txt: StaffSearch");
            }
        });
        
        btnStaffAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: StaffAdd");
            }
        });
        
        btnStaffRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: StaffRemove");
            }
        });
        
        btnStaffSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: StaffSave");
            }
        });
        


        /**
         * Events -> Library
         */
        
        txtLibrarySearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("txt: LibrarySearch");
            }
        });
        
        btnLibraryAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: LibraryAdd");
            }
        });
        
        btnLibrarySave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.l.info("btn: LibrarySave");
            }
        });

        Log.l.info("Admin GUI init'd");
    }
    
    private void populateTab(int tabIndex) {
        switch (tabIndex) {
            case 0: populateAccountTab(); break;
            case 1: populateBranchesTab(); break;
            case 2: populateStaffTab(); break;
            case 3: populateLibraryTab(); break;
        }
    }
    
    private void populateAccountTab() {
        txtAccountName.setText(admin.getName());
        txtAccountEmail.setText(admin.getEmail());
        txtAccountPhone.setText(admin.getPhone());
        Log.l.info("Account tab populated");
    }
    
    private void populateBranchesTab() {
        String[] tableHeaders = {GUIHelpers.htmlBoldText("ID"), GUIHelpers.htmlBoldText("Name")};

        ArrayList<ArrayList<Object>> tableData = new ArrayList<>();

        try {
            ArrayList<Branch> branches = M.getAllBranch();
            for (var branch: branches) {
                ArrayList<Object> record = new ArrayList<>();
                record.add(branch.getID());
                record.add(branch.getName());
                tableData.add(record);
        }
        } catch (SQLException exec) {
            exec.printStackTrace();
        }
        // extract and push each records
        

        CustomTableModel tbmBranches = new CustomTableModel(tableHeaders, tableData);
        tblBranches.setRowHeight(24);
        tblBranches.setModel(tbmBranches);
        tblBranches.setAutoCreateRowSorter(true);
        Log.l.info("Branches tab populated");
    }

    private void populateStaffTab() {
        String[] tableHeaders = {GUIHelpers.htmlBoldText("ID"), GUIHelpers.htmlBoldText("Name"), GUIHelpers.htmlBoldText("Email")};

        ArrayList<ArrayList<Object>> tableData = new ArrayList<>();

        try {
            ArrayList<Staff> staffList = M.getAllStaff();
            for (var staff: staffList) {
                ArrayList<Object> record = new ArrayList<>();
                record.add(staff.getID());
                record.add(staff.getName());
                record.add(staff.getEmail());
                tableData.add(record);
        }
        } catch (SQLException exec) {
            exec.printStackTrace();
        }
        // extract and push each records
        

        CustomTableModel tbmStaff = new CustomTableModel(tableHeaders, tableData);
        tblStaff.setRowHeight(24);
        tblStaff.setModel(tbmStaff);
        tblStaff.setAutoCreateRowSorter(true);
        Log.l.info("Staff tab populated");
    }
    
    private void populateLibraryTab() {
        String[] tableHeaders = {GUIHelpers.htmlBoldText("Title"), GUIHelpers.htmlBoldText("Author"), GUIHelpers.htmlBoldText("Year")};

        ArrayList<ArrayList<Object>> tableData = new ArrayList<>();

        try {
            ArrayList<Book> books = M.getAllBooks();
            for (var book: books) {
                ArrayList<Object> record = new ArrayList<>();
                record.add(book.getTitle());
                record.add(book.getAuthor());
                record.add(book.getYear());
                tableData.add(record);
            }
        } catch (SQLException exec) {
            exec.printStackTrace();
        }
        // extract and push each records

        CustomTableModel tbmLib = new CustomTableModel(tableHeaders, tableData);
        tblLibrary.setRowHeight(24);
        tblLibrary.setModel(tbmLib);
        tblLibrary.setAutoCreateRowSorter(true);
        Log.l.info("Library tab populated");
    }

    private void showBranchDetails() {
    }
}
