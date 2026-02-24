package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    By txtUsername = By.name("username");
    By txtPassword = By.name("password");
    By btnLogin = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://localhost:9090/login");
    }

    public void login(String username, String password) {
        driver.findElement(txtUsername).sendKeys(username);
        driver.findElement(txtPassword).sendKeys(password);
        driver.findElement(btnLogin).click();
    }
}