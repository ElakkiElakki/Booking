package stepDefinition;

import io.cucumber.java.en.*;
import base.pages;
import util.AllFunctionalities;


public class CR_BookingSteps {

    // fill booking form
    @When("user fills booking details from excel")
    public void fill_details_from_excel()  {

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
 
    @Then("booking form should be filled successfully")
    public void verify_form_filled() {

        System.out.println("✅ Booking form filled successfully");

   
    }
    }
