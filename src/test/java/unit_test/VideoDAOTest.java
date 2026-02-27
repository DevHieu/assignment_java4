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
public class VideoDAOTest {

    static VideoDAO videoDAO;

    @BeforeAll
    static void setUp() {
        videoDAO = new VideoDAOImpl();
    }

    @Test
    @Order(1)
    void testFindAll() {
        List<Video> videos = videoDAO.findAll();
        assertNotNull(videos, "findAll() không được trả về null");
        assertFalse(videos.isEmpty(), "Database phải có ít nhất 1 video");
        System.out.println("testFindAll: Tìm thấy " + videos.size() + " video");
    }

    @Test
    @Order(2)
    void testFindByIdExist() {
        Video video = videoDAO.findById("V001");
        assertNotNull(video, "Video V001 phải tồn tại trong Database");
        assertEquals("V001", video.getId());
        assertNotNull(video.getTitle(), "Title không được null");
        System.out.println("testFindByIdExist: V001 => " + video.getTitle());
    }

    @Test
    @Order(3)
    void testFindByIdNotExist() {
        Video video = videoDAO.findById("KHONG_TON_TAI_999");
        assertNull(video, "Video không tồn tại phải trả về null");
        System.out.println("testFindByIdNotExist: KHONG_TON_TAI_999 => null (đúng)");
    }

    @Test
    @Order(4)
    void testSearchByTitleWithKeyword() {
        List<Video> results = videoDAO.searchByTitle("Hài");
        assertNotNull(results, "searchByTitle() không được trả về null");
        for (Video v : results) {
            assertTrue(
                    v.getTitle().toLowerCase().contains("hài"),
                    "Tiêu đề phải chứa 'Hài': " + v.getTitle());
        }
        System.out.println("testSearchByTitleWithKeyword: Tìm 'Hài' => " + results.size() + " kết quả");
    }

    @Test
    @Order(5)
    void testSearchByTitleEmpty() {
        List<Video> results = videoDAO.searchByTitle("");
        assertNotNull(results);
        List<Video> allVideos = videoDAO.findAll();
        assertEquals(allVideos.size(), results.size(),
                "Tìm chuỗi rỗng phải trả về tất cả video");
        System.out.println("testSearchByTitleEmpty: Chuỗi rỗng => " + results.size() + " video (= findAll)");
    }

    @Test
    @Order(6)
    void testSearchByTitleNull() {
        List<Video> results = videoDAO.searchByTitle(null);
        assertNotNull(results);
        assertFalse(results.isEmpty(), "Tìm null phải trả về tất cả video");
        System.out.println("testSearchByTitleNull: null => " + results.size() + " video");
    }

    @Test
    @Order(7)
    void testSearchByTitleNoResult() {
        List<Video> results = videoDAO.searchByTitle("XYZ_KHONG_TON_TAI_123456");
        assertNotNull(results);
        assertTrue(results.isEmpty(), "Tìm keyword không tồn tại phải trả về danh sách rỗng");
        System.out.println("testSearchByTitleNoResult: 0 kết quả (đúng)");
    }

    @Test
    @Order(8)
    void testFind10RandomVideo() {
        List<Video> randomVideos = videoDAO.find10RandomVideo();
        assertNotNull(randomVideos, "find10RandomVideo() không được trả về null");
        assertTrue(randomVideos.size() <= 10, "Tối đa 10 video");
        System.out.println("testFind10RandomVideo: Lấy được " + randomVideos.size() + " video ngẫu nhiên");
    }

    @Test
    @Order(9)
    void testIncreaseViews() {
        Video before = videoDAO.findById("V001");
        assertNotNull(before);
        int viewsBefore = before.getViews();

        videoDAO.increaseViews("V001");

        Video after = videoDAO.findById("V001");
        assertEquals(viewsBefore + 1, after.getViews(),
                "Views phải tăng 1 sau khi gọi increaseViews. Before=" + viewsBefore + ", After=" + after.getViews());
        System.out.println("testIncreaseViews: " + viewsBefore + " -> " + after.getViews());
    }

    @Test
    @Order(10)
    void testIsLikedExist() {
        boolean liked = videoDAO.isLiked("V001", "admin");
        System.out.println("testIsLikedExist: admin liked V001 = " + liked);
    }

    @Test
    @Order(11)
    void testIsLikedNotExist() {
        boolean liked = videoDAO.isLiked("V001", "USER_KHONG_TON_TAI");
        assertFalse(liked, "User không tồn tại thì isLiked phải false");
        System.out.println("testIsLikedNotExist: USER_KHONG_TON_TAI liked V001 = false (đúng)");
    }

    @Test
    @Order(12)
    void testCountAll() {
        int count = videoDAO.countAll();
        assertTrue(count >= 0, "countAll() phải >= 0");
        System.out.println("testCountAll: Tổng số video = " + count);
    }

    @Test
    @Order(13)
    void testSearchVideoWithJPQL() {
        String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id left join Share s on v.id = s.video.id "
                + "WHERE v.title LIKE :text "
                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
                + "ORDER BY v.views DESC";
        List<Object[]> results = videoDAO.searchVideo("%%", JPQL);
        assertNotNull(results);
        assertFalse(results.isEmpty(), "searchVideo với %% phải trả về kết quả");
        System.out.println(
                "testSearchVideoWithJPQL: Tìm thấy " + results.size() + " kết quả với JPQL sort by views DESC");
    }
}
