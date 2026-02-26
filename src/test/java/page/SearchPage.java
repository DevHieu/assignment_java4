package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchPage {

    WebDriver driver;
    WebDriverWait wait;

    By txtSearch = By.name("query");
    By btnSearch = By.cssSelector("form[action='/search'] button[type='submit']");

    By videoItems = By.cssSelector(".video-card");
    By videoTitles = By.cssSelector(".video-card .card-title a");
    By noResultMessage = By.xpath("//p[contains(text(),'Tìm được 0 video liên quan')]");

    By btnSortDropdown = By.cssSelector(".sort-dropdown .dropdown-toggle");
    By btnManyViews = By.cssSelector("a[href*='/search/viewHtoL']");
    By btnFewViews = By.cssSelector("a[href*='/search/viewLtoH']");
    By btnManyLikes = By.cssSelector("a[href*='/search/likeHtoL']");
    By btnAZ = By.cssSelector("a[href*='/search/AZ']");

    By btnNextPage = By.cssSelector(".pagination .next");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("http://localhost:9090/search");
    }

    public void open(String keyword) {
        driver.get("http://localhost:9090/search?query=" + keyword);
    }

    public void enterKeyword(String keyword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtSearch))
                .sendKeys(keyword);
    }

    public void clearKeyword() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtSearch))
                .clear();
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSearch))
                .click();
    }

    public void search(String keyword) {
        clearKeyword();
        enterKeyword(keyword);
        clickSearch();
    }

    public void openSortDropdown() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(btnSortDropdown));
        if ("false".equals(dropdown.getAttribute("aria-expanded"))) {
            dropdown.click();
        }
    }

    public void clickManyViews() {
        openSortDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(btnManyViews)).click();
    }

    public void clickFewViews() {
        openSortDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(btnFewViews)).click();
    }

    public void clickManyLikes() {
        openSortDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(btnManyLikes)).click();
    }

    public void clickAZ() {
        openSortDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(btnAZ)).click();
    }

    public void clickNextPage() {
        wait.until(ExpectedConditions.elementToBeClickable(btnNextPage)).click();
    }

    public List<WebElement> getVideoItems() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(videoItems));
    }

    public List<WebElement> getVideoTitles() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(videoTitles));
    }

    public String getNoResultMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(noResultMessage)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isNoResultMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(noResultMessage))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public long getViewsAt(int index) {
        List<WebElement> items = getVideoItems();
        WebElement viewSpan = items.get(index).findElement(By.xpath(".//span[i[contains(@class,'fa-eye')]]"));
        String raw = viewSpan.getText().trim();
        return Long.parseLong(raw.replaceAll("[^0-9]", ""));
    }

    public long getLikesAt(int index) {
        List<WebElement> items = getVideoItems();
        WebElement likeSpan = items.get(index)
                .findElement(By.xpath(".//span[contains(@class,'like-btn')][1]//span[@class='like-count']"));
        String raw = likeSpan.getText().trim();
        return Long.parseLong(raw.replaceAll("[^0-9]", ""));
    }

    public String getTitleAt(int index) {
        List<WebElement> titles = getVideoTitles();
        return titles.get(index).getText().trim();
    }
}
