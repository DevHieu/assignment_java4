package auto_test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.LoginPage;
import page.DashboardPage;

public class DashboardTest extends BaseTest {

    DashboardPage dashboard;

    @BeforeMethod
    public void setupTest() {

        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("admin", "123");

        dashboard = new DashboardPage(driver);
        dashboard.open();
    }

    // DBA-001
    @Test
    public void DBA_001_LoadTotalStatistics() {
        Assert.assertTrue(dashboard.getTotalVideo() >= 0);
        Assert.assertTrue(dashboard.getTotalUser() >= 0);
        Assert.assertTrue(dashboard.getTotalLike() >= 0);
        Assert.assertTrue(dashboard.getTotalShare() >= 0);
    }

    // DBA-002
    @Test
    public void DBA_002_Top10VideoSorted() {
        Assert.assertTrue(dashboard.getTop10Count() <= 10);
        Assert.assertTrue(dashboard.isTop10SortedDesc());
    }

    // DBA-003
    @Test
    public void DBA_003_LoadBannerVideos() {
        Assert.assertTrue(dashboard.getBannerVideoCount() > 0);
    }

    // DBA-004
    @Test
    public void DBA_004_HandleEmptyDB() {
        Assert.assertTrue(dashboard.getTotalVideo() >= 0);
    }

    // DBA-010
    @Test
    public void DBA_010_LimitTop10WhenLessData() {
        Assert.assertTrue(dashboard.getTop10Count() <= 10);
    }
}