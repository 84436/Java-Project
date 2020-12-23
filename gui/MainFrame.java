package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
	private JTextField SearchField;
	private JTextField SearchBox;
	private JTextField SearchReceipts;
	private JTextField ReceiptInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
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
		Homepage.add(ResultList, "cell 0 1,grow");
		
		JList BooksList = new JList();
		Homepage.add(BooksList, "cell 1 0 1 2,grow");
		
		/**
		 * Account*/
		JPanel Account = new JPanel();
		tabbedPane.addTab("Account", null, Account, null);
		Account.setLayout(new MigLayout("", "[]", "[]"));
		
		/**
		 * Library*/
		JPanel Library = new JPanel();
		tabbedPane.addTab("Library", null, Library, null);

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
		Orders.add(ReceiptList, "cell 1 0 1 2,grow");
		
		ReceiptInfo = new JTextField();
		Orders.add(ReceiptInfo, "cell 0 1 1 3,alignx center,grow");
		ReceiptInfo.setColumns(10);
		
		JButton btnAddReceipt = new JButton("Add Receipt");
		Orders.add(btnAddReceipt, "grow,cell 1 2,alignx center,aligny bottom");
		
		JButton btnRemoveReceipt = new JButton("Remove Receipt");
		Orders.add(btnRemoveReceipt, "grow,cell 1 3,alignx center,aligny bottom");
	}

}
