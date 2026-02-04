package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    WebDriver driver;
    WebDriverWait wait;

    By txtFullname = By.name("fullname");
    By txtUsername = By.name("username");
    By txtEmail = By.name("email");
    By txtPassword = By.id("password");
    By txtConfirmPassword = By.id("confirmPassword");
    By btnRegister = By.cssSelector("button[type='submit']");
    By alertMessage = By.cssSelector(".alert");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("http://localhost:9090/register");
    }

    public void enterFullname(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtFullname))
                .sendKeys(v);
    }

    public void enterUsername(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtUsername))
                .sendKeys(v);
    }

    public void enterEmail(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtEmail))
                .sendKeys(v);
    }

    public void enterPassword(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtPassword))
                .sendKeys(v);
    }

    public void enterConfirmPassword(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtConfirmPassword))
                .sendKeys(v);
    }

    public void clickRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(btnRegister))
                .click();
    }

    public void register(String f, String u, String e, String p, String cp) {
        enterFullname(f);
        enterUsername(u);
        enterEmail(e);
        enterPassword(p);
        enterConfirmPassword(cp);
        clickRegister();
    }

    public String getAlertMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(alertMessage))
                .getText();
    }
}