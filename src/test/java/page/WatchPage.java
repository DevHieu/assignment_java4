package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WatchPage {

    WebDriver driver;
    WebDriverWait wait;

    By videoTitle = By.cssSelector(".video-info h1.h3");
    By videoDescription = By.cssSelector(".video-description p.mb-2");
    By youtubeIframe = By.cssSelector("iframe[src*='youtube']");
    By viewsCountStr = By.xpath("//i[contains(@class, 'fa-eye')]/parent::span");

    By recommendedItems = By.cssSelector(".suggested-video-item");

    By btnLike = By.cssSelector(".like-btn");

    By btnShare = By.cssSelector("button[data-bs-target='#share']");
    By txtShareEmail = By.id("emailInput");
    By btnSubmitShare = By.cssSelector("form[action='/share-video'] button[type='submit']");

    By alertMessage = By.cssSelector(".alert");

    By linkHistory = By.xpath("//a[contains(@href,'/history')]");
    By historyItems = By.cssSelector(".video-item");

    public WatchPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openByVideoId(String videoId) {
        driver.get("http://localhost:9090/watch?id=" + videoId);
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public void clickLikeButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btnLike)).click();
        try {
            Thread.sleep(500);
        } catch (Exception ignored) {
        }
    }

    public void clickShareButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btnShare)).click();
    }

    public void enterShareEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtShareEmail))
                .sendKeys(email);
    }

    public void clickSubmitShare() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmitShare)).click();
    }

    public void shareVideo(String email) {
        clickShareButton();
        enterShareEmail(email);
        clickSubmitShare();
    }

    public void goToHistory() {
        driver.get("http://localhost:9090/history");
    }

    public String getVideoTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(videoTitle))
                .getText().trim();
    }

    public String getVideoDescription() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(videoDescription))
                .getText().trim();
    }

    public long getViews() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(viewsCountStr)).getText();
        return Long.parseLong(text.replaceAll("[^0-9]", ""));
    }

    public boolean isIframeDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(youtubeIframe))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getIframeSrc() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(youtubeIframe))
                .getAttribute("src");
    }

    public String getLikeButtonText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(btnLike))
                .getText().trim();
    }

    public List<WebElement> getRecommendedItems() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(recommendedItems));
    }

    public String getShareMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(alertMessage)).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public List<WebElement> getHistoryItems() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(historyItems));
    }
}
