import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pom.LoginPage;
import pom.MainPage;
import pom.RegistrationPage;
import settings.WebDriverFactory;

import static org.junit.Assert.assertTrue;

@DisplayName("Регистрация нового пользователя")
public class UserRegistrationTest {
    private WebDriver driver;
    private String name;
    private String email;
    private String password;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;

    @Step("Подготовка данных и браузера")
    @Before
    public void setUp() {
        name = RandomStringUtils.randomAlphabetic(8);
        email = RandomStringUtils.randomAlphabetic(8) + "@email.ru";
        password = RandomStringUtils.randomAlphabetic(8);

        String browser = System.getProperty("browser", "chrome");
        driver = WebDriverFactory.getDriver(browser);
        driver.get("https://stellarburgers.nomoreparties.site/");
        driver.manage().window().maximize();

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
    }

    @Step("Закрытие браузера")
    @After
    public void tearDown() {
        driver.quit();
    }

    @DisplayName("Успешная регистрация пользователя")
    @Test
    public void correctUserRegistrationTest() {
        mainPage.waitLoadingMainPage();
        mainPage.clickLoginButton();
        loginPage.waitForLoadLoginPage();
        loginPage.clickRegistrationButton();
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        boolean isEnterButtonVisible = loginPage.isLoginButtonVisible();
        assertTrue("Кнопка 'Вход' не видима на странице", isEnterButtonVisible);
        System.out.println("Пользователь успешно зарегистрирован с email: " + email);
    }

    @DisplayName("Ошибка при регистрации с паролем менее 6 символов")
    @Test
    public void userRegistrationWithShortPasswordTest() {
        password = RandomStringUtils.randomAlphabetic(4);
        mainPage.waitLoadingMainPage();
        mainPage.clickLoginButton();
        loginPage.waitForLoadLoginPage();
        loginPage.clickRegistrationButton();
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        registrationPage.clickRegistrationButton();
        boolean isErrorTextVisible = registrationPage.isIncorrectPasswordTextVisible();
        assertTrue("Текст ошибки виден на странице", isErrorTextVisible);
        System.out.println("Пользователя невозможно зарегистрировать: указан недопустимый пароль");
    }
}
