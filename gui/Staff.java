package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

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
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Staff extends JFrame {

	private JTextField SearchBox;
	private JTextField txtEnterIsbn;
	private JTextField txtEnterEmail;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Staff() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[436px]", "[263px]"));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, "cell 0 0,grow");
		
		/**
		 * Homepage*/

		/**
		 * Counter*/
		JPanel Counter = new JPanel();
		tabbedPane.addTab("Counter", null, Counter, null);
		Counter.setLayout(new MigLayout("", "[225.00,grow][grow]", "[][grow][]"));
		
		txtEnterIsbn = new JTextField();
		txtEnterIsbn.setBackground(Color.LIGHT_GRAY);
		txtEnterIsbn.setForeground(Color.BLACK);
		txtEnterIsbn.setText("Enter ISBN");
		txtEnterIsbn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Counter.add(txtEnterIsbn, "cell 0 0,growx");
		txtEnterIsbn.setColumns(10);
		
		txtEnterEmail = new JTextField();
		txtEnterEmail.setBackground(Color.LIGHT_GRAY);
		txtEnterEmail.setText("Enter Email");
		txtEnterEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Counter.add(txtEnterEmail, "cell 1 0,growx");
		txtEnterEmail.setColumns(10);
		
		JList BookInCartList = new JList();
		Counter.add(BookInCartList, "cell 0 1 1 2,grow");
		
		JList EmailList = new JList();
		Counter.add(EmailList, "cell 1 1,grow");
		
		JButton btnDiscard = new JButton("Discard");
		btnDiscard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		Counter.add(btnDiscard, "flowx,cell 1 2,growx");
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		Counter.add(btnCreate, "cell 1 2,growx");
		
		/**
		 * Library*/
		JPanel Library = new JPanel();
		tabbedPane.addTab("Library", null, Library, null);
		Library.setLayout(new MigLayout("", "[100px][75px,grow][50px,grow][225px]", "[][][grow]"));
		
		SearchBox = new JTextField();
		SearchBox.setBackground(Color.LIGHT_GRAY);
		SearchBox.setText("Search for books");
		Library.add(SearchBox, "cell 0 0 3 1,growx,aligny top");
		SearchBox.setColumns(20);
		
		JLabel lblTitleLib = new JLabel("Title");
		lblTitleLib.setFont(new Font("Tahoma", Font.BOLD, 12));
		Library.add(lblTitleLib, "cell 0 1");
		
		JLabel lblTitleAuthor = new JLabel("Author");
		lblTitleAuthor.setFont(new Font("Tahoma", Font.BOLD, 12));
		Library.add(lblTitleAuthor, "cell 1 1");
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setFont(new Font("Tahoma", Font.BOLD, 12));
		Library.add(lblYear, "cell 2 1,alignx center");
		
		JList TitleList = new JList();
		Library.add(TitleList, "flowx,cell 0 2,grow");
		
		JList BooksList = new JList();
		Library.add(BooksList, "flowx,cell 3 0 1 3,grow");
		
		JList AuthorList = new JList();
		Library.add(AuthorList, "cell 1 2,grow");
		
		JList YearList = new JList();
		Library.add(YearList, "cell 2 2,grow");

		/**
		 * Orders*/
		JPanel Orders = new JPanel();
		tabbedPane.addTab("Orders", null, Orders, null);
		Orders.setLayout(new MigLayout("", "[75,grow][125][60][140]", "[][grow][][]"));
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		Orders.add(lblDate, "cell 0 0");
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		Orders.add(lblTitle, "cell 1 0");
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
		Orders.add(lblTotal, "cell 2 0,alignx center");
		
		JLabel DateandTime = new JLabel("Display");
		DateandTime.setFont(new Font("Tahoma", Font.BOLD, 15));
		Orders.add(DateandTime, "cell 3 0");
		
		JList DateField = new JList();
		Orders.add(DateField, "cell 0 1 1 3,grow");
		
		JList tilteField = new JList();
		Orders.add(tilteField, "cell 1 1 1 3,grow");
		
		JList TotalField = new JList();
		Orders.add(TotalField, "cell 2 1 1 3,grow");
		
		JList ReceiptList = new JList();
		Orders.add(ReceiptList, "flowx,cell 3 1 1 2,grow");
		
		JLabel TotalCost = new JLabel("Total:");
		TotalCost.setFont(new Font("Tahoma", Font.BOLD, 15));
		Orders.add(TotalCost, "cell 3 3");
	}
}
