package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class FormTest {

    WebDriver driver;
    String url;

    @Before
    public void testSetup() {
        System.setProperty("webdriver.chrome.driver", "/home/me/Documents/drivers/chromedriver_linux64/chromedriver");
        driver = new ChromeDriver();
        url = "https://forms.liferay.com/web/forms/shared/-/form/122548";
        driver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testViewDescriptionForms() {
        //View the Description Forms "Let's party rock."

        driver.get(url);
        String description = driver.findElement(By.className("lfr-ddm-form-page-description")).getText();
        assertEquals("Let's party rock.", description);
    }

    @Test
    public void testViewAllRequiredFields() {
        //View all required fields and an error message if they are not filled
        driver.get(url);
        driver.findElement(By.id("ddm-form-submit")).sendKeys(Keys.ENTER);

        String whatsYourName = driver.findElement(By.xpath("//div[@class=\"col-ddm col-md-7\"]//div[contains(@class,'form-feedback-item')]")).getText();
        String required = "This field is required.";

        assertEquals(required, whatsYourName);

        String dateOfBirth = driver.findElement(By.xpath("//div[@class=\"col-ddm col-md-5\"]//div[contains(@class,'form-feedback-item')]")).getText();
        assertEquals(required, dateOfBirth);

        String whyTestingArea = driver.findElement(By.xpath("//div[@class=\"col-ddm col-md-12\"]//div[contains(@class,'form-feedback-item')]")).getText();
        assertEquals(required, whyTestingArea);
    }

    @Test
    public void testViewPublishFormSuccessMessage() {
        //Fill out the form and check the success message
        driver.get(url);

        WebElement inputName = driver.findElement(By.xpath("//input[contains(@class,'form-control ddm-field-text')]"));

        inputName.sendKeys("Test Name");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement inputQuestion = driver.findElement(By.xpath("//textarea[contains(@class,'ddm-field-text form-control')]"));

        inputQuestion.sendKeys("Test");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement datePicker = driver.findElement(By.xpath("//input[contains(@class,'form-control input-group-inset input-group-inset-after')]"));

        datePicker.sendKeys("01/14/1992");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        String successMessage = driver.findElement(By.xpath("//div[contains(@class,'alert-success')]")).getText();
        assertEquals("Success:Your request completed successfully.", successMessage);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
