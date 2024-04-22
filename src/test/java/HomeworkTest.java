import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class HomeworkTest {
    static WebDriver driver;
    String login = "GB202307c175020";
    String password = "0055819c62";
    String userName = "dummy";
    String loginForUser = "dummy1";
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
    static void closeApp() throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("C:\\Users\\polsk\\Desktop\\Git\\SeleniumPractice\\src\\main\\resources\\screenshot.png"));
        driver.quit();
    }

    @Test
    void createUserTest(){
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
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("create-btn"))).click();
        driver.findElements(By.className("mdc-text-field__input")).get(0).sendKeys(userName);
        driver.findElements(By.className("mdc-text-field__input")).get(3).sendKeys(loginForUser);
        driver.findElement(By.xpath("//*[@id=\"upsert-item\"]/div[8]/button")).click();
        driver.findElement(By.xpath("//button[@data-mdc-dialog-action='close']")).click();
        WebElement searchElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='dummy']")));
        Assertions.assertEquals(searchElement.getText(), userName);
    }

}

