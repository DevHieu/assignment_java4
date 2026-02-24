package tests;

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

        @Test
        public void testSearchWithValidKeyword() {
                searchPage.search("Hài");

                List<org.openqa.selenium.WebElement> titles = searchPage.getVideoTitles();

                Assert.assertFalse(titles.isEmpty(),
                                "Danh sách video không được rỗng khi tìm 'Hài'");

                for (org.openqa.selenium.WebElement title : titles) {
                        Assert.assertTrue(
                                        title.getText().toLowerCase().contains("hài"),
                                        "Tiêu đề video phải chứa 'Hài': " + title.getText());
                }
        }

        @Test
        public void testSearchWithEmptyKeyword() {
                searchPage.search("");

                List<org.openqa.selenium.WebElement> videoItems = searchPage.getVideoItems();

                Assert.assertFalse(videoItems.isEmpty(),
                                "Khi tìm kiếm rỗng, hệ thống phải load toàn bộ danh sách video");
        }

        @Test
        public void testSearchWithNonExistentKeyword() {
                searchPage.search("XYZ123");

                boolean isDisplayed = searchPage.isNoResultMessageDisplayed();

                Assert.assertTrue(
                                isDisplayed,
                                "Phải hiển thị thông báo không có kết quả");
        }

        @Test
        public void testSortByMostViews() {
                searchPage.clickManyViews();

                Assert.assertTrue(
                                driver.getCurrentUrl().contains("/search/viewHtoL"),
                                "URL phải chứa /search/viewHtoL");

                List<org.openqa.selenium.WebElement> items = searchPage.getVideoItems();
                Assert.assertTrue(items.size() >= 2, "Cần ít nhất 2 video để kiểm tra thứ tự sắp xếp");

                long firstViews = searchPage.getViewsAt(0);
                long secondViews = searchPage.getViewsAt(1);

                Assert.assertTrue(firstViews >= secondViews,
                                "Video đầu tiên phải có views >= video thứ hai. First=" + firstViews + ", Second="
                                                + secondViews);
        }

        @Test
        public void testSortByLeastViews() {
                searchPage.clickFewViews();

                Assert.assertTrue(
                                driver.getCurrentUrl().contains("/search/viewLtoH"),
                                "URL phải chứa /search/viewLtoH");

                List<org.openqa.selenium.WebElement> items = searchPage.getVideoItems();
                Assert.assertTrue(items.size() >= 2, "Cần ít nhất 2 video để kiểm tra thứ tự sắp xếp");

                long firstViews = searchPage.getViewsAt(0);
                long secondViews = searchPage.getViewsAt(1);

                Assert.assertTrue(firstViews <= secondViews,
                                "Video đầu tiên phải có views <= video thứ hai. First=" + firstViews + ", Second="
                                                + secondViews);
        }

        @Test
        public void testSortByMostLikes() {
                searchPage.clickManyLikes();

                Assert.assertTrue(
                                driver.getCurrentUrl().contains("/search/likeHtoL"),
                                "URL phải chứa /search/likeHtoL");

                List<org.openqa.selenium.WebElement> items = searchPage.getVideoItems();
                Assert.assertTrue(items.size() >= 2, "Cần ít nhất 2 video để kiểm tra thứ tự sắp xếp");

                long firstLikes = searchPage.getLikesAt(0);
                long secondLikes = searchPage.getLikesAt(1);

                Assert.assertTrue(firstLikes >= secondLikes,
                                "Video đầu tiên phải có likes >= video thứ hai. First=" + firstLikes + ", Second="
                                                + secondLikes);
        }

        @Test
        public void testSortByAZ() {
                searchPage.clickAZ();

                Assert.assertTrue(
                                driver.getCurrentUrl().contains("/search/AZ"),
                                "URL phải chứa /search/AZ");

                List<org.openqa.selenium.WebElement> items = searchPage.getVideoItems();
                Assert.assertTrue(items.size() >= 2, "Cần ít nhất 2 video để kiểm tra thứ tự A-Z");

                String firstTitle = searchPage.getTitleAt(0);
                String secondTitle = searchPage.getTitleAt(1);

                Assert.assertTrue(
                                firstTitle.compareToIgnoreCase(secondTitle) <= 0,
                                "Video đầu tiên '" + firstTitle + "' phải đứng trước '" + secondTitle + "' theo A-Z");
        }

        @Test
        public void testFilterMaintainedOnPageTwo() {
                searchPage.search("Phim");

                List<org.openqa.selenium.WebElement> page1Items = searchPage.getVideoItems();
                Assert.assertFalse(page1Items.isEmpty(), "Tìm kiếm 'Phim' phải có kết quả ở trang 1");

                searchPage.clickNextPage();

                List<org.openqa.selenium.WebElement> page2Titles = searchPage.getVideoTitles();
                Assert.assertFalse(page2Titles.isEmpty(),
                                "Trang 2 phải có video kết quả của từ khóa 'Phim'");

                for (org.openqa.selenium.WebElement title : page2Titles) {
                        Assert.assertTrue(
                                        title.getText().toLowerCase().contains("phim"),
                                        "Tiêu đề ở trang 2 phải chứa 'Phim': " + title.getText());
                }
        }
}
