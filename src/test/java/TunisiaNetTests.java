import io.github.bonigarcia.wdm.WebDriverManager;
import models.Account;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TunisiaNetTests {
    WebDriver driver;
    JavascriptExecutor js;

    @Before
    public void prepareDriver(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));

        js = (JavascriptExecutor) driver;
    }

    @Test
    public void buyMacTestCase() throws InterruptedException {
        // -------------- Test Scenario --------------
        // 1 - Create an account, logout and login
        // 2 - Search for Mac
        // 3 - Add it to cart and order
        // -------------------------------------------

        driver.get("https://www.tunisianet.com.tn/");

        String pattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
        String codeForEmailAndPwd = RandomStringUtils.random(10, true, true);
        String codeForNames = RandomStringUtils.random(10, true, false);
        Account userAccount = new Account(
                codeForNames + "firstname",
                codeForNames + "lastname",
                codeForEmailAndPwd.substring(5) + "test@test.com",
                codeForEmailAndPwd,
                new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(1) * 365 * 18) // 18 years ago
        );

        // Click on the user icon
        Thread.sleep(1500); // TODO: Find a better way to wait for an element to become clickable
        WebElement userInfoDropdown = driver.findElement(By.cssSelector("#_desktop_user_info > div > div > svg"));
        userInfoDropdown.click();

        // Click on the "Connexion" button
        Thread.sleep(1500);
        WebElement signinButton = driver.findElement(By.cssSelector(".user-down > li > a > span"));
        signinButton.click();

        // Click to create an account
        Thread.sleep(1500);
        WebElement createAccountButton = driver.findElement(By.className("no-account"));
        Assert.assertEquals("Pas de compte ? Créez-en un", createAccountButton.findElement(By.cssSelector("*")).getText());
        createAccountButton.click();

        // Choose the male option
        Thread.sleep(1500);
        List<WebElement> genderOptions = driver.findElements(By.className("custom-radio"));
        genderOptions.get(0).click();

        //Fill the rest of the form with the random account information
        Thread.sleep(1500);
        List<WebElement> signupFormFields = driver.findElements(By.cssSelector("input.form-control"));
        // Element with index 0 is the searchbar
        signupFormFields.get(1).sendKeys(userAccount.firstName);
        signupFormFields.get(2).sendKeys(userAccount.lastName);
        signupFormFields.get(3).sendKeys(userAccount.email);
        signupFormFields.get(4).sendKeys(userAccount.password);
        signupFormFields.get(5).sendKeys(dateFormatter.format(userAccount.birthday));

        js.executeScript("window.scrollBy(0,250)", ""); // Scroll down

        // Click to sign-up
        Thread.sleep(1500);
        WebElement signupButton = driver.findElement(By.className("form-control-submit"));
        signupButton.click();

        // Click on the user icon
        Thread.sleep(1500);
        userInfoDropdown = driver.findElement(By.cssSelector("#_desktop_user_info > div > div > svg"));
        userInfoDropdown.click();

        // Click on the logout button
        Thread.sleep(1500);
        WebElement signoutButton = driver.findElement(By.className("logout"));
        signoutButton.click();

        // As usual, click on the user icon
        Thread.sleep(1500);
        userInfoDropdown = driver.findElement(By.cssSelector("#_desktop_user_info > div > div > svg"));
        userInfoDropdown.click();

        // Click on "Connexion"
        Thread.sleep(1500);
        signinButton = driver.findElement(By.cssSelector(".user-down > li > a > span"));
        signinButton.click();

        // Fill the email field
        Thread.sleep(1500);
        WebElement emailTextField = driver.findElement(By.cssSelector(".form-group > div > input"));
        emailTextField.sendKeys(userAccount.email);

        // Fill the password field
        Thread.sleep(1500);
        WebElement pwdTextField = driver.findElement(By.cssSelector(".form-group > div > div > input"));
        pwdTextField.sendKeys(userAccount.password);

        // Click on sign in
        Thread.sleep(1500);
        WebElement submitButton = driver.findElement(By.id("submit-login"));
        submitButton.click();

        // Search for the desired Mac
        Thread.sleep(1500);
        WebElement searchBar = driver.findElement(By.className("search_query"));
        searchBar.sendKeys("PC portable MacBook M1 13.3");

        // Click to search
        Thread.sleep(1500);
        WebElement searchButton = driver.findElement(By.cssSelector("#sp-btn-search > button"));
        searchButton.click();

        // Click on the first product
        Thread.sleep(1500);
        List<WebElement> productsTitle = driver.findElements(By.className("product-title"));
        productsTitle.get(0).click();

        // Add product to cart
        Thread.sleep(1500);
        WebElement addToCartButton = driver.findElement(By.className("add-to-cart"));
        addToCartButton.click();

        // Click to order
        Thread.sleep(1500);
        WebElement orderButton = driver.findElement(By.cssSelector("a.btn-block"));
        orderButton.click();
    }

    @After
    public void quitDriver() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }
}

