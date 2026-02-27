package auto_test;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import page.SearchPage;

import java.util.List;

public class SearchTest extends BaseTest {

        private SearchPage searchPage;

        @BeforeMethod
        public void navigateToSearchPage() {
                searchPage = new SearchPage(driver);
                searchPage.open();
        }

        // ======================== TÌM KIẾM ========================

        // SEA-001: Tìm kiếm từ khóa hợp lệ
        @Test
        public void SEA_001_SearchWithValidKeyword() {
                searchPage.search("Hài");

                List<WebElement> titles = searchPage.getVideoTitles();

                Assert.assertFalse(titles.isEmpty(),
                                "Danh sách video không được rỗng khi tìm 'Hài'");

                for (WebElement title : titles) {
                        Assert.assertTrue(
                                        title.getText().toLowerCase().contains("hài"),
                                        "Tiêu đề video phải chứa 'Hài': " + title.getText());
                }
        }

        // SEA-002: Tìm kiếm chuỗi rỗng
        @Test
        public void SEA_002_SearchWithEmptyKeyword() {
                searchPage.search("");

                List<WebElement> videoItems = searchPage.getVideoItems();

                Assert.assertFalse(videoItems.isEmpty(),
                                "Khi tìm kiếm rỗng, hệ thống phải load toàn bộ danh sách video");
        }

        // SEA-003: Từ khóa không tồn tại
        @Test
        public void SEA_003_SearchWithNonExistentKeyword() {
                searchPage.search("XYZ123");

                boolean isDisplayed = searchPage.isNoResultMessageDisplayed();

                Assert.assertTrue(isDisplayed,
                                "Phải hiển thị thông báo 'Tìm được 0 video liên quan'");
        }

        // SEA-004: Ký tự đặc biệt (Security)
        @Test
        public void SEA_004_SearchWithSpecialCharacters() {
                searchPage.search("<script>alert('XSS')</script>");

                String pageSource = driver.getPageSource();
                Assert.assertFalse(
                                pageSource.contains("<script>alert('XSS')</script>"),
                                "Hệ thống không được render script tag trực tiếp (XSS vulnerability)");

                String currentUrl = searchPage.getCurrentUrl();
                Assert.assertTrue(currentUrl.contains("/search"),
                                "Hệ thống phải xử lý an toàn, không lỗi server, vẫn ở trang search");
        }

        // SEA-004b: SQL Injection
        @Test
        public void SEA_004b_SearchWithSQLInjection() {
                searchPage.search("' OR '1'='1");

                String currentUrl = searchPage.getCurrentUrl();
                Assert.assertTrue(currentUrl.contains("/search"),
                                "Hệ thống phải xử lý an toàn chuỗi SQL injection, không lỗi server");
        }

        // ======================== SẮP XẾP ========================

        // SRT-001: Sắp xếp Views giảm dần
        @Test
        public void SRT_001_SortByMostViews() {
                searchPage.clickManyViews();

                Assert.assertTrue(
                                driver.getCurrentUrl().contains("/search/viewHtoL"),
                                "URL phải chứa /search/viewHtoL");

                List<WebElement> items = searchPage.getVideoItems();
                Assert.assertTrue(items.size() >= 2,
                                "Cần ít nhất 2 video để kiểm tra thứ tự sắp xếp");

                long firstViews = searchPage.getViewsAt(0);
                long secondViews = searchPage.getViewsAt(1);

                Assert.assertTrue(firstViews >= secondViews,
                                "Video đầu tiên phải có views >= video thứ hai. First=" + firstViews
                                                + ", Second=" + secondViews);
        }

        // SRT-002: Sắp xếp Views tăng dần
        @Test
        public void SRT_002_SortByLeastViews() {
                searchPage.clickFewViews();

                Assert.assertTrue(
                                driver.getCurrentUrl().contains("/search/viewLtoH"),
                                "URL phải chứa /search/viewLtoH");

                List<WebElement> items = searchPage.getVideoItems();
                Assert.assertTrue(items.size() >= 2,
                                "Cần ít nhất 2 video để kiểm tra thứ tự sắp xếp");

                long firstViews = searchPage.getViewsAt(0);
                long secondViews = searchPage.getViewsAt(1);

                Assert.assertTrue(firstViews <= secondViews,
                                "Video đầu tiên phải có views <= video thứ hai. First=" + firstViews
                                                + ", Second=" + secondViews);
        }

