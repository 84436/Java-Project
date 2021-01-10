package natic.gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.*;

import java.awt.*;
import java.awt.event.*;

import net.miginfocom.swing.MigLayout;

public class AdminGUI extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
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
	
	public AdminGUI() {
	    
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
		tabbedPane.addTab(styledTabName("admin"), null, Account, null);
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
        AccountInfoEdit.add(btnAccountAboutSave, "cell 1 4 2 1");
        
        
        
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
		tabbedPane.addTab(styledTabName("Branches"), null, Branches, null);
		Branches.setLayout(new MigLayout("", "[grow,sizegroup main,fill][grow,sizegroup main,fill]", "[36.00,fill][grow]"));
		
		txtBranchSearch = new JTextField();
		txtBranchSearch.setColumns(20);
        addPlaceholderText(txtBranchSearch, "Search by name or address");
		
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
        tabbedPane.addTab(styledTabName("Staff"), null, Staff, null);
        Staff.setLayout(new MigLayout("", "[grow,sizegroup main,fill][grow,sizegroup main,fill]", "[36.00,fill][grow]"));
        
        txtStaffSearch = new JTextField();
        txtStaffSearch.setColumns(20);
        addPlaceholderText(txtStaffSearch, "Search by name or email");
        
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
        txtStaffEmail.setEditable(false);
        txtStaffEmail.setEnabled(false);
        txtStaffPhone.setEditable(false);
        txtStaffPhone.setEnabled(false);
        
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
        tabbedPane.addTab(styledTabName("Library"), null, Library, null);
        Library.setLayout(new MigLayout("", "[grow,sizegroup main,fill][grow,sizegroup main,fill]", "[36.00,fill][grow]"));
        
        txtLibrarySearch = new JTextField();
        txtLibrarySearch.setColumns(20);
        addPlaceholderText(txtLibrarySearch, "Search by title or ISBN");
        
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
		
		// Always select 2nd tab on startup
        tabbedPane.setSelectedIndex(1);
        
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
         * Events -> Branches
         */
        
        txtBranchSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("txt: BranchSearch");
            }
        });
        
        btnBranchAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: BranchAdd");
            }
        });
        
        btnBranchRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: BranchRemove");
            }
        });
        
        btnBranchSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: BranchSave");
            }
        });
        
        /**
         * Events -> Staff
         */
        
        txtStaffSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("txt: StaffSearch");
            }
        });
        
        btnStaffAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: StaffAdd");
            }
        });
        
        btnStaffRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: StaffRemove");
            }
        });
        
        btnStaffSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: StaffSave");
            }
        });
        
        /**
         * Events -> Library
         */
        
        txtLibrarySearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("txt: LibrarySearch");
            }
        });
        
        btnLibraryAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: LibraryAdd");
            }
        });
        
        btnLibrarySave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn: LibrarySave");
            }
        });
	}
}
