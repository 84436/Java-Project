package natic.gui;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class GUIHelpers {
    // https://stackoverflow.com/a/27873384
    // It's hard to believe Java Swing can understand HTML4 for basic formatting, but it _does_
    private static final String HTML_TEMPLATE = "<html><body style=\"%s\">%s</body></html>";
    
    public static String htmlBoldText(String text) {
        String style = "font-weight: bold;";
        return String.format(HTML_TEMPLATE, style, text);
    }
    
    // styledTabName
    public static String htmlTabName(String text) {
        String style = "margin: 20px 5px; font-family: SansSerif; font-size: 14pt; font-weight: bold;";
        return String.format(HTML_TEMPLATE, style, text);
    }
    
    // styledHeaderLabel
    public static String htmlHeader(String text) {
        String style = "font-weight: bold; font-size: 24pt";
        return String.format(HTML_TEMPLATE, style, text);
    }
    
    // styledHeaderSmallLabel
    public static String htmlHeaderSmall(String text) {
        String style = "font-weight: bold; font-size: 18pt";
        return String.format(HTML_TEMPLATE, style, text);
    }
    
    // adapted from https://stackoverflow.com/a/7434935
    public static void setGlobalFont(String family, int style, int size) {
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
    public static void addPlaceholderText(JTextField field, String text) {
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
    
    public static void addPasswordRevealToggleEvent(JToggleButton btn, JPasswordField passfield) {
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

    public static void showErrorDialog(String content, Exception exc) {
        String errorMessage;
        if (exc == null) {
            errorMessage = String.format(
                "%s",
                content
            );
        }
        else {
            errorMessage = String.format(
                "%s\nException class name: %s",
                content, exc.getClass().getName()
            );
            exc.printStackTrace();
        }
        
        // new JFrame() = Dialog detached from any parents
        JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoDialog(String content) {
        JOptionPane.showMessageDialog(new JFrame(), content, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    // https://stackoverflow.com/a/144950
    public static void centerWindow(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
