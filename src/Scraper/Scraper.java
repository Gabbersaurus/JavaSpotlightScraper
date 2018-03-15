package Scraper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;

public class Scraper {

    private ScraperListener scraperListener;
    private int preferredWidth;
    private int preferredHeight;
    private boolean useGUI;
    private ArrayList<ScrapedImage> scrapedImages;

    /**
     * Scraper constructor
     *
     * @param scraperListener The listener for the callbacks given by this scraper instance
     * @param preferredWidth The width the scraped images should be to be accepted
     * @param preferredHeight The height the scraped images should be to be accepted
     * @param useGUI If this is true the scaper will generate thumbnail images
     */
    public Scraper(ScraperListener scraperListener, int preferredWidth, int preferredHeight, boolean useGUI) {
        this.scraperListener = scraperListener;
        this.preferredWidth = preferredWidth;
        this.preferredHeight = preferredHeight;
        this.useGUI = useGUI;

        scrapedImages = new ArrayList<ScrapedImage>();
    }

    /**
     * Starts a thread in which all the files in the folder will be iterated and stored with the storeImageIfCorrect method
     */
    public void start() {
        new Thread(() -> {
            log("Starting scraper");
            File[] files = getAbsoluteSpotlightPath().toFile().listFiles();

            try {
                log("Found " + String.valueOf(files.length) + " files");

                for(File file : files) {
                    log("Handling " + file.getName());
                    storeImageIfCorrect(file);
                }

                log("Finished scraping, accepted " + scrapedImages.size() + " images");
                finish();
            } catch(Exception e) {
                log(e.toString());
            }
        }).start();
    }

    /**
     * Checks if the image has the proper dimensions, creates a ScrapedImage object and adds it to the scrapedImages list
     *
     * @param file the file to store
     */
    private void storeImageIfCorrect(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            int width = image.getWidth();
            int height = image.getHeight();

            log("The image's resolution is " + width + "x" + height + "px");

            if(width == preferredWidth && height == preferredHeight) {
                log("The image is accepted");
                scrapedImages.add(new ScrapedImage(file, image, useGUI));
            }
            else {
                log("The image is not accepted");
            }
        } catch(Exception e) {
            log(e.toString());
        }
    }

    /**
     * Creates a ScraperCallback with the type 'LOG' and the given message
     *
     * @param message the message to log
     */
    private void log(String message) {
        scraperListener.scraperCallback(new ScraperCallback(ScraperCallbackType.LOG, message));
    }

    /**
     * Creates a ScaperCallback with the type 'Finish'
     */
    private void finish() {
        scraperListener.scraperCallback(new ScraperCallback(ScraperCallbackType.FINISH));
    }

    /**
     * Get the absolute path where the Spotlight images are located
     *
     * @return The absolute Spotlight path
     */
    private Path getAbsoluteSpotlightPath() {
        return getSpotlightPath().toAbsolutePath();
    }

    /**
     * Get the path where the Spotlight images are located
     *
     * @return The Spotlight path
     */
    private Path getSpotlightPath() {
        return Paths.get(System.getenv("LOCALAPPDATA") + "\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets");
    }

    /**
     * Get the list with the scraped images
     *
     * @return the list with scraped images
     */
    public ArrayList<ScrapedImage> getScrapedImages() {
        return scrapedImages;
    }
}
