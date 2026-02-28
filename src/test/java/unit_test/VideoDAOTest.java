package unit_test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.asm.dao.VideoDAO;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Video;

public class VideoDAOTest {

    static VideoDAO videoDAO;

    @BeforeClass
    public static void setUp() {
        videoDAO = new VideoDAOImpl();
    }

    @Test(priority = 1)
    public void testFindAll() {
        List<Video> videos = videoDAO.findAll();
        Assert.assertNotNull(videos, "findAll() không được trả về null");
        Assert.assertFalse(videos.isEmpty(), "Database phải có ít nhất 1 video");
        System.out.println("testFindAll: Tìm thấy " + videos.size() + " video");
    }

    @Test(priority = 2)
    public void testFindByIdExist() {
        Video video = videoDAO.findById("V001");
        Assert.assertNotNull(video, "Video V001 phải tồn tại trong Database");
        Assert.assertEquals(video.getId(), "V001");
        Assert.assertNotNull(video.getTitle(), "Title không được null");
        System.out.println("testFindByIdExist: V001 => " + video.getTitle());
    }

    @Test(priority = 3)
    public void testFindByIdNotExist() {
        Video video = videoDAO.findById("KHONG_TON_TAI_999");
        Assert.assertNull(video, "Video không tồn tại phải trả về null");
        System.out.println("testFindByIdNotExist: KHONG_TON_TAI_999 => null (đúng)");
    }

    @Test(priority = 4)
    public void testSearchByTitleWithKeyword() {
        List<Video> results = videoDAO.searchByTitle("Hài");
        Assert.assertNotNull(results, "searchByTitle() không được trả về null");
        for (Video v : results) {
            Assert.assertTrue(
                    v.getTitle().toLowerCase().contains("hài"),
                    "Tiêu đề phải chứa 'Hài': " + v.getTitle());
        }
        System.out.println("testSearchByTitleWithKeyword: Tìm 'Hài' => " + results.size() + " kết quả");
    }

    @Test(priority = 5)
    public void testSearchByTitleEmpty() {
        List<Video> results = videoDAO.searchByTitle("");
        Assert.assertNotNull(results);
        List<Video> allVideos = videoDAO.findAll();
        Assert.assertEquals(results.size(), allVideos.size(),
                "Tìm chuỗi rỗng phải trả về tất cả video");
        System.out.println("testSearchByTitleEmpty: Chuỗi rỗng => " + results.size() + " video (= findAll)");
    }

    @Test(priority = 6)
    public void testSearchByTitleNull() {
        List<Video> results = videoDAO.searchByTitle(null);
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty(), "Tìm null phải trả về tất cả video");
        System.out.println("testSearchByTitleNull: null => " + results.size() + " video");
    }

    @Test(priority = 7)
    public void testSearchByTitleNoResult() {
        List<Video> results = videoDAO.searchByTitle("XYZ_KHONG_TON_TAI_123456");
        Assert.assertNotNull(results);
        Assert.assertTrue(results.isEmpty(), "Tìm keyword không tồn tại phải trả về danh sách rỗng");
        System.out.println("testSearchByTitleNoResult: 0 kết quả (đúng)");
    }

    @Test(priority = 8)
    public void testFind10RandomVideo() {
        List<Video> randomVideos = videoDAO.find10RandomVideo();
        Assert.assertNotNull(randomVideos, "find10RandomVideo() không được trả về null");
        Assert.assertTrue(randomVideos.size() <= 10, "Tối đa 10 video");
        System.out.println("testFind10RandomVideo: Lấy được " + randomVideos.size() + " video ngẫu nhiên");
    }

    @Test(priority = 9)
    public void testIncreaseViews() {
        Video before = videoDAO.findById("V001");
        Assert.assertNotNull(before);
        int viewsBefore = before.getViews();

        videoDAO.increaseViews("V001");

        Video after = videoDAO.findById("V001");
        Assert.assertEquals(after.getViews(), viewsBefore + 1,
                "Views phải tăng 1 sau khi gọi increaseViews. Before=" + viewsBefore + ", After=" + after.getViews());
        System.out.println("testIncreaseViews: " + viewsBefore + " -> " + after.getViews());
    }

    @Test(priority = 10)
    public void testIsLikedExist() {
        boolean liked = videoDAO.isLiked("V001", "admin");
        System.out.println("testIsLikedExist: admin liked V001 = " + liked);
    }

    @Test(priority = 11)
    public void testIsLikedNotExist() {
        boolean liked = videoDAO.isLiked("V001", "USER_KHONG_TON_TAI");
        Assert.assertFalse(liked, "User không tồn tại thì isLiked phải false");
        System.out.println("testIsLikedNotExist: USER_KHONG_TON_TAI liked V001 = false (đúng)");
    }

    @Test(priority = 12)
    public void testCountAll() {
        int count = videoDAO.countAll();
        Assert.assertTrue(count >= 0, "countAll() phải >= 0");
        System.out.println("testCountAll: Tổng số video = " + count);
    }

    @Test(priority = 13)
    public void testSearchVideoWithJPQL() {
        String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
                + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id left join Share s on v.id = s.video.id "
                + "WHERE v.title LIKE :text "
                + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
                + "ORDER BY v.views DESC";
        List<Object[]> results = videoDAO.searchVideo("%%", JPQL);
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty(), "searchVideo với %% phải trả về kết quả");
        System.out.println(
                "testSearchVideoWithJPQL: Tìm thấy " + results.size() + " kết quả với JPQL sort by views DESC");
    }
}
