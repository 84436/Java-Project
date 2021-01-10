package natic.gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.*;

import java.awt.*;
import java.awt.event.*;

import net.miginfocom.swing.MigLayout;

public class StaffGUI extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
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
	
	private JTextField txtLibrarySearch;
    private JTable tblLibrary;
    
    private JTable tblOrders;
    
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
	
	public StaffGUI() {
	    
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
		tabbedPane.addTab(styledTabName("{staff name}"), null, Account, null);
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
		 * Counter
		 */
		JPanel Counter = new JPanel();
		tabbedPane.addTab(styledTabName("Counter"), null, Counter, null);
		Counter.setLayout(new MigLayout("", "[grow,sizegroup main,fill][grow,sizegroup main,fill]", "[36.00,fill][grow][36.00,fill]"));
		
		txtCounterISBN = new JTextField();
		txtCounterEmail = new JTextField();
		addPlaceholderText(txtCounterISBN, "Enter ISBN");
        addPlaceholderText(txtCounterEmail, "Enter customer email");
		
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
        
        Counter.add(txtCounterISBN, "cell 0 0,growx");
        Counter.add(txtCounterEmail, "cell 1 0,growx");
        Counter.add(scrollCounterBookList, "cell 0 1 1 2,grow");
        Counter.add(scrollCounterCustomerList, "cell 1 1,grow");
        Counter.add(btnCounterCreate, "cell 1 2,growx");
        Counter.add(btnCounterDiscard, "flowx,cell 1 2,growx");
		
        
        
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
		
		JSpinner spinnerSetStock = new JSpinner();
		spinnerSetStock.setPreferredSize(new Dimension(100, 25));
		
		JButton btnSetStock = new JButton("Set stock");
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
        BookActions.add(spinnerSetStock);
        BookActions.add(btnSetStock);
        Library.add(scrollLibrary, "cell 0 1,grow");
        Library.add(scrollBookDetails, "cell 1 1,grow");
        
        
        
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
		
		// Always select 2nd tab on startup
        tabbedPane.setSelectedIndex(1);
	}
}
