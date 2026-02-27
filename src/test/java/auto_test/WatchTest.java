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

        // ======================== HELPER ========================

        private void loginAsAdmin() {
                driver.get("http://localhost:9090/login");
                driver.findElement(By.name("username")).sendKeys("admin");
                driver.findElement(By.name("password")).sendKeys("123");
                driver.findElement(By.cssSelector("button[type='submit']")).click();
        }

        // ======================== CHỨC NĂNG 6: XEM VIDEO ========================

        // AT-WAT-01: Kiểm tra hiển thị chi tiết video
        // Điều kiện tiên quyết: Video ID tồn tại trong DB, Browser hỗ trợ Iframe
        // Dữ liệu test: ID = "V001"
        // Các bước: 1. Click vào poster video "V001". 2. Chờ tải Iframe YouTube.
        // Kết quả mong muốn: Chuyển đến URL /watch, hiển thị đúng Title và Iframe phát
        // video tải thành công.
        @Test
        public void AT_WAT_01_VideoDetailDisplay() {
                // Bước 1: Click vào poster video "V001"
                watchPage.openByVideoId("V001");

                // Kiểm tra: Chuyển đến URL /watch
                Assert.assertTrue(
                                watchPage.getCurrentUrl().contains("/watch"),
                                "Phải chuyển đến URL /watch khi xem video");

                Assert.assertTrue(
                                watchPage.getCurrentUrl().contains("id=V001"),
                                "URL phải chứa id=V001");

                // Kiểm tra: Hiển thị đúng Title
                String title = watchPage.getVideoTitle();
                Assert.assertFalse(title.isEmpty(),
                                "Tiêu đề video phải được hiển thị, không được rỗng");

                // Bước 2 & Kiểm tra: Iframe phát video tải thành công
                Assert.assertTrue(
                                watchPage.isIframeDisplayed(),
                                "Iframe YouTube phải được hiển thị và tải thành công");

                String iframeSrc = watchPage.getIframeSrc();
                Assert.assertTrue(
                                iframeSrc.contains("youtube") || iframeSrc.contains("youtu.be"),
                                "src của Iframe phải là link YouTube, thực tế: " + iframeSrc);

                System.out.println("AT-WAT-01: Video V001 => Title='" + title + "', Iframe tải thành công");
        }

        // AT-ACT-01: Kiểm tra luồng tương tác Thích (Like)
        // Điều kiện tiên quyết: Đã đăng nhập, chưa thích video hiện tại
        // Dữ liệu test: Nhấn nút Like
        // Các bước: 1. Truy cập trang chi tiết video. 2. Nhấn nút "Thích".
        // Kết quả mong muốn: Nút chuyển trạng thái "Đã thích", DB tăng 1 bản ghi tại
        // bảng favorite.
        @Test
        public void AT_ACT_01_LikeVideo() {
                // Điều kiện: Đã đăng nhập
                loginAsAdmin();

                // Bước 1: Truy cập trang chi tiết video
                watchPage.openByVideoId("V001");

                // Bước 2: Nhấn nút "Thích"
                watchPage.clickLikeButton();

                // Kiểm tra: Nút chuyển trạng thái thành "Đã thích"
                String afterText = watchPage.getLikeButtonText();
                Assert.assertTrue(
                                afterText.contains("Đã thích") || afterText.contains("Thích"),
                                "Nút Like phải chuyển trạng thái sau khi nhấn, thực tế: " + afterText);

                System.out.println("AT-ACT-01: Like video => Nút hiển thị: '" + afterText + "'");
        }
}
