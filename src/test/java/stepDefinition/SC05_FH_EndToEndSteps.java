package stepDefinition;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import pages.SC05_FH_EndToEnd;
import util.AllFunctionalities;

import java.util.List;
import java.util.Map;

public class SC05_FH_EndToEndSteps {

    SC05_FH_EndToEnd travellerPage =
            new SC05_FH_EndToEnd(AllFunctionalities.getDriver());

    @When("user enters complete traveller details")
    public void user_enters_traveller_details(DataTable table) {

        List<Map<String, String>> data = table.asMaps(String.class, String.class);

        int travellerIndex = 1;

        for (Map<String, String> row : data) {

            String type = row.get("type");

            // ✅ CONTACT
            if (type.equalsIgnoreCase("contact")) {

                travellerPage.fillContactDetails(
                        row.get("firstName"),
                        row.get("lastName"),
                        row.get("email"),
                        row.get("phone"),
                        row.get("address"),
                        row.get("houseNo"),
                        row.get("pincode"),
                        row.get("city")
                );
            }

            // ✅ ALL TRAVELLERS (dynamic)
            else if (type.toLowerCase().contains("traveller")) {

                travellerPage.fillTraveller(
                        travellerIndex,
                        row.get("gender"),
                        row.get("firstName"),
                        row.get("lastName"),
                        row.get("day"),
                        row.get("month"),
                        row.get("year"),
                        row.get("nationality"),
                        row.get("docNumber"),
                        row.get("issueCountry"),
                        row.get("issueDay"),
                        row.get("issueMonth"),
                        row.get("issueYear"),
                        row.get("expDay"),
                        row.get("expMonth"),
                        row.get("expYear")
                );

                travellerIndex++; // 🔥 IMPORTANT
            }
        }
    }

    @And("user accepts policy and clicks next")
    public void user_accepts_policy_and_clicks_next() throws InterruptedException {
        travellerPage.acceptPolicyAndProceed();
    }

    @Then("payment page should be displayed")
    public void payment_page_should_be_displayed() {
        travellerPage.verifyPaymentSection();
    }
}