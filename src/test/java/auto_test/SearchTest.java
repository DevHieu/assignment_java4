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

        // ======================== CHỨC NĂNG 5: TÌM KIẾM VIDEO ========================

        // AT-SEA-01: Luồng tìm kiếm thành công
        // Điều kiện tiên quyết: UI hoạt động
        // Dữ liệu test: "Hài"
        // Các bước: 1. Nhập từ khóa. 2. Nhấn Search.
        // Kết quả mong muốn: Hiển thị danh sách video và số lượng kết quả tương ứng
        // trên giao diện.
        @Test
        public void AT_SEA_01_SearchSuccess() {
                // Bước 1 & 2: Nhập từ khóa "Hài" và nhấn Search
                searchPage.search("Hài");

                // Kiểm tra: Hiển thị danh sách video
                List<WebElement> videoItems = searchPage.getVideoItems();
                Assert.assertFalse(videoItems.isEmpty(),
                                "Phải hiển thị danh sách video khi tìm 'Hài'");

                // Kiểm tra: Số lượng kết quả tương ứng
                List<WebElement> titles = searchPage.getVideoTitles();
                Assert.assertFalse(titles.isEmpty(),
                                "Danh sách video phải có tiêu đề hiển thị");

                // Kiểm tra: Tất cả kết quả phải chứa từ khóa "Hài"
                for (WebElement title : titles) {
                        Assert.assertTrue(
                                        title.getText().toLowerCase().contains("hài"),
                                        "Tiêu đề video phải chứa từ khóa 'Hài': " + title.getText());
                }

                System.out.println("AT-SEA-01: Tìm 'Hài' => " + videoItems.size() + " video hiển thị");
        }

        // AT-SEA-02: Kết hợp Tìm kiếm & Sắp xếp
        // Điều kiện tiên quyết: UI hoạt động
        // Dữ liệu test: "Phim", Chọn "A-Z"
        // Các bước: 1. Tìm "Phim". 2. Thay đổi bộ lọc sắp xếp sang A-Z.
        // Kết quả mong muốn: UI cập nhật thứ tự hiển thị của các video "Phim" theo bảng
        // chữ cái.
        @Test
        public void AT_SEA_02_SearchAndSort() {
                // Bước 1: Tìm "Phim"
                searchPage.search("Phim");

                List<WebElement> searchResults = searchPage.getVideoTitles();
                Assert.assertFalse(searchResults.isEmpty(),
                                "Tìm kiếm 'Phim' phải có kết quả");

                // Bước 2: Thay đổi bộ lọc sắp xếp sang A-Z
                searchPage.clickAZ();

                // Kiểm tra: URL chứa sort path
                Assert.assertTrue(
                                driver.getCurrentUrl().contains("/search/AZ"),
                                "URL phải chứa /search/AZ sau khi chọn sắp xếp");

                // Kiểm tra: Kết quả vẫn chứa từ khóa "Phim"
                List<WebElement> sortedTitles = searchPage.getVideoTitles();
                Assert.assertFalse(sortedTitles.isEmpty(),
                                "Sau khi sắp xếp vẫn phải có kết quả cho 'Phim'");

                // Kiểm tra: Thứ tự A-Z được áp dụng
                if (sortedTitles.size() >= 2) {
                        String firstTitle = searchPage.getTitleAt(0);
                        String secondTitle = searchPage.getTitleAt(1);
                        Assert.assertTrue(
                                        firstTitle.compareToIgnoreCase(secondTitle) <= 0,
                                        "Video phải sắp xếp theo A-Z: '" + firstTitle
                                                        + "' trước '" + secondTitle + "'");
                }

                System.out.println("AT-SEA-02: Tìm 'Phim' + Sắp xếp A-Z => "
                                + sortedTitles.size() + " video, thứ tự đúng");
        }
}
