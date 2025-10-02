package stepDefinitions;

import io.cucumber.java.en.*;
import pages.PracticeFormPage;
import org.junit.Assert;
import base.BaseTest;

public class PracticeFormSteps extends BaseTest{

	private PracticeFormPage form;

    @Given("I am on the Angular practice form")
    public void openForm() {
        form = new PracticeFormPage(getDriver());
        form.open();
    }

    @When("I enter name {string} and email {string} and password {string}")
    public void fillFields(String name, String email, String password) {
        form.setName(name);
        form.setEmail(email);
        form.setPassword(password);
    }

    @When("I check the ice cream preference {string}")
    public void setIceCream(String like) {
        form.setLoveIceCreams(like.equalsIgnoreCase("yes"));
    }

    @When("I select gender {string}")
    public void setGender(String gender) {
        form.selectGender(gender);
    }

    @When("I choose employment status {string}")
    public void setEmployment(String employment) {
        form.selectEmployment(employment);
    }

    @When("I enter date of birth {string}")
    public void setDob(String dob) {
        form.setDob(dob);
    }

    @When("I submit the form")
    public void submitForm() {
        form.submit();
    }

    @Then("I should see a success message")
    public void verifySuccess() {
        String msg = form.getSuccessMessage();
        Assert.assertTrue("Expected success message, but got: " + msg,
                msg.toLowerCase().contains("success"));
    }
}
