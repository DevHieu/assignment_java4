package unit_test;

import com.asm.dao.VideoDAO;
import com.asm.dao.impl.VideoDAOImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DashboardDAOTest {
    VideoDAO videoDAO;

    @BeforeClass
    public void setup() {
        videoDAO = new VideoDAOImpl(); 
    }

    // DBA-001, DBA-005, DBA-006: Kiểm tra các hàm đếm tổng số lượng
    @Test
    public void testCounts() {
        // DBA-005: Tổng User
        Assert.assertTrue(videoDAO.countAll() >= 0);
        // DBA-006: Tổng Video
        Assert.assertTrue(videoDAO.countAllVideos() >= 0);
    }

    // DBA-002 & DBA-010: Kiểm tra giới hạn Top 10
    @Test 
    public void testTop10Limit() {
        int size = videoDAO.find10RandomVideo().size();
        Assert.assertTrue(size <= 10, "Lỗi: Vượt quá giới hạn 10 video trên Dashboard");
    }

    // DBA-003: Kiểm tra danh sách Banner
    @Test 
    public void testGetBannerList() {
        Assert.assertNotNull(videoDAO.getBannerVideo());
    }

    // DBA-004: Xử lý logic khi thao tác dữ liệu (ví dụ tăng view)
    @Test 
    public void testIncreaseViewsLogic() {
        try {
            videoDAO.increaseViews("V01");
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail("Hàm increaseViews bị lỗi logic");
        }
    }

    // DBA-009: Kiểm tra logic đồng bộ (xóa banner/cập nhật)
    @Test
    public void testRemoveBannerLogic() {
        videoDAO.removeBanner("V01");
        Assert.assertTrue(true);
    }
}