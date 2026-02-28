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

        @Test
        public void AT_SEA_01_SearchSuccess() {
                searchPage.search("and");
                List<WebElement> videoItems = searchPage.getVideoItems();
                Assert.assertFalse(videoItems.isEmpty(),
                                "Phải hiển thị danh sách video khi tìm 'a'");

                List<WebElement> titles = searchPage.getVideoTitles();
                Assert.assertFalse(titles.isEmpty(),
                                "Danh sách video phải có tiêu đề hiển thị");

                for (WebElement title : titles) {
                        Assert.assertTrue(
                                        title.getText().toLowerCase().contains("and"),
                                        "Tiêu đề video phải chứa từ khóa 'And': " + title.getText());
                }

                System.out.println("AT-SEA-01: Tìm 'And' => " + videoItems.size() + " video hiển thị");
        }

        @Test
        public void AT_SEA_02_SearchAndSort() {
                searchPage.search("P");

                List<WebElement> searchResults = searchPage.getVideoTitles();
                Assert.assertFalse(searchResults.isEmpty(),
                                "Tìm kiếm 'Phim' phải có kết quả");

                searchPage.clickAZ();

                Assert.assertTrue(
                                driver.getCurrentUrl().contains("/search/AZ"),
                                "URL phải chứa /search/AZ sau khi chọn sắp xếp");

                List<WebElement> sortedTitles = searchPage.getVideoTitles();
                Assert.assertFalse(sortedTitles.isEmpty(),
                                "Sau khi sắp xếp vẫn phải có kết quả cho 'P'");

                if (sortedTitles.size() >= 2) {
                        String firstTitle = searchPage.getTitleAt(0);
                        String secondTitle = searchPage.getTitleAt(1);
                        Assert.assertTrue(
                                        firstTitle.compareToIgnoreCase(secondTitle) <= 0,
                                        "Video phải sắp xếp theo A-Z: '" + firstTitle
                                                        + "' trước '" + secondTitle + "'");
                }

                System.out.println("AT-SEA-02: Tìm 'P' + Sắp xếp A-Z => "
                                + sortedTitles.size() + " video, thứ tự đúng");
        }
}
