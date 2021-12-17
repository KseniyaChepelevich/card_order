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

public class CardOrderValidationCheckTest {
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
    public void shouldSendTheEmptyForm() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormWithSurnameAndNameInLatinLetters() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+59365456611");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormWithSurnameAndNameWithChineseCharacters() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("姓 姓");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+59365456611");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormWithSurnameAndNameWithNonAlphabeticCharacters() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("+= *-");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+59365456611");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormPhoneNumberWithoutPlus() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий-Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("872226455866");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        List<WebElement> elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormPhoneNumberOf10Digits() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий-Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+9564587512");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        List<WebElement> elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormPhoneNumberOf12Digits() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий-Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+956458751255");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        List<WebElement> elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormNonNumericPhoneNumber() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий-Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+*/.-=--**/-");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        List<WebElement> elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormAlphabeticPhoneNumber() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий-Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+dhgldjghwnv");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        List<WebElement> elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }






}
