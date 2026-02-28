package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SkitManagerPage {

    WebDriver driver;
    WebDriverWait wait;

    public SkitManagerPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By btnAddSkit = By.cssSelector("button[data-bs-target='#skitModal']");
    By modal = By.id("skitModal");
    By txtYoutubeId = By.id("youtubeId");
    By txtTitle = By.name("title");
    By btnSave = By.cssSelector("#skitModal button[type='submit']");
    By successMsg = By.cssSelector(".alert-success");
    By btnDeleteFirst = By.cssSelector("table tbody tr:first-child button.btn-danger");
    By firstVideoId = By.cssSelector("table tbody tr:first-child td:first-child");

    public void open() {
        driver.get("http://localhost:9090/admin/skit");
        wait.until(d -> d.getCurrentUrl().contains("/admin/skit"));
    }

    // ---------- ADD VIDEO ----------

    public void addVideo(String id, String title) {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddSkit)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(modal));

        driver.findElement(txtYoutubeId).clear();
        driver.findElement(txtYoutubeId).sendKeys(id);

        driver.findElement(txtTitle).clear();
        driver.findElement(txtTitle).sendKeys(title);

        wait.until(ExpectedConditions.elementToBeClickable(btnSave)).click();
    }

    public boolean isSuccessDisplayed() {
        return wait.until(d -> driver.findElements(successMsg).size() > 0);
    }

    // ---------- DELETE VIDEO ----------

    public String getFirstVideoId() {
        return wait.until(d ->
                driver.findElement(firstVideoId).getText().trim()
        );
    }

    public void deleteFirstVideo() {
        By firstRow = By.cssSelector("table tbody tr:first-child");
        WebElement row = wait.until(d -> driver.findElement(firstRow));

        row.findElement(By.cssSelector("button.btn-danger")).click();

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception ignored) {}

        // Chờ row cũ biến mất (stale)
        wait.until(ExpectedConditions.stalenessOf(row));
    }

    public boolean isVideoPresent(String id) {
        return driver.getPageSource().contains(id);
    }
}