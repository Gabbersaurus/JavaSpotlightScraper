package GUI;

import Scraper.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class GUI extends JFrame implements ScraperListener {

    private JTextArea outputTextArea;
    private JPanel imagePanel;
    private Scraper scraper;
    private int preferredWidth;
    private int preferredHeight;

    /**
     * GUI constructor
     */
    public GUI() {
        showWindow(); //Start by showing the (empty) window. If I don't do this the dialog will not be attached to a window (and will not show up in the Windows taskbar)
        showDialog(); //Ask for the preferred dimensions
    }

    /**
     * Opens an empty window
     */
    private void showWindow() {
        setTitle("Spotlight Scraper");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Creates the GUI log and starts the scraper with the preferred dimensions from the dialog
     */
    private void startScraper() {
        addLog();

        scraper = new Scraper(this, preferredWidth, preferredHeight, true); //Start the scraper
        scraper.start();

        pack(); //Size window to the log
    }

    /**
     * Creates the scrollable log window
     */
    private void addLog() {
        outputTextArea = new JTextArea("",30,50);
        outputTextArea.setEditable(false);

        add(new JScrollPane(outputTextArea));
    }

    /**
     * Creates the scrollable panel in which the image buttons will come
     */
    private void addImagePanel() {
        imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));

        JScrollPane imagePanelScroll = new JScrollPane(imagePanel);
        imagePanelScroll.setPreferredSize(new Dimension (184, 500));

        add(imagePanelScroll);
    }

    /**
     * Creates buttons from the scraped images if the list is not empty
     */
    private void addImageButtons() {
        ArrayList<ScrapedImage> scrapedImages = scraper.getScrapedImages();

        if(scrapedImages.size() > 0) {
            log("Creating image buttons");

            addImagePanel();

            for(ScrapedImage scrapedImage : scraper.getScrapedImages()) {
                JButton button = new JButton("Save");
                button.setIcon(new ImageIcon(scrapedImage.getThumbnail()));
                button.addActionListener(e -> saveImage(scrapedImage.getFile())); //Open the save dialog when pressed
                imagePanel.add(button);
            }

            pack();
        }
    }

    /**
     * Opens a dialog to save the JPG image and copies it to the chosen location
     *
     * @param file The original file which will be copied to the chosen location
     */
    private void saveImage(File file) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter( "JPG image", "jpg"));
        int returnVal = fileChooser.showDialog(this, "Save");

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File newFile = fileChooser.getSelectedFile();

            if(!newFile.getAbsolutePath().endsWith(".jpg")) {
                newFile = new File(newFile + ".jpg");
            }

            log("Saving image to" + newFile.toString());

            try {
                Files.copy(file.toPath(), newFile.toPath());
            } catch(Exception e) {
                log(e.toString());
            }
        }
    }

    /**
     * Opens the dialog to ask for the dimensions, stores the dimensions and starts the scraper when pressed 'Start'
     */
    private void showDialog() {
        Dialog dialog = new Dialog(this);

        preferredWidth = dialog.getPreferredWidth();
        preferredHeight = dialog.getPreferredHeight();

        startScraper();
    }

    /**
     * Adds the given string to the log and scrolls to the bottom by moving the caret
     *
     * @param message the string to be added to the log
     */
    private void log(String message) {
        outputTextArea.append(message + "\n");
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());
    }


    /**
     * Handles the callback from the scraper
     * When the type is 'LOG', log the message
     * When the type is 'FINISH', create the image buttons so the user can save the scraped images
     *
     * @param scraperCallback the callback class which contains the type and an optional message.
     */
    @Override
    public void scraperCallback(ScraperCallback scraperCallback) {
        switch (scraperCallback.getScraperCallbackType()) {
            case LOG:
                log(scraperCallback.getMessage());
                break;
            case FINISH:
                addImageButtons();
                break;
        }
    }
}
