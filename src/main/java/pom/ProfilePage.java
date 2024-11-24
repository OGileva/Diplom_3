package pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private WebDriver driver;
    private final By constructorButton = By.xpath(".//p[text() = 'Конструктор']");
    private final By profileButton = By.xpath(".//a[text() = 'Профиль']");
    private final By logoutButton = By.xpath(".//nav[starts-with(@class, 'Account_nav')]/ul/li/button");
    private final By logoButton = By.xpath(".//header/nav/div");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with(@class, 'Modal_modal_overlay')]");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitLoadingProfilePage() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(profileButton));
    }


    @Step("Нажать на Конструктор")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Нажать на кнопку Выход")
    public void clickLogoutButton() {
        waitButtonIsClickable();
        driver.findElement(logoutButton).click();
    }

    @Step("Нажать на логотип Stellar Burgers")
    public void clickLogoButton() {
        driver.findElement(logoButton).click();
    }

    @Step("Подождать пока кнопка Выти станет кликабельной")
    public void waitButtonIsClickable() {
        new WebDriverWait(driver,Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOf(driver.findElement(modalOverlay)));}
}
