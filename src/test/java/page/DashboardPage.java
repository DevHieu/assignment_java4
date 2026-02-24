package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class DashboardPage {

    WebDriver driver;
    WebDriverWait wait;

    By totalVideo = By.id("totalVideo");
    By totalUser = By.id("totalUser");
    By totalLike = By.id("totalLike");
    By totalShare = By.id("totalShare");

    By top10Rows = By.cssSelector("#top10Table tbody tr");
    By bannerRows = By.cssSelector("#bannerTable tbody tr");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("http://localhost:9090/admin/home");
    }

    private int getNumber(By locator) {
        return Integer.parseInt(
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                        .getText()
                        .trim()
        );
    }

    public int getTotalVideo() { return getNumber(totalVideo); }
    public int getTotalUser() { return getNumber(totalUser); }
    public int getTotalLike() { return getNumber(totalLike); }
    public int getTotalShare() { return getNumber(totalShare); }

    public int getTop10Count() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(top10Rows));
        return driver.findElements(top10Rows).size();
    }

    public boolean isTop10SortedDesc() {
        List<WebElement> rows = driver.findElements(top10Rows);

        int previous = Integer.MAX_VALUE;

        for (WebElement row : rows) {
            int like = Integer.parseInt(
                    row.findElement(By.className("like-count"))
                            .getText()
                            .trim()
            );

            if (like > previous) return false;
            previous = like;
        }
        return true;
    }

    public int getBannerVideoCount() {
        return driver.findElements(bannerRows).size();
    }
}