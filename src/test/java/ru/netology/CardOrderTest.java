package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderTest {

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

    }

    @AfterEach
    void teardown() {

        driver.quit();
        driver = null;

    }

    @Test
    public void shouldSendForm() throws InterruptedException {

        driver.get("http://localhost:9999");
        System.out.println("");
        Thread.sleep(5000);
    }

    @Test
    public void shouldSendTheCompletedForm() {

        driver.get("http://localhost:9999/");
        //List<WebElement> elements = driver.findElements(By.className("input__control"));
        WebElement form = driver.findElement(By.cssSelector("[data-test-id=callback-form]"));
        //elements.get(0).sendKeys("Васильева Анна");
        //elements.get(1).sendKeys("+79258886611");
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Васильева Анна");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79258886611");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button button_view_extra button_size_m button_theme_alfa-on-white")).click();
        //driver.findElement(By.className("checkbox__control")).click();
        //driver.findElement(By.className("button button_view_extra button_size_m button_theme_alfa-on-white")).click();
        String text = driver.findElements(By.className("paragraph paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

        //



        //form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
    }
}
