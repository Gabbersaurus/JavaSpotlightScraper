package Scraper;

public class ScraperCallback {
    private ScraperCallbackType scraperCallbackType;
    private String message;

    /**
     * ScraperCallback constructor.
     * It will set the type without a message
     *
     * @param scraperCallbackType The callback type
     */
    public ScraperCallback(ScraperCallbackType scraperCallbackType) {
        this(scraperCallbackType, null);
    }
    /**
     * ScraperCallback constructor.
     * It will set the type and a message
     *
     * @param scraperCallbackType The callback type
     * @param message The message
     */
    public ScraperCallback(ScraperCallbackType scraperCallbackType, String message) {
        this.scraperCallbackType = scraperCallbackType;
        this.message = message;
    }

    /**
     * Returns the message
     *
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the callback type
     *
     * @return The callback type
     */
    public ScraperCallbackType getScraperCallbackType() {
        return scraperCallbackType;
    }
}
