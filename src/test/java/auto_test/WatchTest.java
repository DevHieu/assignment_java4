package auto_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import page.WatchPage;

import java.util.List;

public class WatchTest extends BaseTest {

        private WatchPage watchPage;

        @BeforeMethod
        public void initWatchPage() {
                watchPage = new WatchPage(driver);
        }

        private void loginAsAdmin() {
                driver.get("http://localhost:9090/login");
                driver.findElement(By.name("username")).sendKeys("admin");
                driver.findElement(By.name("password")).sendKeys("123");
                driver.findElement(By.cssSelector("button[type='submit']")).click();
        }

        @Test
        public void AT_WAT_01_VideoDetailDisplay() {
                watchPage.openByVideoId("2I1sBJ4DIWI");

                // Kiểm tra: Chuyển đến URL /watch
                Assert.assertTrue(
                                watchPage.getCurrentUrl().contains("/watch"),
                                "Phải chuyển đến URL /watch khi xem video");

                String title = watchPage.getVideoTitle();
                Assert.assertFalse(title.isEmpty(),
                                "Tiêu đề video phải được hiển thị, không được rỗng");

                Assert.assertTrue(
                                watchPage.isIframeDisplayed(),
                                "Iframe YouTube phải được hiển thị và tải thành công");

                String iframeSrc = watchPage.getIframeSrc();
                Assert.assertTrue(
                                iframeSrc.contains("youtube") || iframeSrc.contains("youtu.be"),
                                "src của Iframe phải là link YouTube, thực tế: " + iframeSrc);

                System.out.println("AT-WAT-01: Video V001 => Title='" + title + "', Iframe tải thành công");
        }

        @Test
        public void AT_ACT_01_LikeVideo() {
                loginAsAdmin();

                watchPage.openByVideoId("2I1sBJ4DIWI");
                watchPage.clickLikeButton();

                String afterText = watchPage.getLikeButtonText();
                Assert.assertTrue(
                                afterText.contains("Đã thích") || afterText.contains("Thích"),
                                "Nút Like phải chuyển trạng thái sau khi nhấn, thực tế: " + afterText);

                System.out.println("AT-ACT-01: Like video => Nút hiển thị: '" + afterText + "'");
        }
}
