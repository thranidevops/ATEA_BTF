package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class PracticeFormPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameInput = By.name("name");
    private final By emailInput = By.name("email");
    private final By passwordInput = By.cssSelector("input[type='password']");
    private final By loveIceCreamsCheckbox = By.id("exampleCheck1");
    private final By genderSelect = By.id("exampleFormControlSelect1");
    private final By studentRadio = By.id("inlineRadio1");
    private final By employedRadio = By.id("inlineRadio2");
    private final By dobInput = By.name("bday");
    private final By submitBtn = By.cssSelector("input.btn.btn-success, button.btn.btn-success");
    private final By successAlert = By.cssSelector(".alert-success");

    public PracticeFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://rahulshettyacademy.com/angularpractice/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
    }

    public void setName(String name) {
        driver.findElement(nameInput).clear();
        driver.findElement(nameInput).sendKeys(name);
    }

    public void setEmail(String email) {
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void setPassword(String pwd) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(pwd);
    }

    public void setLoveIceCreams(boolean check) {
        WebElement box = driver.findElement(loveIceCreamsCheckbox);
        if (box.isSelected() != check) box.click();
    }

    public void selectGender(String gender) {
        new Select(driver.findElement(genderSelect)).selectByVisibleText(gender);
    }

    public void selectEmployment(String employment) {
        switch (employment.trim().toLowerCase()) {
            case "student":
                driver.findElement(studentRadio).click(); break;
            case "employed":
                driver.findElement(employedRadio).click(); break;
            default:
                throw new IllegalArgumentException("Unsupported employment: " + employment);
        }
    }

    public void setDob(String mmddyyyy) {
        WebElement dob = driver.findElement(dobInput);
        dob.clear();
        dob.sendKeys(mmddyyyy);
    }

    public void submit() {
        driver.findElement(submitBtn).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert)).getText();
    }
}
