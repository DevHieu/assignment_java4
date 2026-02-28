package auto_test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.LoginPage;
import page.StatisticsPage;

public class StatisticsTest extends BaseTest {
    StatisticsPage statsPage;

    @BeforeMethod
    public void setup() {
        new LoginPage(driver).open();
        new LoginPage(driver).login("admin", "123");
        
        statsPage = new StatisticsPage(driver);
        statsPage.open();
    }

    // TEST AUTO 1: STA-001 - Kiểm tra giao diện mặc định (Tab 1)
    @Test
    public void STA_001_VerifyDefaultTabActive() {
        Assert.assertTrue(statsPage.isTab1Active(), "Lỗi: Mặc định Tab 1 phải được kích hoạt.");
        Assert.assertTrue(statsPage.getFavoritesCount() >= 0, "Bảng Favorites phải hiển thị.");
    }

    // TEST AUTO 2: STA-009 - Kiểm tra chức năng chuyển đổi giữa các Tab
    @Test
    public void STA_009_VerifyTabSwitchingInteraction() {
        statsPage.clickTab2();
        Assert.assertTrue(statsPage.isTab2Active(), "Lỗi: Tab 2 không được kích hoạt sau khi click.");
        statsPage.clickTab3();
        Assert.assertTrue(statsPage.isTab3Active(), "Lỗi: Tab 3 không được kích hoạt sau khi click.");
    }
}