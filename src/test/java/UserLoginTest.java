import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import model.UserApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pom.LoginPage;
import pom.MainPage;
import pom.RecoverPasswordPage;
import pom.RegistrationPage;
import settings.WebDriverFactory;

import static model.Constants.MAIN_PAGE;
import static org.junit.Assert.assertTrue;

@DisplayName("Авторизация пользователя")
public class UserLoginTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private RecoverPasswordPage recoverPasswordPage;
    private User user;
    private UserApi userApi;
    private String accessToken;

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
    }

    @Step("Удаление данных и закрытие браузера")
    @After
    public void tearDown() {
        userApi = new UserApi();
        userApi.deleteUser(accessToken);
        driver.quit();
    }

    @DisplayName("Вход по кнопке Войти в аккаунт на главной")
    @Test
    public void loginFromMainPageTest() {
        mainPage.clickLoginButton();
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();
        boolean isGetOrderButtonVisible = mainPage.isCreateOrderButtonVisible();
        assertTrue("Вместо кнопки Войти появляется кнопка Оформить заказ на главной странице", isGetOrderButtonVisible);
        System.out.println("Пользователь с email: "+ user.getEmail() + " авторизован");
    }

    @DisplayName("Вход через кнопку Личный кабинет")
    @Test
    public void loginFromPersonalAccountButton() {
        mainPage.clickPersonalAccountButton();
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();
        boolean isGetOrderButtonVisible = mainPage.isCreateOrderButtonVisible();
        assertTrue("Вместо кнопки Войти появляется кнопка Оформить заказ на главной странице", isGetOrderButtonVisible);
        System.out.println("Пользователь с email: "+ user.getEmail() + " авторизован");
    }

    @DisplayName("Вход через форму регистрации")
    @Test
    public void loginFromRegistrationPageTest() {
        mainPage.clickLoginButton();
        loginPage.clickRegistrationButton();
        registrationPage.clickLoginButton();
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();
        boolean isGetOrderButtonVisible = mainPage.isCreateOrderButtonVisible();
        assertTrue("Вместо кнопки Войти появляется кнопка Оформить заказ на главной странице", isGetOrderButtonVisible);
        System.out.println("Пользователь с email: "+ user.getEmail() + " авторизован");
    }

    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Test
    public void loginFromPasswordResetPageTest() {
        mainPage.clickLoginButton();
        loginPage.clickRecoverButton();
        recoverPasswordPage.clickLoginButton();
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();
        boolean isGetOrderButtonVisible = mainPage.isCreateOrderButtonVisible();
        assertTrue("Вместо кнопки Войти появляется кнопка Оформить заказ на главной странице", isGetOrderButtonVisible);
        System.out.println("Пользователь с email: "+ user.getEmail() + " авторизован");
    }
}
