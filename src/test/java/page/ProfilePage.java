package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProfilePage {
    WebDriver driver;
    WebDriverWait wait;

    By profileTabBtn = By.id("profile-tab-button");
    By passwordTabBtn = By.id("password-tab-button");

    By fullnameInput = By.id("fullname");
    By emailInput = By.id("email");
    By saveProfileBtn = By.id("btn-save-profile");

    By avatarFileInput = By.id("avatar-file");
    By deleteAvatarBtn = By.id("btn-delete-avatar");

    By currentPwdInput = By.id("current-password");
    By newPwdInput = By.id("new-password");
    By confirmPwdInput = By.id("confirm-password");
    By updatePwdBtn = By.id("btn-update-password");

    By alertMsg = By.className("alert");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    public void open() {
        driver.get("http://localhost:9090/profile");
    }

    private void clickJS(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void clickProfileTab() {
        clickJS(profileTabBtn);
    }

    public void clickPasswordTab() {
        clickJS(passwordTabBtn);
    }

    public void updateProfile(String fullname, String email) {
        clickProfileTab();

        WebElement fn = wait.until(ExpectedConditions.visibilityOfElementLocated(fullnameInput));
        fn.clear();
        fn.sendKeys(fullname);

        WebElement em = driver.findElement(emailInput);
        em.clear();
        em.sendKeys(email);

        clickJS(saveProfileBtn);
    }

    public void updatePassword(String current, String newPwd, String confirm) {
        clickPasswordTab();
        WebElement currentInp = wait.until(ExpectedConditions.visibilityOfElementLocated(currentPwdInput));
        currentInp.clear();
        currentInp.sendKeys(current);

        driver.findElement(newPwdInput).clear();
        driver.findElement(newPwdInput).sendKeys(newPwd);

        driver.findElement(confirmPwdInput).clear();
        driver.findElement(confirmPwdInput).sendKeys(confirm);

        clickJS(updatePwdBtn);
    }

    public void uploadAvatar(String filePath) {
        WebElement input = wait.until(
                ExpectedConditions.presenceOfElementLocated(avatarFileInput)
        );
        input.sendKeys(filePath);
    }
    public void deleteAvatar() {
        clickJS(deleteAvatarBtn);
    }

    public String getAlertMessage() {
        try {
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(alertMsg));
            return msg.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
}