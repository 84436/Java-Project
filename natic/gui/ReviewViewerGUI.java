package natic.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import java.util.*;
import natic.review.Review;

public class ReviewViewerGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private String aggregateBookDetailsReviews(ArrayList<Review> reviews) {
        String r = "";
        r += "<html><body style=\"font-size: 18pt;\">";

        if (reviews.size() == 0) {
            r += "<p style=\"font-family: SansSerif; font-weight: bold; text-align: center;\">No reviews available.</p>";
        }
        else {
            for (int i = 0; i < reviews.size(); i++) {
                Review each = reviews.get(i);
                r += String.format(
                    "<p><i style=\"font-family: Serif;\">%s</i><br><b style=\"font-family: SansSerif; font-size: 16pt;\">-- %s, ★ %s/5<b></p><br>",
                    each.getReviewText(), each.getCustomerName(), Integer.toString(each.getReviewScore())
                );
                if (i < reviews.size() - 1)
                    r += "<br>";
            }
        }

        r += "</body></html>";
        return r;
    }
    
    public ReviewViewerGUI(ArrayList<Review> reviews) {
        setTitle("Reviews");
        setBounds(0, 0, 500, 700);
        GUIHelpers.centerWindow(this);
        GUIHelpers.setGlobalFont("SansSerif", Font.PLAIN, 14);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // addWindowListener(new WindowAdapter() {
        //     public void windowClosing(WindowEvent we) {
        //         dispose();
        //     }
        // });

        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setContentType("text/html");
        scrollPane.setViewportView(textPane);

        textPane.setText(this.aggregateBookDetailsReviews(reviews));

        setVisible(true);
    }
}
