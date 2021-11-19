import io.github.bonigarcia.wdm.WebDriverManager;
import models.Account;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
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

        Thread.sleep(1500); // TODO: Find a better way to wait for an element to become clickable
        WebElement userInfoDropdown = driver.findElement(By.cssSelector("#_desktop_user_info > div > div > svg"));
        userInfoDropdown.click();

        Thread.sleep(1500);
        WebElement signinButton = driver.findElement(By.cssSelector(".user-down > li > a > span"));
        signinButton.click();

        Thread.sleep(1500);
        WebElement createAccountButton = driver.findElement(By.className("no-account"));
        createAccountButton.click();

        Thread.sleep(1500);
        List<WebElement> genderOptions = driver.findElements(By.className("custom-radio"));
        genderOptions.get(0).click();

        Thread.sleep(1500);
        List<WebElement> signupFormFields = driver.findElements(By.cssSelector("input.form-control"));
        // Element with index 0 is the searchbar
        signupFormFields.get(1).sendKeys(userAccount.firstName);
        signupFormFields.get(2).sendKeys(userAccount.lastName);
        signupFormFields.get(3).sendKeys(userAccount.email);
        signupFormFields.get(4).sendKeys(userAccount.password);
        signupFormFields.get(5).sendKeys(dateFormatter.format(userAccount.birthday));

        js.executeScript("window.scrollBy(0,250)", "");

        Thread.sleep(1500);
        WebElement signupButton = driver.findElement(By.className("form-control-submit"));
        signupButton.click();

        Thread.sleep(1500);
        userInfoDropdown = driver.findElement(By.cssSelector("#_desktop_user_info > div > div > svg"));
        userInfoDropdown.click();

        Thread.sleep(1500);
        WebElement signoutButton = driver.findElement(By.className("logout"));
        signoutButton.click();

        Thread.sleep(1500);
        userInfoDropdown = driver.findElement(By.cssSelector("#_desktop_user_info > div > div > svg"));
        userInfoDropdown.click();

        Thread.sleep(1500);
        signinButton = driver.findElement(By.cssSelector(".user-down > li > a > span"));
        signinButton.click();

        Thread.sleep(1500);
        WebElement emailTextField = driver.findElement(By.cssSelector(".form-group > div > input"));
        emailTextField.sendKeys(userAccount.email);

        Thread.sleep(1500);
        WebElement pwdTextField = driver.findElement(By.cssSelector(".form-group > div > div > input"));
        pwdTextField.sendKeys(userAccount.password);

        Thread.sleep(1500);
        WebElement submitButton = driver.findElement(By.id("submit-login"));
        submitButton.click();

        Thread.sleep(1500);
        WebElement searchBar = driver.findElement(By.className("search_query"));
        searchBar.sendKeys("PC portable MacBook M1 13.3");

        Thread.sleep(1500);
        WebElement searchButton = driver.findElement(By.cssSelector("#sp-btn-search > button"));
        searchButton.click();

        Thread.sleep(1500);
        List<WebElement> productsTitle = driver.findElements(By.className("product-title"));
        productsTitle.get(0).click();

        Thread.sleep(1500);
        WebElement addToCartButton = driver.findElement(By.className("add-to-cart"));
        addToCartButton.click();

        Thread.sleep(1500);
        WebElement commandButton = driver.findElement(By.cssSelector("a.btn-block"));
        commandButton.click();
    }

    @After
    public void quitDriver() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }
}

