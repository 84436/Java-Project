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
	private JTextField SearchBox;

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
		 * Branch/Staff*/
		JPanel BranchStaff = new JPanel();
		tabbedPane.addTab("Branch/Staff", null, BranchStaff, null);
		BranchStaff.setLayout(new MigLayout("", "[75px,grow][125px,grow][250px]", "[][][grow]"));
		
		branchSearch = new JTextField();
		branchSearch.setText("Branch name");
		BranchStaff.add(branchSearch, "flowx,cell 0 0 2 1,growx");
		branchSearch.setColumns(10);
		
		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Tahoma", Font.BOLD, 12));
		BranchStaff.add(lblID, "cell 0 1");
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
		BranchStaff.add(lblName, "cell 1 1");
		
		JList idList = new JList();
		BranchStaff.add(idList, "cell 0 2,grow");
		
		JList nameList = new JList();
		BranchStaff.add(nameList, "cell 1 2,grow");
		
		JButton button = new JButton("New button");
		BranchStaff.add(button, "cell 1 0");
	}
}
