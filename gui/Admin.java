package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Admin extends JFrame {
	private JTextField SearchField;
	private JTextField SearchBox;
	private JTextField SearchReceipts;
	private JTextField ReceiptInfo;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Admin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[436px]", "[263px]"));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, "cell 0 0,grow");
		
		/**
		 * Homepage*/
		JPanel Homepage = new JPanel();
		tabbedPane.addTab("Home", null, Homepage, null);
		Homepage.setLayout(new MigLayout("", "[][grow]", "[][grow]"));
		
		SearchBox = new JTextField();
		SearchBox.setBackground(Color.LIGHT_GRAY);
		SearchBox.setText("Search for books");
		Homepage.add(SearchBox, "cell 0 0,alignx center,aligny top");
		SearchBox.setColumns(20);
		
		JList ResultList = new JList();
		Homepage.add(ResultList, "flowx,cell 0 1,grow");
		
		JList BooksList = new JList();
		Homepage.add(BooksList, "flowx,cell 1 0 1 2,grow");
		
		JScrollBar ResListScroll = new JScrollBar();
		Homepage.add(ResListScroll, "cell 0 1,growy");
		
		JScrollBar BookListScroll = new JScrollBar();
		Homepage.add(BookListScroll, "cell 1 0,growy");
		
		/**
		 * Account*/
		JPanel Account = new JPanel();
		tabbedPane.addTab("Account", null, Account, null);
		Account.setLayout(new MigLayout("", "[125][grow]", "[][grow]"));
		
		JTextPane DetailsPane = new JTextPane();
		Account.add(DetailsPane, "cell 1 0 1 7,grow");
		
		JButton btnLogout = new JButton("Log Out");
		Account.add(btnLogout, "cell 0 6,alignx center");
		
		/**
		 * Library*/
		JPanel Library = new JPanel();
		tabbedPane.addTab("Library", null, Library, null);
		Library.setLayout(new MigLayout("", "[]", "[]"));

		/**
		 * Orders*/
		JPanel Orders = new JPanel();
		tabbedPane.addTab("Orders", null, Orders, null);
		Orders.setLayout(new MigLayout("", "[grow][150]", "[][grow][][]"));
		
		SearchReceipts = new JTextField();
		SearchReceipts.setBackground(Color.LIGHT_GRAY);
		SearchReceipts.setText("ISBN, Book ID or Book Name");
		Orders.add(SearchReceipts, "cell 0 0,growx");
		SearchReceipts.setColumns(10);
		
		JList ReceiptList = new JList();
		Orders.add(ReceiptList, "flowx,cell 1 0 1 2,grow");
		
		ReceiptInfo = new JTextField();
		Orders.add(ReceiptInfo, "flowx,cell 0 1 1 3,alignx center,grow");
		ReceiptInfo.setColumns(10);
		
		JButton btnAddReceipt = new JButton("Add Receipt");
		Orders.add(btnAddReceipt, "grow,cell 1 2,alignx center,aligny bottom");
		
		JButton btnRemoveReceipt = new JButton("Remove Receipt");
		Orders.add(btnRemoveReceipt, "grow,cell 1 3,alignx center,aligny bottom");
		
		JScrollBar RecInfoScroll = new JScrollBar();
		Orders.add(RecInfoScroll, "cell 0 1,growy");
		
		JScrollBar RecListScroll = new JScrollBar();
		Orders.add(RecListScroll, "cell 1 0,growy");
		
		/**
		 * Management Panel*/
		JPanel Management = new JPanel();
		tabbedPane.addTab("Management", null, Management, null);
		Management.setLayout(new MigLayout("", "[136px,grow][300px,grow]", "[75px,grow][75px][75px][75px]"));
		
		JList ListOfAction = new JList();
		Management.add(ListOfAction, "cell 1 0 1 4,grow");
		
		JButton btnAccountManage = new JButton("Account");
		Management.add(btnAccountManage, "cell 0 1,alignx center");
		
		JPopupMenu AccountPopUpMenu = new JPopupMenu();
		addPopup(btnAccountManage, AccountPopUpMenu);
		
		JButton btnDeleteUser = new JButton("Delete");
		AccountPopUpMenu.add(btnDeleteUser);
		
		JButton btnBranchManage = new JButton("Branches");
		Management.add(btnBranchManage, "cell 0 2,alignx center");
		
		JPopupMenu BranchesPopUpMenu = new JPopupMenu();
		addPopup(btnBranchManage, BranchesPopUpMenu);
		
		JButton btnAddBranch = new JButton("Add");
		BranchesPopUpMenu.add(btnAddBranch);
		
		JButton btnDeleteBranch = new JButton("Delete");
		BranchesPopUpMenu.add(btnDeleteBranch);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
