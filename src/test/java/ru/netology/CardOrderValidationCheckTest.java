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
        driver.get("http://localhost:9999/");

    }

    @AfterEach
    void teardown() {

        driver.quit();
        driver = null;

    }

    @Test
    public void shouldSendTheEmptyForm() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendAFormWithAnEmptyFieldSurnameAndName() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+45613245577");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendAFormWithAnEmptyFieldPhone() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendAFormWithoutClickInCheckbox() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+56258795544");

        String text = driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__text")).getText().trim();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormWithSurnameAndNameInLatinLetters() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+59365456611");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormWithSurnameAndNameWithChineseCharacters() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("姓 姓");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+59365456611");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormWithSurnameAndNameWithNonAlphabeticCharacters() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("+= *-");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+59365456611");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormPhoneNumberWithoutPlus() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий-Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("872226455866");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormPhoneNumberOf10Digits() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий-Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+9564587512");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormPhoneNumberOf12Digits() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий-Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+956458751255");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormNonNumericPhoneNumber() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий-Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+*/.-=--**/-");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendTheCompletedFormAlphabeticPhoneNumber() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Василий-Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+dhgldjghwnv");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }


}
