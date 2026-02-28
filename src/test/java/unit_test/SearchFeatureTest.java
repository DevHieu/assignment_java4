package unit_test;

import java.text.Collator;
import java.util.List;
import java.util.Locale;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.asm.dao.VideoDAO;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Video;

public class SearchFeatureTest {

        static VideoDAO videoDAO;

        @BeforeClass
        public static void setUp() {
                videoDAO = new VideoDAOImpl();
        }

        // ======================== CHỨC NĂNG 5: TÌM KIẾM VIDEO ========================

        // UT-SEA-01: Tìm kiếm theo từ khóa hợp lệ
        // Điều kiện: DB có ít nhất 1 video chứa từ "Java" trong tiêu đề
        // Kết quả mong muốn: list.size() > 0; Tất cả title chứa "Java"
        @Test(priority = 1)
        public void UT_SEA_01_SearchByValidKeyword() {
                String keyword = "Java";
                String searchText = "%" + keyword + "%";

                String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                                + "left join Share s on v.id = s.video.id "
                                + "WHERE v.title LIKE :text "
                                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active";

                List<Object[]> results = videoDAO.searchVideo(searchText, JPQL);

                Assert.assertNotNull(results, "searchVideo() không được trả về null");
                Assert.assertTrue(results.size() > 0,
                                "Phải có ít nhất 1 video chứa từ 'Java' trong tiêu đề");

                for (Object[] row : results) {
                        Video video = (Video) row[0];
                        Assert.assertTrue(
                                        video.getTitle().toLowerCase().contains(keyword.toLowerCase()),
                                        "Tiêu đề phải chứa '" + keyword + "': " + video.getTitle());
                }
                System.out.println("UT-SEA-01: Tìm '" + keyword + "' => " + results.size() + " kết quả");
        }

        // UT-SEA-02: Tìm kiếm với chuỗi rỗng
        // Điều kiện: DB có dữ liệu video ở trạng thái "Active"
        // Kết quả mong muốn: Trả về toàn bộ danh sách video active
        @Test(priority = 2)
        public void UT_SEA_02_SearchWithEmptyString() {
                String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                                + "left join Share s on v.id = s.video.id "
                                + "WHERE v.title LIKE :text "
                                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active";

                List<Object[]> results = videoDAO.searchVideo("%%", JPQL);
                Assert.assertNotNull(results);
                Assert.assertFalse(results.isEmpty(),
                                "Tìm kiếm chuỗi rỗng phải trả về toàn bộ danh sách video active");

                List<Video> allVideos = videoDAO.findAll();
                Assert.assertEquals(results.size(), allVideos.size(),
                                "Số lượng kết quả phải bằng tổng số video (findAll)");
                System.out.println("UT-SEA-02: Chuỗi rỗng => " + results.size() + " kết quả (= findAll)");
        }

        // UT-SEA-03: Từ khóa không tồn tại trong hệ thống
        // Điều kiện: Không có video nào chứa chuỗi "XYZ123"
        // Kết quả mong muốn: list.size() == 0 (Danh sách rỗng, không gây lỗi)
        @Test(priority = 3)
        public void UT_SEA_03_SearchNonExistentKeyword() {
                String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                                + "left join Share s on v.id = s.video.id "
                                + "WHERE v.title LIKE :text "
                                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active";

                List<Object[]> results = videoDAO.searchVideo("%XYZ123%", JPQL);
                Assert.assertNotNull(results, "Kết quả không được null dù không tìm thấy");
                Assert.assertEquals(results.size(), 0,
                                "Tìm keyword 'XYZ123' không tồn tại phải trả về danh sách rỗng");
                System.out.println("UT-SEA-03: Keyword 'XYZ123' => 0 kết quả (đúng)");
        }

        // UT-SEA-04: Kiểm tra lỗ hổng SQL Injection
        // Điều kiện: DAO sử dụng PreparedStatement hoặc ORM an toàn
        // Dữ liệu test: q = "' OR 1=1 --"
        // Kết quả mong muốn: Hệ thống xử lý chuỗi dưới dạng văn bản thuần; Không trả về
        // toàn bộ DB
        @Test(priority = 4)
        public void UT_SEA_04_SQLInjection() {
                String injection = "' OR 1=1 --";
                String searchText = "%" + injection + "%";

                String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                                + "left join Share s on v.id = s.video.id "
                                + "WHERE v.title LIKE :text "
                                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active";

                List<Object[]> results = videoDAO.searchVideo(searchText, JPQL);
                Assert.assertNotNull(results, "Hệ thống không được crash khi nhận chuỗi SQL injection");

                List<Video> allVideos = videoDAO.findAll();
                Assert.assertTrue(results.size() < allVideos.size(),
                                "SQL Injection không được trả về toàn bộ DB. Kết quả: "
                                                + results.size() + ", Tổng DB: " + allVideos.size());

                System.out.println("UT-SEA-04: SQL Injection => " + results.size()
                                + " kết quả (< " + allVideos.size() + " tổng DB) => An toàn");
        }

