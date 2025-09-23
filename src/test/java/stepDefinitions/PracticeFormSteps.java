package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.PracticeFormPage;
import hooks.TestHooks;
import org.junit.Assert;

public class PracticeFormSteps {

    private final WebDriver driver;
    private final PracticeFormPage form;

    public PracticeFormSteps(TestHooks hooks) {
        this.driver = hooks.getDriver();
        this.form = new PracticeFormPage(driver);
    }

    @Given("I am on the Angular practice form")
    public void i_am_on_the_form() {
        form.open();
    }

    @When("I enter name {string} and email {string} and password {string}")
    public void i_enter_fields(String name, String email, String password) {
        form.setName(name);
        form.setEmail(email);
        form.setPassword(password);
    }

    @When("I check the ice cream preference {string}")
    public void i_set_checkbox(String likeIceCreams) {
        form.setLoveIceCreams(likeIceCreams.equalsIgnoreCase("yes") || likeIceCreams.equalsIgnoreCase("true"));
    }

    @When("I select gender {string}")
    public void i_select_gender(String gender) {
        form.selectGender(gender);
    }

    @When("I choose employment status {string}")
    public void i_choose_employment(String employment) {
        form.selectEmployment(employment);
    }

    @When("I enter date of birth {string}")
    public void i_enter_dob(String dob) {
        form.setDob(dob);
    }

    @When("I submit the form")
    public void i_submit() {
        form.submit();
    }

    @Then("I should see a success message")
    public void i_should_see_success() {
        String msg = form.getSuccessMessage();
        System.out.println("Success banner: " + msg);
        Assert.assertTrue("Expected success message, got: " + msg, msg.toLowerCase().contains("success"));
    }
}