        // SRT-003: Sắp xếp theo Lượt thích giảm dần
        @Test
        public void SRT_003_SortByMostLikes() {
                searchPage.clickManyLikes();

                Assert.assertTrue(
                                driver.getCurrentUrl().contains("/search/likeHtoL"),
                                "URL phải chứa /search/likeHtoL");

                List<WebElement> items = searchPage.getVideoItems();
                Assert.assertTrue(items.size() >= 2,
                                "Cần ít nhất 2 video để kiểm tra thứ tự sắp xếp");

                long firstLikes = searchPage.getLikesAt(0);
                long secondLikes = searchPage.getLikesAt(1);

                Assert.assertTrue(firstLikes >= secondLikes,
                                "Video đầu tiên phải có likes >= video thứ hai. First=" + firstLikes
                                                + ", Second=" + secondLikes);
        }

        // SRT-004: Sắp xếp theo A-Z
        @Test
        public void SRT_004_SortByAZ() {
                searchPage.clickAZ();

                Assert.assertTrue(
                                driver.getCurrentUrl().contains("/search/AZ"),
                                "URL phải chứa /search/AZ");

                List<WebElement> items = searchPage.getVideoItems();
                Assert.assertTrue(items.size() >= 2,
                                "Cần ít nhất 2 video để kiểm tra thứ tự A-Z");

                String firstTitle = searchPage.getTitleAt(0);
                String secondTitle = searchPage.getTitleAt(1);

                Assert.assertTrue(
                                firstTitle.compareToIgnoreCase(secondTitle) <= 0,
                                "Video đầu tiên '" + firstTitle + "' phải đứng trước '"
                                                + secondTitle + "' theo A-Z");
        }

        // SRT-005: Duy trì lọc khi chuyển trang
        @Test
        public void SRT_005_FilterMaintainedOnPageTwo() {
                searchPage.search("Phim");

                List<WebElement> page1Items = searchPage.getVideoItems();
                Assert.assertFalse(page1Items.isEmpty(),
                                "Tìm kiếm 'Phim' phải có kết quả ở trang 1");

                searchPage.clickNextPage();

                List<WebElement> page2Titles = searchPage.getVideoTitles();
                Assert.assertFalse(page2Titles.isEmpty(),
                                "Trang 2 phải có video kết quả của từ khóa 'Phim'");

                for (WebElement title : page2Titles) {
                        Assert.assertTrue(
                                        title.getText().toLowerCase().contains("phim"),
                                        "Tiêu đề ở trang 2 phải chứa 'Phim': " + title.getText());
                }
        }

        // SRT-006: Kết hợp Tìm kiếm & Sắp xếp
        @Test
        public void SRT_006_SearchAndSortCombined() {
                searchPage.search("Hài");

                List<WebElement> searchResults = searchPage.getVideoTitles();
                Assert.assertFalse(searchResults.isEmpty(),
                                "Tìm kiếm 'Hài' phải có kết quả");

                searchPage.clickAZ();

                Assert.assertTrue(
                                driver.getCurrentUrl().contains("/search/AZ"),
                                "URL phải chứa /search/AZ sau khi sắp xếp");

                List<WebElement> sortedItems = searchPage.getVideoItems();
                Assert.assertTrue(sortedItems.size() >= 2,
                                "Cần ít nhất 2 video để kiểm tra kết hợp tìm kiếm + sắp xếp");

                for (WebElement title : searchPage.getVideoTitles()) {
                        Assert.assertTrue(
                                        title.getText().toLowerCase().contains("hài"),
                                        "Kết quả sau sắp xếp vẫn phải chứa 'Hài': " + title.getText());
                }

                String firstTitle = searchPage.getTitleAt(0);
                String secondTitle = searchPage.getTitleAt(1);
                Assert.assertTrue(
                                firstTitle.compareToIgnoreCase(secondTitle) <= 0,
                                "Kết quả 'Hài' phải được sắp xếp A-Z: '" + firstTitle
                                                + "' trước '" + secondTitle + "'");
        }
}
