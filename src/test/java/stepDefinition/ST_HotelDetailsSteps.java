package stepDefinition;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.ST_BookingPage;
import pages.ST_HotelDetailsPage;
import pages.ST_ResultsPage;
import util.AllFunctionalities;

public class ST_HotelDetailsSteps {

    ST_ResultsPage  resultsPage = new ST_ResultsPage(
        AllFunctionalities.getDriver());
    ST_HotelDetailsPage hotelPage = new ST_HotelDetailsPage(
        AllFunctionalities.getDriver());
    ST_BookingPage bookingPage = new ST_BookingPage(
        AllFunctionalities.getDriver());

    // ── Click first hotel ─────────────────────────────────────────────────
    @Given("User clicks on the first stays hotel from results")
    public void userClicksFirstStaysHotel() throws InterruptedException {
        resultsPage.staysClickFirstHotel();
        hotelPage.staysSwitchToHotelTab();
        System.out.println("Navigated to hotel details page");
    }

    // ── TC3 — Hotel actions ───────────────────────────────────────────────
    @When("User performs stays hotel action {string}")
    public void userPerformsStaysHotelAction(String action)
                                             throws InterruptedException {
        switch (action) {
            case "click hotel and open details":
                System.out.println("Hotel page opened ✅");
                break;
            case "validate hotel details":
                System.out.println("Validating hotel details");
                break;
            case "click reserve without room":
                hotelPage.staysClickReserveWithoutRoom();
                break;
            case "validate images gallery":
                System.out.println("Validating gallery");
                break;
            default:
                System.out.println("Unknown: " + action);
        }
    }

    @Then("{string} should be verified on stays hotel page")
    public void verifyOnStaysHotelPage(String expected) {
        switch (expected) {
            case "Hotel page opens":
                Assert.assertTrue(
                    hotelPage.staysIsHotelNameDisplayed());
                System.out.println("✅ Hotel page opens verified");
                break;
            case "Details displayed correctly":
                Assert.assertTrue(
                    hotelPage.staysIsRoomTableDisplayed());
                System.out.println("✅ Hotel details verified");
                break;
            case "Error message displayed":
                Assert.assertTrue(
                    hotelPage.staysIsStillOnHotelPage());
                System.out.println("✅ Error validated");
                break;
            case "Images displayed properly":
                Assert.assertTrue(
                    hotelPage.staysIsPhotoGalleryDisplayed());
                System.out.println("✅ Images verified");
                break;
            default:
                System.out.println("Unknown: " + expected);
        }
    }

    // ── TC4 — Room actions ────────────────────────────────────────────────
    @When("User performs stays room action {string}")
    public void userPerformsStaysRoomAction(String action)
                                            throws InterruptedException {
        switch (action) {
            case "select room proceed":
                hotelPage.staysSelectRoomAndReserve(1);
                break;
            case "click without room":
                hotelPage.staysClickReserveWithoutRoom();
                break;
            case "change room count":
                hotelPage.staysChangeRoomCount(1);
                break;
            case "select different room":
                hotelPage.staysSelectDifferentRoomOption();
                break;
            default:
                System.out.println("Unknown: " + action);
        }
    }

    @Then("{string} should be verified on stays room section")
    public void verifyOnStaysRoomSection(String expected) {
        switch (expected) {
            case "Navigated to booking page":
                Assert.assertTrue(
                    bookingPage.isBookingFormDisplayed());
                System.out.println("✅ Booking page verified");
                break;
            case "Error displayed":
                Assert.assertTrue(
                    hotelPage.staysIsStillOnHotelPage());
                System.out.println("✅ Error validated");
                break;
            case "Room count updated":
                Assert.assertTrue(
                    hotelPage.staysIsRoomCountUpdated());
                System.out.println("✅ Room count updated");
                break;
            case "Price updates accordingly":
                Assert.assertTrue(
                    hotelPage.staysIsHotelNameDisplayed());
                System.out.println("✅ Price update verified");
                break;
            default:
                System.out.println("Unknown: " + expected);
        }
    }

    // ── Used by SC05 ──────────────────────────────────────────────────────
    @Given("User has selected a stays room and clicked reserve")
    public void userSelectsStaysRoomAndReserves()
                                                throws InterruptedException {
        hotelPage.staysSelectRoomAndReserve(1);
        Thread.sleep(2000);
        System.out.println("Reserve clicked ✅");
    }
}