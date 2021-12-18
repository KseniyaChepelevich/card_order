package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderHappyPathTest {

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
        driver.get("http://localhost:9999/");

    }

    @AfterEach
    void teardown() {

        driver.quit();
        driver = null;

    }

    @Test
    public void shouldSendForm()  {
        driver.get("http://localhost:9999/");
        System.out.println("");


    }

    @Test
    public void shouldSendTheCompletedForm()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Васильева Анна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79258886611");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);


    }

    @Test
    public void shouldSendTheCompletedFormWith2LetterName()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ру Ян");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79936455555");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormWith1LetterName()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Р Я");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79936455555");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormWithOnlyNameWithNonAlphabeticCharacters()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+59365456611");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormWithHyphenatedName()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванова Анна-Мария");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79996448822");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormWithSurnameHyphenated()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+78656448822");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormWithSurnameAndNameHyphenated()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий-Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+72226455866");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormWithSurnameAndName1Later()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("И И");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+72226455866");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormPhoneNumberFor8()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+82226455866");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormPhoneNumberFor9()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+92226455866");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormPhoneNumberFor3()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+32226455866");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormPhoneNumberFor1()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+12226455866");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormPhoneNumberFor0()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+02226455866");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormPhoneNumberWithTheSameDigits()  {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий-Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+99999999999");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

}
