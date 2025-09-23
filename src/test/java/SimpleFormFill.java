import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;

public class SimpleFormFill {
    public static void main(String[] args) {
        // If you don't use WebDriverManager, point to your chromedriver:
        // System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            driver.get("https://rahulshettyacademy.com/angularpractice/");

            // Name
            driver.findElement(By.name("name")).sendKeys("Tharani S");

            // Email
            driver.findElement(By.name("email")).sendKeys("tharani@example.com");

            // Password (field usually has type='password' on this page)
            driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Secret@123");

            // Checkbox: "Check me out if you Love IceCreams!"
            // (id is typically exampleCheck1 on this page)
            WebElement loveIceCreams = driver.findElement(By.id("exampleCheck1"));
            if (!loveIceCreams.isSelected()) loveIceCreams.click();

            // Gender dropdown (id is typically exampleFormControlSelect1)
            Select gender = new Select(driver.findElement(By.id("exampleFormControlSelect1")));
            gender.selectByVisibleText("Female"); // or "Male"

            // Employment Status (radio) — pick "Employed"
            // (ids on this page are usually inlineRadio1=Student, inlineRadio2=Employed, inlineRadio3 disabled)
            driver.findElement(By.id("inlineRadio2")).click();

            // Date of Birth (placeholder shows mm/dd/yyyy)
            // Field is commonly name='bday' on this page.
            WebElement dob = driver.findElement(By.name("bday"));
            dob.sendKeys("09/22/2025");

            // Submit (green button)
            driver.findElement(By.cssSelector("input.btn.btn-success, button.btn.btn-success")).click();

            // Read & print the success banner (has .alert-success)
            String alertText = driver.findElement(By.cssSelector(".alert-success")).getText();
            System.out.println("Form submission message: " + alertText);

        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
