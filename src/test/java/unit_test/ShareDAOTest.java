package unit_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.asm.dao.ShareDAO;
import com.asm.dao.VideoDAO;
import com.asm.dao.impl.ShareDAOImpl;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Share;
import com.asm.entity.User;
import com.asm.entity.Video;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShareDAOTest {

    static ShareDAO shareDAO;
    static VideoDAO videoDAO;
    static Long createdShareId;

    @BeforeAll
    static void setUp() {
        shareDAO = new ShareDAOImpl();
        videoDAO = new VideoDAOImpl();
    }

    @Test
    @Order(1)
    void testFindAll() {
        List<Share> shares = shareDAO.findAll();
        assertNotNull(shares, "findAll() không được trả về null");
        System.out.println("testFindAll: Tìm thấy " + shares.size() + " bản ghi Share");
    }

    @Test
    @Order(2)
    void testCountAll() {
        int count = shareDAO.countAll();
        assertTrue(count >= 0, "countAll() phải >= 0");
        System.out.println("testCountAll: Tổng số Share = " + count);
    }

    @Test
    @Order(3)
    void testCreateShare() {
        User user = new User();
        user.setId("admin");

        Video video = videoDAO.findById("V001");
        assertNotNull(video, "Video V001 phải tồn tại");

        int countBefore = shareDAO.countAll();

        Share share = new Share();
        share.setUser(user);
        share.setVideo(video);
        share.setEmails("test@example.com");
        shareDAO.create(share);

        assertTrue(share.getId() > 0, "ID phải được tạo tự động sau khi persist");
        createdShareId = share.getId();

        int countAfter = shareDAO.countAll();
        assertEquals(countBefore + 1, countAfter,
                "Số lượng Share phải tăng 1 sau khi tạo");
        System.out.println("testCreateShare: Tạo Share ID=" + createdShareId);
    }

    @Test
    @Order(4)
    void testFindById() {
        if (createdShareId == null) {
            List<Share> all = shareDAO.findAll();
            if (!all.isEmpty())
                createdShareId = all.get(0).getId();
        }
        assertNotNull(createdShareId);

        Share share = shareDAO.findById(createdShareId);
        assertNotNull(share, "Phải tìm thấy Share vừa tạo");
        assertEquals("test@example.com", share.getEmails());
        System.out.println("testFindById: Share ID=" + createdShareId + ", emails=" + share.getEmails());
    }

    @Test
    @Order(5)
    void testDeleteShare() {
        assertNotNull(createdShareId, "Phải có ID để xóa");

        int countBefore = shareDAO.countAll();
        shareDAO.deleteById(createdShareId);
        int countAfter = shareDAO.countAll();

        assertEquals(countBefore - 1, countAfter,
                "Số lượng Share phải giảm 1 sau khi xóa");
        System.out.println("testDeleteShare: Xóa Share ID=" + createdShareId + " thành công");
    }
}
