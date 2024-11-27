import io.qameta.allure.Description;
import io.qameta.allure.Step;
import model.User;
import model.UserApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pom.*;
import settings.WebDriverFactory;

import static model.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserAccountTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private RecoverPasswordPage recoverPasswordPage;
    private ProfilePage profilePage;
    private User user;
    private UserApi userApi;
    private String accessToken;
    private final String LOGIN_URL = "https://stellarburgers.nomoreparties.site/account/profile";

    @Step("Подготовка данных и браузера")
    @Before
    public void setUp() {
        user = User.getUser();
        userApi = new UserApi();
        accessToken = userApi.createUser(user);

        String browser = System.getProperty("browser", "chrome");
        driver = WebDriverFactory.getDriver(browser);
        driver.get(MAIN_PAGE);
        driver.manage().window().maximize();

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        recoverPasswordPage = new RecoverPasswordPage(driver);
        profilePage = new ProfilePage(driver);
    }

    @Step("Удаление данных и закрытие браузера")
    @After
    public void tearDown() {
        userApi = new UserApi();
        userApi.deleteUser(accessToken);
        driver.quit();
    }

    @Step("Авторизация пользователя")
    public void userAuthorization() {
        mainPage.clickLoginButton();
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();
    }

    @Description("Тестирование перехода в личный кабинет по клику на Личный кабинет")
    @Test
    public void goToAccountFromMainPageTest() {
        userAuthorization();
        mainPage.clickPersonalAccountButton();
        assertEquals("URL после входа в аккаунт и повторного клика по кнопке «Личный кабинет» должен быть переход на страницу профиля", LOGIN_URL, driver.getCurrentUrl());
    }

    @Description("Переход из личного кабинета  по клику на Конструктор")
    @Test
    public void constructorClickTest() {
        userAuthorization();
        mainPage.clickPersonalAccountButton();
        profilePage.clickConstructorButton();
        assertEquals("URL после клика по кнопке Конструктор из личного кабинета должен быть главной страницей", MAIN_PAGE, driver.getCurrentUrl());
    }

    @Description("Переход из личного кабинета на логотип Stellar Burgers")
    @Test
    public void logoClickTest() {
        userAuthorization();
        mainPage.clickPersonalAccountButton();
        profilePage.clickLogoButton();
        assertEquals("URL после клика по логотипу Stellar Burgers должен быть главной страницей", MAIN_PAGE, driver.getCurrentUrl());
    }

    @Description("Выход по кнопке «Выйти» в личном кабинете")
    @Test
    public void logoutTest() {
        userAuthorization();
        mainPage.clickPersonalAccountButton();
        profilePage.clickLogoutButton();
        loginPage.waitForLoadLoginPage();
        boolean isLoginHeader = loginPage.isEnterHeaderVisible();
        assertTrue("Кнопка войти отображается на странице.", isLoginHeader);
        System.out.println("Пользователь Вышел из аккаунта");
    }

}
