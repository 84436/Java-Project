package natic.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import natic.Log;
import natic.Mediator;
import natic.review.Review;
import net.miginfocom.swing.MigLayout;

public class ReviewWriterGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Mediator M = Mediator.getInstance();

    private int reviewScore = -1;
    private String reviewText;

    public ReviewWriterGUI(String CustomerID, String BookISBN, String BookName) {

        // Native LAF
	    try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setResizable(false);
        setTitle("Leave a review for " + BookName);
        setBounds(0, 0, 600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GUIHelpers.centerWindow(this);
        GUIHelpers.setGlobalFont("SansSerif", Font.PLAIN, 14);

        CompoundBorder CBorder4 = new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(4, 4, 4, 4));

        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));
        
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[grow,fill][grow,fill][grow,fill][grow,fill][grow,fill]", "[36px,top][36px,fill][24px][36px,top][grow][36px,fill][36px,center]"));
        
        JLabel lblNewLabel = new JLabel(GUIHelpers.htmlHeaderSmall("Do you like this book?"));
        contentPane.add(lblNewLabel, "cell 0 0 5 1");
        
        JToggleButton btn1 = new JToggleButton("<html><body style=\"text-align: center;\">★☆☆☆☆<br>Thanks<br>I Hate It</body></html>");
        contentPane.add(btn1, "cell 0 1");
        
        JToggleButton btn2 = new JToggleButton("<html><body style=\"text-align: center;\">★★☆☆☆<br>Not good<br>:(</body></html>");
        contentPane.add(btn2, "cell 1 1");
        
        JToggleButton btn3 = new JToggleButton("<html><body style=\"text-align: center;\">★★★☆☆<br>It's<br>okay</body></html>");
        contentPane.add(btn3, "cell 2 1");
        
        JToggleButton btn4 = new JToggleButton("<html><body style=\"text-align: center;\">★★★★☆<br>It's<br>great</body></html>");
        contentPane.add(btn4, "cell 3 1");
        
        JToggleButton btn5 = new JToggleButton("<html><body style=\"text-align: center;\">★★★★★<br>This is<br>perfect!</body></html>");
        contentPane.add(btn5, "cell 4 1");
        
        ButtonGroup bGroup = new ButtonGroup();
        bGroup.add(btn1);
        bGroup.add(btn2);
        bGroup.add(btn3);
        bGroup.add(btn4);
        bGroup.add(btn5);
        
        btn1.setFocusPainted(false);
        btn2.setFocusPainted(false);
        btn3.setFocusPainted(false);
        btn4.setFocusPainted(false);
        btn5.setFocusPainted(false);
        
        JLabel lblNewLabel_1 = new JLabel(GUIHelpers.htmlHeaderSmall("Write something about it:"));
        contentPane.add(lblNewLabel_1, "cell 0 3 5 1");
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(scrollPane, "cell 0 4 5 1,grow");
        
        JTextArea editorPane = new JTextArea();
        scrollPane.setViewportView(editorPane);
        editorPane.setFont(new Font("Serif", Font.ITALIC, 18));
        editorPane.setBorder(CBorder4);
        editorPane.setLineWrap(true);
        editorPane.setWrapStyleWord(true);
        
        JButton btnSubmit = new JButton("Submit");
        contentPane.add(btnSubmit, "cell 0 5 5 1");
        
        JLabel lblDiscardPrompt = new JLabel("<html><body style=\"text-align: center;\"><b>To discard this review</b>, close this window now.<br>Submitted reviews cannot be edited.</body></html>");
        lblDiscardPrompt.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblDiscardPrompt, "cell 0 6 5 1");

        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reviewScore = 1;
            }
        });
        
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reviewScore = 2;
            }
        });
        
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reviewScore = 3;
            }
        });
        
        btn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reviewScore = 4;
            }
        });
        
        btn5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reviewScore = 5;
            }
        });
        
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (reviewScore == -1) {
                    GUIHelpers.showErrorDialog("Please choose a review score.", null);
                    return;
                }
                reviewText = editorPane.getText().trim();

                try {
                    Review newReview = new Review();
                    newReview.setCustomerID(CustomerID);
                    newReview.setISBN(BookISBN);
                    newReview.setReviewScore(reviewScore);
                    newReview.setReviewText(reviewText);
                    
                    M.reviewBook(newReview);
                    Log.l.info("Review submitted: " + CustomerID + ", " + BookISBN);
                    dispose();
                }
                catch (Exception exc) {
                    GUIHelpers.showErrorDialog("Unable to submit review. Check if you have already submitted a review before.", exc);
                }
            }
        });

        setVisible(true);
    }
}
