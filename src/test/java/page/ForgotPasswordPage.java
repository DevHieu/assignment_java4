package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {
    WebDriver driver;

    // Định vị các phần tử
    By usernameInput = By.name("username");
    By emailInput = By.name("email");
    By btnSubmit = By.cssSelector("button[type='submit']");
    By alertMessage = By.className("alert");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        // Thay đổi URL cho đúng với project của bạn
        driver.get("http://localhost:9090/forgot_pw");
    }

    public void forgotPassword(String user, String email) {
        // Sử dụng chuỗi rỗng nếu muốn test trường hợp bỏ trống
        driver.findElement(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(user);

        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);

        driver.findElement(btnSubmit).click();
    }

    public String getMessage() {
        try {
            return driver.findElement(alertMessage).getText().trim().split("\n")[0];
        } catch (Exception e) {
            return "";
        }
    }
}