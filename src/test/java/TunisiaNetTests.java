import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.sql.Driver;
import java.time.Duration;
import java.util.List;

public class TunisiaNetTests {
    WebDriver driver;

    @Before
    public void prepareDriver(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
    }

    @Test
    public void openGoogle() throws InterruptedException {
        driver.get("https://www.tunisianet.com.tn/");

        Thread.sleep(1500); // TODO: Find a better way to wait for an element to become clickable
        WebElement userInfoDropdown = driver.findElement(By.cssSelector("#_desktop_user_info > div > div > svg"));
        userInfoDropdown.click();

        Thread.sleep(1500);
        WebElement signinButton = driver.findElement(By.cssSelector(".user-down > li > a > span"));
        signinButton.click();
    }

    @After
    public void quitDriver() throws InterruptedException {
        Thread.sleep(5000);
        //driver.quit();
    }
}

