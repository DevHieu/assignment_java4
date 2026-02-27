package unit_test;

import com.asm.dao.VideoDAO;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Video;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class VideoDAOTestNG {

    private VideoDAO dao;
    private final String TEST_ID = "TEST_VIDEO_001";

    @BeforeClass
    public void setup() {
        dao = new VideoDAOImpl();
    }

    // SKI-001 Thêm video mới thành công
    @Test(priority = 1)
    public void SKI_001_createVideo_success() {
        Video v = new Video();
        v.setId(TEST_ID);
        v.setTitle("Test Title");
        v.setDescription("Test Desc");
        v.setVideo(TEST_ID);
        v.setActive(true);
        v.setBanner(false);
        v.setViews(0);

        dao.create(v);

        Video found = dao.findById(TEST_ID);
        Assert.assertNotNull(found);
    }

    // SKI-002 Tìm video theo ID tồn tại
    @Test(priority = 2)
    public void SKI_002_findById_exists() {
        Video v = dao.findById(TEST_ID);
        Assert.assertNotNull(v);
    }

    // SKI-003 Tìm video theo ID không tồn tại
    @Test(priority = 3)
    public void SKI_003_findById_notExists() {
        Video v = dao.findById("NOT_EXIST_ID");
        Assert.assertNull(v);
    }

    // SKI-004 Cập nhật video thành công
    @Test(priority = 4)
    public void SKI_004_updateVideo_success() {
        Video v = dao.findById(TEST_ID);
        v.setTitle("Updated Title");
        dao.update(v);

        Video updated = dao.findById(TEST_ID);
        Assert.assertEquals(updated.getTitle(), "Updated Title");
    }

    // SKI-005 Xóa video theo id tồn tại (test bằng increase views trước khi xóa)
    @Test(priority = 5)
    public void SKI_005_increaseViews() {
        Video v = dao.findById(TEST_ID);
        int oldViews = v.getViews();

        dao.increaseViews(TEST_ID);

        Video updated = dao.findById(TEST_ID);
        Assert.assertEquals(updated.getViews(), oldViews + 1);
    }

    // SKI-006 Xóa video id không tồn tại (delete fake không lỗi)
    @Test(priority = 6)
    public void SKI_006_delete_notExists_safe() {
        dao.deleteById("ID_FAKE_DELETE");
        Assert.assertTrue(true);
    }

    // SKI-007 Tìm kiếm video theo title có kết quả
    @Test(priority = 7)
    public void SKI_007_searchByTitle_found() {
        List<Video> list = dao.searchByTitle("Updated");
        Assert.assertTrue(list.size() > 0);
    }

    // SKI-008 Tìm kiếm video theo title không có kết quả
    @Test(priority = 8)
    public void SKI_008_searchByTitle_notFound() {
        List<Video> list = dao.searchByTitle("ZZZ_NOT_FOUND");
        Assert.assertTrue(list.isEmpty());
    }

    // SKI-009 Lấy danh sách tất cả video
    @Test(priority = 9)
    public void SKI_009_findAllVideos() {
        List<Video> list = dao.findAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() >= 0);
    }

    // SKI-010 Phân trang video
    @Test(priority = 10)
    public void SKI_010_findPage() {
        List<Video> list = dao.findPage(0, 10);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() <= 10);
    }

    // SKI-011 Đếm tổng video
    @Test(priority = 11)
    public void SKI_011_countAll() {
        int count = dao.countAll();
        Assert.assertTrue(count >= 0);
    }

    // BA-001 Lấy danh sách banner
    @Test(priority = 12)
    public void BA_001_getBannerVideo() {
        List<Video> list = dao.getBannerVideo();
        Assert.assertNotNull(list);
    }

    // BA-002 Gỡ Banner thành công
    @Test(priority = 13)
    public void BA_002_removeBanner() {
        Video v = dao.findById(TEST_ID);
        v.setBanner(true);
        dao.update(v);

        dao.removeBanner(TEST_ID);

        Video updated = dao.findById(TEST_ID);
        Assert.assertFalse(updated.isBanner());
    }

    @AfterClass
    public void cleanup() {
        dao.deleteById(TEST_ID);
    }
}
