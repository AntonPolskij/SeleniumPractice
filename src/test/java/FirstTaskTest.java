import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FirstTaskTest {

    static WebDriver driver;
    String login = "GB202307c175020";
    String password = "0055819c62";
    @BeforeAll
    static void initElements(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        // chromeOptions.addArguments("--headless") не запускает окно браузера
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://test-stand.gb.ru/login");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @AfterAll
    static void closeApp(){
        driver.quit();
    }

    @Test
    void authorization(){
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       WebElement userLogin = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("form#login input[type='text']")));
       WebElement userPassword = wait.until(ExpectedConditions.presenceOfElementLocated(
               By.cssSelector("form#login input[type='password']")));
//        WebElement userLogin = driver.findElement(By.xpath("//*[@type='text']"));
//        WebElement userPassword = driver.findElement(By.xpath("//*[@type='password']"));
        WebElement button = driver.findElement(By.xpath("//button"));
        userLogin.sendKeys(login);
        userPassword.sendKeys(password);
        button.click();
        Assertions.assertEquals("Hello, " + login,driver.findElement(By.partialLinkText("Hello")).getText());
    }

}
