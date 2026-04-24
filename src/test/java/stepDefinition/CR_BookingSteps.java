package stepDefinition;

import io.cucumber.java.en.*;
import base.pages;
import util.AllFunctionalities;


public class CR_BookingSteps {

    // fill booking form
    @When("user fills booking details from excel")
    public void fill_details_from_excel() {

        Object[][] data = AllFunctionalities.getData("car"); // read excel data

        pages.bookingPage.fillForm(
                data[0][0].toString(),
                data[0][1].toString(),
                data[0][2].toString(),
                data[0][3].toString(),
                data[0][4].toString(),
                data[0][5].toString()
        );
    }

    // select payment and continue
    @And("user selects Google Pay and continues")
    public void select_payment_and_continue() {
        pages.bookingPage.selectGooglePay(); // select payment
        pages.bookingPage.clickContinue(); // continue
    }

    // verify booking
    @Then("booking should proceed successfully")
    public void verify_booking() {
        String url = pages.bookingPage.getCurrentUrl(); // get url
        System.out.println("Booking URL: " + url);
    }
}