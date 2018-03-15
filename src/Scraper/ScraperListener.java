package Scraper;

public interface ScraperListener {

    /**
     * Handles callbacks given by the scraper
     *
     * @param scraperCallback The callback object given by the scraper
     */
    void scraperCallback(ScraperCallback scraperCallback);
}