        // UT-SEA-05: Sắp xếp lượt xem (Giảm dần)
        // Điều kiện: DB có ít nhất 2 video với lượt xem khác nhau
        // Dữ liệu test: sort = "viewHtoL"
        // Kết quả mong muốn: video[i].views >= video[i+1].views với mọi i
        @Test(priority = 5)
        public void UT_SEA_05_SortViewsDescending() {
                String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                                + "left join Share s on v.id = s.video.id "
                                + "WHERE v.title LIKE :text "
                                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
                                + "ORDER BY v.views DESC";

                List<Object[]> results = videoDAO.searchVideo("%%", JPQL);
                Assert.assertNotNull(results);
                Assert.assertTrue(results.size() >= 2,
                                "Cần ít nhất 2 video để kiểm tra sắp xếp views giảm dần");

                for (int i = 0; i < results.size() - 1; i++) {
                        Video current = (Video) results.get(i)[0];
                        Video next = (Video) results.get(i + 1)[0];
                        Assert.assertTrue(current.getViews() >= next.getViews(),
                                        "video[" + i + "].views (" + current.getViews()
                                                        + ") >= video[" + (i + 1) + "].views (" + next.getViews()
                                                        + ")");
                }
                System.out.println("UT-SEA-05: Sắp xếp Views giảm dần => Đúng thứ tự");
        }

        // UT-SEA-06: Sắp xếp lượt xem (Tăng dần)
        // Điều kiện: DB có ít nhất 2 video với lượt xem khác nhau
        // Dữ liệu test: sort = "viewLtoH"
        // Kết quả mong muốn: video[i].views <= video[i+1].views với mọi i
        @Test(priority = 6)
        public void UT_SEA_06_SortViewsAscending() {
                String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                                + "left join Share s on v.id = s.video.id "
                                + "WHERE v.title LIKE :text "
                                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
                                + "ORDER BY v.views ASC";

                List<Object[]> results = videoDAO.searchVideo("%%", JPQL);
                Assert.assertNotNull(results);
                Assert.assertTrue(results.size() >= 2,
                                "Cần ít nhất 2 video để kiểm tra sắp xếp views tăng dần");

                for (int i = 0; i < results.size() - 1; i++) {
                        Video current = (Video) results.get(i)[0];
                        Video next = (Video) results.get(i + 1)[0];
                        Assert.assertTrue(current.getViews() <= next.getViews(),
                                        "video[" + i + "].views (" + current.getViews()
                                                        + ") <= video[" + (i + 1) + "].views (" + next.getViews()
                                                        + ")");
                }
                System.out.println("UT-SEA-06: Sắp xếp Views tăng dần => Đúng thứ tự");
        }

        // UT-SEA-07: Sắp xếp theo mức độ yêu thích (Likes)
        // Điều kiện: DB có dữ liệu về favoriteCount
        // Dữ liệu test: sort = "likeHtoL"
        // Kết quả mong muốn: Phần tử đứng trước luôn có favoriteCount >= phần tử đứng
        // sau
        @Test(priority = 7)
        public void UT_SEA_07_SortByLikesDescending() {
                String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                                + "left join Share s on v.id = s.video.id "
                                + "WHERE v.title LIKE :text "
                                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
                                + "ORDER BY COUNT(f.video.id) DESC";

                List<Object[]> results = videoDAO.searchVideo("%%", JPQL);
                Assert.assertNotNull(results);
                Assert.assertTrue(results.size() >= 2,
                                "Cần ít nhất 2 video để kiểm tra sắp xếp theo likes");

                for (int i = 0; i < results.size() - 1; i++) {
                        Long currentLikes = (Long) results.get(i)[1];
                        Long nextLikes = (Long) results.get(i + 1)[1];
                        Assert.assertTrue(currentLikes >= nextLikes,
                                        "favoriteCount[" + i + "] (" + currentLikes
                                                        + ") >= favoriteCount[" + (i + 1) + "] (" + nextLikes + ")");
                }
                System.out.println("UT-SEA-07: Sắp xếp Likes giảm dần => Đúng thứ tự");
        }

