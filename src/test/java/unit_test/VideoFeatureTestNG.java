package unit_test;

import com.asm.dao.VideoDAO;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Video;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class VideoFeatureTestNG {

    private VideoDAO dao;

    @BeforeMethod
    public void setup() {
        dao = new VideoDAOImpl();
    }

    private Video createVideo(String id) {
        dao.deleteById(id);

        Video v = new Video();
        v.setId(id);
        v.setTitle("Title " + id);
        v.setPoster("poster.jpg");
        v.setVideo("video.mp4");   // NOT NULL trong DB
        v.setViews(0);
        v.setBanner(false);
        v.setActive(true);

        dao.create(v);
        return dao.findById(id);
    }

    // SKI_001 – Thêm video mới thành công
    @Test
    public void SKI_001_create_success() {
        Video v = createVideo("ski01");
        Assert.assertNotNull(v);
    }

    // SKI_002 – Thiếu youtubeId (Video NOT NULL)
    @Test
    public void SKI_002_missingVideo() {
        dao.deleteById("ski02");

        Video v = new Video();
        v.setId("ski02");
        v.setTitle("Missing Video");

        Assert.assertThrows(Exception.class, () -> dao.create(v));
    }

    // SKI_003 – Đổi trạng thái sang Tạm đóng
    @Test
    public void SKI_003_setInactive() {
        Video v = createVideo("ski03");
        v.setActive(false);
        dao.update(v);

        Assert.assertFalse(dao.findById("ski03").isActive());
    }

    // SKI_004 – Thêm video trùng ID
    @Test
    public void SKI_004_duplicateId() {
        createVideo("ski04");

        Video duplicate = new Video();
        duplicate.setId("ski04");
        duplicate.setVideo("dup.mp4");

        Assert.assertThrows(Exception.class, () -> dao.create(duplicate));
    }

    // SKI_005 – Cập nhật video thành công
    @Test
    public void SKI_005_update_success() {
        Video v = createVideo("ski05");
        v.setTitle("Updated Title");
        dao.update(v);

        Assert.assertEquals(
                dao.findById("ski05").getTitle(),
                "Updated Title"
        );
    }

    // SKI_006 – Update video không tồn tại
    @Test
    public void SKI_006_update_notExist() {
        dao.deleteById("not_exist");

        Video v = new Video();
        v.setId("not_exist");
        v.setVideo("abc.mp4");
        v.setTitle("Auto Insert");

        dao.update(v);

        Video result = dao.findById("not_exist");

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getTitle(), "Auto Insert");
    }

    // SKI_007 – Upload poster jpg
    @Test
    public void SKI_007_uploadPosterJpg() {
        Video v = createVideo("ski07");
        v.setPoster("poster.jpg");
        dao.update(v);

        Assert.assertEquals(
                dao.findById("ski07").getPoster(),
                "poster.jpg"
        );
    }

    // SKI_008 – Upload poster png
    @Test
    public void SKI_008_uploadPosterPng() {
        Video v = createVideo("ski08");
        v.setPoster("poster.png");
        dao.update(v);

        Assert.assertEquals(
                dao.findById("ski08").getPoster(),
                "poster.png"
        );
    }

    // SKI_009 – Upload poster sai định dạng (gif)
    @Test
    public void SKI_009_uploadPosterGif() {
        Video v = createVideo("ski09");
        v.setPoster("poster.gif");
        dao.update(v);

        Assert.assertEquals(
                dao.findById("ski09").getPoster(),
                "poster.gif"
        );
    }

    // SKI_010 – Lưu video trạng thái ACTIVE
    @Test
    public void SKI_010_setActiveTrue() {
        Video v = createVideo("ski10");
        v.setActive(true);
        dao.update(v);

        Assert.assertTrue(dao.findById("ski10").isActive());
    }

    // SKI_011 – Trang 1
    @Test
    public void SKI_011_page1() {
        List<Video> list = dao.findPage(0, 5);
        Assert.assertTrue(list.size() <= 5);
    }

    // SKI_011 – Trang 2
    @Test
    public void SKI_012_page2() {
        List<Video> list = dao.findPage(5, 5);
        Assert.assertNotNull(list);
    }

    // SKI_013 – Trang vượt giới hạn
    @Test
    public void SKI_013_pageOverflow() {
        List<Video> list = dao.findPage(9999, 5);
        Assert.assertTrue(list.isEmpty());
    }

    //SKI_014 – Trang âm
    @Test
    public void SKI_014_pageNegative() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            dao.findPage(-5, 5);
        });
    }

    //SKI_015 – page không hợp lệ (abc)
    @Test
    public void SKI_015_countAll() {
        int count = dao.countAll();
        Assert.assertTrue(count >= 0);
    }

    // ================= BANNER =================
    //BA_001 – Đánh dấu banner
    @Test
    public void BA_001_setBanner() {
        Video v = createVideo("ba01");
        v.setBanner(true);
        dao.update(v);

        Assert.assertTrue(dao.findById("ba01").isBanner());
    }

    // BA_002 – Gỡ banner
    @Test
    public void BA_002_removeBanner() {
        Video v = createVideo("ba02");
        v.setBanner(true);
        dao.update(v);

        dao.removeBanner("ba02");

        Assert.assertFalse(dao.findById("ba02").isBanner());
    }

    //BA_003 – Gỡ banner ID không tồn tại
    @Test
    public void BA_003_invalidId() {
        dao.removeBanner("invalid");
        Assert.assertNull(dao.findById("invalid"));
    }

    //BA_004 – Gỡ banner khi video không phải banner
    @Test
    public void BA_004_removeNonBanner() {
        Video v = createVideo("ba04");

        dao.removeBanner("ba04");

        Assert.assertFalse(dao.findById("ba04").isBanner());
    }

    //BA_005 – Kiểm tra hiển thị banner
    @Test
    public void BA_005_getBannerVideos() {
        Video v = createVideo("ba05");
        v.setBanner(true);
        dao.update(v);

        List<Video> banners = dao.getBannerVideo();

        Assert.assertTrue(
                banners.stream()
                        .anyMatch(video -> video.getId().equals("ba05"))
        );
    }
}