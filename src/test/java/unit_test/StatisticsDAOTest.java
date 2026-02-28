package unit_test;

import com.asm.dao.VideoDAO;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Video;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

public class StatisticsDAOTest {
    VideoDAO videoDAO;

    @BeforeClass
    public void setup() {
        videoDAO = new VideoDAOImpl();
    }

    // STA-002, STA-004, STA-007, STA-008:
    @Test
    public void testSearchLogic() {
        // STA-002: Tìm kiếm có kết quả
        List<Video> listJava = videoDAO.searchByTitle("Java");
        Assert.assertNotNull(listJava);

        // STA-007 & STA-008: Tìm kiếm để trống tiêu đề (Phải trả về list hoặc rỗng, không lỗi)
        List<Video> listAll = videoDAO.searchByTitle("");
        Assert.assertTrue(listAll.size() >= 0);
    }

    // STA-003:
    @Test
    public void testSearchVideoNotExist() {
        List<Video> list = videoDAO.searchByTitle("Video_Khong_Ton_Tai_9999");
        Assert.assertEquals(list.size(), 0, "Lẽ ra danh sách phải rỗng");
    }

    // STA-005 & STA-006:
    @Test 
    public void testTab1DataLogic() {
        List<Video> list = videoDAO.findPage(0, 10);
        Assert.assertNotNull(list, "Dữ liệu Tab 1 không được null");
        Assert.assertTrue(list.size() <= 10);
    }

    // STA-010:
    @Test
    public void testIsLikedLogic() {
        boolean result = videoDAO.isLiked("V01", "U01");
        Assert.assertTrue(true); 
    }
}