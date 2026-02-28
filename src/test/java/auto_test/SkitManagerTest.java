package auto_test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.LoginPage;
import page.SkitManagerPage;

public class SkitManagerTest extends BaseTest {

    SkitManagerPage skit;

    @BeforeMethod
    public void setupTest() {

        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("admin", "123");

        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(d -> !d.getCurrentUrl().contains("/login"));

        System.out.println("After login: " + driver.getCurrentUrl());

        skit = new SkitManagerPage(driver);
        skit.open();

        System.out.println("After open skit: " + driver.getCurrentUrl());
    }

    // SKI-AT-001 Admin thêm video mới thành công
    @Test
    public void SKI_AT_001_AddVideoSuccess() {

        String id = "AUTO_" + System.currentTimeMillis();

        skit.addVideo(id, "Auto Test Video");

        Assert.assertTrue(skit.isSuccessDisplayed());
    }

    // SKI-AT-002 Admin xóa video thành công
    @Test
    public void SKI_AT_002_DeleteVideoSuccess() {

        String id = skit.getFirstVideoId();

        skit.deleteFirstVideo();

        Assert.assertFalse(skit.isVideoPresent(id));
    }
}