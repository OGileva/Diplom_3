import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pom.MainPage;
import settings.WebDriverFactory;

public class ConstruktorTest {
    private WebDriver driver;
    private MainPage mainPage;

    @Step("Подготовка данных")
    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        driver = WebDriverFactory.getDriver(browser);
        driver.get("https://stellarburgers.nomoreparties.site/");
        driver.manage().window().maximize();

        mainPage = new MainPage(driver);
    }

    @Step("Закрытие браузера")
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Переход к разделу Булки")
    public void shouldChoiceSectionBuns() {
        mainPage.clickSauceButton();
        mainPage.clickBunsButton();
        boolean isBunsChoiceSectionVisible = mainPage.isChoiceSectionVisible();
        Assert.assertTrue(isBunsChoiceSectionVisible);
    }
    @Test
    @DisplayName("Переход к разделу Соусы")
    public void shouldChoiceSectionSauce() {
        mainPage.clickSauceButton();
        boolean isSauceChoiceSectionVisible = mainPage.isChoiceSectionVisible();
        Assert.assertTrue(isSauceChoiceSectionVisible);
    }
    @Test
    @DisplayName("Переход к разделу Начинки")
    public void shouldChoiceSectionFilling() {
        mainPage.clickFillingButton();
        boolean isFillingChoiceSectionVisible = mainPage.isChoiceSectionVisible();
        Assert.assertTrue(isFillingChoiceSectionVisible);
    }

}
