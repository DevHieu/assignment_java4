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
        new LoginPage(driver).open();
        new LoginPage(driver).login("admin", "123");

        dashboard = new DashboardPage(driver);
        dashboard.open();
    }

    // TEST AUTO 1: DBA-007 - Kiểm tra thông tin hiển thị bảng Top 10
    @Test
    public void DBA_007_VerifyTop10TableDisplay() {
        int rows = dashboard.getTop10Count();
        Assert.assertTrue(rows >= 0 && rows <= 10, "Bảng Top 10 hiển thị sai số lượng dòng.");
        Assert.assertTrue(dashboard.isTop10SortedDesc(), "Lỗi: Top 10 không được sắp xếp giảm dần theo lượt Like.");
    }

    // TEST AUTO 2: DBA-008 - Kiểm tra đồng bộ số liệu sau khi làm mới trang
    @Test
    public void DBA_008_VerifyDataConsistencyOnRefresh() {
        int totalLikeBefore = dashboard.getTotalLike();
        driver.navigate().refresh();
        int totalLikeAfter = dashboard.getTotalLike();
        Assert.assertEquals(totalLikeBefore, totalLikeAfter, "Lỗi: Số liệu bị thay đổi sau khi refresh trang.");
    }
}