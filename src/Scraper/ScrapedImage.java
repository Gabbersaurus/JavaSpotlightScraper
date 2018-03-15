package Scraper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ScrapedImage {
    private File file;
    private Image thumbnail;

    /**
     * Creates the scraped images and generates the thumbnail when needed.
     *
     * @param file The file of the scraped object
     * @param bufferedImage The buffered image of the scraped image
     * @param generateThumbnail Boolean which decides if thumbnails should be generated
     */
    public ScrapedImage(File file, BufferedImage bufferedImage, boolean generateThumbnail) {
        this.file = file;

        if(generateThumbnail) {
            generateThumbnail(bufferedImage);
        }
    }

    /**
     * Generates and sets the thumbnail from the given buffered image
     *
     * @param bufferedImage The buffered image from which the thumbnail should be generated
     */
    private void generateThumbnail(BufferedImage bufferedImage) {
        this.thumbnail = bufferedImage.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);
    }

    /**
     * Returns the scraped image file
     *
     * @return the scraped image file
     */
    public File getFile() {
        return file;
    }

    /**
     * Returns the scraped image thumbnail
     *
     * @return the scraped image thumbnail
     */
    public Image getThumbnail() {
        return thumbnail;
    }
}
