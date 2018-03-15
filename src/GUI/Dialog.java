package GUI;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;

public class Dialog extends JDialog {

    private JFormattedTextField preferredWidth;
    private JFormattedTextField preferredHeight;

    /**
     * Dialog constructor.
     * Asks the user for the dimensions
     * When the user presses 'Start' the dialog will be closed and the process from which this dialog is opened will continue
     * When the user presses 'Cancel' the entire process will be stopped
     *
     * @param jframe the frame the dialog should be attached to
     */
    public Dialog(JFrame jframe) {
        super(jframe, "Spotlight scraper",true);
        setSize(320, 144);
        setLayout( new FlowLayout() );

        addForm();

        setVisible(true);
    }

    /**
     * Creates the form that prompts the user for the dimensions
     */
    private void addForm() {
        add(new JLabel("You are about to scrape Windows Spotlight images"));

        add(new JLabel("Preferred width:"));
        preferredWidth = new JFormattedTextField(new NumberFormatter());
        preferredWidth.setValue(1920);
        preferredWidth.setColumns(15);
        add(preferredWidth);

        add(new JLabel("Preferred height:"));
        preferredHeight = new JFormattedTextField(new NumberFormatter());
        preferredHeight.setValue(1080);
        preferredHeight.setColumns(15);
        add(preferredHeight);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> dispose());
        add(startButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> System.exit(0));
        add(cancelButton);
    }

    /**
     * Returns the preferred width entered by the user
     *
     * @return The preferred width
     */
    public int getPreferredWidth() {
        Object value = preferredWidth.getValue();

        return castValueToInt(value);
    }

    /**
     * Returns the preferred width entered by the user
     *
     * @return The preferred height
     */
    public int getPreferredHeight() {
        Object value = preferredHeight.getValue();

        return castValueToInt(value);
    }

    /**
     * This will cast a Long or an Integer to int
     * If it is neither it will default to 0
     *
     * @param value The value to cast
     *
     * @return the value casted to int
     */
    private int castValueToInt(Object value) {
        if(value instanceof Long) {
            return ((Long) value).intValue();
        }
        else if(value instanceof Integer) {
            return (int) value;
        }
        else {
            return 0;
        }
    }


}
