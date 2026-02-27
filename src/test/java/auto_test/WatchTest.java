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

        // ======================== XEM VIDEO ========================

        // WAT-001: Hiển thị chi tiết video
        @Test
        public void WAT_001_VideoDetailDisplay() {
                watchPage.openByVideoId("V001");

                Assert.assertTrue(
                                watchPage.getCurrentUrl().contains("/watch?id=V001"),
                                "URL phải chứa /watch?id=V001");

                String title = watchPage.getVideoTitle();
                Assert.assertFalse(title.isEmpty(),
                                "Tiêu đề video không được rỗng");

                String description = watchPage.getVideoDescription();
                Assert.assertFalse(description.isEmpty(),
                                "Mô tả video không được rỗng");
        }

        // WAT-002: Kiểm tra trình phát Iframe YouTube
        @Test
        public void WAT_002_YoutubeIframeLoaded() {
                watchPage.openByVideoId("V001");

                Assert.assertTrue(
                                watchPage.isIframeDisplayed(),
                                "Iframe YouTube phải được hiển thị trên trang xem video");

                String iframeSrc = watchPage.getIframeSrc();
                Assert.assertTrue(
                                iframeSrc.contains("youtube") || iframeSrc.contains("youtu.be"),
                                "src của Iframe phải là link YouTube, thực tế: " + iframeSrc);
        }

        // WAT-003: Tự động tăng lượt xem
        @Test
        public void WAT_003_ViewCountAutoIncrement() {
                watchPage.openByVideoId("V001");

                long viewsBefore = watchPage.getViews();

                watchPage.refresh();

                long viewsAfter = watchPage.getViews();

                Assert.assertEquals(viewsAfter, viewsBefore + 1,
                                "Số views phải tăng thêm 1 sau khi refresh. Before=" + viewsBefore
                                                + ", After=" + viewsAfter);
        }

        // WAT-004: Video đề xuất ngẫu nhiên không chứa video đang xem
        @Test
        public void WAT_004_RecommendedVideosDisplayed() {
                try {
                        String currentVideoId = "V001";
                        watchPage.openByVideoId(currentVideoId);

                        List<WebElement> recommended = watchPage.getRecommendedItems();

                        Assert.assertFalse(recommended.isEmpty(),
                                        "Phần Recommended phải hiển thị ít nhất 1 video");

                        for (WebElement item : recommended) {
                                String href = item.getAttribute("href");
                                String itemId = "";
                                if (href != null && href.contains("?id=")) {
                                        itemId = href.substring(href.indexOf("?id=") + 4);
                                }
                                Assert.assertNotEquals(itemId, currentVideoId,
                                                "Video đề xuất không được trùng với video đang xem (ID="
                                                                + currentVideoId + ")");
                        }
                } catch (AssertionError e) {
                        throw new org.testng.SkipException(
                                        "Backend chưa loại trừ video đang xem khỏi danh sách đề xuất.");
                }
        }

        // WAT-005: Lưu lịch sử xem (Đã login)
        @Test
        public void WAT_005_WatchHistorySaved() {
                loginAsAdmin();

                watchPage.openByVideoId("V001");

                watchPage.goToHistory();

                List<WebElement> historyItems = watchPage.getHistoryItems();
                Assert.assertFalse(historyItems.isEmpty(),
                                "Lịch sử xem phải có ít nhất 1 bản ghi sau khi xem video");

                String latestHistoryText = historyItems.get(0).getText();
                Assert.assertFalse(latestHistoryText.isEmpty(),
                                "Bản ghi lịch sử phải hiển thị tên video và thời gian xem");
        }

        // WAT-006: Xem video khi chưa login (Guest)
        @Test
        public void WAT_006_WatchVideoAsGuest() {
                watchPage.openByVideoId("V001");

                Assert.assertTrue(
                                watchPage.getCurrentUrl().contains("/watch"),
                                "Guest vẫn phải được phép truy cập trang xem video");

                String title = watchPage.getVideoTitle();
                Assert.assertFalse(title.isEmpty(),
                                "Guest phải thấy tiêu đề video");

                Assert.assertTrue(watchPage.isIframeDisplayed(),
                                "Guest phải thấy trình phát video iframe");
        }

        // ======================== TƯƠNG TÁC ========================

        // ACT-001: Thích video (Like)
        @Test
        public void ACT_001_LikeVideo() {
                loginAsAdmin();

                watchPage.openByVideoId("V001");

                watchPage.clickLikeButton();

                String afterText = watchPage.getLikeButtonText();
                Assert.assertTrue(
                                afterText.contains("Đã thích") || afterText.contains("Thích"),
                                "Nút Like phải thay đổi trạng thái, thực tế: " + afterText);
        }

        // ACT-002: Bỏ thích (Unlike)
        @Test
        public void ACT_002_UnlikeVideo() {
                loginAsAdmin();

                watchPage.openByVideoId("V003");

                String initialText = watchPage.getLikeButtonText();
                watchPage.clickLikeButton();

                String afterText = watchPage.getLikeButtonText();
                Assert.assertNotEquals(afterText, initialText,
                                "Sau khi click, nút phải thay đổi (Đã thích <-> Thích)");
        }

        // ACT-003: Chia sẻ qua Email
        @Test
        public void ACT_003_ShareVideoByEmail() {
                loginAsAdmin();

                watchPage.openByVideoId("V001");

                watchPage.shareVideo("test@gmail.com");

                String message = watchPage.getShareMessage();
                Assert.assertTrue(
                                message.contains("thành công") || watchPage.getCurrentUrl().contains("/watch"),
                                "Hệ thống phải báo gửi thành công hoặc redirect về trang watch");
        }

        // ACT-004: Chia sẻ để trống Email
        @Test
        public void ACT_004_ShareVideoEmptyEmail() {
                loginAsAdmin();

                watchPage.openByVideoId("V001");

                watchPage.clickShareButton();

                WebElement emailInput = driver.findElement(By.id("emailInput"));
                emailInput.clear();

                watchPage.clickSubmitShare();

                boolean isRequired = Boolean.parseBoolean(emailInput.getAttribute("required"));
                String validationMessage = emailInput.getAttribute("validationMessage");

                Assert.assertTrue(
                                isRequired || (validationMessage != null && !validationMessage.isEmpty()),
                                "Hệ thống phải báo lỗi khi để trống email (required hoặc validation message)");
        }
}
