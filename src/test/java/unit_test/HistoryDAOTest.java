package unit_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.asm.dao.HistoryDAO;
import com.asm.dao.impl.HistoryDAOImpl;
import com.asm.entity.History;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HistoryDAOTest {

    static HistoryDAO historyDAO;
    static Long createdHistoryId;

    @BeforeAll
    static void setUp() {
        historyDAO = new HistoryDAOImpl();
    }

    @Test
    @Order(1)
    void testFindAll() {
        List<History> list = historyDAO.findAll();
        assertNotNull(list, "findAll() không được trả về null");
        System.out.println("testFindAll: Tìm thấy " + list.size() + " bản ghi History");
    }

    @Test
    @Order(2)
    void testCountAll() {
        int count = historyDAO.countAll();
        assertTrue(count >= 0, "countAll() phải >= 0");
        System.out.println("testCountAll: Tổng số History = " + count);
    }

    @Test
    @Order(3)
    void testCreateHistory() {
        int countBefore = historyDAO.countAll();

        History history = new History();
        history.setUserId("admin");
        history.setVideoId("V001");
        history.setViewDate(new Date());
        historyDAO.create(history);

        assertNotNull(history.getId(), "ID phải được tạo tự động");
        createdHistoryId = history.getId();

        int countAfter = historyDAO.countAll();
        assertEquals(countBefore + 1, countAfter,
                "Số lượng History phải tăng 1 sau khi tạo");
        System.out.println("testCreateHistory: Tạo History ID=" + createdHistoryId);
    }

    @Test
    @Order(4)
    void testExistsByUserIdAndVideoIdExist() {
        History history = historyDAO.existsByUserIdAndVideoId("admin", "V001");
        assertNotNull(history, "Phải tìm thấy bản ghi History của admin + V001");
        assertEquals("admin", history.getUserId());
        assertEquals("V001", history.getVideoId());
        System.out.println("testExistsByUserIdAndVideoIdExist: Tìm thấy History ID=" + history.getId());
    }

    @Test
    @Order(5)
    void testExistsByUserIdAndVideoIdNotExist() {
        History history = historyDAO.existsByUserIdAndVideoId("USER_AO", "VIDEO_AO");
        assertNull(history, "Phải trả về null nếu không có bản ghi");
        System.out.println("testExistsByUserIdAndVideoIdNotExist: null (đúng)");
    }

    @Test
    @Order(6)
    void testFindByUserId() {
        List<History> list = historyDAO.findByUserId("admin");
        assertNotNull(list, "findByUserId() không được trả về null");
        assertFalse(list.isEmpty(), "admin phải có ít nhất 1 lịch sử xem (vừa tạo ở test trước)");

        for (History h : list) {
            assertEquals("admin", h.getUserId());
        }
        System.out.println("testFindByUserId: admin có " + list.size() + " bản ghi lịch sử");
    }

    @Test
    @Order(7)
    void testUpdateHistory() {
        if (createdHistoryId == null) {
            History h = historyDAO.existsByUserIdAndVideoId("admin", "V001");
            if (h != null)
                createdHistoryId = h.getId();
        }
        assertNotNull(createdHistoryId);

        History history = historyDAO.findById(createdHistoryId);
        assertNotNull(history);

        Date newDate = new Date();
        history.setViewDate(newDate);
        historyDAO.update(history);

        History updated = historyDAO.findById(createdHistoryId);
        assertNotNull(updated);
        System.out.println("testUpdateHistory: Cập nhật viewDate thành công cho History ID=" + createdHistoryId);
    }

    @Test
    @Order(8)
    void testDeleteHistory() {
        if (createdHistoryId == null) {
            History h = historyDAO.existsByUserIdAndVideoId("admin", "V001");
            if (h != null)
                createdHistoryId = h.getId();
        }
        assertNotNull(createdHistoryId, "Phải có ID để xóa");

        int countBefore = historyDAO.countAll();
        historyDAO.deleteById(createdHistoryId);
        int countAfter = historyDAO.countAll();

        assertEquals(countBefore - 1, countAfter,
                "Số lượng History phải giảm 1 sau khi xóa");
        System.out.println("testDeleteHistory: Xóa History ID=" + createdHistoryId + " thành công");
    }
}
