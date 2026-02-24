package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class StatisticsPage {
    WebDriver driver;
    WebDriverWait wait;

    By tab1Btn = By.id("favorites-tab");
    By tab2Btn = By.id("favorite-users-tab");
    By tab3Btn = By.id("shared-friends-tab");

    By favTitleInput = By.name("favTitle");
    By shareTitleInput = By.name("shareTitle");

    By favSearchBtn = By.cssSelector("#favorite-users button[type='submit']");
    By shareSearchBtn = By.cssSelector("#shared-friends button[type='submit']");

    By favoritesRows = By.cssSelector("#favorites tbody tr");
    By favUsersRows = By.cssSelector("#favorite-users tbody tr");
    By sharedFriendsRows = By.cssSelector("#shared-friends tbody tr");

    public StatisticsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("http://localhost:9090/admin/statistics");
    }

    public void clickTab1() { wait.until(ExpectedConditions.elementToBeClickable(tab1Btn)).click(); }
    public void clickTab2() { wait.until(ExpectedConditions.elementToBeClickable(tab2Btn)).click(); }
    public void clickTab3() { wait.until(ExpectedConditions.elementToBeClickable(tab3Btn)).click(); }

    public boolean isTab1Active() { return driver.findElement(tab1Btn).getAttribute("class").contains("active"); }
    public boolean isTab2Active() { return driver.findElement(tab2Btn).getAttribute("class").contains("active"); }
    public boolean isTab3Active() { return driver.findElement(tab3Btn).getAttribute("class").contains("active"); }

    public void searchFavorite(String title) {
        driver.findElement(favTitleInput).clear();
        driver.findElement(favTitleInput).sendKeys(title);
        driver.findElement(favSearchBtn).click();
    }

    public void searchShared(String title) {
        driver.findElement(shareTitleInput).clear();
        driver.findElement(shareTitleInput).sendKeys(title);
        driver.findElement(shareSearchBtn).click();
    }

    public int getRowsCount(By locator) {
        // Đợi cho bảng hiển thị
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        List<WebElement> rows = driver.findElements(locator);
        if (rows.size() == 1 && rows.get(0).getText().contains("Không có dữ liệu")) {
            return 0;
        }
        return rows.size();
    }

    public int getFavoritesCount() { return getRowsCount(favoritesRows); }
    public int getFavUsersCount() { return getRowsCount(favUsersRows); }
    public int getSharedCount() { return getRowsCount(sharedFriendsRows); }

    public String getFirstRowData(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }
}