        // UT-SEA-08: Sắp xếp theo tiêu đề (A-Z)
        // Điều kiện: DB có các video với tiêu đề khác nhau
        // Dữ liệu test: sort = "AZ"
        // Kết quả mong muốn: title[i].compareToIgnoreCase(title[i+1]) <= 0
        @Test(priority = 8)
        public void UT_SEA_08_SortByTitleAZ() {
            String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id) "
                    + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                    + "LEFT JOIN Share s on v.id = s.video.id "
                    + "WHERE v.title LIKE :text "
                    + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
                    + "ORDER BY v.title ASC";

            List<Object[]> results = videoDAO.searchVideo("%%", JPQL);
            Assert.assertNotNull(results);
            Assert.assertTrue(results.size() >= 2, "Cần ít nhất 2 video để kiểm tra sắp xếp A-Z");

            // Collator cho tiếng Việt
            Collator collator = Collator.getInstance(new Locale("vi", "VN"));
            collator.setStrength(Collator.PRIMARY); // bỏ phân biệt hoa thường

            for (int i = 0; i < results.size() - 1; i++) {
                Video current = (Video) results.get(i)[0];
                Video next = (Video) results.get(i + 1)[0];

                // Dùng collator so sánh thay cho compareToIgnoreCase
                Assert.assertTrue(
                        collator.compare(current.getTitle(), next.getTitle()) <= 0,
                        "'" + current.getTitle() + "' phải đứng trước '" + next.getTitle() + "' theo A-Z"
                );
            }

            System.out.println("UT-SEA-08: Sắp xếp A-Z tiếng Việt => Đúng thứ tự");
        }

        @Test(priority = 9)
        public void UT_SEA_09_Pagination() {
                int page = 2;
                int size = 6;

                List<Video> pageResults = videoDAO.findPage(page, size);
                Assert.assertNotNull(pageResults, "findPage() không được trả về null");

                int totalVideos = videoDAO.countAll();
                System.out.println("UT-SEA-09: Tổng video = " + totalVideos);

                if (totalVideos >= 12) {
                        Assert.assertEquals(pageResults.size(), size,
                                        "Trang 2 với size=6 phải trả về đúng 6 bản ghi");
                } else if (totalVideos > size) {
                        Assert.assertTrue(pageResults.size() <= size,
                                        "Số bản ghi trả về không được vượt quá page size");
                }

                // Kiểm tra dữ liệu trang 2 khác trang 1
                List<Video> page1 = videoDAO.findPage(1, size);
                if (!pageResults.isEmpty() && !page1.isEmpty()) {
                        Assert.assertNotEquals(pageResults.get(0).getId(), page1.get(0).getId(),
                                        "Dữ liệu trang 2 phải khác trang 1");
                }

                System.out.println("UT-SEA-09: Phân trang page=2, size=6 => "
                                + pageResults.size() + " kết quả");
        }

        // UT-SEA-10: Kiểm tra không phân biệt chữ hoa/thường
        // Điều kiện: DB có video tiêu đề "Hài kịch"
        // Dữ liệu test: q = "hÀi"
        // Kết quả mong muốn: Kết quả trả về chứa video "Hài kịch"
        @Test(priority = 10)
        public void UT_SEA_10_CaseInsensitiveSearch() {
                String keyword = "hÀi";
                String searchText = "%" + keyword + "%";

                String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                                + "left join Share s on v.id = s.video.id "
                                + "WHERE LOWER(v.title) LIKE LOWER(:text) "
                                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active";

                List<Object[]> results = videoDAO.searchVideo(searchText, JPQL);
                Assert.assertNotNull(results, "Kết quả không được null");

                // So sánh với khi search "Hài" (chữ hoa chuẩn)
                String normalSearch = "%Hài%";
                String JPQL2 = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                                + "left join Share s on v.id = s.video.id "
                                + "WHERE LOWER(v.title) LIKE LOWER(:text) "
                                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active";

                List<Object[]> normalResults = videoDAO.searchVideo(normalSearch, JPQL2);

                Assert.assertEquals(results.size(), normalResults.size(),
                                "Tìm 'hÀi' phải cho kết quả giống 'Hài' (case insensitive)");
                System.out.println("UT-SEA-10: Tìm 'hÀi' => " + results.size()
                                + " kết quả (= tìm 'Hài': " + normalResults.size() + ")");
        }
}
