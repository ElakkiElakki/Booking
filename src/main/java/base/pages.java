package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pages.CR_SearchPage;
import pages.CR_ResultsPage;
import pages.CR_DetailsPage;
import pages.CR_ExtrasPage;
import pages.CR_ProtectionPage;
import pages.CR_BookingPage;

/**
 * This class acts as a Page Object Manager.
 * It initializes all Page classes using PageFactory.
 */
public class pages {

    public static CR_SearchPage searchPage;       // object for Search page
    public static CR_ResultsPage resultsPage;     // object for Results page
    public static CR_DetailsPage detailsPage;     // object for Details page
    public static CR_ExtrasPage extrasPage;       // object for Extras page
    public static CR_ProtectionPage protectionPage; // object for Protection page
    public static CR_BookingPage bookingPage;     // object for Booking page

    /**
     * This method initializes all page objects.
     * It must be called after WebDriver is launched.
     */
    public static void loadAllPages(WebDriver driver) {

        searchPage = PageFactory.initElements(driver, CR_SearchPage.class); // init search page
        resultsPage = PageFactory.initElements(driver, CR_ResultsPage.class); // init results page
        detailsPage = PageFactory.initElements(driver, CR_DetailsPage.class); // init details page
        extrasPage = PageFactory.initElements(driver, CR_ExtrasPage.class); // init extras page
        protectionPage = PageFactory.initElements(driver, CR_ProtectionPage.class); // init protection page
        bookingPage = PageFactory.initElements(driver, CR_BookingPage.class); // init booking page
    }
}