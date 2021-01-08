package natic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;

public class Customer extends JFrame {
	private JTextField SearchBox;

	/**
	 * Create the frame.
	 */
	public Customer() {
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
		
		/**
		 * Search*/
		JPanel Search = new JPanel();
		tabbedPane.addTab("Search", null, Search, null);
		Search.setLayout(new MigLayout("", "[100px][75px,grow][50px,grow][225px]", "[][][grow]"));
		
		SearchBox = new JTextField();
		SearchBox.setBackground(Color.LIGHT_GRAY);
		SearchBox.setText("Search for books");
		Search.add(SearchBox, "cell 0 0 3 1,growx,aligny top");
		SearchBox.setColumns(20);
		
		JLabel lblTitleSearch = new JLabel("Title");
		lblTitleSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		Search.add(lblTitleSearch, "cell 0 1");
		
		JLabel lblAuthorSearch = new JLabel("Author");
		lblAuthorSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		Search.add(lblAuthorSearch, "cell 1 1");
		
		JLabel lblYearSearch = new JLabel("Year");
		lblYearSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		Search.add(lblYearSearch, "cell 2 1,alignx center");
		
		JList TitleListSearch = new JList();
		Search.add(TitleListSearch, "flowx,cell 0 2,grow");
		
		JList BooksListSearch = new JList();
		Search.add(BooksListSearch, "flowx,cell 3 0 1 3,grow");
		
		JList AuthorListSearch = new JList();
		Search.add(AuthorListSearch, "cell 1 2,grow");
		
		JList YearListSearch = new JList();
		Search.add(YearListSearch, "cell 2 2,grow");
		
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
