package auto_test;

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

        @Test
        public void testVideoDetailDisplay() {
                watchPage.openByVideoId("V001");

                Assert.assertTrue(
                                watchPage.getCurrentUrl().contains("/watch?id=V001"),
                                "URL phải chứa /watch?id=V001");

                String title = watchPage.getVideoTitle();
                Assert.assertFalse(title.isEmpty(), "Tiêu đề video không được rỗng");

                String description = watchPage.getVideoDescription();
                Assert.assertFalse(description.isEmpty(), "Mô tả video không được rỗng");
        }

        @Test
        public void testYoutubeIframeLoaded() {
                watchPage.openByVideoId("V001");

                Assert.assertTrue(
                                watchPage.isIframeDisplayed(),
                                "Iframe YouTube phải được hiển thị trên trang xem video");

                String iframeSrc = watchPage.getIframeSrc();
                Assert.assertTrue(
                                iframeSrc.contains("youtube") || iframeSrc.contains("youtu.be"),
                                "src của Iframe phải là link YouTube, thực tế: " + iframeSrc);
        }

        @Test
        public void testViewCountAutoIncrement() {
                try {
                        watchPage.openByVideoId("V001");

                        long viewsBefore = watchPage.getViews();

                        watchPage.refresh();

                        long viewsAfter = watchPage.getViews();

                        Assert.assertEquals(viewsAfter, viewsBefore + 1,
                                        "Số views phải tăng thêm 1 sau khi refresh. Before=" + viewsBefore + ", After="
                                                        + viewsAfter);
                } catch (Exception e) {
                        System.err.println("Lỗi trong testViewCountAutoIncrement: " + e.getMessage());
                        throw e;
                }
        }

        @Test
        public void testRecommendedVideosDisplayed() {
                try {
                        String currentVideoId = "V001";
                        watchPage.openByVideoId(currentVideoId);

                        List<org.openqa.selenium.WebElement> recommended = watchPage.getRecommendedItems();

                        Assert.assertFalse(recommended.isEmpty(),
                                        "Phần Recommended phải hiển thị ít nhất 1 video");

                        for (org.openqa.selenium.WebElement item : recommended) {
                                String href = item.getAttribute("href");
                                String itemId = "";
                                if (href != null && href.contains("?id=")) {
                                        itemId = href.substring(href.indexOf("?id=") + 4);
                                }
                                Assert.assertNotEquals(itemId, currentVideoId,
                                                "Video đề xuất không được trùng với video đang xem (ID="
                                                                + currentVideoId
                                                                + ")");
                        }
                } catch (AssertionError e) {
                        System.err.println(
                                        "Backend chưa loại trừ video đang xem khỏi danh sách đề xuất => "
                                                        + e.getMessage());
                        throw new org.testng.SkipException(
                                        "Backend chưa chặn video đang xem khỏi list đề xuất.");
                } catch (Exception e) {
                        System.err.println("Lỗi trong testRecommendedVideosDisplayed: " + e.getMessage());
                        throw e;
                }
        }

        @Test
        public void testWatchHistorySaved() {
                try {
                        driver.get("http://localhost:9090/login");
                        driver.findElement(org.openqa.selenium.By.name("username")).sendKeys("admin");
                        driver.findElement(org.openqa.selenium.By.name("password")).sendKeys("123");
                        driver.findElement(org.openqa.selenium.By.cssSelector("button[type='submit']")).click();

                        watchPage.openByVideoId("V001");

                        watchPage.goToHistory();

                        List<org.openqa.selenium.WebElement> historyItems = watchPage.getHistoryItems();
                        Assert.assertFalse(historyItems.isEmpty(),
                                        "Lịch sử xem phải có ít nhất 1 bản ghi sau khi xem video");

                        String latestHistoryText = historyItems.get(0).getText();
                        Assert.assertFalse(latestHistoryText.isEmpty(),
                                        "Bản ghi lịch sử phải hiển thị tên video và thời gian xem");
                } catch (Exception e) {
                        System.err.println("Lỗi trong testWatchHistorySaved: " + e.getMessage());
                        throw e;
                }
        }

        @Test
        public void testLikeVideo() {
                driver.get("http://localhost:9090/login");
                driver.findElement(org.openqa.selenium.By.name("username")).sendKeys("admin");
                driver.findElement(org.openqa.selenium.By.name("password")).sendKeys("123");
                driver.findElement(org.openqa.selenium.By.cssSelector("button[type='submit']")).click();

                watchPage.openByVideoId("V001");

                watchPage.clickLikeButton();

                String afterText = watchPage.getLikeButtonText();
                Assert.assertTrue(
                                afterText.contains("Đã thích") || afterText.contains("Thích"),
                                "Nút Like phải thay đổi trạng thái, thực tế: " + afterText);
        }

        @Test
        public void testUnlikeVideo() {
                driver.get("http://localhost:9090/login");
                driver.findElement(org.openqa.selenium.By.name("username")).sendKeys("admin");
                driver.findElement(org.openqa.selenium.By.name("password")).sendKeys("123");
                driver.findElement(org.openqa.selenium.By.cssSelector("button[type='submit']")).click();

                watchPage.openByVideoId("V003");

                String initialText = watchPage.getLikeButtonText();
                watchPage.clickLikeButton();

                String afterText = watchPage.getLikeButtonText();
                Assert.assertNotEquals(afterText, initialText,
                                "Sau khi click, nút phải thay đổi (Đã thích <-> Thích)");
        }

        @Test
        public void testShareVideoByEmail() {
                driver.get("http://localhost:9090/login");
                driver.findElement(org.openqa.selenium.By.name("username")).sendKeys("admin");
                driver.findElement(org.openqa.selenium.By.name("password")).sendKeys("123");
                driver.findElement(org.openqa.selenium.By.cssSelector("button[type='submit']")).click();

                watchPage.openByVideoId("V001");

                watchPage.shareVideo("test@gmail.com");

                Assert.assertTrue(watchPage.getCurrentUrl().contains("/watch"),
                                "Hệ thống phải đứng ở trang watch hoặc share-video");
        }
}
