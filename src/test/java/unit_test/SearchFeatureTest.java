package unit_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.asm.dao.VideoDAO;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Video;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchFeatureTest {

    static VideoDAO videoDAO;

    @BeforeAll
    static void setUp() {
        videoDAO = new VideoDAOImpl();
    }

    // ======================== TÌM KIẾM ========================

    // UT-SEA-01: Tìm kiếm theo từ khóa "Java"
    @Test
    @Order(1)
    void UT_SEA_01_SearchByKeyword() {
        String keyword = "Java";
        String searchText = "%" + keyword + "%";

        String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                + "left join Share s on v.id = s.video.id "
                + "WHERE v.title LIKE :text "
                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active";

        List<Object[]> results = videoDAO.searchVideo(searchText, JPQL);

        assertNotNull(results, "searchVideo() không được trả về null");
        System.out.println("UT-SEA-01: Tìm '" + keyword + "' => " + results.size() + " kết quả");

        for (Object[] row : results) {
            Video video = (Video) row[0];
            assertTrue(
                    video.getTitle().toLowerCase().contains(keyword.toLowerCase()),
                    "Tiêu đề phải chứa '" + keyword + "': " + video.getTitle());
        }
    }

    // UT-SEA-01b: Tìm kiếm chuỗi rỗng phải trả về tất cả
    @Test
    @Order(2)
    void UT_SEA_01b_SearchEmptyReturnsAll() {
        String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                + "left join Share s on v.id = s.video.id "
                + "WHERE v.title LIKE :text "
                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active";

        List<Object[]> results = videoDAO.searchVideo("%%", JPQL);
        assertNotNull(results);
        assertFalse(results.isEmpty(),
                "Tìm '%%' (chuỗi rỗng) phải trả về tất cả video");

        List<Video> allVideos = videoDAO.findAll();
        assertEquals(allVideos.size(), results.size(),
                "Số lượng kết quả phải bằng findAll()");
        System.out.println("UT-SEA-01b: Chuỗi rỗng => " + results.size() + " kết quả (= findAll)");
    }

    // UT-SEA-01c: Tìm kiếm từ khóa không tồn tại
    @Test
    @Order(3)
    void UT_SEA_01c_SearchNonExistentKeyword() {
        String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                + "left join Share s on v.id = s.video.id "
                + "WHERE v.title LIKE :text "
                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active";

        List<Object[]> results = videoDAO.searchVideo("%XYZ_KHONG_TON_TAI_999%", JPQL);
        assertNotNull(results);
        assertTrue(results.isEmpty(),
                "Tìm keyword không tồn tại phải trả về danh sách rỗng");
        System.out.println("UT-SEA-01c: Keyword không tồn tại => 0 kết quả (đúng)");
    }

    // ======================== SẮP XẾP ========================

    // UT-SEA-02: Sắp xếp A-Z
    @Test
    @Order(4)
    void UT_SEA_02_SortAZ() {
        String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                + "left join Share s on v.id = s.video.id "
                + "WHERE v.title LIKE :text "
                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
                + "ORDER BY v.title ASC";

        List<Object[]> results = videoDAO.searchVideo("%%", JPQL);
        assertNotNull(results);
        assertTrue(results.size() >= 2,
                "Cần ít nhất 2 video để kiểm tra sắp xếp A-Z");

        Video first = (Video) results.get(0)[0];
        Video second = (Video) results.get(1)[0];

        assertTrue(
                first.getTitle().compareToIgnoreCase(second.getTitle()) <= 0,
                "Video đầu tiên '" + first.getTitle() + "' phải đứng trước '"
                        + second.getTitle() + "' theo A-Z");
        System.out.println("UT-SEA-02: Sắp xếp A-Z => '" + first.getTitle()
                + "' trước '" + second.getTitle() + "' (đúng)");
    }

    // UT-SEA-02b: Sắp xếp Views giảm dần
    @Test
    @Order(5)
    void UT_SEA_02b_SortViewsDesc() {
        String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                + "left join Share s on v.id = s.video.id "
                + "WHERE v.title LIKE :text "
                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
                + "ORDER BY v.views DESC";

        List<Object[]> results = videoDAO.searchVideo("%%", JPQL);
        assertNotNull(results);
        assertTrue(results.size() >= 2,
                "Cần ít nhất 2 video để kiểm tra sắp xếp views DESC");

        Video first = (Video) results.get(0)[0];
        Video second = (Video) results.get(1)[0];

        assertTrue(first.getViews() >= second.getViews(),
                "Video đầu tiên phải có views >= video thứ hai. First="
                        + first.getViews() + ", Second=" + second.getViews());
        System.out.println("UT-SEA-02b: Views DESC => " + first.getViews()
                + " >= " + second.getViews() + " (đúng)");
    }

    // UT-SEA-02c: Sắp xếp Views tăng dần
    @Test
    @Order(6)
    void UT_SEA_02c_SortViewsAsc() {
        String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                + "left join Share s on v.id = s.video.id "
                + "WHERE v.title LIKE :text "
                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
                + "ORDER BY v.views ASC";

        List<Object[]> results = videoDAO.searchVideo("%%", JPQL);
        assertNotNull(results);
        assertTrue(results.size() >= 2,
                "Cần ít nhất 2 video để kiểm tra sắp xếp views ASC");

        Video first = (Video) results.get(0)[0];
        Video second = (Video) results.get(1)[0];

        assertTrue(first.getViews() <= second.getViews(),
                "Video đầu tiên phải có views <= video thứ hai. First="
                        + first.getViews() + ", Second=" + second.getViews());
        System.out.println("UT-SEA-02c: Views ASC => " + first.getViews()
                + " <= " + second.getViews() + " (đúng)");
    }

    // UT-SEA-02d: Kết hợp tìm kiếm + sắp xếp
    @Test
    @Order(7)
    void UT_SEA_02d_SearchAndSort() {
        String keyword = "Hài";
        String searchText = "%" + keyword + "%";

        String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
                + "left join Share s on v.id = s.video.id "
                + "WHERE v.title LIKE :text "
                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
                + "ORDER BY v.title ASC";

        List<Object[]> results = videoDAO.searchVideo(searchText, JPQL);
        assertNotNull(results);

        for (Object[] row : results) {
            Video video = (Video) row[0];
            assertTrue(
                    video.getTitle().toLowerCase().contains(keyword.toLowerCase()),
                    "Kết quả phải chứa '" + keyword + "': " + video.getTitle());
        }

        if (results.size() >= 2) {
            Video first = (Video) results.get(0)[0];
            Video second = (Video) results.get(1)[0];
            assertTrue(first.getTitle().compareToIgnoreCase(second.getTitle()) <= 0,
                    "Kết quả phải sắp xếp A-Z");
        }

        System.out.println("UT-SEA-02d: Tìm '" + keyword + "' + sắp xếp A-Z => "
                + results.size() + " kết quả (đúng)");
    }
}
