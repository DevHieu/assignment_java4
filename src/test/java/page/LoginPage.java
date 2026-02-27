package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    By txtUsername = By.name("username");
    By txtPassword = By.name("password");
    By chkRemember = By.name("remember");
    By btnLogin = By.id("btnLogin");
    By lblMessage = By.id("message");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://localhost:9090/login");
    }

    public void login(String username, String password) {
        driver.findElement(txtUsername).clear();
        driver.findElement(txtPassword).clear();

        driver.findElement(txtUsername).sendKeys(username);
        driver.findElement(txtPassword).sendKeys(password);
        driver.findElement(btnLogin).click();
    }

    public void loginWithRemember(String username, String password) {
        driver.findElement(chkRemember).click();
        login(username, password);
    }

    public String getErrorMessage() {
        return driver.findElement(lblMessage).getText();
    }
}